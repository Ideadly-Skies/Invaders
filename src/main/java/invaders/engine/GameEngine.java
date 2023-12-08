package invaders.engine;

import java.util.ArrayList;
import java.util.List;

import invaders.ConfigReader;
import invaders.singleton.EasyLevelConfig;
import invaders.singleton.HardLevelConfig;
import invaders.singleton.MediumLevelConfig;
import invaders.builder.BunkerBuilder;
import invaders.builder.Director;
import invaders.builder.EnemyBuilder;
import invaders.factory.Projectile;
import invaders.entities.Bunker;
import invaders.entities.Enemy;
import invaders.entities.Player;
import invaders.memento.CheatingCaretaker;
import invaders.memento.CheatingSnapshot;
import invaders.memento.SnapshotCaretaker;
import invaders.memento.LastSnapshot;
import invaders.observer.Scoreboard;
import invaders.observer.Subject;
import invaders.observer.Timer;
import invaders.rendering.Renderable;
import org.json.simple.JSONObject;

/**
 * This class manages the main loop and logic of the game which exhibit
 * one-to-many relationship with its observers (Scoreboard and timer) -
 * note that extra observers can be attached if needed in the future
 */
public class GameEngine implements Subject {
	/* renderables attribute */
	private List<Renderable> pendingToAddRenderable = new ArrayList<>();
	private List<Renderable> pendingToRemoveRenderable = new ArrayList<>();

	private List<Renderable> renderables = new ArrayList<>();
	private List<Renderable> enemies = new ArrayList<>();
	private Player player;
	private boolean left;
	private boolean right;
	private int gameWidth;
	private int gameHeight;
	private int timer = 45; // timer for shoot
	private boolean resettingLevel;

	/* observer design pattern attributes */
	private Scoreboard scoreboard;
	private Timer clock;
	private int score = 0;
	private int clock_timer = 0;
	private int pixelsPerSecond = 120;
	private int pixelCounter = 0;
	private boolean scoreTextUpdated;
	private boolean clockTextUpdated;

	/* memento attributes */
	private LastSnapshot lastSnapshot;
	private CheatingSnapshot cheatingSnapshot;
	private boolean isReverting;
	private boolean isCheating;
	private boolean isRemoveAlien;
	private boolean isRemoveFast;
	private SnapshotCaretaker snapshotCaretaker;
	private CheatingCaretaker cheatingCaretaker;
	private boolean gameOver;

	/**
	 * This class manages the main loop and logic of the game
	 */
	public GameEngine(String config) {
		// parse the GameEngine class with the config
		ConfigReader.parse(config);
		initializeRenderables();

		// create observers
		this.scoreboard = new Scoreboard(this);
		this.clock = new Timer(this);
	}

	/**
	 * Initialize/Re-initialize game renderables
	 * with new level information
	 */
	public void initializeRenderables() {

		// clear previous renderables
		renderables.clear();
		enemies.clear();

		// initialize renderable
		gameWidth = ((Long) ((JSONObject) ConfigReader.getGameInfo().get("size")).get("x")).intValue();
		gameHeight = ((Long) ((JSONObject) ConfigReader.getGameInfo().get("size")).get("y")).intValue();

		// Get player info
		this.player = new Player(ConfigReader.getPlayerInfo());
		renderables.add(player);

		Director director = new Director();
		BunkerBuilder bunkerBuilder = new BunkerBuilder();

		// Get Bunkers info
		for (Object eachBunkerInfo : ConfigReader.getBunkersInfo()) {
			Bunker bunker = director.constructBunker(bunkerBuilder, (JSONObject) eachBunkerInfo);
			renderables.add(bunker);
		}

		EnemyBuilder enemyBuilder = new EnemyBuilder();

		// Get Enemy info
		for (Object eachEnemyInfo : ConfigReader.getEnemiesInfo()) {
			Enemy enemy = director.constructEnemy(this, enemyBuilder, (JSONObject) eachEnemyInfo);
			renderables.add(enemy);
			enemies.add(enemy);
		}

		if (scoreboard != null && clock != null) {
			// reset score and timer
			resetScore();
			resetClockTimer();
		}

		// set the snapshots to null && re-initialize caretaker
		resettingLevel = false;
		this.snapshotCaretaker = new SnapshotCaretaker();
		this.cheatingCaretaker = new CheatingCaretaker();
		setLastSnapshot(null);
		setCheatingSnapshot(null);
	}

	/**
	 * reinitialize GameState with snapshot's renderableList
	 * @param renderableList, snapshot's renderableList which is
	 *                        obtained via the memento's getter
	 */
	private void reinitializeGameState(List<Renderable> renderableList) {
		renderables.clear();
		enemies.clear();

		for (Renderable renderable : renderableList) {
			if (renderable.getRenderableObjectName().equals("Player")) {
				// Reset player position
				player.getPosition().setX(renderable.getPosition().getX());
				renderables.add(player);
			}
			else if (renderable.getRenderableObjectName().equals("Enemy")){
				// add enemies to the list
				enemies.add(renderable);
				renderables.add(renderable);
			}
			else {
				renderables.add(renderable);
			}
		}

		// Reset score and timer
		resetScore();
		resetClockTimer();
	}

	/**
	 * clone the Renderable entities in the screen
	 * @return, a List of the cloned Renderable entities
	 */
	private List<Renderable> cloneRenderables() {
		List<Renderable> renderableList = new ArrayList<>();

		for (Renderable renderable : renderables) {
			if (renderable.isAlive()) {
				renderableList.add(renderable.Clone());
			}
		}
		return renderableList;
	}

	/**
	 * Updates the game/simulation
	 */
	public void update() {
		timer += 1;
		pixelCounter++;

		if (!player.isAlive()){
			// game over!
			gameOver = true;
		}

		if (pixelCounter == pixelsPerSecond) {
			// update clock timer && save every 1 second
			updateClockTimer();
			pixelCounter = 0;
		}

		// create cheating snapshot once cheating is triggered
		if (isCheating)
			cheatingCaretaker.saveSnapshot(createCheatingSnapshot());

		movePlayer();

		for (Renderable ro : renderables) {
			// update each renderable objects
			ro.update(this);
		}

		for (int i = 0; i < renderables.size(); i++) {
			Renderable renderableA = renderables.get(i);
			for (int j = i + 1; j < renderables.size(); j++) {
				Renderable renderableB = renderables.get(j);

				if (renderableA.isColliding(renderableB) && (renderableA.getHealth() > 0 && renderableB.getHealth() > 0)) {
					// update score upon hitting enemy
					if ((renderableA.getRenderableObjectName().equals("PlayerProjectile") && renderableB.getRenderableObjectName().equals("Enemy"))
							|| (renderableA.getRenderableObjectName().equals("Enemy") && renderableB.getRenderableObjectName().equals("PlayerProjectile"))) {

						if (renderableA.getRenderableObjectName().equals("PlayerProjectile") && renderableB.getRenderableObjectName().equals("Enemy")) {
							if (renderableB.toString().equals("slow_alien")) {
								// update score to reflect hitting slow alien
								updateScore(3);
							} else if (renderableB.toString().equals("fast_alien")) {
								// update score to reflect hitting fast alien
								updateScore(4);
							}
						} else if (renderableA.getRenderableObjectName().equals("Enemy") && renderableB.getRenderableObjectName().equals("PlayerProjectile")) {
							if (renderableA.toString().equals("slow_alien")) {
								// update score to reflect hitting slow alien
								updateScore(3);
							} else if (renderableA.toString().equals("fast_alien")) {
								// update score to reflect hitting fast alien
								updateScore(4);
							}
						}
					}

					// update score upon hitting enemy projectile
					if ((renderableA.getRenderableObjectName().equals("PlayerProjectile") && renderableB.getRenderableObjectName().equals("EnemyProjectile"))
							|| (renderableA.getRenderableObjectName().equals("EnemyProjectile") && renderableB.getRenderableObjectName().equals("PlayerProjectile"))) {

						if (renderableA.getRenderableObjectName().equals("PlayerProjectile") && renderableB.getRenderableObjectName().equals("EnemyProjectile")) {
							if (renderableB.toString().equals("slow_straight")) {
								// update score to reflect hitting slow alien
								updateScore(1);
							} else if (renderableB.toString().equals("fast_straight")) {
								// update score to reflect hitting fast alien
								updateScore(2);
							}
						} else if (renderableA.getRenderableObjectName().equals("EnemyProjectile") && renderableB.getRenderableObjectName().equals("PlayerProjectile")) {
							if (renderableA.toString().equals("slow_straight")) {
								// update score to reflect hitting slow alien
								updateScore(1);
							} else if (renderableA.toString().equals("fast_straight")) {
								// update score to reflect hitting fast alien
								updateScore(2);
							}
						}
					}

				}

				// logic to handle damage
				if ((renderableA.getRenderableObjectName().equals("Enemy") && renderableB.getRenderableObjectName().equals("EnemyProjectile"))
						|| (renderableA.getRenderableObjectName().equals("EnemyProjectile") && renderableB.getRenderableObjectName().equals("Enemy")) ||
						(renderableA.getRenderableObjectName().equals("EnemyProjectile") && renderableB.getRenderableObjectName().equals("EnemyProjectile"))) {
				} else {
					if (renderableA.isColliding(renderableB) && (renderableA.getHealth() > 0 && renderableB.getHealth() > 0)) {
						renderableA.takeDamage(1);
						renderableB.takeDamage(1);
						if (renderableA.getRenderableObjectName().equals("Enemy"))
							enemies.remove(renderableA);
						else if (renderableB.getRenderableObjectName().equals("Enemy"))
							enemies.remove(renderableB);
					}
				}

			}
		}

		// ensure that renderable foreground objects don't go off-screen
		int offset = 1;
		for (Renderable ro : renderables) {
			if (!ro.getLayer().equals(Renderable.Layer.FOREGROUND)) {
				continue;
			}
			if (ro.getPosition().getX() + ro.getWidth() >= gameWidth) {
				ro.getPosition().setX((gameWidth - offset) - ro.getWidth());
			}

			if (ro.getPosition().getX() <= 0) {
				ro.getPosition().setX(offset);
			}

			if (ro.getPosition().getY() + ro.getHeight() >= gameHeight) {
				ro.getPosition().setY((gameHeight - offset) - ro.getHeight());
			}

			if (ro.getPosition().getY() <= 0) {
				ro.getPosition().setY(offset);
			}
		}

	}

	/**
	 * general getter/methods used by the GameEngine class
	 */
	public List<Renderable> getRenderables() {
		return renderables;
	}
	public List<Renderable> getPendingToAddRenderable() {
		return pendingToAddRenderable;
	}

	public List<Renderable> getPendingToRemoveRenderable() {
		return pendingToRemoveRenderable;
	}
	
	public void leftReleased() {
		this.left = false;
	}

	public void rightReleased() {
		this.right = false;
	}

	public void leftPressed() {
		this.left = true;
	}

	public void rightPressed() {
		this.right = true;
	}

	public boolean shootPressed() {

		if (timer > 45 && player.isAlive()) {
			// save a snapshot of the current state when shooting
			snapshotCaretaker.saveSnapshot(createLastSnapshot());
			Projectile projectile = player.shoot();
			renderables.add(projectile);
			timer = 0;
			return true;
		}
		return false;
	}

	private void movePlayer() {
		if (left) {
			player.left();
		}

		if (right) {
			player.right();
		}
	}

	public int getGameWidth() {
		return gameWidth;
	}

	public int getGameHeight() {
		return gameHeight;
	}

	public Player getPlayer() {
		return player;
	}

	public List<Renderable> getEnemies(){return this.enemies;}

	/**
	 * Singleton pattern method
	 * @param level
	 */
	public void changeDifficultyLevel(String level) {
		if (level.equals("easy")){
			// load easy difficulty
			ConfigReader.parse(EasyLevelConfig.getInstance().getLevelPath());
		}
		else if (level.equals("medium")){
			// load medium difficulty
			ConfigReader.parse(MediumLevelConfig.getInstance().getLevelPath());
		}
		else if (level.equals("hard")){
			// load hard difficulty
			ConfigReader.parse(HardLevelConfig.getInstance().getLevelPath());
		}
		resettingLevel = true;
	}

	public boolean isResettingLevel() {
		return resettingLevel;
	}

	/**
	 * Observer design pattern methods
	 */
	@Override
	public void Notify() {
		// notify the observers of the current
		// state of the GameEngine class
		scoreboard.Update();
		clock.Update();
	}

	@Override
	public void setTimer(Timer timer) {
		this.clock = timer;
	}

	@Override
	public void setScoreboard(Scoreboard scoreboard) {
		this.scoreboard = scoreboard;
	}

	public void updateScore(int value) {
		// update value of the score
		this.score += value;
		Notify();
		setScoreTextUpdated(true);
	}

	public void resetScore() {
		if (isReverting()) {
			this.score = lastSnapshot.getScore();
		}
		else if (isCheating()){
			this.score = cheatingSnapshot.getScore();
		}
		else {
			this.score = 0;
		}
		Notify();
		setScoreTextUpdated(true);
	}

	public void updateClockTimer() {
		// increase clock_timer
		clock_timer++;
		Notify();
		setClockTextUpdated(true);
	}

	public void resetClockTimer() {
		if (isReverting()) {
			this.clock_timer = lastSnapshot.getClockTimer();
		}
		else if (isCheating()){
			this.clock_timer = cheatingSnapshot.getClockTimer();
		}
		else {
			this.clock_timer = 0;
		}
		Notify();
		setClockTextUpdated(true);
	}

	public int getScore() {
		return score;
	}
	public int getTimeRemaining() {
		return clock_timer;
	}
	public void setScoreTextUpdated(boolean scoreTextUpdated) {
		this.scoreTextUpdated = scoreTextUpdated;
	}
	public boolean getScoreTextUpdated() {
		return this.scoreTextUpdated;
	}
	public void setClockTextUpdated(boolean clockTextUpdated) {
		this.clockTextUpdated = clockTextUpdated;
	}
	public boolean getClockTextUpdated() {
		return this.clockTextUpdated;
	}

	/**
	 * LastSnapshot memento design pattern methods
	 */
	public LastSnapshot createLastSnapshot() {
		return new LastSnapshot(cloneRenderables(), score, clock_timer);
	}
	public void setLastSnapshot(LastSnapshot lastSnapshot) {
		this.lastSnapshot = lastSnapshot;
	}

	public void undoLastShot() {
		if (lastSnapshot != null) {
			reinitializeGameState(lastSnapshot.getRenderableList());
		}
	}
	public boolean isReverting() {
		return this.isReverting;
	}

	public void setReverting(boolean value) {
		this.isReverting = value;
	}

	public void undo() {
		setReverting(true);
	}
	public SnapshotCaretaker getSnapshotCaretaker() {
		return this.snapshotCaretaker;
	}

	/**
	 * cheating memento design pattern methods
	 */
	public boolean isCheating(){
		return this.isCheating;
	}

	public void setCheating(boolean value){
		this.isCheating = value;
	}

	public void enableCheat(boolean isRemoveAlien, boolean isRemoveFast){
		this.isRemoveAlien = isRemoveAlien;
		this.isRemoveFast = isRemoveFast;
		setCheating(true);
	}

	public void cheat(){
		if (cheatingSnapshot != null) {
			// load the cheat
			cheatingSnapshot.setRemoveAlien(isRemoveAlien);
			cheatingSnapshot.setRemoveFast(isRemoveFast);
			cheatingSnapshot.loadCheat();

			reinitializeGameState(cheatingSnapshot.getRenderableList());
		}
	}

	public CheatingCaretaker getCheatingCaretaker(){
		return this.cheatingCaretaker;
	}
	public CheatingSnapshot createCheatingSnapshot(){
		return new CheatingSnapshot(cloneRenderables(), score, clock_timer);
	}
	public void setCheatingSnapshot(CheatingSnapshot cheatingSnapshot){
		this.cheatingSnapshot = cheatingSnapshot;
	}
	public boolean isGameOver(){
		return gameOver;
	}
}

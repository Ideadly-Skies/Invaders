# Invaders-
A space shooters game where you survive an incoming alien attack and win by killing all of them. 

1. How to Run Your Code: 
    - simply invoke 'gradle clean build run' on the console
    - NO quirks or special requirements other than the above mentioned are needed to run the code

2. Implemented Features
    - Three difficulty levels in the game: easy, medium, and hard where only a single instance of each level difficulty
      are available in the game. I have implemented a button for each of the difficulty level, so the player can choose
      a level through clicking the button using their mouse. In my implementation, default level difficulty is set to easy. 
      The buttons are located on the bottom right corner of the screen, on top of the undo button, where each represent distinct
      button that will change the game state to match the difficulty level that you chose. 
    - Time and Score is given at the top right hand corner of the screen; The duration of the game is clocked until all aliens
      destroyed or the player lose, where the game displays a continually updating time (initially at 0:00) incrementing by one
      second for each game update cycle. Time and Score is updated automatically so the player won't be needing to click any buttons/
      press any key to update them.
        - the score is increased by 1 point if a player projectile hit an alien's slow projectile
        - the score is increased by 2 points if a player projectile hit an alien's fast projectile
        - the score is increased by 3 points if a player projectile hit a slow alien
        - the score is increased by 4 points if a player projectile hit a fast alien
    - "You Win" and "You Lose" Text is displayed when all aliens are destroyed or when the player dies. 
    - Undo and cheating functionality implemented so that a player can undo the state of the game to the player's last shot (it will be autosave automatically
      to that point once you've started firing a projectile). Cheating functionality implemented so that a player can remove all aliens projectile of the same type
      or all alien's who have the same strategy immediately. There is only one undo button on the bottom right corner of the screen, and that is located below the difficulty 
      level buttons; There are four buttons corresponding to the cheating functionality implemented: a button to remove slow alien, a button to remove fast alien,
      a button to remove slow projectile, and a button to remove fast projectile; located at the bottom left corner of the screen.

3. Design patterns used
    - Singleton pattern is used to implement the three difficulty levels in the game; the participants are defined in its own package called "singleton". The classes
      (with .java encoding) associated with the singleton pattern are:
        - EasyLevelConfig: a singleton class, which is used to configure the level path for the easy level difficulty
        - MediumLevelConfig: a singleton class, which is used to configure the level path for the medium level difficulty 
        - HardLevelConfig: a singleton class, which is used to configure the level path for the hard level difficulty
    - Observer pattern is used to implement the time and score in the game; the participants are defined in its own package called "observer". The classes
      associated with the observer pattern are:
        - Subject: the subject of the observer pattern; and an interface which defines methods that should be implemented by the concrete subject.
        - GameEngine: a concrete subject which gives update to its concrete observers (not defined within the "observer" package).
        - Observer: the observer of the observer pattern; and an interface which defines methods that should be implemented by the concrete observers.
        - Scoreboard: a concrete observer which is responsible for displaying the total scores tallied from the enemy projectile(s) hit and enemy killed. 
        - Timer: a concrete observer which is responsible for displaying continually updating time for the game.
    - Memento design pattern is used to implement the undo and cheating functionality in the game; the participants are defined in its own packaged called "memento". The classes
      associated with the memento pattern are: 
        - GameEngine: acts as the originator in this case, responsible for invoking the createLastSnapshot and createCheatingSnapshot for both undo and cheating functionality respectively. 
          and retrieving the memento back from the caretaker class when wanting to revert/set the game state when undoing the last player shot/implement the cheating functionality.
        - CheatingSnapshot: acts as the memento for the cheating functionality, it is responsible for storing the state of the game when the cheating functionality is activated.
        - LastSnapshot: acts as the memento for the undo functionality, it is responsible for storing the state of the game when the player's last shot.
        - CheatingCaretaker: stores the CheatingSnapshot memento for the GameEngine class to store/retrieve it when wanting to set the state of the game after a cheat is activated
        - SnapshotCaretaker: stores the LastSnapshot memento for the GameEngine class to store/retrieve it when wanting to undo the game state to the point where the player's last shot

4. Selecting difficulty level: 
    - To change to a difficulty level, simply click on any difficulty button that you want at any point in the game, this will dynamically change the difficulty level of the game for you.
    - The difficulty level is set to easy by default. 

5. How to undo and cheat: 
    - the game will automatically autosave once you shoot a shot. Simply click on the undo button to revert the state of the game to the point where you last shoot your shot.
    - to cheat, again simply click on any of the four buttons to remove all aliens projectile of the same type or all alien's who have the same strategy immediately. There are four buttons: 
        - "Remove Fast Alien": removes all fast aliens you see on the screen.
        - "Remove Slow Alien": removes all slow aliens you see on the screen.
        - "Remove Fast Projectile": removes all alien's fast projectiles you see on the screen.
        - "Remove Slow Projectile": removes all alien's slow projectiles you see on the screen. 

6. Additional information: 
   - Game implemented to adhere strictly to the required specification of the assignment and tutors feedback. Each feature aligns with the specified criteria
   - GameEngine class being relatively extensive and contains 569 lines of code. This is due to the comprehensive nature of the game and the inclusion of various features. 
   - if you have any questions or require any further details about specific aspects of the implementation, please feel free to reach out to me.

7. Contact information: 
   - email me at Oana3745@uni.sydney.edu.au (Obie Ananda)

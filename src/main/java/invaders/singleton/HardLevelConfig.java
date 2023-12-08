package invaders.singleton;

/**
 * hard level config singleton class
 */
public class HardLevelConfig {
    // hard level config attributes
    private static HardLevelConfig instance;
    private String levelPath;

    private HardLevelConfig(){
        this.levelPath = "src/main/resources/config_hard.json";
    }

    public static HardLevelConfig getInstance(){
        if (instance == null){
            instance = new HardLevelConfig();
        }
        return instance;
    }

    public String getLevelPath(){
        return this.levelPath;
    }

}
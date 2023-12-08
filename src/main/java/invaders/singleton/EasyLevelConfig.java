package invaders.singleton;

/**
 * easy level config singleton class
 */
public class EasyLevelConfig {
    // easy level config attributes
    private static EasyLevelConfig instance;
    private String levelPath;

    private EasyLevelConfig(){
        // load configuration for the easy level
        this.levelPath = "src/main/resources/config_easy.json";
    }

    public static EasyLevelConfig getInstance(){
        // get instance of the EasyLevelConfig
        if (instance == null){
            instance = new EasyLevelConfig();
        }
        return instance;
    }

    public String getLevelPath(){
        return this.levelPath;
    }
}
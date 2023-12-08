package invaders.singleton;

/**
 * medium level config singleton class
 */
public class MediumLevelConfig {
    // medium level config attributes
    private static MediumLevelConfig instance;
    private String levelPath;

    private MediumLevelConfig(){
        this.levelPath = "src/main/resources/config_medium.json";
    }

    public static MediumLevelConfig getInstance(){
        if (instance == null){
            instance = new MediumLevelConfig();
        }
        return instance;
    }

    public String getLevelPath(){
        return this.levelPath;
    }
}

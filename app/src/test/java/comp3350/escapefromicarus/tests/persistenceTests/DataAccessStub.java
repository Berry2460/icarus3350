package comp3350.escapefromicarus.tests.persistenceTests;

import java.util.HashMap;

import comp3350.escapefromicarus.persistence.DataAccess;

// Implements Stub Database
public class DataAccessStub implements DataAccess {

    // indexs for stats
    private final static int LIFE = 0;
    private final static int ATTACK = 1;
    private final static int DEFENSE = 2;
    private final static int SPEED = 3;
    private final static int SAVE = 4;

    private HashMap<String, String> textures;
    private HashMap<String, float[]> stats;
    private HashMap<String, String> audio;
    private Float[] save = new Float[8];

    public DataAccessStub() {

        // set up initial hard coded character stats and textures
        textures = new HashMap<>();
        stats = new HashMap<>();
        audio = new HashMap<>();
    }

    public void open(String stub) {

        // Add textures to Stub DB
        textures.put("bottomLeftWallStone", "tileset/bottomLeftWallStone.png");
        textures.put("bottomRightWallStone", "tileset/bottomRightWallStone.png");
        textures.put("combinedWallStone", "tileset/combinedWallStone.png");
        textures.put("craftacular", "font/craftacular-ui.png");
        textures.put("grass", "tileset/grass.png");
        textures.put("leftWallStone", "tileset/leftWallStone.png");
        textures.put("lifeBar", "UI/lifebar.png");
        textures.put("menuFont", "font/menuFont.png");
        textures.put("pauseBtn", "UI/pauseBtn.png");
        textures.put("player", "characters/player.png");
        textures.put("rightWallStone", "tileset/rightWallStone.png");
        textures.put("sideWallGrass", "tileset/sideWallGrass.png");
        textures.put("sideWallStone", "tileset/sideWallStone.png");
        textures.put("slime", "characters/slime.png");
        textures.put("splashscreen", "UI/splashscreen.png");
        textures.put("stone", "tileset/stone.png");
        textures.put("theEnd", "UI/TheEnd.png");
        textures.put("woodenDoor", "tileset/woodenDoor.png");

        textures.put("grass0", "tileset/grass0.png");
        textures.put("grass1", "tileset/grass1.png");
        textures.put("grass2", "tileset/grass2.png");
        textures.put("grass3", "tileset/grass3.png");
        textures.put("grass4", "tileset/grass4.png");
        textures.put("grass5", "tileset/grass5.png");
        textures.put("grass6", "tileset/grass6.png");
        textures.put("grass7", "tileset/grass7.png");
        textures.put("grass8", "tileset/grass8.png");
        textures.put("grass9", "tileset/grass9.png");
        textures.put("grass10", "tileset/grass10.png");
        textures.put("grass11", "tileset/grass11.png");
        textures.put("grass12", "tileset/grass12.png");
        textures.put("grass13", "tileset/grass13.png");
        textures.put("grass14", "tileset/grass14.png");
        textures.put("grass15", "tileset/grass15.png");
        textures.put("grass16", "tileset/grass16.png");

        // Add stats to Stub DB
        float[] characterStats = {12, 1, 0, 1.7f, 0.f};
        stats.put("player", characterStats);

        characterStats = new float[]{5, 1, 1, 0.9f, 0.f};
        stats.put("slime", characterStats);

        // Add audio to Stub DB
        audio.put("gameOverInitialMusic", "Music/gameOverInitialMusic.wav");
        audio.put("gameOverLoopMusic", "Music/gameOverLoopMusic.wav");
        audio.put("levelMusic", "Music/levelMusic.ogg");
        audio.put("mainMenuMusic", "Music/mainMenuMusic.ogg");
        audio.put("victoryInitialMusic", "Music/victoryInitialMusic.wav");
        audio.put("victoryLoopMusic", "Music/victoryLoopMusic.wav");


        save[0] = 1f;   //Default volume
        save[1] = 1f;   //Default difficulty
        save[2] = 0f;   //Default level
        save[3] = 0f;   //Level Seed
        save[4] = 0f;   //SAVE EXISTS boolean
        save[5] = 0f;   // Game Muted is set to false by default
        save[6] = 5f;   // Maximum number of level is set to 5 by default
        save[7] = 0f;   // Music Muted is set to false by default

    }

    public void close() {

        //empty but required
        //implements data access which requires close for HSQLDB
    }

    public int getLife(String character) {

        int result = -1;
        float[] stats = this.stats.get(character);
        if (stats != null) {
            result = (int)stats[LIFE];
        }
        return result;
    }

    public float getSpeed(String character) {

        float result = -1;
        float[] stats = this.stats.get(character);
        if (stats != null) {
            result = stats[SPEED];
        }
        return result;
    }

    public int getAttack(String character) {

        int result = -1;
        float[] stats = this.stats.get(character);
        if (stats != null) {
            result = (int)stats[ATTACK];
        }
        return result;
    }

    public int getDefense(String character) {

        int result = -1;
        float[] stats = this.stats.get(character);
        if (stats != null) {
            result = (int)stats[DEFENSE];
        }
        return result;
    }

    public String getTexture(String textureName) {

        return textures.get(textureName);
    }

    public String getAudio(String audioName) {

        return audio.get(audioName);
    }

    public int updateLife(String character, int amount) {

        int updatedLife = -1;
        int currentLife;

        float[] stats = this.stats.get(character);
        if (stats != null) {

            currentLife = getLife(character);
            if(amount > 0) {
                updatedLife = currentLife + amount;
            }
            else{
                if((amount * -1) >= currentLife) {
                    updatedLife = 1;
                }
                else{
                    updatedLife = currentLife + amount;
                }
            }

            stats[LIFE] = updatedLife;
            updatedLife = (int)stats[LIFE];
            save[SAVE] = 1.0f; // save after life increase
        }
        return updatedLife;
    }

    public int newGame() {

        float[] characterStats = {12, 1, 0, 1.7f};
        stats.put("player", characterStats);

        save[1] = 1f;   //Default difficulty
        save[2] = 0f;   //Default level
        save[3] = 0f;   //Level Seed
        save[4] = 0f;   //SAVE EXISTS boolean
        save[5] = 0f;   // Muted is set to false by default
        save[6] = 5f;   // Maximum number of level is set to 5 by default
        return 1;
    }

    public float getVolume() {

        return save[0];
    }

    public float setVolume(float level) {

        if(level >= 0 && level <= 1)
            save[0] = level;
        return save[0];
    }

     public int setLevel(int level) {

         int currentLevel = -1;
         if (level >= 0 && level <= 30) {
             save[2] = (float) level;
             currentLevel = Math.round(save[2]);
         }
        return currentLevel;
     }

    public int getLevel() {

        return Math.round(save[2]);
    }


    public int getSeed() {

        return Math.round(save[3]);
    }

    public int setSeed(int value) {

        int seed = -1;
        if(value >= 0 && value <= 1000000){
            save[3] = (float)value;
            seed = Math.round(save[3]);
        }
        return seed;
    }

    public int saveExists() {

        return Math.round(save[4]);
    }

    public int isGameMuted() {

        return Math.round(save[5]);
    }

    public int setGameMute(int value) {

        if(value == 0 || value == 1) {
            save[5] = (float)value;
        }
        return Math.round(save[5]);
    }

    public int isMusicMuted() {

        return Math.round(save[7]);
    }

    public int setMusicMute(int value) {

        if(value == 0 || value == 1){
            save[7] = (float)value;
        }
        return Math.round(save[7]);
    }

    public int getMaxLevel() {

        return Math.round(save[6]);
    }

    public int setMaxLevel(int value) {

        int maxLevel = -1;

        if (value >= 1){
            save[6] = (float)value;
            maxLevel = Math.round(save[6]);
        }
        return maxLevel;
    }

    public void setDifficulty(int value) {

        if (value >= 1 && value <= 30) {
            save[1] = (float) value;
        }
    }

    public int getDifficulty() {

        return Math.round(save[1]);
    }
}
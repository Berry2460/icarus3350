package comp3350.escapefromicarus.presentation;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

import comp3350.escapefromicarus.application.Main;
import comp3350.escapefromicarus.application.Services;
import comp3350.escapefromicarus.persistence.DataAccess;

public class MyGame extends Game {

    private DataAccess dataAccess;
    private ScreenAdapter[] allScreens;
    private Music music;
    private int difficulty;
    private float musicVol;
    private boolean muted;
    private boolean musicMuted;
    private boolean musicContinue; //checks if screen change from SplashScreen or InstructionsScreen
    private AssetManager audioManager;

    public MyGame() {

        super();
        this.dataAccess = Services.getDataAccess(Main.dbName);
        this.audioManager = new AssetManager();
        this.musicVol = this.dataAccess.getVolume();
        this.musicContinue = false;
        this.difficulty = this.dataAccess.getDifficulty();

        if(this.dataAccess.isGameMuted() == 1) {
            this.muted = true;
            this.musicMuted = true;
        }
        else {
            if(this.dataAccess.isMusicMuted() == 1) {
                this.musicMuted = true;
            }
            else {
                this.musicMuted = false;
            }
            this.muted = false;
        }
    }

    @Override
    public void create() {

        loadMusic();
        this.allScreens = setScreenArray();
        changeScreen(ScreenType.SPLASH_SCREEN);
    }

    @Override
    public void dispose() {

        //dispose and exit
        unloadMusic();
        this.audioManager.dispose();
        getScreen().dispose();
        this.music.dispose();
        Gdx.app.exit();
    }

    // function called from other screen classes to switch the screens
    public void changeScreen(ScreenType screen) {

        if(screen == ScreenType.GAME_SCREEN) {
            //GameScreen specifically has to be disposed and created new
            this.allScreens[screen.getIndex()].dispose();
            this.allScreens[screen.getIndex()] = new GameScreen(this);
        }
        setScreen(this.allScreens[screen.getIndex()]);
        this.allScreens[screen.getIndex()].show();
    }

    private ScreenAdapter[] setScreenArray() {

        ScreenAdapter[] allScreens = new ScreenAdapter[7];

        allScreens[ScreenType.SPLASH_SCREEN.getIndex()] = new SplashScreen(this);
        allScreens[ScreenType.GAME_SCREEN.getIndex()] = new GameScreen(this);
        allScreens[ScreenType.MENU_SCREEN.getIndex()] = new MenuScreen(this);
        allScreens[ScreenType.GAME_OVER_SCREEN.getIndex()] = new GameOver(this);
        allScreens[ScreenType.GAME_END_SCREEN.getIndex()] = new EndScreen(this);
        allScreens[ScreenType.INSTRUCTIONS_SCREEN.getIndex()] = new InstructionsScreen(this);
        allScreens[ScreenType.CREDITS_SCREEN.getIndex()] = new CreditsScreen(this);

        return allScreens;
    }

    public void makeNewGame(DifficultyLevel difficultylvl) {

        this.difficulty = difficultylvl.getIndex();
        this.dataAccess.newGame();
        this.dataAccess.setDifficulty(this.difficulty);

        this.allScreens[ScreenType.GAME_SCREEN.getIndex()] = new GameScreen(this);
    }

    public int getDifficulty() {

        return this.difficulty;
    }

    public void setMusic(Music music) {

        if(this.music != null && this.music.isPlaying()) {
            this.music.stop();
        }
        this.music = music;
        this.music.play();
    }

    public float getMusicVol() {

        float result = this.musicVol;

        if(this.muted || this.musicMuted) {
            result = 0.0f;
        }
        return result;
    }
    
    public void muteGame() {

        this.muted = true;
        this.dataAccess.setGameMute(1);
        muteMusic();
    }
    
    public void unmuteGame() {

        unmuteMusic();
        this.muted = false;
        this.dataAccess.setGameMute(0);
    }

    public void muteMusic() {

        this.music.setVolume(0.0f);

        if(!this.muted) {
            //if this was not a forced mute
            this.musicMuted = true;
            this.dataAccess.setMusicMute(1);
        }
    }

    public void unmuteMusic() {

        if(this.muted) {
            //if the gameSound was muted, check if our music was muted
            if(!this.musicMuted) {
                //music previously wasn't muted so play music
                this.music.setVolume(this.musicVol);
            }
        }
        else {
            //if the gameSound was not muted, then play music and update DB
            this.music.setVolume(this.musicVol);
            this.musicMuted = false;
            this.dataAccess.setMusicMute(0);
        }
    }
    
    public boolean getMuted() {
        
        return this.muted;
    }

    public boolean getMusicMuted() {

        return this.musicMuted;
    }
    
    public boolean getMusicContinue() {

        return this.musicContinue;
    }

    public void setMusicContinue(boolean isItStart) {

        this.musicContinue = isItStart;
    }

    public void loadMusic() {

        this.audioManager.load(this.dataAccess.getAudio("mainMenuMusic"), Music.class);
        this.audioManager.load(this.dataAccess.getAudio("levelMusic"), Music.class);
        this.audioManager.load(this.dataAccess.getAudio("victoryInitialMusic"), Music.class);
        this.audioManager.load(this.dataAccess.getAudio("victoryLoopMusic"), Music.class);
        this.audioManager.load(this.dataAccess.getAudio("gameOverInitialMusic"), Music.class);
        this.audioManager.load(this.dataAccess.getAudio("gameOverLoopMusic"), Music.class);

        this.audioManager.finishLoading();
    }

    private void unloadMusic() {

        this.audioManager.unload(this.dataAccess.getAudio("mainMenuMusic"));
        this.audioManager.unload(this.dataAccess.getAudio("levelMusic"));
        this.audioManager.unload(this.dataAccess.getAudio("victoryInitialMusic"));
        this.audioManager.unload(this.dataAccess.getAudio("victoryLoopMusic"));
        this.audioManager.unload(this.dataAccess.getAudio("gameOverInitialMusic"));
        this.audioManager.unload(this.dataAccess.getAudio("gameOverLoopMusic"));
    }

    public AssetManager getMusicManager() {

        return this.audioManager;
    }
}

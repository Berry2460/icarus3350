package comp3350.escapefromicarus.presentation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class GameOver extends Screen {

    private final float BUTTON_SIZE = 100.0f;
    private final float BUTTON_SPACE = 25.0f;
    private final float GAME_OVER_SIZE = 25.0f;
    private final float TRY_AGAIN_SIZE = 10.0f;
    private final Skin BUTTON_SKIN = new Skin(Gdx.files.internal("font/craftacular-ui.json"));
    private final BitmapFont FONT_STYLE = new BitmapFont(Gdx.files.internal("font/menuFont.fnt"));

    private Music musicLoop;
    private float tryAgainHeight;
    private float tryAgainY;

    public GameOver(MyGame mainApplication) {

        super(mainApplication);
        this.background = new Texture(this.dataAccess.getTexture("theEnd"));
        createLabels();
        addButtons();
        makeMusic();
    }

    @Override
    public void show() {

        super.show();
        //always set the level back to 1 if gameOver
        this.dataAccess.setLevel(0);
        this.musicLoop.setVolume(this.mainApplication.getMusicVol());
        this.mainApplication.setMusic(this.music);
    }

    @Override
    public void render(float delta) {

        this.setting.doBasicRender(delta, this.stage, this.batch, null);
    }

    @Override
    public void dispose() {

        super.dispose();
        BUTTON_SKIN.dispose();
        FONT_STYLE.dispose();
    }

    //Add labels with the instructions
    private void createLabels() {

        float gameOverHeight = this.stage.getHeight() / 5;
        float gameOverWidth = this.stage.getWidth() / 2;

        float optionHeight = gameOverHeight / 2;
        float optionWidth = gameOverWidth / 3;

        //Initializing the style for the labels, and setting the styles below
        Label.LabelStyle gameOverStyle = new Label.LabelStyle();
        Label.LabelStyle optionStyle = new Label.LabelStyle();

        gameOverStyle.font = FONT_STYLE;
        optionStyle.font = FONT_STYLE;

        //Label that has the title of the screen "How To Play"
        Label gameOverLabel = new Label("GAME OVER", gameOverStyle);

        //Label that has the title of the screen "How To Play"
        Label tryAgainLabel = new Label("Try Again?", optionStyle);

        //Place gameOver at the top center
        gameOverLabel.setSize(gameOverWidth, gameOverHeight);
        gameOverLabel.setPosition(this.stage.getWidth() / 2 - (gameOverLabel.getWidth() / 2), this.stage.getHeight() - (gameOverHeight * 2));
        gameOverLabel.setAlignment(Align.center);
        gameOverLabel.setFontScale(GAME_OVER_SIZE,GAME_OVER_SIZE);
        gameOverLabel.setWrap(false);

        //Place tryAgainLabel below gameOverLabel
        tryAgainLabel.setSize(optionWidth, optionHeight);
        tryAgainLabel.setPosition(this.stage.getWidth() / 2 - (tryAgainLabel.getWidth() / 2),gameOverLabel.getY() - gameOverHeight);
        tryAgainLabel.setAlignment(Align.center);
        tryAgainLabel.setFontScale(TRY_AGAIN_SIZE,TRY_AGAIN_SIZE);
        tryAgainLabel.setWrap(false);

        this.stage.addActor(gameOverLabel);
        this.stage.addActor(tryAgainLabel);

        //save the tryAgain label location for the buttons
        this.tryAgainHeight = tryAgainLabel.getHeight();
        this.tryAgainY = tryAgainLabel.getY();
    }

    //Adds the Buttons for the screen into the stage for rendering
    private void addButtons() {

        TextButton yesButton = new TextButton("Yes", BUTTON_SKIN);
        TextButton noButton = new TextButton("No", BUTTON_SKIN);

        yesButton.setWidth(BUTTON_SIZE);
        yesButton.setHeight(BUTTON_SIZE);
        yesButton.setPosition((this.stage.getWidth() / 2) - yesButton.getWidth() - BUTTON_SPACE, this.tryAgainY - this.tryAgainHeight - BUTTON_SIZE);

        noButton.setWidth(BUTTON_SIZE);
        noButton.setHeight(BUTTON_SIZE);
        noButton.setPosition((this.stage.getWidth() / 2) + BUTTON_SPACE, this.tryAgainY - this.tryAgainHeight - BUTTON_SIZE);

        yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundPlayer.setEffect(SoundEffect.BUTTON_FX);
                mainApplication.changeScreen(ScreenType.GAME_SCREEN);
            }
        });

        noButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundPlayer.setEffect(SoundEffect.BUTTON_FX);
                mainApplication.changeScreen(ScreenType.MENU_SCREEN);
            }
        });

        this.stage.addActor(yesButton);
        this.stage.addActor(noButton);
    }

    public void makeMusic() {

        AssetManager manager = this.mainApplication.getMusicManager();
        this.music = manager.get("Music/gameOverInitialMusic.wav", Music.class);
        this.music.setLooping(false);
        this.music.setVolume(this.mainApplication.getMusicVol());

        this.musicLoop = manager.get("Music/gameOverLoopMusic.wav", Music.class);
        this.musicLoop.setLooping(true);
        this.musicLoop.setVolume(this.mainApplication.getMusicVol());

        this.music.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                mainApplication.setMusic(musicLoop);
            }
        });
    }
}

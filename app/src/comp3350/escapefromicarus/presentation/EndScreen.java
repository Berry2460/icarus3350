package comp3350.escapefromicarus.presentation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class EndScreen extends Screen {

    private final float BUTTON_WIDTH = 600.0f;
    private final float MARGIN = 100.0f;
    private final float HEART_SPACING = 10.0f;
    private final Skin BUTTON_SKIN = new Skin(Gdx.files.internal("font/craftacular-ui.json"));
    private final BitmapFont FONT_STYLE = new BitmapFont(Gdx.files.internal("font/blackfont.fnt"));
    private Music musicLoop;
    private Group boostPopup;

    public EndScreen(MyGame mainApplication) {

        super(mainApplication);
        this.background = new Texture(this.dataAccess.getTexture("theEnd"));
        addButtons();
        makeMusic();
        addLabels();
        addBoostPopup();
    }

    @Override
    public void show() {

        super.show();
        this.dataAccess.setLevel(0);
        this.dataAccess.updateLife("player", 1);
        this.musicLoop.setVolume(this.mainApplication.getMusicVol());
        this.mainApplication.setMusic(this.music);
    }

    @Override
    public void dispose() {

        super.dispose();
        BUTTON_SKIN.dispose();
    }

    //Adds the Buttons for the screen into the this.stage for rendering
    private void addButtons() {

        TextButton menuButton = new TextButton("Menu", BUTTON_SKIN);
        menuButton.setWidth(BUTTON_WIDTH);
        menuButton.setPosition((this.stage.getWidth() / 2) - menuButton.getWidth() / 2, MARGIN);

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundPlayer.setEffect(SoundEffect.BUTTON_FX);
                mainApplication.changeScreen(ScreenType.MENU_SCREEN);
            }
        });

        this.stage.addActor(menuButton);
    }

    private void addLabels() {

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = FONT_STYLE;
        Label escapedLabel = new Label("You escaped!!!", titleStyle);
        escapedLabel.setAlignment(Align.center);
        escapedLabel.setFontScale(10f,10f);
        escapedLabel.setWrap(false);
        escapedLabel.setPosition(this.stage.getWidth() / 2 - escapedLabel.getWidth() / 2, this.stage.getHeight() - (escapedLabel.getHeight() + MARGIN * 2));

        this.stage.addActor(escapedLabel);
    }

    private void addBoostPopup() {

        boostPopup = new Group();
        Image popupPanelFrame = new Image(new Texture(this.dataAccess.getTexture("playerFrame")));
        Image popupPanel = new Image(new Texture(this.dataAccess.getTexture("hudPanel")));
        Image heart = new Image(new Texture(this.dataAccess.getTexture("heart")));
        Image heart2 = new Image(new Texture(this.dataAccess.getTexture("heart")));
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = FONT_STYLE;
        Label boostTitle = new Label("Health Boost", titleStyle);
        Label healthBoost = new Label("You now receive a permanent 2 life point increase on your next run!", titleStyle);
        Label warning = new Label("Note: The boost only applies if you continue the game and resets if you play a new game.", titleStyle);

        //set parameters
        popupPanel.setSize(this.stage.getWidth() / 2, this.stage.getHeight() / 3);
        popupPanel.setPosition(this.stage.getWidth() / 2 - popupPanel.getWidth() / 2, this.stage.getHeight() / 2 - popupPanel.getHeight() / 2);

        popupPanelFrame.setSize(popupPanel.getWidth() + HEART_SPACING * 5, popupPanel.getHeight() + HEART_SPACING * 5);
        popupPanelFrame.setPosition(this.stage.getWidth() / 2 - popupPanelFrame.getWidth() / 2,
                                    this.stage.getHeight() / 2 - popupPanelFrame.getHeight() / 2);

        boostTitle.setSize(popupPanel.getWidth() / 3, popupPanel.getHeight() / 3);
        boostTitle.setAlignment(Align.center);
        boostTitle.setFontScale(5.0f, 5.0f);
        boostTitle.setWrap(false);
        boostTitle.setPosition(this.stage.getWidth() / 2 - boostTitle.getWidth() / 2,popupPanel.getY() + popupPanel.getHeight() - boostTitle.getHeight());

        healthBoost.setSize(popupPanel.getWidth() - MARGIN / 4, popupPanel.getHeight() / 3);
        healthBoost.setAlignment(Align.center);
        healthBoost.setFontScale(3.0f, 3.0f);
        healthBoost.setWrap(true);
        healthBoost.setPosition(this.stage.getWidth() / 2 - healthBoost.getWidth() / 2,boostTitle.getY() - healthBoost.getHeight());

        warning.setSize(popupPanel.getWidth() - MARGIN / 4, popupPanel.getHeight() / 5);
        warning.setAlignment(Align.center);
        warning.setFontScale(2.5f, 2.5f);
        warning.setWrap(true);
        warning.setPosition(this.stage.getWidth() / 2 - warning.getWidth() / 2,healthBoost.getY() - warning.getHeight());

        heart.setSize(boostTitle.getHeight(), boostTitle.getHeight());
        heart.setPosition(popupPanel.getX() + (popupPanel.getWidth() / 5) - (HEART_SPACING * 2),
                          popupPanel.getY() + popupPanel.getHeight() - boostTitle.getHeight());

        heart2.setSize(boostTitle.getHeight(), boostTitle.getHeight());
        heart2.setPosition(popupPanel.getX() + popupPanel.getWidth() - ((popupPanel.getWidth() / 5) + heart2.getWidth()) + HEART_SPACING,
                           popupPanel.getY() + popupPanel.getHeight() - boostTitle.getHeight());

        this.boostPopup.addActor(popupPanelFrame);
        this.boostPopup.addActor(popupPanel);
        this.boostPopup.addActor(boostTitle);
        this.boostPopup.addActor(heart);
        this.boostPopup.addActor(heart2);
        this.boostPopup.addActor(healthBoost);
        this.boostPopup.addActor(warning);

        this.stage.addActor(this.boostPopup);
    }

    public void makeMusic() {

        AssetManager manager = this.mainApplication.getMusicManager();
        this.music = manager.get("Music/victoryInitialMusic.wav", Music.class);
        this.music.setLooping(false);
        this.music.setVolume(this.mainApplication.getMusicVol());

        this.musicLoop = manager.get("Music/victoryLoopMusic.wav", Music.class);
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

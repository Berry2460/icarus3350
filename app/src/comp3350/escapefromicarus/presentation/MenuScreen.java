package comp3350.escapefromicarus.presentation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class MenuScreen extends Screen {

    private final float BUTTON_WIDTH = 600.0f;
    private final float BUTTON_SPACE = 30.0f;
    private final float BUTTON_SPACING = 25.0f;
    private final Skin BUTTON_SKIN = new Skin(Gdx.files.internal("font/craftacular-ui.json"));
    private final BitmapFont FONT_STYLE = new BitmapFont(Gdx.files.internal("font/menuFont.fnt"));

    private TextButton continueButton;
    private TextButton newGameButton;
    private TextButton instructionsButton;
    private TextButton creditsButton;
    private TextButton exitButton;
    private Group difficultyGroup;
    private boolean isPaused;

    public MenuScreen(MyGame mainApplication) {

        super(mainApplication);
        this.background = new Texture(this.dataAccess.getTexture("mainscreen"));
        this.isPaused = false;
        setButtons();
        makeMusic();
    }

    @Override
    public void show() {

        super.show();
        if(this.dataAccess.saveExists() == 1)
        {
            this.continueButton.setTouchable(Touchable.enabled);
        }
        if(!this.mainApplication.getMusicContinue()) {
            //from any other screen other than instructions to menuScreen we need to change music
            this.mainApplication.setMusic(this.music);
        }
    }

    @Override
    public void dispose() {

        super.dispose();
        BUTTON_SKIN.dispose();
    }

    private void setButtons() {

        float buttonX = this.stage.getWidth() - ((this.stage.getWidth() / 4) + BUTTON_WIDTH / 2);
        //Instantiating buttons, and making it appearing on the screen.
        this.continueButton = new TextButton("Continue", BUTTON_SKIN);
        this.newGameButton = new TextButton("New Game", BUTTON_SKIN);
        this.instructionsButton = new TextButton("Instructions", BUTTON_SKIN);
        this.creditsButton = new TextButton("Credits", BUTTON_SKIN);
        this.exitButton = new TextButton("Exit", BUTTON_SKIN);

        //Set X and Y coordinates for the buttons
        this.continueButton.setWidth(BUTTON_WIDTH);
        this.continueButton.setPosition(buttonX,this.stage.getHeight() / 2 + (this.continueButton.getHeight() + BUTTON_SPACE * 2 + BUTTON_SPACE / 2));

        this.newGameButton.setWidth(BUTTON_WIDTH);
        this.newGameButton.setPosition(buttonX,this.continueButton.getY() - (this.continueButton.getHeight() + BUTTON_SPACE));

        this.instructionsButton.setWidth(BUTTON_WIDTH);
        this.instructionsButton.setPosition(buttonX,this.newGameButton.getY() - (this.newGameButton.getHeight() + BUTTON_SPACE));

        this.creditsButton.setWidth(BUTTON_WIDTH);
        this.creditsButton.setPosition(buttonX,this.instructionsButton.getY() - (this.instructionsButton.getHeight() + BUTTON_SPACE));

        this.exitButton.setWidth(BUTTON_WIDTH);
        this.exitButton.setPosition(buttonX,this.creditsButton.getY() - (this.creditsButton.getHeight() + BUTTON_SPACE));

        //Connecting buttons to mouse/tap input/click events.
        this.continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                SoundPlayer.setEffect(SoundEffect.BUTTON_FX);
                mainApplication.changeScreen(ScreenType.GAME_SCREEN);
                mainApplication.setMusicContinue(false);
            }
        });

        this.newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                SoundPlayer.setEffect(SoundEffect.BUTTON_FX);
                //get difficulty selection
                setDifficulty();
            }
        });

        this.instructionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                SoundPlayer.setEffect(SoundEffect.BUTTON_FX);
                mainApplication.changeScreen(ScreenType.INSTRUCTIONS_SCREEN);
                mainApplication.setMusicContinue(true);
            }
        });

        this.creditsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                SoundPlayer.setEffect(SoundEffect.BUTTON_FX);
                mainApplication.changeScreen(ScreenType.CREDITS_SCREEN);
                mainApplication.setMusicContinue(true);
            }
        });

        this.exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                SoundPlayer.setEffect(SoundEffect.BUTTON_FX);
                Gdx.app.exit();
            }
        });

        //if newGame was never made then disable the continue button
        if(this.dataAccess.saveExists() == 0)
        {
            this.continueButton.setTouchable(Touchable.disabled);
            this.continueButton.setVisible(false);
        }

        //Adding them to the screen to make them appear.
        this.stage.addActor(this.continueButton);
        this.stage.addActor(this.newGameButton);
        this.stage.addActor(this.instructionsButton);
        this.stage.addActor(this.creditsButton);
        this.stage.addActor(this.exitButton);
    }

    private void setDifficulty() {

        this.isPaused = true;
        //make other buttons untouchable
        this.continueButton.setTouchable(Touchable.disabled);
        this.newGameButton.setTouchable(Touchable.disabled);
        this.instructionsButton.setTouchable(Touchable.disabled);
        this.creditsButton.setTouchable(Touchable.disabled);
        this.exitButton.setTouchable(Touchable.disabled);
        this.difficultyGroup = new Group();
        addDifficultyButtons();
        this.stage.addActor(this.difficultyGroup);
    }

    private void addDifficultyButtons() {

        Table table = new Table();
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = FONT_STYLE;
        Label difficultyChoice = new Label("Choose the game difficulty", titleStyle);
        TextButton cancelButton = new TextButton("Cancel", this.BUTTON_SKIN);
        TextButton hardButton = new TextButton("Hard", this.BUTTON_SKIN);
        TextButton normalButton = new TextButton("Normal", this.BUTTON_SKIN);
        TextButton easyButton = new TextButton("Easy", this.BUTTON_SKIN);

        table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(this.dataAccess.getTexture("copperhud")))));
        table.setWidth(this.stage.getWidth()/2);
        table.setHeight(this.stage.getHeight());
        table.setPosition(this.stage.getWidth() / 2 - table.getWidth() / 2, this.stage.getHeight() / 2 - table.getHeight() / 2);

        difficultyChoice.setAlignment(Align.center);
        difficultyChoice.setFontScale(4f,4f);
        difficultyChoice.setWrap(false);

        hardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundPlayer.setEffect(SoundEffect.BUTTON_FX);
                makeNewGame(DifficultyLevel.HARD);
            }
        });

        normalButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundPlayer.setEffect(SoundEffect.BUTTON_FX);
                makeNewGame(DifficultyLevel.NORMAL);
            }
        });

        easyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundPlayer.setEffect(SoundEffect.BUTTON_FX);
                makeNewGame(DifficultyLevel.EASY);
            }
        });

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundPlayer.setEffect(SoundEffect.BUTTON_FX);
                cancelNewGame();
            }
        });

        table.add(difficultyChoice);
        table.row().pad(BUTTON_SPACING);
        table.add(hardButton);
        table.row().pad(BUTTON_SPACING);
        table.add(normalButton);
        table.row().pad(BUTTON_SPACING);
        table.add(easyButton);
        table.row().pad(BUTTON_SPACING);
        table.add(cancelButton);
        this.difficultyGroup.addActor(table);
    }

    private void cancelNewGame() {

        if(this.isPaused) {
            this.difficultyGroup.remove();

            //enable all the buttons
            this.continueButton.setTouchable(Touchable.enabled);
            this.newGameButton.setTouchable(Touchable.enabled);
            this.instructionsButton.setTouchable(Touchable.enabled);
            this.creditsButton.setTouchable(Touchable.enabled);
            this.exitButton.setTouchable(Touchable.enabled);
            this.isPaused = false;
        }
    }

    private void makeNewGame(DifficultyLevel difficultyLvl) {

        //reset the menuScreen for when we come back later
        cancelNewGame();
        this.continueButton.setVisible(true);

        //make a new game
        this.mainApplication.makeNewGame(difficultyLvl);
        this.mainApplication.changeScreen(ScreenType.GAME_SCREEN);
        this.mainApplication.setMusicContinue(false);
    }
}
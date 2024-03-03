package comp3350.escapefromicarus.presentation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class InstructionsScreen extends Screen {

    private final float MARGIN = 50.0f;
    private final float BUTTON_WIDTH = 600.0f;
    private final Skin BUTTON_SKIN = new Skin(Gdx.files.internal("font/craftacular-ui.json"));
    private final BitmapFont FONT_STYLE = new BitmapFont(Gdx.files.internal("font/menuFont.fnt"));

    public InstructionsScreen(MyGame mainApplication) {

        super(mainApplication);
        createLabels();
        addButtons();
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

        String instructions = "The goal of this game is to escape the rooms while fighting enemies along the way." +
                "\n\n 1. To move, tap on the screen to move the player in the tapped direction." +
                "\n\n 2. While next to an enemy, you can attack it by tapping it " +
                "\n\n 3. Enemies wander around the level, pursuit the player and damage them if you get too close." +
                "\n\n 4. There are health pickups in the form of a heart throughout the level. Navigate to it and tap on it to regain your health." +
                "\n\n 5. There is a doorway in each level. Navigate to it and tap on it to progress to the next level." +
                "\n\n 6. Find the last door, and Escape from Icarus!";

        float titleHeight = MARGIN;
        float titleWidth = this.stage.getWidth() - MARGIN;

        float instructionHeight = this.stage.getHeight() - titleHeight - (MARGIN * 3); //height is extra small because of title label
        float instructionWidth = this.stage.getWidth() - (MARGIN * 2);

        //Initializing the style for the label, and setting the styles below
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        Label.LabelStyle instructionStyle = new Label.LabelStyle();

        titleStyle.font = FONT_STYLE;
        instructionStyle.font = FONT_STYLE;

        //Label that has the title of the screen "How To Play"
        Label titleLabel = new Label("How To Play", titleStyle);

        //Label that has the actual instructions inside
        Label instructionLabel = new Label(instructions,instructionStyle);

        //Place titleLabel at the top center
        titleLabel.setSize(titleWidth, titleHeight);
        titleLabel.setPosition(MARGIN,this.stage.getHeight() - titleHeight - MARGIN);
        titleLabel.setAlignment(Align.center);
        titleLabel.setFontScale(7f,7f);
        titleLabel.setWrap(false);

        //Placing the instructionlabel at the centre of the screen with a margin of 50 all around
        instructionLabel.setSize(instructionWidth, instructionHeight);
        instructionLabel.setPosition(MARGIN,titleLabel.getY() - instructionHeight);
        instructionLabel.setAlignment(Align.center);
        instructionLabel.setFontScale(3.2f,3.2f);
        instructionLabel.setWrap(true);

        stage.addActor(titleLabel);
        stage.addActor(instructionLabel);
    }

    //Adds the Buttons for the screen into the stage for rendering
    private void addButtons() {

        TextButton backButton = new TextButton("Back to Main Menu", BUTTON_SKIN);
        backButton.setWidth(BUTTON_WIDTH);
        backButton.setPosition((this.stage.getWidth() - backButton.getWidth() - MARGIN),MARGIN / 2);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundPlayer.setEffect(SoundEffect.BUTTON_FX);
                mainApplication.changeScreen(ScreenType.MENU_SCREEN);
                mainApplication.setMusicContinue(true);
            }
        });

        this.stage.addActor(backButton);
    }
}

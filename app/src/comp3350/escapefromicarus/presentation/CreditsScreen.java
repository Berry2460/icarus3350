package comp3350.escapefromicarus.presentation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class CreditsScreen extends Screen {

    private final float MARGIN = 50.0f;
    private final float BUTTON_WIDTH = 600.0f;
    private final Skin BUTTON_SKIN = new Skin(Gdx.files.internal("font/craftacular-ui.json"));
    private final BitmapFont FONT_STYLE = new BitmapFont(Gdx.files.internal("font/menuFont.fnt"));

    public CreditsScreen(MyGame mainApplication) {

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

        String developers =
                    "Ying Liang\n" +
                    "Agape Seo\n" +
                    "Tristan Dyck\n" +
                    "Ryan Campbell\n" +
                    "Tanisha Turner\n";

        String allAssets =
                "Mystic Woods by Game Endeavor\n" +
                "Humble Fonts by Eeve Somepx\n" +
                "Infinity Crystal by Sara Garrard\n" +
                "Let's Adventure by Sara Garrard\n" +
                "Craftacular UI by Raymond \"Raeleus\" Buckley\n" +
                "Lite UI Fantasy by Cathean\n" +
                "Pixel Art Fantasy Lands by Masayume\n" +
                "Misc HUD and UI Graphics by Zed Hanok\n" +
                "8-bit / 16-bit Sound Effects (x25) Pack by JDWasabi\n" +
                "50 Menu Interface SFX by ColorAlpha\n" +
                "Minifantasy - Dungeon SFX Pack by Krishna Palacio\n" +
                "Interface SFX Pack by ObsydianX\n" +
                "2D Pixel Art Shardsoul Slayer Sprites\nby Elthen's Pixel Art Shop";

        float titleHeight = this.stage.getHeight() / 10;
        float titleWidth = this.stage.getWidth() - (MARGIN * 2);
        float subtitleWidth = (this.stage.getWidth() / 2) - (MARGIN * 2);
        float creditsHeight = this.stage.getHeight() - (titleHeight + (MARGIN * 5));

        //Initializing the style for the label, and setting the styles below
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        Label.LabelStyle instructionStyle = new Label.LabelStyle();

        titleStyle.font = FONT_STYLE;
        instructionStyle.font = FONT_STYLE;

        //Labels
        Label titleLabel = new Label("Credits", titleStyle);
        Label developerLabel = new Label("Developers", titleStyle);
        Label assetsLabel = new Label("Assets", titleStyle);
        Label allDevelopers = new Label(developers,instructionStyle);
        Label allAssetsLabel = new Label(allAssets,instructionStyle);

        titleLabel.setAlignment(Align.center);
        titleLabel.setFontScale(7f,7f);
        titleLabel.setWrap(false);
        titleLabel.setSize(titleWidth, titleHeight);
        titleLabel.setPosition(MARGIN,this.stage.getHeight() - (titleHeight + MARGIN * 2));

        assetsLabel.setAlignment(Align.center);
        assetsLabel.setFontScale(5f,5f);
        assetsLabel.setWrap(false);
        assetsLabel.setSize(subtitleWidth, titleHeight);
        assetsLabel.setPosition(this.stage.getWidth() / 2 + MARGIN,titleLabel.getY() - assetsLabel.getHeight());

        developerLabel.setAlignment(Align.center);
        developerLabel.setFontScale(5f,5f);
        developerLabel.setWrap(false);
        developerLabel.setSize(subtitleWidth, titleHeight);
        developerLabel.setPosition(MARGIN,titleLabel.getY() - developerLabel.getHeight());

        allDevelopers.setAlignment(Align.top);
        allDevelopers.setFontScale(2.5f,2.5f);
        allDevelopers.setWrap(false);
        allDevelopers.setSize(subtitleWidth, creditsHeight);
        allDevelopers.setPosition(MARGIN, developerLabel.getY() - (allDevelopers.getHeight() + MARGIN / 2));

        allAssetsLabel.setAlignment(Align.top);
        allAssetsLabel.setFontScale(2.5f,2.5f);
        allAssetsLabel.setWrap(false);
        allAssetsLabel.setSize(subtitleWidth, creditsHeight);
        allAssetsLabel.setPosition((this.stage.getWidth() / 2) + MARGIN,assetsLabel.getY() - (allAssetsLabel.getHeight() + MARGIN / 2));

        //add to stage
        this.stage.addActor(titleLabel);
        this.stage.addActor(developerLabel);
        this.stage.addActor(assetsLabel);
        this.stage.addActor(allAssetsLabel);
        this.stage.addActor(allDevelopers);
    }

    //Adds the Buttons for the screen into the stage for rendering
    private void addButtons() {

        TextButton backButton = new TextButton("Back to Main Menu", BUTTON_SKIN);
        backButton.setWidth(BUTTON_WIDTH);
        backButton.setPosition((this.stage.getWidth() / 2 - backButton.getWidth() / 2),MARGIN / 2);

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

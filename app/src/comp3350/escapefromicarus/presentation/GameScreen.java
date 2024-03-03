package comp3350.escapefromicarus.presentation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import comp3350.escapefromicarus.business.GameLogic;
import comp3350.escapefromicarus.business.WorldLogic;
import comp3350.escapefromicarus.objects.Character;
import comp3350.escapefromicarus.objects.Enemy;
import comp3350.escapefromicarus.objects.Movement;
import comp3350.escapefromicarus.objects.World;
import comp3350.escapefromicarus.objects.Actor;
import comp3350.escapefromicarus.objects.Player;
import comp3350.escapefromicarus.objects.TextureType;
import comp3350.escapefromicarus.objects.Tile;

public class GameScreen extends Screen {

    private final float SETTINGS_BUTTON_SIZE = 100.0f;
    private final float BUTTON_WIDTH = 600.0f;
    private final float MARGIN = 50.0f;

    private final BitmapFont HUD_FONT_STYLE = new BitmapFont(Gdx.files.internal("font/blackfont.fnt"));
    private final BitmapFont FONT_STYLE = new BitmapFont(Gdx.files.internal("font/menuFont.fnt"));

    private final Player mainCharacter;

    private Skin buttonSkin;
    private Texture pauseBtnTexture;
    private Texture unmuteBtnTexture;
    private Texture muteBtnTexture;
    private Texture muteMusicTexture;
    private Texture unmuteMusicTexture;
    private Drawable pauseBtnDrawable;
    private Drawable muteBtnDrawable;
    private Drawable unmuteBtnDrawable;
    private Drawable muteMusicDrawable;
    private Drawable unmuteMusicDrawable;

    private Label currLevel;
    private Label healthLabel;
    private Image hudHealthBar;

    private InputAdapter gameInput;
    private InputMultiplexer multiInput;
    private World world;
    private Sprite toDraw;

    private Texture[] textures;

    private Camera camera;

    private boolean isPaused = false;
    private Group pauseGroup;
    private Group popUpGroup;

    public GameScreen(MyGame newMainApplication) {

        super(newMainApplication);

        //init sound
        SoundPlayer.initSounds(this.dataAccess);

        //init levels
        this.world = new World();

        //depending on what the last saved level was
        for(int i = this.dataAccess.getLevel(); i < this.world.MAX_LEVELS * this.mainApplication.getDifficulty(); i++) {

            WorldLogic.addLevel(this.world, this.dataAccess);
        }

        this.world.addPlayer(this.dataAccess);

        this.mainCharacter = this.world.getPlayer();

        //init textures
        this.textures = new Texture[] {
                new Texture(this.dataAccess.getTexture("grass0")),
                new Texture(this.dataAccess.getTexture("grass1")),
                new Texture(this.dataAccess.getTexture("grass2")),
                new Texture(this.dataAccess.getTexture("grass3")),
                new Texture(this.dataAccess.getTexture("grass4")),
                new Texture(this.dataAccess.getTexture("grass5")),
                new Texture(this.dataAccess.getTexture("grass6")),
                new Texture(this.dataAccess.getTexture("grass7")),
                new Texture(this.dataAccess.getTexture("grass8")),
                new Texture(this.dataAccess.getTexture("grass9")),
                new Texture(this.dataAccess.getTexture("grass10")),
                new Texture(this.dataAccess.getTexture("grass11")),
                new Texture(this.dataAccess.getTexture("grass12")),
                new Texture(this.dataAccess.getTexture("grass13")),
                new Texture(this.dataAccess.getTexture("grass14")),
                new Texture(this.dataAccess.getTexture("grass15")),
                new Texture(this.dataAccess.getTexture("grass16")),
                new Texture(this.dataAccess.getTexture("sideWallStone")),
                new Texture(this.dataAccess.getTexture("stone")),
                new Texture(this.dataAccess.getTexture("leftWallStone")),
                new Texture(this.dataAccess.getTexture("rightWallStone")),
                new Texture(this.dataAccess.getTexture("bottomLeftWallStone")),
                new Texture(this.dataAccess.getTexture("bottomRightWallStone")),
                new Texture(this.dataAccess.getTexture("combinedWallStone")),
                new Texture(this.dataAccess.getTexture("woodenDoor")),
                new Texture(this.dataAccess.getTexture("player")),
                new Texture(this.dataAccess.getTexture("slime")),
                new Texture(this.dataAccess.getTexture("skeleton")),
                new Texture(this.dataAccess.getTexture("lifeBar")),
                new Texture(this.dataAccess.getTexture("blocker0")),
                new Texture(this.dataAccess.getTexture("blocker1")),
                new Texture(this.dataAccess.getTexture("blocker2")),
                new Texture(this.dataAccess.getTexture("blocker3")),
                new Texture(this.dataAccess.getTexture("blocker4")),
                new Texture(this.dataAccess.getTexture("blocker5")),
                new Texture(this.dataAccess.getTexture("blocker6")),
                new Texture(this.dataAccess.getTexture("blocker7")),
                new Texture(this.dataAccess.getTexture("blocker8")),
                new Texture(this.dataAccess.getTexture("blocker9")),
                new Texture(this.dataAccess.getTexture("blocker10")),
                new Texture(this.dataAccess.getTexture("blocker11")),
                new Texture(this.dataAccess.getTexture("heart")),
                new Texture(this.dataAccess.getTexture("levelBoss"))
        };

        this.toDraw = new Sprite(this.textures[TextureType.GRASS.ordinal()]);

        //menu button init
        this.buttonSkin = new Skin(Gdx.files.internal("font/craftacular-ui.json"));
        this.pauseBtnTexture = new Texture(this.dataAccess.getTexture("pauseBtn"));
        this.pauseBtnDrawable = new TextureRegionDrawable(new TextureRegionDrawable(this.pauseBtnTexture));

        this.muteBtnTexture = new Texture(this.dataAccess.getTexture("Speaker1"));
        this.muteBtnDrawable = new TextureRegionDrawable(new TextureRegionDrawable(this.muteBtnTexture));

        this.unmuteBtnTexture = new Texture(this.dataAccess.getTexture("SpeakerMute"));
        this.unmuteBtnDrawable = new TextureRegionDrawable(new TextureRegionDrawable(this.unmuteBtnTexture));

        this.muteMusicTexture = new Texture(this.dataAccess.getTexture("music1"));
        this.muteMusicDrawable = new TextureRegionDrawable(new TextureRegionDrawable(this.muteMusicTexture));

        this.unmuteMusicTexture = new Texture(this.dataAccess.getTexture("musicMute"));
        this.unmuteMusicDrawable = new TextureRegionDrawable(new TextureRegionDrawable(this.unmuteMusicTexture));

        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.position.set(0, 0, 0);
        this.camera.update();

        addUI();
        addHudElements();
        addInputAdapter();
        makeMusic();

        if(this.dataAccess.saveExists() == 0) {
            //this is a new game
            storyPopUp();
        }
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(this.multiInput);
        this.music.setVolume(this.mainApplication.getMusicVol());
        this.mainApplication.setMusic(this.music);
    }

    @Override
    public void render(float delta) {

        updateCamera();
        this.batch.setProjectionMatrix(this.camera.combined);
        ScreenUtils.clear(0, 0, 0, 1); //black background
        this.batch.begin();
        drawLevel();
        this.batch.end();
        this.healthLabel.setText(this.mainCharacter.getLife() + " / " + this.mainCharacter.getMaxLife());
        this.hudHealthBar.setWidth(this.stage.getWidth() / 5 * ((float)this.mainCharacter.getLife() / this.mainCharacter.getMaxLife()));
        this.stage.draw();

        if(GameLogic.checkNextLevel(this.world)) {
            this.mainApplication.changeScreen(ScreenType.GAME_END_SCREEN);
        }
        this.currLevel.setText("Level: " + (this.dataAccess.getLevel() + this.world.getLevelID() + 1));
    }

    public void updateCamera() {

        // update camera
        this.camera.position.set(this.mainCharacter.getPixelX() + Tile.TILE_SIZE / 2.0f,
                this.mainCharacter.getPixelY() + Tile.TILE_SIZE / 2.0f, 0);
        this.camera.update();
    }

    public void drawLevel() {

        Tile[][] tilemap = this.world.getCurrentLevel().getTilemap();
        //culling params
        int startY = (int) ((this.camera.position.y - (Tile.TILE_SIZE + this.camera.viewportHeight) / 2.0f) / Tile.TILE_SIZE);
        int endY = (int) ((this.camera.position.y + (Tile.TILE_SIZE + this.camera.viewportHeight) / 2.0f) / Tile.TILE_SIZE);
        int startX = (int) ((this.camera.position.x - (Tile.TILE_SIZE + this.camera.viewportWidth) / 2.0f) / Tile.TILE_SIZE);
        int endX = (int) ((this.camera.position.x + (Tile.TILE_SIZE + this.camera.viewportWidth) / 2.0f) / Tile.TILE_SIZE);

        //culling bounds
        startY = Math.max(0, startY - 1);
        startX = Math.max(0, startX - 1);
        endY = Math.min(tilemap.length - 1, endY + 1);
        endX = Math.min(tilemap[0].length - 1, endX + 1);

        //for actor culling
        ArrayList<Actor> actorsToDraw = new ArrayList<>();
        //draw tilemap
        for (int i = endY - 1; i >= startY; i--) {
            for (int j = startX; j < endX; j++) {
                TextureType tileFloor = tilemap[i][j].getFloor();
                this.toDraw.setTexture(this.textures[tileFloor.ordinal()]);
                this.toDraw.setSize(Tile.TILE_SIZE, Tile.TILE_SIZE);
                this.toDraw.setRegion(0, 0, this.toDraw.getTexture().getWidth(), this.toDraw.getTexture().getHeight());
                this.toDraw.setPosition(j * Tile.TILE_SIZE, i * Tile.TILE_SIZE);
                this.toDraw.draw(this.batch);
                //if actor is standing on tile, add it to draw list
                Actor currActor = tilemap[i][j].getOccupyingActor();
                if (currActor != null) {
                    actorsToDraw.add(currActor);
                }
            }
        }
        drawSprites(actorsToDraw);
    }

    //draw sprites on top of tilemap (prevents artifact when moving)
    private void drawSprites(ArrayList<Actor> actorsToDraw) {

        //to draw routine for actors
        for (int i = 0; i < actorsToDraw.size(); i++) {
            Actor currActor = actorsToDraw.get(i);
            //draw sprite
            this.toDraw.setTexture(this.textures[currActor.getTextureType().ordinal()]);
            this.toDraw.setPosition(currActor.getPixelX(), currActor.getPixelY());
            this.toDraw.draw(this.batch);
        }

        //do character related things, death, hp bars
        for (int i = 0; i < actorsToDraw.size(); i++) {
            Actor currActor = actorsToDraw.get(i);

            if (currActor instanceof Movement) {
                Movement currMover = (Movement) currActor;
                if (this.isPaused) {
                    currMover.halt();
                }
                else {
                    currMover.glide(Gdx.graphics.getDeltaTime());
                    if (currActor instanceof Enemy) {
                        GameLogic.doEnemyLogic((Enemy) currActor, this.mainCharacter.getTileX(), this.mainCharacter.getTileY(), Gdx.graphics.getDeltaTime());
                    }
                    else if (currActor instanceof Player) {
                        // main character death check
                        GameLogic.playerDeathCheck(this.mainApplication, (Player) currActor);
                    }
                }

                //character things
                if (currActor instanceof Character) {
                    Character currCharacter = (Character) currActor;
                    if (!currCharacter.isDead()) {
                        //draw lifebar
                        this.toDraw.setTexture(this.textures[TextureType.LIFEBAR.ordinal()]);
                        float lifePercent = (float) currCharacter.getLife() / currCharacter.getMaxLife();
                        this.toDraw.setSize(Tile.TILE_SIZE * lifePercent, this.textures[TextureType.LIFEBAR.ordinal()].getHeight());
                        this.toDraw.setRegion(0, 0, (int) (this.textures[TextureType.LIFEBAR.ordinal()].getWidth() * lifePercent), this.textures[TextureType.LIFEBAR.ordinal()].getHeight());
                        this.toDraw.setPosition(currActor.getPixelX(), currActor.getPixelY() + Tile.TILE_SIZE + this.textures[TextureType.LIFEBAR.ordinal()].getHeight());
                        this.toDraw.draw(this.batch);
                    }
                    else {
                        SoundPlayer.setEffect(SoundEffect.DEAD_FX);
                        currCharacter.kill();
                    }
                }
            }
        }
    }

    @Override
    public void dispose() {

        this.buttonSkin.dispose();
        this.pauseBtnTexture.dispose();
        this.background.dispose();
        this.batch.dispose();
        this.stage.dispose();
    }

    private void addUI() {

        Label.LabelStyle levelStyle = new Label.LabelStyle();
        levelStyle.font = FONT_STYLE;
        this.currLevel = new Label("Level: " + (this.world.getLevelID() + 1), levelStyle);

        Button settingsButton = new Button(this.pauseBtnDrawable);

        this.currLevel.setSize(BUTTON_WIDTH, MARGIN);
        this.currLevel.setAlignment(Align.center);
        this.currLevel.setFontScale(4f,4f);
        this.currLevel.setWrap(false);
        this.currLevel.setPosition(this.stage.getWidth() / 2 - this.currLevel.getWidth() / 2, this.stage.getHeight() - this.currLevel.getHeight());

        settingsButton.setWidth(SETTINGS_BUTTON_SIZE);
        settingsButton.setHeight(SETTINGS_BUTTON_SIZE);
        settingsButton.setPosition((this.stage.getWidth() - settingsButton.getWidth()),this.stage.getHeight() - settingsButton.getHeight());

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!isPaused) {
                    //Player wants to pause the game, otherwise game is already paused
                    SoundPlayer.setEffect(SoundEffect.BUTTON_FX);
                    pauseGame();
                }
            }
        });

        this.stage.addActor(this.currLevel);
        this.stage.addActor(settingsButton);
    }

    private void pauseGame() {

        this.isPaused = true;
        this.pauseGroup = new Group();
        addPauseScreenButtons();
        this.stage.addActor(this.pauseGroup);
    }

    private void addPauseScreenButtons() {

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = FONT_STYLE;
        Label pausedLabel = new Label("Game Paused", titleStyle);
        Label muteAllLabel = new Label("Mute all sounds", titleStyle);
        Label muteMusicLabel = new Label("Mute music", titleStyle);

        Image transparentBg = new Image(new Texture(this.dataAccess.getTexture("transparentBg")));
        Image panel = new Image(new Texture(this.dataAccess.getTexture("copperhud")));

        //music button and its styles to set to
        Button muteButton = new Button(this.muteBtnDrawable);
        Button.ButtonStyle muteStyle = new Button.ButtonStyle(this.muteBtnDrawable, null, null);
        Button.ButtonStyle unmuteStyle = new Button.ButtonStyle(this.unmuteBtnDrawable, null, null);

        Button muteMusicButton = new Button(this.muteMusicDrawable);
        Button.ButtonStyle muteMusicStyle = new Button.ButtonStyle(this.muteMusicDrawable, null, null);
        Button.ButtonStyle unmuteMusicStyle = new Button.ButtonStyle(this.unmuteMusicDrawable, null, null);

        TextButton resumeButton = new TextButton("Resume", this.buttonSkin);
        TextButton mainMenuButton = new TextButton("Main Menu", this.buttonSkin);

        //set parameters
        transparentBg.setSize(this.stage.getWidth(), this.stage.getHeight());
        panel.setSize(this.stage.getWidth() / 2, this.stage.getHeight());
        panel.setPosition(this.stage.getWidth() / 2 - panel.getWidth() / 2, this.stage.getHeight() / 2 - panel.getHeight() / 2);

        resumeButton.setWidth(BUTTON_WIDTH);
        resumeButton.setPosition(this.stage.getWidth() / 2 - resumeButton.getWidth() / 2,this.stage.getHeight() / 2 - resumeButton.getHeight() / 2);

        muteAllLabel.setSize(SETTINGS_BUTTON_SIZE, MARGIN);
        muteAllLabel.setAlignment(Align.center);
        muteAllLabel.setFontScale(2f,2f);
        muteAllLabel.setWrap(false);
        muteAllLabel.setPosition(this.stage.getWidth() / 2 - (muteAllLabel.getWidth() + MARGIN), resumeButton.getY() + (resumeButton.getHeight() + MARGIN));

        muteMusicLabel.setSize(SETTINGS_BUTTON_SIZE, MARGIN);
        muteMusicLabel.setAlignment(Align.center);
        muteMusicLabel.setFontScale(2f,2f);
        muteMusicLabel.setWrap(false);
        muteMusicLabel.setPosition(this.stage.getWidth() / 2 + MARGIN, resumeButton.getY() + (resumeButton.getHeight() + MARGIN));

        muteButton.setWidth(SETTINGS_BUTTON_SIZE);
        muteButton.setHeight(SETTINGS_BUTTON_SIZE);
        muteButton.setPosition(this.stage.getWidth() / 2 - (muteButton.getWidth() + MARGIN),muteAllLabel.getY() + muteAllLabel.getHeight());

        muteMusicButton.setWidth(SETTINGS_BUTTON_SIZE);
        muteMusicButton.setHeight(SETTINGS_BUTTON_SIZE);
        muteMusicButton.setPosition(this.stage.getWidth() / 2 + MARGIN,muteMusicLabel.getY() + muteMusicLabel.getHeight());

        pausedLabel.setAlignment(Align.center);
        pausedLabel.setFontScale(7f,7f);
        pausedLabel.setWrap(false);
        pausedLabel.setPosition(this.stage.getWidth() / 2 - pausedLabel.getWidth() / 2, muteButton.getY() + (muteButton.getHeight() + MARGIN));

        mainMenuButton.setWidth(BUTTON_WIDTH);
        mainMenuButton.setPosition(this.stage.getWidth() / 2 - mainMenuButton.getWidth() / 2,resumeButton.getY() - (resumeButton.getHeight() + MARGIN));

        muteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                SoundPlayer.setEffect(SoundEffect.BUTTON_FX);
                if(mainApplication.getMuted()) {
                    mainApplication.unmuteGame();
                    muteButton.setStyle(muteStyle);
                    if(!mainApplication.getMusicMuted()) {
                        muteMusicButton.setStyle(muteMusicStyle);
                    }
                }
                else {
                    mainApplication.muteGame();
                    muteButton.setStyle(unmuteStyle);
                    muteMusicButton.setStyle(unmuteMusicStyle);
                }
            }
        });

        muteMusicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(!mainApplication.getMuted()) {
                    SoundPlayer.setEffect(SoundEffect.BUTTON_FX);
                    //gameSound is not muted, we can freely set music
                    if(mainApplication.getMusicMuted()) {
                        //music is muted
                        mainApplication.unmuteMusic();
                        muteMusicButton.setStyle(muteMusicStyle);
                    }
                    else {
                        mainApplication.muteMusic();
                        muteMusicButton.setStyle(unmuteMusicStyle);
                    }
                }
            }
        });

        if(this.mainApplication.getMuted()) {
            //game sound was muted
            muteButton.setStyle(unmuteStyle);
            muteMusicButton.setStyle(unmuteMusicStyle);
        }
        else {
            //game sound was not muted
            muteButton.setStyle(muteStyle);
            if(this.mainApplication.getMusicMuted()) {
                //music was muted
                muteMusicButton.setStyle(unmuteMusicStyle);
            }
            else {
                //music was not muted
                muteMusicButton.setStyle(muteMusicStyle);
            }
        }
        
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundPlayer.setEffect(SoundEffect.BUTTON_FX);
                updateDB();
                mainApplication.changeScreen(ScreenType.MENU_SCREEN);
            }
        });

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundPlayer.setEffect(SoundEffect.BUTTON_FX);
                resumeGame();
            }
        });

        this.pauseGroup.addActor(transparentBg);
        this.pauseGroup.addActor(panel);
        this.pauseGroup.addActor(pausedLabel);
        this.pauseGroup.addActor(mainMenuButton);
        this.pauseGroup.addActor(resumeButton);
        this.pauseGroup.addActor(muteAllLabel);
        this.pauseGroup.addActor(muteMusicLabel);
        this.pauseGroup.addActor(muteButton);
        this.pauseGroup.addActor(muteMusicButton);
    }

    private void resumeGame() {

        if(this.isPaused) {
            this.pauseGroup.remove();
            this.isPaused = false;
        }
    }

    public void makeMusic() {

        AssetManager manager = this.mainApplication.getMusicManager();
        this.music = manager.get("Music/levelMusic.ogg", Music.class);
        this.music.setLooping(true);
        this.music.setVolume(this.mainApplication.getMusicVol());
    }

    private void addInputAdapter() {

        this.gameInput = new InputAdapter() {
            @Override
            public boolean touchDown (int x, int y, int pointer, int button) {
                // get location
                boolean touching = false;
                if(!isPaused) {
                    // adjust camera position and tilemap
                    int xPos = (int) (x + camera.position.x - (camera.viewportWidth / 2.0f))
                            / Tile.TILE_SIZE;
                    int yPos = (int) ((Gdx.graphics.getHeight() - y)
                            + camera.position.y - (camera.viewportHeight / 2.0f)) / Tile.TILE_SIZE;
                    mainCharacter.setDestination(xPos, yPos);
                    touching = true;
                }
                return touching;
            }
        };

        this.multiInput = new InputMultiplexer(this.stage, this.gameInput);
    }

    private void updateDB() {

        this.dataAccess.setLevel(this.dataAccess.getLevel() + this.world.getLevelID());
        this.dataAccess.setDifficulty(this.mainApplication.getDifficulty());
        this.dataAccess.setMaxLevel(this.world.MAX_LEVELS * this.mainApplication.getDifficulty());
    }

    private void storyPopUp() {

        this.isPaused = true;
        this.popUpGroup = new Group();
        addStoryPopUpElements();
        this.stage.addActor(this.popUpGroup);
    }

    private void addStoryPopUpElements() {

        String storyLine = "You woke up to find yourself in a peculiar place full of various creatures. " +
                "You notice a sign that says\n\nIcarus\nMay only those who escape survive..." +
                "\n\nYou remember you were gathering a few things from the dangerous forest outside the " +
                "town and a misstep caused you to roll down a hill in the forest." +
                "You resolve to escape from this strange place called Icarus and make it back to the town...";

        Image blackBg = new Image(new Texture(this.dataAccess.getTexture("blackBg")));
        Image storyImage = new Image(new Texture(this.dataAccess.getTexture("forestBg")));

        TextButton playButton = new TextButton("Play", this.buttonSkin);

        float storyImageHeight = this.stage.getHeight() / 4;
        float storyImageWidth = this.stage.getWidth() - (MARGIN * 2);
        float storyHeight = this.stage.getHeight() - storyImageHeight - (MARGIN * 4);
        float storyWidth = this.stage.getWidth() - (MARGIN * 2);

        Label.LabelStyle storyStyle = new Label.LabelStyle();
        storyStyle.font = FONT_STYLE;
        Label storyLabel = new Label(storyLine, storyStyle);

        //set parameters
        blackBg.setSize(this.stage.getWidth(), this.stage.getHeight());
        storyImage.setSize(storyImageWidth, storyImageHeight);
        storyImage.setPosition(MARGIN, this.stage.getHeight() - (MARGIN + storyImage.getHeight()));

        storyLabel.setSize(storyWidth, storyHeight);
        storyLabel.setPosition(MARGIN,storyImage.getY() - storyLabel.getHeight());
        storyLabel.setAlignment(Align.center);
        storyLabel.setFontScale(3.75f,3.75f);
        storyLabel.setWrap(true);

        playButton.setWidth(BUTTON_WIDTH / 2);
        playButton.setPosition(this.stage.getWidth() - playButton.getWidth() - MARGIN, MARGIN);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundPlayer.setEffect(SoundEffect.BUTTON_FX);
                playGame();
            }
        });

        this.popUpGroup.addActor(blackBg);
        this.popUpGroup.addActor(storyImage);
        this.popUpGroup.addActor(storyLabel);
        this.popUpGroup.addActor(playButton);
    }

    private void playGame() {

        if(this.isPaused) {
            this.popUpGroup.remove();
            this.isPaused = false;
        }
    }

    private void addHudElements() {

        Label.LabelStyle storyStyle = new Label.LabelStyle();
        storyStyle.font = HUD_FONT_STYLE;
        this.healthLabel = new Label(this.mainCharacter.getLife() + " / " + this.mainCharacter.getMaxLife(), storyStyle);

        Image hudPanel = new Image(new Texture(this.dataAccess.getTexture("hudPanel")));
        Image playerFrame = new Image(new Texture(this.dataAccess.getTexture("playerFrame")));
        Image playerChar = new Image (this.textures[TextureType.PLAYER.ordinal()]);
        Image hudHealthBarBg = new Image(new Texture(this.dataAccess.getTexture("blackBg")));
        this.hudHealthBar = new Image(this.textures[TextureType.LIFEBAR.ordinal()]);

        float lifePercent = (float) mainCharacter.getLife() / mainCharacter.getMaxLife();
        this.toDraw.setSize(Tile.TILE_SIZE * lifePercent, this.textures[TextureType.LIFEBAR.ordinal()].getHeight());

        //set parameters
        playerFrame.setSize(SETTINGS_BUTTON_SIZE, SETTINGS_BUTTON_SIZE);
        playerFrame.setPosition(0, 0);

        playerChar.setSize(SETTINGS_BUTTON_SIZE, SETTINGS_BUTTON_SIZE);
        playerChar.setPosition(0, 0);

        hudHealthBarBg.setSize(this.stage.getWidth() / 5, SETTINGS_BUTTON_SIZE / 2);
        hudHealthBarBg.setPosition(playerFrame.getWidth(), SETTINGS_BUTTON_SIZE / 2);

        this.hudHealthBar.setSize((this.stage.getWidth() / 5) * lifePercent, SETTINGS_BUTTON_SIZE / 2);
        this.hudHealthBar.setPosition(playerFrame.getWidth(), SETTINGS_BUTTON_SIZE / 2);

        this.healthLabel.setSize(this.stage.getWidth() / 5, SETTINGS_BUTTON_SIZE / 2);
        this.healthLabel.setPosition(playerFrame.getWidth(), hudHealthBarBg.getY() - this.healthLabel.getHeight());
        this.healthLabel.setAlignment(Align.center);
        this.healthLabel.setFontScale(3.75f,3.75f);
        this.healthLabel.setWrap(false);

        hudPanel.setSize(playerFrame.getWidth() + hudHealthBarBg.getWidth(), playerFrame.getHeight());
        hudPanel.setPosition(0, 0);

        this.stage.addActor(hudPanel);
        this.stage.addActor(playerFrame);
        this.stage.addActor(playerChar);
        this.stage.addActor(hudHealthBarBg);
        this.stage.addActor(this.hudHealthBar);
        this.stage.addActor(this.healthLabel);
    }
}
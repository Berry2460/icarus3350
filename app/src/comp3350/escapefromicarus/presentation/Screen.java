package comp3350.escapefromicarus.presentation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import comp3350.escapefromicarus.application.Main;
import comp3350.escapefromicarus.application.Services;
import comp3350.escapefromicarus.persistence.DataAccess;

public class Screen extends ScreenAdapter {

    protected DataAccess dataAccess;
    protected Texture background;
    protected Stage stage;
    protected SpriteBatch batch;
    protected ScreenSetter setting;
    protected MyGame mainApplication;
    protected Music music;

    public Screen(MyGame mainApplication) {

        super();
        this.dataAccess = Services.getDataAccess(Main.dbName);
        this.background = new Texture(this.dataAccess.getTexture("splashscreen"));
        this.setting = new ScreenSetter();
        this.batch = new SpriteBatch();
        this.stage = new Stage();
        this.mainApplication = mainApplication;
        makeMusic();
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(this.stage);
        this.music.setVolume(this.mainApplication.getMusicVol());
    }

    @Override
    public void render(float delta) {

        this.setting.doBasicRender(delta, this.stage, this.batch, this.background);
    }

    @Override
    public void dispose() {

        this.music.dispose();
        this.background.dispose();
        this.stage.dispose();
        this.batch.dispose();
    }

    public void makeMusic() {

        AssetManager manager = this.mainApplication.getMusicManager();
        this.music = manager.get("Music/mainMenuMusic.ogg", Music.class);
        this.music.setLooping(true);
        this.music.setVolume(this.mainApplication.getMusicVol());
    }
}

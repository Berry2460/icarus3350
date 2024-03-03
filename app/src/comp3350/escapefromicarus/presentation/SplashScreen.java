package comp3350.escapefromicarus.presentation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class SplashScreen extends Screen {

    private float screenShowtime = 2.0f; // 0 seconds

    public SplashScreen(MyGame newMainApplication) {
        
        super(newMainApplication);
        this.background = new Texture(this.dataAccess.getTexture("splashscreen"));
    }

    @Override
    public void render(float delta) {
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.batch.begin();
        this.batch.draw(this.background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.batch.end();

        this.screenShowtime -= delta; // remove delta from time
        if (this.screenShowtime <= 0) {
            // 2 seconds pass, tell parent to change screen
            this.mainApplication.changeScreen(ScreenType.MENU_SCREEN);
        }
    }
}

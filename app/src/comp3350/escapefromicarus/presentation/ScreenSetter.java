package comp3350.escapefromicarus.presentation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ScreenSetter {

    public ScreenSetter() {}

    //Does a basic rendering of the screen by doing batch draw and stage draw
    //Stage and batch changes for each screen
    public void doBasicRender(float delta, Stage stage, SpriteBatch batch, Texture backgroundImage) {

        if(stage != null && batch != null) {
            stage.act(delta);
            Gdx.gl.glClearColor(0, 0, 0, 1); // clear with black screen
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            if(backgroundImage != null) {
                batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            }
            batch.end();
            stage.draw();
        }
    }
}

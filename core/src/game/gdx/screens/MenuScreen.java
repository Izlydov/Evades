package game.gdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import game.gdx.GameSettings;
import game.gdx.MyGdxGame;

public class MenuScreen implements Screen {
    private final MyGdxGame myGdxGame;
    SpriteBatch batch;
    GameScreen gameScreen;
    private Stage stage;
    private Skin skin;
    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        batch = new SpriteBatch();
        gameScreen = new GameScreen(myGdxGame);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load skin (make sure you have a skin.json file in your assets folder)
        skin = new Skin(Gdx.files.internal("uiskin.json")); // Replace with your skin file

        TextButton startButton = new TextButton("Start Game", skin);
        TextButton quitButton = new TextButton("Quit", skin);

        startButton.setSize(200, 50);
        startButton.setPosition(Gdx.graphics.getWidth() / 2f - startButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 2f - startButton.getHeight() / 2);
        quitButton.setSize(200, 50);
        quitButton.setPosition(Gdx.graphics.getWidth() / 2f - startButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 2f - startButton.getHeight() / 2 - 70);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                myGdxGame.setScreen(gameScreen);
            }
        });
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(startButton);
        stage.addActor(quitButton);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1); // Black background color (can be changed)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the stage (if you have a stage for UI elements like buttons)
        stage.act(delta);

        // Draw the stage
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

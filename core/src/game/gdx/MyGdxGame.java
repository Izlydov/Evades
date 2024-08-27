package game.gdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;


import game.gdx.screens.GameScreen;

public class MyGdxGame extends Game {
	private ShapeRenderer shapeRenderer;
	public OrthographicCamera camera;
	SpriteBatch batch;
	GameScreen gameScreen;



	@Override
	public void create () {
		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);

		gameScreen = new GameScreen(this);
		shapeRenderer = new ShapeRenderer();
		setScreen(gameScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	@Override
	public void pause() {
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}

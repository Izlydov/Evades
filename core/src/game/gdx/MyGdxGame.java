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
import game.gdx.screens.MenuScreen;

public class MyGdxGame extends Game {
	private ShapeRenderer shapeRenderer;
	public OrthographicCamera camera;
	SpriteBatch batch;
	GameScreen gameScreen;
	MenuScreen menuScreen;



	@Override
	public void create () {
		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);

		gameScreen = new GameScreen(this);
		menuScreen = new MenuScreen(this);
		shapeRenderer = new ShapeRenderer();
		setScreen(menuScreen);
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

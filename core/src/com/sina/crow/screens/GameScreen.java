package com.sina.crow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.sina.crow.gameworld.GameRenderer;
import com.sina.crow.gameworld.GameWorld;
import com.sina.crow.helpers.InputHandler;

/**
 * Created by Sina on 2016-05-12.
 */
public class GameScreen implements Screen {

	private GameWorld world;
	private GameRenderer renderer;
	private float runTime;
	private com.sina.crow.AdHandler handler;

	// This is the constructor, not the class declaration
	public GameScreen(com.sina.crow.AdHandler handler) {
		this.handler = handler;

		float screenWidth = Gdx.graphics.getWidth();	//272
		float screenHeight = Gdx.graphics.getHeight();	//408
		float gameWidth = 136;
		float gameHeight = screenHeight / (screenWidth / gameWidth); // simply 408/2

		int midPointY = (int) (gameHeight / 2);

		world = new GameWorld(midPointY);


		Gdx.input.setInputProcessor(new InputHandler(world, screenWidth/gameWidth, screenHeight/gameHeight)); // (world, 2, 2)
		renderer = new GameRenderer(world, (int) gameHeight, midPointY, handler);

	}

	@Override
	public void render(float delta) {
		runTime += delta;
		world.update(delta);
		renderer.render(delta, runTime);
	}

	@Override
	public void resize(int width, int height) {
		System.out.println("GameScreen - resizing");
	}

	@Override
	public void show() {
		System.out.println("GameScreen - show called");
	}

	@Override
	public void hide() {
		System.out.println("GameScreen - hide called");
	}

	@Override
	public void pause() {
		System.out.println("GameScreen - pause called");
	}

	@Override
	public void resume() {
		System.out.println("GameScreen - resume called");
	}

	@Override
	public void dispose() {
		// Leave blank
	}

}

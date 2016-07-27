package com.sina.crow.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.sina.crow.AdHandler;
import com.sina.crow.TweenAccessors.Value;
import com.sina.crow.TweenAccessors.ValueAccessor;
import com.sina.crow.gameobjects.Bird;
import com.sina.crow.gameobjects.Ground;
import com.sina.crow.gameobjects.Rock;
import com.sina.crow.gameobjects.ScrollHandler;
import com.sina.crow.gameobjects.SimpleButton;
import com.sina.crow.helpers.AssetLoader;
import com.sina.crow.helpers.InputHandler;

import java.util.ArrayList;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Sina on 2016-05-12.
 */
public class GameRenderer {

	private GameWorld myWorld;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;

	private SpriteBatch batcher;

	private int midPointY;
	private int gameHeight;

    private Bird bird;
    private ScrollHandler scroller;
    private Ground ground1, ground2;
    private Rock rock1, rock2, rock3;

    private TextureRegion bg, ground;
    private TextureRegion rock;
    private TextureRegion b1, b2, b3, b4, b5;  // these are the bird animations

    private Animation birdAnimation;

    private TweenManager manager;
    private Value alpha = new Value();

    private ArrayList<SimpleButton> menuButtons;

    private AdHandler handler;

    public GameRenderer(GameWorld world, int gameHeight, int midPointY, com.sina.crow.AdHandler handler) {
		myWorld = world;
        this.handler = handler;

		// The word "this" refers to this instance.
		// We are setting the instance variables' values to be that of the
		// parameters passed in from GameScreen.
		this.gameHeight = gameHeight;
		this.midPointY = midPointY;
        this.menuButtons = ((InputHandler)Gdx.input.getInputProcessor()).getMenuButtons();

		cam = new OrthographicCamera();
		cam.setToOrtho(true, 136, gameHeight);

		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);

        initGameObjects();
        initAssets();
        setupTweens();

	}


    private void initGameObjects() {
        bird = myWorld.getBird();
        scroller = myWorld.getScroller();
        ground1 = scroller.getGround1();
        ground2 = scroller.getGround2();
        rock1 = scroller.getRock1();
        rock2 = scroller.getRock2();
        rock3 = scroller.getRock3();
    }

    private void initAssets() {
        bg = AssetLoader.bg;
        ground = AssetLoader.ground;
        birdAnimation = AssetLoader.birdAnimation;
        b1 = AssetLoader.b1;
        b2 = AssetLoader.b2;
        b3 = AssetLoader.b3;
        b4 = AssetLoader.b4;
        b5 = AssetLoader.b5;
        rock = AssetLoader.rock;
    }

    private void drawGround() {
        // Draw the grass
        batcher.draw(ground, ground1.getX(), ground1.getY(),
                ground1.getWidth(), ground1.getHeight());
        batcher.draw(ground, ground2.getX(), ground2.getY(),
                ground2.getWidth(), ground2.getHeight());
    }

    private void drawRocks() {
        batcher.draw(rock, rock1.getX(), rock1.getY(),
                rock1.getWidth(), rock1.getHeight());

        batcher.draw(rock, rock2.getX(), rock2.getY(),
                rock2.getWidth(), rock2.getHeight());

        batcher.draw(rock, rock3.getX(), rock3.getY(),
                rock3.getWidth(), rock3.getHeight());
    }

    private void drawMenuUI() {
        batcher.draw(AssetLoader.crowLogo, 136/2 - AssetLoader.crowLogo.getRegionWidth()/4, midPointY - 50,
                AssetLoader.crowLogo.getRegionWidth()/2, AssetLoader.crowLogo.getRegionHeight()/2);

        for (SimpleButton button : menuButtons) {
            button.draw(batcher);
        }


    }

    private void drawBird(float runTime) {
        // Draw bird at its coordinates. Retrieve the Animation object from AssetLoader
        // Pass in the runTime variable to get the current frame.

        batcher.draw(birdAnimation.getKeyFrame(runTime),
                bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight());
    }

    private void drawCoin(int temp) {
        if (myWorld.getScore() >= 10 && myWorld.getScore() < 20) {
            batcher.draw(AssetLoader.bronze, temp + 80, midPointY - 15);
        } else if (myWorld.getScore() >= 20 && myWorld.getScore() < 30) {
            batcher.draw(AssetLoader.silver, temp + 80, midPointY - 15);
        } else if (myWorld.getScore() >= 30) {
            batcher.draw(AssetLoader.gold, temp + 80, midPointY - 15);
        }
    }

	public void render(float delta, float runTime) {

		// Fill the entire screen with black, to prevent potential flickering.
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Begin ShapeRenderer
		shapeRenderer.begin(ShapeType.Filled);
		
		// Draw Background color
		shapeRenderer.setColor(43 / 255.0f, 66 / 255.0f, 93 / 255.0f, 1);
		shapeRenderer.rect(0, 0, 136, midPointY + 66);
		
		// End ShapeRenderer
		shapeRenderer.end();

		// Begin SpriteBatch
		batcher.begin();

		// Disable transparency (good for performance when not using)
		batcher.disableBlending();
		batcher.draw(bg, 0, midPointY + 23, 136, 43);

        drawGround();
        drawRocks();

		// The bird needs transparency, so we enable that again.
		batcher.enableBlending();
		AssetLoader.music.play();

		if (myWorld.isRunning()) {
            drawBird(runTime);
            AssetLoader.font.getData().setScale(.4f, -.4f);
            AssetLoader.font.draw(batcher, "" + myWorld.getScore(), 136 - 16, 5);
            handler.showAds(false);
        } else if (myWorld.isReady()) {
            drawBird(runTime);
            handler.showAds(false);
            batcher.draw(AssetLoader.tap, 136/2 - AssetLoader.tap.getRegionWidth(), midPointY - 50, 68, 34);
        } else if (myWorld.isMenu()) {
            drawBird(runTime);
            drawMenuUI();
            handler.showAds(true);
        } else if (myWorld.isGameOver()) {
            drawBird(runTime);
            AssetLoader.font.getData().setScale(0.2f, -0.2f);

            batcher.draw(com.sina.crow.helpers.AssetLoader.score, 136/2 - com.sina.crow.helpers.AssetLoader.score.getRegionWidth()/2, midPointY - 30, 116, 51);
            int temp = 136/2 - com.sina.crow.helpers.AssetLoader.score.getRegionWidth()/2;
            AssetLoader.font.draw(batcher, "Score:   " + myWorld.getScore(), temp + 10, midPointY - 20);
            AssetLoader.font.draw(batcher, "Best:   " + com.sina.crow.helpers.AssetLoader.getHighScore(), temp + 10, midPointY);

            if (myWorld.bool) {
                AssetLoader.font.setColor(Color.GOLD);
                AssetLoader.font.draw(batcher, "Best:   " + AssetLoader.getHighScore(), temp + 10, midPointY);
                AssetLoader.font.setColor(Color.WHITE);
            }

            drawCoin(temp);
            handler.showAds(true);
        }



		// End SpriteBatch
		batcher.end();
        drawTransition(delta);

	}

    private void setupTweens() {
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, .5f).target(0).ease(TweenEquations.easeOutQuad).start(manager);
    }


    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, alpha.getValue());
            shapeRenderer.rect(0, 0, 136, 300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

        }
    }




}

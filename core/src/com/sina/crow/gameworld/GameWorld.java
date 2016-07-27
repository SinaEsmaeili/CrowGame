package com.sina.crow.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.sina.crow.gameobjects.Bird;
import com.sina.crow.gameobjects.ScrollHandler;
import com.sina.crow.helpers.AssetLoader;

/**
 * Created by Sina on 2016-05-12.
 */
public class GameWorld {

	private Bird bird;
	private ScrollHandler scroller;
	private Rectangle ground;
	private int score = 0;
    private int midPointY;
    private float runTime = 0;

    public boolean bool = false;

    private GameState currentState;

    public enum GameState {

        MENU, READY, RUNNING, GAMEOVER

    }

	public GameWorld(int midPointY) {
        currentState = GameState.MENU;
        this.midPointY = midPointY;
		bird = new Bird(33, midPointY - 5, 30, 30);
        scroller = new ScrollHandler(this, midPointY + 66);
        ground = new Rectangle(0, midPointY + 68, 168, 26);
	}

    public void update(float delta) {
    runTime += delta;

        switch (currentState) {
            case READY:
            case MENU:
                updateReady(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;
        }
    }

	public void updateRunning(float delta) {
		if (delta > .15f) {
			delta = .15f;
		}

		bird.update(delta);
        scroller.update(delta);

        if (scroller.collides(bird) && bird.isAlive()) {
            scroller.stop();
			bird.die();
        }

		if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
            scroller.stop();
            bird.die();
            bird.decelerate();
            currentState = GameState.GAMEOVER;

            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                bool = true;
            }
        }
	}

    private void updateReady(float delta) {
        bird.updateReady(runTime);
        scroller.updateReady(delta);
    }


	public Bird getBird() {
		return bird;
	}

    public ScrollHandler getScroller() {
        return scroller;
    }

    public int getScore() {
        return score;
    }

    public void addScore() {
        score += 1;
    }

    public int getMidPointY() {
        return midPointY;
    }



    public void start() {
        currentState = GameState.RUNNING;
    }

    public void ready() {
        currentState = GameState.READY;
    }

    public void restart() {
        currentState = GameState.READY;
        score = 0;
        bird.onRestart(midPointY - 5);
        scroller.onRestart();
        currentState = GameState.READY;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }





}
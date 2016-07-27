package com.sina.crow.gameobjects;

import java.util.Random;

/**
 * Created by Sina on 2016-05-12.
 */
public class ScrollHandler {
    // ScrollHandler will create all five objects that we need.
    private Ground ground1, ground2;
    private Rock rock1, rock2, rock3;
    private Random rand;
    private com.sina.crow.gameworld.GameWorld gameWorld;

    public static final int SCROLL_SPEED = -160;
    public static final int PIPE_GAP = 49;

    // Constructor receives a float that tells us where we need to create our
    // Grass and Pipe objects.
    public ScrollHandler(com.sina.crow.gameworld.GameWorld gameWorld, float yPos) {
        this.gameWorld = gameWorld;
        ground1 = new Ground(0 , yPos, 168, 56, SCROLL_SPEED);
        ground2 = new Ground(ground1.getTailX(), yPos, 168, 56, SCROLL_SPEED);

        rock1 = new Rock(210, 0, 22, 22, SCROLL_SPEED);
        rock2 = new Rock(rock1.getTailX() + PIPE_GAP, 32, 22, 22, SCROLL_SPEED);
        rock3 = new Rock(rock2.getTailX() + PIPE_GAP, 89, 22, 22, SCROLL_SPEED);

    }

    public void update(float delta) {
        ground1.update(delta);
        ground2.update(delta);
        rock1.update(delta);
        rock2.update(delta);
        rock3.update(delta);

        if (ground1.isScrolledLeft()) {
            ground1.reset(ground2.getTailX());
        } else if (ground2.isScrolledLeft()) {
            ground2.reset(ground1.getTailX());
        }

        if (rock1.isScrolledLeft()) {
            rock1.reset(rock3.getTailX() + PIPE_GAP);
        } else if (rock2.isScrolledLeft()) {
            rock2.reset(rock1.getTailX() + PIPE_GAP);

        } else if (rock3.isScrolledLeft()) {
           rock3.reset(rock2.getTailX() + PIPE_GAP);
        }
    }

    public void updateReady(float delta) {
        ground1.update(delta);
        ground2.update(delta);

        if (ground1.isScrolledLeft()) {
            ground1.reset(ground2.getTailX());
        } else if (ground2.isScrolledLeft()) {
            ground2.reset(ground1.getTailX());
        }
    }

    public void stop() {
        ground1.stop();
        ground2.stop();
        rock1.stop();
        rock2.stop();
        rock3.stop();
    }

    public boolean collides(Bird bird) {


        if (!rock1.isScored() && rock1.getX() + rock1.getWidth() < bird.getX() + bird.getWidth()) {
            gameWorld.addScore();
            rock1.setScored(true);
        } else if (!rock2.isScored() && rock2.getX() + rock2.getWidth() < bird.getX() + bird.getWidth()) {
            gameWorld.addScore();
            rock2.setScored(true);
        } else if (!rock3.isScored() && rock3.getX() + rock3.getWidth() < bird.getX() + bird.getWidth()) {
            gameWorld.addScore();
            rock3.setScored(true);
        }

        return (rock1.collides(bird) || rock2.collides(bird) || rock3.collides(bird));
    }

    public void onRestart() {
        ground1.onRestart(0, SCROLL_SPEED);
        ground2.onRestart(ground1.getTailX(), SCROLL_SPEED);
        rock1.onRestart(210, SCROLL_SPEED);
        rock2.onRestart(rock1.getTailX() + PIPE_GAP, SCROLL_SPEED);
        rock3.onRestart(rock2.getTailX() + PIPE_GAP, SCROLL_SPEED);
    }

    // The getters for our five instance variables
    public Ground getGround1() {
        return ground1;
    }

    public Ground getGround2() {
        return ground2;
    }

    public Rock getRock1() {
        return rock1;
    }

    public Rock getRock2() {
        return rock2;
    }

    public Rock getRock3() {
        return rock3;
    }

}

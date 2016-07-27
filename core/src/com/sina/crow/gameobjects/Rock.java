package com.sina.crow.gameobjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.sina.crow.gameobjects.Bird;

import java.util.Random;

/**
 * Created by Sina on 2016-05-12.
 */
public class Rock extends Scrollable {

    private Random r;
    private Rectangle square;
    private Boolean isScored = false;

    public Rock(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
        r = new Random();
        square = new Rectangle();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        square.set(position.x, position.y, width, height);
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        position.y = r.nextInt(140);
        isScored = false;
    }

    public Rectangle getSquare() {
        return square;
    }

    public boolean collides(Bird bird) {
        if (position.x < bird.getX() + bird.getWidth()) {
            return (Intersector.overlaps(bird.getBoundingCircle(), square));
        }
        return false;
    }

    public boolean isScored() {
        return isScored;
    }

    public void setScored(boolean b) {
        isScored = b;
    }

    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
    }

}

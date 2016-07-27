package com.sina.crow.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Sina on 2016-05-12.
 */
public class Bird {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private int width;
    private int height;
    private Circle boundingCircle;
    private float originalY;

    private boolean isAlive;

    public Bird(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 460);
        boundingCircle = new Circle();
        isAlive = true;
        this.originalY = y;
    }

    public void update(float delta) {

        velocity.add(acceleration.cpy().scl(delta));

        if (velocity.y > 200) {
            velocity.y = 200;
        }

        if (position.y <= 0 - 5) {
            position.y = 0 - 5;
        }

        position.add(velocity.cpy().scl(delta));
        boundingCircle.set(position.x + 18, position.y + 20, 6.5f);

    }

    public void updateReady(float runTime) {
        position.y =  2 * (float) Math.sin(7 * runTime) + originalY;
    }


    public void onClick() {
        if (isAlive) {
            velocity.y = -180;
        }
    }

    public void onRestart(int y) {
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 460;
        isAlive = true;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public Boolean isAlive() {
        return isAlive;
    }

    public void decelerate() {
        acceleration.y = 0;
    }

    public void die() {
        isAlive = false;
        velocity.y = 0;
    }


}

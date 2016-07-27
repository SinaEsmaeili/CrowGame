package com.sina.crow.gameobjects;

/**
 * Created by Sina on 2016-05-12.
 */
public class Ground extends Scrollable {


    public Ground(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);

    }

    public void onRestart(float x, float scrollSpeed) {
        position.x = x;
        velocity.x = scrollSpeed;
    }

}

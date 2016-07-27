package com.sina.crow.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Sina on 2016-05-12.
 */
public class AssetLoader {

	public static Texture texture, loading;

    public static TextureRegion bg, ground, playButtonUp, playButtonDown, crowLogo, tap;
    public static TextureRegion b1, b2, b3, b4, b5;
    public static TextureRegion rock, score, gold, silver, bronze;

    public static Animation birdAnimation;

    public static BitmapFont font;

    public static Music music;

    public static Preferences prefs;

	public static void load() {

		texture = new Texture(Gdx.files.internal("texture.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		bg = new TextureRegion(texture, 0, 0, 256, 100);
		bg.flip(false, true);

		ground = new TextureRegion(texture, 0, 150, 336, 112);
		ground.flip(false, true);

		b1 = new TextureRegion(texture, 250, 275, 50, 50);
		b1.flip(false, true);

		b2 = new TextureRegion(texture, 300, 275, 50, 50);
		b2.flip(false, true);

		b3 = new TextureRegion(texture, 350, 275, 50, 50);
		b3.flip(false, true);

		b4 = new TextureRegion(texture, 400, 275, 50, 50);
		b4.flip(false, true);

		b5 = new TextureRegion(texture, 450, 275, 50, 50);
		b5.flip(false, true);


		TextureRegion[] birds = { b1, b2, b3, b4, b5 };
		birdAnimation = new Animation(0.3f, birds);
		birdAnimation.setPlayMode(Animation.PlayMode.LOOP);

		rock = new TextureRegion(texture, 256, 0, 32, 32);
        rock.flip(false, true);

        font = new BitmapFont(Gdx.files.internal("edo.fnt"));

        playButtonUp = new TextureRegion(texture, 256, 32, 29, 16);
        playButtonUp.flip(false, true);

        playButtonDown = new TextureRegion(texture, 285, 32, 29, 16);
        playButtonDown.flip(false, true);

        crowLogo = new TextureRegion(texture, 322, 0, 173, 69);
        crowLogo.flip(false, true);

        score = new TextureRegion(texture, 0, 264, 116, 51);
        score.flip(false, true);

        bronze = new TextureRegion(texture, 0, 315, 18, 18);
        bronze.flip(false, true);

        silver = new TextureRegion(texture, 18, 315, 18, 18);
        silver.flip(false, true);

        gold = new TextureRegion(texture, 36, 315, 18, 18);
        gold.flip(false, true);

        tap = new TextureRegion(texture, 256, 49, 34, 17);
        tap.flip(false, true);

        music = Gdx.audio.newMusic(Gdx.files.internal("caverns.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);

        loading = new Texture(Gdx.files.internal("loading.png"));

        prefs = Gdx.app.getPreferences("Crow");

        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }

	}

    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush(); // flush saves preferences
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

	public static void dispose() {
		// We must dispose of the texture when we are finished.
		texture.dispose();
        font.dispose();
        loading.dispose();


	}

}
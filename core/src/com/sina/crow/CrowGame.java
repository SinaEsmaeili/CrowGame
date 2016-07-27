package com.sina.crow;

import com.badlogic.gdx.Game;

public class CrowGame extends Game {
    AdHandler handler;

    public CrowGame(AdHandler handler) {
        this.handler = handler;
    }

	@Override
	public void create () {
        com.sina.crow.helpers.AssetLoader.load();
        setScreen(new com.sina.crow.screens.SplashScreen(this, handler));
	}

    @Override
    public void dispose() {
        super.dispose();
        com.sina.crow.helpers.AssetLoader.dispose();
    }

}

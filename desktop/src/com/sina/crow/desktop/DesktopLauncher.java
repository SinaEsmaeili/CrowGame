package com.sina.crow.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sina.crow.CrowGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Crow";
		config.width = 272;
		config.height = 408;
		//new LwjglApplication(new CrowGame(), config);
	}
}

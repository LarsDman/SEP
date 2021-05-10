package de.paluno.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.paluno.game.Constants;
import de.paluno.game.SEPgame;
import de.paluno.game.screens.MenuScreen;

public class DesktopLauncher {
	
	
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=Constants.BILDSCHIRMBREITE;
		config.height= Constants.BILDSCHIRMHOEHE;
		config.backgroundFPS=60;
		config.foregroundFPS=60;
		new LwjglApplication(new SEPgame(), config);
	}
}


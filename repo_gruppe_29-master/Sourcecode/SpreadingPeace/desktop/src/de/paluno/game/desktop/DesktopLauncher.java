  package de.paluno.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication; 
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.paluno.game.Constants;
import de.paluno.game.SEPgame;

public class DesktopLauncher {
	
	
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.backgroundFPS=60;
		config.foregroundFPS=60;
		new LwjglApplication(new SEPgame(), config);
//		config.width = Gdx.graphics.getWidth();
//		config.height = Gdx.graphics.getHeight();
		
//		config.width=1440;
//		config.height=855;
		
		config.width = Constants.BILDSCHIRMBREITE; 
		config.height = Constants.BILDSCHIRMHOEHE; 
		
		
		config.resizable = false;
	}
}


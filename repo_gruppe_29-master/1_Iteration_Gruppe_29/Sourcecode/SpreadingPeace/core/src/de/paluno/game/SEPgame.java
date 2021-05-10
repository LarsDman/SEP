package de.paluno.game;

//This is the class that should be instantiated in the game´s main-method and passed to an LwjglApplication instance to start the game.
//It extends Game to enable support for multiple screens. The only method that this class needs to overwrite is the create method.
//Because this method should only ever be called once the game starts, it should set the screen to a new instance of the first screen that needs to be displayed.

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.paluno.game.screens.MenuScreen;

public class SEPgame extends Game {

	public SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		this.setScreen(new MenuScreen(this));
//		batch = new SpriteBatch();
//		img = new Texture("wall-1961660_1280.jpg");
	}

	public void render() {
		super.render();
	}
}

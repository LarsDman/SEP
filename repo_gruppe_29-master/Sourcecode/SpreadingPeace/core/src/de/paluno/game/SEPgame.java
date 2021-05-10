package de.paluno.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.paluno.game.screens.StartScreen;

public class SEPgame extends Game {

	public SpriteBatch batch;
	public BitmapFont font;

	@Override
	public void create() {
//		try {
		batch = new SpriteBatch();
		this.setScreen(new StartScreen(this));
		ManualCreator.createFile();
//		}
//		catch (Exception e)
//		{
//			setScreen(new ErrorScreen(this));
//		}
	}

	public void render() {
		super.render();
	}
}

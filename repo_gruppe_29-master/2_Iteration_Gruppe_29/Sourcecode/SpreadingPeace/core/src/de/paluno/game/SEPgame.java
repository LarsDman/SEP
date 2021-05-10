package de.paluno.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.paluno.game.screens.GameOverScreen;
import de.paluno.game.screens.MenuScreen;
import de.paluno.game.screens.WinScreen;

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

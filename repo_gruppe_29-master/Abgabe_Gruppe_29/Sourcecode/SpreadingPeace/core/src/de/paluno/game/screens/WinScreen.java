package de.paluno.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import de.paluno.game.SEPgame;

public class WinScreen implements Screen {

	private static final int WIN_SCHRIFTZUG_breite = 600;
	private static final int WIN_SCHRIFTZUG_hoehe = 70;
	
	SEPgame game;
	Texture WinSchriftzug;
	BitmapFont scoreFont;
	BitmapFont scoreFontActive;
	
	public WinScreen(SEPgame game) {
		this.game=game;
		
		WinSchriftzug = new Texture("Win_title.png");
		scoreFont = new BitmapFont(Gdx.files.internal("fonts/game.fnt"));
		scoreFontActive = new BitmapFont(Gdx.files.internal("fontActive/gamey.fnt"));
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		
		GlyphLayout MainMenuLayout = new GlyphLayout(scoreFont, "Zurueck zum Hauptmenu");
		
		float MainMenuX = Gdx.graphics.getWidth() / 2 - MainMenuLayout.width / 2;
		float MainMenuY = Gdx.graphics.getHeight() / 2 - MainMenuLayout.height / 2 - 100;	
		float klickX = Gdx.input.getX();
		float klickY = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		if(Gdx.input.isTouched()) {
			if (klickX > MainMenuX && klickX < MainMenuX + MainMenuLayout.width && klickY > MainMenuY - MainMenuLayout.height && klickY < MainMenuY) {
				this.dispose();
				game.batch.end();
				game.setScreen(new MenuScreen(game));
				return;
			}
		}
		
		scoreFont.draw(game.batch, MainMenuLayout, MainMenuX, MainMenuY);
		
		if (klickX > MainMenuX && klickX < MainMenuX + MainMenuLayout.width && klickY > MainMenuY - MainMenuLayout.height && klickY < MainMenuY) {
			scoreFontActive.draw(game.batch, MainMenuLayout, MainMenuX, MainMenuY);
		}
		
		game.batch.draw(WinSchriftzug, Gdx.graphics.getWidth() / 2 - WIN_SCHRIFTZUG_breite / 2, Gdx.graphics.getHeight() - WIN_SCHRIFTZUG_hoehe - 100, WIN_SCHRIFTZUG_breite, WIN_SCHRIFTZUG_hoehe);
		
		game.batch.end();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}

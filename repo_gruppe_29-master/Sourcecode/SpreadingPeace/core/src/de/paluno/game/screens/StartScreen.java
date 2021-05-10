package de.paluno.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import de.paluno.game.SEPgame;

public class StartScreen implements Screen {

	private static final int SPREADING_SCHRIFTZUG_breite = 632;
	private static final int SPREADING_SCHRIFTZUG_hoehe = 65;
	private static final int PEACE_SCHRIFTZUG_breite = 362;
	private static final int PEACE_SCHRIFTZUG_hoehe = 65;
	
	
	SEPgame game;
	Texture SpreadingSchriftzug;
	Texture PeaceSchriftzug;
	BitmapFont scoreFont;
	BitmapFont scoreFontActive;
	Sound backgroundmusic;
	long soundid;
	
	public StartScreen(SEPgame game) {
		this.game=game;
		
		SpreadingSchriftzug = new Texture("Spreading_title.png");
		PeaceSchriftzug = new Texture("Peace_title.png");
		scoreFont = new BitmapFont(Gdx.files.internal("fonts/game.fnt"));
		scoreFontActive = new BitmapFont (Gdx.files.internal("fontActive/gamey.fnt"));
		backgroundmusic = Gdx.audio.newSound(Gdx.files.internal("Demise_Short.wav"));
		soundid = backgroundmusic.loop(0.15f);
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
		
		GlyphLayout MainMenuLayout = new GlyphLayout(scoreFont, "Hauptmenu");
		
		float MainMenuX = Gdx.graphics.getWidth() / 2 - MainMenuLayout.width / 2;
		float MainMenuY = Gdx.graphics.getHeight() / 2 - MainMenuLayout.height / 2 - 100;	
		float klickX = Gdx.input.getX();
		float klickY = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		if(Gdx.input.isTouched()) {
			if (klickX > MainMenuX && klickX < MainMenuX + MainMenuLayout.width && klickY > MainMenuY - MainMenuLayout.height && klickY < MainMenuY) {
				this.dispose();
				game.batch.end();
				backgroundmusic.stop();
				game.setScreen(new MenuScreen(game));
				return;
			}
		}
		
		scoreFont.draw(game.batch, MainMenuLayout, MainMenuX, MainMenuY);
		
		if (klickX > MainMenuX && klickX < MainMenuX + MainMenuLayout.width && klickY > MainMenuY - MainMenuLayout.height && klickY < MainMenuY) {
		scoreFontActive.draw(game.batch, MainMenuLayout, MainMenuX, MainMenuY);
		}
		
		game.batch.draw(SpreadingSchriftzug, Gdx.graphics.getWidth() / 2 - SPREADING_SCHRIFTZUG_breite / 2, Gdx.graphics.getHeight() - SPREADING_SCHRIFTZUG_hoehe - 100, SPREADING_SCHRIFTZUG_breite, SPREADING_SCHRIFTZUG_hoehe);
		game.batch.draw(PeaceSchriftzug, Gdx.graphics.getWidth() / 2 - PEACE_SCHRIFTZUG_breite / 2, Gdx.graphics.getHeight() - PEACE_SCHRIFTZUG_hoehe - 200, PEACE_SCHRIFTZUG_breite, PEACE_SCHRIFTZUG_hoehe);
		
		game.batch.end();

	}

	@Override
	public void resize(int width, int height) {
//		game.batch.setProjectionMatrix(new SpriteBatch().getProjectionMatrix());

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

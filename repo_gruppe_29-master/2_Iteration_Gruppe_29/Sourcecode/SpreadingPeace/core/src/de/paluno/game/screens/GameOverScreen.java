package de.paluno.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;


import de.paluno.game.Constants;
import de.paluno.game.SEPgame;

public class GameOverScreen implements Screen {
	
	protected float width = Constants.BILDSCHIRMBREITE;
	protected float height = Constants.BILDSCHIRMHOEHE;
	private static final int GAME_OVER_SCHRIFTZUG_breite = 700;
	private static final int GAME_OVER_SCHRIFTZUG_hoehe = 65;
	public OrthographicCamera camera;

	SEPgame game;
	int score, highscore;
	 
	Texture gameOverSchriftzug;
	BitmapFont scoreFont;
	
	Preferences prefs = Gdx.app.getPreferences("SpreadingPeace");
	 
	public GameOverScreen (SEPgame game ,int score) {
		this.game = game;
		this.score = score;
		

		this.highscore = prefs.getInteger("highscore", 0);
		

		
		if (score > highscore) {
			prefs.putInteger("highscore", score);
			prefs.flush(); 		// speichert den neuen Wert in die Datei
		} 
		
		
		this.highscore = prefs.getInteger("highscore", 0);
		
		gameOverSchriftzug = new Texture("game_over_title2.png");
		scoreFont = new BitmapFont(Gdx.files.internal("fonts/game.fnt"));
		
	}


	public void setHighscore(int highscore) {
		prefs.putInteger("highscore", highscore);
		prefs.flush(); 
	}


	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 
//		camera = new OrthographicCamera(); 
//		camera.setToOrtho(false, width, height);
		game.batch.begin(); 
		
		game.batch.draw(gameOverSchriftzug, Gdx.graphics.getWidth() / 2 - GAME_OVER_SCHRIFTZUG_breite / 2, Gdx.graphics.getHeight() - GAME_OVER_SCHRIFTZUG_hoehe - 100, GAME_OVER_SCHRIFTZUG_breite, GAME_OVER_SCHRIFTZUG_hoehe);
		
		GlyphLayout ueberredetLayout = new GlyphLayout(scoreFont, "ueberredete Friedenspanzer: " + score);
		GlyphLayout HighscoreLayout = new GlyphLayout(scoreFont, "Highscore: " + highscore);
		scoreFont.draw(game.batch, ueberredetLayout, Gdx.graphics.getWidth() / 2 - ueberredetLayout.width / 2, Gdx.graphics.getHeight() - GAME_OVER_SCHRIFTZUG_hoehe - 100 * 2);
		scoreFont.draw(game.batch, HighscoreLayout, Gdx.graphics.getWidth() / 2 - ueberredetLayout.width / 2, Gdx.graphics.getHeight() - GAME_OVER_SCHRIFTZUG_hoehe - 100 * 3);
		
		GlyphLayout MainMenuLayout = new GlyphLayout(scoreFont, "Hauptmenu");
		GlyphLayout HighscoreTabelleLayout = new GlyphLayout(scoreFont, "Highscoretabelle");
		
		float highscoreTabelleX = Gdx.graphics.getWidth() / 2 - HighscoreTabelleLayout.width / 2;
		float highscoreTabelleY	= Gdx.graphics.getHeight() / 2 - HighscoreTabelleLayout.height / 2 - 100;	
		float MainMenuX = Gdx.graphics.getWidth() / 2 - MainMenuLayout.width / 2;
		float MainMenuY = Gdx.graphics.getHeight() / 2 - MainMenuLayout.height / 2 - HighscoreTabelleLayout.height - 180;	
		
		float klickX = Gdx.input.getX();
		float klickY = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		if (Gdx.input.isTouched()) {
		// HighscoreTabelle
		if (klickX > highscoreTabelleX && klickX < highscoreTabelleX + HighscoreTabelleLayout.width && klickY > highscoreTabelleY - HighscoreTabelleLayout.height && klickY < highscoreTabelleY) {
			this.dispose();
			game.batch.end();
			game.setScreen(new HighScoreScreen(game, score)); 
			return;
		}
		// Hauptmenu
		if (klickX > MainMenuX && klickX < MainMenuX + MainMenuLayout.width && klickY > MainMenuY - MainMenuLayout.height && klickY < MainMenuY) {
			this.dispose();
			game.batch.end();
			game.setScreen(new MenuScreen(game));
			return;
		}
	}
		
		scoreFont.draw(game.batch, HighscoreTabelleLayout, highscoreTabelleX, highscoreTabelleY);
		scoreFont.draw(game.batch, MainMenuLayout, MainMenuX, MainMenuY);
		
		game.batch.end();

	}

	@Override
	public void resize(int width, int height) {

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

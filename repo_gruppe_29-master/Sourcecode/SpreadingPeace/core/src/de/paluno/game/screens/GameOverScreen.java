package de.paluno.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.paluno.game.Constants;
import de.paluno.game.SEPgame;

public class GameOverScreen implements Screen {
	
//	protected float width = Constants.BILDSCHIRMBREITE;
//	protected float height = Constants.BILDSCHIRMHOEHE;
	private static final int GAME_OVER_SCHRIFTZUG_breite = 700;
	private static final int GAME_OVER_SCHRIFTZUG_hoehe = 65;
	public OrthographicCamera camera;
	private static float screenWidth = Constants.BILDSCHIRMBREITE;
	private static float screenHeight = Constants.BILDSCHIRMHOEHE;
	
	SEPgame game;
	int score, highscore;
	 
	Texture gameOverSchriftzug;
	BitmapFont scoreFont;
	BitmapFont scoreFontActive;
	
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
		scoreFontActive = new BitmapFont(Gdx.files.internal("fontActive/gamey.fnt"));
		
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
		
		game.batch.draw(gameOverSchriftzug, screenWidth / 2 - GAME_OVER_SCHRIFTZUG_breite / 2, screenHeight - GAME_OVER_SCHRIFTZUG_hoehe - 60, GAME_OVER_SCHRIFTZUG_breite, GAME_OVER_SCHRIFTZUG_hoehe);
		
		GlyphLayout ueberredetLayout = new GlyphLayout(scoreFont, "ueberredete Friedenspanzer: " + score);
		GlyphLayout HighscoreLayout = new GlyphLayout(scoreFont, "Highscore: " + highscore);
		scoreFont.draw(game.batch, ueberredetLayout, screenWidth / 2 - ueberredetLayout.width / 2, screenHeight - GAME_OVER_SCHRIFTZUG_hoehe - 100 * 1.2f);
		scoreFont.draw(game.batch, HighscoreLayout, screenWidth / 2 - ueberredetLayout.width / 2, screenHeight - GAME_OVER_SCHRIFTZUG_hoehe - 100 * 2f);
		
		GlyphLayout MainMenuLayout = new GlyphLayout(scoreFont, "Hauptmenu");
		GlyphLayout HighscoreTabelleLayout = new GlyphLayout(scoreFont, "Highscoretabelle");
		GlyphLayout MainMenuLayoutActivated = new GlyphLayout(scoreFontActive, "Hauptmenu");
		GlyphLayout HighscoreTabelleLayoutActivated = new GlyphLayout(scoreFontActive, "Highscoretabelle");
		
		float highscoreTabelleX = screenWidth / 2 - HighscoreTabelleLayout.width / 2;
		float highscoreTabelleY	= screenHeight / 2 - HighscoreTabelleLayout.height / 2 - 100;	
		float MainMenuX = screenWidth / 2 - MainMenuLayout.width / 2;
		float MainMenuY = screenHeight / 2 - MainMenuLayout.height / 2 - HighscoreTabelleLayout.height - 180;	
		
		float klickX = Gdx.input.getX();
		float klickY = screenHeight - Gdx.input.getY();
		
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
		
		if (klickX > MainMenuX && klickX < MainMenuX + MainMenuLayout.width && klickY > MainMenuY - MainMenuLayout.height && klickY < MainMenuY) {
			scoreFontActive.draw(game.batch, MainMenuLayoutActivated, MainMenuX, MainMenuY);
		}
		
		if (klickX > highscoreTabelleX && klickX < highscoreTabelleX + HighscoreTabelleLayout.width && klickY > highscoreTabelleY - HighscoreTabelleLayout.height && klickY < highscoreTabelleY) {
			scoreFontActive.draw(game.batch, HighscoreTabelleLayoutActivated, highscoreTabelleX, highscoreTabelleY);
		}
		
		
		game.batch.end();

	}

	@Override
	public void resize(int width, int height) {
		game.batch.setProjectionMatrix(new SpriteBatch().getProjectionMatrix());
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

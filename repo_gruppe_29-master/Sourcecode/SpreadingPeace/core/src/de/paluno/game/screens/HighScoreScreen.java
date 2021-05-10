package de.paluno.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.paluno.game.SEPgame;

public class HighScoreScreen implements Screen {
	
	private static final int HIGHSCORES_SCHRIFTZUG_breite = 700;
	private static final int HIGHSCORES_SCHRIFTZUG_hoehe = 70;

	SEPgame game;
	int score, Highscore2, Highscore3, Highscore4, Highscore5;
	static int Highscore1;
	

	Texture HighScoresSchriftzug;
	BitmapFont scoreFont;
	BitmapFont scoreFontActive;
	BitmapFont scoreFontWarning;
	Preferences prefs = Gdx.app.getPreferences("SpreadingPeace");
	
	public HighScoreScreen (SEPgame game, int score) {
		this.game = game;
		
		
		HighScoreScreen.Highscore1 = prefs.getInteger("highscore1", 0);
		this.Highscore2 = prefs.getInteger("highscore2", 0);
		this.Highscore3 = prefs.getInteger("highscore3", 0);
		this.Highscore4 = prefs.getInteger("highscore4", 0);
		this.Highscore5 = prefs.getInteger("highscore5", 0);
		

		if (score > Highscore1) {
			prefs.putInteger("highscore2", Highscore1);
			prefs.putInteger("highscore3", Highscore2);
			prefs.putInteger("highscore4", Highscore3);
			prefs.putInteger("highscore5", Highscore4);
			prefs.putInteger("highscore1", score);
			prefs.flush(); 		// speichert den neuen Wert in die Datei
		}
		else if (score > Highscore2) {
			prefs.putInteger("highscore3", Highscore2);
			prefs.putInteger("highscore4", Highscore3);
			prefs.putInteger("highscore5", Highscore4);
			prefs.putInteger("highscore2", score);
			prefs.flush(); 	
		}
		else if (score > Highscore3) {
			prefs.putInteger("highscore4", Highscore3);
			prefs.putInteger("highscore5", Highscore4);
			prefs.putInteger("highscore3", score);
			prefs.flush(); 		
		}
		else if (score > Highscore4) {
			prefs.putInteger("highscore5", Highscore4);
			prefs.putInteger("highscore4", score);
			prefs.flush(); 		
		}
		else if (score > Highscore5) {
			prefs.putInteger("highscore5", score);
			prefs.flush(); 
		}
		
		HighScoreScreen.Highscore1 = prefs.getInteger("highscore1", 0);
		this.Highscore2 = prefs.getInteger("highscore2", 0);
		this.Highscore3 = prefs.getInteger("highscore3", 0);
		this.Highscore4 = prefs.getInteger("highscore4", 0);
		this.Highscore5 = prefs.getInteger("highscore5", 0);
		
		HighScoresSchriftzug = new Texture("highscores_title.png");
		scoreFont = new BitmapFont(Gdx.files.internal("fonts/game.fnt"));
		scoreFontActive = new BitmapFont(Gdx.files.internal("fontActive/gamey.fnt"));
		scoreFontWarning = new BitmapFont(Gdx.files.internal("fonts/warning.fnt"));
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		
		GlyphLayout HighscoreLayout1 = new GlyphLayout(scoreFont, "1. \t Platz: \t" + "     " + Highscore1);
		GlyphLayout HighscoreLayout2 = new GlyphLayout(scoreFont, "2. \t Platz: \t" + "    " + Highscore2);
		GlyphLayout HighscoreLayout3 = new GlyphLayout(scoreFont, "3. \t Platz: \t" + "    " + Highscore3);
		GlyphLayout HighscoreLayout4 = new GlyphLayout(scoreFont, "4. \t Platz: \t" + "    " + Highscore4);
		GlyphLayout HighscoreLayout5 = new GlyphLayout(scoreFont, "5. \t Platz: \t" + "    " + Highscore5);
		
		GlyphLayout MainMenuLayout = new GlyphLayout(scoreFont, "Zurueck zum Hauptmenu");
		GlyphLayout MainMenuLayoutYellow = new GlyphLayout(scoreFontActive, "Zurueck zum Hauptmenu");
		GlyphLayout ResetHighscoreLayout = new GlyphLayout (scoreFont, "Reset Highscores");
		GlyphLayout ResetHighscoreLayoutYellow = new GlyphLayout (scoreFontActive, "Reset Highscores");
		
		float MainMenuX = Gdx.graphics.getWidth() / 2 - MainMenuLayout.width / 2;
		float MainMenuY = Gdx.graphics.getHeight() / 2 - MainMenuLayout.height / 2 - 200;	
		float klickX = Gdx.input.getX();
		float klickY = Gdx.graphics.getHeight() - Gdx.input.getY();
		float ResetHighscoreX = Gdx.graphics.getWidth() - ResetHighscoreLayout.width*1.2f;
		float ResetHighscoreY = 60 + ResetHighscoreLayout.height;
		
		if(Gdx.input.isTouched()) {
			if (klickX > MainMenuX && klickX < MainMenuX + MainMenuLayout.width && klickY > MainMenuY - MainMenuLayout.height && klickY < MainMenuY) {
				this.dispose();
				game.batch.end();
				game.setScreen(new MenuScreen(game));
				return;
			}
		}
		
		if (Gdx.input.isTouched()) {
			if (klickX > ResetHighscoreX && klickX < ResetHighscoreX + ResetHighscoreLayout.width && klickY > ResetHighscoreY - ResetHighscoreLayout.height && klickY < ResetHighscoreY) {
				prefs.putInteger("highscore1", 0);
				prefs.putInteger("highscore2", 0);
				prefs.putInteger("highscore3", 0);
				prefs.putInteger("highscore4", 0);
				prefs.putInteger("highscore5", 0);
				GameOverScreen a = new GameOverScreen(game,0);
				a.setHighscore(0);
				prefs.flush(); 	
				game.setScreen(new HighScoreScreen(game, 0));
				this.dispose();
			}
		}

		
		game.batch.draw(HighScoresSchriftzug, Gdx.graphics.getWidth() / 2 - HIGHSCORES_SCHRIFTZUG_breite / 2, Gdx.graphics.getHeight() - HIGHSCORES_SCHRIFTZUG_hoehe - 60, HIGHSCORES_SCHRIFTZUG_breite, HIGHSCORES_SCHRIFTZUG_hoehe);
		scoreFont.draw(game.batch, HighscoreLayout1, Gdx.graphics.getWidth() / 2 - HIGHSCORES_SCHRIFTZUG_breite / 2, Gdx.graphics.getHeight() - HIGHSCORES_SCHRIFTZUG_hoehe - 120);
		scoreFont.draw(game.batch, HighscoreLayout2, Gdx.graphics.getWidth() / 2 - HIGHSCORES_SCHRIFTZUG_breite / 2, Gdx.graphics.getHeight() - HIGHSCORES_SCHRIFTZUG_hoehe - 170);
		scoreFont.draw(game.batch, HighscoreLayout3, Gdx.graphics.getWidth() / 2 - HIGHSCORES_SCHRIFTZUG_breite / 2, Gdx.graphics.getHeight() - HIGHSCORES_SCHRIFTZUG_hoehe - 220);
		scoreFont.draw(game.batch, HighscoreLayout4, Gdx.graphics.getWidth() / 2 - HIGHSCORES_SCHRIFTZUG_breite / 2, Gdx.graphics.getHeight() - HIGHSCORES_SCHRIFTZUG_hoehe - 270);
		scoreFont.draw(game.batch, HighscoreLayout5, Gdx.graphics.getWidth() / 2 - HIGHSCORES_SCHRIFTZUG_breite / 2, Gdx.graphics.getHeight() - HIGHSCORES_SCHRIFTZUG_hoehe - 320);
		scoreFont.draw(game.batch, MainMenuLayout, MainMenuX, MainMenuY);
		scoreFont.draw(game.batch, ResetHighscoreLayout, ResetHighscoreX, ResetHighscoreY);
		
		if (klickX > ResetHighscoreX && klickX < ResetHighscoreX + ResetHighscoreLayout.width && klickY > ResetHighscoreY - ResetHighscoreLayout.height && klickY < ResetHighscoreY) {
			scoreFontWarning.draw(game.batch, ResetHighscoreLayoutYellow, ResetHighscoreX, ResetHighscoreY);
		}
		if (klickX > MainMenuX && klickX < MainMenuX + MainMenuLayout.width && klickY > MainMenuY - MainMenuLayout.height && klickY < MainMenuY) {
			scoreFontActive.draw(game.batch, MainMenuLayoutYellow, MainMenuX, MainMenuY);
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

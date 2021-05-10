package de.paluno.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.paluno.game.SEPgame;

public class WinScreenCollection implements Screen {

	private static final int DRAW_SCHRIFTZUG_breite = 360;
	private static final int DRAW_SCHRIFTZUG_hoehe = 65;
	private static final int TEAM_1_WIN_SCHRIFTZUG_breite = 700;
	private static final int TEAM_1_WIN_SCHRIFTZUG_hoehe = 65;
	private static final int TEAM_2_WIN_SCHRIFTZUG_breite = 730;
	private static final int TEAM_2_WIN_SCHRIFTZUG_hoehe = 65;
	private static final int TEAM_3_WIN_SCHRIFTZUG_breite = 740;
	private static final int TEAM_3_WIN_SCHRIFTZUG_hoehe = 65;
	private static final int TEAM_4_WIN_SCHRIFTZUG_breite = 740;
	private static final int TEAM_4_WIN_SCHRIFTZUG_hoehe = 65;
	private static final int TEAM_5_WIN_SCHRIFTZUG_breite = 740;
	private static final int TEAM_5_WIN_SCHRIFTZUG_hoehe = 65;
	
	SEPgame game;
	private String text;
	Texture DrawSchriftzug, Team1WinSchriftzug, Team2WinSchriftzug, Team3WinSchriftzug, Team4WinSchriftzug,
			Team5WinSchriftzug;
	BitmapFont scoreFont;
	BitmapFont scoreFontActive;
	
	public WinScreenCollection(SEPgame game, String text) {
		this.game=game;
		this.text=text;
		
		DrawSchriftzug = new Texture("Draw_title.png");
		Team1WinSchriftzug = new Texture("Team_1_Winscreen.png");
		Team2WinSchriftzug = new Texture("Team_2_Winscreen.png");
		Team3WinSchriftzug = new Texture("Team_3_Winscreen.png");
		Team4WinSchriftzug = new Texture("Team_4_Winscreen.png");
		Team5WinSchriftzug = new Texture("Team_5_Winscreen.png");
		scoreFont = new BitmapFont(Gdx.files.internal("fonts/game.fnt"));
		scoreFontActive = new BitmapFont (Gdx.files.internal("fontActive/gamey.fnt"));
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
		
		if (text.equals("Draw")) {
		game.batch.draw(DrawSchriftzug, Gdx.graphics.getWidth() / 2 - DRAW_SCHRIFTZUG_breite / 2, Gdx.graphics.getHeight() - DRAW_SCHRIFTZUG_hoehe - 100, DRAW_SCHRIFTZUG_breite, DRAW_SCHRIFTZUG_hoehe);
		}
		if (text.equals("Team1")) {
			game.batch.draw(Team1WinSchriftzug, Gdx.graphics.getWidth() / 2 - TEAM_1_WIN_SCHRIFTZUG_breite / 2, Gdx.graphics.getHeight() - TEAM_1_WIN_SCHRIFTZUG_hoehe - 100, TEAM_1_WIN_SCHRIFTZUG_breite, TEAM_1_WIN_SCHRIFTZUG_hoehe);
		}
		if (text.equals("Team2")) {
			game.batch.draw(Team2WinSchriftzug, Gdx.graphics.getWidth() / 2 - TEAM_2_WIN_SCHRIFTZUG_breite / 2, Gdx.graphics.getHeight() - TEAM_2_WIN_SCHRIFTZUG_hoehe - 100, TEAM_2_WIN_SCHRIFTZUG_breite, TEAM_2_WIN_SCHRIFTZUG_hoehe);
		}
		if (text.equals("Team3")) {
			game.batch.draw(Team3WinSchriftzug, Gdx.graphics.getWidth() / 2 - TEAM_3_WIN_SCHRIFTZUG_breite / 2, Gdx.graphics.getHeight() - TEAM_3_WIN_SCHRIFTZUG_hoehe - 100, TEAM_3_WIN_SCHRIFTZUG_breite, TEAM_3_WIN_SCHRIFTZUG_hoehe);
		}
		if (text.equals("Team4")) {
			game.batch.draw(Team4WinSchriftzug, Gdx.graphics.getWidth() / 2 - TEAM_4_WIN_SCHRIFTZUG_breite / 2, Gdx.graphics.getHeight() - TEAM_4_WIN_SCHRIFTZUG_hoehe - 100, TEAM_4_WIN_SCHRIFTZUG_breite, TEAM_4_WIN_SCHRIFTZUG_hoehe);
		}
		if (text.equals("Team5")) {
			game.batch.draw(Team5WinSchriftzug, Gdx.graphics.getWidth() / 2 - TEAM_5_WIN_SCHRIFTZUG_breite / 2, Gdx.graphics.getHeight() - TEAM_5_WIN_SCHRIFTZUG_hoehe - 100, TEAM_5_WIN_SCHRIFTZUG_breite, TEAM_5_WIN_SCHRIFTZUG_hoehe);
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

package de.paluno.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.paluno.game.SEPgame;

public class Hud_Survival extends Hud{

	private int killCounter;
	private Label killCounterLabel, highscoreLabel;
	
	public Hud_Survival(SpriteBatch sb, Gamemode playScreen, SEPgame game) {
		super(sb, playScreen, game);
		String kills = String.valueOf(killCounter);
		killCounterLabel = new Label("Kills: " + kills, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		highscoreLabel = new Label("Highscore: " + HighScoreScreen.Highscore1, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		killCounter = 0;
		this.show();
	}
	
	@Override
	public void show() {
		this.actions();
		this.createTableUp();
		this.createStopTable();
		this.addStopTableListeners();
	}
	
	@Override
	public void createTableUp() {
		stage.addActor(tableUp);
		tableUp.top();
		tableUp.setFillParent(true);
		tableUp.add(killCounterLabel).expandX();
		tableUp.add(highscoreLabel).expandX();
		tableUp.add(gamemodeLabel).expandX();
		tableUp.add(levelLabel).expandX();
		tableUp.add(countdownLabel).expandX();
		tableUp.add(stopLabel).expandX();
	}
	
	@Override
	public void update(float delta) {
		this.timerStart(delta);
		this.changePauseColor();
		this.stop();
		this.updateKillCounter();
	}
	
	public void updateKillCounter() {
		if(playScreen.getKilledTankList().isEmpty() == false) {
			killCounter = playScreen.getKilledTankList().size();
			killCounterLabel.setText("Kills: " + killCounter);
		}
	}
}

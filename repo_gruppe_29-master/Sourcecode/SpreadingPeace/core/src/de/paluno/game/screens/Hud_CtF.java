package de.paluno.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.paluno.game.SEPgame;

public class Hud_CtF extends Hud{
	
	private Label roundscore, score;
	private int scoreTeam1, scoreTeam2, roundscoreTeam1,roundscoreTeam2;
	private boolean acted;

	public Hud_CtF(SpriteBatch sb, Gamemode playScreen, SEPgame game) {
		super(sb, playScreen, game);
		
		acted = false;
		
		scoreTeam1 = CaptureTheFlag.scoreTeam1;
		scoreTeam2 = CaptureTheFlag.scoreTeam2;
		
		roundscoreTeam1 = CaptureTheFlag.roundScoreTeam1;
		roundscoreTeam2 = CaptureTheFlag.roundScoreTeam2;
		
		roundscore = new Label("Team 1 " + scoreTeam1 + ":" + scoreTeam2 + " Team 2", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		score = new Label("Team 1" + roundscoreTeam1 + ":" + roundscoreTeam2 + "Team 2", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		this.show();
	}

	@Override
	public void update(float delta) {
		this.timerStart(delta);
		this.changePauseColor();
		this.stop();
		this.updateScore();
		this.updateRoundScore();
		this.nextRound();
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
		
		tableUp.add(roundscore);
		tableUp.add(gamemodeLabel).expandX();
		tableUp.add(levelLabel).expandX();
		tableUp.add(countdownLabel).expandX();
		tableUp.add(stopLabel);
		tableUp.row();
		tableUp.add(score);
	}
	
	public void updateRoundScore() {
		if(CaptureTheFlag.round == 2) {
			roundscoreTeam1 = CaptureTheFlag.roundScoreTeam1;
			roundscoreTeam2 = CaptureTheFlag.roundScoreTeam2;
			roundscore.setText("Team 1:  " + roundscoreTeam1 + ":" + roundscoreTeam2 + "  :Team 2");
		}
	}
	
	public void updateScore() {	
		scoreTeam1 = CaptureTheFlag.scoreTeam1;
		scoreTeam2 = CaptureTheFlag.scoreTeam2;
		score.setText("Team 1:  " + scoreTeam1 + ":" + scoreTeam2 + "  :Team 2");
	}
	
	public void nextRound() {
		if(CaptureTheFlag.startNextRoundForHud == true && acted == false) {
			acted = true;
			this.actions();//321 Counter
		}
	}

}

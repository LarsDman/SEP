package de.paluno.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.paluno.game.SEPgame;

public class Hud_Survival_mit_Geld extends Hud{

	private Label coinsLabel;
	
	public Hud_Survival_mit_Geld(SpriteBatch sb, Gamemode playScreen, SEPgame game) {
		super(sb, playScreen, game);
		coinsLabel = new Label("Coins: " + Survival_mit_Geld.taler, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		this.show();
	}

	@Override
	public void update(float delta) {
		this.timerStart(delta);
		this.changePauseColor();
		this.stop();
		coinsLabel.setText("Coins: " + Survival_mit_Geld.taler);
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
		
		tableUp.add(coinsLabel).expandX();
		tableUp.add(gamemodeLabel).expandX();
		tableUp.add(levelLabel).expandX();
		tableUp.add(countdownLabel).expandX();
		tableUp.add(stopLabel);
	}

}

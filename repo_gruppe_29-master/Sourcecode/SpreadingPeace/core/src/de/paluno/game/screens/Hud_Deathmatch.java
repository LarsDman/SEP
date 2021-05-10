package de.paluno.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.paluno.game.SEPgame;

public class Hud_Deathmatch extends Hud{
	
	private Label killfeedLabel;
	private int counter;
	
	public Hud_Deathmatch(SpriteBatch sb, Gamemode playScreen, SEPgame game) {
		super(sb, playScreen, game);
		killfeedLabel = new Label("Killfeed", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		counter = 1;
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
		tableUp.add(killfeedLabel).expandX();
		tableUp.add(gamemodeLabel).expandX();
		tableUp.add(levelLabel).expandX();
		tableUp.add(countdownLabel).expandX();
		tableUp.add(stopLabel);
	}
	
	@Override
	public void update(float delta) {
		this.timerStart(delta);
		this.changePauseColor();
		this.stop();
		this.updateKillfeed();
	}
	
	public void updateKillfeed() {
		if(playScreen.getKilledTankList().isEmpty() == false && playScreen.getKillerTankList().isEmpty() == false){
			if(playScreen.getKilledTankList().size() == counter && playScreen.getKillerTankList().size() == counter) {
				String newKillString = playScreen.getKilledTankList().get(0) + " killed by " + playScreen.getKillerTankList().get(0);
				Label newKill = new Label(newKillString, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
				tableUp.row();
				tableUp.add(newKill);
				counter++;
			}
		}
	}
}

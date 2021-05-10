package de.paluno.game.input;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import de.paluno.game.GameState;
import de.paluno.game.gameobjects.Blumenauswahl;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.input.actions.Action;
import de.paluno.game.input.actions.AimTurn;
import de.paluno.game.input.actions.Drive;
import de.paluno.game.input.actions.Shoot;
import de.paluno.game.input.actions.Turn;
import de.paluno.game.screens.Gamemode;
import de.paluno.game.screens.Schatzkammer;
import de.paluno.game.screens.Survival_mit_Geld;

public class KeyboardInputProvider extends java.lang.Object implements InputProvider {

	public static final float schussUnterbrechung = 0.3f;
	public static float schussZeit = 0;

	public KeyboardInputProvider() {
		// 0 vorne, 1 links, 2 hinten, 3 rechts, 4 links drehen, 5 rechts drehen, 6
		// schuss, 7 Auswahlff, 8 Auswahlbf, 9 Auswahlsf, 10 Auswahltf, 11 stop
	}

	@Override
	public Action[] getInputs(Tank actor, Gamemode playScreen, ArrayList<String> settings) {
		Action[] act = new Action[4];
		boolean forwards = true;
		schussZeit += Gdx.graphics.getDeltaTime();
		
		if (Gdx.input.isKeyPressed(Keys.valueOf(settings.get(0)))) {
			act[0] = new Drive(actor, actor.getVelocity());
			forwards = true;
		}
		if (Gdx.input.isKeyPressed(Keys.valueOf(settings.get(2)))) {
			act[0] = new Drive(actor, actor.getVelocity() * -1);
			forwards = false;
			if (Gdx.input.isKeyPressed(Keys.valueOf(settings.get(1))) && forwards == false) {
				act[1] = new Turn(actor, -1);
			}
			if (Gdx.input.isKeyPressed(Keys.valueOf(settings.get(3))) && forwards == false) {
				act[1] = new Turn(actor, 1);
			}
		}
		if (Gdx.input.isKeyPressed(Keys.valueOf(settings.get(1))) && forwards == true) {
			act[1] = new Turn(actor, 1);
		}
		if (Gdx.input.isKeyPressed(Keys.valueOf(settings.get(3))) && forwards == true) {
			act[1] = new Turn(actor, -1);
		}
		if (Gdx.input.isKeyPressed(Keys.valueOf(settings.get(5)))) {
			act[2] = new AimTurn(actor, -1);
		}
		if (Gdx.input.isKeyPressed(Keys.valueOf(settings.get(4)))) {
			act[2] = new AimTurn(actor, 1);
		}
		if (playScreen instanceof Survival_mit_Geld) {
			actor.setFlower(Schatzkammer.getGold());
		}
		else {
		 this.chooseFlower(actor, settings);
		}
			
		if (Gdx.input.isKeyJustPressed(Keys.valueOf(settings.get(6))) && schussZeit >= schussUnterbrechung && playScreen.state == GameState.RUNNING) {
			schussZeit = 0;
			act[3] = new Shoot(actor, playScreen);
		}
		
		if(playScreen.state==GameState.RUNNING){
			if (Gdx.input.isKeyJustPressed(Keys.valueOf(settings.get(11)))) {
				playScreen.state = GameState.PAUSED;
			}
		} else{
			if (Gdx.input.isKeyJustPressed(Keys.valueOf(settings.get(11)))) {
				playScreen.state = GameState.RUNNING;}
			}
		return act;
	}
	
	private void chooseFlower(Tank actor, ArrayList<String> settings){
		if (Gdx.input.isKeyPressed(Keys.valueOf(settings.get(7)))) {
			actor.setFlower(Blumenauswahl.NORMALFLOWER);
		}
		if (Gdx.input.isKeyPressed(Keys.valueOf(settings.get(8)))) {
			actor.setFlower(Blumenauswahl.FASTFLOWER);
		}
		if (Gdx.input.isKeyPressed(Keys.valueOf(settings.get(9)))) {
			actor.setFlower(Blumenauswahl.BOUNCEFLOWER);
		}
		if (Gdx.input.isKeyPressed(Keys.valueOf(settings.get(10)))) {
			actor.setFlower(Blumenauswahl.SPLITTEDFLOWER);
		}
	}

}
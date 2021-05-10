package de.paluno.game.input;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.input.actions.Action;
import de.paluno.game.input.actions.AimTurn;
import de.paluno.game.input.actions.Drive;
import de.paluno.game.input.actions.Shoot;
import de.paluno.game.input.actions.Turn;
import de.paluno.game.screens.Gamemode;

public class KeyboardInputProvider extends java.lang.Object implements InputProvider {
	
	public KeyboardInputProvider() {
		//0 vorne, 1 links, 2 hinten, 3 rechts, 4 links drehen, 5 rechts drehen, 6 schuss
	}
	
	@Override
	public Action[] getInputs(Tank actor, Gamemode playScreen, ArrayList<String> settings) {
		Action[] act = new Action[4];
		boolean forwards = true;
		
		if(Gdx.input.isKeyPressed(Keys.valueOf(settings.get(0)))) {
			act[0] = new Drive(actor, actor.getVelocity());
			forwards = true;
		}
		if(Gdx.input.isKeyPressed(Keys.valueOf(settings.get(2)))) {
			act[0] = new Drive(actor, actor.getVelocity()*-1);
			forwards = false;
			if(Gdx.input.isKeyPressed(Keys.valueOf(settings.get(1))) && forwards == false) {
				act[1] = new Turn(actor, -1);
			}
			if(Gdx.input.isKeyPressed(Keys.valueOf(settings.get(3))) && forwards == false) {
				act[1] = new Turn(actor, 1);
			}
		}
		if(Gdx.input.isKeyPressed(Keys.valueOf(settings.get(1))) && forwards == true) {
			act[1] = new Turn(actor, 1);
		}
		if(Gdx.input.isKeyPressed(Keys.valueOf(settings.get(3))) && forwards == true) {
			act[1] = new Turn(actor, -1);
		}
		if(Gdx.input.isKeyPressed(Keys.valueOf(settings.get(5)))) {
			act[2] = new AimTurn(actor, -1);
		}
		if(Gdx.input.isKeyPressed(Keys.valueOf(settings.get(4)))) {
			act[2] = new AimTurn(actor, 1);
		}
		if(Gdx.input.isKeyJustPressed(Keys.valueOf(settings.get(6)))) {
			act[3] = new Shoot(actor, playScreen);
		}
		return act;
	}
}
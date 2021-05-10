package de.paluno.game.input;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import de.paluno.game.GameState;
import de.paluno.game.gameobjects.Bosstank;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.input.actions.Action;
import de.paluno.game.input.actions.Shoot;
import de.paluno.game.screens.Deathmatch;
import de.paluno.game.screens.Gamemode;
import de.paluno.game.screens.MenuScreen;

public class KiInputProvider extends java.lang.Object implements InputProvider {
	
	public static final float schussUnterbrechung = 1.5f;
	public static float schussZeit=0;
	private boolean schussbol =true;

	public KiInputProvider() {
	}

	public Action[] getInputs(Tank actor, Gamemode playScreen, ArrayList<String> settings) {
		
		schussZeit += Gdx.graphics.getDeltaTime();
	
		Action[] act = new Action[5];
		boolean forwards = true;	
		if(playScreen.state==GameState.RUNNING) {
		if (schussZeit >= schussUnterbrechung&&!(actor instanceof Bosstank)) {
			schussZeit = 0;
//			if (actor.getName()=="KI1") {
			
				if(actor.getSchussBol())act[3] = new Shoot(actor, playScreen);
			
//			}
//			else if (actor.getName()=="KI2") {
//				act[3] = new ShootKi2(actor, playScreen);
//			}
//			else if (actor.getName()=="KI3") {
//				act[3] = new ShootKi3(actor, playScreen);
//			}
//			else if (actor.getName()=="Ki") {
//				act[3] = new ShootKi1(actor, playScreen);
//			}
		}
		}
		

		
		
		return act;
	}

	}

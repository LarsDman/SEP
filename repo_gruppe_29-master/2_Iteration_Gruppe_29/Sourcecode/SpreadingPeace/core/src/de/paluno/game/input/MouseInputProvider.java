package de.paluno.game.input;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import de.paluno.game.gameobjects.Tank;
import de.paluno.game.input.actions.Action;
import de.paluno.game.input.actions.AimAt;
import de.paluno.game.input.actions.Drive;
import de.paluno.game.input.actions.Shoot;
import de.paluno.game.input.actions.Turn;
import de.paluno.game.screens.Gamemode;

public class MouseInputProvider extends java.lang.Object implements InputProvider {

	public static final float schussUnterbrechung = 0.3f;
	public static float schussZeit=0;

	public MouseInputProvider() {
		//0 vorne, 1 links, 2 hinten, 3 rechts, 4 links drehen, 5 rechts drehen, 6 schuss
	}

	public Action[] getInputs(Tank actor, Gamemode playScreen, ArrayList<String> settings) {
		Action[] act = new Action[4];
		boolean forwards = true;
	
		schussZeit += Gdx.graphics.getDeltaTime();
		
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
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && schussZeit >= schussUnterbrechung) {
			schussZeit = 0;
			act[3] = new Shoot(actor, playScreen);
		}
//		if (Gdx.input.isButtonPressed(Input.Buttons.MIDDLE)) {
//			act[2] = new AimTurn(actor, 2);
//		}
//		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
//			act[2] = new AimTurn(actor, -2);
//		}
		
		Vector3 t = playScreen.camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(), 1));
		
		act[2] = new AimAt(actor, new Vector2(t.x, t.y));
		
		return act;
	}
	
	
}
//The MouseInputProvider returns the actions that correspond to the inputs the user performs on the mouse and keyboard.
		// Called during the update-method of a tank. It should provide the tank with an
		// array of Action instances which define the actions the tank should perform on
		// the frame that this method is called. What actions the array contains is
		// determined by the specific implementation of this interface. The
		// KeyboardInputProvider for example provides the actions that correspond to the
		// keys the player currently presses on the keyboard.
		// actor - Reference to the tank which called this method.
		// playScreen - Reference to the current PlayScreen.
		// Returns Array of Actions the tank should perform on the current frame. If the
		// tank should perform no Actions, it is easiest to return an empty array and
		// not the null-reference.  
		// Wenn Drive > 0 bewegt sich actor nach vorne, wenn < 0 nach hinten
		// Wenn Turn > 0, dreht sich actor gegen den Uhrzeigersinn, wenn < 0 andersherum
		// Gleiches gilt für AimTurn, für 1.Iteration drehen sich Panzer und Aim gleich
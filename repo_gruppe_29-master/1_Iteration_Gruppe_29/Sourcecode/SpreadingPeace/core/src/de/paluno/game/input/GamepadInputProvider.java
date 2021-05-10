package de.paluno.game.input;

import static de.paluno.game.Constants.SPEED;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Xbox;

import de.paluno.game.Constants;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.input.actions.Action;
import de.paluno.game.input.actions.Drive;
import de.paluno.game.input.actions.Shoot;
import de.paluno.game.input.actions.Turn;
import de.paluno.game.screens.PlayScreen;

//The GamepadInputProvider returns the actions that correspond to the inputs the user performs on a gamepad.

public class GamepadInputProvider extends java.lang.Object implements InputProvider {

	private float y;
	private float x;
	Controller controller;

	public GamepadInputProvider() {
		// controller = Controllers.getControllers().first(); muss auskomentiert
		// bleiben, fehlermeldung falls kein Controller angeschlossen ist
	}

	public Action[] getInputs(Tank actor, PlayScreen playScreen) {
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

		Action act[] = new Action[5];
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
		if (Gdx.input.isKeyPressed(Keys.W) && y < Constants.BILDSCHIRMHOEHE - 55) {

			act[0] = new Drive(actor, 0, SPEED);
		} else if (Gdx.input.isKeyPressed(Keys.S) && x >= 0) {
			act[1] = new Drive(actor, 0, -SPEED);
		}

		// Wenn Turn > 0, dreht sich actor gegen den Uhrzeigersinn, wenn < 0 andersherum
		else if (Gdx.input.isKeyPressed(Keys.A) && y >= 0) {
			act[2] = new Turn(actor, 1);
		}

		else if (Gdx.input.isKeyPressed(Keys.D) && x < Constants.BILDSCHIRMBREITE - 55) {
			act[3] = new Turn(actor, -1);
		}

		else if (Gdx.input.isKeyPressed(Keys.SPACE) && x < Constants.BILDSCHIRMBREITE - 55) {
			act[4] = new Shoot(actor, playScreen);
		} else {
			act[0] = new Drive(actor, 0, 0); // act 0 ist die Aktion die ausgef�hrt wird, wenn keine Taste gedr�ckt wird
			act[0] = new Turn(actor, 0);
		}
		return act;

	}

}

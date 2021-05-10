package de.paluno.game.input;

import java.util.ArrayList;

import de.paluno.game.gameobjects.Tank;
import de.paluno.game.input.actions.Action;
import de.paluno.game.screens.Gamemode;
import de.paluno.game.screens.Deathmatch;

//The EmptyInputProvider always returns an empty array of type Action in its getInputs method. It is thereby the simplest form of artificial intelligence possible, as it never performs any actions.

public class EmptyInputProvider extends java.lang.Object implements InputProvider {

	public EmptyInputProvider() {

	}

	public Action[] getInputs(Tank actor, Gamemode playScreen, ArrayList<String> settings) {

		Action[] dummy = new Action[0];
		return dummy;
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

	}

}

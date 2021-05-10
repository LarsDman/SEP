package de.paluno.game.input;
//The InputProvider-interface is a layer of abstraction between the actions a tank can perform and the entity which decides on what action it should perform. Some implementations of this interface map the inputs the player performs (verbs) to the corresponding actions the tank should perform. Other implementations decide based on algorithms how the tank should act, basically a simple form of artificial intelligence.

import java.util.ArrayList;

//Every tank object holds a reference to an implementation of this interface, depending on how it should be decided what the tank does. In its update-method it calls the getInputs-method of said reference.

import de.paluno.game.gameobjects.Tank;
import de.paluno.game.input.actions.Action;
import de.paluno.game.screens.Gamemode;
import de.paluno.game.screens.Deathmatch;

public interface InputProvider {

	Action[] getInputs(Tank actor, Gamemode playScreen, ArrayList<String> settings);
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

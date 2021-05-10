package de.paluno.game.input.actions;

import de.paluno.game.gameobjects.Tank;

//Implementations of this abstract class represent the different actions a tank can perform and define how they are carried out. When instances of Tank call the getInputs-method of their respective InputProvider implementation in their update-method, they receive an array of Actions and call the act-method on every Action in the array.
//During the act-method, the Action needs to be carried out. Possible actions for the tanks include driving, aiming or shooting.

public abstract class Action extends java.lang.Object {

	protected Tank actor;
	// The tank that is carrying out the action. Supplied by the constructor.

	public Action(Tank actor) {
		this.actor = actor;
		// Instantiates a new Action. Usually Actions are instantiated anew on every
		// frame. Subclasses should provide further parameters to their constructors,
		// modifying the action in question.

		// actor - The tank that carries out this action.
	}

	public abstract void act(float delta);
	// On every frame, instances of Tank should call the act-method of every Action
	// returned by their respective InputProvider.
	// The act-method needs to be implemented by subclasses of Action. In order to
	// perform the Action in question, it should manipulate the attributes of the
	// actor-field which is the Tank that is supposed to perform the action.

	// delta - The time in seconds that has passed since the last frame.
}

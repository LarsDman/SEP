package de.paluno.game.input.actions;

import de.paluno.game.gameobjects.Tank;

//This Action should turn the Tank´s turret to aim at a specific point in the world.
//To achieve that, this Action needs to manipulate the aim-field of the Tank instance in question.

public class AimAt extends Action {

	private com.badlogic.gdx.math.Vector2 target;
	// The new target of the Tank´s turret. Supplied by the constructor.

	public AimAt(Tank actor, com.badlogic.gdx.math.Vector2 target) {
		super(actor);
		this.target = target;
		// actor - The tank that should carry out the action.
		// target - The new target of the Tank´s turret in world coordinates.
	}

	void act(float delta) {
		// On every frame, instances of Tank should call the act-method of every Action
		// returned by their respective InputProvider.
		// The act-method needs to be implemented by subclasses of Action. In order to
		// perform the Action in question, it should manipulate the attributes of the
		// actor-field which is the Tank that is supposed to perform the action.

		// delta - The time in seconds that has passed since the last frame.
	}

}

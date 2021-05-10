package de.paluno.game.input.actions;

import com.badlogic.gdx.Gdx;

import de.paluno.game.gameobjects.Tank;

//This Action should turn the Tank´s turret either clockwise or counterclockwise, depending on its turnPower-field which is supplied by the constructor.
//To turn the turret of the Tank, this Action needs to rotate the Vector2 referenced by the aim-field of the Tank. However, because this Action might be performed on every frame (for example, if the player holds down the respective key on the keyboard) it needs to account for frametimes and multiply them with the turnPower to determine the actual amount by which the turret turns.

public class AimTurn extends Action {

	private float turnPower;
	// How far the Tank´s turret should be turned.

	public AimTurn(Tank actor, float turnPower) {
		super(actor);
		this.turnPower = turnPower;
		act(Gdx.graphics.getDeltaTime());
		// actor - The tank that should carry out the action.
		// turnPower - Defines how far and in what direction the Tank´s turret should be
		// turned. If turnPower is positive, the turret will turn counterclockwise.
		// Clockwise, if turnPower is negative.

		// How far the turret turns depends on the absolute value of turnPower and on
		// the frametime of the current frame.
	}

	void act(float delta) {
		// On every frame, instances of Tank should call the act-method of every Action
		// returned by their respective InputProvider.
		// The act-method needs to be implemented by subclasses of Action. In order to
		// perform the Action in question, it should manipulate the attributes of the
		// actor-field which is the Tank that is supposed to perform the action.
		this.actor.setAim(this.actor.getAim().setAngle(turnPower));
		// delta - The time in seconds that has passed since the last frame.
	}

}

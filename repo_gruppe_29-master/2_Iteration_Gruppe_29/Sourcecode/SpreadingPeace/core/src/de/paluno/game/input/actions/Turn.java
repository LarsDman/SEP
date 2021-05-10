package de.paluno.game.input.actions;

import com.badlogic.gdx.Gdx;

import de.paluno.game.gameobjects.Tank;

public class Turn extends Action {

	private float turnPower;

	public Turn(Tank actor, float turnPower) {
		super(actor);
		this.turnPower = turnPower;
//		if (turnPower != 1 && turnPower != -1) {
//			act(Gdx.graphics.getDeltaTime());
//		} else
//			act2(Gdx.graphics.getDeltaTime());
		//act(Gdx.graphics.getDeltaTime());
	}

	public void act(float delta) {
		this.actor.getBody().setAngularVelocity(turnPower);
		//this.actor.getBody().setTransform(this.actor.getBody().getPosition(), (float) Math.toRadians(turnPower));
		//actor.getBody().getAngle()+
	}

	public void act2(float delta) {
		this.turnPower = turnPower;
		this.actor.getBody().setTransform(this.actor.getBody().getPosition(), (float) Math.toRadians(turnPower));
		//this.actor.getBody().setAngularVelocity(turnPower);
	}
}
//This Action should turn the Tank either clockwise or counterclockwise, depending on its turnPower-field which is supplied by the constructor.

//To turn the Tank, this Action needs to apply torque to the Body of the Tank. However, because this Action might be performed on every frame (for example, if the player holds down the respective key on the keyboard) it needs to account for frametimes and multiply them with the turnPower to determine the actual amount by which the turret turns.
//How far the Tank should be turned.
//actor - The tank that should carry out the action.
// turnPower - Defines how far and in what direction the Tank should be turned.
// If turnPower is positive, the Tank will turn counterclockwise. Clockwise, if
// turnPower is negative.

// How far the Tank turns depends on the absolute value of turnPower and on the
// frametime of the current frame.

//On every frame, instances of Tank should call the act-method of every Action
// returned by their respective InputProvider.
// The act-method needs to be implemented by subclasses of Action. In order to
// perform the Action in question, it should manipulate the attributes of the
// actor-field which is the Tank that is supposed to perform the action.
//delta - The time in seconds that has passed since the last frame
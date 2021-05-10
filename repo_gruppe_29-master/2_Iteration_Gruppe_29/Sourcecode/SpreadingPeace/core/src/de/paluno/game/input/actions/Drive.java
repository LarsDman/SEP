package de.paluno.game.input.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.gameobjects.Tank;

//This action should let the Tank drive either forwards or backwards, depending on the power-field supplied by the constructor.
//To make the Tank drive, this Action needs to manipulate its body, applying a force to the direction in which it´s facing. However, because this Action might be carried out on every frame (for example, if the player holds down the respective key on the keyboard) it needs to account for frametimes and multiply them with the power-field to determine the actual amount of force to be applied.

public class Drive extends Action {

	private float power;

	public Drive(Tank actor, float power) {
		super(actor);
		this.power = power;
	}

	public void act(float delta) {
		//this.actor.getBody().setLinearVelocity(power);
		//this.actor.getBody().setLinearVelocity(this.actor.getBody().getAngle());
		this.actor.getBody().setLinearVelocity(actor.getDirection().scl(power, power));
	}

}

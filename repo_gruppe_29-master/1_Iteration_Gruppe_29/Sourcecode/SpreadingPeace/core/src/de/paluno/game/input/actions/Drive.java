package de.paluno.game.input.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import de.paluno.game.gameobjects.Tank;

//This action should let the Tank drive either forwards or backwards, depending on the power-field supplied by the constructor.
//To make the Tank drive, this Action needs to manipulate its body, applying a force to the direction in which it´s facing. However, because this Action might be carried out on every frame (for example, if the player holds down the respective key on the keyboard) it needs to account for frametimes and multiply them with the power-field to determine the actual amount of force to be applied.

public class Drive extends Action {

	private float power;
	private float powerVertical;
	private float powerHorizontal;
	// How strongly the Tank should be accelerated.

	public Drive(Tank actor, float powerHorizontal, float powerVertical) {
		super(actor);
		this.powerHorizontal = powerHorizontal;
		this.powerVertical = powerVertical;
		act(Gdx.graphics.getDeltaTime());
		// actor - The tank that should carry out the action.
		// power - Defines how strongly the Tank should be accelerated and in what
		// direction. If power is positive, the Tank will drive forwards. Backwards, if
		// power is negative.

		// How strongly the Tank is accelerated depends on the absolute value of power
		// and on the frametime of the current frame.
	}

	void act(float delta) {
		// On every frame, instances of Tank should call the act-method of every Action
		// returned by their respective InputProvider.
		// The act-method needs to be implemented by subclasses of Action. In order to
		// perform the Action in question, it should manipulate the attributes of the
		// actor-field which is the Tank that is supposed to perform the action.
		// delta - The time in seconds that has passed since the last frame.
//		int horizontalForce =0;
//		int verticalForce=0;
//		if(Gdx.input.isKeyPressed(Keys.LEFT))
//			horizontalForce-=1;
//		if(Gdx.input.isKeyPressed(Keys.RIGHT))
//			horizontalForce+=1;
//		if(Gdx.input.isKeyPressed(Keys.UP))
//			verticalForce+=1;
//		if(Gdx.input.isKeyPressed(Keys.DOWN))
//			verticalForce-=1;
//		this.actor.getBody().setLinearVelocity(horizontalForce*5, verticalForce*5 );
		// fahre nach links
//		if (Gdx.input.isKeyPressed(Keys.A)) {
		this.actor.getBody().setLinearVelocity(powerHorizontal, powerVertical);
//		}
//			fahre nach rechts
		// if (Gdx.input.isKeyPressed(Keys.D) {
		// this.tank.body.applyLinearImpulse ( power , 0 , true) ;
		// }
		// fahre nach oben
		// if (Gdx.input.isKeyPressed(Keys.W) {
		// this.tank.body.applyLinearImpulse ( 0 , power , true) ;
		// }
		// fahre nach unten
		// if (Gdx.input.isKeyPressed(Keys.S) {
		// this.tank.body.applyLinearImpulse ( 0 , -power , true) ;
		// }
		// true ist daf�r da den K�rper zu "wecken", um Performance zu verbessern kann
		// es auch auf false gelegt werden damit der K�rper "schl�ft" wenn er
		// nicht gebraucht wird. Ich denke nicht, dass es in diesem Kontext n�tig wird
		// Performance zu optimieren.

	}

}

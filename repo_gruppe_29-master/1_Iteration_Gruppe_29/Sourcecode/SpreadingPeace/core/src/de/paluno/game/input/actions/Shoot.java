package de.paluno.game.input.actions;

import com.badlogic.gdx.math.Vector2;

import de.paluno.game.gameobjects.F_L_O_W_E_R;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.screens.PlayScreen;

//This Action should try to let the Tank shoot out a new F.L.O.W.E.R in the direction it is aiming. Because Tanks should not be able to shoot on every frame, they have a timestamp-attribute which shows when they last shot. If that occurred only in the near past, this Action should do nothing. However, if this Action does result in a shot, it should set the Tank´s timestamp-attribute to the current system-time.

public class Shoot extends Action {

	private PlayScreen playScreen;
	// Reference to the current PlayScreen.

	public Shoot(Tank actor, PlayScreen playScreen) {
		super(actor);
		this.playScreen = playScreen;
		this.act(1);
		// actor - The tank that should carry out the action.
		// playScreen - Reference to the current PlayScreen.
	}
	
	void act(float delta) {
		// On every frame, instances of Tank should call the act-method of every Action
		// returned by their respective InputProvider.
		// The act-method needs to be implemented by subclasses of Action. In order to
		// perform the Action in question, it should manipulate the attributes of the
		// actor-field which is the Tank that is supposed to perform the action.

		// delta - The time in seconds that has passed since the last frame.
		// flowers.add(myFlower);
		// F_L_O_W_E_R myFlower = new F_L_O_W_E_R(playScreen, actor.getBody().getPosition(), actor.getAim());
		playScreen.getFlowerList().add(new F_L_O_W_E_R(playScreen, actor.getBody().getPosition(), actor.getAim()));
	}

}

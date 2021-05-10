package de.paluno.game.input;

import static de.paluno.game.Constants.SPEED;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import de.paluno.game.Constants;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.input.actions.Action;
import de.paluno.game.input.actions.AimTurn;
import de.paluno.game.input.actions.Drive;
import de.paluno.game.input.actions.Shoot;
import de.paluno.game.input.actions.Turn;
import de.paluno.game.screens.PlayScreen;

public class KeyboardInputProvider extends java.lang.Object implements InputProvider {
	


	public KeyboardInputProvider() {
		
	}

	public Action[] getInputs(Tank actor, PlayScreen playScreen) {
		Action[] act = new Action[5];
		
		// Wenn Drive > 0 bewegt sich actor nach vorne, wenn < 0 nach hinten
		// Wenn Turn > 0, dreht sich actor gegen den Uhrzeigersinn, wenn < 0 andersherum
		// Gleiches gilt für AimTurn, für 1.Iteration drehen sich Panzer und Aim gleich
		if (Gdx.input.isKeyPressed(Keys.W)) {
			act[0] = new Drive(actor, 0, SPEED);
			act[1] = new Turn(actor, 0);
			act[2] = new AimTurn(actor, 90);
			if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				act[4] = new Shoot(actor, playScreen);
			}
		} else if (Gdx.input.isKeyPressed(Keys.S)) {
			act[0] = new Drive(actor, 0, -SPEED);
			act[1] = new Turn(actor, 180);
			act[2] = new AimTurn(actor, 270);
			if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				act[4] = new Shoot(actor, playScreen);
			}
		} else if (Gdx.input.isKeyPressed(Keys.A)) {
			act[0] = new Drive(actor, -SPEED, 0);
			act[1] = new Turn(actor, 90);
			act[2] = new AimTurn(actor, 180);
			if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				act[4] = new Shoot(actor, playScreen);
			}
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			act[0] = new Drive(actor, SPEED, 0);
			act[1] = new Turn(actor, 270);
			act[2] = new AimTurn(actor, 0);
			if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				act[4] = new Shoot(actor, playScreen);
			}
		} else if (Gdx.input.isKeyPressed(Keys.O)) {
			act[1] = new Turn(actor, 1);
		} else if (Gdx.input.isKeyPressed(Keys.P)) {
			act[1] = new Turn(actor, -1);
		} else if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			act[4] = new Shoot(actor, playScreen);
		} else {
			act[0] = new Drive(actor, 0, 0); // wenn nichts passiert wird der Act[0] einfach �berschrieben
			actor.getBody().setAngularVelocity(0); //sorgt daf�r, dass der Panzer sich aufh�rt zu drehen, wenn kein act[1-4] ausgef�hrt wird
		}
		return act;
	}
}

//The KeyboardInputProvider returns the actions that correspond to the inputs the user performs on the keyboard.
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

package de.paluno.game.input;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.input.actions.Action;
import de.paluno.game.input.actions.AimTurn;
import de.paluno.game.input.actions.Drive;
import de.paluno.game.input.actions.Shoot;
import de.paluno.game.input.actions.Turn;
import de.paluno.game.screens.Gamemode;

//The GamepadInputProvider returns the actions that correspond to the inputs the user performs on a gamepad.

public class GamepadInputProvider extends java.lang.Object implements InputProvider{
	public static final float schussUnterbrechung = 0.3f;
	public static float schussZeit=0;
	Controller pad;
	public static final float SCHUSSWARTEZEIT=0.3f;
	float schusszeit;
	public GamepadInputProvider() {
		schusszeit=0;
	}

	public Action[] getInputs(Tank actor, Gamemode playScreen, ArrayList<String> settings) {
		Controller pad = Controllers.getControllers().first();
		Action[] act = new Action[5];
		boolean forwards = true;
		schusszeit+=Gdx.graphics.getDeltaTime();
		
		if(settings.get(0) == "1") {
			if(pad.getPov(0)==PovDirection.north) {
				act[0] = new Drive(actor, actor.getVelocity());
				forwards = true;
			}
			if(pad.getPov(0)==PovDirection.south) {
				act[0] = new Drive(actor, actor.getVelocity()*-1);
				forwards = false;
				if(pad.getPov(0)==PovDirection.west  && forwards == false) {
					act[1] = new Turn(actor, -1);
				}
				if(pad.getPov(0)==PovDirection.east  && forwards == false) {
					act[1] = new Turn(actor, 1);
				}
			}
			if(pad.getPov(0)==PovDirection.west  && forwards == true) {
				act[1] = new Turn(actor, 1);
			}
			if(pad.getPov(0)==PovDirection.east  && forwards == true) {
				act[1] = new Turn(actor, -1);
			}
			if(pad.getButton(5)) {
				act[2] = new AimTurn(actor, -1);
			}
			if(pad.getButton(4)) {
				act[2] = new AimTurn(actor, 1);
			}
			if(pad.getButton(0) && schusszeit>= SCHUSSWARTEZEIT) {
				schusszeit=0;
				act[3] = new Shoot(actor, playScreen);
			}
		}else if(settings.get(0) == "2") {
			if(pad.getButton(3)) {
				act[0] = new Drive(actor, actor.getVelocity());
				forwards = true;
			}
			if(pad.getButton(0)) {
				act[0] = new Drive(actor, actor.getVelocity()*-1);
				forwards = false;
				if(pad.getButton(2)  && forwards == false) {
					act[1] = new Turn(actor, -1);
				}
				if(pad.getButton(1)  && forwards == false) {
					act[1] = new Turn(actor, 1);
				}
			}
			if(pad.getButton(2)  && forwards == true) {
				act[1] = new Turn(actor, 1);
			}
			if(pad.getButton(1)  && forwards == true) {
				act[1] = new Turn(actor, -1);
			}
			if(pad.getButton(7)) {
				act[2] = new AimTurn(actor, -1);
			}
			if(pad.getButton(6)) {
				act[2] = new AimTurn(actor, 1);
			}
			if(pad.getButton(5) && schusszeit>= SCHUSSWARTEZEIT) {
				schusszeit=0;
				act[3] = new Shoot(actor, playScreen);
			}
		}
		
		
		return act;

	}


}

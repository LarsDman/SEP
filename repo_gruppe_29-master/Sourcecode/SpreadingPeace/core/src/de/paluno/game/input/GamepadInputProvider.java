package de.paluno.game.input;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;

import de.paluno.game.GameState;
import de.paluno.game.gameobjects.Blumenauswahl;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.input.actions.Action;
import de.paluno.game.input.actions.AimTurn;
import de.paluno.game.input.actions.Drive;
import de.paluno.game.input.actions.Shoot;
import de.paluno.game.input.actions.Turn;
import de.paluno.game.screens.Gamemode;
import de.paluno.game.screens.MenuScreen;
import de.paluno.game.screens.Schatzkammer;
import de.paluno.game.screens.Survival_mit_Geld;

//The GamepadInputProvider returns the actions that correspond to the inputs the user performs on a gamepad.

public class GamepadInputProvider extends java.lang.Object implements InputProvider{
	HashMap<String, Integer> keyhash;
	public static final float schussUnterbrechung = 0.3f;
	public static float schussZeit=0;
	Controller pad;
	public static final float SCHUSSWARTEZEIT=0.3f;
	float schusszeit, blumenzeit, pausezeit;
	short currentblume=0;
	public GamepadInputProvider() {
		keyhash = new HashMap<String, Integer>();
		keyhash.put("a",0);
		keyhash.put("A",0);
		keyhash.put("B",1);
		keyhash.put("b",1);
		keyhash.put("x",2);
		keyhash.put("X",2);
		keyhash.put("Y",3);
		keyhash.put("y",3);
		keyhash.put("RT",5);
		keyhash.put("rt",5);
		keyhash.put("lt",4);
		keyhash.put("LT",4);
		keyhash.put("LX",6); //links von xbox logo, selbst ausgedachter name
		keyhash.put("RX",7); //rechts von xbox logo, selbst ausgedachter name
		keyhash.put("RSdown",9); //rechten stick runterdruecken, selbst ausgedachter name
		keyhash.put("LSdown",8); //linken stick runterdruecken, selbst ausgedachter name
		keyhash.put("lx",6); //links von xbox logo, selbst ausgedachter name
		keyhash.put("rx",7); //rechts von xbox logo, selbst ausgedachter name
		keyhash.put("rsdown",9); //rechten stick runterdruecken, selbst ausgedachter name
		keyhash.put("lsdown",8); //linken stick runterdruecken, selbst ausgedachter name
		schusszeit=0f;
		blumenzeit =0f;
		pausezeit=0f;	}
	
	
	public Action[] getInputs(Tank actor, Gamemode playScreen, ArrayList<String> settings) {

		Controller pad = Controllers.getControllers().first();

		Action[] act = new Action[5];
		boolean forwards = true;
		schusszeit+=Gdx.graphics.getDeltaTime();
		blumenzeit+=Gdx.graphics.getDeltaTime();
		pausezeit+=Gdx.graphics.getDeltaTime();
		
			//Blumenauswahl code neu 3. Iteration
			if(pad.getButton(keyhash.get(settings.get(4)))&&blumenzeit>=SCHUSSWARTEZEIT && playScreen.state == GameState.RUNNING)
			{
				if(currentblume==0) {actor.setFlower(Blumenauswahl.SPLITTEDFLOWER); currentblume =3;}
				else if(currentblume ==1){actor.setFlower(Blumenauswahl.NORMALFLOWER); currentblume =0;}
				else if(currentblume==2) {actor.setFlower(Blumenauswahl.FASTFLOWER); currentblume =1;}
				else if(currentblume ==3){actor.setFlower(Blumenauswahl.BOUNCEFLOWER); currentblume =2;}
				blumenzeit=0f;
			}
			if (playScreen instanceof Survival_mit_Geld) {
				actor.setFlower(Schatzkammer.getGold());
			}
			else if(pad.getButton(keyhash.get(settings.get(3)))&&blumenzeit>=SCHUSSWARTEZEIT && playScreen.state == GameState.RUNNING)
			{
				if(currentblume==0) {actor.setFlower(Blumenauswahl.FASTFLOWER); currentblume =1;}
				else if(currentblume ==1){actor.setFlower(Blumenauswahl.BOUNCEFLOWER); currentblume =2;}
				else if(currentblume==2) {actor.setFlower(Blumenauswahl.SPLITTEDFLOWER); currentblume =3;}
				else if(currentblume ==3){actor.setFlower(Blumenauswahl.NORMALFLOWER); currentblume =0;}
				blumenzeit=0f;
			}
		if(settings.get(0).contains("dpad")||settings.get(0).contains("Dpad")||settings.get(0).contains("dPad")||settings.get(0).contains("DPAD")) {
			if(pad.getPov(0)==PovDirection.north&& playScreen.state == GameState.RUNNING) {
				act[0] = new Drive(actor, actor.getVelocity());
				forwards = true;
			}
			if(pad.getPov(0)==PovDirection.south&& playScreen.state == GameState.RUNNING) {
				act[0] = new Drive(actor, actor.getVelocity()*-1);
				forwards = false;
				if(pad.getPov(0)==PovDirection.west  && forwards == false) {
					act[1] = new Turn(actor, -1);
				}
				if(pad.getPov(0)==PovDirection.east  && forwards == false) {
					act[1] = new Turn(actor, 1);
				}
			}
			if(pad.getPov(0)==PovDirection.west  && forwards == true&& playScreen.state == GameState.RUNNING) {
				act[1] = new Turn(actor, 1);
			}
			if(pad.getPov(0)==PovDirection.east  && forwards == true&& playScreen.state == GameState.RUNNING) {
				act[1] = new Turn(actor, -1);
			}
		}
		else if(settings.get(0).contains("LS")||settings.get(0).contains("ls")) //linker stick
		{
			if(pad.getAxis(0)<-0.5f&& playScreen.state == GameState.RUNNING) {
				act[0] = new Drive(actor, actor.getVelocity());
				forwards = true;
			}
			if(pad.getAxis(0)>0.5f&& playScreen.state == GameState.RUNNING) {
				act[0] = new Drive(actor, actor.getVelocity()*-1);
				forwards = false;
				if(pad.getAxis(1)>0.5f  && forwards == false) {
					act[1] = new Turn(actor, -1);
				}
				if(pad.getAxis(1)<-0.5f  && forwards == false) {
					act[1] = new Turn(actor, 1);
				}
			}
			if(pad.getAxis(1)>0.5f  && forwards == true&& playScreen.state == GameState.RUNNING) {
				act[1] = new Turn(actor, 1);
			}
			if(pad.getAxis(1)<-0.5f  && forwards == true&& playScreen.state == GameState.RUNNING) {
				act[1] = new Turn(actor, -1);
			}
		}
		else // rechter stick
		{
			if(pad.getAxis(2)<-0.5f&& playScreen.state == GameState.RUNNING) {
				act[0] = new Drive(actor, actor.getVelocity());
				forwards = true;
			}
			if(pad.getAxis(2)>0.5f&& playScreen.state == GameState.RUNNING) {
				act[0] = new Drive(actor, actor.getVelocity()*-1);
				forwards = false;
				if(pad.getAxis(3)>0.5f  && forwards == false) {
					act[1] = new Turn(actor, -1);
				}
				if(pad.getAxis(3)<-0.5f  && forwards == false) {
					act[1] = new Turn(actor, 1);
				}
			}
			if(pad.getAxis(3)>0.5f  && forwards == true&& playScreen.state == GameState.RUNNING) {
				act[1] = new Turn(actor, 1);
			}
			if(pad.getAxis(3)<-0.5f  && forwards == true&& playScreen.state == GameState.RUNNING) {
				act[1] = new Turn(actor, -1);
			}
		} //ende des LS, RS und dpad if blocks
		
			if(pad.getButton(keyhash.get(settings.get(1)))&& playScreen.state == GameState.RUNNING) {
				act[2] = new AimTurn(actor, -1);
			}
			if(pad.getButton(keyhash.get(settings.get(2)))&& playScreen.state == GameState.RUNNING) {
				act[2] = new AimTurn(actor, 1);
			}
			if (pad.getButton(keyhash.get(settings.get(5))) && schusszeit>= SCHUSSWARTEZEIT && playScreen.state == GameState.RUNNING) {
					act[3] = new Shoot(actor, playScreen);
					schusszeit = 0f;
				}

		if(playScreen.state==GameState.RUNNING) 
		{
			if (pad.getButton(keyhash.get(settings.get(6)))) playScreen.state = GameState.PAUSED;
			pausezeit = 0f;
		} 
		else
			{if  (pad.getButton(keyhash.get(settings.get(6)))) playScreen.state = GameState.RUNNING; pausezeit = 0f;}
		
		//System.out.println(settings.get(6));
		return act;

	}


}

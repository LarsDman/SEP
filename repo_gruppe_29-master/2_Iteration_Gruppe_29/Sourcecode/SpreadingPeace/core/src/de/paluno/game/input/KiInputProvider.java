package de.paluno.game.input;

import static de.paluno.game.Constants.SPEED;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;

import de.paluno.game.Constants;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.input.actions.Action;
import de.paluno.game.input.actions.AimTurn;
import de.paluno.game.input.actions.Drive;
import de.paluno.game.input.actions.Shoot;
import de.paluno.game.input.actions.Turn;
import de.paluno.game.screens.Gamemode;
import de.paluno.game.screens.Deathmatch;

public class KiInputProvider extends java.lang.Object implements InputProvider {
	
	public static final float schussUnterbrechung = 1.5f;
	public static float schussZeit=0;

	public KiInputProvider() {
	}

	public Action[] getInputs(Tank actor, Gamemode playScreen, ArrayList<String> settings) {
		
		schussZeit += Gdx.graphics.getDeltaTime();
	
		Action[] act = new Action[5];
		boolean forwards = true;	
		
		if (schussZeit >= schussUnterbrechung) {
			schussZeit = 0;
			act[3] = new Shoot(actor, playScreen);
		}
		
		return act;
	}
	}

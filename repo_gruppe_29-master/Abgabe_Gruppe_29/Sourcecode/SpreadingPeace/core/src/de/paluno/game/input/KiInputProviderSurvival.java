package de.paluno.game.input;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.GameState;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.gameobjects.TankKi;
import de.paluno.game.input.actions.Action;
import de.paluno.game.input.actions.AimAt;
import de.paluno.game.input.actions.Shoot;
import de.paluno.game.screens.Gamemode;

public class KiInputProviderSurvival extends java.lang.Object implements InputProvider {
	public static final float schussUnterbrechung = 3f;
	public static float schussZeit = 0;
	private TankKi actor;

	public KiInputProviderSurvival() {

	}

	@Override
	public Action[] getInputs(Tank actor, Gamemode playScreen, ArrayList<String> settings) {
		this.actor = (TankKi) actor;
		schussZeit += Gdx.graphics.getDeltaTime();

		Action[] act = new Action[5];
		if (playScreen.state == GameState.RUNNING) {
			if (schussZeit >= schussUnterbrechung) {
				schussZeit = 0;
				if (((TankKi) actor).haslineofsight()) {
					act[3] = new Shoot(actor, playScreen);
					act[2] = new AimAt(actor, new Vector2(playScreen.getSpielerTank().getPosition().x,
							playScreen.getSpielerTank().getPosition().y));
					return act;
				}
			}
		}
		return act;

	}
}
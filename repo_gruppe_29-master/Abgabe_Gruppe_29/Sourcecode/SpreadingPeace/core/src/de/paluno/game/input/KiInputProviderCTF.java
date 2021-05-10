package de.paluno.game.input;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.GameState;
import de.paluno.game.gameobjects.FlagHolder;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.gameobjects.TankKi;
import de.paluno.game.input.actions.Action;
import de.paluno.game.input.actions.AimAt;
import de.paluno.game.input.actions.Shoot;
import de.paluno.game.screens.CaptureTheFlag;
import de.paluno.game.screens.Deathmatch;
import de.paluno.game.screens.Gamemode;
import de.paluno.game.screens.MenuScreen;

public class KiInputProviderCTF extends java.lang.Object implements InputProvider {
	public static final float schussUnterbrechung = 1.5f;
	public static float schussZeit = 0;
	private static TankKi actor;
	public static double PlayerEntfernung = 500000;
	public static double ZielEntfernungKi = 500000;

	public KiInputProviderCTF() {

	}

	@Override
	public Action[] getInputs(Tank actor, Gamemode playScreen, ArrayList<String> settings) {
		this.actor = (TankKi)actor;
		schussZeit += Gdx.graphics.getDeltaTime();
		Action[] act = new Action[5];
		boolean forwards = true;
		if (playScreen.state == GameState.RUNNING  && actor.getBody().isActive()==true) { // && actor.getBody().isActive()==true von Robin
			if (schussZeit >= schussUnterbrechung && Ziel(actor) != actor) {
				schussZeit = 0;
					act[3] = new Shoot(actor, playScreen);
					act[2] = new AimAt(actor, new Vector2(Ziel(actor).getPosition().x , Ziel(actor).getPosition().y ));
			}
		}

		return act;
	}
	public static Tank Ziel(Tank actor) {
		int ZielPl = naechsterTank(actor);
		int ZielKi = naechsterTankKi(actor);
		if (PlayerEntfernung < ZielEntfernungKi) {
			return CaptureTheFlag.gettanks()[ZielPl];
		} else {
			if (ZielEntfernungKi != 500000) {
				return CaptureTheFlag.gettanksKi()[ZielKi];
			} else
				return actor;
		}
	}


	public static int naechsterTank(Tank actor) {
		PlayerEntfernung = 500000;
		int ZielPl = 0;

		if (CaptureTheFlag.gettanks()[1] != null && !TankInVisible(actor, CaptureTheFlag.gettanks()[1])) {

			if (TankInVisible(actor, CaptureTheFlag.gettanks()[0]) == false
					&& CaptureTheFlag.gettanks()[0].getTeam() != actor.getTeam()) {
				PlayerEntfernung = Math.pow(CaptureTheFlag.gettanks()[0].getPosition().x - actor.getPosition().x, 2)
						+ Math.pow(CaptureTheFlag.gettanks()[0].getPosition().y - actor.getPosition().y, 2);
			}
			if ((Math.pow(CaptureTheFlag.gettanks()[1].getPosition().x - actor.getPosition().x, 2)
					+ Math.pow(CaptureTheFlag.gettanks()[1].getPosition().y - actor.getPosition().y, 2)) < PlayerEntfernung
					&& CaptureTheFlag.gettanks()[1].getHealth() > 0
					&& CaptureTheFlag.gettanks()[1].getTeam() != actor.getTeam()) {
				PlayerEntfernung = Math.pow(CaptureTheFlag.gettanks()[1].getPosition().x - actor.getPosition().x, 2)
						+ Math.pow(CaptureTheFlag.gettanks()[1].getPosition().y - actor.getPosition().y, 2);
				ZielPl = 1;
			} else
				ZielPl = 0;
		} else {
			if (TankInVisible(actor, CaptureTheFlag.gettanks()[0]) == false
					&& CaptureTheFlag.gettanks()[0].getTeam() != actor.getTeam()) {
				PlayerEntfernung = Math.pow(CaptureTheFlag.gettanks()[0].getPosition().x - actor.getPosition().x, 2)
						+ Math.pow(CaptureTheFlag.gettanks()[0].getPosition().y - actor.getPosition().y, 2);
			}
		}

		return ZielPl;
	}

	public static int naechsterTankKi(Tank actor) {
		int ZielKi = 0;
		ZielEntfernungKi = 500000;

		for (int x = 0; x < 3; x++) {
			if (CaptureTheFlag.gettanksKi()[x] != null && CaptureTheFlag.gettanksKi()[x] != actor
					&& !TankInVisible(actor, CaptureTheFlag.gettanksKi()[x])) {
				if ((Math.pow(CaptureTheFlag.gettanksKi()[x].getPosition().x - actor.getPosition().x, 2) + Math
						.pow(CaptureTheFlag.gettanksKi()[x].getPosition().y - actor.getPosition().y, 2)) < ZielEntfernungKi
						&& CaptureTheFlag.gettanksKi()[x].getHealth() > 0
						&& CaptureTheFlag.gettanksKi()[x].getTeam() != actor.getTeam()) {
					ZielEntfernungKi = Math.pow(CaptureTheFlag.gettanksKi()[x].getPosition().x - actor.getPosition().x, 2)
							+ Math.pow(CaptureTheFlag.gettanksKi()[x].getPosition().y - actor.getPosition().y, 2);
					ZielKi = x;
				}
			}

		}
		return ZielKi;
	}

	
	public static boolean TankInVisible(Tank actor, Tank Player) {
		double xAbstand = actor.getPosition().x - Player.getPosition().x;
		double yAbstand = actor.getPosition().y - Player.getPosition().y;
		float xAktuell = Player.getPosition().x;
		float yAktuell = Player.getPosition().y;
		boolean InVisible = false;
		if (!(Player.getBody().isActive())){ //für inactivetanks
			return true;					//
		}
		for (int i = 0; i < 25; i++) {

			int count = 0;
			while (CaptureTheFlag.getObjektFix().size() > count) {

				if (CaptureTheFlag.getObjektFix().get(count).getBody() != Player.getBody()
						&& CaptureTheFlag.getObjektFix().get(count).getBody() != actor.getBody()
						&& CaptureTheFlag.getObjektFix().get(count).testPoint(xAktuell, yAktuell) == true) {
					InVisible = true;
				}
				if (InVisible == true) {
					count = CaptureTheFlag.getObjektFix().size();
				}
				count++;
			}
			xAktuell += xAbstand / 25;
			yAktuell += yAbstand / 25;
			if (InVisible) {
				i = 25;
			}
		}

		return InVisible;

	}

}
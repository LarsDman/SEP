package de.paluno.game.input;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import de.paluno.game.GameState;
import de.paluno.game.gameobjects.Blumenauswahl;
import de.paluno.game.gameobjects.DummyBody;
import de.paluno.game.gameobjects.Ghost;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.gameobjects.TankKi;
import de.paluno.game.input.actions.Action;
import de.paluno.game.input.actions.AimAt;
import de.paluno.game.input.actions.Drive;
import de.paluno.game.input.actions.Shoot;
import de.paluno.game.screens.Deathmatch;
import de.paluno.game.screens.Gamemode;
import de.paluno.game.screens.MenuScreen;

public class KiInputProviderDM extends java.lang.Object implements InputProvider {
	public static final float schussUnterbrechung = 1.5f;
	private static float schussZeit = 0;
	private static TankKi actor;
	private static Gamemode playScreen;
	
	

	public KiInputProviderDM() {

	}

	@Override
	public Action[] getInputs(Tank actor, Gamemode playScreen, ArrayList<String> settings) {
		this.actor = (TankKi)actor;
		this.playScreen = playScreen;
		schussZeit += Gdx.graphics.getDeltaTime();
		Action[] act = new Action[5];
		boolean forwards = true;
		if (playScreen.state == GameState.RUNNING) {
			if (schussZeit >= schussUnterbrechung && Ziel(actor) != actor) {
				
				schussZeit = 0;
					act[3] = new Shoot(actor, playScreen);
					act[2] = new AimAt(actor, new Vector2(Ziel(actor).getPosition().x , Ziel(actor).getPosition().y ));
			}					
		}
		KiBlumenauswahl(actor);
		

		return act;
	}

	public static Tank Ziel(Tank actor) {
		int ZielPl = naechsterTank(actor);
		int ZielKi = naechsterTankKi(actor);
		if (actor.getPlayerEntfernung() < actor.getZielEntfernungKi()) {
			actor.setZielPastPosX(Deathmatch.gettanks()[ZielPl].getPosition().x);
			actor.setZielPastPosY(Deathmatch.gettanks()[ZielPl].getPosition().y);
			return Deathmatch.gettanks()[ZielPl];
		} else {
			if (actor.getZielEntfernungKi() < actor.getPlayerEntfernung()) {
				actor.setZielPastPosX(Deathmatch.gettanksKi()[ZielKi].getPosition().x);
				actor.setZielPastPosY(Deathmatch.gettanksKi()[ZielKi].getPosition().y);
				return Deathmatch.gettanksKi()[ZielKi];
			} else

				return actor;
		}
	}

	public static int naechsterTank(Tank actor) {
		int ZielPl = 0;
		actor.setPlayerEntfernung(500000f);

		if (Deathmatch.gettanks()[1] != null && Deathmatch.gettanks()[1].getHealth() > -1 && 
				Deathmatch.gettanks()[1].getTeam() != actor.getTeam() && !TankInVisible(actor, Deathmatch.gettanks()[1])) {

			if (Deathmatch.gettanks()[0] != null && Deathmatch.gettanks()[0].getHealth() > -1 &&
					Deathmatch.gettanks()[0].getTeam() != actor.getTeam() && !TankInVisible(actor, Deathmatch.gettanks()[0])) {
				actor.setPlayerEntfernung (Math.pow(Deathmatch.gettanks()[0].getPosition().x - actor.getPosition().x, 2)
						+ Math.pow(Deathmatch.gettanks()[0].getPosition().y - actor.getPosition().y, 2));
			}
			if ((Math.pow(Deathmatch.gettanks()[1].getPosition().x - actor.getPosition().x, 2)
					+ Math.pow(Deathmatch.gettanks()[1].getPosition().y - actor.getPosition().y, 2)) < actor.getPlayerEntfernung()
					&& Deathmatch.gettanks()[1].getHealth() > -1
					&& Deathmatch.gettanks()[1].getTeam() != actor.getTeam()) {
				actor.setPlayerEntfernung (Math.pow(Deathmatch.gettanks()[1].getPosition().x - actor.getPosition().x, 2)
						+ Math.pow(Deathmatch.gettanks()[1].getPosition().y - actor.getPosition().y, 2));
				ZielPl = 1;
			}
		}
		else {
			if (Deathmatch.gettanks()[0] != null && Deathmatch.gettanks()[0].getHealth() > -1 &&
					Deathmatch.gettanks()[0].getTeam() != actor.getTeam() && !TankInVisible(actor, Deathmatch.gettanks()[0])) {
				actor.setPlayerEntfernung (Math.pow(Deathmatch.gettanks()[0].getPosition().x - actor.getPosition().x, 2)
						+ Math.pow(Deathmatch.gettanks()[0].getPosition().y - actor.getPosition().y, 2));
			}
		}

		return ZielPl;
	}

	public static int naechsterTankKi(Tank actor) {
		int ZielKi = 0;
		actor.setZielEntfernungKi(500000f);
		

		for (int x = 0; x < 4; x++) {
			if (Deathmatch.gettanksKi()[x] != null && Deathmatch.gettanksKi()[x] != actor && Deathmatch.gettanksKi()[x].getHealth() > -1
					&& !TankInVisible(actor, Deathmatch.gettanksKi()[x])) {
				if ((Math.pow(Deathmatch.gettanksKi()[x].getPosition().x - actor.getPosition().x, 2) + Math
						.pow(Deathmatch.gettanksKi()[x].getPosition().y - actor.getPosition().y, 2)) < actor.getZielEntfernungKi()
						&& Deathmatch.gettanksKi()[x].getHealth() > -1
						&& Deathmatch.gettanksKi()[x].getTeam() != actor.getTeam()) {
					actor.setZielEntfernungKi(Math.pow(Deathmatch.gettanksKi()[x].getPosition().x - actor.getPosition().x, 2)
							+ Math.pow(Deathmatch.gettanksKi()[x].getPosition().y - actor.getPosition().y, 2));
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
		for (int i = 0; i < 25; i++) {

			int count = 0;
			
			while (Deathmatch.getObjektFix().size() > count) {

				if (Deathmatch.getObjektFix().get(count).getBody() != Player.getBody()
						&& Deathmatch.getObjektFix().get(count).getBody() != actor.getBody()
						&& Deathmatch.getObjektFix().get(count).testPoint(xAktuell, yAktuell) == true) {
					InVisible = true;
				}
				if (InVisible == true) {
					count = Deathmatch.getObjektFix().size();
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
	
	private void KiBlumenauswahl(Tank actor) {
		double PlayerEntfernung = actor.getPlayerEntfernung();
		double ZielEntfernungKi = actor.getZielEntfernungKi();
		double Entfernung = PlayerEntfernung;
		if (ZielEntfernungKi < PlayerEntfernung) {
			Entfernung = ZielEntfernungKi;
		}
		int value;
		value = 0;
		if (Math.sqrt(Entfernung) > 100 && Math.sqrt(Entfernung) <= 190) {
			value = 3;
		}
		if (Math.sqrt(Entfernung) > 191 && Math.sqrt(Entfernung) <= 300) {
			value = 2;
		}
		if (Math.sqrt(Entfernung) > 301) {
			value = 1;
		}

		switch (value) {
		case 0:
		default:
		    actor.setFlower(Blumenauswahl.NORMALFLOWER);
			break;

		case 1:
			actor.setFlower(Blumenauswahl.FASTFLOWER);
			break;

		case 2:
			actor.setFlower(Blumenauswahl.BOUNCEFLOWER);
			break;

		case 3:
			actor.setFlower(Blumenauswahl.SPLITTEDFLOWER);
			break;
		}
	}

}
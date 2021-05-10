package de.paluno.game.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import de.paluno.game.GameState;
import de.paluno.game.SEPgame;
import de.paluno.game.gameobjects.Colour;
import de.paluno.game.gameobjects.FlagHolder;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.gameobjects.TankKi;
import de.paluno.game.gameobjects.Updateable;
import de.paluno.game.input.KiInputProvider;
import de.paluno.game.input.KiInputProviderCTF;
import de.paluno.game.input.KiInputProviderDM;

public class CaptureTheFlag extends Gamemode {
	private static Tank[] tanks = new Tank[2];
	private static TankKi[] tanksKi = new TankKi[4];

	private Vector2 spawnPointTankPlayer1 = new Vector2(100, 100);
	private Vector2 spawnPointTankPlayer2 = new Vector2(700, 100);
	private Vector2 spawnPointFlagHolder1 = new Vector2(100, 350);// 100,400//100,200
	private Vector2 spawnPointFlagHolder2 = new Vector2(780, 200);// 780,250//200,150
	private static FlagHolder flagHolder1;
	private static FlagHolder flagHolder2;
	public static int scoreTeam1 = 0;
	public static int scoreTeam2 = 0;
	public static int round = 1;
	KiInputProviderCTF inputKiCTF = new KiInputProviderCTF();
	public static int roundScoreTeam1 = 0;
	public static int roundScoreTeam2 = 0;
	Sound backgroundmusic;
	long soundid;

	private long endtime;
	public static boolean startNextRound, endtimeIsFinal, nextRoundIsStarted, isChecked, startNextRoundForHud;

	public CaptureTheFlag(SEPgame game, String inputMethod, ArrayList<String> settings, ArrayList<String> settings2,
			String map) {
		super(game, inputMethod, settings, settings2, map);
		hud = new Hud_CtF(game.batch, this, game);
		startNextRound = false;
		endtime = 0;
		endtimeIsFinal = false;
		nextRoundIsStarted = false;
		isChecked = false;
		backgroundmusic = Gdx.audio.newSound(Gdx.files.internal("Demise_Short.wav"));
		soundid = backgroundmusic.loop(0.15f);
	}

	@Override
	public void start() {
		spawnBase();
		spawnTankP1();
		spawnTankP2();
		spawnKiTanks();
		spawnItems(false, false, false, true, false);
		buildTilemapBodies(tiledMap, world, "CollisionObjects", 0.001f);
		buildTilemapBodies(tiledMap, world, "WasserObjects", 0.001f);
	}



	private void spawnTankP1() {
		Colour player1Colour = Colour.YELLOW;
		int player1 = Integer.parseInt(MenuScreen.teams.get(0));
		if (Integer.parseInt(MenuScreen.teams.get(0)) == 1)
			player1Colour = Colour.BLUE;
		if (Integer.parseInt(MenuScreen.teams.get(0)) == 2)
			player1Colour = Colour.RED;
		if (Integer.parseInt(MenuScreen.teams.get(0)) == 1 || Integer.parseInt(MenuScreen.teams.get(0)) == 2) {
			if (inputMethod == "keyboard") {
				tanks[0] = new Tank(this, spawnPointTankPlayer1, inputKeyboard, player1, settings, player1Colour,
						"Player1");
			} else if (inputMethod == "gamepad") {
				tanks[0] = new Tank(this, spawnPointTankPlayer1, inputGamepad, player1, settings, player1Colour,
						"Player1");
			} else if (inputMethod == "maus") {
				tanks[0] = new Tank(this, spawnPointTankPlayer1, inputMouse, player1, settings, player1Colour,
						"Player1");
				Pixmap pm = new Pixmap(Gdx.files.internal("CursorScheibe16x16.png"));
				Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
				pm.dispose();
			}
			registerAfterUpdate(tanks[0]);
		}
	}

	private void spawnTankP2() {
		if (MenuScreen.playerState == PlayerState.ACTIVE) {
			Colour player2Colour = Colour.YELLOW;
			int player2 = Integer.parseInt(MenuScreen.teams.get(1));
			if (Integer.parseInt(MenuScreen.teams.get(1)) == 1)
				player2Colour = Colour.BLUE;
			if (Integer.parseInt(MenuScreen.teams.get(1)) == 2)
				player2Colour = Colour.RED;
			if (Integer.parseInt(MenuScreen.teams.get(1)) == 1 || Integer.parseInt(MenuScreen.teams.get(1)) == 2) {
				if (MenuScreen.inputPlayer2 == PlayerInput.KEYBOARD) {
					tanks[1] = new Tank(this, spawnPointTankPlayer2, inputKeyboard, player2, settings2, player2Colour,
							"Player2");
				}
				if (MenuScreen.inputPlayer2 == PlayerInput.MOUSE) {
					tanks[1] = new Tank(this, spawnPointTankPlayer2, inputMouse, player2, settings2, player2Colour,
							"Player2");
					Pixmap pm = new Pixmap(Gdx.files.internal("CursorScheibe16x16.png"));
					Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
					pm.dispose();
				}
				if (MenuScreen.inputPlayer2 == PlayerInput.GAMEPAD) {
					tanks[1] = new Tank(this, spawnPointTankPlayer2, inputGamepad, player2, settings2, player2Colour,
							"Player2");
				}
			}
			registerAfterUpdate(tanks[1]);
		}
	}

	private void spawnKiTanks() {
		Colour Ki1Colour = Colour.YELLOW;
		Colour Ki2Colour = Colour.YELLOW;
		Colour Ki3Colour = Colour.YELLOW;
		int teamKi1 = Integer.parseInt(MenuScreen.teams.get(2));
		int teamKi2 = Integer.parseInt(MenuScreen.teams.get(3));
		int teamKi3 = Integer.parseInt(MenuScreen.teams.get(4));
		if (Integer.parseInt(MenuScreen.teams.get(2)) == 1)
			Ki1Colour = Colour.BLUE;
		if (Integer.parseInt(MenuScreen.teams.get(2)) == 2)
			Ki1Colour = Colour.RED;
		if (Integer.parseInt(MenuScreen.teams.get(3)) == 1)
			Ki2Colour = Colour.BLUE;
		if (Integer.parseInt(MenuScreen.teams.get(3)) == 2)
			Ki2Colour = Colour.RED;
		if (Integer.parseInt(MenuScreen.teams.get(4)) == 1)
			Ki3Colour = Colour.BLUE;
		if (Integer.parseInt(MenuScreen.teams.get(4)) == 2)
			Ki3Colour = Colour.RED;
		if (Integer.parseInt(MenuScreen.teams.get(2)) == 1 || Integer.parseInt(MenuScreen.teams.get(2)) == 2) {
			if (teamKi1 != 0) {
				tanksKi[0] = new TankKi(this, new Vector2(300, 200), inputKiCTF, teamKi1, settings, Ki1Colour, "KI1");
			}
		}
		if (Integer.parseInt(MenuScreen.teams.get(3)) == 1 || Integer.parseInt(MenuScreen.teams.get(3)) == 2) {
			if (teamKi2 != 0) {
				tanksKi[1] = new TankKi(this, new Vector2(600, 450), inputKiCTF, teamKi2, settings, Ki2Colour, "KI2");
			}
		}
		if (Integer.parseInt(MenuScreen.teams.get(4)) == 1 || Integer.parseInt(MenuScreen.teams.get(4)) == 2) {
			if (teamKi3 != 0) {
				tanksKi[2] = new TankKi(this, new Vector2(600, 300), inputKiCTF, teamKi3, settings, Ki3Colour, "KI3");
			}
		}

		if (MenuScreen.playerState == PlayerState.KI) {// Wenn Tank2 als Ki spielt
			Colour Ki4Colour = Colour.YELLOW;
			int teamKi4 = Integer.parseInt(MenuScreen.teams.get(1));
			if (Integer.parseInt(MenuScreen.teams.get(1)) == 1)
				Ki4Colour = Colour.BLUE;
			if (Integer.parseInt(MenuScreen.teams.get(1)) == 2)
				Ki4Colour = Colour.RED;
			if (Integer.parseInt(MenuScreen.teams.get(1)) == 1 || Integer.parseInt(MenuScreen.teams.get(1)) == 2) {
				if (teamKi4 != 0) {
					tanksKi[3] = new TankKi(this, new Vector2(200, 300), inputKiCTF, teamKi4, settings, Ki4Colour,
							"KI4");
				}
				if (tanksKi[3] != null) {
					registerAfterUpdate(tanksKi[3]);
				}
			}
		}
		if (tanksKi[0] != null) {
			registerAfterUpdate(tanksKi[0]);
		}
		if (tanksKi[1] != null) {
			registerAfterUpdate(tanksKi[1]);
		}
		if (tanksKi[2] != null) {
			registerAfterUpdate(tanksKi[2]);
		}
	}

	private void spawnBase() {
		flagHolder1 = new FlagHolder(this, spawnPointFlagHolder1, 1);
		flagHolder2 = new FlagHolder(this, spawnPointFlagHolder2, 2);
		registerAfterUpdate(flagHolder1);
		registerAfterUpdate(flagHolder2);

	}

	public void updatePhase(float delta) {
		this.checkItemLife();
		checkRound2();
		checkWin();

		hud.update(delta);
		for (Updateable i : updatableObjects) {
			i.update(delta);
		}
		for (Object j : gameObjectsToRegisterAfterUpdate) {
			register(j);
		}
		for (Object k : gameObjectsToForgetAfterUpdate) {
			forget(k);
		}
		gameObjectsToRegisterAfterUpdate.clear();
		gameObjectsToForgetAfterUpdate.clear();

	}

	public void updatePhase2(float delta) {

		for (int i = 0; i < 4; i++) {
			if (tanksKi[i] != null) {
				setKiVerhalten(tanksKi[i], tanksKi[i].getTeam());
			}
		}
	}

	public static void resetFlagHolder1() {
		flagHolder1.setFlag(1);
	}

	public static void resetFlagHolder2() {
		flagHolder2.setFlag(1);
	}

	private void checkRound2() {
		if ((scoreTeam1 == 2 && round == 1 || scoreTeam2 == 2 && round == 1) && endtimeIsFinal == false) {// Wenn 1
																											// Runde
																											// beendet
																											// wurde
//			round=2;
			endtime = System.currentTimeMillis();
			endtimeIsFinal = true;
			startNextRoundForHud = true;
		}

		if ((endtime < (long) System.currentTimeMillis() - 3000) && endtimeIsFinal == true) {// 3 Sekunden zwischen 2
																								// Runden
			startNextRound = true;
			round = 2;
		}

		if (round == 2 && startNextRound == true && nextRoundIsStarted == false) {// nächste Runde wird gestartet
			flagHolder1.swapPosition();
			flagHolder2.swapPosition();
			for (int i = 0; i < tanks.length; i++) {
				if (tanks[i] != null)
					tanks[i].reset();
			}
			for (int i = 0; i < tanksKi.length; i++) {
				if (tanksKi[i] != null)
					tanksKi[i].reset();
			}
			if (scoreTeam1 == 2) {
				roundScoreTeam1++;

			}
			if (scoreTeam2 == 2) {
				roundScoreTeam2++;
			}
			scoreTeam1 = 0;
			scoreTeam2 = 0;

			round = 2;
			nextRoundIsStarted = true;
			endtimeIsFinal = false;
			startNextRound = false;

		}
	}

	private void checkWin() {
		if (scoreTeam1 == 2 && round == 2 || scoreTeam2 == 2 && round == 2) {
			if (scoreTeam1 == 2 && isChecked == false) {
				roundScoreTeam1++;
				isChecked = true;
			}
			if (scoreTeam2 == 2 && isChecked == false) {
				roundScoreTeam2++;
				isChecked = true;
			}
			if (roundScoreTeam2 == roundScoreTeam1) {
				super.game.setScreen(new WinScreenCollection(game, "Draw"));
			}
			if (roundScoreTeam2 < roundScoreTeam1) {
				super.game.setScreen(new WinScreenCollection(game, "Team1"));
			}
			if (roundScoreTeam2 > roundScoreTeam1) {
				super.game.setScreen(new WinScreenCollection(game, "Team2"));
			}
		}
	}

	public static Tank[] gettanks() {
		return tanks;
	}

	public static TankKi[] gettanksKi() {
		return tanksKi;
	}

	private static Tank getFlaggenTraeger(int team) {
		for (int i = 0; i < 2; i++) {
			if (tanks[i] != null) { // von mir -Robin
				if (tanks[i].getTeam() == team && tanks[i].getFlag() == 1) {
					return tanks[i];
				}
			}
		}
		for (int i = 0; i < 4; i++) {// hab ich zu 4 ge�ndert war vorher 2 -Robin
			if (tanksKi[i] != null) { // von mir -Robin
				if (tanksKi[i].getTeam() == team && tanksKi[i].getFlag() == 1) {
					return tanksKi[i];
				}
			}
		}
		return null;
	}

	@Override
	public Tank getSpielerTank() {
		return tanks[1];
	}

	private void setKiVerhalten(TankKi actor, int Team) {
		if (actor != null && actor.getHealth() > -1) {

			FlagHolder flagHolderGegner = flagHolder2;
			FlagHolder flagHolderEigen = flagHolder1;

			if (Team == 2) {
				flagHolderGegner = flagHolder1;
				flagHolderEigen = flagHolder2;
			}

			if (flagHolderGegner.getFlag() == 1) {
				Arrive<Vector2> arriveSB = new Arrive<Vector2>(actor, flagHolderGegner).setArrivalTolerance(0.1f)
						.setTimeToTarget(0.01f).setDecelerationRadius(10);
				PrioritySteering<Vector2> behaviors = new PrioritySteering<Vector2>(actor, 0.01f)
						.add(actor.whiskersAvoid).add(arriveSB);
				actor.setPrioritySteering(behaviors);
			} else {

				if (getFlaggenTraeger(Team) != actor) {
					Arrive<Vector2> arriveSB = new Arrive<Vector2>(actor, getFlaggenTraeger(Team))
							.setArrivalTolerance(0.1f).setTimeToTarget(0.01f).setDecelerationRadius(10);
					PrioritySteering<Vector2> behaviors = new PrioritySteering<Vector2>(actor, 0.01f)
							.add(actor.whiskersAvoid).add(arriveSB);
					actor.setPrioritySteering(behaviors);
				} else {
					Arrive<Vector2> arriveSB = new Arrive<Vector2>(actor, flagHolderEigen).setArrivalTolerance(0.1f)
							.setTimeToTarget(0.01f).setDecelerationRadius(10);
					PrioritySteering<Vector2> behaviors = new PrioritySteering<Vector2>(actor, 0.01f)
							.add(actor.whiskersAvoid).add(arriveSB);
					actor.setPrioritySteering(behaviors);
				}
			}
		}

	}

	@Override
	public void stopSounds() {
		backgroundmusic.stop();
		backgroundmusic.dispose();
	}

	@Override
	public void tacticalNuke2() {
		// existiert in diesem modus nicht

	}

}

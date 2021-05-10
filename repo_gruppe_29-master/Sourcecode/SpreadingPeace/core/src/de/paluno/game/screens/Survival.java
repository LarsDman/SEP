package de.paluno.game.screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.SEPgame;
import de.paluno.game.gameobjects.Colour;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.gameobjects.TankKi;
import de.paluno.game.gameobjects.Updateable;
import de.paluno.game.gameobjects.Waypoint;
import de.paluno.game.input.KiInputProviderSurvival;

public class Survival extends Gamemode {

	Vector2 spawnPointTank = new Vector2(100, 100);
	protected Tank playerTank;
	protected TankKi kiTank;
	protected TankKi zombie1;
	protected TankKi zombie2;
	protected ArrayList<TankKi> tanksKiList;
	protected ArrayList<Waypoint> allwaypoints;
	KiInputProviderSurvival inputKiSurvival = new KiInputProviderSurvival();
	protected boolean start;
	int momentaneZombies = 1;
	int neueZombies = 1;
	Sound backgroundmusic;
	long soundid;

	public Survival(SEPgame game, String inputMethod, ArrayList<String> settings, ArrayList<String> settings2,
			String map) {
		super(game, inputMethod, settings, settings2, map);
		start = false;
		backgroundmusic = Gdx.audio.newSound(Gdx.files.internal("Demise_Short.wav"));
		soundid = backgroundmusic.loop(0.15f);

		tanksKiList = new ArrayList<TankKi>();
	}

	public void stopSounds() {
		backgroundmusic.stop();
		backgroundmusic.dispose();
	}

	@Override
	public void start() {
		try {
			hud = new Hud_Survival(game.batch, this, game);
			spawnTanks();
			spawnItems(true, true, false, true, false);
			buildTilemapBodies(tiledMap, world, "CollisionObjects", 0.001f);
			buildTilemapBodies(tiledMap, world, "WasserObjects", 0.001f);
			buildTilemapBodies(tiledMap, world, "TreeObjects", 0.001f);

			this.addTank(1, playerTank);
		} catch (Exception e) { 											// try catch block fuer den errorscreen
			game.setScreen(new ErrorScreen(super.game));
		}
	}

	public void spawnTanks() {
		Colour player1 = MenuScreen.colours.get(0);
		Colour ki = MenuScreen.colours.get(1);
		if (inputMethod == "keyboard") {
			playerTank = new Tank(this, spawnPointTank, inputKeyboard, 1, settings, player1, "Player1");
			System.out.println("Keyboard ausgewaehlt");
		} else if (inputMethod == "gamepad") {
			playerTank = new Tank(this, spawnPointTank, inputGamepad, 1, settings, player1, "Player1");
			System.out.println("Gamepad ausgewaehlt");
		} else if (inputMethod == "maus") {
			playerTank = new Tank(this, spawnPointTank, inputMouse, 1, settings, player1, "Player1");
			System.out.println("Maus ausgewaehlt");
		}
		kiTank = new TankKi(this, new Vector2(600, 300), inputKiSurvival, 1, settings, ki, "Ki1");
		registerAfterUpdate(playerTank);
		tanksKiList.add(kiTank);
		registerAfterUpdate(kiTank);
	}

	public void Zombiespawner() {
		Gdx.app.postRunnable(new Runnable() {
			Colour ki = MenuScreen.colours.get(1);

			@Override
			public void run() {

				Random random = new Random();
				int zufallspawn1 = random.nextInt(4);
				int zufallspawn2 = random.nextInt(4);
				int spawnpunkt1_X = 100;
				int spawnpunkt1_Y = 100;
				int spawnpunkt2_X = 100;
				int spawnpunkt2_Y = 400;
				int spawnpunkt3_X = 800;
				int spawnpunkt3_Y = 100;
				int spawnpunkt4_X = 800;
				int spawnpunkt4_Y = 400;

				if (zufallspawn1 != zufallspawn2) {
					if (zufallspawn1 == 0) {
						zombie1 = new TankKi(Survival.this, new Vector2(spawnpunkt1_X, spawnpunkt1_Y), inputKiSurvival,
								1, settings, ki, "Ki");
						tanksKiList.add(zombie1);
					}
					if (zufallspawn1 == 1) {
						zombie1 = new TankKi(Survival.this, new Vector2(spawnpunkt2_X, spawnpunkt2_Y), inputKiSurvival,
								1, settings, ki, "Ki");
						tanksKiList.add(zombie1);
					}
					if (zufallspawn1 == 2) {
						zombie1 = new TankKi(Survival.this, new Vector2(spawnpunkt3_X, spawnpunkt3_Y), inputKiSurvival,
								1, settings, ki, "Ki");
						tanksKiList.add(zombie1);
					}
					if (zufallspawn1 == 3) {
						zombie1 = new TankKi(Survival.this, new Vector2(spawnpunkt4_X, spawnpunkt4_Y), inputKiSurvival,
								1, settings, ki, "Ki");
						tanksKiList.add(zombie1);
					}
					if (zufallspawn2 == 0) {
						zombie2 = new TankKi(Survival.this, new Vector2(spawnpunkt1_X, spawnpunkt1_Y), inputKiSurvival,
								1, settings, ki, "Ki");
						tanksKiList.add(zombie2);
					}
					if (zufallspawn2 == 1) {
						zombie2 = new TankKi(Survival.this, new Vector2(spawnpunkt2_X, spawnpunkt2_Y), inputKiSurvival,
								1, settings, ki, "Ki");
						tanksKiList.add(zombie2);
					}
					if (zufallspawn2 == 2) {
						zombie2 = new TankKi(Survival.this, new Vector2(spawnpunkt3_X, spawnpunkt3_Y), inputKiSurvival,
								1, settings, ki, "Ki");
						tanksKiList.add(zombie2);
					}
					if (zufallspawn2 == 3) {
						zombie2 = new TankKi(Survival.this, new Vector2(spawnpunkt4_X, spawnpunkt4_Y), inputKiSurvival,
								1, settings, ki, "Ki");
						tanksKiList.add(zombie2);
					}
				} else {
					zombie1 = new TankKi(Survival.this, new Vector2(spawnpunkt1_X, spawnpunkt1_Y), inputKiSurvival, 1,
							settings, ki, "Ki");
					tanksKiList.add(zombie1);
					zombie2 = new TankKi(Survival.this, new Vector2(spawnpunkt2_X, spawnpunkt2_Y), inputKiSurvival, 1,
							settings, ki, "Ki");
					tanksKiList.add(zombie2);

				}
				registerAfterUpdate(zombie1);
				registerAfterUpdate(zombie2);
				score = score + 1;
			}
		});
	}

	public void tacticalNuke2() {
		for (Tank tank : tanksKiList) {
			if (tank != null) {
				tank.setHealth(tank.getHealth() / 2);
			}
		}
	}

	public void coindrop() {
	}

	@Override
	public void updatePhase(float delta) {

		if (playerTank.getHealth() <= 0) {
			super.game.setScreen(new GameOverScreen(game, score));
			backgroundmusic.stop();
			Sound sound = Gdx.audio.newSound(Gdx.files.internal("clapping.mp3"));
			sound.play(1f);
		}

		hud.update(delta);
		this.checkItemLife();
		
		for (Updateable i : updatableObjects) {
			i.update(delta);
		}
		for (Object j : gameObjectsToRegisterAfterUpdate) {
			register(j);
		}
		for (Object k : gameObjectsToForgetAfterUpdate) {
			if (k instanceof TankKi) {
				Zombiespawner();
			}
			forget(k);
		}
		gameObjectsToRegisterAfterUpdate.clear();
		gameObjectsToForgetAfterUpdate.clear();

		for (TankKi tankKi : tanksKiList) {
			tankKi.rayCast(playerTank);
		}
		
		if (playerTank.getHealth() < 20)
			backgroundmusic.setPitch(soundid, 1.01f);

		for (TankKi tankKi : tanksKiList) {
			if (tankKi.haslineofsight()) {
				tankKi.setGhosts(this.getSpielerTank());
			}

			if (tankKi.getGhost() != null) {
				Arrive<Vector2> arrive = new Arrive<Vector2>(tankKi, tankKi.getGhost()).setArrivalTolerance(0.1f)
						.setTimeToTarget(0.01f).setDecelerationRadius(10);
				PrioritySteering<Vector2> behaviors = new PrioritySteering<Vector2>(tankKi, 0.01f)
						.add(tankKi.whiskersAvoid).add(arrive);
				tankKi.setPrioritySteering(behaviors);
			} else {
//				if (tankKi.getLastTimeSeen() > 5f) {
//					tankKi.setGhosts(this.getSpielerTank());
//				}
				Arrive<Vector2> arrive = new Arrive<Vector2>(tankKi, tankKi).setArrivalTolerance(0.1f)
						.setTimeToTarget(0.01f).setDecelerationRadius(10);
				PrioritySteering<Vector2> behaviors = new PrioritySteering<Vector2>(tankKi, 0.01f)
						.add(tankKi.whiskersAvoid).add(arrive);
				tankKi.setPrioritySteering(behaviors);
				;
			}
		}
	}

	public Tank getSpielerTank() {
		return playerTank;
	}

	public void render(float delta) {
		this.gameState(delta);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render(hintergrundlayers);
		renderPhase(delta);
		tiledMapRenderer.getBatch().begin();
		tiledMapRenderer.renderTileLayer(vordergrundlayer);
		tiledMapRenderer.getBatch().end();
		spriteBatch.setProjectionMatrix(camera.combined);
		hud.stage.draw();
		hud.stage.act();
	}
}

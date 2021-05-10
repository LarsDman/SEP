package de.paluno.game.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;

import java.util.Random;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.SEPgame;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.gameobjects.TankKi;
import de.paluno.game.gameobjects.Updateable;
import de.paluno.game.gameobjects.Wall;

public class Survival extends Gamemode {

	private Wall wallbelow;
	private Wall wallabove;
	private Wall wallleftside;
	private Wall wallrightside;
	private Wall wallmiddle;
	Vector2 spawnPointTank = new Vector2(100, 100);
	private Tank tank;
	private Tank kiTank;
	private TankKi zombie1;
	private TankKi zombie2;
	int momentaneZombies = 1;
	int neueZombies = 1;

	public Survival(SEPgame game, String inputMethod, ArrayList<String> settings, ArrayList<String> settings2) {
		super(game, inputMethod, settings, settings2);
	}

	@Override
	public void start() {
		spawnTanks();
		spawnItems();
		Vector2 spawnWallbelow = new Vector2(500, 17);
		wallbelow = new Wall(this, spawnWallbelow, 540, 17);
		Vector2 spawnWallabove = new Vector2(500, 524);
		wallabove = new Wall(this, spawnWallabove, 540, 17);
		Vector2 spawnWallleft = new Vector2(17, 0);	
		wallleftside = new Wall(this, spawnWallleft, 17,1920);
		Vector2 spawnWallright = new Vector2(943, 0);
		wallrightside = new Wall(this, spawnWallright, 17,1920);
		Vector2 spawnWallmiddle = new Vector2(450, 250);
		wallmiddle = new Wall(this, spawnWallmiddle, 200,17);
		wallmiddle.setMiddle(true);
		registerAfterUpdate(wallleftside);
		registerAfterUpdate(wallmiddle);
		registerAfterUpdate(wallrightside);
		registerAfterUpdate(wallabove);
		registerAfterUpdate(wallbelow);

	}

	public void spawnTanks() {
		kiTank = new TankKi(this, new Vector2(50, 50), inputKi, 1, settings);

		if (inputMethod == "keyboard") {
			tank = new Tank(this, spawnPointTank, inputKeyboard, 1, settings);
			System.out.println("Keyboard ausgewaehlt");
		} else if (inputMethod == "gamepad") {
			tank = new Tank(this, spawnPointTank, inputGamepad, 1, settings);
			System.out.println("Gamepad ausgewaehlt");
		} else if (inputMethod == "maus") {
			tank = new Tank(this, spawnPointTank, inputMouse, 1, settings);
			System.out.println("Maus ausgewaehlt");
		}
		registerAfterUpdate(tank);
		registerAfterUpdate(kiTank);
		
		//Ki Integration erster KiTank
		Arrive<Vector2> arriveKi = new Arrive<Vector2> (kiTank, tank)
				.setArrivalTolerance(0.1f)
				.setTimeToTarget(0.01f)
				.setDecelerationRadius(10);
		kiTank.setBehavior(arriveKi); 

	}

	public void Zombiespawner() {
		Gdx.app.postRunnable(new Runnable() {

			@Override
			public void run() { 
				
				System.out.println("Zombiespawner vor, noch ein Tor!");
				Random random = new Random();
				int zufallspawn1 = random.nextInt(4);
				int zufallspawn2 = random.nextInt(4);
				System.out.println(zufallspawn1 + "  und  " + zufallspawn2);
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
						zombie1 = new TankKi(Survival.this, new Vector2(spawnpunkt1_X, spawnpunkt1_Y), inputKi, 1,
								settings);
					}
					if (zufallspawn1 == 1) {
						zombie1 = new TankKi(Survival.this, new Vector2(spawnpunkt2_X, spawnpunkt2_Y), inputKi, 1,
								settings);
					}
					if (zufallspawn1 == 2) {
						zombie1 = new TankKi(Survival.this, new Vector2(spawnpunkt3_X, spawnpunkt3_Y), inputKi, 1,
								settings);
					}
					if (zufallspawn1 == 3) {
						zombie1 = new TankKi(Survival.this, new Vector2(spawnpunkt4_X, spawnpunkt4_Y), inputKi, 1,
								settings);
					}
					if (zufallspawn2 == 0) {
						zombie2 = new TankKi(Survival.this, new Vector2(spawnpunkt1_X, spawnpunkt1_Y), inputKi, 1,
								settings);
					}
					if (zufallspawn2 == 1) {
						zombie2 = new TankKi(Survival.this, new Vector2(spawnpunkt2_X, spawnpunkt2_Y), inputKi, 1,
								settings);
					}
					if (zufallspawn2 == 2) {
						zombie2 = new TankKi(Survival.this, new Vector2(spawnpunkt3_X, spawnpunkt3_Y), inputKi, 1,
								settings);
					}
					if (zufallspawn2 == 3) {
						zombie2 = new TankKi(Survival.this, new Vector2(spawnpunkt4_X, spawnpunkt4_Y), inputKi, 1,
								settings);
					}
				} else {
					zombie1 = new TankKi(Survival.this, new Vector2(spawnpunkt1_X, spawnpunkt1_Y), inputKi, 1,
							settings);
					zombie2 = new TankKi(Survival.this, new Vector2(spawnpunkt2_X, spawnpunkt2_Y), inputKi, 1,
							settings);
					
				}
				registerAfterUpdate(zombie1);
				registerAfterUpdate(zombie2);
				
				//Ki Bewegung f�r Zombies
				Arrive<Vector2> arriveZB1 = new Arrive<Vector2> (zombie1, tank)
						.setArrivalTolerance(0.1f)
						.setTimeToTarget(0.01f)
						.setDecelerationRadius(10);
				zombie1.setBehavior(arriveZB1); 
				
				Arrive<Vector2> arriveZB2 = new Arrive<Vector2> (zombie2, tank)
						.setArrivalTolerance(0.1f)
						.setTimeToTarget(0.01f)
						.setDecelerationRadius(10);
				zombie2.setBehavior(arriveZB2); 
					
				score = score + 1;
				
			}
		});
	}

	@Override
	public void updatePhase(float delta) {
		
		if (tank.getHealth()<=0) {
			camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // für meine Auflösung: 1400 * 864
			super.game.setScreen(new GameOverScreen(game, score));	
		}

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
		
		this.checkItemLife();
		
		gameObjectsToRegisterAfterUpdate.clear();
		gameObjectsToForgetAfterUpdate.clear();
	}
}

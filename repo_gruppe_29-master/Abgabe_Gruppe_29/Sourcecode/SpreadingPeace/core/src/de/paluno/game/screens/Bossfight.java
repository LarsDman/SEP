package de.paluno.game.screens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import de.paluno.game.CollisionHandler;
import de.paluno.game.Constants;
import de.paluno.game.SEPgame;
import de.paluno.game.gameobjects.Bosstank;
import de.paluno.game.gameobjects.Colour;
import de.paluno.game.gameobjects.F_L_O_W_E_R;
import de.paluno.game.gameobjects.Item;
import de.paluno.game.gameobjects.PhysicsObject;
import de.paluno.game.gameobjects.Trumpitem;
import de.paluno.game.gameobjects.Power;
import de.paluno.game.gameobjects.Renderable;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.gameobjects.TankKi;
import de.paluno.game.gameobjects.Tower;
import de.paluno.game.gameobjects.Updateable;
import de.paluno.game.gameobjects.Velocity;
import de.paluno.game.gameobjects.Wall;
import de.paluno.game.gameobjects.Waypoint;
import de.paluno.game.input.InputProvider;

public class Bossfight extends de.paluno.game.screens.Gamemode implements com.badlogic.gdx.Screen {

	Random random = new Random();
	Sound backgroundmusic;
	long soundid;
	int zielscheibenPositionNeuX = random.nextInt(1900); // zufällige X-Koordinate für den Spawn der Zielscheibe im
															// Poxelbereich bis 700
	int zielscheibenPositionNeuY = random.nextInt(1000); // zufällige Y-Koordinate für den Spawn der Zielscheibe im
															// Pixelbereich bis 450
	private Tank[] tanks = new Tank[6];
	private Tower[] towers = new Tower[4];
	Bosstank boss;
	Vector2 spawnPointZielscheibe = new Vector2(zielscheibenPositionNeuX, zielscheibenPositionNeuY);

	Vector2 spawnPointTankPlayer1 = new Vector2(100, 100);
	Vector2 spawnPointTower1 = new Vector2(700, 400);
	Vector2 spawnPointTower2 = new Vector2(100, 400);
	Vector2 spawnPointTower3 = new Vector2(400, 400);
	Vector2 spawnPointTower4 = new Vector2(400, 400);
	private TankKi kiTank;
	
	
	public Bossfight(SEPgame game, String inputMethod, ArrayList<String> settings, ArrayList<String> settings2, String map) {
		super(game, inputMethod, settings, settings2, map);
		backgroundmusic = Gdx.audio.newSound(Gdx.files.internal("death-metal-loop.wav"));
		soundid = backgroundmusic.loop(0.1f);
		hud = new Hud_Deathmatch(game.batch, this, game);
	}

	public void start() { // ersetzt ehemalige show() methode, da diese in gamemode ausgelagert wurde ->
			try {				// "show()" sachen also hier rein
			spawnTankP1();
			spawnTowersandBoss();
			spawnItems(true, true, false, true, false);
			buildTilemapBodies(tiledMap, world, "CollisionObjects", 0.001f);
			buildTilemapBodies(tiledMap, world, "WasserObjects", 0.001f);
			buildTilemapBodies(tiledMap, world, "TreeObjects", 0.001f);
			
			}
			catch(Exception e)
			{				
				e.printStackTrace();
				game.setScreen(new ErrorScreen(super.game));
			}
	}
	
	public void stopSounds()
	{
		backgroundmusic.stop();
		backgroundmusic.dispose();
	}
	
	public void tacticalNuke2()
	{
		for(Tank tank: tanks) {
			if (tank != null) {
				tank.setHealth(tank.getHealth()/2);				
			}
		}

	}

	public void spawnTowersandBoss()
	{ 
		Colour y = Colour.RED;
		kiTank = new TankKi(this, new Vector2(70, 475), inputKi, 1, settings, y, "Ki1");
		towers[0] = new Tower(this, new Vector2(250, 250), inputKi, 1, settings, y, "TOWER1");
		towers[1] = new Tower(this, new Vector2(720, 250), inputKi, 1, settings, y, "TOWER2");
		towers[2] = new Tower(this, new Vector2(250, 350), inputKi, 1, settings, y, "TOWER3");
		towers[3] = new Tower(this, new Vector2(720, 350), inputKi, 1, settings, y, "TOWER3");
		boss = new Bosstank(this, new Vector2(450, 300), inputKi, settings);
		registerAfterUpdate(towers[0]);
		registerAfterUpdate(towers[1]);
		registerAfterUpdate(towers[2]);
		registerAfterUpdate(towers[3]);
		registerAfterUpdate(boss);
		registerAfterUpdate(kiTank);
	}

	public void spawnTankP1() {
		Colour x = MenuScreen.colours.get(0);
		int player1 = Integer.parseInt(MenuScreen.teams.get(0));
		if (inputMethod == "keyboard") {
			tanks[0] = new Tank(this, spawnPointTankPlayer1, inputKeyboard, player1, settings, x, "Player1");
		} else if (inputMethod == "gamepad") {
			tanks[0] = new Tank(this, spawnPointTankPlayer1, inputGamepad, player1, settings, x, "Player1");
		} else if (inputMethod == "maus") {
			tanks[0] = new Tank(this, spawnPointTankPlayer1, inputMouse, player1, settings, x, "Player1");
		}
		registerAfterUpdate(tanks[0]);
	}

	public void updatePhase(float delta) {
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
		for(int i=0; i<4; i++)
		{
		Arrive<Vector2> arrivetw = new Arrive<Vector2> (towers[i], tanks[0])
				.setArrivalTolerance(0.1f)
				.setTimeToTarget(1f)
				.setDecelerationRadius(1000);
		towers[i].setBehavior(arrivetw);
		}
		
			Arrive<Vector2> arriveboss = new Arrive<Vector2> (boss, tanks[0])
					.setArrivalTolerance(0.1f)
					.setTimeToTarget(1f)
					.setDecelerationRadius(1000);
			boss.setBehavior(arriveboss);
			
		this.checkItemLife();


			if (tanks[0].getHealth() >= 0 && boss.getHealth()<0) {
				camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				super.game.setScreen(new WinScreen(game));
				backgroundmusic.stop();
			}
			else if (tanks[0].getHealth()<0&&boss.getHealth()>0) {
				camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				super.game.setScreen(new KIWinScreen(game));
				backgroundmusic.stop();
			}
		
		
		if (tanks[0].getHealth()<10) backgroundmusic.setPitch(soundid, 1.1f);
		kiTank.rayCast(tanks[0]);
		
			if (kiTank.haslineofsight()) {
				kiTank.setSchussBool(true);
				Arrive<Vector2> arrive = new Arrive<Vector2>(kiTank, tanks[0]).setArrivalTolerance(0.1f)
						.setTimeToTarget(0.01f).setDecelerationRadius(10);
				PrioritySteering<Vector2> behaviors = new PrioritySteering<Vector2>(kiTank, 0.01f).add(kiTank.whiskersAvoid).add(arrive);
				//kiTank.setBehavior(arrive);
				kiTank.setPrioritySteering(behaviors);
			} 
			else
			{
				kiTank.setSchussBool(false);
				Waypoint randompoint = new Waypoint(random.nextFloat()* (900 - 70) + 70,random.nextFloat()* (900 - 70) + 70);
				Arrive<Vector2> arrive = new Arrive<Vector2>(kiTank,randompoint).setArrivalTolerance(0.1f)
						.setTimeToTarget(0.01f).setDecelerationRadius(10);
				PrioritySteering<Vector2> behaviors = new PrioritySteering<Vector2>(kiTank, 0.01f).add(kiTank.whiskersAvoid).add(arrive);
				//kiTank.setBehavior(arrive);
				kiTank.setPrioritySteering(behaviors);
			}
		
		
		
	}
	
	public Tank getSpielerTank() {
		return tanks[0];

	}

	public Tank[] settanks() {
		return tanks;
	}

}


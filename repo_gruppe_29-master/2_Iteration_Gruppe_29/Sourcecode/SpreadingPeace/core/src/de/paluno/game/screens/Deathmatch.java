package de.paluno.game.screens;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
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
import de.paluno.game.gameobjects.F_L_O_W_E_R;
import de.paluno.game.gameobjects.Item;
import de.paluno.game.gameobjects.PhysicsObject;
import de.paluno.game.gameobjects.Poison;
import de.paluno.game.gameobjects.Power;
import de.paluno.game.gameobjects.Renderable;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.gameobjects.TankKi;
import de.paluno.game.gameobjects.Updateable;
import de.paluno.game.gameobjects.Velocity;
import de.paluno.game.gameobjects.Wall;

public class Deathmatch extends de.paluno.game.screens.Gamemode implements com.badlogic.gdx.Screen {

	Random random = new Random();
	int zielscheibenPositionNeuX = random.nextInt(1900); // zufällige X-Koordinate für den Spawn der Zielscheibe im
															// Poxelbereich bis 700
	int zielscheibenPositionNeuY = random.nextInt(1000); // zufällige Y-Koordinate für den Spawn der Zielscheibe im
															// Pixelbereich bis 450
	private Tank [] tanks = new Tank[5];
	private TankKi [] tanksKi = new TankKi[3];
	private Wall wallbelow, wallabove, wallrightside, wallleftside, wallmiddle;
	Vector2 spawnPointZielscheibe = new Vector2(zielscheibenPositionNeuX, zielscheibenPositionNeuY);
	
	Vector2 spawnPointTankPlayer1 = new Vector2(100, 100);
	Vector2 spawnPointTankPlayer2 = new Vector2(700, 100);
	Vector2 spawnPointTankKi1 = new Vector2(700, 400);
	Vector2 spawnPointTankKi2 = new Vector2(100, 400);
	Vector2 spawnPointTankKi3 = new Vector2(400, 400);

	public Deathmatch(SEPgame game, String inputMethod, ArrayList<String> settings, ArrayList<String> settings2) {
		
		super(game, inputMethod, settings, settings2);
	}


	public void start() { //ersetzt ehemalige show() methode, da diese in gamemode ausgelagert wurde -> "show()" sachen also hier rein
		spawnTankP1();
		spawnTankP2();
		spawnTankKi();
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

	public void spawnTankKi() {
		
		int teamKi1 = Integer.parseInt(MenuScreen.teams.get(2));
		int teamKi2 = Integer.parseInt(MenuScreen.teams.get(3));
		int teamKi3 = Integer.parseInt(MenuScreen.teams.get(4));
		
		tanksKi [0] = new TankKi(this, new Vector2(300,200), inputKi , teamKi1, settings);
		tanksKi [1] = new TankKi(this, new Vector2(600,200), inputKi, teamKi2, settings);
		tanksKi [2] = new TankKi(this, new Vector2(600,300), inputKi, teamKi3, settings);
		
		registerAfterUpdate(tanksKi [0]);
		registerAfterUpdate(tanksKi [1]);
		registerAfterUpdate(tanksKi [2]);
	}
	
    public void updatePhase(float delta) {

		
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
		
		//Zielerfassung des Ki0 Panzers
		double ZielEntfernungKi0 = 500000;
		double ZielEntfernungPl0 = Math.pow(tanks[0].getPosition().x - tanksKi[0].getPosition().x,2)+Math.pow(tanks[0].getPosition().y - tanksKi[0].getPosition().y,2);
		int ZielKi0 = 1;
		int ZielPl0 = 0;
		
		for (int x = 1; x < 3; x++) {
			if ((Math.pow(tanksKi[x].getPosition().x - tanksKi[0].getPosition().x,2)+Math.pow(tanksKi[x].getPosition().y - tanksKi[0].getPosition().y,2)) < ZielEntfernungKi0 && tanksKi [x].getHealth() > -1 && tanksKi [x].getTeam() != tanksKi [0].getTeam() ){
				ZielEntfernungKi0 = Math.pow(tanksKi[x].getPosition().x - tanksKi[0].getPosition().x,2)+Math.pow(tanksKi[x].getPosition().y - tanksKi[0].getPosition().y,2);
				ZielKi0 = x;
			}		
		}
		if(tanks [1] !=null){
			ZielEntfernungPl0 = Math.pow(tanks[0].getPosition().x - tanksKi[0].getPosition().x,2)+Math.pow(tanks[0].getPosition().y - tanksKi[0].getPosition().y,2);
				if ((Math.pow(tanks[1].getPosition().x - tanksKi[0].getPosition().x,2)+Math.pow(tanks[1].getPosition().y - tanksKi[0].getPosition().y,2)) < ZielEntfernungPl0 && tanks [1].getHealth() > -1 && tanks [1].getTeam() != tanksKi [0].getTeam()){
					ZielEntfernungPl0 = Math.pow(tanks[1].getPosition().x - tanksKi[0].getPosition().x,2)+Math.pow(tanks[1].getPosition().y - tanksKi[0].getPosition().y,2);
					ZielPl0 = 1;
				}
				else ZielPl0 = 0;
			
		}
		if (ZielEntfernungKi0 < ZielEntfernungPl0) {
			Arrive<Vector2> arriveSB0 = new Arrive<Vector2> (tanksKi [0], tanksKi[ZielKi0])
					.setArrivalTolerance(0.1f)
					.setTimeToTarget(0.01f)
					.setDecelerationRadius(10);
			tanksKi[0].setBehavior(arriveSB0); 
		}
		else {
			if (tanks[ZielPl0].getTeam() != tanksKi [0].getTeam()) {
			Arrive<Vector2> arriveSB0 = new Arrive<Vector2> (tanksKi [0], tanks[ZielPl0])
					.setArrivalTolerance(0.1f)
					.setTimeToTarget(0.01f)
					.setDecelerationRadius(10);
			tanksKi[0].setBehavior(arriveSB0);
			}
		}
		
		
		//Zielerfassung des Ki1 Panzers
		double ZielEntfernungKi1 = 500000;
		double ZielEntfernungPl1 = Math.pow(tanks[0].getPosition().x - tanksKi[1].getPosition().x,2)+Math.pow(tanks[0].getPosition().y - tanksKi[1].getPosition().y,2);
		int ZielKi1 = 1;
		int ZielPl1 = 0;
		
		for (int x = 0; x < 3; x++) {
			if ((Math.pow(tanksKi[x].getPosition().x - tanksKi[1].getPosition().x,2)+Math.pow(tanksKi[x].getPosition().y - tanksKi[1].getPosition().y,2)) < ZielEntfernungKi1 && tanksKi [x].getHealth() > -1 && x != 1 && tanksKi [x].getTeam() != tanksKi [1].getTeam()){
				ZielEntfernungKi1 = Math.pow(tanksKi[x].getPosition().x - tanksKi[1].getPosition().x,2)+Math.pow(tanksKi[x].getPosition().y - tanksKi[1].getPosition().y,2);
				ZielKi1 = x;
			}		
		}
		if(tanks [1] !=null){
			ZielEntfernungPl1 = Math.pow(tanks[0].getPosition().x - tanksKi[1].getPosition().x,2)+Math.pow(tanks[0].getPosition().y - tanksKi[1].getPosition().y,2);
				if ((Math.pow(tanks[1].getPosition().x - tanksKi[1].getPosition().x,2)+Math.pow(tanks[1].getPosition().y - tanksKi[1].getPosition().y,2)) < ZielEntfernungPl1 && tanks [1].getHealth() > -1 && tanks [1].getTeam() != tanksKi [1].getTeam()){
					ZielEntfernungPl1 = Math.pow(tanks[1].getPosition().x - tanksKi[1].getPosition().x,2)+Math.pow(tanks[1].getPosition().y - tanksKi[1].getPosition().y,2);
					ZielPl1 = 1;
				}
				else ZielPl1 = 0;
			
		}
		if (ZielEntfernungKi1 < ZielEntfernungPl1) {
			Arrive<Vector2> arriveSB1 = new Arrive<Vector2> (tanksKi [1], tanksKi[ZielKi1])
					.setArrivalTolerance(0.1f)
					.setTimeToTarget(0.01f)
					.setDecelerationRadius(10);
			tanksKi[1].setBehavior(arriveSB1); 
		}
		else {
			if (tanks[ZielPl1].getTeam() != tanksKi [1].getTeam()) {
			Arrive<Vector2> arriveSB1 = new Arrive<Vector2> (tanksKi [1], tanks[ZielPl1])
					.setArrivalTolerance(0.1f)
					.setTimeToTarget(0.01f)
					.setDecelerationRadius(10);
			tanksKi[1].setBehavior(arriveSB1); 
			}
		}
		
		
		//Zielerfassung des Ki2 Panzers
				double ZielEntfernungKi2 = 500000;
				double ZielEntfernungPl2 = Math.pow(tanks[0].getPosition().x - tanksKi[2].getPosition().x,2)+Math.pow(tanks[0].getPosition().y - tanksKi[2].getPosition().y,2);
				int ZielKi2 = 1;
				int ZielPl2 = 0;
				
				for (int x = 0; x < 2; x++) {
					if ((Math.pow(tanksKi[x].getPosition().x - tanksKi[2].getPosition().x,2)+Math.pow(tanksKi[x].getPosition().y - tanksKi[2].getPosition().y,2)) < ZielEntfernungKi2 && tanksKi [x].getHealth() > -1 && tanksKi [x].getTeam() != tanksKi [2].getTeam()){
						ZielEntfernungKi2 = Math.pow(tanksKi[x].getPosition().x - tanksKi[2].getPosition().x,2)+Math.pow(tanksKi[x].getPosition().y - tanksKi[2].getPosition().y,2);
						ZielKi2 = x;
					}		
				}
				if(tanks [1] !=null){
					ZielEntfernungPl2 = Math.pow(tanks[0].getPosition().x - tanksKi[2].getPosition().x,2)+Math.pow(tanks[0].getPosition().y - tanksKi[2].getPosition().y,2);
						if ((Math.pow(tanks[1].getPosition().x - tanksKi[2].getPosition().x,2)+Math.pow(tanks[1].getPosition().y - tanksKi[2].getPosition().y,2)) < ZielEntfernungPl2 && tanks [1].getHealth() > -1 && tanks [1].getTeam() != tanksKi [2].getTeam()){
							ZielEntfernungPl2 = Math.pow(tanks[1].getPosition().x - tanksKi[2].getPosition().x,2)+Math.pow(tanks[1].getPosition().y - tanksKi[2].getPosition().y,2);
							ZielPl2 = 1;
						}
						else ZielPl2 = 0;
					
				}
				if (ZielEntfernungKi2 < ZielEntfernungPl2) {
					Arrive<Vector2> arriveSB2 = new Arrive<Vector2> (tanksKi [2], tanksKi[ZielKi2])
							.setArrivalTolerance(0.1f)
							.setTimeToTarget(0.01f)
							.setDecelerationRadius(10);
					tanksKi[2].setBehavior(arriveSB2); 
				}
				else {
					if (tanks[ZielPl2].getTeam() != tanksKi [2].getTeam()) {
					Arrive<Vector2> arriveSB2 = new Arrive<Vector2> (tanksKi [2], tanks[ZielPl2])
							.setArrivalTolerance(0.1f)
							.setTimeToTarget(0.01f)
							.setDecelerationRadius(10);
					tanksKi[2].setBehavior(arriveSB2);
					}
				}
				this.checkItemLife();
	}
	
    public void spawnTankP1() {
    	int player1 = Integer.parseInt(MenuScreen.teams.get(0));
    	if (inputMethod == "keyboard") {
			tanks [0] = new Tank(this, spawnPointTankPlayer1, inputKeyboard, player1, settings);
		} else if (inputMethod == "gamepad") {
			tanks [0] = new Tank(this, spawnPointTankPlayer1, inputGamepad, player1, settings);
		} else if (inputMethod == "maus") {
			tanks [0] = new Tank(this, spawnPointTankPlayer1, inputMouse, player1, settings);
		}
    	registerAfterUpdate(tanks [0]);
    }
    
	public void spawnTankP2(){
		if(MenuScreen.multiplayer==true){
			int team = Integer.parseInt(MenuScreen.teams.get(1));
			if(MenuScreen.inputPlayer2==1){
				tanks [1] = new Tank(this, spawnPointTankPlayer2, inputKeyboard, team, settings2);
			}
			if(MenuScreen.inputPlayer2==2){
				tanks [1] = new Tank(this, spawnPointTankPlayer2, inputMouse, team, settings2);
			}
			if(MenuScreen.inputPlayer2==3){
				tanks [1] = new Tank(this, spawnPointTankPlayer2, inputGamepad, team, settings2);
			}
		}
		registerAfterUpdate(tanks [1]);
	}
		
	public Tank getSpielerTank() {
		return tanks [0];

	}
	public Tank [] settanks() {
		return tanks;
	}

}

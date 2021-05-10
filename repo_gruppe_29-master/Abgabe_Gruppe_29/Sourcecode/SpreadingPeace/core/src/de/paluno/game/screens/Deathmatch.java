package de.paluno.game.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.SEPgame;
import de.paluno.game.gameobjects.Colour;
import de.paluno.game.gameobjects.Ghost;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.gameobjects.TankKi;
import de.paluno.game.gameobjects.Team;
import de.paluno.game.gameobjects.Updateable;
import de.paluno.game.input.KiInputProviderDM;

public class Deathmatch extends de.paluno.game.screens.Gamemode implements com.badlogic.gdx.Screen {
	
	KiInputProviderDM inputKiDM = new KiInputProviderDM();
	Random random = new Random();
	Sound backgroundmusic;
	long soundid;
	int zielscheibenPositionNeuX = random.nextInt(1900); // zufällige X-Koordinate für den Spawn der Zielscheibe im
															// Poxelbereich bis 700
	int zielscheibenPositionNeuY = random.nextInt(1000); // zufällige Y-Koordinate für den Spawn der Zielscheibe im
															// Pixelbereich bis 450
	public static Tank[] tanks = new Tank[2];
	public static TankKi[] tanksKi = new TankKi[4];
	Vector2 spawnPointZielscheibe = new Vector2(zielscheibenPositionNeuX, zielscheibenPositionNeuY);
	private Colour tank1Colour, tank2Colour, tank3Colour, tank4Colour, tank5Colour;

	Vector2 spawnPointTankPlayer1 = new Vector2(100, 100);
	Vector2 spawnPointTankPlayer2 = new Vector2(700, 100);
	Vector2 spawnPointTankKi1 = new Vector2(700, 400);
	Vector2 spawnPointTankKi2 = new Vector2(100, 400);
	Vector2 spawnPointTankKi3 = new Vector2(400, 400);
	
	private ArrayList<Integer> team= new ArrayList<Integer>();
	private boolean dead1, dead2, dead3, dead4, dead5;
	private boolean same;
	private int deadcounter;
	private float drawTime;
	
	private HashMap<Integer, Team> teams = new HashMap<Integer, Team>(); 


	public Deathmatch(SEPgame game, String inputMethod, ArrayList<String> settings, ArrayList<String> settings2,
			String map, ArrayList<String> teamSetup) {
		super(game, inputMethod, settings, settings2, map);
		backgroundmusic = Gdx.audio.newSound(Gdx.files.internal("Demise_Short.wav"));
		soundid = backgroundmusic.loop(0.15f);
		hud = new Hud_Deathmatch(game.batch, this, game);
		dead1 = false;
		dead2 = false;
		dead3 = false;
		dead4 = false;
		dead5 = false;
		deadcounter = 0;
		drawTime = 0;
		
		this.setupTeams(teamSetup);
	}
	
	HashMap<Integer, Team> tankInTeam = new HashMap<Integer, Team>();
	
	protected void setupTeams(ArrayList<String> teamSetup) 
	{
		for (int i = 0; i < teamSetup.size(); i++) {
			int tankIndex = i + 1;
			int teamNumber = Integer.parseInt(teamSetup.get(i));
			if (!this.teams.containsKey(Integer.valueOf(teamNumber))) {
				this.teams.put(Integer.valueOf(teamNumber), new Team());
			}
			
			this.tankInTeam.put(Integer.valueOf(tankIndex), this.teams.get(Integer.valueOf(teamNumber)));
		}
	}

	public void start() { // ersetzt ehemalige show() methode, da diese in gamemode ausgelagert wurde ->
			try {				// "show()" sachen also hier rein
		this.setupColour();
		spawnTankP1();
		spawnTankP2();
		
		this.addTank(1, Deathmatch.gettanks()[0]);
		this.addTank(2, Deathmatch.gettanks()[1]);
		
		spawnTankKi();
		spawnItems(true, true, true, true, false);
		buildTilemapBodies(tiledMap, world, "CollisionObjects", 0.001f);
		buildTilemapBodies(tiledMap, world, "WasserObjects", 0.001f);
		buildTilemapBodies(tiledMap, world, "TreeObjects", 0.001f);

			}
			catch(Exception e)
			{	
				backgroundmusic.stop();
				backgroundmusic.dispose(); //try catch block fuer den errorscreen wieder einkommentieren vor abnahme
				game.setScreen(new ErrorScreen(super.game));
			}
	}
	
	public void addTank(int playerNum, Tank tank) {
		if (tank != null) {
		super.addTank(playerNum, tank);
		if(this.tankInTeam.containsKey(Integer.valueOf(playerNum))) {
			Team team = this.tankInTeam.get(Integer.valueOf(playerNum));
			team.addTank(tank);
		}
		}
	}
	
	
	public void tacticalNuke2()
	{
		for(Tank tank: tanksKi) {
			if (tank != null&&tank.getHealth()>0) {
				tank.setHealth(tank.getHealth()/2);				
			}
		}

	}
	
	public void setupColour() {
		tank1Colour = MenuScreen.colours.get(0);
		tank2Colour = MenuScreen.colours.get(1);
		tank3Colour = MenuScreen.colours.get(2);
		tank4Colour = MenuScreen.colours.get(3);
		tank5Colour = MenuScreen.colours.get(4);
	}

	public void stopSounds()
	{
		backgroundmusic.stop();
		backgroundmusic.dispose();
	}
	
	public void spawnTankKi(){
		
		int teamKi1 = Integer.parseInt(MenuScreen.teams.get(2));
		int teamKi2 = Integer.parseInt(MenuScreen.teams.get(3));
		int teamKi3 = Integer.parseInt(MenuScreen.teams.get(4));
		

		if(MenuScreen.playerState == PlayerState.KI) {//Wenn Tank2 als Ki spielt
			int teamKi4 = Integer.parseInt(MenuScreen.teams.get(1));
			if (teamKi4 != 0) {
				tanksKi[3] = new TankKi(this, new Vector2(200, 300), inputKiDM, teamKi4, settings, tank2Colour, "Tank2");
				team.add(teamKi4);
				deadcounter++;
				if (this.tankInTeam.containsKey(Integer.valueOf(2))) {
					Team team = this.tankInTeam.get(Integer.valueOf(2));
					team.addTank(tanksKi[3]);
				}

			}
			if (tanksKi[3] != null) {
				registerAfterUpdate(tanksKi[3]);
			}
		}
		
		
		if (teamKi1 != 0) {
			tanksKi[0] = new TankKi(this, new Vector2(300, 200), inputKiDM, teamKi1, settings, tank3Colour, "Tank3");
			team.add(teamKi1);
			deadcounter++;
			if (this.tankInTeam.containsKey(Integer.valueOf(3))) {
				Team team = this.tankInTeam.get(Integer.valueOf(3));
				team.addTank(tanksKi[0]);
			}
		}
		
		if (teamKi2 != 0) {
			tanksKi[1] = new TankKi(this, new Vector2(500, 450), inputKiDM, teamKi2, settings, tank4Colour, "Tank4");
			team.add(teamKi2);
			deadcounter++;
			if (this.tankInTeam.containsKey(Integer.valueOf(4))) {
				Team team = this.tankInTeam.get(Integer.valueOf(4));
				team.addTank(tanksKi[1]);
			}
		}
		if (teamKi3 != 0) {
			tanksKi[2] = new TankKi(this, new Vector2(600, 300), inputKiDM, teamKi3, settings, tank5Colour, "Tank5");
			team.add(teamKi3);
			deadcounter++;
			if (this.tankInTeam.containsKey(Integer.valueOf(5))) {
				Team team = this.tankInTeam.get(Integer.valueOf(5));
				team.addTank(tanksKi[2]);
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
		//throw new RuntimeException();
	}

	public void spawnTankP1() {
		int player1 = Integer.parseInt(MenuScreen.teams.get(0));
		if (inputMethod == "keyboard") {
			tanks[0] = new Tank(this, spawnPointTankPlayer1, inputKeyboard, player1, settings, tank1Colour, "Player1");
			team.add(player1);
			deadcounter++;
		} else if (inputMethod == "gamepad") {
			tanks[0] = new Tank(this, spawnPointTankPlayer1, inputGamepad, player1, settings, tank1Colour, "Player1");
			team.add(player1);
			deadcounter++;
		} else if (inputMethod == "maus") {
			tanks[0] = new Tank(this, spawnPointTankPlayer1, inputMouse, player1, settings, tank1Colour, "Player1");
			team.add(player1);
			deadcounter++;
			Pixmap pm = new Pixmap(Gdx.files.internal("CursorScheibe16x16.png"));
			Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
			pm.dispose();
		}
		registerAfterUpdate(tanks[0]);
	}

	public void spawnTankP2() {
		if (MenuScreen.playerState == PlayerState.ACTIVE) {
			int teamnr = Integer.parseInt(MenuScreen.teams.get(1));
			if (MenuScreen.inputPlayer2 == PlayerInput.KEYBOARD) {
				tanks[1] = new Tank(this, spawnPointTankPlayer2, inputKeyboard, teamnr, settings2, tank2Colour, "Player2");
				team.add(teamnr);
				deadcounter++;
			}
			if (MenuScreen.inputPlayer2 == PlayerInput.MOUSE) {
				tanks[1] = new Tank(this, spawnPointTankPlayer2, inputMouse, teamnr, settings2, tank2Colour, "Player2");
				Pixmap pm = new Pixmap(Gdx.files.internal("CursorScheibe16x16.png"));
				team.add(teamnr);
				Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
				pm.dispose();
				deadcounter++;
			}
			if (MenuScreen.inputPlayer2 == PlayerInput.GAMEPAD) {
				tanks[1] = new Tank(this, spawnPointTankPlayer2, inputGamepad, teamnr, settings2, tank2Colour, "Player2");
				team.add(teamnr);
				deadcounter++;
			}
		}
		registerAfterUpdate(tanks[1]);
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
//		debugRenderer.render(world, camera.combined); //zum ansehen der hitboxen
		spriteBatch.setProjectionMatrix(camera.combined);
		hud.stage.draw();
		hud.stage.act();
//		sr.setProjectionMatrix(camera.combined);
//		sr.begin(ShapeType.Line);
//		for (TankKi tankKi : tanksKi) {
//			sr.line(tankKi.getPosition(), tankKi.getNormal());
//		}
//		sr.end();
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
		
		
		setArriveforKI(this, tanksKi[0]);
		setArriveforKI(this, tanksKi[1]);
		setArriveforKI(this, tanksKi[2]);
		setArriveforKI(this, tanksKi[3]);
		
		if (AllPlayersDead() && AllKiSeeNothing()) {
			drawTime += Gdx.graphics.getDeltaTime();
			if (drawTime > 15f) {
			super.game.setScreen(new WinScreenCollection(game, "Draw"));
			}
		}
		
		this.checkItemLife();
	}
	public void updatePhase2(float delta) {	
		
		boolean allDead = true;
		for (int x = 0; x < tanksKi.length; x++) {
			if (tanksKi[x] != null) {
				if (tanksKi[x].getHealth() >= 0) {
					allDead = false;
				}

			}
		}

		int AnzahlKi = 0;
		for (int x = 0; x < tanksKi.length; x++) {
			if (tanksKi[x] != null) {
				if (tanksKi[x].getHealth() > -1) {
					AnzahlKi++;
				}
			}
		}

		// Singleplayer
		if (tanks[1] == null) {
			if (tanks[0].getHealth() > -1 && allDead) {

//			if (tanks[0].getHealth() > 0 && tanksKi[0].getHealth() <= 0 && tanksKi[1].getHealth() <= 0
//					&& tanksKi[2].getHealth() <= 0) {
				camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				super.game.setScreen(new WinScreen(game));
				backgroundmusic.stop();
				Sound sound = Gdx.audio.newSound(Gdx.files.internal("clapping.mp3"));
				sound.play(1f);
			}

//			if (tanks[0].getHealth() <= 0 && tanksKi[0].getHealth() > 0 && tanksKi[1].getHealth() <= 0
//					&& tanksKi[2].getHealth() <= 0
//					|| tanks[0].getHealth() <= 0 && tanksKi[0].getHealth() <= 0 && tanksKi[1].getHealth() > 0
//							&& tanksKi[2].getHealth() <= 0
//					|| tanks[0].getHealth() <= 0 && tanksKi[0].getHealth() <= 0 && tanksKi[1].getHealth() <= 0
//							&& tanksKi[2].getHealth() > 0) {
			if (tanks[0].getHealth() < 0 && AnzahlKi == 1) {
				camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				super.game.setScreen(new KIWinScreen(game));
				backgroundmusic.stop();
			}
		}
		// Coop
		else {
			// Player 1 gewinnt

//			if (tanks[0].getHealth() > 0 && tanks[1].getHealth() <= 0 && tanksKi[0].getHealth() <= 0
//					&& tanksKi[1].getHealth() <= 0 && tanksKi[2].getHealth() <= 0) {

			if (tanks[0].getHealth() >= 0 && tanks[1].getHealth() < 0 && allDead) {
				camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				super.game.setScreen(new PlayerOneWinScreen(game));
				backgroundmusic.stop();
			}
			// Player 2 gewinnt

//			else if (tanks[0].getHealth() <= 0 && tanks[1].getHealth() > 0 && tanksKi[0].getHealth() <= 0
//					&& tanksKi[1].getHealth() <= 0 && tanksKi[2].getHealth() <= 0) {

			if (tanks[0].getHealth() < 0 && tanks[1].getHealth() >= 0 && allDead) {
				camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				super.game.setScreen(new PlayerTwoWinScreen(game));
				backgroundmusic.stop();
				Sound sound = Gdx.audio.newSound(Gdx.files.internal("clapping.mp3"));
				sound.play(1f);
			}
			// KI gewinnt

//			else if (tanksKi[0] != null && tanksKi[1] != null && tanksKi[2] != null) {
//				if (tanks[0].getHealth() <= 0 && tanks[1].getHealth() <= 0 && tanksKi[0].getHealth() > 0
//						&& tanksKi[1].getHealth() <= 0 && tanksKi[2].getHealth() <= 0
//						|| tanks[0].getHealth() <= 0 && tanks[1].getHealth() <= 0 && tanksKi[0].getHealth() <= 0
//								&& tanksKi[1].getHealth() > 0 && tanksKi[2].getHealth() <= 0
//						|| tanks[0].getHealth() <= 0 && tanks[1].getHealth() <= 0 && tanksKi[0].getHealth() <= 0
//								&& tanksKi[1].getHealth() <= 0 && tanksKi[2].getHealth() > 0) {

			else if (tanks[0].getHealth() == -1 && tanks[1].getHealth() == -1 && AnzahlKi == 1) {
				camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				super.game.setScreen(new KIWinScreen(game));
				backgroundmusic.stop();
			}

//			} else if (tanksKi[0] != null && tanksKi[1] != null && tanksKi[2] == null) {
//				if (tanks[0].getHealth() <= 0 && tanks[1].getHealth() <= 0 && tanksKi[0].getHealth() > 0
//						&& tanksKi[1].getHealth() <= 0
//						|| tanks[0].getHealth() <= 0 && tanks[1].getHealth() <= 0 && tanksKi[0].getHealth() <= 0
//								&& tanksKi[1].getHealth() > 0
//						|| tanks[0].getHealth() <= 0 && tanks[1].getHealth() <= 0 && tanksKi[0].getHealth() <= 0
//								&& tanksKi[1].getHealth() <= 0) {
//					camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//					super.game.setScreen(new KIWinScreen(game));
//					backgroundmusic.stop();
//				}
//			} else if (tanksKi[0] != null && tanksKi[1] == null && tanksKi[2] == null) {
//				if (tanks[0].getHealth() <= 0 && tanks[1].getHealth() <= 0 && tanksKi[0].getHealth() > 0
//						|| tanks[0].getHealth() <= 0 && tanks[1].getHealth() <= 0 && tanksKi[0].getHealth() <= 0
//						|| tanks[0].getHealth() <= 0 && tanks[1].getHealth() <= 0 && tanksKi[0].getHealth() <= 0) {
//					camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//					super.game.setScreen(new KIWinScreen(game));
//					backgroundmusic.stop();
//				}
//			}
		}
		if (tanks[0].getHealth() < 5) {
			backgroundmusic.setPitch(soundid, 1.1f);
		}
//		this.checkTanks();
		
		 
		if (this.numberOfTeamsAlive()==1) { 
			endScreenAufruf(); 
		} 
 
	
	}
	
	public int numberOfTeamsAlive() { 
		 
		int count = 0; 
 
		for (Team team : this.teams.values()) { 
			if (team.isAlive()) { 
				count++; 
			} 
		}
		return count;
	}
	
	
	public Team getWinningTeam() { 
		Team winningTeam=null; 
		 
		for (Team team : this.teams.values()) { 
			if (team.isAlive()) {
				winningTeam=team;	
			} 
		} 
		return winningTeam; 
	} 
	 
	 
	public void endScreenAufruf() { 
		 
		if(this.getWinningTeam()==this.teams.get(Integer.valueOf(1))) { 
			super.game.setScreen(new WinScreenCollection(game, "Team1")); 
			backgroundmusic.stop(); 
			 
		} else if(this.getWinningTeam()==this.teams.get(Integer.valueOf(2))) { 
			super.game.setScreen(new WinScreenCollection(game, "Team2")); 
			backgroundmusic.stop(); 
			 
		} else if(this.getWinningTeam()==this.teams.get(Integer.valueOf(3))) { 
			super.game.setScreen(new WinScreenCollection(game, "Team3")); 
			backgroundmusic.stop(); 
			 
		} else if(this.getWinningTeam()==this.teams.get(Integer.valueOf(4))) { 
			super.game.setScreen(new WinScreenCollection(game, "Team4")); 
			backgroundmusic.stop(); 
			 
		} else if(this.getWinningTeam()==this.teams.get(Integer.valueOf(5))) { 
			super.game.setScreen(new WinScreenCollection(game, "Team5")); 
			backgroundmusic.stop(); 
		} 
	}
	
//	public void checkTanks() {
//		if(tanks[0].getHealth() <= 0 && dead1 == false) {
//			team.set(0, 1000);
////			team.remove(tanks[0].getTeam());
//			System.out.println(team);
//			dead1 = true;
//			deadcounter--;
//		}
//		if(tanks[1].getHealth() <= 0 && dead2 == false) {
//			team.set(1, 1000);
////			team.remove(tanks[1].getTeam());
//			System.out.println(team);
//			dead2 = true;
//			deadcounter--;
//		}
//		if(tanksKi[0].getHealth() <= 0 && dead3 == false) {
//			team.set(2, 1000);
////			team.remove(tanksKi[0].getTeam());
//			System.out.println(team);
//			dead3 = true;
//			deadcounter--;
//		}
//		if(tanksKi[1].getHealth() <= 0 && dead4 == false) {
//			team.set(3, 1000);
////			team.remove(tanksKi[1].getTeam());
//			System.out.println(team);
//			dead4 = true;
//			deadcounter--;
//		}
//		if(tanksKi[2].getHealth() <= 0 && dead5 == false) {
//			team.set(4, 1000);
////			team.remove(tanksKi[2].getTeam());
//			System.out.println(team);
//			dead5 = true;
//			deadcounter--;
//		}
//		
////		for(int i = 0; i < team.size(); i++) {
////			if(team.get(0).e) {
////				
////			}
////			
////		}
//	}

	public Tank getSpielerTank() {
		return tanks[0];

	}

	public Tank[] settanks() {
		return tanks;
	}
	public static Tank[] gettanks() {
		return tanks;
	}
	public static TankKi[] gettanksKi() {
		return tanksKi;
	}
	public static void setArriveforKI(Gamemode playScreen, TankKi actor) {
		if (actor != null && actor.getHealth() > -1) {
			Tank target = KiInputProviderDM.Ziel(actor);
			Vector2 PastPosTarget =actor.getZielPastPos();
			Ghost PastPosTargetGhost = new Ghost(playScreen, PastPosTarget);
			
			if (actor != target) {
				Arrive<Vector2> arriveSB0 = new Arrive<Vector2>(actor, target).setArrivalTolerance(0.1f)
						.setTimeToTarget(0.01f).setDecelerationRadius(10);
				PrioritySteering<Vector2> behaviors = new PrioritySteering<Vector2>(actor, 0.01f).add(arriveSB0);
				actor.setPrioritySteering(behaviors);
				//actor.setBehavior(arriveSB0);
			}
			else {
				
				if (PastPosTarget.x != 0 && PastPosTarget.y != 0) {
					Arrive<Vector2> arriveSB0 = new Arrive<Vector2>(actor, PastPosTargetGhost).setArrivalTolerance(0.1f)
							.setTimeToTarget(0.01f).setDecelerationRadius(10);
					PrioritySteering<Vector2> behaviors = new PrioritySteering<Vector2>(actor, 0.01f).add(arriveSB0);
					actor.setPrioritySteering(behaviors);
					//actor.setBehavior(arriveSB0);
				}
			}
		}
	}
	public boolean AllPlayersDead() {
		if (tanks[0] != null && tanks[0].getHealth() > -1) return false;
		if (tanks[1] != null && tanks[1].getHealth() > -1) return false;
		return true;
	}
	public boolean AllKiSeeNothing() {
		for (int i = 0; i < 4; i++) {
			if (tanksKi[i] != null && tanksKi[i].getHealth() > -1 && KiInputProviderDM.Ziel(tanksKi[i]) != tanksKi[i]) {
				return false;
			}
		}
		return true;
	}

}

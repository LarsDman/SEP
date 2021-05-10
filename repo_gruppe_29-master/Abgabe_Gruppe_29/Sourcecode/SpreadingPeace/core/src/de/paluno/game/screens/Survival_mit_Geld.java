package de.paluno.game.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.SEPgame;
import de.paluno.game.gameobjects.Coin;
import de.paluno.game.gameobjects.Poison;
import de.paluno.game.gameobjects.Power;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.gameobjects.TankKi;
import de.paluno.game.gameobjects.Updateable;
import de.paluno.game.gameobjects.Velocity;

public class Survival_mit_Geld extends Survival {
	static int taler = 0;
	protected Coin coin;
	static boolean istSurvival_mit_Geld = true;
	
	public Survival_mit_Geld(SEPgame game, String inputMethod, ArrayList<String> settings, ArrayList<String> settings2, String map) {
		super(game, inputMethod, settings, settings2, map);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void start() {
		hud = new Hud_Survival_mit_Geld(game.batch, this, game);
		spawnTanks();
		spawnItems();
		buildTilemapBodies(tiledMap, world, "CollisionObjects", 0.001f);
		buildTilemapBodies(tiledMap, world, "WasserObjects", 0.001f);
		buildTilemapBodies(tiledMap, world, "TreeObjects", 0.001f);
		this.addTank(1, playerTank);
	}
	
	public void coindrop(Vector2 spawnLocation) {
//		Vector2 spawnLocation = tankKi.getBody().getPosition();
		System.out.println("eine Muenze wurde gedroppt!!!");
//		Vector2 spawnPointCoin = new Vector2 (600,500);
		coin = new Coin(this , spawnLocation);
		registerAfterUpdate(coin);
		
	}
	
	protected void spawnItems() {
		taler = Schatzkammer.getMuenzen();
		System.out.println("Muenzen am Start von Survival = " + taler);
		
		Vector2 spawnPointPoison = new Vector2(200,100);
		Vector2 spawnPointPower = new Vector2(200, 200);
		Vector2 spawnPointVelocity = new Vector2(200, 300);
	
		if (Schatzkammer.getSilber()== 1) {
			poison = new Poison(this, spawnPointPoison );
			registerAfterUpdate(poison);
		}
		else if (Schatzkammer.getSilber()== 2) {
			velocity = new Velocity(this, spawnPointVelocity);
			registerAfterUpdate(velocity);
		}
		else if (Schatzkammer.getSilber()== 3) {
			power = new Power(this, spawnPointPower);
			registerAfterUpdate(power);
		}
	}
	
	public static int getTaler() {
		return taler;
	}
	public static boolean getIstSurvival_mit_Geld() {
		return istSurvival_mit_Geld;
	}
	
	public static void sammleTaler() {
		System.out.println("Taler vorher: " + taler);
		taler= taler + 1;
		System.out.println("Taler nachher: " + taler);
	}
	
//	public void playerDeath() {
//		game.setScreen(new Schatzkammer( game,  inputMethod,  settings,  settings2, map));		
//	}
	
	protected void checkItemLife() {
//	 	Methode muss �berschrieben werden, f�hrt sonst zu error
		
		}
	public void updatePhase(float delta) {
		System.out.println(coin);
		if (playerTank.getHealth() <= 0) {
//			camera.setToOrtho(false, width, height);
//			super.game.batch.setProjectionMatrix(new SpriteBatch().getProjectionMatrix());

			super.game.setScreen(new Schatzkammer( game,  inputMethod,  settings,  settings2, "schatzkammer.tmx"));
//			super.game.setScreen(new GameOverScreen(game, score));
			
			backgroundmusic.stop();
			Sound sound = Gdx.audio.newSound(Gdx.files.internal("clapping.mp3"));
			sound.play(1f);
		}

		hud.update(delta);

		for (Updateable i : updatableObjects) {
			i.update(delta);
		}
		for (Object j : gameObjectsToRegisterAfterUpdate) {
			register(j);
		}
		for (final Object k : gameObjectsToForgetAfterUpdate) {
			if (k instanceof TankKi) {
				Zombiespawner();
				final Vector2 spawnCoin = ((TankKi) k).getBody().getPosition().cpy();
				Gdx.app.postRunnable(new Runnable() {	
					public void run() {
						coindrop(spawnCoin);
					}
				});
//				coindrop((TankKi)k);
			}
			forget(k);
		}
		gameObjectsToRegisterAfterUpdate.clear();
		gameObjectsToForgetAfterUpdate.clear();

		for (TankKi tankKi : tanksKiList) {
			tankKi.rayCast(playerTank);
		}

		this.checkItemLife();
		if (playerTank.getHealth() < 20)
			backgroundmusic.setPitch(soundid, 1.01f);

		for (TankKi tankKi : tanksKiList) {
			if (tankKi.haslineofsight()) {
				tankKi.setGhosts(this.getSpielerTank());
			}
			
			if (tankKi.getGhost() != null) {
				Arrive<Vector2> arrive = new Arrive<Vector2>(tankKi, tankKi.getGhost()).setArrivalTolerance(0.1f)
						.setTimeToTarget(0.01f).setDecelerationRadius(10);
				PrioritySteering<Vector2> behaviors = new PrioritySteering<Vector2>(tankKi, 0.01f).add(tankKi.whiskersAvoid).add(arrive);
				tankKi.setPrioritySteering(behaviors);
			} else {
				Arrive<Vector2> arrive = new Arrive<Vector2>(tankKi, tankKi).setArrivalTolerance(0.1f)
						.setTimeToTarget(0.01f).setDecelerationRadius(10);
				PrioritySteering<Vector2> behaviors = new PrioritySteering<Vector2>(tankKi, 0.01f).add(tankKi.whiskersAvoid).add(arrive);
				tankKi.setPrioritySteering(behaviors);;
			}
		}
	}
	
	public void tacticalNuke2()
	{
		for(Tank tank: tanksKiList) {
			if (tank != null) {
				tank.setHealth(tank.getHealth()/2);				
			}
		}

	}
}

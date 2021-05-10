package de.paluno.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;

import de.paluno.game.gameobjects.BounceFlower;
import de.paluno.game.gameobjects.Coin;
import de.paluno.game.gameobjects.Exit_Door;
import de.paluno.game.gameobjects.F_L_O_W_E_R;
import de.paluno.game.gameobjects.FastFlower;
import de.paluno.game.gameobjects.FlagHolder;
import de.paluno.game.gameobjects.Item;
import de.paluno.game.gameobjects.Kiste;
import de.paluno.game.gameobjects.NormalFlower;
import de.paluno.game.gameobjects.Trumpitem;
import de.paluno.game.gameobjects.Power;
import de.paluno.game.gameobjects.SplittedFlower;
import de.paluno.game.gameobjects.TacticalNuke;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.gameobjects.TankKi;
import de.paluno.game.gameobjects.Tower;
import de.paluno.game.gameobjects.TowerFlower;
import de.paluno.game.gameobjects.Bosstank;
import de.paluno.game.gameobjects.Tree;
import de.paluno.game.gameobjects.TripleFlower;
import de.paluno.game.gameobjects.Poison;
import de.paluno.game.gameobjects.Velocity;
import de.paluno.game.gameobjects.Wall;
import de.paluno.game.gameobjects.WallBlock;
import de.paluno.game.screens.CaptureTheFlag;
import de.paluno.game.screens.Gamemode;
import de.paluno.game.screens.Schatzkammer;
import de.paluno.game.screens.MenuScreen;
import de.paluno.game.screens.Survival_mit_Geld;

public class CollisionHandler extends java.lang.Object implements com.badlogic.gdx.physics.box2d.ContactListener {

	protected Gamemode playScreen;
	Random random;

	public CollisionHandler(Gamemode playScreen) {
		this.playScreen = playScreen;
		random = new Random();
	}

	public void beginContact(com.badlogic.gdx.physics.box2d.Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		Object a = fa.getUserData();
		Object b = fb.getUserData();

		Tree tree = null;
		Wall wall = null;

		F_L_O_W_E_R flower = null;
		F_L_O_W_E_R flower2 = null;

		Tank tank = null;
		TankKi tankKi = null;
		TankKi tankKi2 = null;

		Item item = null;

		Kiste kiste = null;
		Exit_Door exit = null;
		Coin coin = null;

		FlagHolder flagholder = null;

		if (a != null && b != null) {
//			Flower-Oberklasse kollidiert mit Flower:
			if (a instanceof F_L_O_W_E_R && b instanceof F_L_O_W_E_R) {
				flower = (F_L_O_W_E_R) a;
				flower2 = (F_L_O_W_E_R) b;
				collideFlowerFlower(flower, flower2);
			}

//			Flower-Oberklasse kollidiert mit WallBlock:			
			else if (a instanceof F_L_O_W_E_R && b instanceof Wall || a instanceof Wall && b instanceof F_L_O_W_E_R) {
				if (a instanceof F_L_O_W_E_R) {
					flower = (F_L_O_W_E_R) a;
					wall = (Wall) b;
					collideFlowerWall(flower);
				} else {
					flower = (F_L_O_W_E_R) b;
					wall = (Wall) a;
					collideFlowerWall(flower);
				}
			}

//			Flower-Oberklasse kollidiert mit Item
			else if (a instanceof F_L_O_W_E_R && b instanceof Item || a instanceof Item && b instanceof F_L_O_W_E_R) {
				if (a instanceof F_L_O_W_E_R) {
					flower = (F_L_O_W_E_R) a;
					item = (Item) b;
					collideFlowerItem(flower, item);
				} else {
					flower = (F_L_O_W_E_R) b;
					item = (Item) a;
					flower.explode();
					collideFlowerItem(flower, item);
				}
			}

//			TowerFlower kollidiert mit Tower			
			else if (a instanceof TowerFlower && b instanceof Tower || a instanceof Tower && b instanceof TowerFlower) {

			}

// 			Flower-Oberklasse kollidiert mit Tree:			
			else if (a instanceof F_L_O_W_E_R && b instanceof Tree || a instanceof Tree && b instanceof F_L_O_W_E_R) {
				if (a instanceof F_L_O_W_E_R) {
					flower = (F_L_O_W_E_R) a;
					tree = (Tree) b;
					collideFlowerTree(flower, tree);
				} else {
					flower = (F_L_O_W_E_R) b;
					tree = (Tree) a;
					collideFlowerTree(flower, tree);
				}
			}
//			TowerFlower kollidiert mit Bosstank	 Patrick	
			else if (a instanceof TowerFlower && b instanceof Bosstank
					|| a instanceof Bosstank && b instanceof TowerFlower) {
				// mach gar nichts
			}

//			Flower-Oberklasse kollidiert mit Tank			
			else if (a instanceof F_L_O_W_E_R && b instanceof Tank || a instanceof Tank && b instanceof F_L_O_W_E_R) {
				if (a instanceof F_L_O_W_E_R) {
					flower = (F_L_O_W_E_R) a;
					tank = (Tank) b;
					this.collideFlowerTank(flower, tank);
				} else {
					flower = (F_L_O_W_E_R) b;
					tank = (Tank) a;
					this.collideFlowerTank(flower, tank);
				}
			}
//			Tank kollidiert mit Coin:			
			else if (a instanceof Coin && b instanceof Tank || a instanceof Tank && b instanceof Coin) {
				if (a instanceof Coin) {
					coin = (Coin) a;
					tank = (Tank) b;
					this.collideTankCoin(coin, tank);
				} else {
					coin = (Coin) b;
					tank = (Tank) a;
					this.collideTankCoin(coin, tank);
				}
			}
// 			Tank kollidiert mit Kiste:			
			else if (a instanceof Tank && b instanceof Kiste || a instanceof Kiste && b instanceof Tank) {
				if (a instanceof Tank) {
					tank = (Tank) a;
					kiste = (Kiste) b;
					collideTankKiste(tank, kiste);
				} else {
					tank = (Tank) b;
					kiste = (Kiste) a;
					collideTankKiste(tank, kiste);
				}
			}
// 			Tank kollidiert mit Exit:			
			else if (a instanceof Tank && b instanceof Exit_Door || a instanceof Exit_Door && b instanceof Tank) {
				if (a instanceof Tank) {
					tank = (Tank) a;
					exit = (Exit_Door) b;
					collideTankExit(tank, exit);
				} else {
					tank = (Tank) b;
					exit = (Exit_Door) a;
					collideTankExit(tank, exit);
				}
			}
//			Tank kollidiert mit Item
			else if (a instanceof Item && b instanceof Tank || a instanceof Tank && b instanceof Item) {
				if (a instanceof Item) {
					item = (Item) a;
					tank = (Tank) b;
					this.collideTankItem(tank, item);
				} else {
					item = (Item) b;
					tank = (Tank) a;
					this.collideTankItem(tank, item);
				}
			}
// 			Tank kollidiert mit Flagholder:			
			else if (a instanceof Tank && b instanceof FlagHolder || a instanceof FlagHolder && b instanceof Tank) {
				if (a instanceof Tank) {
					tank = (Tank) a;
					flagholder = (FlagHolder) b;
					collideTankFlagHolder(tank, flagholder);
				} else {
					tank = (Tank) b;
					flagholder = (FlagHolder) a;
					collideTankFlagHolder(tank, flagholder);
				}
			}
// 			Flower kollidiert mit Flagholder:			
			else if (a instanceof F_L_O_W_E_R && b instanceof FlagHolder
					|| a instanceof FlagHolder && b instanceof F_L_O_W_E_R) {
				if (a instanceof F_L_O_W_E_R) {
					flower = (F_L_O_W_E_R) a;
					flagholder = (FlagHolder) b;
					collideFlowerFlagholder(flower, flagholder);
				} else {
					flower = (F_L_O_W_E_R) b;
					flagholder = (FlagHolder) a;
					collideFlowerFlagholder(flower, flagholder);
				}
			}
//			TankKi kollidiert mit Tankki
			else if (a instanceof TankKi && b instanceof TankKi) {
				tankKi = (TankKi) a;
				tankKi2 = (TankKi) b;
				collideTankTank(tankKi, tankKi2);
			}
		}
	}

	public void endContact(com.badlogic.gdx.physics.box2d.Contact contact) {
	}

	public void preSolve(com.badlogic.gdx.physics.box2d.Contact contact,
			com.badlogic.gdx.physics.box2d.Manifold oldManifold) {
	}

	public void postSolve(com.badlogic.gdx.physics.box2d.Contact contact,
			com.badlogic.gdx.physics.box2d.ContactImpulse impulse) {
	}

	// Items
	private void collideTankItem(Tank tank, Item item) {
		if (item instanceof Poison) {
			Poison poison = null;
			poison = (Poison) item;
			tank.setHealth(tank.getHealth() - poison.getDamage());
			poison.destroy();
			if (tank.getHealth() < 0) {
				tank.destroy();
			}
		} else if (item instanceof Velocity) {
			Velocity velocity = null;
			velocity = (Velocity) item;
			tank.setVelocityPushTime((long) System.currentTimeMillis());
			velocity.destroy();
		} else if (item instanceof Power) {
			Power power = null;
			power = (Power) item;
			if (tank.getHealth() + power.getHealth() <= 24) {
				tank.setHealth(tank.getHealth() + power.getHealth());
				if (tank.getHealth() >= 20) {
					tank.setHealth(20);
				}
				power.destroy();
			}
		} else if (item instanceof Trumpitem) { // Patrick
			this.collideTankTrumpItem(item);
		} else if (item instanceof TacticalNuke) { // patrick
			this.collideTankTacticalNuke(item);
		}
	}

	private void collideTankTrumpItem(Item item) {
		Trumpitem trump = null;
		trump = (Trumpitem) item;
		final Vector2 wallspawnpoint = new Vector2(random.nextFloat() * (900 - 70) + 70,
				random.nextFloat() * (900 - 70) + 70); // tank.getCenter();
		wallspawnpoint.set(wallspawnpoint.x + 50, wallspawnpoint.y);
		wallspawnpoint.y += 50;
		Gdx.app.postRunnable(new Runnable() {

			@Override
			public void run() {
				WallBlock wall = new WallBlock(playScreen, wallspawnpoint, 20, 20, false, true);
				playScreen.registerAfterUpdate(wall);

			}
		});
		trump.destroy();
	}

	private void collideTankTacticalNuke(Item item) {
		playScreen.tacticalNuke();
		item.destroy();
	}

	private void collideFlowerItem(F_L_O_W_E_R flower, Item item) {
		flower.explode();
	}

	private void collideFlowerFlower(F_L_O_W_E_R flower1, F_L_O_W_E_R flower2) {
		flower1.explode();
		flower2.explode();
	}

	private void collideFlowerTank(F_L_O_W_E_R flower, Tank tank) {
		if (flower instanceof NormalFlower) {
			NormalFlower normalFlower = null;
			normalFlower = (NormalFlower) flower;
			tank.setHealth(tank.getHealth() - normalFlower.getDamage());
		} else if (flower instanceof FastFlower) {
			FastFlower fastFlower = null;
			fastFlower = (FastFlower) flower;
			tank.setHealth(tank.getHealth() - fastFlower.getDamage());
		} else if (flower instanceof BounceFlower) {
			BounceFlower bounceFlower = null;
			bounceFlower = (BounceFlower) flower;
			tank.setHealth(tank.getHealth() - bounceFlower.getDamage());
		} else if (flower instanceof TripleFlower) {
			TripleFlower tripleFlower = null;
			tripleFlower = (TripleFlower) flower;
			tank.setHealth(tank.getHealth() - tripleFlower.getDamage());
		} else if (flower instanceof SplittedFlower) {
			SplittedFlower splittedFlower = null;
			splittedFlower = (SplittedFlower) flower;
			tank.setHealth(tank.getHealth() - splittedFlower.getDamage());
		} else if (flower instanceof TowerFlower) { // patrick
			this.towerflower(flower, tank);
		}
		flower.explode();
		this.ctfRespawn(tank);
		this.killFeed(flower, tank);
	}

	private void towerflower(F_L_O_W_E_R flower, Tank tank) {
		TowerFlower towerFlower = null;
		towerFlower = (TowerFlower) flower;
		tank.setHealth(tank.getHealth() - towerFlower.getDamage());
	}

	private void killFeed(F_L_O_W_E_R flower, Tank tank) {
		if (tank.getHealth() < 0) {
			Tank killer = flower.getActor();
			killer.setKillcounter(killer.getKillcounter());
			playScreen.getKilledTankList().add(0, tank.getName());
			playScreen.getKillerTankList().add(0, killer.getName());
			tank.destroy();
		}
	}

	private void ctfRespawn(Tank tank) {
		if (MenuScreen.CtF == true) {
			if (tank.getHealth() <= 0) {
				tank.respawn();
				tank.setHealth(20);
				return;
			}
		}
	}

	private void collideFlowerWall(F_L_O_W_E_R flower) {
		if (flower instanceof BounceFlower) {
			BounceFlower bounceFlower = null;
			bounceFlower = (BounceFlower) flower;
			if (bounceFlower.getWallCounter() == 2) {
				bounceFlower.explode();
			} else {
				bounceFlower.setCollisionWall(true);
			}
		} else {
			flower.explode();
		}
	}

	private void collideFlowerTree(F_L_O_W_E_R flower, Tree tree) {
		if (flower instanceof BounceFlower) {
			BounceFlower bounceFlower = null;
			bounceFlower = (BounceFlower) flower;
			if (bounceFlower.getWallCounter() == 2) {
				bounceFlower.explode();
			} else {
				bounceFlower.setCollisionWall(true);
			}
		} else {
			flower.explode();
		}
		tree.setHealth(tree.getHealth() - flower.getDamage());
		if (tree.getHealth() <= 0) {
			tree.destroy();
		}
	}

	private void collideTankTank(TankKi tank1, TankKi tank2) {
		tank1.KollisionsLoeserLinks();
		tank2.KollisionsLoeserRechts();
	}

	private void collideTankKiste(Tank tank, Kiste kiste) {
		kiste.Muenzenzahlen();
	}

	private void collideTankExit(Tank tank, Exit_Door exit) {
//	exit.start_Mode();
		Schatzkammer.Screenwechsel();

	}

	private void collideTankCoin(Coin coin, Tank tank) {
		coin.destroy();
		if (Survival_mit_Geld.getTaler() < 10) {
			Survival_mit_Geld.sammleTaler();
		}
	}

	private void collideFlowerFlagholder(F_L_O_W_E_R flower, FlagHolder flagholder) {
		if (flower instanceof BounceFlower) {
			BounceFlower bounceFlower = null;
			bounceFlower = (BounceFlower) flower;
			if (bounceFlower.getWallCounter() == 2) {
				bounceFlower.explode();
			} else {
				bounceFlower.setCollisionWall(true);
			}
		} else {
			flower.explode();
		}
	}

	private void collideTankFlagHolder(Tank tank, FlagHolder flagholder) {	
		if (flagholder.getFlag() == 1 && flagholder.getTeam() != tank.getTeam()) {
			flagholder.setFlag(0);
			tank.setFlag(1);
		} else if (flagholder.getTeam() == tank.getTeam() && tank.getFlag() == 1 && tank.getTeam() == 1) {
			tank.setFlag(0);
			CaptureTheFlag.scoreTeam1 = CaptureTheFlag.scoreTeam1 + 1;
			CaptureTheFlag.resetFlagHolder2();

		} else if (flagholder.getTeam() == tank.getTeam() && tank.getFlag() == 1 && tank.getTeam() == 2) {
			tank.setFlag(0);
			CaptureTheFlag.scoreTeam2 = CaptureTheFlag.scoreTeam2 + 1;
			CaptureTheFlag.resetFlagHolder1();

		}

	}
}

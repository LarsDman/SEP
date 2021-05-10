package de.paluno.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Fixture;

import de.paluno.game.gameobjects.F_L_O_W_E_R;
import de.paluno.game.gameobjects.Item;
import de.paluno.game.gameobjects.Poison;
import de.paluno.game.gameobjects.Power;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.gameobjects.TankKi;
import de.paluno.game.gameobjects.Target;
import de.paluno.game.gameobjects.Velocity;
import de.paluno.game.gameobjects.Wall;
import de.paluno.game.screens.Gamemode;

//The CollisionHandler should manage the outcomes of two physics objects coming in contact with one another, or ending said contact.
//In the first iteration of SpreadingPeace, the only contact that has meaning to it is the contact between an instance of Flower and an instance of Target.

public class CollisionHandler extends java.lang.Object implements com.badlogic.gdx.physics.box2d.ContactListener {

	protected Gamemode playScreen;

	public CollisionHandler(Gamemode playScreen) {
		this.playScreen = playScreen;
		// The CollisionHandler only ever needs to be instantiated once in the
		// show-method of the PlayScreen.
	}

	public void beginContact(com.badlogic.gdx.physics.box2d.Contact contact) {

		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		Wall tbw = null;
		F_L_O_W_E_R tba = null;
		F_L_O_W_E_R tbaa = null;
		Target tbb = null;
		Tank tunk = null;
		TankKi tink = null;
		TankKi tink2 = null;
		Item tbi = null;
		Power tbp = null;
		Velocity tbv = null;
		Poison tbpo = null;

		if (fa.getUserData() != null && fb.getUserData() != null) {

//			if (fa.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R
//					&& fb.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R) {

// 			Flower kollidiert mit Target:			
			if (fa.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R
					&& fb.getUserData() instanceof de.paluno.game.gameobjects.Target
					|| fa.getUserData() instanceof de.paluno.game.gameobjects.Target
							&& fb.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R) {
				System.out.println("ein Target wurde getroffen!");
				if (fa.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R) {
					tba = (F_L_O_W_E_R) fa.getUserData();
					tbb = (Target) fb.getUserData();
					collideFlowerTarget(tba, tbb);
				} else {
					tba = (F_L_O_W_E_R) fb.getUserData();
					tbb = (Target) fa.getUserData();
					collideFlowerTarget(tba, tbb);
				}
			}
//			Flower kollidiert mit Flower:
			else if (fa.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R
					&& fb.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R) {
				tba = (F_L_O_W_E_R) fa.getUserData();
				tbaa = (F_L_O_W_E_R) fb.getUserData();
				collideFlowerFlower(tba, tbaa);
			}
// 			Flower kollidiert mit Wall:			
			else if (fa.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R
					&& fb.getUserData() instanceof de.paluno.game.gameobjects.Wall || 
					fa.getUserData() instanceof de.paluno.game.gameobjects.Wall
					&& fb.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R){
				System.out.println("Eine Wall wurde getroffen!");
				if (fa.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R) {
					tba = (F_L_O_W_E_R) fa.getUserData();
					tbw = (Wall) fb.getUserData();
					collideFlowerWall(tba, tbw);
				} else {
					tba = (F_L_O_W_E_R) fb.getUserData();
					tbw = (Wall) fa.getUserData();
					collideFlowerWall(tba, tbw);
				}
			}
// 			Tank kollidiert mit Wall:			
			else if (fa.getUserData() instanceof de.paluno.game.gameobjects.TankKi
					&& fb.getUserData() instanceof de.paluno.game.gameobjects.Wall || 
					fa.getUserData() instanceof de.paluno.game.gameobjects.Wall
					&& fb.getUserData() instanceof de.paluno.game.gameobjects.TankKi){
				System.out.println("Ein KI-Panzer hat eine Wand gerammt!");
				if (fa.getUserData() instanceof de.paluno.game.gameobjects.TankKi) {
					tink = (TankKi) fa.getUserData();
					tbw = (Wall) fb.getUserData();
					collideTankWall(tink, tbw);
				} else {
					tink = (TankKi) fb.getUserData();
					tbw = (Wall) fa.getUserData();
					collideTankWall(tink, tbw);
				}
			}
// TankKi kollidiert mit Tankki
			else if (fa.getUserData()instanceof de.paluno.game.gameobjects.TankKi 
					&& fb.getUserData() instanceof de.paluno.game.gameobjects.TankKi) {
				tink = (TankKi) fa.getUserData();
				tink2 = (TankKi) fb.getUserData();
				collideTankTank (tink,tink2);
			}
//			Flower kollidiert mit Tank			
			else if (fa.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R
					&& fb.getUserData() instanceof de.paluno.game.gameobjects.Tank || 
					fa.getUserData() instanceof de.paluno.game.gameobjects.Tank
					&& fb.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R){
				System.out.println("Ein Panzer wurde getroffen!");
				if (fa.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R) {
					tba = (F_L_O_W_E_R) fa.getUserData();
					tunk = (Tank) fb.getUserData();
					collideFlowerTank(tba, tunk);
				} else {
					tba = (F_L_O_W_E_R) fb.getUserData();
					tunk = (Tank) fa.getUserData();
					collideFlowerTank(tba, tunk);
				}
			}
//			Flower kollidiert mit Item
			else if (fa.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R
					&& fb.getUserData() instanceof de.paluno.game.gameobjects.Item || 
					fa.getUserData() instanceof de.paluno.game.gameobjects.Item
					&& fb.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R){
				System.out.println("Flower & Item");
				if (fa.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R) {
					tba = (F_L_O_W_E_R) fa.getUserData();
					tbi = (Item) fb.getUserData();
					collideFlowerItem(tba, tbi);
				} else {
					tba = (F_L_O_W_E_R) fb.getUserData();
					tbi = (Item) fa.getUserData();
					collideFlowerItem(tba, tbi);
				}
			}
			

//			Tank trifft Healthkit			
			else if (fa.getUserData() instanceof de.paluno.game.gameobjects.Power
					&& fb.getUserData() instanceof de.paluno.game.gameobjects.Tank || 
					fa.getUserData() instanceof de.paluno.game.gameobjects.Tank
					&& fb.getUserData() instanceof de.paluno.game.gameobjects.Power){
				System.out.println("Ein Healthkit wurde angefahren!");
				if (fa.getUserData() instanceof de.paluno.game.gameobjects.Power) {
					tbp = (Power) fa.getUserData();
					tunk = (Tank) fb.getUserData();
					collidePowerTank(tbp, tunk);
				} else {
					tbp = (Power) fb.getUserData();
					tunk = (Tank) fa.getUserData();
					collidePowerTank(tbp, tunk);
				}
			}
//			Tank kollidiert mit Nitrokit			
			else if (fa.getUserData() instanceof de.paluno.game.gameobjects.Velocity
					&& fb.getUserData() instanceof de.paluno.game.gameobjects.Tank || 
					fa.getUserData() instanceof de.paluno.game.gameobjects.Tank
					&& fb.getUserData() instanceof de.paluno.game.gameobjects.Velocity){
				System.out.println("Ein Nitrokit wurde aufgesammelt!");
				if (fa.getUserData() instanceof de.paluno.game.gameobjects.Item) {
					tbv = (Velocity) fa.getUserData();
					tunk = (Tank) fb.getUserData();
					collideVelocityTank(tbv, tunk);
				} else {
					tbv = (Velocity) fb.getUserData();
					tunk = (Tank) fa.getUserData();
					collideVelocityTank(tbv, tunk);
				}
			}
			
//			Tank kollidiert mit GiftItem
			else if (fa.getUserData() instanceof de.paluno.game.gameobjects.Poison
					&& fb.getUserData() instanceof de.paluno.game.gameobjects.Tank || 
					fa.getUserData() instanceof de.paluno.game.gameobjects.Tank
					&& fb.getUserData() instanceof de.paluno.game.gameobjects.Poison){
				System.out.println("Ein Giftkit wurde angefahren!");
				if (fa.getUserData() instanceof de.paluno.game.gameobjects.Poison) {
					tbpo = (Poison) fa.getUserData();
					tunk = (Tank) fb.getUserData();
					collidePoisonTank(tbpo, tunk);
				} else {
					tbpo = (Poison) fb.getUserData();
					tunk = (Tank) fa.getUserData();
					collidePoisonTank(tbpo, tunk);
				}
			}
//			tba.remove = true;
//			tbb.remove = true;
//			collideFlowerTarget(tba, tbb);
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

	private void collideFlowerTarget(F_L_O_W_E_R f_L_O_W_E_R, Target target) {
		System.out.println("Leben vor Abzug:    " + target.getHealth());
		target.getroffen();
		System.out.println("Leben nach Abzug:    " + target.getHealth());

		if (target.getHealth() < 0) {
			target.destroy();
		}

		f_L_O_W_E_R.explode();

		Sound sound = Gdx.audio.newSound(Gdx.files.internal("hitmarker.mp3"));
		sound.play(1.0f);
	}

	private void collideFlowerFlower(F_L_O_W_E_R flower1, F_L_O_W_E_R flower2) {
		System.out.println("collideFlowerFlower");
		flower1.explode();
		flower2.explode();
	}

	private void collideFlowerWall (F_L_O_W_E_R flower, Wall wall) {
		System.out.println("collideFlowerWall");
		flower.explode();
	}

	private void collideFlowerTank (F_L_O_W_E_R flower, Tank tank) {
		System.out.println("collideFlowerTank wurde aufgerufen!");
		flower.explode();
		tank.getroffen();
		if (tank.getHealth() < 0) {
			System.out.println("Dieser Tank ist Tot!");
			tank.Panzer_zerstoert();
		}
	}
	
	private void collidePowerTank (Power power, Tank tank) {
		if(tank.getHealth()+power.getHealth()<=10) {
			tank.setHealth(tank.getHealth()+power.getHealth());
			power.destroy();
		}
	}
	
	private void collideVelocityTank (Velocity velocity, Tank tank) {
		tank.setVelocityPushTime((long)System.currentTimeMillis());
		velocity.destroy();
	}
	
	private void collidePoisonTank (Poison poison, Tank tank) {
		tank.setHealth(tank.getHealth()-poison.getDamage());
		poison.destroy();
		if (tank.getHealth() < 0) {
			tank.Panzer_zerstoert();
		}
	}
	
	private void collideFlowerItem(F_L_O_W_E_R flower, Item item) {
		flower.explode();
	}
	
	private void collideTankWall(TankKi tankki, Wall wall) {
		System.out.println("collideTankWall");
		if (wall.getMiddle() == true) {
			tankki.TankSchubser();
		}
	}
	
	private void collideTankTank(TankKi tank1, TankKi tank2) {
		tank1.KollisionsLoeserLinks();
		tank2.KollisionsLoeserRechts();
	}
	// Sound sound = Gdx.audio.newSound(Gdx.files.internal("data/mysound.mp3"));
	// sound.play(1.0f);
	//test
}

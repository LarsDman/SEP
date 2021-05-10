package de.paluno.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import org.omg.CosNaming.IstringHelper;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import de.paluno.game.gameobjects.F_L_O_W_E_R;
import de.paluno.game.gameobjects.Target;
import de.paluno.game.screens.PlayScreen;

//The CollisionHandler should manage the outcomes of two physics objects coming in contact with one another, or ending said contact.
//In the first iteration of SpreadingPeace, the only contact that has meaning to it is the contact between an instance of Flower and an instance of Target.

public class CollisionHandler extends java.lang.Object implements com.badlogic.gdx.physics.box2d.ContactListener {

	protected PlayScreen playScreen;

	public CollisionHandler(PlayScreen playScreen) {
		this.playScreen = playScreen;
		// The CollisionHandler only ever needs to be instantiated once in the
		// show-method of the PlayScreen.
	}

	public void beginContact(com.badlogic.gdx.physics.box2d.Contact contact) {

		// legt die Fixtures fest
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		F_L_O_W_E_R tba = null;
		Target tbb = null;

		System.out.println("Collision2!!!");

		// Ist eins der Objekte null?
		if (fa.getUserData() != null && fb.getUserData() != null) {
			System.out.println("Collision2.2!!!");

			// Testen
			boolean klasse = fb.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R;
			System.out.println(klasse);

			if (fa.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R
					&& fb.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R) {
				return;
			} else {
				if (fa.getUserData() instanceof de.paluno.game.gameobjects.F_L_O_W_E_R) {
					tba = (F_L_O_W_E_R) fa.getUserData();
					tbb = (Target) fb.getUserData();
				} else {
					tba = (F_L_O_W_E_R) fb.getUserData();
					tbb = (Target) fa.getUserData();
				}
			}

			tba.remove = true;
			tbb.remove = true;
			collideFlowerTarget(tba, tbb);
			
			
			System.out.println("Collision4!!!");
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

		target.destroy();
		f_L_O_W_E_R.explode();

// 		hier methode um das Ziel neu zu spawnen einfuegen?
	}

}

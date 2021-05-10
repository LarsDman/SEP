package de.paluno.game.gameobjects;
//Subclass of tank representing the target that is to be shot by the player. It needs to override the setupSprites- method at least and can also override the setupBody-method to get different physical properties if needed.

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import de.paluno.game.Constants;
import de.paluno.game.input.InputProvider;
import de.paluno.game.screens.PlayScreen;

public class Target extends Tank {

	public boolean remove;

	public Target(PlayScreen playScreen, com.badlogic.gdx.math.Vector2 spawnPoint, InputProvider input) {
		super(playScreen, spawnPoint, input);

		sprite = new Sprite(new Texture("Zielscheibe30x30.png"));
	}

	protected void setupSprites(com.badlogic.gdx.math.Vector2 spawnPoint) {
		// Instantiates this tank´s sprites. Should be called in the constructor and
		// overridden by subclasses if they need to have a different appearance.
		// spawnPoint - The initial position of the sprites.
	}
	public void render(SpriteBatch batch, float delta) {
		sprite.draw(batch);
	}

	public Body getBody() {
		// returns a reference to this object´s body.
		return body;

	}

	public void destroy() {
		ArrayList<Target> targetsToRemove = new ArrayList<Target>();
		ArrayList<Target> laufTargetArray = playScreen.getTargetList();
		for (Target target : playScreen.getTargetList()) {
			if (remove) {
				targetsToRemove.add(target);
				System.out.println("target zur liste zerstören hinzugefügt");
						}
					}
					for(int i = 0; i < playScreen.getTargetList().size(); i++) {
						for(int j = 0; j < targetsToRemove.size(); j++) {
							if(playScreen.getTargetList().get(i) == targetsToRemove.get(j)) {
								laufTargetArray.remove(i);
							}
						}
					}
					// System.out.println(targetsToRemove);
					playScreen.setTargetList(laufTargetArray);
					targetsToRemove.removeAll(targetsToRemove);
					// System.out.println(targetsToRemove);

					System.out.println("dieses Target ist zerst�rt");

				}

	

	public void setupBody() {
		// Creates this object´s body.
		Random random = new Random();
		int zielscheibenPositionX = random.nextInt(335); // keine ahnung warum die 2 zahlen der rand
		int zielscheibenPositionY = random.nextInt(220); // des Bildschirms sind
		BodyDef targetbody = new BodyDef();
		targetbody.fixedRotation = true;
		targetbody.type = BodyDef.BodyType.DynamicBody;
		targetbody.position.set(zielscheibenPositionX, zielscheibenPositionY);
		

		body = PlayScreen.getWorld().createBody(targetbody);
		body.setUserData(this);

		PolygonShape viereck = new PolygonShape();
		viereck.setAsBox(17, 17);

		FixtureDef targetfixture = new FixtureDef();
		targetfixture.shape = viereck;
		targetfixture.density = 1.0f;

		// Create Fixture and attatch to body
		Fixture zielFixture = this.body.createFixture(targetfixture);
		zielFixture.setUserData(this);

		// Dispose
		viereck.dispose();

	}

}

package de.paluno.game.gameobjects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.Constants;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import de.paluno.game.screens.Gamemode;

// F_L_O_W_E_Rs are the projectiles shot from the tank.
//In their update method, F_L_O_W_E_Rs should check whether they are outside the game´s limits and destroy themselves if so. 

import de.paluno.game.screens.Deathmatch;

public class F_L_O_W_E_R extends java.lang.Object implements Renderable, Updateable, PhysicsObject {
	public boolean remove;
	public static final int speed = 300;
	private static Texture texture;
	float x, y;
	public Vector2 bulletLocation = new Vector2(0, 0);
	public Vector2 bulletVelocity = new Vector2(0, 0);

	public Vector2 origin;
	public Vector2 direction;


	// Constructs a new F_L_O_W_E_R object.
	protected com.badlogic.gdx.physics.box2d.Body body;
	protected Gamemode playScreen;
	protected com.badlogic.gdx.graphics.g2d.Sprite sprite;

	public F_L_O_W_E_R(Gamemode playScreen2, com.badlogic.gdx.math.Vector2 origin,
			com.badlogic.gdx.math.Vector2 direction) {
		this.playScreen = playScreen2;
		this.origin = origin;
		this.direction = direction;

		// float collisionX = body.getPosition().x; //neuer Ansatz
		// float collisionY = body.getPosition().y; //neuer Ansatz

		// this.flowerbody= new CollisionBody(collisionX, collisionY,
		// Constants.FLOWERSIZE, Constants.FLOWERSIZE);

		sprite = new Sprite(new Texture("Blume10x10.png"));
		sprite.setPosition(origin.x, origin.y);
		setupBody();
	}

	public void update(float delta) {
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
		
		if (body.getPosition().x > Constants.BILDSCHIRMBREITE || body.getPosition().x < 0 || body.getPosition().y > Constants.BILDSCHIRMHOEHE || body.getPosition().y < 0) {
			remove=true;
		}
	}

	public void render(com.badlogic.gdx.graphics.g2d.SpriteBatch batch, float delta) {
		// Renders the object.
		sprite.draw(batch);

	}

	public void setupBody() {
		// Body Definition
		BodyDef flowerBodyDef = new BodyDef();
		flowerBodyDef.fixedRotation = true;
		flowerBodyDef.type = BodyDef.BodyType.DynamicBody;
		flowerBodyDef.position.set(origin);

		// Create Body
		body = Deathmatch.getWorld().createBody(flowerBodyDef);
		body.setLinearVelocity(direction.cpy().nor().scl(speed));
		body.setUserData(this);

		// Box shape
		CircleShape flowerShape = new CircleShape();
		flowerShape.setRadius(5f);

		// Fixture shape
		FixtureDef flowerFixtureDef = new FixtureDef();
		flowerFixtureDef.shape = flowerShape;
		flowerFixtureDef.density = 1.0f;

		// Create Fixture and attatch to body
		Fixture flowerFixture = this.body.createFixture(flowerFixtureDef);
		flowerFixture.setUserData(this);
		
		flowerShape.dispose();
	}

	public com.badlogic.gdx.physics.box2d.Body getBody() {
		return body;

	}

	public void setBodyToNullReference() {

		body = null;
		// After a body is destroyed, the reference to that body object should never be
		// used again.
	}

	public void explode() {
//		ArrayList<F_L_O_W_E_R> flowersToRemove = new ArrayList<F_L_O_W_E_R>();
//		ArrayList<F_L_O_W_E_R> laufFlowerArray = playScreen.getFlowerList();
//		for (F_L_O_W_E_R myFlower : playScreen.getFlowerList()) {
//			if (remove) {
//				flowersToRemove.add(myFlower);
//				System.out.println("blume zur liste zerstören hinzugefügt");
//			}
//		}
//		for(int i = 0; i < playScreen.getFlowerList().size(); i++) {
//			for(int j = 0; j < flowersToRemove.size(); j++) {
//				if(playScreen.getFlowerList().get(i) == flowersToRemove.get(j)) {
//					laufFlowerArray.remove(i);
//				}
//			}
//		}
//		// System.out.println(flowersToRemove);
//		playScreen.setFlowerList(laufFlowerArray);
//		flowersToRemove.removeAll(flowersToRemove);
//		// System.out.println(flowersToRemove);
//
		playScreen.forgetAfterUpdate(this);
		System.out.println("diese Blume ist zerstoert");

	}
}

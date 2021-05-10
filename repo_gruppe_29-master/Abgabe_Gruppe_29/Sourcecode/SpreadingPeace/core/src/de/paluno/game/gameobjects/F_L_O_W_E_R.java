package de.paluno.game.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import de.paluno.game.screens.Gamemode;
import de.paluno.game.Constants;
import de.paluno.game.screens.Deathmatch;

public abstract class F_L_O_W_E_R extends java.lang.Object implements Renderable, Updateable, PhysicsObject {
	protected boolean remove;
	protected int speed, damage;
	protected Vector2 origin, direction;
	protected Body body;
	protected Gamemode playScreen;
	protected Sprite sprite;
	protected Tank actor;

	public F_L_O_W_E_R(Gamemode playScreen, Vector2 origin, Vector2 direction, Tank actor) {
		this.actor = actor;
		this.playScreen = playScreen;
		this.origin = origin;
		this.direction = direction;
	}

	protected abstract void setupSprites();
	
	public abstract void update(float delta);

	public void render(SpriteBatch batch, float delta) {
		sprite.draw(batch);
	}
	
	public double calculateDistance(Vector2 origin, Vector2 actualPos) {
		float actualXPos = actualPos.x;
		float actualYPos = actualPos.y;
		float originXPos = origin.x;
		float originYPos = origin.y;
		
		double x = actualXPos - originXPos;
		double y = actualYPos - originYPos;
		
		double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y,2));
		
		return distance;
	}

	public void setupBody() {
		BodyDef flowerBodyDef = new BodyDef();
		flowerBodyDef.fixedRotation = true;
		flowerBodyDef.type = BodyDef.BodyType.DynamicBody;
		flowerBodyDef.position.set(origin);

		body = Deathmatch.getWorld().createBody(flowerBodyDef);
		body.setLinearVelocity(direction.cpy().nor().scl(this.getSpeed()));
		body.setUserData(this);

		CircleShape flowerShape = new CircleShape();
		flowerShape.setRadius(5f);

		FixtureDef flowerFixtureDef = new FixtureDef();
		flowerFixtureDef.shape = flowerShape;
		flowerFixtureDef.density = 0.0001f;
		flowerFixtureDef.restitution = 1f;
		flowerFixtureDef.filter.groupIndex = -2;

		Fixture flowerFixture = this.body.createFixture(flowerFixtureDef);
		flowerFixture.setUserData(this);
		
		flowerShape.dispose();
	}

	public void explode() {
		playScreen.forgetAfterUpdate(this);
	}
	
	public void outOfBounds() {
		if (body.getPosition().x > Constants.BILDSCHIRMBREITE || body.getPosition().x < 0 || body.getPosition().y > Constants.BILDSCHIRMHOEHE || body.getPosition().y < 0) {
			this.explode();
		}
	}
	
	public void setBodyToNullReference() {
		body = null;
	}
	
	public Body getBody() {
		return body;
	}
	
	public Vector2 getDirection() {
		return direction;
	}
	
	public void setDirection(Vector2 direction) {
		this.direction = direction;
	}
	
	public int getDamage() {
		return damage;
	}
	
	protected void setDamage(int damage) {
		this.damage = damage;
	}
	
	public Tank getActor() {
		return actor;
	}

	public void setActor(Tank actor) {
		this.actor = actor;
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}

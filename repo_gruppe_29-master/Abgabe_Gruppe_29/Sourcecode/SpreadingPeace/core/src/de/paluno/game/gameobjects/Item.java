package de.paluno.game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import de.paluno.game.screens.Gamemode;

import java.util.Random;

public abstract class Item extends java.lang.Object implements PhysicsObject, Renderable, Updateable{
	protected Vector2 spawnPoint;
	protected Sprite sprite;
	protected Body body;

	public Item(Gamemode gamemode, Vector2 spawnPoint) {
		this.spawnPoint = spawnPoint;
		setupSprites(spawnPoint);
		setupBody();
	}

	protected abstract void setupSprites(com.badlogic.gdx.math.Vector2 spawnPoint);
	
	public void update(float delta) {
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
	}
	
	public void render(SpriteBatch batch, float delta) {
		sprite.draw(batch);
	}
	
	public void setupBody() {
		BodyDef itemBodyDef = new BodyDef();
		itemBodyDef.fixedRotation = true;
		itemBodyDef.type = BodyDef.BodyType.StaticBody;
		itemBodyDef.position.set(spawnPoint.x, spawnPoint.y);

		body = Gamemode.getWorld().createBody(itemBodyDef);
		body.setUserData(this);
		body.setLinearDamping(50);
		body.setAngularDamping(50);
		
		CircleShape itemShape = new CircleShape();
		itemShape.setRadius(5);

		FixtureDef itemFixtureDef = new FixtureDef();
		itemFixtureDef.shape = itemShape;
		itemFixtureDef.density = 1.0f;

		Fixture itemFixture = this.body.createFixture(itemFixtureDef);
		itemFixture.setUserData(this);

		itemShape.dispose();
	}

	public void setBodyToNullReference() {
		body = null;
	}

	public Body getBody() {
		return body;
	}
	
	public void destroy() {
		Random random = new Random();
		final int itemX = random.nextInt(800);
		final int itemY = random.nextInt(500);		
		Gdx.app.postRunnable(new Runnable() {
			
			@Override
			public void run() {
				body.setTransform(itemX, itemY, 0);
			}
		});
	}
}

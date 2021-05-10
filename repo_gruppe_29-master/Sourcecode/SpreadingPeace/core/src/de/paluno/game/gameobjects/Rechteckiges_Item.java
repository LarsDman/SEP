package de.paluno.game.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import de.paluno.game.screens.Gamemode;

public abstract class Rechteckiges_Item extends Item{

	protected Gamemode playScreen;
	
	public Rechteckiges_Item(Gamemode gamemode, Vector2 spawnPoint) {
		super(gamemode, spawnPoint);
		// TODO Auto-generated constructor stub
	}

	protected abstract void setupSprites(com.badlogic.gdx.math.Vector2 spawnPoint);
	
	public void setupBody() {
//		BodyDef bodyDef = new BodyDef();
//		bodyDef.type = BodyDef.BodyType.StaticBody;
//		bodyDef.position.set(spawnPoint);
//		body = playScreen.getWorld().createBody(bodyDef);
//		PolygonShape shape = new PolygonShape();
//		shape.setAsBox(50, 30);
//		body.createFixture(shape, 0.0f);
//		shape.dispose();
		BodyDef itemBodyDef = new BodyDef();
		itemBodyDef.fixedRotation = true;
		itemBodyDef.type = BodyDef.BodyType.StaticBody;
		itemBodyDef.position.set(spawnPoint.x, spawnPoint.y);

		body = Gamemode.getWorld().createBody(itemBodyDef);
		body.setUserData(this);
		body.setLinearDamping(50);
		body.setAngularDamping(50);

		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(50, 30);
//		CircleShape itemShape = new CircleShape();
//		itemShape.setRadius(5);

		FixtureDef itemFixtureDef = new FixtureDef();
		itemFixtureDef.shape = boxShape;
		itemFixtureDef.density = 1.0f;

		Fixture itemFixture = this.body.createFixture(itemFixtureDef);
		itemFixture.setUserData(this);

		boxShape.dispose();

	}

}

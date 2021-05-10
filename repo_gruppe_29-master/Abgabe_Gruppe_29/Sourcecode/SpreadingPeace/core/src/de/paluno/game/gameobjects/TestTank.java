package de.paluno.game.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class TestTank  extends Tank {
//	protected Body body;
	Vector2 aim;

	public TestTank() {
		this.setupBody();
		setAim(new Vector2(0, 1));
	}
	
	@Override
	public void setAim(Vector2 aim) {
		this.aim = aim;
	}
	
	@Override
	public void setupBody() {
		// Body definition
		BodyDef tankBodyDef = new BodyDef();
		tankBodyDef.fixedRotation = true;
		tankBodyDef.type = BodyDef.BodyType.DynamicBody;
		tankBodyDef.position.set(new Vector2(100,100));

		// Create Body
		World world = new World(spawnPoint, true);
		body = world.createBody(tankBodyDef);
		body.setUserData(this);
		body.setLinearDamping(100000f);
		body.setAngularDamping(2);
		
		// Box shape
		PolygonShape tankShape = new PolygonShape();
		tankShape.setAsBox(12, 18);

		// Fixture shape
		FixtureDef tankFixtureDef = new FixtureDef();
		tankFixtureDef.shape = tankShape;


		// Create Fixture and attatch to body
		tankFixture = this.body.createFixture(tankFixtureDef);
		tankFixture.setUserData(this);

		// Dispose
		tankShape.dispose();
	}
	@Override
	public Vector2 getAim() {
		return aim;
	}

}

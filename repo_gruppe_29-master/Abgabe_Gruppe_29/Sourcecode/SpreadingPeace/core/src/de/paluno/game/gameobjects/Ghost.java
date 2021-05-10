package de.paluno.game.gameobjects;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import de.paluno.game.Constants;
import de.paluno.game.screens.Gamemode;

public class Ghost implements PhysicsObject, Steerable<Vector2>{

	private float MaxLinearSpeed = 0;
	private Gamemode playScreen;
	private Vector2 spawnPoint;
	private Body body;
	private Fixture tankFixture;
	private float MaxLinearAcceleration;
	private float MaxAngularSpeed;
	private float MaxAngularAcceleration;
	private float boundingradius;
	private boolean tagged;

	public Ghost(Gamemode playScreen, Tank parent) {
		this.playScreen = playScreen;
		this.spawnPoint = parent.getPosition();
		setupBody();
	}
	public Ghost(Gamemode playScreen, Vector2 PastPos) {
		this.playScreen = playScreen;
		this.spawnPoint = PastPos;
		setupBody();
	}
	
	public void setupBody() {
		// Body definition
		BodyDef tankBodyDef = new BodyDef();
		tankBodyDef.fixedRotation = true;
		tankBodyDef.type = BodyDef.BodyType.StaticBody;
		tankBodyDef.position.set(spawnPoint);

		// Create Body
		body = playScreen.getWorld().createBody(tankBodyDef);
		body.setUserData(this);
	}

	@Override
	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return body.getPosition();
	}

	@Override
	public float getOrientation() {
		// TODO Auto-generated method stub
		return body.getAngle();
	}

	@Override
	public void setOrientation(float orientation) {		
	}

	@Override
	public float vectorToAngle(Vector2 vector) {
		return Constants.vectorToAngle(vector);
	}

	@Override
	public Vector2 angleToVector(Vector2 outVector, float angle) {
		return Constants.angleToVector(outVector, angle);
	}

	@Override
	public Location<Vector2> newLocation() {
		return null;
	}

	@Override
	public float getZeroLinearSpeedThreshold() {
		return 0;
	}

	@Override
	public void setZeroLinearSpeedThreshold(float value) {		
	}

	@Override
	public float getMaxLinearSpeed() {
		// TODO Auto-generated method stub
		return MaxLinearSpeed;
	}

	@Override
	public void setMaxLinearSpeed(float maxLinearSpeed) {
		this.MaxLinearSpeed = maxLinearSpeed;
		
	}

	@Override
	public float getMaxLinearAcceleration() {
		return MaxLinearAcceleration;
	}

	@Override
	public void setMaxLinearAcceleration(float maxLinearAcceleration) {
		this.MaxLinearAcceleration = maxLinearAcceleration;
	}

	@Override
	public float getMaxAngularSpeed() {
		return MaxAngularSpeed;
	}

	@Override
	public void setMaxAngularSpeed(float maxAngularSpeed) {
		this.MaxAngularSpeed = maxAngularSpeed;
	}

	@Override
	public float getMaxAngularAcceleration() {
		return MaxAngularAcceleration;
	}

	@Override
	public void setMaxAngularAcceleration(float maxAngularAcceleration) {
		this.MaxAngularAcceleration = maxAngularAcceleration;
	}

	@Override
	public Vector2 getLinearVelocity() {
		return body.getLinearVelocity();
	}

	@Override
	public float getAngularVelocity() {
		return body.getAngularVelocity();
	}

	@Override
	public float getBoundingRadius() {
		return boundingradius;
	}

	@Override
	public boolean isTagged() {
		return tagged;
	}

	@Override
	public void setTagged(boolean tagged) {
		this.tagged = tagged;
	}
	@Override
	public Body getBody() {
		// TODO Auto-generated method stub
		return this.body;
	}
	@Override
	public void setBodyToNullReference() {
		// TODO Auto-generated method stub

	}
}

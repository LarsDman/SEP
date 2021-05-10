package de.paluno.game.gameobjects;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.Constants;

public class Waypoint implements Steerable<Vector2>{

	public Vector2 coordinates;
	public float width, height;

	public Waypoint(float x, float y) {
		this.coordinates=new Vector2(x,y);
		this.height=50;
		this.width=50;
	}
	
	public Waypoint(Vector2 coordinates) {
		this.coordinates=coordinates;
		this.height=50;
		this.width=50;
	}
	
	@Override
	public Vector2 getPosition() {
		return this.coordinates;
	}

	@Override
	public float getOrientation() {
		return 0;
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

	public float getMaxLinearSpeed() {
		return 0;
	}

	public void setMaxLinearSpeed(float maxLinearSpeed) {
		//this.MaxLinearSpeed = maxLinearSpeed;

	}

	@Override
	public float getMaxLinearAcceleration() {
		return 0;
	}

	@Override
	public void setMaxLinearAcceleration(float maxLinearAcceleration) {
		//this.MaxLinearAcceleration = maxLinearAcceleration;
	}

	@Override
	public float getMaxAngularSpeed() {
		return 0;
	}

	@Override
	public void setMaxAngularSpeed(float maxAngularSpeed) {
		//this.MaxAngularSpeed = MaxAngularSpeed;

	}

	@Override
	public float getMaxAngularAcceleration() {
		return 0;
	}

	@Override
	public void setMaxAngularAcceleration(float maxAngularAcceleration) {
		//this.MaxAngularAcceleration = MaxAngularAcceleration;

	}

	@Override
	public Vector2 getLinearVelocity() {
		return new Vector2(0,0);
	}

	@Override
	public float getAngularVelocity() {
		return 0;
	}

	@Override
	public float getBoundingRadius() {
				return 0;
	}

	@Override
	public boolean isTagged() {
		return false;
	}

	@Override
	public void setTagged(boolean tagged) {
		//this.tagged = tagged;

	}

	public Vector2 newVector() {
		return new Vector2();
	}

	public void setBehavior(SteeringBehavior<Vector2> behavior) {
		//this.behavior = behavior;
	}

//	public SteeringBehavior<Vector2> getBehavior() {
//		return behavior;
//	}

}

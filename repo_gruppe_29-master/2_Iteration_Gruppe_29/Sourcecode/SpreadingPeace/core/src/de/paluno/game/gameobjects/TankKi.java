package de.paluno.game.gameobjects;

import java.util.ArrayList;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.Constants;
import de.paluno.game.input.InputProvider;
import de.paluno.game.input.actions.Action;
import de.paluno.game.screens.Gamemode;
import java.util.Random;

public class TankKi  extends Tank implements Steerable<Vector2>{
	boolean tagged;
	float boundingradius;
	float MaxLinearSpeed;
	float MaxLinearAcceleration;
	float MaxAngularSpeed;
	float MaxAngularAcceleration;
	Random rand;
	SteeringBehavior<Vector2> behavior;
	SteeringAcceleration<Vector2> steerOutput;

	public TankKi(Gamemode playScreen, Vector2 spawnPoint, InputProvider input, int team, ArrayList<String> settings) {
	// Creates a new tank.
	super(playScreen, spawnPoint, input, team, settings);
	this.settings = settings;
	this.playScreen = playScreen;
	this.input = input;
	this.spawnPoint = spawnPoint;
	this.MaxLinearSpeed = 180;
	this.MaxLinearAcceleration = 50000;
	this.MaxAngularSpeed = 2000;
	this.MaxAngularAcceleration = 50000;
	this.boundingradius = 1;
	this.tagged = false;
	lastTimeShot = System.currentTimeMillis();
	rand = new Random();
	this.steerOutput = new SteeringAcceleration<Vector2> (new Vector2());
	this.body.setUserData(this);
    }
	
	public void update (float delta) {
		if(behavior != null) {
			behavior.calculateSteering(steerOutput);
			applySteering(delta);
		}
		this.updateSprites();
		setAim(convertFloatToVector(body.getAngle()));

		if (health == 10) {
			healthbar_100.setPosition(body.getPosition().x-30, body.getPosition().y+ 30);}
		if (health == 9) {
			healthbar_90.setPosition(body.getPosition().x-30, body.getPosition().y+30);}
		if (health == 8) {
			healthbar_80.setPosition(body.getPosition().x-30, body.getPosition().y +30);}
		if (health == 7) {
			healthbar_70.setPosition(body.getPosition().x-30, body.getPosition().y+30);}
		if (health == 6) {
			healthbar_60.setPosition(body.getPosition().x-30, body.getPosition().y+30);}
		if (health == 5) {
			healthbar_50.setPosition(body.getPosition().x-30, body.getPosition().y+30);}
		if (health == 4) {
			healthbar_40.setPosition(body.getPosition().x-30, body.getPosition().y+30);}
		if (health == 3) {
			healthbar_30.setPosition(body.getPosition().x-30, body.getPosition().y+30);}
		if (health == 2) {
			healthbar_20.setPosition(body.getPosition().x-30, body.getPosition().y+30 );}
		if (health == 1) {
			healthbar_10.setPosition(body.getPosition().x-30 , body.getPosition().y+30);}
		if (health == 0) {
			healthbar_0.setPosition(body.getPosition().x-30, body.getPosition().y+30 );}
		
		Action[] acts = input.getInputs(this, playScreen, settings);
		for(Action a : acts) {
			
			if(a!=null) {
					a.act(delta);
			}
		}
		this.checkVelocityItem();
	}
	
	public void applySteering(float delta) {
		
		boolean anyAccelerations = false;
		
		if(!steerOutput.linear.isZero()) {
			Vector2 force= steerOutput.linear.scl(delta);
			body.applyForceToCenter(force, true);
			anyAccelerations = true;
		}
		
		if(steerOutput.angular != 0) {
			body.applyTorque(steerOutput.angular*delta, true);
			anyAccelerations = true;
		}
		
		else {
			Vector2 linVel=getLinearVelocity();
			if(!linVel.isZero()) {
				float newOrientation = vectorToAngle(linVel);
				body.setAngularVelocity(newOrientation - getAngularVelocity() * delta);
				body.setTransform(body.getPosition(), newOrientation);
			}
		}
		
		if(anyAccelerations) {
			Vector2 velocity = body.getLinearVelocity();
			float currentSpeedSquare = velocity.len2();
			//Vektor f�r Linear Speed
			if(currentSpeedSquare > MaxLinearSpeed * MaxLinearSpeed) {
				body.setLinearVelocity(velocity.scl(MaxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));
			}
			//Normaler Wert f�r AngularSpeed
			if (body.getAngularVelocity() > MaxAngularSpeed) {
				body.setAngularVelocity(MaxAngularSpeed);
		    }
		}
	}
	
	@Override
	protected void checkVelocityItem() {
		if((long)System.currentTimeMillis()-3000 < this.getVelocityPushTime()) {
			this.setMaxLinearSpeed(540);
		}else {
			this.setMaxLinearSpeed(180);
		}
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public float vectorToAngle(Vector2 vector) {
		// TODO Auto-generated method stub
		return Constants.vectorToAngle(vector);
	}

	@Override
	public Vector2 angleToVector(Vector2 outVector, float angle) {
		// TODO Auto-generated method stub
		return Constants.angleToVector(outVector, angle);
	}

	@Override
	public Location<Vector2> newLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getZeroLinearSpeedThreshold() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setZeroLinearSpeedThreshold(float value) {
		// TODO Auto-generated method stub
		
	}

	public float getMaxLinearSpeed() {
		// TODO Auto-generated method stub
		return MaxLinearSpeed;
	}

	
	public void setMaxLinearSpeed(float maxLinearSpeed) {
		// TODO Auto-generated method stub
		this.MaxLinearSpeed = maxLinearSpeed;
		
	}

	@Override
	public float getMaxLinearAcceleration() {
		// TODO Auto-generated method stub
		return MaxLinearAcceleration;
	}

	@Override
	public void setMaxLinearAcceleration(float maxLinearAcceleration) {
		// TODO Auto-generated method stub
		this.MaxLinearAcceleration = maxLinearAcceleration;
	}

	@Override
	public float getMaxAngularSpeed() {
		// TODO Auto-generated method stub
		return MaxAngularSpeed;
	}

	@Override
	public void setMaxAngularSpeed(float maxAngularSpeed) {
		// TODO Auto-generated method stub
		this.MaxAngularSpeed = MaxAngularSpeed;
		
	}

	@Override
	public float getMaxAngularAcceleration() {
		// TODO Auto-generated method stub
		return MaxAngularAcceleration;
	}

	@Override
	public void setMaxAngularAcceleration(float maxAngularAcceleration) {
		// TODO Auto-generated method stub
		this.MaxAngularAcceleration = MaxAngularAcceleration;
		
	}

	@Override
	public Vector2 getLinearVelocity() {
		// TODO Auto-generated method stub
		return body.getLinearVelocity();
	}

	@Override
	public float getAngularVelocity() {
		// TODO Auto-generated method stub
		return body.getAngularVelocity();
	}

	@Override
	public float getBoundingRadius() {
		// TODO Auto-generated method stub
		return boundingradius;
	}

	@Override
	public boolean isTagged() {
		// TODO Auto-generated method stub
		return tagged;
	}

	@Override
	public void setTagged(boolean tagged) {
		// TODO Auto-generated method stub
		this.tagged = tagged;
		
	}
	public Vector2 newVector() {
		return new Vector2();
	}
	
	public void setBehavior(SteeringBehavior<Vector2> behavior) {
		this.behavior = behavior;
	}
	public SteeringBehavior<Vector2> getBehavior() {
		return behavior;
	}
	public void TankSchubser () {
		if(this.body.getPosition().x>480)
		{
			if(this.body.getPosition().y<520) body.setLinearVelocity(rand.nextFloat()*20000, rand.nextFloat()*-15000); //x y
			else body.setLinearVelocity(rand.nextFloat()*20000, rand.nextFloat()*15000); //x y
			
			//System.out.println(rand.nextFloat());
		}
		else //x<480
		{
			if(this.body.getPosition().y<520) body.setLinearVelocity(rand.nextFloat()*-20000,rand.nextFloat()*-15000); //x y
			else body.setLinearVelocity(rand.nextFloat()*-20000, rand.nextFloat()*15000); //x y
			//System.out.println(rand.nextFloat());
		}
	}
	public void KollisionsLoeserLinks () {
		body.setLinearVelocity(-20000,0);
	}
	public void KollisionsLoeserRechts () {
		body.setLinearVelocity(20000,0);
	}
}
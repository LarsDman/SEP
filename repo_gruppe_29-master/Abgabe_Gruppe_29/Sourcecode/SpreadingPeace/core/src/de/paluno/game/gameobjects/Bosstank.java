package de.paluno.game.gameobjects;

import java.util.ArrayList;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

import de.paluno.game.Constants;
import de.paluno.game.input.InputProvider;
import de.paluno.game.input.actions.Action;
import de.paluno.game.screens.Gamemode;
import de.paluno.game.screens.MenuScreen;

import java.util.Random;

public class Bosstank  extends Tank{
	float boundingradius, MaxLinearSpeed, MaxLinearAcceleration, MaxAngularSpeed, MaxAngularAcceleration;
	Random rand;
	public Blumenauswahl flower;


	public Bosstank(Gamemode playScreen, Vector2 spawnPoint, InputProvider input, ArrayList<String> settings) {
	super(playScreen, spawnPoint, input, 1, settings, MenuScreen.colours.get(0), "Bosstank");
	this.MaxLinearSpeed = 0;
	this.MaxLinearAcceleration = 0;
	this.MaxAngularSpeed = 0;
	this.MaxAngularAcceleration = 0;
	this.boundingradius = 1;
	lastTimeShot = System.currentTimeMillis();
	rand = new Random();
	this.body.setUserData(this);
    }
	
	
	public F_L_O_W_E_R useFlower() {
		flower=Blumenauswahl.NORMALFLOWER;
		return FlowerFactory.create(this.flower, this.playScreen, this);
	}
	
	@Override
	protected void setupSprites(Vector2 spawnPoint) {
		
			spriteBody = new Sprite(new Texture("Bossboden.png"));
			spriteTurret = new Sprite(new Texture("T_Braun.png"));
			spriteTurret.scale(2f);

		
		flag= new Sprite (new Texture("Flagge.png"));
		noflag= new Sprite (new Texture("wallBlock.jpg"));
		leben_100 = new Sprite (new Texture("Leben_100%.png"));
		leben_95 = new Sprite (new Texture("Leben_95%.png"));
		leben_90 = new Sprite (new Texture("Leben_90%.png"));
		leben_85 = new Sprite (new Texture("Leben_85%.png"));
		leben_80 = new Sprite (new Texture("Leben_80%.png"));
		leben_75 = new Sprite (new Texture("Leben_75%.png"));
		leben_70 = new Sprite (new Texture("Leben_70%.png"));
		leben_65 = new Sprite (new Texture("Leben_65%.png"));
		leben_60 = new Sprite (new Texture("Leben_60%.png"));
		leben_55 = new Sprite (new Texture("Leben_55%.png"));
		leben_50 = new Sprite (new Texture("Leben_50%.png"));
		leben_45 = new Sprite (new Texture("Leben_45%.png"));
		leben_40 = new Sprite (new Texture("Leben_40%.png"));
		leben_35 = new Sprite (new Texture("Leben_35%.png"));
		leben_30 = new Sprite (new Texture("Leben_30%.png"));
		leben_25 = new Sprite (new Texture("Leben_25%.png"));
		leben_20 = new Sprite (new Texture("Leben_20%.png"));
		leben_15 = new Sprite (new Texture("Leben_15%.png"));
		leben_10 = new Sprite (new Texture("Leben_10%.png"));
		leben_5 = new Sprite (new Texture("Leben_5%.png"));
		leben_0 = new Sprite (new Texture("Leben_0%.png"));
		normalFl = new Sprite (new Texture("NormalFlower.png"));
	}
	
	@Override
	public void setupBody() {
		// Body definition
		BodyDef tankBodyDef = new BodyDef();
		tankBodyDef.fixedRotation = true;
		tankBodyDef.type = BodyDef.BodyType.DynamicBody;
		tankBodyDef.position.set(spawnPoint);

		// Create Body
		body = playScreen.getWorld().createBody(tankBodyDef);
		body.setUserData(this);
		body.setLinearDamping(100000f);
		body.setAngularDamping(2);
		
		// Box shape
		PolygonShape tankShape = new PolygonShape();
		tankShape.setAsBox(50, 75);

		// Fixture shape
		FixtureDef tankFixtureDef = new FixtureDef();
		tankFixtureDef.shape = tankShape;
		tankFixtureDef.density = 0.001f;
		tankFixtureDef.filter.groupIndex = 1;

		// Create Fixture and attatch to body
		tankFixture = this.body.createFixture(tankFixtureDef);
		tankFixture.setUserData(this);

		// Dispose
		tankShape.dispose();
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
	protected void updateSpritesTurret() {
		spriteTurret.setPosition((body.getPosition().x - spriteTurret.getWidth() /2), (body.getPosition().y - spriteTurret.getHeight() / 2));
		spriteTurret.setRotation(this.getAim().angle()-90);
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
	protected void updateHealthbarSprites() {
		float healthbarX = body.getPosition().x-50;
		float healthbarY = body.getPosition().y+90;

		if (health == 50) {
			leben_100.setPosition(healthbarX, healthbarY);
		} else if (health < 50 && health >= 40) {
			leben_80.setPosition(healthbarX, healthbarY);
		} else if (health < 40 && health >= 30) {
			leben_60.setPosition(healthbarX, healthbarY);
		} else if (health < 30 && health >= 20) {
			leben_40.setPosition(healthbarX, healthbarY);
		} else if (health < 20 && health >= 10) {
			leben_20.setPosition(healthbarX, healthbarY);
		} else if (health < 10 && health >= 0) {
			leben_5.setPosition(healthbarX, healthbarY);
		} else {
			leben_0.setPosition(healthbarX, healthbarY);
		}
		
	}
		
	
	@Override
	public void render(SpriteBatch batch, float delta) {

		switch(hasFlag) {
		case 1:
			flag.draw(batch);
			break;
		case 0:
			break;
		default:
			break;
		}
		
		if (health == 50) {
			leben_100.draw(batch);
		} else if (health < 50 && health >= 40) {
			leben_80.draw(batch);
		} else if (health < 40 && health >= 30) {
			leben_60.draw(batch);
		} else if (health < 30 && health >= 20) {
			leben_40.draw(batch);
		} else if (health < 20 && health >= 10) {
			leben_20.draw(batch);
		} else if (health < 10 && health >= 0) {
			leben_5.draw(batch);
		} else {
			leben_0.draw(batch);
		}
		spriteBody.draw(batch);
		spriteTurret.draw(batch);

		
	}
	
	@Override
	public Vector2 getPosition() {
		return body.getPosition();
	}

	@Override
	public float getOrientation() {
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

	public float getMaxLinearSpeed() {
		return MaxLinearSpeed;
	}

	
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
		this.MaxAngularSpeed = MaxAngularSpeed;
		
	}

	@Override
	public float getMaxAngularAcceleration() {
		return MaxAngularAcceleration;
	}

	@Override
	public void setMaxAngularAcceleration(float maxAngularAcceleration) {
		this.MaxAngularAcceleration = MaxAngularAcceleration;
		
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
package de.paluno.game.gameobjects;

import java.util.ArrayList;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

import de.paluno.game.Constants;
import de.paluno.game.input.InputProvider;
import de.paluno.game.input.actions.Action;
import de.paluno.game.screens.Gamemode;
import java.util.Random;

public class Tower extends Tank implements Steerable<Vector2> {
	boolean tagged;
	float boundingradius, MaxLinearSpeed, MaxLinearAcceleration, MaxAngularSpeed, MaxAngularAcceleration;
	Random rand;
	Tank player1;
	SteeringBehavior<Vector2> behavior;
	SteeringAcceleration<Vector2> steerOutput;
	protected boolean haslineofsight;

	public Tower(Gamemode playScreen, Vector2 spawnPoint, InputProvider input, int team, ArrayList<String> settings,
			Colour colour, String name) {
		super(playScreen, spawnPoint, input, team, settings, colour, name);
		this.MaxLinearSpeed = 1;
		this.MaxLinearAcceleration = 1;
		this.MaxAngularSpeed = 1;
		this.MaxAngularAcceleration = 1;
		this.boundingradius = 1;
		this.tagged = false;
		lastTimeShot = System.currentTimeMillis();
		rand = new Random();
		this.steerOutput = new SteeringAcceleration<Vector2>(new Vector2());
		this.body.setUserData(this);
	}

	@Override
	public void update(float delta) {
		if (this.playScreen.getHud().isReady()) {
			if (behavior != null) {
				behavior.calculateSteering(steerOutput);
				applySteering(delta);
			}
			setAim(convertFloatToVector(body.getAngle()));

			Action[] acts = input.getInputs(this, playScreen, settings);
			for (Action a : acts) {
				if (a != null) {
					a.act(delta);
				}
			}
			this.checkVelocityItem();
			this.updateFlowerAuswahl();
		}

//		this.setCenter(this.getBody().getPosition().cpy().rotate(this.getBody().getAngle()).add(new Vector2(6,9).cpy().rotate(this.getBody().getAngle())));
		this.updateHealthbarSprites();
		this.updateSpritesBody();
		this.updateSpritesTurret();
		if (body.getPosition().x > Constants.BILDSCHIRMBREITE || body.getPosition().x < 0
				|| body.getPosition().y > Constants.BILDSCHIRMHOEHE || body.getPosition().y < 0) {
			this.destroy();
		}
	}

	public void applySteering(float delta) {
		boolean anyAccelerations = false;
		if (!steerOutput.linear.isZero()) {
			Vector2 force = steerOutput.linear.scl(delta);
			body.applyForceToCenter(force, true);
			anyAccelerations = true;
		}
		if (steerOutput.angular != 0) {
			body.applyTorque(steerOutput.angular * delta, true);
			anyAccelerations = true;
		} else {
			Vector2 linVel = getLinearVelocity();
			if (!linVel.isZero()) {
				float newOrientation = vectorToAngle(linVel);
				body.setTransform(body.getPosition(), newOrientation);
			}
		}
		if (anyAccelerations) {
			Vector2 velocity = body.getLinearVelocity();
			float currentSpeedSquare = velocity.len2();
			// Vektor fuer Linear Speed
			if (currentSpeedSquare > MaxLinearSpeed * MaxLinearSpeed) {
				body.setLinearVelocity(velocity.scl(MaxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));
			}
			// Normaler Wert fuer AngularSpeed
			if (body.getAngularVelocity() > MaxAngularSpeed) {
				body.setAngularVelocity(MaxAngularSpeed);
			}
		}
	}

	@Override
	protected void checkVelocityItem() {
		if ((long) System.currentTimeMillis() - 3000 < this.getVelocityPushTime()) {
			this.setMaxLinearSpeed(540);
		} else {
			this.setMaxLinearSpeed(180);
		}
	}

	@Override
	protected void updateHealthbarSprites() {
		float healthbarX = body.getPosition().x - 50;
		float healthbarY = body.getPosition().y + 30;
		float FloweranzeigeX = body.getPosition().x - 5;
		float FloweranzeigeY = body.getPosition().y - 5;

		switch (this.getFlower()) {
		case NORMALFLOWER:
			// normalFl.setPosition(FloweranzeigeX, FloweranzeigeY);
			break;
		case FASTFLOWER:
			fastFl.setPosition(FloweranzeigeX, FloweranzeigeY);
			break;
		case BOUNCEFLOWER:
			bounceFl.setPosition(FloweranzeigeX, FloweranzeigeY);
			break;
		case SPLITTEDFLOWER:
			splittedFl.setPosition(FloweranzeigeX, FloweranzeigeY);
			break;
		default:
			break;
		}
//		
//		leben_100 = new Sprite (new Texture("Leben_100%.png"));
//		leben_95 = new Sprite (new Texture("Leben_95%.png"));
//		leben_90 = new Sprite (new Texture("Leben_90%.png"));
//		leben_85 = new Sprite (new Texture("Leben_85%.png"));
//		leben_80 = new Sprite (new Texture("Leben_80%.png"));
//		leben_75 = new Sprite (new Texture("Leben_75%.png"));
//		leben_70 = new Sprite (new Texture("Leben_70%.png"));
//		leben_65 = new Sprite (new Texture("Leben_65%.png"));
//		leben_60 = new Sprite (new Texture("Leben_60%.png"));
//		leben_55 = new Sprite (new Texture("Leben_55%.png"));
//		leben_50 = new Sprite (new Texture("Leben_50%.png"));
//		leben_45 = new Sprite (new Texture("Leben_45%.png"));
//		leben_40 = new Sprite (new Texture("Leben_40%.png"));
//		leben_35 = new Sprite (new Texture("Leben_35%.png"));
//		leben_30 = new Sprite (new Texture("Leben_30%.png"));
//		leben_25 = new Sprite (new Texture("Leben_25%.png"));
//		leben_20 = new Sprite (new Texture("Leben_20%.png"));
//		leben_15 = new Sprite (new Texture("Leben_15%.png"));
//		leben_10 = new Sprite (new Texture("Leben_10%.png"));
//		leben_5 = new Sprite (new Texture("Leben_5%.png"));
//		leben_0 = new Sprite (new Texture("Leben_0%.png"));
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
	public void render( SpriteBatch batch, float delta) {
		alpha += delta;
//		endtime = System.currentTimeMillis();
//		System.out.println("test1");
//		if(respawnAnimation==true) {
//			System.out.println("test2");
//
////			endtime = System.currentTimeMillis();
//			if((endtime < (long)System.currentTimeMillis() - 3000)) {
//				System.out.println("test3");
//
//			spriteBody.draw(batch, +5f*(float)Math.sin(alpha+ .5f));
//			spriteTurret.draw(batch, +5f*(float)Math.sin(alpha+ .5f));
//			}
//			System.out.println("test4");
//
////			respawnAnimation=false;
//		if(respawnAnimation==true) {
//			Timer.schedule(new Task() {
//				@Override
//				public void run() {
//					spriteBody.draw(batch, +5f*(float)Math.sin(alpha+ .5f));
//					spriteTurret.draw(batch, +5f*(float)Math.sin(alpha+ .5f));
//					respawnAnimation=false;
//				}
//			},delay);
//				
//			spriteBody.draw(batch, +5f*(float)Math.sin(alpha+ .5f));
//			spriteTurret.draw(batch, +5f*(float)Math.sin(alpha+ .5f));
		if(respawnAnimation==true) {
			spriteBody.draw(batch, +5f*(float)Math.sin(alpha+ .5f));
			spriteTurret.draw(batch, +5f*(float)Math.sin(alpha+ .5f));
		}
		else {
			spriteBody.draw(batch);
			spriteTurret.draw(batch);
		}
		switch(hasFlag) {
		case 1:
			zeiteinheit++;
			if(zeiteinheit>=40&&zeiteinheit<80)
				flag.draw(batch);
			else if(zeiteinheit<40) {
				flaginverse.draw(batch);
			}
			if(zeiteinheit>=79)
				zeiteinheit=0;
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
		
		switch(this.getFlower()) {
		case NORMALFLOWER:
			normalFl.draw(batch);
			break;
		case FASTFLOWER:
			fastFl.draw(batch);
			break;
		case BOUNCEFLOWER:
			bounceFl.draw(batch);
			break;
		case SPLITTEDFLOWER:
			splittedFl.draw(batch);
			break;
		default:
			break;
		}
		
	}
	

	public boolean haslineofsight() {
		return this.haslineofsight;
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

}
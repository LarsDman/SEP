package de.paluno.game.gameobjects;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.ai.steer.behaviors.RaycastObstacleAvoidance;
import com.badlogic.gdx.ai.steer.utils.rays.CentralRayWithWhiskersConfiguration;
import com.badlogic.gdx.ai.utils.Box2dRaycastCollisionDetector;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

import de.paluno.game.Constants;
import de.paluno.game.input.InputProvider;
import de.paluno.game.input.actions.Action;
import de.paluno.game.screens.CaptureTheFlag;
import de.paluno.game.screens.Deathmatch;
import de.paluno.game.screens.Gamemode;
import de.paluno.game.screens.Survival;

public class TankKi extends Tank implements Steerable<Vector2> {
	static final float BlumenwechselGrenze = 2f;
	static float BlumenwechselZeit = 0;
	boolean tagged;
//	private boolean schussbool = true;
	float boundingradius, MaxLinearSpeed, MaxLinearAcceleration, MaxAngularSpeed, MaxAngularAcceleration;
	Random rand;
	public Blumenauswahl flower;
	private SteeringAcceleration<Vector2> steerOutput;
	public SteeringBehavior<Vector2> behavior;
	// Raycasting
	protected boolean haslineofsight;
	protected Fixture rayFixture;
	private static Vector2 collision = new Vector2();
	private static Vector2 normal = new Vector2();
	// Ki Steering
	private Ghost ghost;
	private float lastTimeSeen;
	// RaycastCollisionAvoidance
	private PrioritySteering<Vector2> behaviors;
	private CentralRayWithWhiskersConfiguration<Vector2> rayWhiskersConfiguration;
	private RaycastCollisionDetector<Vector2> raycastCollisionDetector;
	public RaycastObstacleAvoidance<Vector2> whiskersAvoid;

	public TankKi(Gamemode playScreen, Vector2 spawnPoint, InputProvider input, int team, ArrayList<String> settings,
			Colour colour, String name) {
		super(playScreen, spawnPoint, input, team, settings, colour, name);

		this.MaxLinearSpeed = 180;
		this.MaxLinearAcceleration = 50000;
		this.MaxAngularSpeed = 2000;
		this.MaxAngularAcceleration = 50000;
		this.boundingradius = 10;
		this.tagged = false;
		lastTimeShot = System.currentTimeMillis();
		rand = new Random();
		this.steerOutput = new SteeringAcceleration<Vector2>(new Vector2());
		this.body.setUserData(this);
		this.name = name;

		// raycastObstacleavoidance
		rayWhiskersConfiguration = new CentralRayWithWhiskersConfiguration<Vector2>(this, 60f, 50f,
				(float) Math.toRadians(45));
		raycastCollisionDetector = new Box2dRaycastCollisionDetector(playScreen.getWorld());
		whiskersAvoid = new RaycastObstacleAvoidance<Vector2>(this, rayWhiskersConfiguration, raycastCollisionDetector,
				boundingradius);
	}

	@Override
	public void update(float delta) {

		if (this.playScreen.getHud().isReady()) {
			if (behaviors != null) {
				behaviors.calculateSteering(steerOutput);
				applySteering(delta);
			}
			if (!(playScreen instanceof CaptureTheFlag) && !(playScreen instanceof Deathmatch)
					&& !(playScreen instanceof Survival)) {
				setAim(convertFloatToVector(body.getAngle()));
			}

			Action[] acts = input.getInputs(this, playScreen, settings);
			for (Action a : acts) {
				if (a != null) {
					a.act(delta);
				}
			}
			this.checkVelocityItem();
			this.updateFlowerAuswahl();
			this.updateLastTimeSeen();
		}

		BlumenwechselZeit += Gdx.graphics.getDeltaTime();

		if (!(playScreen instanceof Deathmatch)) {
			KiBlumenwechsel();
		}

		this.updateHealthbarSprites();
		this.updateSpritesBody();
		this.updateSpritesTurret();
		this.updateFlagSprites();
		this.rayWhiskersConfiguration.updateRays();

		if (body.getPosition().x > Constants.BILDSCHIRMBREITE || body.getPosition().x < 0
				|| body.getPosition().y > Constants.BILDSCHIRMHOEHE || body.getPosition().y < 0) {
			this.destroy();
		}
	}

	public void KiBlumenwechsel() {
		Random rand = new Random();
		int value;

		if (BlumenwechselZeit >= BlumenwechselGrenze) {
			BlumenwechselZeit = 0;
			value = rand.nextInt(4);

			switch (value) {
			case 0:
			default:
				this.setFlower(Blumenauswahl.NORMALFLOWER);
				break;
			case 1:
				this.setFlower(Blumenauswahl.FASTFLOWER);
				break;
			case 2:
				this.setFlower(Blumenauswahl.BOUNCEFLOWER);
				break;
			case 3:
				this.setFlower(Blumenauswahl.SPLITTEDFLOWER);
				break;
			}
		}
	}

	public void rayCast(final Tank target) {

		final RayCastCallback callback = new RayCastCallback() {
			@Override
			public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {

				if (fixture.getFilterData().groupIndex == -2 | fixture.getUserData() instanceof Tree) { // Der TankKi
																										// soll durch Wasser
					return -1; 																			// und Baeume schauen koennen

				} else {
					TankKi.collision.set(point);
					TankKi.normal.set(normal);
					rayFixture = fixture;
					return fraction;
				}
			}
		};

		playScreen.world.rayCast(callback, this.getPosition(), target.getPosition());
		
		if (rayFixture.getUserData() != null &&rayFixture.getUserData() == target) {
			haslineofsight = true;
			lastTimeSeen = 0;
		} else {
			haslineofsight = false;
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
	public void render(SpriteBatch batch, float delta) {
		alpha += delta;
		if (noAnimation == false) {
			if (respawnAnimation == true) {
				spriteBody.draw(batch,(float) Math.abs(Math.sin(alpha*10f)));
				spriteTurret.draw(batch,(float) Math.abs(Math.sin(alpha*10f)));
			} else {
				spriteBody.draw(batch);
				spriteTurret.draw(batch);
			}
			switch (hasFlag) {
			case 1:
				zeiteinheit++;
				if (zeiteinheit >= 40 && zeiteinheit < 80)
					flag.draw(batch);
				else if (zeiteinheit < 40) {
					flaginverse.draw(batch);
				}
				if (zeiteinheit >= 79)
					zeiteinheit = 0;
				break;
			case 0:
				break;
			default:
				break;
			}

			switch (health) {
			case 20:
				leben_100.draw(batch);
				break;
			case 19:
				leben_95.draw(batch);
				break;
			case 18:
				leben_90.draw(batch);
				break;
			case 17:
				leben_85.draw(batch);
				break;
			case 16:
				leben_80.draw(batch);
				break;
			case 15:
				leben_75.draw(batch);
				break;
			case 14:
				leben_70.draw(batch);
				break;
			case 13:
				leben_65.draw(batch);
				break;
			case 12:
				leben_60.draw(batch);
				break;
			case 11:
				leben_55.draw(batch);
				break;
			case 10:
				leben_50.draw(batch);
				break;
			case 9:
				leben_45.draw(batch);
				break;
			case 8:
				leben_40.draw(batch);
				break;
			case 7:
				leben_35.draw(batch);
				break;
			case 6:
				leben_30.draw(batch);
				break;
			case 5:
				leben_25.draw(batch);
				break;
			case 4:
				leben_20.draw(batch);
				break;
			case 3:
				leben_15.draw(batch);
				break;
			case 2:
				leben_10.draw(batch);
				break;
			case 1:
				leben_5.draw(batch);
				break;
			case 0:
				leben_0.draw(batch);
				break;
			default:
				leben_100.draw(batch);
				break;
			}

			renderFlower(batch);

		}
	}
	
	private void renderFlower(SpriteBatch batch) {
		
		switch (this.getFlower()) {
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

	public void setGhosts(Tank target) {
		if (this.getGhost() != null) {
			playScreen.getWorld().destroyBody(this.ghost.getBody());
		}
		this.ghost = new Ghost(playScreen, target);
	}

	public Ghost getGhost() {
		return this.ghost;
	}

	public Vector2 getCollision() {
		return collision;
	}

	public Vector2 getNormal() {
		return normal;
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
	public float getZeroLinearSpeedThreshold() {
		return 0.01f;
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

	public Vector2 newVector() {
		return new Vector2();
	}

	public void setBehavior(SteeringBehavior<Vector2> behavior) {
		this.behavior = behavior;
	}

	public void setPrioritySteering(PrioritySteering<Vector2> behaviors) {
		this.behaviors = behaviors;
	}

	public SteeringBehavior<Vector2> getBehavior() {
		return behavior;
	}

	public void TankSchubser() {
		if (this.body.getPosition().x > 480) {
			if (this.body.getPosition().y < 520)
				body.setLinearVelocity(rand.nextFloat() * 20000, rand.nextFloat() * -15000); // x y
			else
				body.setLinearVelocity(rand.nextFloat() * 20000, rand.nextFloat() * 15000); // x y

			// System.out.println(rand.nextFloat());
		} else // x<480
		{
			if (this.body.getPosition().y < 520)
				body.setLinearVelocity(rand.nextFloat() * -20000, rand.nextFloat() * -15000); // x y
			else
				body.setLinearVelocity(rand.nextFloat() * -20000, rand.nextFloat() * 15000); // x y
			// System.out.println(rand.nextFloat());
		}
	}

	public void KollisionsLoeserLinks() {
		body.setLinearVelocity(-20000, 0);
	}

	public void KollisionsLoeserRechts() {
		body.setLinearVelocity(20000, 0);
	}

	public void updateLastTimeSeen() {
		lastTimeSeen += Gdx.graphics.getDeltaTime();
	}

	public float getLastTimeSeen() {
		return lastTimeSeen;
	}

}
package de.paluno.game.gameobjects;
//Object representation of a tank, encapsulating both, the chassis of the tank and the turret. Both need to be drawn in the render-method, but only the chassis needs to have a body and be therefore affected by collisions and physics.

//Every Tank object also holds a reference to an InputProvider instance which defines how the tank acts. Therefore, during the tank´s update-method, it needs to call the getInputs-method of said InputProvider and call the act-method on all the returned Action objects.
//In terms of concerns this means that the tank itself decides if it should perform actions at all, while the InputProvider decides on what type of actions it performs, and the Action implementations define how the action is carried out.

import static de.paluno.game.Constants.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import de.paluno.game.Constants;
import de.paluno.game.input.InputProvider;
import de.paluno.game.input.actions.Action;
import de.paluno.game.screens.Gamemode;

public class Tank extends java.lang.Object implements PhysicsObject, Renderable, Updateable, Steerable<Vector2> {
	protected com.badlogic.gdx.physics.box2d.Body body;
	protected com.badlogic.gdx.graphics.g2d.Sprite spriteBody;
	protected com.badlogic.gdx.graphics.g2d.Sprite spriteTurret;
	protected Gamemode playScreen;
	protected InputProvider input;
	public com.badlogic.gdx.math.Vector2 aim;
	protected com.badlogic.gdx.math.Vector2 direction;
	public long lastTimeShot; // Timestamp of when this tank last shot.
	protected com.badlogic.gdx.graphics.g2d.Sprite sprite;
	public Vector2 spawnPoint;
	protected com.badlogic.gdx.graphics.g2d.Sprite healthbar_100;
	protected com.badlogic.gdx.graphics.g2d.Sprite healthbar_90;
	protected com.badlogic.gdx.graphics.g2d.Sprite healthbar_80;
	protected com.badlogic.gdx.graphics.g2d.Sprite healthbar_70;
	protected com.badlogic.gdx.graphics.g2d.Sprite healthbar_60;
	protected com.badlogic.gdx.graphics.g2d.Sprite healthbar_50;
	protected com.badlogic.gdx.graphics.g2d.Sprite healthbar_40;
	protected com.badlogic.gdx.graphics.g2d.Sprite healthbar_30;
	protected com.badlogic.gdx.graphics.g2d.Sprite healthbar_20;
	protected com.badlogic.gdx.graphics.g2d.Sprite healthbar_10;
	protected com.badlogic.gdx.graphics.g2d.Sprite healthbar_0;
	int health = 10;
	float velocity;
	ArrayList<String> settings = new ArrayList<String>();
	private long pushTime;
	
	boolean tagged;
	float boundingradius;
	float MaxLinearSpeed;
	float MaxLinearAcceleration;
	float MaxAngularSpeed;
	float MaxAngularAcceleration;
	int speedTime;
	int team;
	SteeringBehavior<Vector2> behavior;
	SteeringAcceleration<Vector2> steerOutput;

	public Tank(Gamemode playScreen, Vector2 spawnPoint, InputProvider input, int team, ArrayList<String> settings) {
		// Creates a new tank.
		this.settings = settings;
		this.playScreen = playScreen;
		this.input = input;
		this.spawnPoint = spawnPoint;
		this.team = team;
		lastTimeShot = System.currentTimeMillis();
		
		sprite = new Sprite(new Texture("Boden2.png"));
		spriteTurret = new Sprite(new Texture("Turm2.png"));
		
		//sprite = new Sprite(new Texture("panzer35x55"));
		//spriteTurret = new Sprite(new Texture("Turm30x30.png"));
		
		setAim(new Vector2(0, 10));
		setDirection(new Vector2(0, 10));
		setupBody();

		healthbar_100 = new Sprite (new Texture("Healthbar_100%.png"));
		healthbar_90 = new Sprite (new Texture("Healthbar_90%.png"));
		healthbar_80 = new Sprite (new Texture("Healthbar_80%.png"));
		healthbar_70 = new Sprite (new Texture("Healthbar_70%.png"));
		healthbar_60 = new Sprite (new Texture("Healthbar_60%.png"));
		healthbar_50 = new Sprite (new Texture("Healthbar_50%.png"));
		healthbar_40 = new Sprite (new Texture("Healthbar_40%.png"));
		healthbar_30 = new Sprite (new Texture("Healthbar_30%.png"));
		healthbar_20 = new Sprite (new Texture("Healthbar_20%.png"));
		healthbar_10 = new Sprite (new Texture("Healthbar_10%.png"));
		healthbar_0 = new Sprite (new Texture("Healthbar_0%.png"));
		
		velocity = 1;
		speedTime = 3000;
	}

	@Override
	public void render(SpriteBatch batch, float delta) {
		sprite.draw(batch);
		spriteTurret.draw(batch);
		
		if (health == 10) {
			healthbar_100.draw(batch);}
		if (health == 9) {
			healthbar_90.draw(batch);}
		if (health == 8) {
			healthbar_80.draw(batch);}
		if (health == 7) {
			healthbar_70.draw(batch);}
		if (health == 6) {
			healthbar_60.draw(batch);}
		if (health == 5) {
			healthbar_50.draw(batch);}
		if (health == 4) {
			healthbar_40.draw(batch);}
		if (health == 3) {
			healthbar_30.draw(batch);}
		if (health == 2) {
			healthbar_20.draw(batch);}
		if (health == 1) {
			healthbar_10.draw(batch);}
		if (health == 0) {
			healthbar_0.draw(batch);}
		
		// Renders the object.
		// batch - The SpriteBatch that the object should be rendered to.
		// delta - The time in seconds between the last frames.
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
		body.setLinearDamping(1.5f);
		body.setAngularDamping(20);
		

		// Box shape
		PolygonShape tankShape = new PolygonShape();
		tankShape.setAsBox(12, 18);

		// Fixture shape
		FixtureDef tankFixtureDef = new FixtureDef();
		tankFixtureDef.shape = tankShape;
		tankFixtureDef.density = 0.001f;

		// Create Fixture and attatch to body
		Fixture tankFixture = this.body.createFixture(tankFixtureDef);
		tankFixture.setUserData(this);

		// Dispose
		tankShape.dispose();
		
		
	}

	public void setBodyToNullReference() {

	}

	public com.badlogic.gdx.math.Vector2 getAim() {
		return aim;
	}

	public Body getBody() {
		return body;
	}

	public long getLastTimeShot() {
		return this.lastTimeShot;
	}

	public void setDirection(com.badlogic.gdx.math.Vector2 direction) {
		this.direction = direction;
	}
	
	public Vector2 getDirection() {
		return direction;
	}
	
	public Vector2 convertFloatToVector(float angle) {
		Vector2 v = new Vector2();
		v.x = (float) Math.sin(angle)*-50;
		v.y = (float) Math.cos(angle)*50;
		return v;
	}
	
	public void setAim(com.badlogic.gdx.math.Vector2 aim) {
		this.aim = aim;
	}

	public void setLastTimeShot(long lastTimeShot) {
		this.lastTimeShot = lastTimeShot;
	}

	protected void setupSprites(com.badlogic.gdx.math.Vector2 spawnPoint) {
		// Instantiates this tank´s sprites. Should be called in the constructor and
		// overridden by subclasses if they need to have a different appearance.
	}

	public void update(float delta) {
		this.updateSprites();
		
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
		
		this.checkVelocityItem();
		
		Action[] acts = input.getInputs(this, playScreen, settings);
		for(Action a : acts) {
			
			if(a!=null) {
					a.act(delta);
			}
		}
		// InputProvider.getInputs(tank, playscreen);
		// Update this object.
		// delta - The time in seconds between the last frames.
	}
	
	public void updateSprites() {
		setDirection(convertFloatToVector(body.getAngle()));
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
		sprite.setRotation((float) Math.toDegrees(body.getAngle()));
		spriteTurret.setPosition(body.getPosition().x - spriteTurret.getWidth() / 2, body.getPosition().y - spriteTurret.getHeight() / 2);
		spriteTurret.setRotation(this.getAim().angle()-90);
	}
	
	protected void checkVelocityItem() {
		if((long)System.currentTimeMillis()-speedTime < this.getVelocityPushTime()) {
			this.setVelocity(Velocity.velocity);
		}else {
			this.setVelocity(1);
		}
	}
	
	public int getHealth () {
		return health;
	}
	
	public  void setHealth (int health) {
		this.health = health;
	}
	
	public void getroffen () {
		health = health -1;
	}
	
	public void Panzer_zerstoert() {
		playScreen.forgetAfterUpdate(this);
	}
	
	public float getVelocity() {
		return velocity;
	}
	
	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}
	
	public long getVelocityPushTime() {
		return pushTime;
	}
	
	public void setVelocityPushTime(long pushTime) {
		this.pushTime = pushTime;
	}
	 //Ki relevant
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

	@Override
	public float getMaxLinearSpeed() {
		return MaxLinearSpeed;
	}

	@Override
	public void setMaxLinearSpeed(float maxLinearSpeed) {
		this.MaxLinearSpeed = MaxLinearSpeed;
		
	}

	@Override
	public float getMaxLinearAcceleration() {
		return MaxLinearAcceleration;
	}

	@Override
	public void setMaxLinearAcceleration(float maxLinearAcceleration) {
		this.MaxLinearAcceleration = MaxLinearAcceleration;
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
	
	public int getTeam() {
		return team;
	}
	
//	public void HannesDerZombieSpawntgerne() {
//		System.out.println("Wir spawnen Hannes!!!!");
//		
////		playScreen.erhoeheneueZombies();
//		playScreen.Zombiespawner();
//	}
}

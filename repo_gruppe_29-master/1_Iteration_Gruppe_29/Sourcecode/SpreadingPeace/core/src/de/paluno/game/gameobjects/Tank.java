package de.paluno.game.gameobjects;
//Object representation of a tank, encapsulating both, the chassis of the tank and the turret. Both need to be drawn in the render-method, but only the chassis needs to have a body and be therefore affected by collisions and physics.

//Every Tank object also holds a reference to an InputProvider instance which defines how the tank acts. Therefore, during the tank´s update-method, it needs to call the getInputs-method of said InputProvider and call the act-method on all the returned Action objects.
//In terms of concerns this means that the tank itself decides if it should perform actions at all, while the InputProvider decides on what type of actions it performs, and the Action implementations define how the action is carried out.

import static de.paluno.game.Constants.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import de.paluno.game.input.InputProvider;
import de.paluno.game.screens.PlayScreen;

public class Tank extends java.lang.Object implements PhysicsObject, Renderable, Updatable {
	protected com.badlogic.gdx.physics.box2d.Body body;
	protected com.badlogic.gdx.graphics.g2d.Sprite spriteBody;
	protected com.badlogic.gdx.graphics.g2d.Sprite spriteTurret;
	protected PlayScreen playScreen;
	protected InputProvider input;
	protected static com.badlogic.gdx.math.Vector2 aim;
	public long lastTimeShot; // Timestamp of when this tank last shot.
	protected com.badlogic.gdx.graphics.g2d.Sprite sprite;
	private Vector2 spawnPoint;

	public Tank(PlayScreen playScreen, com.badlogic.gdx.math.Vector2 spawnPoint, InputProvider input) {
		// Creates a new tank.
		this.playScreen = playScreen;
		this.input = input;
		this.spawnPoint = spawnPoint;
		lastTimeShot = System.currentTimeMillis();

		sprite = new Sprite(new Texture("Panzer35x55.png"));
		spriteTurret = new Sprite(new Texture("Turm30x30.png"));
		setAim(new Vector2(0, 10));
		setupBody();

	}

	@Override
	public void render(SpriteBatch batch, float delta) {
		sprite.draw(batch);
		spriteTurret.draw(batch);
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
		body = PlayScreen.getWorld().createBody(tankBodyDef);
		// body.setUserData(this);

		// Box shape
		PolygonShape tankShape = new PolygonShape();
		tankShape.setAsBox(12, 18);

		// Fixture shape
		FixtureDef tankFixtureDef = new FixtureDef();
		tankFixtureDef.shape = tankShape;
		tankFixtureDef.density = 1.0f;

		// Create Fixture and attatch to body
		Fixture tankFixture = this.body.createFixture(tankFixtureDef);
		// tankFixture.setUserData(this);

		// Dispose
		tankShape.dispose();
	}

	public void setBodyToNullReference() {
		// After a body is destroyed, the reference to that body object should never be
		// used again.
	}

	public static com.badlogic.gdx.math.Vector2 getAim() {
		return aim;
	}

	public Body getBody() {
		// returns a reference to this object´s body.
		return body;

	}

	public long getLastTimeShot() {
		return this.lastTimeShot;

	}

	public void setAim(com.badlogic.gdx.math.Vector2 aim) {
		this.aim = aim;
	}

	void setLastTimeShot(long lastTimeShot) {
		this.lastTimeShot = lastTimeShot;
	}

	protected void setupSprites(com.badlogic.gdx.math.Vector2 spawnPoint) {
		// Instantiates this tank´s sprites. Should be called in the constructor and
		// overridden by subclasses if they need to have a different appearance.
	}

	public void update(float delta) {
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
		sprite.setRotation((float) Math.toDegrees(body.getAngle()));
		spriteTurret.setPosition(body.getPosition().x - spriteTurret.getWidth() / 2,
				body.getPosition().y - spriteTurret.getHeight() / 2);
		spriteTurret.setRotation((float) Math.toDegrees(body.getAngle()));
		// InputProvider.getInputs(tank, playscreen);
		// Update this object.
		// delta - The time in seconds between the last frames.
	}

}

package de.paluno.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import de.paluno.game.screens.Gamemode;

public class Water extends java.lang.Object implements PhysicsObject, Renderable, Updateable {

	private Gamemode playScreen;
	private Vector2 spawnPoint;
	private Body body;
	private Sprite spriteWall;
	private Shape shape;
	private Rectangle rectangle;
	private FixtureDef fixtureDef;

	public Water(Gamemode playScreen, Vector2 spawnPoint, Shape shape, FixtureDef fixtureDef, Rectangle rectangle) {
		this.playScreen = playScreen;
		this.spawnPoint = spawnPoint;
		this.shape = shape;
		this.fixtureDef = fixtureDef;
		this.rectangle = rectangle;

		setupBody();
	}

	@Override
	public void update(float delta) {
	}

	public void render(SpriteBatch batch, float delta) {
	}

	public void destroy() {
		playScreen.forgetAfterUpdate(this);
	}

	public void setupBody() {
		BodyDef wallBodyDef = new BodyDef();
		wallBodyDef.type = BodyDef.BodyType.StaticBody;
		wallBodyDef.position.set(spawnPoint);

		body = Gamemode.getWorld().createBody(wallBodyDef);
		body.setUserData(this);

		fixtureDef.shape = shape;

		Fixture wallFixture = this.body.createFixture(fixtureDef);
		wallFixture.setUserData(this);

		shape.dispose();
	}

	public Body getBody() {
		return body;
	}

	@Override
	public void setBodyToNullReference() {
		// TODO Auto-generated method stub

	}
}

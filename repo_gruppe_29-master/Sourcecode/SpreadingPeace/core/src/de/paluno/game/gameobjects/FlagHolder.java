package de.paluno.game.gameobjects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import de.paluno.game.Constants;
import de.paluno.game.input.InputProvider;
import de.paluno.game.screens.CaptureTheFlag;
import de.paluno.game.screens.Gamemode;

public class FlagHolder implements PhysicsObject, Renderable, Updateable, Steerable<Vector2> {
	protected Body body;
	protected Gamemode playScreen;
	private int team;
	private Vector2 spawnPoint;
	private int hasFlag;
	private Sprite spriteBody;
	private Sprite flag, noflag, flaginverse;
	private Vector2 spawnPointFlagHolder1 = new Vector2(100, 400);
	private Vector2 spawnPointFlagHolder2 = new Vector2(780, 150);
	private int zeiteinheit = 0;

	public FlagHolder(Gamemode playScreen, Vector2 spawnPoint, int team) {
		this.playScreen = playScreen;
		this.spawnPoint = spawnPoint;
		this.team = team;
		hasFlag = 1;
		setupSprites(spawnPoint);
		setupBody();
	}

	public void setupBody() {
		BodyDef flagHolderBodyDef = new BodyDef();
		flagHolderBodyDef.type = BodyDef.BodyType.StaticBody;
		flagHolderBodyDef.position.set(spawnPoint.x, spawnPoint.y);

		body = Gamemode.getWorld().createBody(flagHolderBodyDef);
		body.setUserData(this);

		PolygonShape flagHolderShape = new PolygonShape();
		flagHolderShape.setAsBox(27, 13);

		FixtureDef flagHolderFixtureDef = new FixtureDef();
		flagHolderFixtureDef.shape = flagHolderShape;

		Fixture flagHolderFixture = this.body.createFixture(flagHolderFixtureDef);
		flagHolderFixture.setUserData(this);

		flagHolderShape.dispose();
	}

	private void setupSprites(com.badlogic.gdx.math.Vector2 spawnPoint) {
		spriteBody = new Sprite(new Texture("Sockel_60x80.png"));
		if (getTeam() == 1) {
			flag = new Sprite(new Texture("Flagge_blau_40x80.png"));
			flaginverse = new Sprite(new Texture("Flagge_blau__invers_40x80.png"));
		}
		if (getTeam() == 2) {
			flag = new Sprite(new Texture("Flagge_rot_40x80.png"));
			flaginverse = new Sprite(new Texture("Flagge_rot__invers_40x80.png"));
		}
		noflag = new Sprite(new Texture("wallBlock.jpg"));

	}

	public void render(SpriteBatch batch, float delta) {
		spriteBody.draw(batch);
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
	}

	public void update(float delta) {
		spriteBody.setPosition(body.getPosition().x - 27, body.getPosition().y - 18);
		float flagPositionX = body.getPosition().x + 1;
		float flagPositionY = body.getPosition().y + 8;

		switch (hasFlag) {
		case 1:
			flag.setPosition(flagPositionX, flagPositionY);
			flaginverse.setPosition(flagPositionX, flagPositionY);

			break;
		case 0:
			noflag.setPosition(flagPositionX, flagPositionY);
			break;
		default:
			break;
		}
	}

	// getteam
	public int getTeam() {
		return this.team;
	}

	// getflag
	public int getFlag() {
		return this.hasFlag;
	}

	// setflag
	public void setFlag(int hasFlag) {
		this.hasFlag = hasFlag;
	}

	public void swapPosition() {
		Gdx.app.postRunnable(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (getTeam() == 1) {
					body.setTransform(spawnPointFlagHolder2, body.getAngle());
					;
				}
				if (getTeam() == 2) {
					body.setTransform(spawnPointFlagHolder1, body.getAngle());
					;
				}
			}
		});
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
		return 0;
	}

	public void setMaxLinearSpeed(float maxLinearSpeed) {
		// TODO Auto-generated method stub

	}

	@Override
	public float getMaxLinearAcceleration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaxLinearAcceleration(float maxLinearAcceleration) {
		// TODO Auto-generated method stub

	}

	@Override
	public float getMaxAngularSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaxAngularSpeed(float maxAngularSpeed) {
		// TODO Auto-generated method stub

	}

	@Override
	public float getMaxAngularAcceleration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaxAngularAcceleration(float maxAngularAcceleration) {
		// TODO Auto-generated method stub

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
		return 0;
	}

	@Override
	public boolean isTagged() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setTagged(boolean tagged) {
		// TODO Auto-generated method stub

	}

	public Vector2 newVector() {
		return new Vector2();
	}

	public void setBehavior(SteeringBehavior<Vector2> behavior) {

	}

	public SteeringBehavior<Vector2> getBehavior() {
		return null;
	}

	@Override
	public Body getBody() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBodyToNullReference() {
		// TODO Auto-generated method stub

	}

}
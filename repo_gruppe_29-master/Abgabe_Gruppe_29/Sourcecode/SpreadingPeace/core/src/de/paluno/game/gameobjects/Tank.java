package de.paluno.game.gameobjects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import de.paluno.game.Constants;
import de.paluno.game.input.EmptyInputProvider;
import de.paluno.game.input.InputProvider;
import de.paluno.game.input.actions.Action;
import de.paluno.game.screens.CaptureTheFlag;
import de.paluno.game.screens.Gamemode;

public class Tank extends java.lang.Object implements PhysicsObject, Renderable, Updateable, Steerable<Vector2> {
	protected Body body;
	protected Gamemode playScreen;
	protected InputProvider input;

	Fixture tankFixture; // hierhin verschoben

	protected Vector2 aim, direction, spawnPoint, center;
	public long lastTimeShot; // Timestamp of when this tank last shot.
	protected Sprite spriteBody, spriteTurret, turretboss1, turretboss2, turretboss3;
	protected Sprite leben_100, leben_95, leben_90, leben_85, leben_80, leben_75, leben_70, leben_65, leben_60,
			leben_55, leben_50, leben_45, leben_40, leben_35, leben_30, leben_25, leben_20, leben_15, leben_10, leben_5,
			leben_0, flag, noflag, flaginverse;
	protected Sprite spriteFlag;
	protected Sprite normalFl, fastFl, bounceFl, splittedFl;
	protected float velocity;
	ArrayList<String> settings = new ArrayList<String>();
	protected long pushTime;
	protected String name;
	protected int speedTime, team;
	protected int health;
	float alpha = 0.f;
	protected boolean respawnAnimation;
	protected boolean noAnimation;

	private float delay = 4;
	private float delay2 = 6;

	protected Colour colour;
	boolean tagged;
	protected int hasFlag;
	float boundingradius, MaxLinearSpeed, MaxLinearAcceleration, MaxAngularSpeed, MaxAngularAcceleration;
	SteeringBehavior<Vector2> behavior;
	SteeringAcceleration<Vector2> steerOutput;
//	int Blumenauswahl=0;
	public Blumenauswahl flower;
	Vector2 team1Spawn = new Vector2(100, 350);
	Vector2 team2Spawn = new Vector2(780, 200);
	int zeiteinheit = 0;

	protected int killcounter;

	protected Tank killer;
	private boolean schussbol = true;

	private double PlayerEntfernung;
	private double ZielEntfernungKi;
	private Vector2 ZielPastPos = new Vector2();

	public Tank() {
		// this.playScreen = new Deathmatch(new SEPgame(),"Keyboard", new
		// ArrayList<String>(), new ArrayList<String>(), "gfkhgscfjs" );
		this.input = new EmptyInputProvider();
		this.spawnPoint = new Vector2(100, 100);
		this.team = 1;
		this.colour = Colour.BLUE;
		this.setName("Hjhvshshsh");
		lastTimeShot = System.currentTimeMillis();
		setKillcounter(0);

		setAim(new Vector2(0, 1));
		setDirection(new Vector2(0, 10));
		// setupBody();
		// setupSprites(spawnPoint);

		hasFlag = 0;
		velocity = 1;
		if (this instanceof Tower || this instanceof Bosstank)
			health = 50;
		else
			health = 20;
		speedTime = 3000;

	}

	public Tank(Gamemode playScreen, Vector2 spawnPoint, InputProvider input, int team, ArrayList<String> settings,
			Colour colour, String name) {
		this.settings = settings;
		this.playScreen = playScreen;
		this.input = input;
		this.spawnPoint = spawnPoint;
		this.team = team;
		this.colour = colour;
		this.setName(name);
		lastTimeShot = System.currentTimeMillis();
		setKillcounter(0);

		setAim(new Vector2(0, 1));
		setDirection(new Vector2(0, 10));
		setupBody();
		setupSprites(spawnPoint);

		hasFlag = 0;
		velocity = 1;
		if (this instanceof Tower || this instanceof Bosstank)
			health = 50;
		else
			health = 20;
		speedTime = 3000;
		this.flower = Blumenauswahl.NORMALFLOWER;
		respawnAnimation = false;
		noAnimation = false;
		this.PlayerEntfernung = 500000;
		this.ZielEntfernungKi = 500000;
	}

	public boolean isAlive() {
		if (this.getHealth() > -1) {
			return true;
		} else {
			return false;
		}
	}

	public void setFlower(Blumenauswahl flower) {
		this.flower = flower;
	}

	public Blumenauswahl getFlower() {
		return flower;
	}

	public F_L_O_W_E_R useFlower() {
		return FlowerFactory.create(this.flower, this.playScreen, this);
	}

	// Sprites

	protected void setupColours() {
		switch (colour) {
		case BLUE:
			spriteBody = new Sprite(new Texture("B_Blau.png"));
			spriteTurret = new Sprite(new Texture("T_Blau.png"));
			break;
		case BROWN:
			spriteBody = new Sprite(new Texture("B_Braun.png"));
			spriteTurret = new Sprite(new Texture("T_Braun.png"));
			break;
		case GREEN:
			spriteBody = new Sprite(new Texture("B_Gruen.png"));
			spriteTurret = new Sprite(new Texture("T_Gruen.png"));
			break;
		case PINK:
			spriteBody = new Sprite(new Texture("B_Pink.png"));
			spriteTurret = new Sprite(new Texture("T_Pink.png"));
			break;
		case RED:
			spriteBody = new Sprite(new Texture("B_Rot.png"));
			spriteTurret = new Sprite(new Texture("T_Rot.png"));
			break;
		case YELLOW:
			spriteBody = new Sprite(new Texture("B_Gelb.png"));
			spriteTurret = new Sprite(new Texture("T_Gelb.png"));
			break;
		default:
			spriteBody = new Sprite(new Texture("B_Blau.png"));
			spriteTurret = new Sprite(new Texture("T_Blau.png"));
			break;
		}
	}

	protected void setupSprites(Vector2 spawnPoint) {
		if (this instanceof Tower) {
			spriteBody = new Sprite(new Texture("1x1.png"));
			spriteTurret = new Sprite(new Texture("T_Braun.png"));
		} else {
			this.setupColours();
		}

		if (getTeam() == 1) {
			flag = new Sprite(new Texture("Flagge_rot_40x80.png"));
			flaginverse = new Sprite(new Texture("Flagge_rot__invers_40x80.png"));
		}
		if (getTeam() == 2) {
			flag = new Sprite(new Texture("Flagge_blau_40x80.png"));
			flaginverse = new Sprite(new Texture("Flagge_blau__invers_40x80.png"));
		}
		noflag = new Sprite(new Texture("wallBlock.jpg"));
		leben_100 = new Sprite(new Texture("Leben_100%.png"));
		leben_95 = new Sprite(new Texture("Leben_95%.png"));
		leben_90 = new Sprite(new Texture("Leben_90%.png"));
		leben_85 = new Sprite(new Texture("Leben_85%.png"));
		leben_80 = new Sprite(new Texture("Leben_80%.png"));
		leben_75 = new Sprite(new Texture("Leben_75%.png"));
		leben_70 = new Sprite(new Texture("Leben_70%.png"));
		leben_65 = new Sprite(new Texture("Leben_65%.png"));
		leben_60 = new Sprite(new Texture("Leben_60%.png"));
		leben_55 = new Sprite(new Texture("Leben_55%.png"));
		leben_50 = new Sprite(new Texture("Leben_50%.png"));
		leben_45 = new Sprite(new Texture("Leben_45%.png"));
		leben_40 = new Sprite(new Texture("Leben_40%.png"));
		leben_35 = new Sprite(new Texture("Leben_35%.png"));
		leben_30 = new Sprite(new Texture("Leben_30%.png"));
		leben_25 = new Sprite(new Texture("Leben_25%.png"));
		leben_20 = new Sprite(new Texture("Leben_20%.png"));
		leben_15 = new Sprite(new Texture("Leben_15%.png"));
		leben_10 = new Sprite(new Texture("Leben_10%.png"));
		leben_5 = new Sprite(new Texture("Leben_5%.png"));
		leben_0 = new Sprite(new Texture("Leben_0%.png"));
		normalFl = new Sprite(new Texture("NormalFlower.png"));
		fastFl = new Sprite(new Texture("FastFlower.png"));
		bounceFl = new Sprite(new Texture("BounceFlower.png"));
		splittedFl = new Sprite(new Texture("SplittedFlower.png"));

	}

	protected void updateSpritesBody() {
		setDirection(convertFloatToVector(body.getAngle()));
		spriteBody.setPosition(body.getPosition().x - spriteBody.getWidth() / 2,
				body.getPosition().y - spriteBody.getHeight() / 2);
		spriteBody.setRotation((float) Math.toDegrees(body.getAngle()));
	}

	protected void updateSpritesTurret() {
		spriteTurret.setPosition(body.getPosition().x - spriteTurret.getWidth() / 2,
				body.getPosition().y - spriteTurret.getHeight() / 2);
		spriteTurret.setRotation(this.getAim().angle() - 90);
	}

	protected void updateHealthbarSprites() {
		float healthbarX = body.getPosition().x - 50;
		float healthbarY = body.getPosition().y + 40;

		switch (health) {
		case 20:
			leben_100.setPosition(healthbarX, healthbarY);
			break;
		case 19:
			leben_95.setPosition(healthbarX, healthbarY);
			break;
		case 18:
			leben_90.setPosition(healthbarX, healthbarY);
			break;
		case 17:
			leben_85.setPosition(healthbarX, healthbarY);
			break;
		case 16:
			leben_80.setPosition(healthbarX, healthbarY);
			break;
		case 15:
			leben_75.setPosition(healthbarX, healthbarY);
			break;
		case 14:
			leben_70.setPosition(healthbarX, healthbarY);
			break;
		case 13:
			leben_65.setPosition(healthbarX, healthbarY);
			break;
		case 12:
			leben_60.setPosition(healthbarX, healthbarY);
			break;
		case 11:
			leben_55.setPosition(healthbarX, healthbarY);
			break;
		case 10:
			leben_50.setPosition(healthbarX, healthbarY);
			break;
		case 9:
			leben_45.setPosition(healthbarX, healthbarY);
			break;
		case 8:
			leben_40.setPosition(healthbarX, healthbarY);
			break;
		case 7:
			leben_35.setPosition(healthbarX, healthbarY);
			break;
		case 6:
			leben_30.setPosition(healthbarX, healthbarY);
			break;
		case 5:
			leben_25.setPosition(healthbarX, healthbarY);
			break;
		case 4:
			leben_20.setPosition(healthbarX, healthbarY);
			break;
		case 3:
			leben_15.setPosition(healthbarX, healthbarY);
			break;
		case 2:
			leben_10.setPosition(healthbarX, healthbarY);
			break;
		case 1:
			leben_5.setPosition(healthbarX, healthbarY);
			break;
		case 0:
			leben_0.setPosition(healthbarX, healthbarY);
			break;
		default:
			leben_100.setPosition(healthbarX, healthbarY);
			break;
		}
	}

	protected void updateFlagSprites() {
		switch (hasFlag) {
		case 1:
			flag.setPosition(body.getPosition().x, body.getPosition().y);
			flaginverse.setPosition(body.getPosition().x, body.getPosition().y);
			break;
		case 0:
			noflag.setPosition(body.getPosition().x - 50, body.getPosition().y + 30);
			break;
		default:
			break;
		}
	}

	public void updateFlowerAuswahl() {
		float FloweranzeigeX = body.getPosition().x - 5;
		float FloweranzeigeY = body.getPosition().y - 5;

		switch (this.getFlower()) {
		case NORMALFLOWER:
			normalFl.setPosition(FloweranzeigeX, FloweranzeigeY);
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
		if (!(this instanceof Tower))
			body.setLinearDamping(1.5f);
		else
			body.setLinearDamping(100000f);
		body.setAngularDamping(2);

		// Box shape
		PolygonShape tankShape = new PolygonShape();
		tankShape.setAsBox(12, 18);

		// Fixture shape
		FixtureDef tankFixtureDef = new FixtureDef();
		tankFixtureDef.shape = tankShape;
		tankFixtureDef.density = 0.001f;
		tankFixtureDef.restitution = 0f;
		
		// Create Fixture and attatch to body
		tankFixture = this.body.createFixture(tankFixtureDef);
		tankFixture.setUserData(this);

		// Dispose
		tankShape.dispose();
	}

	public Fixture getFixture() {
		return this.tankFixture;
	}

	public void setBodyToNullReference() {
//		this.body = null;
	}

	public Vector2 getAim() {
		return aim;
	}

	public void setAim(Vector2 aim) {
		this.aim = aim;
	}

	public Body getBody() {
		return body;
	}

	public long getLastTimeShot() {
		return this.lastTimeShot;
	}

	public void setDirection(Vector2 direction) {
		this.direction = direction;
	}

	public Vector2 getDirection() {
		return direction;
	}

	public Vector2 convertFloatToVector(float angle) {
		Vector2 v = new Vector2();
		v.x = (float) Math.sin(angle) * -50;
		v.y = (float) Math.cos(angle) * 50;
		return v;
	}

	public void setLastTimeShot(long lastTimeShot) {
		this.lastTimeShot = lastTimeShot;
	}

	public void update(float delta) {
		if (this.playScreen.getHud().isReady()) {
			Action[] acts = input.getInputs(this, playScreen, settings);
			for (Action a : acts) {
				if (a != null) {
					a.act(delta);
				}
			}
		}
		this.checkVelocityItem();
//		this.setCenter(this.getBody().getPosition().cpy().rotate(this.getBody().getAngle()).add(new Vector2(6,9).cpy().rotate(this.getBody().getAngle())));

		if (body.getPosition().x > Constants.BILDSCHIRMBREITE || body.getPosition().x < 0
				|| body.getPosition().y > Constants.BILDSCHIRMHOEHE || body.getPosition().y < 0) {
			this.destroy();
		}
		this.updateSpritesBody();
		this.updateSpritesTurret();
		this.updateHealthbarSprites();
		this.updateFlagSprites();
		this.updateFlowerAuswahl();
	}

	protected void checkVelocityItem() {
		if ((long) System.currentTimeMillis() - speedTime < this.getVelocityPushTime()) {
			this.setVelocity(Velocity.velocity);
		} else {
			this.setVelocity(1);
		}
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void getroffen() {
		health = health - 1;
	}

	public void setSchussBool(boolean f) {
		this.schussbol = f;
	}

	public boolean getSchussBol() {
		return schussbol;
	}

	public void destroy() {
		playScreen.forgetAfterUpdate(this);
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("explosion01.wav"));
		sound.play(1f);

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

	public void respawn() {
		Gdx.app.postRunnable(new Runnable() {

			@Override
			public void run() {
				body.setActive(false);
				noAnimation = true;
				Timer.schedule(new Task() {
					@Override
					public void run() {
						body.setActive(true);
						noAnimation = false;
						respawn2();
					}
				}, delay2);
				if (getTeam() == 1) {
					if (getFlag() == 1) {
						setFlag(0);
						CaptureTheFlag.resetFlagHolder2();
					}
					if (CaptureTheFlag.round == 1) {
						body.setTransform(team1Spawn, body.getAngle());
					} else {
						body.setTransform(team2Spawn, body.getAngle());

					}
				}
				if (getTeam() == 2) {
					if (getFlag() == 1) {
						setFlag(0);
						CaptureTheFlag.resetFlagHolder1();
					}
					if (CaptureTheFlag.round == 1) {
						body.setTransform(team2Spawn, body.getAngle());
					} else {
						body.setTransform(team1Spawn, body.getAngle());
					}
				}
			}
		});

	}

	private void respawn2() {
		respawnAnimation = true;
		Timer.schedule(new Task() {
			@Override
			public void run() {
				respawnAnimation = false;
			}
		}, delay);
	}

	public void reset() {
		Gdx.app.postRunnable(new Runnable() {

			@Override
			public void run() {
				setHealth(20);
				respawnAnimation = true;
				Timer.schedule(new Task() {
					@Override
					public void run() {
						respawnAnimation = false;
					}
				}, delay);

				if (getTeam() == 1) {
					if (getFlag() == 1) {
						setFlag(0);
						CaptureTheFlag.resetFlagHolder2();
					}
					if (CaptureTheFlag.round == 1) {
						body.setTransform(team1Spawn, body.getAngle());
					} else {
						body.setTransform(team2Spawn, body.getAngle());

					}
				}
				if (getTeam() == 2) {
					if (getFlag() == 1) {
						setFlag(0);
						CaptureTheFlag.resetFlagHolder1();
					}
					if (CaptureTheFlag.round == 1) {
						body.setTransform(team2Spawn, body.getAngle());
					} else {
						body.setTransform(team1Spawn, body.getAngle());
					}
				}
			}
		});
	}

	public void setVelocityPushTime(long pushTime) {
		this.pushTime = pushTime;
	}

	// Ki relevant
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
		body.setTransform(body.getPosition(), orientation);
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
		return 0.01f;
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
		return this.boundingradius;
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

	public Vector2 getCenter() {
		return center;
	}

	public void setCenter(Vector2 center) {
		this.center = center;
	}

	public Tank getKiller() {
		return killer;
	}

	public void setKiller(Tank killer) {
		this.killer = killer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKillcounter() {
		return killcounter;
	}

	public void setKillcounter(int killcounter) {
		this.killcounter = killcounter;
	}

	public int getFlag() {
		return this.hasFlag;
	}

	public void setFlag(int hasFlag) {
		this.hasFlag = hasFlag;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public void setPlayerEntfernung(double PlayerEntfernung) {
		this.PlayerEntfernung = PlayerEntfernung;
	}

	public void setZielEntfernungKi(double ZielEntfernungKi) {
		this.ZielEntfernungKi = ZielEntfernungKi;
	}

	public double getPlayerEntfernung() {
		return PlayerEntfernung;
	}

	public double getZielEntfernungKi() {
		return ZielEntfernungKi;
	}

	public void setZielPastPosX(float ZielPastPosX) {
		this.ZielPastPos.x = ZielPastPosX;
	}

	public void setZielPastPosY(float ZielPastPosY) {
		this.ZielPastPos.y = ZielPastPosY;
	}

	public Vector2 getZielPastPos() {
		return ZielPastPos;
	}
//	public void HannesDerZombieSpawntgerne() {
//		System.out.println("Wir spawnen Hannes!!!!");
//		
////		playScreen.erhoeheneueZombies();
//		playScreen.Zombiespawner();
//	}
}

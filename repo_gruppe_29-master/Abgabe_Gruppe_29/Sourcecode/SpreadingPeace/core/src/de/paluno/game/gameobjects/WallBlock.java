package de.paluno.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import de.paluno.game.screens.Deathmatch;
import de.paluno.game.screens.Gamemode;

public class WallBlock extends java.lang.Object implements PhysicsObject, Renderable, Updateable{

	private Gamemode playScreen;
	private Vector2 spawnPoint;
	private int length, height;
	private float health;
	private Body body; 
	private Sprite spriteWall;
	private boolean destructible, edge;
	
	public WallBlock(Gamemode playScreen, Vector2 spawnPoint,int length, int heigth, boolean edge, boolean destructible) {
		this.spawnPoint = spawnPoint;
		this.playScreen = playScreen;
		this.length = length;
	    this.height = heigth;
	    this.setHealth(10);
	    this.setDestructible(destructible);
	    this.setEdge(edge);
	    
	    
		setupBody();
		setupSprites();
	}
	
	private void setupSprites() {
    	if(length == 17 && height == 1920) { //Seite
	    	spriteWall = new Sprite(new Texture("wallsides.jpg"));
	    	spriteWall.setPosition(body.getPosition().x - spriteWall.getWidth() / 2, body.getPosition().y - spriteWall.getHeight() / 2);
    	} else if(length == 540 && height == 17){//Oben/Unten
	    		spriteWall = new Sprite(new Texture("wallaboveandbelow.jpg")); //also eine obere/untere wand
	    		spriteWall.setPosition(body.getPosition().x - spriteWall.getWidth() / 2, body.getPosition().y - spriteWall.getHeight() / 2);
	    }else {//Einzelner Block
	    	spriteWall = new Sprite(new Texture("wallBlock.jpg"));
	    	spriteWall.setPosition(body.getPosition().x - spriteWall.getWidth() / 2, body.getPosition().y - spriteWall.getHeight() / 2);
	    }
	}
	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render(SpriteBatch batch, float delta) {
		spriteWall.draw(batch);
	}

	@Override
	public void setupBody() {
		int wallPositionX = (int)(spawnPoint.x);
		int wallPositionY = (int)(spawnPoint.y);
		BodyDef wallbody = new BodyDef();
		wallbody.fixedRotation = true;
		wallbody.type = BodyDef.BodyType.StaticBody;
		wallbody.position.set(wallPositionX, wallPositionY);

		body = Gamemode.getWorld().createBody(wallbody);
		body.setUserData(this);

		PolygonShape viereck = new PolygonShape();
		viereck.setAsBox(length,height);
		FixtureDef wallfixture = new FixtureDef();
		wallfixture.shape = viereck;
		wallfixture.density = 1.0f;
		
		// Create Fixture and attach to body
		Fixture mauerFixture = this.body.createFixture(wallfixture);
		mauerFixture.setUserData(this);

		// Dispose
		viereck.dispose();
	}

	@Override
	public Body getBody() {
		return body;
	}

	@Override
	public void setBodyToNullReference() {
	}
	
	public void destroy() {
		playScreen.forgetAfterUpdate(this);
	}

	public boolean isDestructible() {
		return destructible;
	}

	public void setDestructible(boolean destructible) {
		this.destructible = destructible;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public boolean isEdge() {
		return edge;
	}

	public void setEdge(boolean edge) {
		this.edge = edge;
	}

}

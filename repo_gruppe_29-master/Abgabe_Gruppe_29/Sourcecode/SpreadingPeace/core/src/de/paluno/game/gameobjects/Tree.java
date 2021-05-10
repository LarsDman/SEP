package de.paluno.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;

import de.paluno.game.screens.Gamemode;

public class Tree extends java.lang.Object implements PhysicsObject, Renderable, Updateable{
	
	private float health;
	private Gamemode playScreen;
	private Vector2 spawnPoint;
	private Body body; 
	private Sprite spriteTree;
	private Shape shape;
	private FixtureDef fixtureDef;
	
	public Tree(Gamemode playScreen, Vector2 spawnPoint, Shape shape, FixtureDef fixtureDef, float health) {
		this.playScreen = playScreen;
		this.spawnPoint = spawnPoint;
	    this.health = health;
	    this.shape = shape;
	    this.fixtureDef = fixtureDef;
	    
	    setupBody();
	    setupSprites();
	}

	private void setupSprites() {
		
		spriteTree = new Sprite(new Texture("Tree.png"));
		spriteTree.setPosition(body.getPosition().x - spriteTree.getWidth() / 2, 
							   body.getPosition().y - spriteTree.getHeight() / 2);
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render(SpriteBatch batch, float delta) {
		spriteTree.draw(batch);
	}

	@Override
	public void setupBody() {
		
		// Statischen Koertper erzeugen
		BodyDef treeBodyDef = new BodyDef();
		treeBodyDef.type = BodyDef.BodyType.StaticBody;
		treeBodyDef.position.set(spawnPoint);
		
		body = Gamemode.getWorld().createBody(treeBodyDef);
		body.setUserData(this);
		
		fixtureDef.shape = shape;
		
		// Von der Shape eine Fixture fuer jeden Koertper erstellen
		Fixture treeFixture = this.body.createFixture(fixtureDef);
		treeFixture.setUserData(this);
		
		shape.dispose();
	}


	public Body getBody() {
		return body;
	}

	public void setBodyToNullReference() {
		
	}
	
	public void destroy() {
		playScreen.forgetAfterUpdate(this);
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}
}

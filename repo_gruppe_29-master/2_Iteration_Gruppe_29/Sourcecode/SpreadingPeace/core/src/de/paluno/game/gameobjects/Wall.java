package de.paluno.game.gameobjects;
//Subclass of tank representing the target that is to be shot by the player. It needs to override the setupSprites- method at least and can also override the setupBody-method to get different physical properties if needed.

import java.util.ArrayList;  
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import de.paluno.game.input.EmptyInputProvider;
import de.paluno.game.screens.Deathmatch;
import de.paluno.game.screens.Gamemode;

public class Wall extends Tank {

	public boolean remove;
	public int width;
	public int height;
	public boolean isMiddleWall=false;
	static ArrayList<String> settings = new ArrayList<String>();
	
	public Wall(Gamemode playScreen, com.badlogic.gdx.math.Vector2 spawnPoint,int x, int y) {
		super(playScreen, spawnPoint, new EmptyInputProvider(), 1, settings); //die 1 f�r die teamnummer ist ein platzhalter
		width=x;
	    height=y;
	    if(width==17&&height>500) { //ist folglich also eine seitliche wand
	    	sprite = new Sprite(new Texture("wallsides.jpg"));
	    }
	    else {
	    	if(width==540) sprite = new Sprite(new Texture("wallaboveandbelow.jpg")); //also eine obere/untere wand
	    	else sprite = new Sprite(new Texture("wallmiddle.jpg")); //mittelwand
	    }
		
		setupBody2();
		
	}
	
	public void setMiddle(boolean isIt) {this.isMiddleWall=isIt;}
	
	public boolean getMiddle()  //zum herausfinden, ob das aktuelle Wall-objekt die mittlere wand ist
	{
		return this.isMiddleWall;
	}

	protected void setupSprites(com.badlogic.gdx.math.Vector2 spawnPoint) {
		// Instantiates this tank´s sprites. Should be called in the constructor and
		// overridden by subclasses if they need to have a different appearance.
		// spawnPoint - The initial position of the sprites.
	}

	public void render(SpriteBatch batch, float delta) {
		sprite.draw(batch);
	}

	public Body getBody() {
		// returns a reference to this object´s body.
		return body;

	}

	public void destroy() {	
		health = 999999999;
		final int wallPositionX = (int)(spawnPoint.x); // keine ahnung warum die 2 zahlen der rand
		final int wallPositionY = (int)(spawnPoint.y); // des Bildschirms sind
		
		Gdx.app.postRunnable(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				body.setTransform(wallPositionX, wallPositionY, 0);
				body.setLinearVelocity(new Vector2(0,0));
			}
		});
	}

	public void setupBody() {
//		// Creates this object´s body
//		int wallPositionX = (int)(spawnPoint.x); // keine ahnung warum die 2 zahlen der rand
//		int wallPositionY = (int)(spawnPoint.y); // des Bildschirms sind
//		BodyDef wallbody = new BodyDef();
//		wallbody.fixedRotation = true;
//		wallbody.type = BodyDef.BodyType.StaticBody;
//		wallbody.position.set(wallPositionX, wallPositionY);
//
//		body = Deathmatch.getWorld().createBody(wallbody);
//		body.setUserData(this);
//
//		PolygonShape viereck = new PolygonShape();
//		viereck.setAsBox(width,height);
//		FixtureDef wallfixture = new FixtureDef();
//		wallfixture.shape = viereck;
//		wallfixture.density = 1.0f;
//		// Create Fixture and attatch to body
//		Fixture mauerFixture = this.body.createFixture(wallfixture);
//		mauerFixture.setUserData(this);
//
//		// Dispose
//		viereck.dispose();
//		
	}
	
	public void setupBody2() {
		// Creates this object´s body
		int wallPositionX = (int)(spawnPoint.x); // keine ahnung warum die 2 zahlen der rand
		int wallPositionY = (int)(spawnPoint.y); // des Bildschirms sind
		BodyDef wallbody = new BodyDef();
		wallbody.fixedRotation = true;
		wallbody.type = BodyDef.BodyType.StaticBody;
		wallbody.position.set(wallPositionX, wallPositionY);

		body = Deathmatch.getWorld().createBody(wallbody);
		body.setUserData(this);

		PolygonShape viereck = new PolygonShape();
		viereck.setAsBox(width,height);
		FixtureDef wallfixture = new FixtureDef();
		wallfixture.shape = viereck;
		wallfixture.density = 1.0f;
		// Create Fixture and attatch to body
		Fixture mauerFixture = this.body.createFixture(wallfixture);
		mauerFixture.setUserData(this);

		// Dispose
		viereck.dispose();
		
	}
	
}

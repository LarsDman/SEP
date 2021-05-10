package de.paluno.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.screens.Gamemode;

public class BounceFlower extends F_L_O_W_E_R {

	private boolean collisionWall;
	private int wallCounter;
	
	public BounceFlower(Gamemode playScreen, Vector2 origin, Vector2 direction, Tank actor) {
		super(playScreen, origin, direction, actor);
		wallCounter = 0;
		collisionWall = false;
		setDamage(2);
		setSpeed(250);
		setupBody();
		setupSprites();
	}
	
	@Override
	protected void setupSprites() {
		sprite = new Sprite(new Texture("BounceFlower.png"));
		sprite.setPosition(origin.x, origin.y);
	}
	
	@Override
	public void update(float delta) {
		if(collisionWall) {
			this.setWallCounter(this.getWallCounter()+1);
			collisionWall = false;
		}
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
		this.outOfBounds();
	}
	
	public boolean isCollisionWall() {
		return collisionWall;
	}

	public void setCollisionWall(boolean collisionWall) {
		this.collisionWall = collisionWall;
	}
	
	public int getWallCounter() {
		return wallCounter;
	}

	public void setWallCounter(int wallCounter) {
		this.wallCounter = wallCounter;
	}
}

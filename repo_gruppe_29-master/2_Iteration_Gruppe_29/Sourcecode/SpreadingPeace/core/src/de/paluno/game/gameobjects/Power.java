package de.paluno.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.screens.Gamemode;

public class Power extends Item{
	
	private int health;
	
	public Power(Gamemode gamemode, Vector2 spawnPoint) {
		super(gamemode, spawnPoint);
		setupSprites(spawnPoint);
		health = 1;
	}
	
	@Override
	protected void setupSprites(com.badlogic.gdx.math.Vector2 spawnPoint) {
		sprite = new Sprite(new Texture("Healthkit_10x10.png"));
		sprite.setPosition(spawnPoint.x, spawnPoint.y);
	}
	
	public int getHealth() {
		return health;
	}
	
}
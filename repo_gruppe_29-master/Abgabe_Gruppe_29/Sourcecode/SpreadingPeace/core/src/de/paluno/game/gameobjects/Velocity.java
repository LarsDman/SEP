package de.paluno.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.screens.Gamemode;

public class Velocity extends Item{
	
	static int velocity;
	
	public Velocity(Gamemode gamemode, Vector2 spawnPoint) {
		super(gamemode, spawnPoint);
		velocity = 3;
	}
	
	@Override
	protected void setupSprites(com.badlogic.gdx.math.Vector2 spawnPoint) {
		sprite = new Sprite(new Texture("Nitro_Kit.png"));
		sprite.setPosition(spawnPoint.x, spawnPoint.y);
	}
}
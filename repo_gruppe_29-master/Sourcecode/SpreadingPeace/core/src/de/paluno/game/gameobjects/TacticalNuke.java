package de.paluno.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.screens.Gamemode;

public class TacticalNuke extends Item{
	
	public TacticalNuke(Gamemode gamemode, Vector2 spawnPoint) {
		super(gamemode, spawnPoint);
	}
	
	@Override
	protected void setupSprites(com.badlogic.gdx.math.Vector2 spawnPoint) {
		sprite = new Sprite(new Texture("nuke.png"));
		sprite.setScale(0.07f);
		sprite.setPosition(spawnPoint.x, spawnPoint.y);
	}
	

	
}
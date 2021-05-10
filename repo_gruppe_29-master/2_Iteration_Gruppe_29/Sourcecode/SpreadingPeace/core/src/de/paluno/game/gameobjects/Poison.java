package de.paluno.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import de.paluno.game.screens.Gamemode;

public class Poison extends Item{
	
	private int damage;
	
	public Poison(Gamemode gamemode, Vector2 spawnPoint) {
		super(gamemode, spawnPoint);
		setupSprites(spawnPoint);
		damage = 1;
	}
	
	@Override
	protected void setupSprites(com.badlogic.gdx.math.Vector2 spawnPoint) {
		sprite = new Sprite(new Texture("poisonkit.png"));
		sprite.setPosition(spawnPoint.x, spawnPoint.y);
	}

	public int getDamage() {
		return damage;
	}
	
}
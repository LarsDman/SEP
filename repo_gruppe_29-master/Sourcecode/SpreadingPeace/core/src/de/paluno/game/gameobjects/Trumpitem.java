package de.paluno.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import de.paluno.game.screens.Gamemode;

public class Trumpitem extends Item{
	
	
	
	public Trumpitem(Gamemode gamemode, Vector2 spawnPoint) {
		super(gamemode, spawnPoint);
	}
	
	@Override
	protected void setupSprites(com.badlogic.gdx.math.Vector2 spawnPoint) {
		sprite = new Sprite(new Texture("trumpitem.png"));
		sprite.setPosition(spawnPoint.x, spawnPoint.y);
		sprite.setBounds(spawnPoint.x, spawnPoint.y, 40, 40);
	}

	
}
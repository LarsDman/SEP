package de.paluno.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.screens.Gamemode;

public class FastFlower extends F_L_O_W_E_R {
	
	public FastFlower (Gamemode playScreen, Vector2 origin, Vector2 direction, Tank actor) {
		super(playScreen, origin, direction, actor);
		setDamage(2);
		setSpeed(1000);
		setupBody();
		setupSprites();
	}
	
	@Override
	protected void setupSprites() {
		sprite = new Sprite(new Texture("FastFlower.png"));
		sprite.setPosition(origin.x, origin.y);
	}
	
	@Override
	public void update(float delta) {
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
		this.outOfBounds();
	}
}

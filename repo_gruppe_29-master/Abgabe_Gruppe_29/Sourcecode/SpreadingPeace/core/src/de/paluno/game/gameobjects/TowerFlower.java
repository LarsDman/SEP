package de.paluno.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.screens.Gamemode;

public class TowerFlower extends F_L_O_W_E_R{

	public TowerFlower (Gamemode playScreen, Vector2 origin, Vector2 direction, Tank actor) {
		super(playScreen, origin, direction, actor);
		setDamage(3);
		setSpeed(250);
		setupBody();
		setupSprites();
	}
	
	@Override
	protected void setupSprites() {
		sprite = new Sprite(new Texture("NormalFlower.png"));
	}
	
	@Override
	public void update(float delta) {
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
		this.outOfBounds();
	}
}

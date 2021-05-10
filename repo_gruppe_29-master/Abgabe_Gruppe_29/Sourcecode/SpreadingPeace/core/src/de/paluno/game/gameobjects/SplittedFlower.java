package de.paluno.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import de.paluno.game.screens.Gamemode;

public class SplittedFlower extends F_L_O_W_E_R {
	
	public SplittedFlower(Gamemode playScreen, Vector2 origin, Vector2 direction, Tank actor) {
		super(playScreen, origin, direction, actor);
		setDamage(1);
		setSpeed(125);
		setupBody();
		setupSprites();
	}
	
	@Override
	protected void setupSprites() {
		sprite = new Sprite(new Texture("SplittedFlower.png"));
	}
	
	@Override
	public void update(float delta) {
		Vector2 actualPos = new Vector2(this.getBody().getPosition().x, this.getBody().getPosition().y);
		if(calculateDistance(origin, actualPos) > 100) {
			this.explode();
		}
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
		this.outOfBounds();
	}
}

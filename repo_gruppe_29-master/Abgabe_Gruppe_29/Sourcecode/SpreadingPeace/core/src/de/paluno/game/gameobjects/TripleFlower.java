package de.paluno.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import de.paluno.game.screens.Gamemode;

public class TripleFlower extends F_L_O_W_E_R{

	private Vector2 direction;
	
	public TripleFlower(Gamemode playScreen, Vector2 origin, Vector2 direction, Tank actor) {
		super(playScreen, origin, direction, actor);
		setDamage(2);
		setSpeed(200);
		this.direction = direction.cpy();//Aim würde sich ohne cpy mit verändern, sonst in Shoot cpy hinzufuegen
		setupBody();
		setupSprites();
	}
	
	@Override
	protected void setupSprites() {
		sprite = new Sprite(new Texture("SplittedFlower.png"));
		sprite.setPosition(origin.x, origin.y);
	}
	
	@Override
	public void update(float delta) {
		checkDistance();
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
		outOfBounds();
	}
	
	public void checkDistance() {
		Vector2 actualPos = new Vector2(this.getBody().getPosition().x, this.getBody().getPosition().y);
		if(calculateDistance(origin, actualPos) > 100) {
			Vector2 newOrigin = actualPos;
			SplittedFlower split1 = new SplittedFlower(playScreen, newOrigin.cpy().add(direction.cpy().nor().scl(15)), direction.cpy().nor(), actor);//gerade
			SplittedFlower split2 = new SplittedFlower(playScreen, newOrigin.cpy().add(direction.cpy().nor().rotate(45).scl(15)), direction.cpy().nor().rotate(10), actor);//links
			SplittedFlower split3 = new SplittedFlower(playScreen, newOrigin.cpy().add(direction.cpy().nor().rotate(-45).scl(15)), direction.cpy().nor().rotate(-10), actor);//rechts
			explode();
			playScreen.registerAfterUpdate(split1);
			playScreen.registerAfterUpdate(split2);
			playScreen.registerAfterUpdate(split3);
		}
	}
}

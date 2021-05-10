package de.paluno.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.screens.Gamemode;
import de.paluno.game.screens.Survival_mit_Geld;

public class Coin extends Item {
	int zeiteinheit = 0;
	public Coin(Gamemode gamemode, Vector2 spawnPoint) {
		super(gamemode, spawnPoint);
	}

	@Override
	protected void setupSprites(Vector2 spawnPoint) {
		sprite = new Sprite(new Texture("Coin_gerade_15x15.png"));
//		sprite.setPosition(spawnPoint.x, spawnPoint.y);
//		sprite.setPosition(200, 200);
	}
	
	public void update(float delta) {
		zeiteinheit++;
		if (zeiteinheit >= 40 && zeiteinheit < 80) {
			sprite = new Sprite(new Texture("Coin_leicht_gedreht_15x15.png"));
		}
		else if (zeiteinheit >= 80 && zeiteinheit < 120) {
			sprite = new Sprite(new Texture("Coin_stark_gedreht_15x15.png"));
		}
		else if (zeiteinheit >= 120 && zeiteinheit < 160) {
			sprite = new Sprite(new Texture("Coin_komplett_gedreht_15x15.png"));
		}
		else if (zeiteinheit >= 160 && zeiteinheit < 200) {
			sprite = new Sprite(new Texture("Coin_stark_gedreht_gespiegelt_15x15.png"));
		}
		else if (zeiteinheit >= 200 && zeiteinheit < 240) {
			sprite = new Sprite(new Texture("Coin_leicht_gedreht_gespiegelt_15x15.png"));
		}
		else if (zeiteinheit == 240) {
			sprite = new Sprite(new Texture("Coin_gerade_15x15.png"));
			zeiteinheit = 0;
		}
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
	}
	
	public void destroy() {
		// muss ich noch machen!!!!!!
		//playScreen.forgetAfterUpdate(this);
		Survival_mit_Geld.forgetAfterUpdate(this);
	}

}

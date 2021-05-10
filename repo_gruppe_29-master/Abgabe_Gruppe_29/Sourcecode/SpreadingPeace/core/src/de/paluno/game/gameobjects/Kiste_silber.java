package de.paluno.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.screens.Gamemode;
import de.paluno.game.screens.Schatzkammer;

public class Kiste_silber extends Kiste{
	int preis = 2;
	int zeiteinheit = 0;
	
	public Kiste_silber(Gamemode gamemode, Vector2 spawnPoint) {
		super(gamemode, spawnPoint);
		// TODO Auto-generated constructor stub
	}
	
	public void update(float delta) {
		if (geschlossen == false) {
			sprite = new Sprite(new Texture("Kiste_silber_geoeffnet_150x100.png"));
			sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2 + 20);
			if (Schatzkammer.getSilber() == 0) {
				itemsprite = new Sprite(new Texture ("Nietelootanzeige_30x30.png"));
			}
			else if (Schatzkammer.getSilber() == 1) {
				itemsprite = new Sprite(new Texture ("Giftlootanzeige_30x30.png"));
			}
			else if (Schatzkammer.getSilber() == 2) {
				itemsprite = new Sprite(new Texture ("Nitrolootanzeige_30x30.png"));
			}
			else if (Schatzkammer.getSilber() == 3) {
				itemsprite = new Sprite(new Texture ("Healthlootanzeige_30x30.png"));
			}
			zeiteinheit++;
						
			if (zeiteinheit == 150) {
				sprite = new Sprite(new Texture("Kiste_silber_geschlossen_150x100.png"));
				zeiteinheit = 0;
				this.setGeschlossen(true);
			}
		}
		
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2 + 20);
		itemsprite.setPosition(100, 280);
	}
	
	public void render(SpriteBatch batch, float delta) {
		sprite.draw(batch);
		if (geschlossen == false) {
			itemsprite.draw(batch);
		}
	}
	
	protected void setupSprites(Vector2 spawnPoint) {
		// TODO Auto-generated method stub
		sprite = new Sprite(new Texture("Kiste_silber_geschlossen_150x100.png"));
		//		sprite.setPosition(spawnPoint.x, spawnPoint.y);
		itemsprite = new Sprite(new Texture ("Flagge_blau_40x80.png"));
		itemsprite.setPosition(100, 280);
	}
	
	
	public void zufallsloot (int zufallzahl) {
		if (zufallzahl == 0) {
			System.out.println("silberne Niete");
			Schatzkammer.setSilber(0);
		}
		else if (zufallzahl > 0 && zufallzahl <= 4) {
			System.out.println("silbernes Giftkit");
			Schatzkammer.setSilber(1);
		}
		else if (zufallzahl > 4 && zufallzahl <= 7) {
			System.out.println("silbernes Nitrokit");
			Schatzkammer.setSilber(2);
		}
		else if (zufallzahl > 7) {
			System.out.println("silbernes Healthkit");
			Schatzkammer.setSilber(3);
		}
	}
	
	public void testausgabe() {
		System.out.println("Kiste silber");
	}
	
	public void Muenzenzahlen() {
		System.out.println("Preis: " + this.getPreis());
		System.out.println("Boolean : " + this.getGeschlossen());
		System.out.println("Schatzkammerm�nzen: " + Schatzkammer.getMuenzen());
		int x = Schatzkammer.getMuenzen() - this.getPreis();
		System.out.println("Schatzkammerm�nzen - Preis: " + x);
		if (Schatzkammer.getMuenzen() - getPreis() >= 0 && getGeschlossen()==true) {
			this.setGeschlossen(false);
			Schatzkammer.setMuenzen(Schatzkammer.getMuenzen() - getPreis());
			zufallsziehen();
		}
	}
	
	protected int getPreis() {
		return this.preis;
	}
	
}

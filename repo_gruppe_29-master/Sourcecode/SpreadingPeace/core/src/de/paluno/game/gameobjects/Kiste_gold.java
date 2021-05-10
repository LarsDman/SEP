package de.paluno.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.screens.Gamemode;
import de.paluno.game.screens.Schatzkammer;

public class Kiste_gold extends Kiste{
	int zeiteinheit = 0;
	int preis = 5;
	public Kiste_gold(Gamemode gamemode, Vector2 spawnPoint) {
		super(gamemode, spawnPoint);
		// TODO Auto-generated constructor stub
	}

	protected void setupSprites(Vector2 spawnPoint) {
		// TODO Auto-generated method stub
		sprite = new Sprite(new Texture("Kiste_gold_geschlossen_150x100.png"));
		itemsprite = new Sprite(new Texture ("Flagge_blau_40x80.png"));
		itemsprite.setPosition(500, 280);
//		sprite.setPosition(spawnPoint.x, spawnPoint.y);
	}
	
	public void update(float delta) {
		if (geschlossen == false) {
			sprite = new Sprite(new Texture("Kiste_gold_geoeffnet_150x100.png"));
			sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2 + 20);
			
			if (Schatzkammer.getGold()==Blumenauswahl.BOUNCEFLOWER) {
				itemsprite = new Sprite(new Texture ("Auswahl_Springende_Blume.png"));
			}
			else if (Schatzkammer.getGold()==Blumenauswahl.FASTFLOWER) {
				itemsprite = new Sprite(new Texture ("Auswahl_schnelle_Blume.png"));
			}
			else if (Schatzkammer.getGold()==Blumenauswahl.NORMALFLOWER) {
				itemsprite = new Sprite(new Texture ("Auswahl_Normale_Blume.png"));
			}
			else if (Schatzkammer.getGold()==Blumenauswahl.SPLITTEDFLOWER) {
				itemsprite = new Sprite(new Texture ("Auswahl_Geteilte_Blume.png"));
			}
			
			zeiteinheit++;
			
			if (zeiteinheit == 150) {
				sprite = new Sprite(new Texture("Kiste_gold_geschlossen_150x100.png"));
				zeiteinheit = 0;
				this.setGeschlossen(true);
			}
		}
		
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2 + 20);
		itemsprite.setPosition(500, 280);
	}
	
	public void render(SpriteBatch batch, float delta) {
		sprite.draw(batch);
		if (geschlossen == false) {
			itemsprite.draw(batch);
		}
	}
	
	public void zufallsloot (int zufallzahl) {
		System.out.println("goldene Kisten Zufallzahl = " + zufallzahl);
		
		if (zufallzahl == 0) {
			System.out.println("goldene Niete");
			Schatzkammer.setGold(Blumenauswahl.NORMALFLOWER);
		}
		else if (zufallzahl > 0 && zufallzahl <= 4) {
			System.out.println("goldenes Munition 1");
			Schatzkammer.setGold(Blumenauswahl.FASTFLOWER);
		}
		else if (zufallzahl > 4 && zufallzahl <= 7) {
			System.out.println("goldenes Munition 2");
			Schatzkammer.setGold(Blumenauswahl.BOUNCEFLOWER);
		}
		else if (zufallzahl > 7) {
			System.out.println("goldenes Munition 3");
			Schatzkammer.setGold(Blumenauswahl.SPLITTEDFLOWER);
		}
	}
	
	public void Muenzenzahlen() {
		System.out.println("Preis: " + this.getPreis());
		if (Schatzkammer.getMuenzen() - getPreis() >= 0 && getGeschlossen()==true) {
			setGeschlossen(false);
			Schatzkammer.setMuenzen(Schatzkammer.getMuenzen() - getPreis());
			zufallsziehen();
		}
	}
	
	protected int getPreis() {
		return this.preis;
	}
	
	
	
	
}

package de.paluno.game.gameobjects;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.screens.Gamemode;
import de.paluno.game.screens.Schatzkammer;

public class Kiste extends Rechteckiges_Item {
	protected Sprite itemsprite;
	protected boolean geschlossen = true;
	int preis = 0;

	static int zufallszahl;

	public Kiste(Gamemode gamemode, Vector2 spawnPoint) {
		super(gamemode, spawnPoint);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setupSprites(Vector2 spawnPoint) {
		// TODO Auto-generated method stub
	}
	
	public void zufallsziehen() {
		Random random = new Random();
		zufallszahl = random.nextInt(9);
		zufallsloot(zufallszahl);
	}

	public void zufallsloot(int zugzahl) {
	}

	protected boolean getGeschlossen() {
		return geschlossen;
	}

	public void setGeschlossen(boolean status) {
		geschlossen = status;
	}

	protected int getPreis() {
		return 0;
	}

	public void Muenzenzahlen() {
	}

	public void testausgabe() {
		System.out.println("Kiste");
	}

}

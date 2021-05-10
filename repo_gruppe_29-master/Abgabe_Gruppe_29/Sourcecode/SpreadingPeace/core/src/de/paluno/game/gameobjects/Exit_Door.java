package de.paluno.game.gameobjects;

import java.util.Vector;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import de.paluno.game.screens.Gamemode;
import de.paluno.game.screens.Schatzkammer;

public class Exit_Door extends Rechteckiges_Item{
//	Sprite DekospriteA = new Sprite (new Texture("Dekom�nzen.png"));
//	Sprite DekospriteB = new Sprite (new Texture("Dekom�nzen.png"));
	Sprite DekospriteA;
	Sprite DekospriteB;
	Sprite DekospriteC;
	Sprite DekospriteD;
	Sprite DekospriteE;
	
	public Exit_Door(Gamemode gamemode, Vector2 spawnPoint) {
		super(gamemode, spawnPoint);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setupSprites(Vector2 spawnPoint) {
		// TODO Auto-generated method stub
		sprite = new Sprite(new Texture("Exit_150x250.png"));
		DekospriteA = new Sprite (new Texture("Dekomuenzen.png"));
		DekospriteB = new Sprite (new Texture("Dekomuenzen.png"));
		DekospriteC = new Sprite (new Texture("Dekomuenzen.png"));
		DekospriteD = new Sprite (new Texture("Dekomuenzen.png"));
		DekospriteE = new Sprite (new Texture("Dekomuenzen.png"));
	}
	
	public void update(float delta) {
//		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
		sprite.setPosition(500, 350);
		DekospriteA.setPosition(170,300);
		DekospriteB.setPosition(250,320);
		DekospriteC.setPosition(570,100);
		DekospriteD.setPosition(700,300);
		DekospriteE.setPosition(780,400);
	}
	
	public void render(SpriteBatch batch, float delta) {
		sprite.draw(batch);
		DekospriteA.draw(batch);
		DekospriteB.draw(batch);
		DekospriteC.draw(batch);
		DekospriteD.draw(batch);
		DekospriteE.draw(batch);
	}
	
	public void start_Mode() {
//	 	hier soll "Survival_mit_Geld" gestartet werden!!
		System.out.println("start_Mode");
//		game.setScreen(new Survival_mit_Geld(hier den Konstruktor));
		
	}
}

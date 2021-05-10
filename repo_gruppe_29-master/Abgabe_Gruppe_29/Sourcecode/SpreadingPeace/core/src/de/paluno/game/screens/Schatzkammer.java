package de.paluno.game.screens;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import de.paluno.game.SEPgame;
import de.paluno.game.gameobjects.Blumenauswahl;
import de.paluno.game.gameobjects.Coin;
import de.paluno.game.gameobjects.Colour;
import de.paluno.game.gameobjects.Exit_Door;
import de.paluno.game.gameobjects.Kiste_gold;
import de.paluno.game.gameobjects.Kiste_silber;
import de.paluno.game.gameobjects.Tank;

public class Schatzkammer extends Survival{
	static int silber = 0;
	static Blumenauswahl gold = Blumenauswahl.NORMALFLOWER;
	static int muenzen = 0;
	private Kiste_silber silberKiste;
	private Kiste_gold goldKiste;
	private Exit_Door exit;
	private Coin coin;
	public static ArrayList<String> settingsx = new ArrayList<String>();
	public static ArrayList<String> settingsy = new ArrayList<String>();;
	
	public Schatzkammer(SEPgame game, String inputMethod, ArrayList<String> settings, ArrayList<String> settings2, String map) {
		super(game, inputMethod, settings, settings2, map);
		settingsx = settings;
		settingsy = settings2;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void start() {
		hud = new Hud_Schatzkammer(game.batch, this, game);
		spawnTanks();
//		spawnItems(true, true, false, true, false);
		buildTilemapBodies(tiledMap, world, "CollisionObjects", 0.001f);
		buildTilemapBodies(tiledMap, world, "WasserObjects", 0.001f);
		buildTilemapBodies(tiledMap, world, "TreeObjects", 0.001f);
		this.addTank(1, playerTank);
	}

	
	public void spawnTanks() {
		muenzen = Survival_mit_Geld.getTaler();
		System.out.println("Muenzen am Start von Schatzkammer = " + muenzen);
		
		Colour player1 = MenuScreen.colours.get(0);
		
		if (inputMethod == "keyboard") {
			playerTank = new Tank(this, spawnPointTank, inputKeyboard, 1, settings, player1, inputMethod);
			System.out.println("Keyboard ausgewaehlt");
		} else if (inputMethod == "gamepad") {
			playerTank = new Tank(this, spawnPointTank, inputGamepad, 1, settings, player1, inputMethod);
			System.out.println("Gamepad ausgewaehlt");
		} else if (inputMethod == "maus") {
			playerTank = new Tank(this, spawnPointTank, inputMouse, 1, settings, player1, inputMethod);
			System.out.println("Maus ausgewaehlt");
		}
		Vector2 silberKisteSpawn = new Vector2(100,200);
		Vector2 goldKisteSpawn = new Vector2(500,200);
		Vector2 exitSpawn = new Vector2(560,400);
		Vector2 coinSpawn = new Vector2(700,300);
		
		silberKiste = new Kiste_silber(this, silberKisteSpawn);
		goldKiste = new Kiste_gold(this, goldKisteSpawn);
		exit = new Exit_Door(this, exitSpawn);
//		coin = new Coin(this,coinSpawn);
		
		registerAfterUpdate(playerTank);
		registerAfterUpdate(silberKiste);
		registerAfterUpdate(goldKiste);
		registerAfterUpdate(exit);
//		registerAfterUpdate(coin);
		
		System.out.println("istSurvival_mit_Geld: " + istSurvival_mit_Geld);
	}	
	
	protected void spawnItems() {
		System.out.println("SCHATZKAMMMMMMEEEEEEEEEEEEEEEEER");
//		Vector2 spawnPointPoison = new Vector2(200,100);
//		Vector2 spawnPointPower = new Vector2(200, 200);
//		Vector2 spawnPointVelocity = new Vector2(200, 300);
//		
//		power = new Power(this, spawnPointPower);
//		poison = new Poison(this, spawnPointPoison );
//		velocity = new Velocity(this, spawnPointVelocity);
//		
//		registerAfterUpdate(power);
//		registerAfterUpdate(poison);
//		registerAfterUpdate(velocity);
	}
	
	public static void Screenwechsel() {
		game.setScreen(new Survival_mit_Geld(game, inputMethod,  settingsx,  settingsy, "TiledMap32.tmx"));
	}
	
	public static int getSilber() {
		return silber;
	}
	public static void setSilber(int x) {
		silber = x;
	}
	public static Blumenauswahl getGold() {
		return gold;
	}
	public static void setGold(Blumenauswahl x) {
		gold = x;
	}
	public static int getMuenzen () {
		return muenzen;
	}
	public static void setMuenzen(int x) {
		muenzen = x;
	}
	protected void checkItemLife() {
// 	Methode muss leer bleiben!!!!!
//	sonst error!
	}
}

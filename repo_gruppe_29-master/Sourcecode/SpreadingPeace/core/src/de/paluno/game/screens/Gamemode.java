package de.paluno.game.screens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import de.paluno.game.CollisionHandler;
import de.paluno.game.Constants;
import de.paluno.game.GameState;
import de.paluno.game.SEPgame;
import de.paluno.game.gameobjects.Blumenauswahl;
import de.paluno.game.gameobjects.PhysicsObject;
import de.paluno.game.gameobjects.Poison;
import de.paluno.game.gameobjects.Power;
import de.paluno.game.gameobjects.Renderable;
import de.paluno.game.gameobjects.TacticalNuke;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.gameobjects.Tree;
import de.paluno.game.gameobjects.Trumpitem;
import de.paluno.game.gameobjects.Updateable;
import de.paluno.game.gameobjects.Velocity;
import de.paluno.game.gameobjects.Wall;
import de.paluno.game.gameobjects.Water;
import de.paluno.game.input.GamepadInputProvider;
import de.paluno.game.input.KeyboardInputProvider;
import de.paluno.game.input.KiInputProvider;
import de.paluno.game.input.MouseInputProvider;

public abstract class Gamemode extends com.badlogic.gdx.ScreenAdapter {

	private static final int FFA_width = 30; // FFA = FastFlower Auswahl
	private static final int FFA_height = 30;
	private static final int BFA_width = 30; // BFA = Bounce-Flower Auswahl
	private static final int BFA_height = 30;
	private static final int SFA_width = 30; // SFA = Split-Flower Auswahl
	private static final int SFA_height = 30;
	private static final int NFA_width = 30; // NFA = Normal-Flower Auswahl
	private static final int NFA_height = 30;

	// Zum pausieren/fortfahren, Patrick
	public GameState state;

	protected HashMap<Integer, Tank> tanks;
	
	protected Box2DDebugRenderer debugRenderer;
	public static SEPgame game;
	protected static Collection<Object> gameObjectsToForgetAfterUpdate;
	protected Collection<Object> gameObjectsToRegisterAfterUpdate;
	protected Collection<Renderable> renderableObjects;
	protected Collection<Updateable> updatableObjects;
	public static World world;
	public OrthographicCamera camera;
	protected float accumulator;
	protected static float width = Constants.BILDSCHIRMBREITE;
	protected static float height = Constants.BILDSCHIRMHOEHE;
	protected static float tileSize = Constants.TILESIZE;
	public SpriteBatch spriteBatch;
	protected static String inputMethod;
	KeyboardInputProvider inputKeyboard = new KeyboardInputProvider();
	MouseInputProvider inputMouse = new MouseInputProvider();
	GamepadInputProvider inputGamepad = new GamepadInputProvider();
	KiInputProvider inputKi = new KiInputProvider();
	protected ArrayList<String> settings = new ArrayList<String>();
	protected ArrayList<String> settings2 = new ArrayList<String>();
	public int score = 0;
	Texture nfa = new Texture("Auswahl_Normale_Blume.png");
	Texture ffa = new Texture("Auswahl_schnelle_Blume.png");
	Texture bfa = new Texture("Auswahl_Springende_Blume.png");
	Texture sfa = new Texture("Auswahl_Geteilte_Blume.png");
	Texture AuswahlRahmen = new Texture("Auswahlrahmen.png");
	static boolean istSurvival_mit_Geld = false;

	protected Power power;
	protected TacticalNuke nuke;
	protected Velocity velocity;
	protected Poison poison;
	protected Trumpitem trump;
	private long spawnTimeItems;
	private int spawnTime;
	//TiledMap
	protected TiledMap tiledMap;
	protected OrthogonalTiledMapRenderer tiledMapRenderer;
	protected int[] hintergrundlayers;
	protected TiledMapTileLayer vordergrundlayer;
	protected String map;
	
	protected Hud hud;
	protected ArrayList<String> killedTankList, killerTankList;
	
	static ArrayList<Fixture> ObjektFix = new ArrayList<Fixture>();

	public Gamemode(SEPgame game, String inputMethod, ArrayList<String> settings, ArrayList<String> settings2, String map) {

		this.tanks = new HashMap<Integer, Tank>();
		this.game = game;
		this.state = GameState.RUNNING; // Zum pausieren/fortfahren, Patrick
		this.inputMethod = inputMethod;
		this.settings = settings;
		this.settings2 = settings2;
		this.map = map;
		Gamemode.world = new World(new Vector2(0, 0), true);

		setKilledTankList(new ArrayList<String>());
		setKillerTankList(new ArrayList<String>());

		spawnTimeItems = System.currentTimeMillis();
		spawnTime = 7000;
	}
	
	public abstract void stopSounds();

	public void show() {
		try {
		world.setContactListener(new CollisionHandler(this));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
		camera.update();
		spriteBatch = game.batch;
		
		//TiledMap
		tiledMap = new TmxMapLoader().load(map);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		//Alles, was hinter dem Spieler gerendert werden soll
		hintergrundlayers = new int[] {
				tiledMap.getLayers().getIndex("Wasser"),
				tiledMap.getLayers().getIndex("Hintergrund"),
				tiledMap.getLayers().getIndex("Andere"),
				tiledMap.getLayers().getIndex("Collision")
		};
		//Alles, was vor dem Spieler gerendert werden soll
		vordergrundlayer = (TiledMapTileLayer) tiledMap.getLayers().get("Vor");
		
		gameObjectsToRegisterAfterUpdate = new HashSet<Object>(); // HashSet verhindert, dass Objekte doppelt vorkommen
		gameObjectsToForgetAfterUpdate = new HashSet<Object>();
		renderableObjects = new LinkedList<Renderable>();
		updatableObjects = new LinkedList<Updateable>();
		start();
		}
		catch(Exception e)
		{	
			this.game.setScreen(new ErrorScreen(game));
		}
	}
	
	public abstract void start();
	
	
	public void buildTilemapBodies(TiledMap tiledMap, World world, String layer, float density) {
		short group = 0;
		
		if (layer == "CollisionObjects") {
			group = 1;
		} else if (layer == "WasserObjects") {
			group = -2;
		} else if (layer == "TreeObjects") {
			group = 3;
		}

		MapObjects objects = tiledMap.getLayers().get(layer).getObjects();

		for (MapObject object : objects) {
			
			Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
			
			// Die Position fuer den Ursprung des Koerpers einstellen. Ohne Rotation
			Vector2 spawnPoint = new Vector2();
			rectangle.getCenter(spawnPoint);
			
			// Die Shape des Koerpers erstellen
			Shape shape = getShapeFromRectangle(rectangle);
			
			// FixtureDef erstellen
			FixtureDef fixtureDef = getFixtureDef(rectangle, density, group);

			if (layer == "TreeObjects") {
				Tree tree = new Tree(this, spawnPoint, shape, fixtureDef, 5F);
				registerAfterUpdate(tree);
			} else if (layer == "CollisionObjects") {
				Wall wall = new Wall(this, spawnPoint, shape, fixtureDef, rectangle);
				registerAfterUpdate(wall);
				ObjektFix.add(wall.getBody().getFixtureList().first()); //Cedric
			} else if (layer == "WasserObjects") {
				Water water = new Water(this, spawnPoint, shape, fixtureDef, rectangle);
				registerAfterUpdate(water);
			} else {
				return;
			}
		}
	}
	
	public static FixtureDef getFixtureDef(Rectangle rectangle, float density, short group) {
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = getShapeFromRectangle(rectangle);
		fixtureDef.density = density;
		fixtureDef.filter.groupIndex = group;
		return fixtureDef;
	}
	
	public static Shape getShapeFromRectangle(Rectangle rectangle) {
		
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(rectangle.width * 0.5F, rectangle.height * 0.5F);
		return polygonShape;
	}
	
	public void tacticalNuke(){ //patrick
		
		for(Map.Entry<Integer, Tank> currentopfer : this.tanks.entrySet()) {
			if (currentopfer.getValue() != null&&currentopfer.getValue().getHealth()>0) {
				currentopfer.getValue().setHealth(currentopfer.getValue().getHealth()/2);				
			}
		}
		this.tacticalNuke2();
	}
	
	public abstract void tacticalNuke2(); //patrick

	public void gameState(float delta) {// wird an Spielmodi vererbt und dort in render aufgerufen
		switch (state) {
		case RUNNING:
			physicsPhase(delta);
			updatePhase(Gdx.graphics.getDeltaTime());
			updatePhase2(Gdx.graphics.getDeltaTime());

			break;
		case PAUSED:
			updatePhase(0);
			updatePhase2(0);
			physicsPhase(0);
			break;
		case RESUMED:
			break;
		case STOPPED:
			break;
		default:
			break;
		}
	}

	public void render(float delta) {
		try {
		this.gameState(delta);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render(hintergrundlayers);
		renderPhase(delta);
		tiledMapRenderer.getBatch().begin();
		tiledMapRenderer.renderTileLayer(vordergrundlayer);
		tiledMapRenderer.getBatch().end();
		spriteBatch.setProjectionMatrix(camera.combined);
		hud.stage.draw();
		hud.stage.act();

		}
		catch(Exception e)
		{	
			game.setScreen(new ErrorScreen(game));
		}
		
	}

	public void setGame(SEPgame game) {
		this.game = game;
	}

	public void resize(int width, int height) {
		// camera.setToOrtho(false, width, height);
	}

	public int getScore() {
		return score;
	}


	public void physicsPhase(float delta) {
		float frameTime = Math.min(delta, 0.25f);
		accumulator += frameTime;
		while (accumulator >= Constants.PHYSICSSTEPLENGTH) {
			world.step(Constants.PHYSICSSTEPLENGTH, 1, 1);
			accumulator -= Constants.PHYSICSSTEPLENGTH;
		}
	}
	

	public void renderPhase(float delta) {
		spriteBatch.begin();
		for (Iterator<Renderable> i = renderableObjects.iterator(); i.hasNext();) {
			Renderable renderable = (Renderable) i.next();
			renderable.render(spriteBatch, delta);
		}
		
//		if (MenuScreen.survivalMode) {
//			this.renderSelectionPlayer1(Survival.getSpielerTank());
//		}
		checkRenderSelection();

		cameraUpdate(delta);
		spriteBatch.end();
//		debugRenderer.render(world, camera.combined); //hitboxen
		
	}
	
	private void checkRenderSelection() {
		for(Map.Entry<Integer, Tank> e : this.tanks.entrySet()) {
			
			if (e.getValue() != null) {
				switch (e.getKey().intValue()) {
					case 1:
						this.renderSelectionPlayer1(e.getValue());
						break;
						
					case 2:
						this.renderSelectionPlayer2(e.getValue());
						break;	
				}
			}
		}
	}

	private void renderSelectionPlayer2(Tank tank) {
		Blumenauswahl BlumenauswahlP2 = tank.getFlower();
		
		// spicial-Flowers-AuswahlMenu-Icons Position
		float nfaxP2 = Constants.BILDSCHIRMBREITE / 2 - NFA_width / 2 + 280;
		float nfayP2 = Constants.BILDSCHIRMHOEHE / 2 - NFA_height / 2 - 250;
		float ffaxP2 = Constants.BILDSCHIRMBREITE / 2 - FFA_width / 2 + 330;
		float ffayP2 = Constants.BILDSCHIRMHOEHE / 2 - FFA_height / 2 - 250;
		float bfaxP2 = Constants.BILDSCHIRMBREITE / 2 - BFA_width / 2 + 380;
		float bfayP2 = Constants.BILDSCHIRMHOEHE / 2 - BFA_height / 2 - 250;
		float sfaxP2 = Constants.BILDSCHIRMBREITE / 2 - SFA_width / 2 + 430;
		float sfayP2 = Constants.BILDSCHIRMHOEHE / 2 - SFA_height / 2 - 250;

		// spicial-Flowers-AuswahlMenu inkl. Auswahlrahmen zeichnen
		if (BlumenauswahlP2 == Blumenauswahl.SPLITTEDFLOWER) {
			spriteBatch.draw(AuswahlRahmen, Constants.BILDSCHIRMBREITE / 2 - SFA_width / 2 + 427,
					Constants.BILDSCHIRMHOEHE / 2 - SFA_height / 2 - 253);
		}
		if (BlumenauswahlP2 == Blumenauswahl.BOUNCEFLOWER) {
			spriteBatch.draw(AuswahlRahmen, Constants.BILDSCHIRMBREITE / 2 - BFA_width / 2 + 377,
					Constants.BILDSCHIRMHOEHE / 2 - BFA_height / 2 - 253);
		}
		if (BlumenauswahlP2 == Blumenauswahl.FASTFLOWER) {
			spriteBatch.draw(AuswahlRahmen, Constants.BILDSCHIRMBREITE / 2 - FFA_width / 2 + 327,
					Constants.BILDSCHIRMHOEHE / 2 - FFA_height / 2 - 253);
		}
		if (BlumenauswahlP2 == Blumenauswahl.NORMALFLOWER) {
			spriteBatch.draw(AuswahlRahmen, Constants.BILDSCHIRMBREITE / 2 - NFA_width / 2 + 277,
					Constants.BILDSCHIRMHOEHE / 2 - NFA_height / 2 - 253);
		}

		spriteBatch.draw(nfa, nfaxP2, nfayP2);
		spriteBatch.draw(ffa, ffaxP2, ffayP2);
		spriteBatch.draw(bfa, bfaxP2, bfayP2);
		spriteBatch.draw(sfa, sfaxP2, sfayP2);
	}

	private void renderSelectionPlayer1(Tank tank) {
		Blumenauswahl BlumenauswahlP1 = tank.getFlower();
		
		// spicial-Flowers-AuswahlMenu-Icons Position
		float nfaxP1 = Constants.BILDSCHIRMBREITE / 2 - NFA_width / 2 - 430;
		float nfayP1 = Constants.BILDSCHIRMHOEHE / 2 - NFA_height / 2 - 250;
		float ffaxP1 = Constants.BILDSCHIRMBREITE / 2 - FFA_width / 2 - 380;
		float ffayP1 = Constants.BILDSCHIRMHOEHE / 2 - FFA_height / 2 - 250;
		float bfaxP1 = Constants.BILDSCHIRMBREITE / 2 - BFA_width / 2 - 330;
		float bfayP1 = Constants.BILDSCHIRMHOEHE / 2 - BFA_height / 2 - 250;
		float sfaxP1 = Constants.BILDSCHIRMBREITE / 2 - SFA_width / 2 - 280;
		float sfayP1 = Constants.BILDSCHIRMHOEHE / 2 - SFA_height / 2 - 250;
		
		// special-Flowers-AuswahlMenu inkl. Auswahlrahmen zeichnen
		if (BlumenauswahlP1 == Blumenauswahl.SPLITTEDFLOWER) {
			spriteBatch.draw(AuswahlRahmen, Constants.BILDSCHIRMBREITE / 2 - SFA_width / 2 - 283,
					Constants.BILDSCHIRMHOEHE / 2 - SFA_height / 2 - 253);
		}
		if (BlumenauswahlP1 == Blumenauswahl.BOUNCEFLOWER) {
			spriteBatch.draw(AuswahlRahmen, Constants.BILDSCHIRMBREITE / 2 - BFA_width / 2 - 333,
					Constants.BILDSCHIRMHOEHE / 2 - BFA_height / 2 - 253);
		}
		if (BlumenauswahlP1 == Blumenauswahl.FASTFLOWER) {
			spriteBatch.draw(AuswahlRahmen, Constants.BILDSCHIRMBREITE / 2 - FFA_width / 2 - 383,
					Constants.BILDSCHIRMHOEHE / 2 - FFA_height / 2 - 253);
		}
		if (BlumenauswahlP1 == Blumenauswahl.NORMALFLOWER) {
			spriteBatch.draw(AuswahlRahmen, Constants.BILDSCHIRMBREITE / 2 - NFA_width / 2 - 433,
					Constants.BILDSCHIRMHOEHE / 2 - NFA_height / 2 - 253);
		}

		spriteBatch.draw(nfa, nfaxP1, nfayP1);
		spriteBatch.draw(ffa, ffaxP1, ffayP1);
		spriteBatch.draw(bfa, bfaxP1, bfayP1);
		spriteBatch.draw(sfa, sfaxP1, sfayP1);
	}

	public void updatePhase(float delta) {
//		hud.update(delta);
		for (Updateable i : updatableObjects) {
			i.update(delta);
		}
		for (Object j : gameObjectsToRegisterAfterUpdate) {
			register(j);
		}
		for (Object k : gameObjectsToForgetAfterUpdate) {
			forget(k);
		}
		gameObjectsToRegisterAfterUpdate.clear();
		gameObjectsToForgetAfterUpdate.clear();
	}
	
	public void updatePhase2(float delta) {
		
	}

	public void cameraUpdate(float delta) {
//		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
//		{
//			camera.translate(-1, 0);
//		}
//		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
//		{
//			camera.translate(+5, 0);
//		}
//		camera.update(); nur zum debuggen
	}

	public void registerAfterUpdate(Object gameObject) {
		gameObjectsToRegisterAfterUpdate.add(gameObject);
	}

	public void register(Object gameObject) {
		if (gameObject instanceof Updateable) {
			updatableObjects.add((Updateable) gameObject);
		}
		if (gameObject instanceof Renderable) {
			renderableObjects.add((Renderable) gameObject);
		}
	}

	public static void forgetAfterUpdate(Object gameObject) {
		gameObjectsToForgetAfterUpdate.add(gameObject);
	}

	protected void forget(Object gameObject) {

		if (gameObject instanceof Updateable) {
			updatableObjects.remove((Updateable) gameObject);
		}
		if (gameObject instanceof Renderable) {
			renderableObjects.remove((Renderable) gameObject);
		}
		if (gameObject instanceof PhysicsObject) {
			world.destroyBody(((PhysicsObject) gameObject).getBody());
//			((PhysicsObject) gameObject).setBodyToNullReference();
		}
	}

	public static World getWorld() {
		return world;
	}

	public void hide() {
	}

//	public void dispose() {
//		world.dispose();
//		tiledMapRenderer.dispose();
//		game.dispose();
//		stage.dispose();
//		//debugRenderer.dispose();
//	}

	public void Zombiespawner() {

	}

	protected void spawnItems(boolean spawnPower, boolean spawnPoison, boolean spawnNuke, boolean spawnVelocity, boolean spawnTrump) {
		Vector2 spawnPointPoison = new Vector2(200, 100);
		Vector2 spawnPointTrump = new Vector2(300, 100);
		Vector2 spawnPointPower = new Vector2(200, 200);
		Vector2 spawnPointNuke = new Vector2(400, 300);
		Vector2 spawnPointVelocity = new Vector2(200, 300);

		if(spawnTrump)trump = new Trumpitem(this, spawnPointTrump);
		if(spawnPower)power = new Power(this, spawnPointPower);
		if(spawnPoison)poison = new Poison(this, spawnPointPoison);
		if(spawnVelocity)velocity = new Velocity(this, spawnPointVelocity);
		if(spawnNuke)nuke = new TacticalNuke(this, spawnPointNuke);

		if(spawnTrump)registerAfterUpdate(trump);
		if(spawnPower)registerAfterUpdate(power);
		if(spawnPoison)registerAfterUpdate(poison);
		if(spawnNuke)registerAfterUpdate(nuke);
		if(spawnVelocity)registerAfterUpdate(velocity);
	}

	protected void checkItemLife() {
		if (this.state == GameState.RUNNING) {
			if ((long) System.currentTimeMillis() - spawnTime > (long) spawnTimeItems) {
			if(power!=null)	power.destroy();
			if(velocity!=null)	velocity.destroy();
			if(poison!=null)	poison.destroy();
				spawnTimeItems = System.currentTimeMillis();
			}
		}
	}

	public Hud getHud() {
		return hud;
	}

	public void setHud(Hud hud) {
		this.hud = hud;
	}

	public ArrayList<String> getKilledTankList() {
		return killedTankList;
	}

	public void setKilledTankList(ArrayList<String> killedTankList) {
		this.killedTankList = killedTankList;
	}

	public ArrayList<String> getKillerTankList() {
		return killerTankList;
	}

	public void setKillerTankList(ArrayList<String> killerTankList) {
		this.killerTankList = killerTankList;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public static boolean getIstSurvival_mit_Geld() {
		return istSurvival_mit_Geld;
	}

	public static ArrayList<Fixture> getObjektFix() {
		return ObjektFix;
	}
	
	public abstract Tank getSpielerTank();

	public Collection<Updateable> getUpdateable() {
		return updatableObjects;
	}
	
	public ArrayList<String> getSettings2() {
		return settings2;
	}

	public void setSettings2(ArrayList<String> settings2) {
		this.settings2 = settings2;
	}

	public ArrayList<String> getSettings() {
		return settings;
	}

	public void setSettings(ArrayList<String> settings) {
		this.settings = settings;
	}
	
	public void addTank(int playerNum, Tank tank) {
		this.tanks.put(Integer.valueOf(playerNum), tank);
	}
}

package de.paluno.game.screens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import de.paluno.game.CollisionHandler;
import de.paluno.game.Constants;
import de.paluno.game.SEPgame;
import de.paluno.game.gameobjects.PhysicsObject;
import de.paluno.game.gameobjects.Poison;
import de.paluno.game.gameobjects.Power;
import de.paluno.game.gameobjects.Renderable;
import de.paluno.game.gameobjects.TankKi;
import de.paluno.game.gameobjects.Updateable;
import de.paluno.game.gameobjects.Velocity;
import de.paluno.game.input.GamepadInputProvider;
import de.paluno.game.input.KeyboardInputProvider;
import de.paluno.game.input.KiInputProvider;
import de.paluno.game.input.MouseInputProvider;

public abstract class Gamemode extends com.badlogic.gdx.ScreenAdapter {

	protected Box2DDebugRenderer debugRenderer;
	public SEPgame game;
	protected Collection<Object> gameObjectsToForgetAfterUpdate;
	protected Collection<Object> gameObjectsToRegisterAfterUpdate;
	protected Collection<Renderable> renderableObjects;
	protected Collection<Updateable> updatableObjects;
	public static World world; 										
	public OrthographicCamera camera;
	protected float accumulator;
	protected float width = Constants.BILDSCHIRMBREITE; 
	protected float height = Constants.BILDSCHIRMHOEHE;
	public SpriteBatch spriteBatch;
	protected String inputMethod;
	KeyboardInputProvider inputKeyboard = new KeyboardInputProvider();
	MouseInputProvider inputMouse = new MouseInputProvider(); 
	GamepadInputProvider inputGamepad = new GamepadInputProvider();
	KiInputProvider inputKi = new KiInputProvider();
	ArrayList<String> settings = new ArrayList<String>();
	ArrayList<String> settings2 = new ArrayList<String>();
	public TankKi zombie1;
	public TankKi zombie2;
	public int score = 0;
	
	private Power power;
	private Velocity velocity;
	private Poison poison;
	private long spawnTimeItems;
	private int spawnTime;
	
	public Gamemode(SEPgame game, java.lang.String inputMethod, ArrayList<String> settings, ArrayList<String> settings2) {

		this.game = game;
		this.inputMethod = inputMethod;
		this.settings = settings;
		this.settings2 = settings2;
		Gamemode.world = new World(new Vector2(0, 0), true);
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("death-metal-loop.wav"));
		sound.loop(0.1f, 1, 0);
		
		spawnTimeItems = System.currentTimeMillis();
		spawnTime = 7000;
	}

	
	public void show() {

		float w = width/2;
		float h = height/2;		

		world.setContactListener(new CollisionHandler(this));
		//debugRenderer = new Box2DDebugRenderer(); //zum ansehen der hitboxen
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		spriteBatch = game.batch;
		gameObjectsToRegisterAfterUpdate = new HashSet<Object>(); // HashSet verhindert, dass Objekte doppelt vorkommen
		gameObjectsToForgetAfterUpdate = new HashSet<Object>();
		renderableObjects = new LinkedList<Renderable>();
		updatableObjects = new LinkedList<Updateable>();
		start();
	} 
	
	
	public abstract void start();
	
	
	public void render(float delta) {
		
		updatePhase(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderPhase(delta);
		//debugRenderer.render(world, camera.combined); //zum ansehen der hitboxen
		spriteBatch.setProjectionMatrix(camera.combined);
		physicsPhase(delta);
	}
	
//	public static SEPgame getGame() {
//		return game;
//	}


	public void setGame(SEPgame game) {
		this.game = game;
	}


	public void resize(int width, int height) {
		
//		camera.setToOrtho(false, width/2, height/2);
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
		cameraUpdate(delta);
		spriteBatch.end();
	}

	
	public void updatePhase(float delta) {
		
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

	
	public void forgetAfterUpdate(Object gameObject) {
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
			((PhysicsObject) gameObject).setBodyToNullReference();
		}
	}

	public static World getWorld() {
		return world;
	}
	
	
	public void hide() {

	}
	
	
	public void dispose() {
		
		world.dispose();
		debugRenderer.dispose();
	}


	public void Zombiespawner() {

	}
	
	protected void spawnItems() {
		Vector2 spawnPointPoison = new Vector2(200,100);
		Vector2 spawnPointPower = new Vector2(200, 200);
		Vector2 spawnPointVelocity = new Vector2(200, 300);
		
		power = new Power(this, spawnPointPower);
		poison = new Poison(this, spawnPointPoison );
		velocity = new Velocity(this, spawnPointVelocity);
		
		registerAfterUpdate(power);
		registerAfterUpdate(poison);
		registerAfterUpdate(velocity);
	}
	
	 protected void checkItemLife() {
	    	if((long)System.currentTimeMillis() - spawnTime > (long)spawnTimeItems) {
	    		power.destroy();
	    		velocity.destroy();
	    		poison.destroy();
	    		spawnTimeItems = System.currentTimeMillis();
	    	}
	    }
}

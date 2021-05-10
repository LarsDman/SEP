package de.paluno.game.screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import de.paluno.game.CollisionHandler;
import de.paluno.game.Constants;
import de.paluno.game.SEPgame;
import de.paluno.game.gameobjects.F_L_O_W_E_R;
import de.paluno.game.gameobjects.Renderable;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.gameobjects.Target;
import de.paluno.game.gameobjects.Updatable;
import de.paluno.game.input.GamepadInputProvider;
import de.paluno.game.input.KeyboardInputProvider;
import de.paluno.game.input.MouseInputProvider;

//This screen should handle the game´s main game loop. Only one instance of it should exist at any time. This instance should be created by the MenuScreen, when the player has selected his desired input method and wishes to start the game.

public class PlayScreen extends com.badlogic.gdx.ScreenAdapter implements com.badlogic.gdx.Screen, ControllerListener {

	Random random = new Random();
	int zielscheibenPositionNeuX = random.nextInt(700); // zufällige X-Koordinate für den Spawn der Zielscheibe im
														// Poxelbereich bis 700
	int zielscheibenPositionNeuY = random.nextInt(450); // zufällige Y-Koordinate für den Spawn der Zielscheibe im
														// Poxelbereich bis 450

	private com.badlogic.gdx.graphics.g2d.SpriteBatch batch;
	private com.badlogic.gdx.physics.box2d.Box2DDebugRenderer debugRenderer;
	private SEPgame game;
	private java.util.Collection<java.lang.Object> gameObjectsToForgetAfterUpdate;
	private java.util.Collection<java.lang.Object> gameObjectsToRegisterAfterUpdate;
	private java.lang.String inputMethod;
	private java.util.Collection<Renderable> renderableObjects;
	private float timeToSimulate;
	private float accumulator;
	ArrayList<F_L_O_W_E_R> flowers = new ArrayList<F_L_O_W_E_R>();
	ArrayList<Target> targets = new ArrayList<Target>();
	private java.util.Collection<Updatable> updatableObjects;
	public static com.badlogic.gdx.physics.box2d.World world;
	private OrthographicCamera camera;
	private Box2DDebugRenderer b2dr;
	private float width = Constants.BILDSCHIRMBREITE;
	private float height = Constants.BILDSCHIRMHOEHE;
	public SpriteBatch spriteBatch;

	private Tank tank;
	private F_L_O_W_E_R myFlower;
	private Target target;

	private Vector2 movement = new Vector2();
	Vector2 spawnPointZielscheibe = new Vector2(zielscheibenPositionNeuX, zielscheibenPositionNeuY);
	Vector2 spawnPointTank = new Vector2(100, 100);
	KeyboardInputProvider inputKeyboard = new KeyboardInputProvider();

	MouseInputProvider inputMouse = new MouseInputProvider();
	GamepadInputProvider inputGamepad = new GamepadInputProvider();
	PovDirection currentDirection = PovDirection.center;
	boolean isApressed = false;

	public PlayScreen(SEPgame game, java.lang.String inputMethod) {
		// Instantiates this class and should set the parameters to their respective
		// attributes.
		this.game = game;
		this.inputMethod = inputMethod;
		PlayScreen.world = new World(new Vector2(0, 0), true);
		Controllers.addListener(this);

	}

	public void show() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		world.setContactListener(new CollisionHandler(this));
		debugRenderer = new Box2DDebugRenderer();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w / 2, h / 2);
		b2dr = new Box2DDebugRenderer();
		spriteBatch = game.batch;

		// Steuerung
		steuerung();

		target = new Target(this, spawnPointZielscheibe, inputKeyboard);
		targets.add(target);
	}

	public void steuerung() {
		if (inputMethod == "keyboard") {
			tank = new Tank(this, spawnPointTank, inputKeyboard);
			System.out.println("Keyboard ausgewählt"); // zum Testen der Abfrage
		} else if (inputMethod == "gamepad") {
			tank = new Tank(this, spawnPointTank, inputGamepad);
			System.out.println("Gamepad ausgewählt");
		} else if (inputMethod == "maus") {
			tank = new Tank(this, spawnPointTank, inputMouse);
			System.out.println("Maus ausgewählt");
		}
	}

	public void render(float delta) {

		updatePhase(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		debugRenderer.render(world, camera.combined);

		tank.getBody().applyForceToCenter(movement, true);
		renderPhase(delta);

		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		if (inputMethod == "keyboard") {
			inputKeyboard.getInputs(tank, this); // zum Testen der Abfrage
		} else if (inputMethod == "gamepad") {
			inputGamepad.getInputs(tank, this);
		} else if (inputMethod == "maus") {
			inputMouse.getInputs(tank, this);
			MouseInputProvider.schussZeit += delta;
		}

		int currentButton = 99; // irsinniger wert um fehler zu vermeiden. ControllerListener scheint bereits 0
								// fuer den A Knopf zu benutzen
		
//		int counter = 0;
//		Vector2 direction = new Vector2(10, 10);
//		while (counter < flowers.size()) {
//			F_L_O_W_E_R currentFlower = flowers.get(counter);
//			currentFlower.update(Gdx.graphics.getDeltaTime());
//			currentFlower.render(spriteBatch, delta);
//			counter++;
//		}
		
		for(F_L_O_W_E_R flower : flowers) {
			flower.update(Gdx.graphics.getDeltaTime());
			flower.render(spriteBatch, delta);
		}
		
		tank.update(delta);
		tank.render(spriteBatch, delta);
		target.update(delta);
		target.render(spriteBatch, delta);

		

		spriteBatch.end();
		physicsPhase(delta);

	}

	@Override
	public void resize(int width, int height) {
//		camera.setToOrtho(false, width/2, height/2);
	}

	public void hide() {

	}

	public ArrayList<F_L_O_W_E_R> getFlowerList() {
		return flowers;
	}
	
	public void setFlowerList(ArrayList<F_L_O_W_E_R> flowers) {
		this.flowers = flowers;
	}
	
	public ArrayList<Target> getTargetList() {
		return targets;
	}
	
	public void setTargetList(ArrayList<Target> targets) {
		this.targets = targets;
	}

	public void physicsPhase(float delta) {
//		This method is the render phase of the play screen´s game loop. It should be called on every frame. It iterates over every Renderable object that has been added to the game with the registerAfterUpdate method before and calls their render method. It also needs to manage the game´s camera as well as the game´s SpriteBatch that is used to render the objects.
		float frameTime = Math.min(delta, 0.25f);
		accumulator += frameTime;
		while (accumulator >= Constants.PHYSICSSTEPLENGTH) {
			world.step(Constants.PHYSICSSTEPLENGTH, 1, 1);
			accumulator -= Constants.PHYSICSSTEPLENGTH;
		}
//		delta - The time in seconds between the last frames.
	}

	public void renderPhase(float delta) {
//		spriteBatch.begin();
//		for (F_L_O_W_E_R flower : flowers) {
//			
//			flower.render(spriteBatch,delta);
//		}
//		spriteBatch.end();
//		
	}

	public void updatePhase(float delta) {
		// This method is the update step of the play screen´s game loop. It should be
		// called on every frame. It iterates over every Updatable object that has been
		// added to the game with the registerAfterUpdate method before and calls their
		// update method.

		cameraUpdate(delta);
		// delta - The time in seconds between the last frames.
	}

	public void cameraUpdate(float delta) {
//		Vector3 position = camera.position;
//		position.x =player.getPosition().x*PPM;
//		position.y=player.getPosition().y*PPM;
//		camera.position.set(position);

//		camera.update();
	}

	public void registerAfterUpdate(java.lang.Object gameObject) {
		// This method should be called whenever a new gameobject needs to be registered
		// with the PlayScreen, meaning that it enters the game. The PlayScreen then
		// registers the gameobject after the next update step.

		// gameObject - The new gameobject to add to the game.
	}

	private void register(java.lang.Object gameObject) {
		// This method should be called after the update step for every gameobject that
		// has been given to the PlayScreen with the registerAfterUpdate-method but has
		// not yet actually been registered. Registering the gameobjcect simply means
		// adding to the Collection of Updatable objects, if it is of the type
		// Updatable, and adding it to the Collection of Renderable objects, if it is of
		// the type Renderable.

		// gameObject - The gameobject to register.
	}

	public void forgetAfterUpdate(java.lang.Object gameObject) {
		// This method should be called whenever a gameobject needs to be forgotten by
		// the PlayScreen, meaning that it leaves the game. The PlayScreen then forgets
		// the gameobject after the next update step.

		// gameObject - The game object to remove from the game.
	}

	protected void forget(java.lang.Object gameObject) {
		// This method should be called after the update step for every gameobject that
		// has been indicated to the PlayScreen with the forgetAfterUpdate-method but
		// has not yet actually been forgotten. Forgetting the gameobjcect simply means
		// removing it from the Collection of Updatable objects, if it is of the type
		// Updatable, and removing it from the Collection of Renderable objects, if it
		// is of the type Renderable.

		// gameObject - The gameobject to forget.
	}

	public static com.badlogic.gdx.physics.box2d.World getWorld() {
		return world;

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		world.dispose();
		b2dr.dispose();
	}

	@Override
	public void connected(Controller controller) {
		// wird noch nicht ben�tigt, da hotplugging keine Anforderung

	}

	@Override
	public void disconnected(Controller controller) {
		// wird noch nicht ben�tigt, da hotplugging keine Anforderung

	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		// Gdx.app.log("controller", ""+buttonCode); Gibt in der Konsole den code der
		// buttons aus. beispiel: "[controller] 0" f�r den A Knopf
		if (buttonCode == 0)
			isApressed = true; // returnt true wenn A gedr�ckt wird
		return false; // Sehr billige L�sung, ich weiss
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		currentDirection = value;
		return false;
	}

	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
		// der Xbox controller hat kein Accelerometer
		return false;
	}

//	public Body createBox(int x, int y, int width, int height, boolean isStatic) {
//		Body pBody;
//		BodyDef def = new BodyDef();
//		
//		if(isStatic)
//			def.type= BodyDef.BodyType.StaticBody;
//		else
//			def.type= BodyDef.BodyType.DynamicBody;
//		
//		def.position.set(x/PPM,y/PPM);
//		def.fixedRotation=true;
//		pBody = world.createBody(def);
//		
//		PolygonShape shape= new PolygonShape();
//		shape.setAsBox(width/2/PPM, height/2/PPM);
//		
//		pBody.createFixture(shape, 1.0f);
//		shape.dispose();
//		
//		return pBody;
//	}

//	if (inputMethod == "gamepad") {
//		if (currentDirection == PovDirection.north && y < Constants.BILDSCHIRMHOEHE - 55) { // 55 ist die l�nge des
//			// panzers,damit diese nicht
//			// �bers bild hinaus geht
//			y += SPEED * Gdx.graphics.getDeltaTime();
//			texTank = new Texture("Panzer35x55o.png");
//			Schussrichtung = 1;
//		}
//
//		if (currentDirection == PovDirection.south && y >= 0) {
//			y -= SPEED * Gdx.graphics.getDeltaTime();
//			texTank = new Texture("Panzer35x55u.png");
//			Schussrichtung = 2;
//		}
//
//		if (currentDirection == PovDirection.east && x < Constants.BILDSCHIRMBREITE - 55) {
//			x += SPEED * Gdx.graphics.getDeltaTime();
//			texTank = new Texture("Panzer35x55r.png");
//			Schussrichtung = 3;
//		}
//
//		if (currentDirection == PovDirection.west && x >= 0) {
//			x -= SPEED * Gdx.graphics.getDeltaTime();
//			texTank = new Texture("Panzer35x55l.png");
//			Schussrichtung = 4;
//		}

//Diagonales Fahren
//		if (currentDirection == PovDirection.northWest && x >= 0 && y < Constants.BILDSCHIRMHOEHE - 55) {
//			texTank = new Texture("Panzer35x55 ol.png");
//			Schussrichtung = 5;
//		}
//
//		if (currentDirection == PovDirection.northEast && x < Constants.BILDSCHIRMBREITE - 55
//				&& y < Constants.BILDSCHIRMHOEHE - 55) {
//			texTank = new Texture("Panzer35x55 or.png");
//			Schussrichtung = 6;
//		}
//
//		if (currentDirection == PovDirection.southEast && x < Constants.BILDSCHIRMBREITE - 55 && y >= 0) {
//			texTank = new Texture("Panzer35x55 ur.png");
//			Schussrichtung = 7;
//		}
//
//		if (currentDirection == PovDirection.southWest && x >= 0 && y >= 0) {
//			texTank = new Texture("Panzer35x55 ul.png");
//			Schussrichtung = 8;
//		}
//
//		if (currentDirection == PovDirection.northWest && x >= 0 && y < Constants.BILDSCHIRMHOEHE - 55) {
//			texTank = new Texture("Panzer35x55 ol.png");
//			Schussrichtung = 5;
//		}
//
//		if (currentDirection == PovDirection.northEast && x < Constants.BILDSCHIRMBREITE - 55
//				&& y < Constants.BILDSCHIRMHOEHE - 55) {
//			texTank = new Texture("Panzer35x55 or.png");
//			Schussrichtung = 6;
//		}
//
//		if (currentDirection == PovDirection.southEast && x < Constants.BILDSCHIRMBREITE - 55 && y >= 0) {
//			texTank = new Texture("Panzer35x55 ur.png");
//			Schussrichtung = 7;
//		}
//
//		if (currentDirection == PovDirection.southWest && x >= 0 && y >= 0) {
//			texTank = new Texture("Panzer35x55 ul.png");
//			Schussrichtung = 8;
//		}
//Schießen in Himmlesrichtungen

//		if (isApressed && Schussrichtung == 1) { // Panzer schie?t jetzt nur noch in die
//			// Richtung wo der letzte
//			// Bewegungsbefehl hinging
////tank = new Tank(this, spawnPointTank, inputKeyboard); //sorgte daf�r das mit jedem schuss panzer spawnen
//			myFlower = new F_L_O_W_E_R(this, new Vector2(x, y), new Vector2(0, 20));
//			flowers.add(myFlower);
//			isApressed = false;
//		}
//		if (isApressed && Schussrichtung == 3) {
//			myFlower = new F_L_O_W_E_R(this, new Vector2(x, y), new Vector2(20, 0));
//			flowers.add(myFlower);
//			isApressed = false;
//		}
//		if (isApressed && Schussrichtung == 4) {
//			myFlower = new F_L_O_W_E_R(this, new Vector2(x, y), new Vector2(-20, 0));
//			flowers.add(myFlower);
//			isApressed = false;
//		}
//		if (isApressed && Schussrichtung == 2) {
//			myFlower = new F_L_O_W_E_R(this, new Vector2(x, y), new Vector2(0, -20));
//			flowers.add(myFlower);
//			isApressed = false;
//		}
////Diagonales Schießen
//		if (isApressed && Schussrichtung == 5) {
//			myFlower = new F_L_O_W_E_R(this, new Vector2(x, y), new Vector2(-20, 20));
//			flowers.add(myFlower);
//			isApressed = false;
//		}
//		if (isApressed && Schussrichtung == 6) {
//			myFlower = new F_L_O_W_E_R(this, new Vector2(x, y), new Vector2(20, 20));
//			flowers.add(myFlower);
//			isApressed = false;
//		}
//		if (isApressed && Schussrichtung == 7) {
//			myFlower = new F_L_O_W_E_R(this, new Vector2(x, y), new Vector2(20, -20));
//			flowers.add(myFlower);
//			isApressed = false;
//		}
//		if (isApressed && Schussrichtung == 8) {
//			myFlower = new F_L_O_W_E_R(this, new Vector2(x, y), new Vector2(-20, -20));
//			flowers.add(myFlower);
//			isApressed = false;
//		}
//	} else {
//		if (Gdx.input.isKeyPressed(Keys.UP) && y < Constants.BILDSCHIRMHOEHE - 55) { // 55 ist die l�nge des
//																						// panzers,damit diese nicht
//																						// �bers bild hinaus geht
//			y += SPEED * Gdx.graphics.getDeltaTime();
//			texTank = new Texture("Panzer35x55o.png");
//			Schussrichtung = 1;
//		}
//
//		if (Gdx.input.isKeyPressed(Keys.DOWN) && y >= 0) {
//			y -= SPEED * Gdx.graphics.getDeltaTime();
//			texTank = new Texture("Panzer35x55u.png");
//			Schussrichtung = 2;
//		}
//
//		if (Gdx.input.isKeyPressed(Keys.RIGHT) && x < Constants.BILDSCHIRMBREITE - 55) {
//			x += SPEED * Gdx.graphics.getDeltaTime();
//			texTank = new Texture("Panzer35x55r.png");
//			Schussrichtung = 3;
//		}
//
//		if (Gdx.input.isKeyPressed(Keys.LEFT) && x >= 0) {
//			x -= SPEED * Gdx.graphics.getDeltaTime();
//			texTank = new Texture("Panzer35x55l.png");
//			Schussrichtung = 4;
//		}
//
//		if (Gdx.input.isKeyPressed(Keys.W) && y < Constants.BILDSCHIRMHOEHE - 55) {
//			y += SPEED * Gdx.graphics.getDeltaTime();
//			texTank = new Texture("Panzer35x55o.png");
//			Schussrichtung = 1;
//		}
//
//		if (Gdx.input.isKeyPressed(Keys.S) && y >= 0) {
//			y -= SPEED * Gdx.graphics.getDeltaTime();
//			texTank = new Texture("Panzer35x55u.png");
//			Schussrichtung = 2;
//		}
//
//		if (Gdx.input.isKeyPressed(Keys.D) && x < Constants.BILDSCHIRMBREITE - 55) {
//			x += SPEED * Gdx.graphics.getDeltaTime();
//			texTank = new Texture("Panzer35x55r.png");
//			Schussrichtung = 3;
//		}
//
//		if (Gdx.input.isKeyPressed(Keys.A) && x >= 0) {
//			x -= SPEED * Gdx.graphics.getDeltaTime();
//			texTank = new Texture("Panzer35x55l.png");
//			Schussrichtung = 4;
//		}
//		// Diagonales Fahren
//		if (Gdx.input.isKeyPressed(Keys.UP) && Gdx.input.isKeyPressed(Keys.LEFT) && x >= 0
//				&& y < Constants.BILDSCHIRMHOEHE - 55) {
//			texTank = new Texture("Panzer35x55 ol.png");
//			Schussrichtung = 5;
//		}
//
//		if (Gdx.input.isKeyPressed(Keys.UP) && Gdx.input.isKeyPressed(Keys.RIGHT)
//				&& x < Constants.BILDSCHIRMBREITE - 55 && y < Constants.BILDSCHIRMHOEHE - 55) {
//			texTank = new Texture("Panzer35x55 or.png");
//			Schussrichtung = 6;
//		}
//
//		if (Gdx.input.isKeyPressed(Keys.DOWN) && Gdx.input.isKeyPressed(Keys.RIGHT)
//				&& x < Constants.BILDSCHIRMBREITE - 55 && y >= 0) {
//			texTank = new Texture("Panzer35x55 ur.png");
//			Schussrichtung = 7;
//		}
//
//		if (Gdx.input.isKeyPressed(Keys.DOWN) && Gdx.input.isKeyPressed(Keys.LEFT) && x >= 0 && y >= 0) {
//			texTank = new Texture("Panzer35x55 ul.png");
//			Schussrichtung = 8;
//		}
//
//		if (Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.A) && x >= 0
//				&& y < Constants.BILDSCHIRMHOEHE - 55) {
//			texTank = new Texture("Panzer35x55 ol.png");
//			Schussrichtung = 5;
//		}
//
//		if (Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.D) && x < Constants.BILDSCHIRMBREITE - 55
//				&& y < Constants.BILDSCHIRMHOEHE - 55) {
//			texTank = new Texture("Panzer35x55 or.png");
//			Schussrichtung = 6;
//		}
//
//		if (Gdx.input.isKeyPressed(Keys.S) && Gdx.input.isKeyPressed(Keys.D) && x < Constants.BILDSCHIRMBREITE - 55
//				&& y >= 0) {
//			texTank = new Texture("Panzer35x55 ur.png");
//			Schussrichtung = 7;
//		}
//
//		if (Gdx.input.isKeyPressed(Keys.S) && Gdx.input.isKeyPressed(Keys.A) && x >= 0 && y >= 0) {
//			texTank = new Texture("Panzer35x55 ul.png");
//			Schussrichtung = 8;
//		}
	// Schießen in Himmlesrichtungen
	/*
	 * if (Gdx.input.isKeyJustPressed(Keys.SPACE) && Schussrichtung == 1) { //
	 * Panzer schie?t jetzt nur noch in die // Richtung wo der letzte //
	 * Bewegungsbefehl hinging // tank = new Tank(this, spawnPointTank,
	 * inputKeyboard); //sorgte daf�r das mit jedem schuss panzer spawnen myFlower =
	 * new F_L_O_W_E_R(this, new Vector2(x, y), new Vector2(0, 20));
	 * flowers.add(myFlower); } if (Gdx.input.isKeyJustPressed(Keys.SPACE) &&
	 * Schussrichtung == 3) { myFlower = new F_L_O_W_E_R(this, new Vector2(x, y),
	 * new Vector2(20, 0)); flowers.add(myFlower); } if
	 * (Gdx.input.isKeyJustPressed(Keys.SPACE) && Schussrichtung == 4) { myFlower =
	 * new F_L_O_W_E_R(this, new Vector2(x, y), new Vector2(-20, 0));
	 * flowers.add(myFlower); } if (Gdx.input.isKeyJustPressed(Keys.SPACE) &&
	 * Schussrichtung == 2) { myFlower = new F_L_O_W_E_R(this, new Vector2(x, y),
	 * new Vector2(0, -20)); flowers.add(myFlower); } // Diagonales Schießen if
	 * (Gdx.input.isKeyJustPressed(Keys.SPACE) && Schussrichtung == 5) { myFlower =
	 * new F_L_O_W_E_R(this, new Vector2(x, y), new Vector2(-20, 20));
	 * flowers.add(myFlower); } if (Gdx.input.isKeyJustPressed(Keys.SPACE) &&
	 * Schussrichtung == 6) { myFlower = new F_L_O_W_E_R(this, new Vector2(x, y),
	 * new Vector2(20, 20)); flowers.add(myFlower); } if
	 * (Gdx.input.isKeyJustPressed(Keys.SPACE) && Schussrichtung == 7) { myFlower =
	 * new F_L_O_W_E_R(this, new Vector2(x, y), new Vector2(20, -20));
	 * flowers.add(myFlower); } if (Gdx.input.isKeyJustPressed(Keys.SPACE) &&
	 * Schussrichtung == 8) { myFlower = new F_L_O_W_E_R(this, new Vector2(x, y),
	 * new Vector2(-20, -20)); flowers.add(myFlower); }
	 */
// geh�rt zum if else block f�r den controllerinput
//	Target zielscheibe = new Target(this, spawnPointZielscheibe, inputKeyboard); // --> erster, einmaliger Spawn der
	// Zielscheibe
	// auskommentiert und nach oben verschoben
	// weil sonst viele boxen spawnen

	// if(Zielscheibe wird von F_L_O_W_E_R getroffen --> Collision)
	// altes Target entfernen und
	// spriteBatch.draw(texTarget, zielscheibenPositionNeuX,
	// zielscheibenPositionNeuY); --> neue Random Position anhand von oben
	// definierten random X und Y Werten

}

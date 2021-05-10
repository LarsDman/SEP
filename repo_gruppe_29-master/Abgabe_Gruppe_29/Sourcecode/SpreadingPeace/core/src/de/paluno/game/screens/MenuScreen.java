package de.paluno.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import de.paluno.game.SEPgame;
import de.paluno.game.gameobjects.Colour;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuScreen extends com.badlogic.gdx.ScreenAdapter implements Screen {
	private SEPgame game;
	protected static PlayerInput inputPlayer1, inputPlayer2;//Spielmodi
	public static boolean CtF; //CtF für CollisionHandler -> public static
	protected static boolean multiplayer;//für Hud -> protected static
	private Viewport vport;
	private SpriteBatch sb;
	private Stage stage;
	private Skin skin;
	
	protected static String modename;//für Hud -> protected static
	
	//Alles für ColourTable
	private Table colourTable;
	private TextButton colourToMenu;
	private Label colourTank1Label, colourTank2Label, colourTank3Label, colourTank4Label, colourTank5Label;
	protected CheckBox tank1Blue, tank1Brown, tank1Green, tank1Pink, tank1Red, tank1Yellow;
	protected CheckBox tank2Blue, tank2Brown, tank2Green, tank2Pink, tank2Red, tank2Yellow;
	protected CheckBox tank3Blue, tank3Brown, tank3Green, tank3Pink, tank3Red, tank3Yellow;
	protected CheckBox tank4Blue, tank4Brown, tank4Green, tank4Pink, tank4Red, tank4Yellow;
	protected CheckBox tank5Blue, tank5Brown, tank5Green, tank5Pink, tank5Red, tank5Yellow;
	
	private ButtonGroup<CheckBox>colourBoxTank1, colourBoxTank2, colourBoxTank3, colourBoxTank4, colourBoxTank5;
	public static ArrayList<Colour> colours;//für Spielmodi & Bosstank -> public static
	
	//Alles für MapTable
	private Table mapTable;
	private TextButton mapToMenu;
	private Label mapLabel;
	private CheckBox map1, map2, map3;
	private Image map1Img;
	private Texture map1Tex, map2Tex, map3Tex;
	private String mapForModi;
	public static String mapToShow;
	private ButtonGroup<CheckBox> maps;
	
	//Alles für SteuerungTable
	private Table steuerung;
	protected TextField keyboardStop, keyboardUp, keyboardDown, keyboardLeft, keyboardRight, keyboardLeftTurn, keyboardRightTurn, keyboardShoot;
	protected TextField mouseStop, mouseUp, mouseDown, mouseLeft, mouseRight, mouseLeftTurn, mouseRightTurn, mouseShoot;
	protected TextField gamepadStop, gamepadDrive, gamepadAim1, gamepadAim2, gamepadFlowerUp, gamepadFlowerDown, gamepadShoot;
	protected TextField keyboardNormalFlower, keyboardFastFlower, keyboardBounceFlower, keyboardTripleFlower;
	protected TextField mouseNormalFlower, mouseFastFlower, mouseBounceFlower, mouseTripleFlower;
	protected TextField gamepadNormalFlower, gamepadFastFlower, gamepadBounceFlower,gamepadTripleFlower;
	
	private ArrayList<String> keyboardSettings, mouseSettings, gamepadSettings, emptySettings;

	protected static ArrayList<String> teams;//für Spielmodi -> protected static
	private TextButton steuerungToMenu;
	private Label keyboardLabel, mouseLabel, gamepadLabel, labelUp, labelDown, labelLeft, labelRight, labelLeftTurn, labelRightTurn, labelShoot;
	private Label labelNormalFlower, labelFastFlower, labelBounceFlower, labelTripleFlower, platz, stop;
	private Label gamepadDriveLabel, gamepadAim1Label, gamepadAim2Label, gamepadFlowerUpLabel, gamepadFlowerDownLabel, gamepadShootLabel;
	
	//Alles für TeamTable
	private Table teamTable;
	private TextButton teamToMenu, teamButton;
	private Label labelTank1, labelTank2, labelTank3, labelTank4, labelTank5, labelTeam, labelTeam2, labelInfoTank3, labelInfoTank4, labelInfoTank5;
	private CheckBox keyboardTank1, mouseTank1, gamepadTank1, keyboardTank2, mouseTank2, gamepadTank2, kiTank2;
	private ButtonGroup<CheckBox> keyboards, mouses, gamepads;
	protected static PlayerState playerState;//für Spielmodi -> protected
	protected TextField ki1, ki2, ki3;
	protected TextField player1Team, player2Team;

	//Alles für FirstTable
	private Table firstTable;
	private TextButton preferences, exit, swc, bossfight, CaptureTheFlag, freeForAll, zombie, colourButton, mapButton;
	private Label modiLabel, settingsLabel;
	
	public MenuScreen(SEPgame game) {
		this.game = game;
		multiplayer = false;
		CtF = false;
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		inputPlayer1 = PlayerInput.KEYBOARD;
		inputPlayer2 = PlayerInput.MOUSE;
		playerState = PlayerState.ACTIVE;
		
		
		sb = new SpriteBatch();
		vport = new ScreenViewport();
		
		stage = new Stage(vport, sb);
		Gdx.input.setInputProcessor(stage);
	}

	public void show() {
		this.firstTable();
		this.chooseSettings();
		this.chooseColour();
		this.chooseMap();
		this.chooseTeam();
	}
	
	public void firstTable() {
		firstTable = new Table();
		firstTable.setFillParent(true);
		firstTable.setDebug(false);
		firstTable.setVisible(true);
		stage.addActor(firstTable);
		preferences = new TextButton("Preferences", skin);
		exit = new TextButton("Exit", skin);
		freeForAll = new TextButton("Free for all", skin);
		zombie = new TextButton("Zombie", skin);
		CaptureTheFlag = new TextButton("Capture The Flag", skin);
		swc = new TextButton("Survival with coins", skin);
		bossfight = new TextButton("Bossfight", skin);
		colourButton = new TextButton("Colours", skin);
		mapButton = new TextButton("Maps", skin);
		teamButton = new TextButton("Teams", skin);
		
		modiLabel = new Label("Modes", skin);
		settingsLabel = new Label("Settings", skin);
		
		firstTable.add(modiLabel);
		firstTable.add(settingsLabel);
		firstTable.row().pad(10, 0, 10, 0);
		firstTable.add(freeForAll).fillX();
		firstTable.add(preferences).fillX();
		firstTable.row().pad(10, 0, 10, 0);
		firstTable.add(zombie).fillX();
		firstTable.add(colourButton).fillX();
		firstTable.row().pad(10, 0, 10, 0);
		firstTable.add(swc).fillX();
		firstTable.add(mapButton).fillX();
		firstTable.row().pad(10, 0, 10, 0);
		firstTable.add(bossfight).fillX();
		firstTable.add(teamButton).fillX();
		firstTable.row().pad(10, 0, 10, 0);
		firstTable.add(CaptureTheFlag).fillX();
		firstTable.row().pad(10, 0, 10, 0);
		firstTable.add(exit).fillX();
		
		teamButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				firstTable.setVisible(false);
				teamTable.setVisible(true);
				Sound sound = Gdx.audio.newSound(Gdx.files.internal("wetclick.wav"));
				sound.play(1f);
			}
		});
		
		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();				
			}
		});

		preferences.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				firstTable.setVisible(false);
				steuerung.setVisible(true);
				Sound sound = Gdx.audio.newSound(Gdx.files.internal("wetclick.wav"));
				sound.play(1f);
			}
		});
		
		zombie.addListener(new OwnChangeListener(this) {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				firstTable.setVisible(false);
				modename = "Survival";
				if(inputPlayer1== PlayerInput.KEYBOARD)game.setScreen(new Survival(game, "keyboard", keyboardSettings, emptySettings, mapForModi));
				if(inputPlayer1== PlayerInput.MOUSE)game.setScreen(new Survival(game, "maus", mouseSettings, emptySettings, mapForModi));
				if(inputPlayer1== PlayerInput.GAMEPAD)game.setScreen(new Survival(game, "gamepad", gamepadSettings, emptySettings, mapForModi));
				Sound sound = Gdx.audio.newSound(Gdx.files.internal("wetclick.wav"));
				sound.play(1f);
			}
		});
		
//		Niklas.addListener(new OwnChangeListener(this) {
//			@Override
//			public void changed(ChangeEvent event, Actor actor) {
//				firstTable.setVisible(false);
//				modename = "Survival mit Geld";
//				if(inputPlayer1== PlayerInput.KEYBOARD)game.setScreen(new Schatzkammer(game, "keyboard", keyboardSettings, emptySettings, mapForModi));
//				if(inputPlayer1== PlayerInput.MOUSE)game.setScreen(new Schatzkammer(game, "maus", mouseSettings, emptySettings, mapForModi));
//				if(inputPlayer1== PlayerInput.GAMEPAD)game.setScreen(new Schatzkammer(game, "gamepad", gamepadSettings, emptySettings, mapForModi));
//				Sound sound = Gdx.audio.newSound(Gdx.files.internal("wetclick.wav"));
//				sound.play(1f);
//			}
//		});
		
		swc.addListener(new OwnChangeListener(this) {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				firstTable.setVisible(false);
				modename = "Survival mit Geld";
				if(inputPlayer1== PlayerInput.KEYBOARD)game.setScreen(new Survival_mit_Geld(game, "keyboard", keyboardSettings, emptySettings, mapForModi));
				if(inputPlayer1== PlayerInput.MOUSE)game.setScreen(new Survival_mit_Geld(game, "maus", mouseSettings, emptySettings, mapForModi));
				if(inputPlayer1== PlayerInput.GAMEPAD)game.setScreen(new Survival_mit_Geld(game, "gamepad", gamepadSettings, emptySettings, mapForModi));
				Sound sound = Gdx.audio.newSound(Gdx.files.internal("wetclick.wav"));
				sound.play(1f);
			}
		});
		
		bossfight.addListener(new OwnChangeListener(this) {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				firstTable.setVisible(false);
				modename = "Bossfight";
				multiplayer = false;
				mapForModi = "bossmap1.tmx";
				if(inputPlayer1== PlayerInput.KEYBOARD)game.setScreen(new Bossfight(game, "keyboard", keyboardSettings, emptySettings, mapForModi));
				if(inputPlayer1== PlayerInput.MOUSE)game.setScreen(new Bossfight(game, "maus", mouseSettings, emptySettings, mapForModi));
				if(inputPlayer1== PlayerInput.GAMEPAD)game.setScreen(new Bossfight(game, "gamepad", gamepadSettings, emptySettings, mapForModi));
				Sound sound = Gdx.audio.newSound(Gdx.files.internal("wetclick.wav"));
				sound.play(1f);
			}
		});
		
		freeForAll.addListener(new OwnChangeListener(this) {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				modename = "Deathmatch";
				firstTable.setVisible(false);
				if(playerState == PlayerState.INACTIVE || playerState == PlayerState.KI) {
					multiplayer = false;
					if(inputPlayer1== PlayerInput.KEYBOARD) game.setScreen(new Deathmatch(game, "keyboard", keyboardSettings, emptySettings, mapForModi, MenuScreen.teams));
					if(inputPlayer1== PlayerInput.MOUSE) game.setScreen(new Deathmatch(game, "maus", mouseSettings, emptySettings, mapForModi, MenuScreen.teams));
					if(inputPlayer1== PlayerInput.GAMEPAD) game.setScreen(new Deathmatch(game, "gamepad", gamepadSettings, emptySettings, mapForModi, MenuScreen.teams));
				}else if(playerState == PlayerState.ACTIVE){
					multiplayer = true;
					
					if(inputPlayer1== PlayerInput.KEYBOARD && inputPlayer2 == PlayerInput.MOUSE) game.setScreen(new Deathmatch(game, "keyboard", keyboardSettings, mouseSettings, mapForModi, MenuScreen.teams));
					if(inputPlayer1== PlayerInput.KEYBOARD && inputPlayer2 == PlayerInput.GAMEPAD) game.setScreen(new Deathmatch(game, "keyboard", keyboardSettings, gamepadSettings, mapForModi, MenuScreen.teams));
					if(inputPlayer1== PlayerInput.MOUSE && inputPlayer2 == PlayerInput.KEYBOARD) game.setScreen(new Deathmatch(game, "maus", mouseSettings, keyboardSettings, mapForModi, MenuScreen.teams));
					if(inputPlayer1== PlayerInput.MOUSE && inputPlayer2 == PlayerInput.GAMEPAD) game.setScreen(new Deathmatch(game, "maus", mouseSettings, gamepadSettings, mapForModi, MenuScreen.teams));
					if(inputPlayer1== PlayerInput.GAMEPAD && inputPlayer2 == PlayerInput.KEYBOARD) game.setScreen(new Deathmatch(game, "gamepad", gamepadSettings, keyboardSettings, mapForModi, MenuScreen.teams));
					if(inputPlayer1== PlayerInput.GAMEPAD && inputPlayer2 == PlayerInput.MOUSE) game.setScreen(new Deathmatch(game, "gamepad", gamepadSettings, mouseSettings, mapForModi, MenuScreen.teams));
				}
				
				Sound sound = Gdx.audio.newSound(Gdx.files.internal("wetclick.wav"));
				sound.play(1f);
			}
		});
		
		CaptureTheFlag.addListener(new OwnChangeListener(this) {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				modename = "CaptureTheFlag";
				CtF=true;
				firstTable.setVisible(false);
				if(playerState == PlayerState.INACTIVE || playerState == PlayerState.KI) {
					if(inputPlayer1== PlayerInput.KEYBOARD) game.setScreen(new CaptureTheFlag(game, "keyboard", keyboardSettings, emptySettings, mapForModi));
					if(inputPlayer1== PlayerInput.MOUSE) game.setScreen(new CaptureTheFlag(game, "maus", mouseSettings, emptySettings, mapForModi));
					if(inputPlayer1== PlayerInput.GAMEPAD) game.setScreen(new CaptureTheFlag(game, "gamepad", gamepadSettings, emptySettings, mapForModi));
				}else if(playerState == PlayerState.ACTIVE){
					if(inputPlayer1== PlayerInput.KEYBOARD && inputPlayer2 == PlayerInput.MOUSE) game.setScreen(new CaptureTheFlag(game, "keyboard", keyboardSettings, mouseSettings, mapForModi));
					if(inputPlayer1== PlayerInput.KEYBOARD && inputPlayer2 == PlayerInput.GAMEPAD) game.setScreen(new CaptureTheFlag(game, "keyboard", keyboardSettings, gamepadSettings, mapForModi));
					if(inputPlayer1== PlayerInput.MOUSE && inputPlayer2 == PlayerInput.KEYBOARD) game.setScreen(new CaptureTheFlag(game, "maus", mouseSettings, keyboardSettings, mapForModi));
					if(inputPlayer1== PlayerInput.MOUSE && inputPlayer2 == PlayerInput.GAMEPAD) game.setScreen(new CaptureTheFlag(game, "maus", mouseSettings, gamepadSettings, mapForModi));
					if(inputPlayer1== PlayerInput.GAMEPAD && inputPlayer2 == PlayerInput.KEYBOARD) game.setScreen(new CaptureTheFlag(game, "gamepad", gamepadSettings, keyboardSettings, mapForModi));
					if(inputPlayer1== PlayerInput.GAMEPAD && inputPlayer2 == PlayerInput.MOUSE) game.setScreen(new CaptureTheFlag(game, "gamepad", gamepadSettings, mouseSettings, mapForModi));
				}
				Sound sound = Gdx.audio.newSound(Gdx.files.internal("wetclick.wav"));
				sound.play(1f);
				
			}
		});
		
		colourButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				colourTable.setVisible(true);
				firstTable.setVisible(false);
			}
		});
		
		mapButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				mapTable.setVisible(true);
				firstTable.setVisible(false);
			}
		});
	}
	
	public void chooseColour() {
		colours = new ArrayList<Colour>();
		colours.add(Colour.BLUE);
		colours.add(Colour.YELLOW);
		colours.add(Colour.RED);
		colours.add(Colour.GREEN);
		colours.add(Colour.BROWN);
		
		tank1Blue = new CheckBox("Blue", skin);
		tank1Brown = new CheckBox("Brown", skin);
		tank1Green = new CheckBox("Green", skin);
		tank1Pink = new CheckBox("Pink", skin);
		tank1Red = new CheckBox("Red", skin);
		tank1Yellow = new CheckBox("Yellow", skin);
		
		tank2Blue = new CheckBox("Blue", skin);
		tank2Brown = new CheckBox("Brown", skin);
		tank2Green = new CheckBox("Green", skin);
		tank2Pink = new CheckBox("Pink", skin);
		tank2Red = new CheckBox("Red", skin);
		tank2Yellow = new CheckBox("Yellow", skin);
		
		tank3Blue = new CheckBox("Blue", skin);
		tank3Brown = new CheckBox("Brown", skin);
		tank3Green = new CheckBox("Green", skin);
		tank3Pink = new CheckBox("Pink", skin);
		tank3Red = new CheckBox("Red", skin);
		tank3Yellow = new CheckBox("Yellow", skin);
		
		tank4Blue = new CheckBox("Blue", skin);
		tank4Brown = new CheckBox("Brown", skin);
		tank4Green = new CheckBox("Green", skin);
		tank4Pink = new CheckBox("Pink", skin);
		tank4Red = new CheckBox("Red", skin);
		tank4Yellow = new CheckBox("Yellow", skin);
		
		tank5Blue = new CheckBox("Blue", skin);
		tank5Brown = new CheckBox("Brown", skin);
		tank5Green = new CheckBox("Green", skin);
		tank5Pink = new CheckBox("Pink", skin);
		tank5Red = new CheckBox("Red", skin);
		tank5Yellow = new CheckBox("Yellow", skin);
		
		colourBoxTank1 = new ButtonGroup<CheckBox>();
		colourBoxTank2 = new ButtonGroup<CheckBox>();
		colourBoxTank3 = new ButtonGroup<CheckBox>();
		colourBoxTank4 = new ButtonGroup<CheckBox>();
		colourBoxTank5 = new ButtonGroup<CheckBox>();
		
		colourBoxTank1.setMinCheckCount(1);
		colourBoxTank1.setMaxCheckCount(1);
		
		colourBoxTank2.setMinCheckCount(1);
		colourBoxTank2.setMaxCheckCount(1);
		
		colourBoxTank3.setMinCheckCount(1);
		colourBoxTank3.setMaxCheckCount(1);
		
		colourBoxTank4.setMinCheckCount(1);
		colourBoxTank4.setMaxCheckCount(1);
		
		colourBoxTank5.setMinCheckCount(1);
		colourBoxTank5.setMaxCheckCount(1);
		
		colourBoxTank1.add(tank1Blue, tank1Brown, tank1Green, tank1Pink, tank1Red, tank1Yellow);
		colourBoxTank2.add(tank2Blue, tank2Brown, tank2Green, tank2Pink, tank2Red, tank2Yellow);
		colourBoxTank3.add(tank3Blue, tank3Brown, tank3Green, tank3Pink, tank3Red, tank3Yellow);
		colourBoxTank4.add(tank4Blue, tank4Brown, tank4Green, tank4Pink, tank4Red, tank4Yellow);
		colourBoxTank5.add(tank5Blue, tank5Brown, tank5Green, tank5Pink, tank5Red, tank5Yellow);
		
		colourTable = new Table();
		colourTable.setVisible(false);
		stage.addActor(colourTable);
		colourToMenu = new TextButton("Back", skin);
		colourTank1Label = new Label("Tank 1:", skin);
		colourTank2Label = new Label("Tank 2:", skin);
		colourTank3Label = new Label("Tank 3:", skin);
		colourTank4Label = new Label("Tank 4:", skin);
		colourTank5Label = new Label("Tank 5:", skin);
		
		colourTable.setFillParent(true);
		colourTable.center();
		colourTable.add(colourTank1Label);
		colourTable.add(tank1Blue);
		colourTable.add(tank1Brown);
		colourTable.add(tank1Green);
		colourTable.add(tank1Pink);
		colourTable.add(tank1Red);
		colourTable.add(tank1Yellow);
		
		colourTable.row();
		colourTable.add(colourTank2Label);
		colourTable.add(tank2Blue);
		colourTable.add(tank2Brown);
		colourTable.add(tank2Green);
		colourTable.add(tank2Pink);
		colourTable.add(tank2Red);
		colourTable.add(tank2Yellow);
		
		colourTable.row();
		colourTable.add(colourTank3Label);
		colourTable.add(tank3Blue);
		colourTable.add(tank3Brown);
		colourTable.add(tank3Green);
		colourTable.add(tank3Pink);
		colourTable.add(tank3Red);
		colourTable.add(tank3Yellow);
		
		colourTable.row();
		colourTable.add(colourTank4Label);
		colourTable.add(tank4Blue);
		colourTable.add(tank4Brown);
		colourTable.add(tank4Green);
		colourTable.add(tank4Pink);
		colourTable.add(tank4Red);
		colourTable.add(tank4Yellow);
		
		colourTable.row();
		colourTable.add(colourTank5Label);
		colourTable.add(tank5Blue);
		colourTable.add(tank5Brown);
		colourTable.add(tank5Green);
		colourTable.add(tank5Pink);
		colourTable.add(tank5Red);
		colourTable.add(tank5Yellow);
		
		colourTable.row();
		colourTable.add(colourToMenu).fillX().colspan(7);
		
		colourToMenu.addListener(new OwnChangeListener(this) {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				this.updateColourSettings();
				colourTable.setVisible(false);
				firstTable.setVisible(true);
			}
		});
	}
	
	public void chooseMap() {
		maps = new ButtonGroup<CheckBox>();
		map1 = new CheckBox("Green Hills", skin);
		map2 = new CheckBox("Desert", skin);
		map3 = new CheckBox("Cave", skin);
		
		mapForModi = "TiledMap32.tmx";
		mapToShow = "Green Hills";
		map1Tex = new Texture("Green Hills_500x284.png");
		map2Tex = new Texture("Desert_500x284.png");
		map3Tex = new Texture("Cave_500x284.png");
		map1Img = new Image(map1Tex);
		
		mapTable = new Table();
		mapTable.setVisible(false);
		stage.addActor(mapTable);
		mapToMenu = new TextButton("Back", skin);
		mapLabel = new Label("Green Hills", skin);
		
		maps.add(map1, map2, map3);
		maps.setMinCheckCount(1);
		maps.setMaxCheckCount(1);
		
		mapTable.setFillParent(true);
		mapTable.center();
		mapTable.add(mapLabel).colspan(3);
		mapTable.row();
		mapTable.add(map1Img).colspan(3);
		mapTable.row();
		mapTable.add(map1);
		mapTable.add(map2);
		mapTable.add(map3);
		mapTable.row();
		mapTable.add(mapToMenu).fillX().colspan(4);
		
		map1.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				map1Img.setDrawable(new TextureRegionDrawable(new TextureRegion(map1Tex)));
				mapLabel.setText("Green Hills");
				mapToShow = "Green Hills";
			}
		});
						
		map2.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				map1Img.setDrawable(new TextureRegionDrawable(new TextureRegion(map2Tex)));
				mapLabel.setText("Desert");
				mapToShow = "Desert";
			}
		});
		map3.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				map1Img.setDrawable(new TextureRegionDrawable(new TextureRegion(map3Tex)));
				mapLabel.setText("Cave");
				mapToShow = "Cave";
			}
		});	
		
		mapToMenu.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(map1.isChecked()) {
					mapForModi = "TiledMap32.tmx";
				}else if(map2.isChecked()) {
					mapForModi = "Desert.tmx";
				}else if(map3.isChecked()) {
					mapForModi = "Cave.tmx";
				}
				mapTable.setVisible(false);
				firstTable.setVisible(true);
			}
		});
	}
	
	public void chooseTeam() {
		player1Team = new TextField("1", skin);
		player2Team = new TextField("2", skin);
		ki1 = new TextField("3", skin);
		ki2 = new TextField("4", skin);
		ki3 = new TextField("5", skin);
		teams = new ArrayList<String>();
		teams.add(player1Team.getText());
		teams.add(player2Team.getText());
		teams.add(ki1.getText());
		teams.add(ki2.getText());
		teams.add(ki3.getText());
		
		labelTank1 = new Label("Tank 1", skin);
		labelTank2 = new Label("Tank 2", skin);
		labelTank3 = new Label("Tank 3", skin);
		labelTank4 = new Label("Tank 4", skin);
		labelTank5 = new Label("Tank 5", skin);
		labelTeam = new Label("Choose your Teams from 1 to 5 (for CtF 1 or 2)", skin);
		labelTeam2 = new Label("If you won't use a tank , take 0 as team", skin);
		labelInfoTank3 = new Label("Only Ki", skin);
		labelInfoTank4 = new Label("Only Ki", skin);
		labelInfoTank5 = new Label("Only Ki", skin);
		
		keyboardTank1 = new CheckBox("Keyboard", skin);
		mouseTank1 = new CheckBox("Maus", skin);
		gamepadTank1 = new CheckBox("Gamepad", skin);
		keyboardTank2 = new CheckBox("Keyboard", skin);
		mouseTank2 = new CheckBox("Maus", skin);
		gamepadTank2 = new CheckBox("Gamepad", skin);
		kiTank2 = new CheckBox("Ki", skin);
		
		keyboards = new ButtonGroup<CheckBox>();
		mouses = new ButtonGroup<CheckBox>();
		gamepads = new ButtonGroup<CheckBox>();
		
		keyboards.add(keyboardTank1, keyboardTank2);
		keyboards.setMaxCheckCount(1);
		keyboards.setMinCheckCount(0);
		
		mouses.add(mouseTank1, mouseTank2);
		mouses.setMaxCheckCount(1);
		mouses.setMinCheckCount(0);
		mouseTank1.setChecked(false);
		mouseTank2.setChecked(true);
		
		gamepads.add(gamepadTank1, gamepadTank2);
		gamepads.setMaxCheckCount(1);
		gamepads.setMinCheckCount(0);
		gamepadTank1.setChecked(false);
		
		teamTable = new Table();
		teamTable.setVisible(false);
		stage.addActor(teamTable);
		teamToMenu = new TextButton("Back", skin);
		
		teamTable.setFillParent(true);
		teamTable.center();
		teamTable.add(labelTeam).colspan(6);
		teamTable.row();
		teamTable.add(labelTeam2).colspan(6);
		teamTable.row();
		teamTable.add(labelTank1);
		teamTable.add(player1Team);
		teamTable.add(keyboardTank1);
		teamTable.add(mouseTank1);
		teamTable.add(gamepadTank1);
		teamTable.row();
		teamTable.add(labelTank2);
		teamTable.add(player2Team);
		teamTable.add(keyboardTank2);
		teamTable.add(mouseTank2);
		teamTable.add(gamepadTank2);
		teamTable.add(kiTank2);
		teamTable.row();
		teamTable.add(labelTank3);
		teamTable.add(ki1);
		teamTable.add(labelInfoTank3);
		teamTable.row();
		teamTable.add(labelTank4);
		teamTable.add(ki2);
		teamTable.add(labelInfoTank4);
		teamTable.row();
		teamTable.add(labelTank5);
		teamTable.add(ki3);
		teamTable.add(labelInfoTank5);
		teamTable.row();
		teamTable.add(teamToMenu).fillX().colspan(6);
		
		player2Team.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				String zero = "0";
				if(player2Team.getText().equalsIgnoreCase(zero)) {
					keyboardTank2.setVisible(false);
					mouseTank2.setVisible(false);
					gamepadTank2.setVisible(false);
					kiTank2.setVisible(false);
				}else {
					keyboardTank2.setVisible(true);
					mouseTank2.setVisible(true);
					gamepadTank2.setVisible(true);
					kiTank2.setVisible(true);
				}
			}
		});
		
		teamToMenu.addListener(new OwnChangeListener(this) {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				this.updateTeamSettings();
				
				if(keyboardTank1.isChecked()) {
					inputPlayer1 = PlayerInput.KEYBOARD;
				}else if(mouseTank1.isChecked()) {
					inputPlayer1 = PlayerInput.MOUSE;
				}else if(gamepadTank1.isChecked()) {
					inputPlayer1 = PlayerInput.GAMEPAD;
				}
				
				String zero = "0";
				if(player2Team.getText().equalsIgnoreCase(zero) == false) {
					if(keyboardTank2.isChecked()) {
						inputPlayer2 = PlayerInput.KEYBOARD;
						playerState = PlayerState.ACTIVE;
					}else if(mouseTank2.isChecked()) {
						inputPlayer2 = PlayerInput.MOUSE;
						playerState = PlayerState.ACTIVE;
					}else if(gamepadTank2.isChecked()) {
						inputPlayer2 = PlayerInput.GAMEPAD;
						playerState = PlayerState.ACTIVE;
					}else if(kiTank2.isChecked()) {
						playerState = PlayerState.KI;
					}
				}else {
					playerState = PlayerState.INACTIVE;
				}
				teamTable.setVisible(false);
				firstTable.setVisible(true);
			}
		});
	}
	
	public void chooseSettings() {
		keyboardSettings = new ArrayList<String>();
		mouseSettings = new ArrayList<String>();
		gamepadSettings = new ArrayList<String>();
		emptySettings = new ArrayList<String>();
		
		keyboardStop = new TextField("Q", skin);
		keyboardUp = new TextField("W", skin);
		keyboardDown = new TextField("S", skin);
		keyboardLeft = new TextField("A", skin);
		keyboardRight = new TextField("D", skin);
		keyboardLeftTurn = new TextField("O", skin);
		keyboardRightTurn = new TextField("P", skin);
		keyboardShoot = new TextField("Space", skin);
		keyboardNormalFlower = new TextField("1", skin);
		keyboardFastFlower = new TextField("2", skin);
		keyboardBounceFlower = new TextField("3", skin);
		keyboardTripleFlower = new TextField("4", skin);
		
		mouseStop = new TextField("I", skin);
		mouseUp = new TextField("H", skin);
		mouseDown = new TextField("N", skin);
		mouseLeft = new TextField("B", skin);
		mouseRight = new TextField("M", skin);
		mouseLeftTurn = new TextField("Mausbewegung", skin);
		mouseRightTurn = new TextField("Mausbewegung", skin);
		mouseShoot = new TextField("Linksklick", skin);
		mouseNormalFlower = new TextField("7", skin);
		mouseFastFlower = new TextField("8", skin);
		mouseBounceFlower = new TextField("9", skin);
		mouseTripleFlower = new TextField("0", skin);
		
		//fahren, zielen1, zielen2, blumenup, blumendown
		
		gamepadStop = new TextField("y", skin);
		gamepadDrive = new TextField("dpad",skin);
		gamepadAim1 = new TextField("rt",skin);
		gamepadAim2 = new TextField("lt",skin);
		gamepadFlowerUp = new TextField("rx",skin);
		gamepadFlowerDown = new TextField("lx",skin);
		gamepadShoot = new TextField("a", skin);
		
		keyboardSettings.add(keyboardUp.getText());
		keyboardSettings.add(keyboardLeft.getText());
		keyboardSettings.add(keyboardDown.getText());
		keyboardSettings.add(keyboardRight.getText());
		keyboardSettings.add(keyboardLeftTurn.getText());
		keyboardSettings.add(keyboardRightTurn.getText());
		keyboardSettings.add(keyboardShoot.getText());
		keyboardSettings.add(keyboardNormalFlower.getText());
		keyboardSettings.add(keyboardFastFlower.getText());
		keyboardSettings.add(keyboardBounceFlower.getText());
		keyboardSettings.add(keyboardTripleFlower.getText());
		keyboardSettings.add(keyboardStop.getText());
		
		mouseSettings.add(mouseUp.getText());
		mouseSettings.add(mouseLeft.getText());
		mouseSettings.add(mouseDown.getText());
		mouseSettings.add(mouseRight.getText());
		mouseSettings.add(mouseLeftTurn.getText());
		mouseSettings.add(mouseRightTurn.getText());
		mouseSettings.add(mouseShoot.getText());
		mouseSettings.add(mouseNormalFlower.getText());
		mouseSettings.add(mouseFastFlower.getText());
		mouseSettings.add(mouseBounceFlower.getText());
		mouseSettings.add(mouseTripleFlower.getText());
		mouseSettings.add(mouseStop.getText());
		
		gamepadSettings.add(gamepadDrive.getText());
		gamepadSettings.add(gamepadAim1.getText());
		gamepadSettings.add(gamepadAim2.getText());
		gamepadSettings.add(gamepadFlowerUp.getText());
		gamepadSettings.add(gamepadFlowerDown.getText());
		gamepadSettings.add(gamepadShoot.getText());
		gamepadSettings.add(gamepadStop.getText());
		
		keyboardLabel = new Label("Tastatur", skin);
		mouseLabel = new Label("Tastatur & Maus", skin);
		gamepadLabel = new Label("Gamepad", skin);
		labelUp = new Label("Oben", skin);
		labelDown = new Label("Unten", skin);
		labelLeft = new Label("Links", skin);
		labelRight = new Label("Rechts", skin);
		labelLeftTurn = new Label("Links drehen", skin);
		labelRightTurn = new Label("Rechts drehen", skin);
		labelShoot = new Label("Schiessen", skin);	
		labelNormalFlower = new Label("Normal Flower", skin);
		labelFastFlower = new Label("Fast Flower", skin);
		labelBounceFlower = new Label("Bounce Flower", skin);
		labelTripleFlower = new Label("Triple Flower", skin);
		platz = new Label(null, skin);
		gamepadDriveLabel = new Label("Fahren", skin);
		gamepadAim1Label = new Label("Rechts drehen", skin);
		gamepadAim2Label = new Label("Links drehen", skin);
		gamepadFlowerUpLabel = new Label("Flower hochschalten", skin);
		gamepadFlowerDownLabel = new Label("Flower runterschalten", skin);
		gamepadShootLabel = new Label("Schiessen", skin);
		stop = new Label("Pause", skin);
		
		steuerung = new Table();
		steuerung.setFillParent(true);
		steuerung.setDebug(false);
		steuerung.setVisible(false);
		stage.addActor(steuerung);
		steuerungToMenu = new TextButton("Back", skin);
		
		steuerung.add(platz);
		steuerung.add(keyboardLabel);
		steuerung.add(mouseLabel);
		steuerung.add(gamepadLabel);
		steuerung.row();
		steuerung.add(labelUp);
		steuerung.add(keyboardUp).fillX();
		steuerung.add(mouseUp).fillX();
		steuerung.add(gamepadDrive).fillX();
		steuerung.add(gamepadDriveLabel).fillX();
		steuerung.row();
		steuerung.add(labelDown);
		steuerung.add(keyboardDown).fillX();
		steuerung.add(mouseDown).fillX();
		steuerung.add(gamepadAim1).fillX();
		steuerung.add(gamepadAim1Label).fillX();
		steuerung.row();
		steuerung.add(labelLeft);
		steuerung.add(keyboardLeft).fillX();
		steuerung.add(mouseLeft).fillX();
		steuerung.add(gamepadAim2).fillX();
		steuerung.add(gamepadAim2Label).fillX();
		steuerung.row();
		steuerung.add(labelRight);
		steuerung.add(keyboardRight).fillX();
		steuerung.add(mouseRight).fillX();
		steuerung.add(gamepadFlowerUp).fillX();
		steuerung.add(gamepadFlowerUpLabel).fillX();
		steuerung.row();
		steuerung.add(labelLeftTurn);
		steuerung.add(keyboardLeftTurn).fillX();
		steuerung.add(mouseLeftTurn).fillX();
		steuerung.add(gamepadFlowerDown).fillX();
		steuerung.add(gamepadFlowerDownLabel).fillX();
		steuerung.row();
		steuerung.add(labelRightTurn);
		steuerung.add(keyboardRightTurn).fillX();
		steuerung.add(mouseRightTurn).fillX();
		steuerung.add(gamepadShoot).fillX();
		steuerung.add(gamepadShootLabel).fillX();
		steuerung.row();
		steuerung.add(labelShoot);
		steuerung.add(keyboardShoot).fillX();
		steuerung.add(mouseShoot).fillX();
		steuerung.row();
		steuerung.add(labelNormalFlower);
		steuerung.add(keyboardNormalFlower);
		steuerung.add(mouseNormalFlower);
		steuerung.add(gamepadNormalFlower).fillX();
		steuerung.row();
		steuerung.add(labelFastFlower);
		steuerung.add(keyboardFastFlower);
		steuerung.add(mouseFastFlower);
		steuerung.add(gamepadFastFlower).fillX();
		steuerung.row();
		steuerung.add(labelBounceFlower);
		steuerung.add(keyboardBounceFlower);
		steuerung.add(mouseBounceFlower);
		steuerung.add(gamepadBounceFlower).fillX();
		steuerung.row();
		steuerung.add(labelTripleFlower);
		steuerung.add(keyboardTripleFlower);
		steuerung.add(mouseTripleFlower);
		steuerung.add(gamepadTripleFlower).fillX();
		steuerung.row();
		steuerung.add(stop);
		steuerung.add(keyboardStop);
		steuerung.add(mouseStop);
		steuerung.add(gamepadStop);
		steuerung.row();
		steuerung.add(steuerungToMenu).fillX().colspan(5);
		
		steuerungToMenu.addListener(new OwnChangeListener(this) {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				this.updateKeyboardSettings();
				this.updateMouseSettings();
				this.updateGamepadSettings();
				Sound sound = Gdx.audio.newSound(Gdx.files.internal("wetclick.wav"));
				sound.play(1f);

				steuerung.setVisible(false);	
				firstTable.setVisible(true);
			}
		});
	}

	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
	}
	
	public void dispose() {
		stage.dispose();
	}
	
	public void hide() {}

	public static PlayerInput getInputPlayer1() {
		return inputPlayer1;
	}

	public static PlayerInput getInputPlayer2() {
		return inputPlayer2;
	}
	
	public static PlayerState getPlayerState() {
		return playerState;
	}

	public static void setPlayerState(PlayerState playerState) {
		MenuScreen.playerState = playerState;
	}
	
	public ArrayList<String> getEmptySettings() {
		return emptySettings;
	}

	public void setEmptySettings(ArrayList<String> emptySettings) {
		this.emptySettings = emptySettings;
	}

	public ArrayList<String> getGamepadSettings() {
		return gamepadSettings;
	}

	public void setGamepadSettings(ArrayList<String> gamepadSettings) {
		this.gamepadSettings = gamepadSettings;
	}

	public ArrayList<String> getMouseSettings() {
		return mouseSettings;
	}

	public void setMouseSettings(ArrayList<String> mouseSettings) {
		this.mouseSettings = mouseSettings;
	}

	public ArrayList<String> getKeyboardSettings() {
		return keyboardSettings;
	}

	public void setKeyboardSettings(ArrayList<String> keyboardSettings) {
		this.keyboardSettings = keyboardSettings;
	}
}

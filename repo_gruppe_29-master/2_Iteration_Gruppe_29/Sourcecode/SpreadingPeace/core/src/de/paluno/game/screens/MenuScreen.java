package de.paluno.game.screens;

import com.badlogic.gdx.Screen;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import de.paluno.game.SEPgame;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class MenuScreen extends com.badlogic.gdx.ScreenAdapter implements Screen {

	private SEPgame game;
	private int inputPlayer1;
	static int inputPlayer2;//static, da Deathmatch im selben Package darauf zugreift
	static boolean multiplayer = false;//static, da Deathmatch im selben Package darauf zugreift
	public static boolean survivalMode = false;

	private com.badlogic.gdx.scenes.scene2d.Stage stage; // wird f�r die Buttons zum funktionieren ben�tigt
	public ArrayList<String> keyboardSettings = new ArrayList<String>();
	public ArrayList<String> mouseSettings = new ArrayList<String>();
	public ArrayList<String> gamepadSettings = new ArrayList<String>();//besteht nur aus einem Feld, wird im Gamepadprovider nachgefragt
	public ArrayList<String> emptySettings = new ArrayList<String>();//wird beim Singleplayer an die Spielmodi übergeben für Player2
	public static ArrayList<String> teams = new ArrayList<String>();
	private String gamepadMethod1;
	private String gamepadMethod2;
	
	public MenuScreen(SEPgame game) {
		this.game = game;
		//0 vorne, 1 links, 2 hinten, 3 rechts, 4 links drehen, 5 rechts drehen, 6 schuss
		keyboardSettings.add("W");
		keyboardSettings.add("A");
		keyboardSettings.add("S");
		keyboardSettings.add("D");
		keyboardSettings.add("O");
		keyboardSettings.add("P");
		keyboardSettings.add("Space");
		
		mouseSettings.add("H");
		mouseSettings.add("B");
		mouseSettings.add("N");
		mouseSettings.add("M");
		mouseSettings.add("K");
		mouseSettings.add("L");
		mouseSettings.add("V");
		
		teams.add("1");
		teams.add("2");
		teams.add("3");
		teams.add("4");
		teams.add("5");
		
		gamepadMethod1 = "1";
		gamepadMethod2 = "2";
		gamepadSettings.add(gamepadMethod1);
		
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
	}

	public void hide() {

	}

	public void show() {
		
		//create tables

		final Table firstTable = new Table();
		firstTable.setFillParent(true);
		firstTable.setDebug(false);
		firstTable.setVisible(true);
		stage.addActor(firstTable);
		
		final Table modeTable = new Table();
		modeTable.setFillParent(true);
		modeTable.setDebug(false);
		modeTable.setVisible(false);
		stage.addActor(modeTable);

		final Table numberPlayersTable = new Table();
		numberPlayersTable.setFillParent(true);
		numberPlayersTable.setDebug(false);
		numberPlayersTable.setVisible(false);
		stage.addActor(numberPlayersTable);
		
		final Table Player2Table = new Table();
		Player2Table.setFillParent(true);
		Player2Table.setDebug(false);
		Player2Table.setVisible(false);
		stage.addActor(Player2Table);
		
		final Table steuerung = new Table();
		steuerung.setFillParent(true);
		steuerung.setDebug(false);
		steuerung.setVisible(false);
		stage.addActor(steuerung);
		
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		//buttons, labels etc
		 
		TextButton startWithKeyboardAndMouse = new TextButton("Start with Keyboard and Mouse", skin);
		TextButton startWithKeyboard = new TextButton("Start with Keyboard", skin);
		TextButton startWithGamepad = new TextButton("Start with Gamepad", skin);
		TextButton preferences = new TextButton("Preferences", skin);
		TextButton exit = new TextButton("Exit", skin);
		TextButton freeForAll = new TextButton("Free for all", skin);
		TextButton zombie = new TextButton("Zombie", skin);
		TextButton singleplayer = new TextButton("Singleplayer", skin);
		TextButton coop = new TextButton("Coop", skin);
		TextButton player2keyboard = new TextButton("Player2 with keyboard", skin);
		TextButton player2kmouse = new TextButton("Player2 with keyboard and Mouse", skin);
		TextButton player2gamepad = new TextButton("Player2 with Gamepad", skin);
		TextButton modeTableToMenu = new TextButton("Back", skin);
		TextButton numberPlayersTableToModeTable = new TextButton("Back", skin);
		TextButton steuerungToMenu = new TextButton("Back", skin);
		
		final CheckBox gamepad1 = new CheckBox(gamepadMethod1, skin);
		final CheckBox gamepad2 = new CheckBox(gamepadMethod2, skin);

		final TextField keyboardUp = new TextField("W", skin);
		final TextField keyboardDown = new TextField("S", skin);
		final TextField keyboardLeft = new TextField("A", skin);
		final TextField keyboardRight = new TextField("D", skin);
		final TextField keyboardLeftTurn = new TextField("O", skin);
		final TextField keyboardRightTurn = new TextField("P", skin);
		final TextField keyboardShoot = new TextField("Space", skin);
		
		final TextField mouseUp = new TextField("H", skin);
		final TextField mouseDown = new TextField("N", skin);
		final TextField mouseLeft = new TextField("B", skin);
		final TextField mouseRight = new TextField("M", skin);
		final TextField mouseLeftTurn = new TextField("Mausbewegung", skin);
		final TextField mouseRightTurn = new TextField("Mausbewegung", skin);
		final TextField mouseShoot = new TextField("Linksklick", skin);
		
		final TextField player1 = new TextField("1", skin);
		final TextField player2 = new TextField("2", skin);
		final TextField ki1 = new TextField("3", skin);
		final TextField ki2 = new TextField("4", skin);
		final TextField ki3 = new TextField("5", skin);
		
		final Label keyboardLabel = new Label("Tastatur", skin);
		final Label mouseLabel = new Label("Tastatur & Maus", skin);
		final Label gamepadLabel = new Label("Gamepad", skin);
		final Label labelUp = new Label("Oben", skin);
		final Label labelDown = new Label("Unten", skin);
		final Label labelLeft = new Label("Links", skin);
		final Label labelRight = new Label("Rechts", skin);
		final Label labelLeftTurn = new Label("Links drehen", skin);
		final Label labelRightTurn = new Label("Rechts drehen", skin);
		final Label labelShoot = new Label("Schiessen", skin);
		final Label platz1 = new Label(null, skin);
		final Label platz2 = new Label(null, skin);
		final Label platz3 = new Label(null, skin);
		final Label platz4 = new Label(null, skin);
		
		final Label labelPlayer1 = new Label("Player 1", skin);
		final Label labelPlayer2 = new Label("Player 2", skin);
		final Label labelKi1 = new Label("Ki 1", skin);
		final Label labelKi2 = new Label("Ki 2", skin);
		final Label labelKi3 = new Label("Ki 3", skin);
		final Label labelTeam = new Label("Teams 1-5", skin);
		
		
		//design tables
		
		numberPlayersTable.add(singleplayer).fillX();
		numberPlayersTable.row().pad(10, 0, 10, 0);
		numberPlayersTable.add(coop).fillX();
		numberPlayersTable.row().pad(10, 0, 10, 0);
		numberPlayersTable.add(numberPlayersTableToModeTable).fillX();

		modeTable.add(freeForAll).fillX();
		modeTable.row().pad(10, 0, 10, 0);
		modeTable.add(zombie).fillX();
		modeTable.row().pad(10, 0, 10, 0);
		modeTable.add(modeTableToMenu).fillX();

		Player2Table.add(player2keyboard).fillX();
		Player2Table.row().pad(10, 0, 10, 0);
		Player2Table.add(player2kmouse).fillX();
		Player2Table.row();
		Player2Table.add(player2gamepad).fillX();
		
		firstTable.add(startWithKeyboard).fillX();
		firstTable.row().pad(10, 0, 10, 0);
		firstTable.add(startWithKeyboardAndMouse).fillX();
		firstTable.row();
		firstTable.add(startWithGamepad).fillX();
		firstTable.row().pad(10, 0, 10, 0);
		firstTable.add(preferences).fillX();
		firstTable.row();
		firstTable.add(exit).fillX();
		//zeile 1
		steuerung.add(platz1);
		steuerung.add(keyboardLabel);
		steuerung.add(mouseLabel);
		steuerung.add(gamepadLabel);
		steuerung.add(labelTeam).colspan(2);
		//zeile 2
		steuerung.row().pad(10, 0, 10, 0);
		steuerung.add(labelUp);
		steuerung.add(keyboardUp).fillX();
		steuerung.add(mouseUp).fillX();
		steuerung.add(gamepad1);
		steuerung.add(labelPlayer1);
		steuerung.add(player1);
		//zeile 3
		steuerung.row().pad(10, 0, 10, 0);
		steuerung.add(labelDown);
		steuerung.add(keyboardDown).fillX();
		steuerung.add(mouseDown).fillX();
		steuerung.add(gamepad2);
		steuerung.add(labelPlayer2);
		steuerung.add(player2);
		//zeile 4
		steuerung.row().pad(10, 0, 10, 0);
		steuerung.add(labelLeft);
		steuerung.add(keyboardLeft).fillX();
		steuerung.add(mouseLeft).fillX();
		steuerung.add(platz2);
		steuerung.add(labelKi1);
		steuerung.add(ki1);
		//zeile 5
		steuerung.row().pad(10, 0, 10, 0);
		steuerung.add(labelRight);
		steuerung.add(keyboardRight).fillX();
		steuerung.add(mouseRight).fillX();
		steuerung.add(platz3);
		steuerung.add(labelKi2);
		steuerung.add(ki2);
		//zeile 6
		steuerung.row().pad(10, 0, 10, 0);
		steuerung.add(labelLeftTurn);
		steuerung.add(keyboardLeftTurn).fillX();
		steuerung.add(mouseLeftTurn).fillX();
		steuerung.add(platz4);
		steuerung.add(labelKi3);
		steuerung.add(ki3);
		//zeile 7
		steuerung.row().pad(10, 0, 10, 0);
		steuerung.add(labelRightTurn);
		steuerung.add(keyboardRightTurn).fillX();
		steuerung.add(mouseRightTurn).fillX();
		//zeile 8
		steuerung.row().pad(10, 0, 10, 0);
		steuerung.add(labelShoot);
		steuerung.add(keyboardShoot).fillX();
		steuerung.add(mouseShoot).fillX();
		//zeile 9
		steuerung.row().pad(10, 0, 10, 0);
		steuerung.add(steuerungToMenu).fillX().colspan(6);
		
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
			}
		});
		
		//Player1
		startWithKeyboard.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				keyboardSettings.set(0, keyboardUp.getText());
				keyboardSettings.set(1, keyboardLeft.getText());
				keyboardSettings.set(2, keyboardDown.getText());
				keyboardSettings.set(3, keyboardRight.getText());
				keyboardSettings.set(4, keyboardLeftTurn.getText());
				keyboardSettings.set(5, keyboardRightTurn.getText());
				keyboardSettings.set(6, keyboardShoot.getText());
				inputPlayer1=1;
				firstTable.setVisible(false);
				modeTable.setVisible(true);		
			}
		});
		startWithKeyboardAndMouse.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				mouseSettings.set(0, mouseUp.getText());
				mouseSettings.set(1, mouseLeft.getText());
				mouseSettings.set(2, mouseDown.getText());
				mouseSettings.set(3, mouseRight.getText());
				inputPlayer1=2;
				firstTable.setVisible(false);
				modeTable.setVisible(true);				
			}
		});
		startWithGamepad.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(gamepad1.isChecked() && gamepad2.isChecked() == false) {
					gamepadSettings.set(0, gamepadMethod1);
				}else if(gamepad2.isChecked() && gamepad1.isChecked() == false) {
					gamepadSettings.set(0, gamepadMethod2);
				}
				inputPlayer1=3;
				firstTable.setVisible(false);
				modeTable.setVisible(true);	

			}
		});
		
		//Modi
		freeForAll.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				modeTable.setVisible(false);
				numberPlayersTable.setVisible(true);
				
			}
		});
		zombie.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				survivalMode =true;
				if(inputPlayer1==1)
					game.setScreen(new Survival(game, "keyboard", keyboardSettings, emptySettings));
				if(inputPlayer1==2)
						game.setScreen(new Survival(game, "maus", mouseSettings, emptySettings));
				if(inputPlayer1==3)
						game.setScreen(new Survival(game, "gamepad", gamepadSettings, emptySettings));
			}
		});
		
		//freeForAll-Singleplayer
		singleplayer.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(inputPlayer1==1)
					game.setScreen(new Deathmatch(game, "keyboard", keyboardSettings, emptySettings));
				if(inputPlayer1==2)
					game.setScreen(new Deathmatch(game, "maus", mouseSettings, emptySettings));
				if(inputPlayer1==3)
					game.setScreen(new Deathmatch(game, "gamepad", gamepadSettings, emptySettings));
			}
		});
		coop.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				numberPlayersTable.setVisible(false);
				Player2Table.setVisible(true);
				multiplayer = true;
			}
		});
		
		//Player2
		player2keyboard.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				inputPlayer2 = 1;
				keyboardSettings.set(0, keyboardUp.getText());
				keyboardSettings.set(1, keyboardLeft.getText());
				keyboardSettings.set(2, keyboardDown.getText());
				keyboardSettings.set(3, keyboardRight.getText());
				keyboardSettings.set(4, keyboardLeftTurn.getText());
				keyboardSettings.set(5, keyboardRightTurn.getText());
				keyboardSettings.set(6, keyboardShoot.getText());
				if(inputPlayer1 == inputPlayer2){
					System.out.println("Die Panzer brauchen andere steuerungen");
				}
				else{
				if(inputPlayer1 == 2 && inputPlayer2 == 1)
					game.setScreen(new Deathmatch(game, "maus", mouseSettings, keyboardSettings));
				if(inputPlayer1 == 3 && inputPlayer2 == 1)
					game.setScreen(new Deathmatch(game, "gamepad", gamepadSettings, keyboardSettings));
			}}
		});
		player2kmouse.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				inputPlayer2 = 2;
				mouseSettings.set(0, mouseUp.getText());
				mouseSettings.set(1, mouseDown.getText());
				mouseSettings.set(2, mouseLeft.getText());
				mouseSettings.set(3, mouseRight.getText());
				mouseSettings.set(4, mouseLeftTurn.getText());
				mouseSettings.set(5, mouseRightTurn.getText());
				if(inputPlayer1== inputPlayer2){
					System.out.println("Die Panzer brauchen andere steuerungen");
				}
				else{
					if(inputPlayer1 == 1 && inputPlayer2 == 2)
						game.setScreen(new Deathmatch(game, "keyboard", keyboardSettings, mouseSettings));
					if(inputPlayer1 == 3 && inputPlayer2 == 2)
						game.setScreen(new Deathmatch(game, "gamepad", gamepadSettings, mouseSettings));	
			}}
		});
		player2gamepad.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				inputPlayer2 = 3;
				if(inputPlayer1== inputPlayer2){
					System.out.println("Die Panzer brauchen andere steuerungen");
				}
				else{
					if(inputPlayer1 == 1 && inputPlayer2 == 3)
						game.setScreen(new Deathmatch(game, "keyboard", keyboardSettings, gamepadSettings));
					if(inputPlayer1 == 2 && inputPlayer2 == 3)
						game.setScreen(new Deathmatch(game,"keyboard", mouseSettings, gamepadSettings));
			}}
		});
		
		//Back-Buttons
		modeTableToMenu.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				modeTable.setVisible(false);
				firstTable.setVisible(true);
			}
		});
		
		numberPlayersTableToModeTable.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				numberPlayersTable.setVisible(false);
				modeTable.setVisible(true);
			}
		});
		steuerungToMenu.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				teams.set(0, player1.getText());
				teams.set(1, player2.getText());
				teams.set(2, ki1.getText());
				teams.set(3, ki2.getText());
				teams.set(4, ki3.getText());
				
				
				if(player1.getText().length() != 0 && player2.getText().length() != 0 && ki1.getText().length() != 0 && ki2.getText().length() != 0 && ki3.getText().length() != 0) {
					if(gamepad1.isChecked() && gamepad2.isChecked()) {
						System.out.println("only use 1 Gamepad checkbox");
					}
					else{
					steuerung.setVisible(false);	
					firstTable.setVisible(true);
					}
				}
			}
		});
	}

	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	public void dispose() {
		stage.dispose();
	}
}

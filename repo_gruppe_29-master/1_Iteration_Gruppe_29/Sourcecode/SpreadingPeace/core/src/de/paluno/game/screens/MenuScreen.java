package de.paluno.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import de.paluno.game.Constants;
import de.paluno.game.SEPgame;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

//    This Screen should be shown when the game starts and allow the player to choose his input method. Once the player has done so, it should allow him to start the game by instantiating a new PlayScreen, passing the chosen input method along to its constructor.

public class MenuScreen extends com.badlogic.gdx.ScreenAdapter implements Screen {
	// Instantiates a new MenuScreen. As with the PlayScreen, this constructor
	// should not be used to actually create the menu structure. Such belongs in the
	// show-method which is automatically called once after the Screen is registered
	// with the Game instance.

	private SEPgame game;
	Skin skin = new Skin(Gdx.files.internal("uiskin.json")); // wird f�r die Checkboxen ben�tigt, skindatei kann gerne
																// ge�ndert werden
	private com.badlogic.gdx.scenes.scene2d.ui.CheckBox keybButton;
	private com.badlogic.gdx.scenes.scene2d.ui.CheckBox gpadButton;
	private com.badlogic.gdx.scenes.scene2d.ui.CheckBox mouseButton;
	private com.badlogic.gdx.scenes.scene2d.ui.CheckBox start;
	private com.badlogic.gdx.scenes.scene2d.ui.CheckBox ende;
	private com.badlogic.gdx.scenes.scene2d.ui.Window window;

	// game - The instance of SEPGame.
	// private
	// com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup<com.badlogic.gdx.scenes.scene2d.ui.CheckBox>
	// inputMethod; <- war von Anfang an vorhanden, gibt jedoch nur fehler beim
	// benutzen, daher auskommentiert
	private com.badlogic.gdx.scenes.scene2d.Stage stage; // wird f�r die Buttons zum funktionieren ben�tigt

	public MenuScreen(SEPgame game) {
		this.game = game;
		stage = new com.badlogic.gdx.scenes.scene2d.Stage(new ScreenViewport());
		keybButton = new com.badlogic.gdx.scenes.scene2d.ui.CheckBox("Tastatur", skin);
		gpadButton = new com.badlogic.gdx.scenes.scene2d.ui.CheckBox("Gamepad", skin);
		mouseButton = new com.badlogic.gdx.scenes.scene2d.ui.CheckBox("Tastatur + Maus", skin);
		start = new com.badlogic.gdx.scenes.scene2d.ui.CheckBox("Start", skin);
		ende = new com.badlogic.gdx.scenes.scene2d.ui.CheckBox("Ende", skin);
		window = new Window("Menu", skin);
	}

	public void hide() {

	}

	public void show() {
		Gdx.input.setInputProcessor(stage);
		keybButton.getCells().get(0).size(50, 50); // bestimmt die gr��e der Box uum die Checkboxen herum, bisher keine
													// m�glichkeit entdeckt, die Checkboxen AN SICH zu vergr��ern
		gpadButton.getCells().get(0).size(50, 50);
		mouseButton.getCells().get(0).size(50, 50);
		start.getCells().get(0).size(50, 50);
		ende.getCells().get(0).size(50, 50);
		window.setPosition(Constants.BILDSCHIRMBREITE / 3, Constants.BILDSCHIRMHOEHE / 2, 100);
		window.add(keybButton);
		window.add(gpadButton);
		window.add(mouseButton);
		window.add(start);
		window.add(ende);
		window.pack();
		stage.addActor(window);
		this.start();
		this.ende();
	}

	public void start() {
		if (keybButton.isChecked() != mouseButton.isChecked() != gpadButton.isChecked()) {
			if (start.isChecked()) {
				if (keybButton.isChecked() == true) {
					game.setScreen(new PlayScreen(game, "keyboard"));
				} else if (gpadButton.isChecked() == true) {
					game.setScreen(new PlayScreen(game, "gamepad"));
				} else if (mouseButton.isChecked() == true) {
					game.setScreen(new PlayScreen(game, "maus"));
				}
			}
		}
	}

	public void ende() {
		if (ende.isChecked()) {
			Gdx.app.exit();
		}
	}

	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	public void render(float delta) {
		Texture background = new Texture("wall-blurred.jpg");
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.draw(background, 0, 0, Constants.BILDSCHIRMBREITE, Constants.BILDSCHIRMHOEHE);
		this.show();
		game.batch.end();
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
		stage.draw();
	}

	public void dispose() {
		stage.dispose();
		skin.dispose();
	}

}

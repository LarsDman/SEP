package de.paluno.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import de.paluno.game.SEPgame;
import de.paluno.game.gameobjects.Colour;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;




public class ErrorScreen extends com.badlogic.gdx.ScreenAdapter implements Screen {

	private SEPgame game;
	private Desktop desktop;

	private Stage stage;

	private Skin skin;
	private BitmapFont font;
	private float timestage;
	private float thr=300f;
	private float thrft=350;
	
	private Texture florianimg, joelimg, niklasimg, cedricimg, robinimg, larsimg, dummyimg;
	
	public ErrorScreen(SEPgame game) {
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("failure.mp3"));
		sound.play(1f);
		this.game = game;
		timestage=0;
		font = new BitmapFont();
		skin = new Skin(Gdx.files.internal("uiskin.json"));		
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		florianimg= new Texture("florian.png");
		joelimg = new Texture("joel.jpeg");
		cedricimg = new Texture("cedric.jpeg");
		niklasimg = new Texture("niklas.jpeg");
		robinimg = new Texture("robin.jpeg");
		larsimg = new Texture("lars.jpeg");
		dummyimg = new Texture("1x1.png");
	}

	public void hide() {

	}

	public void show() {
		
		TextButton florian = new TextButton("Florian", skin);
		TextButton cedric = new TextButton("Cedric", skin);
		TextButton joel = new TextButton("Joel", skin);
		TextButton niklas = new TextButton("Niklas", skin);
		TextButton robin = new TextButton("Robin", skin);
		TextButton lars = new TextButton("Lars", skin);
		
		florian.moveBy(100, 260);
		cedric.moveBy(450, 260);
		joel.moveBy(800, 260);
		niklas.moveBy(100, 10);
		robin.moveBy(450, 10);
		lars.moveBy(800, 10);
		
		stage.addActor(florian);
		stage.addActor(cedric);
		stage.addActor(joel);
		stage.addActor(niklas);
		stage.addActor(robin);
		stage.addActor(lars);
		
		
		florian.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.print("Exmatrikulation erfolgreich. Florian kriegt bald Post vom Arbeitsamt");
				if (Desktop.isDesktopSupported()&&(desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
					try {
						desktop.mail(new URI("mailto:florian.ruehl@stud.uni-due.de?subject=SEP-rauswurf"));
					} catch (IOException e) {
						e.printStackTrace();
						Gdx.app.exit();	
					} catch (URISyntaxException e) {
						e.printStackTrace();
						Gdx.app.exit();	
					}
				}
				Gdx.app.exit();				
			}
		});
		cedric.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.print("Gut. Cedric muss nun Gender-Studies studieren");
				if (Desktop.isDesktopSupported()&&(desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
					try {
						desktop.mail(new URI("mailto:cedric.juessen@stud.uni-due.de?subject=SEP-rauswurf"));
					} catch (IOException e) {
						e.printStackTrace();
						Gdx.app.exit();	
					} catch (URISyntaxException e) {
						e.printStackTrace();
						Gdx.app.exit();	
					}
				}
				Gdx.app.exit();			}
		});
		joel.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.print("Joel wird nun mit einer Karriere als Pokemon-Trainer vorlieb nehmen müssen");
				if (Desktop.isDesktopSupported()&&(desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
					try {
						desktop.mail(new URI("mailto:joel.schneider@stud.uni-duisburg-essen.de?subject=SEP-rauswurf"));
					} catch (IOException e) {
						e.printStackTrace();
						Gdx.app.exit();	
					} catch (URISyntaxException e) {
						e.printStackTrace();
						Gdx.app.exit();	
					}
				}
				Gdx.app.exit();			}
		});
		niklas.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.print("Druecke F um respekt zu zollen");
				if (Desktop.isDesktopSupported()&&(desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
					try {
						desktop.mail(new URI("mailto:niklas.schiller@stud.uni-due.de?subject=SEP-rauswurf"));
					} catch (IOException e) {
						e.printStackTrace();
						Gdx.app.exit();	
					} catch (URISyntaxException e) {
						e.printStackTrace();
						Gdx.app.exit();	
					}
				}
				Gdx.app.exit();			}
		});
		robin.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.print("Na ja, vielleicht wird es was mit der Lol-Streamer Karriere");
				if (Desktop.isDesktopSupported()&&(desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
					try {
						desktop.mail(new URI("mailto:robin.rongen@stud.uni-due.de?subject=SEP-rauswurf"));
					} catch (IOException e) {
						e.printStackTrace();
						Gdx.app.exit();	
					} catch (URISyntaxException e) {
						e.printStackTrace();
						Gdx.app.exit();	
					}
				}
				Gdx.app.exit();			}
		});
		lars.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.print("Anscheinend waren die 50 Mio. commits nicht genug. RIP.");
				if (Desktop.isDesktopSupported()&&(desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
					try {
						desktop.mail(new URI("mailto:lars.daumann@stud.uni-due.de?subject=SEP-rauswurf"));
					} catch (IOException e) {
						e.printStackTrace();
						Gdx.app.exit();	
					} catch (URISyntaxException e) {
						e.printStackTrace();
						Gdx.app.exit();	
					}
				}
				Gdx.app.exit();			}
		});
		
	}

	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	public void render(float delta) {
		game.batch.begin();
		Gdx.gl.glClearColor(0f, 0f, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(timestage<10)
		{

			font.draw(game.batch, "Es gab einen Fehler im Code. Das ist unakzeptabel !!", thr, thrft);
		}
		if(timestage>5&&timestage<10)
		{
			font.draw(game.batch, "Einer aus der gruppe muss dafür bestraft werden", thr, thr);

		}
		
		if(timestage>10)
		{

			font.getData().setScale(2f);
			font.draw(game.batch, "Wählen Sie aus, wer SEP nicht bestehen soll", 210, 530);
			stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
			game.batch.draw(florianimg,	30, 290, 200, 200);
			game.batch.draw(joelimg,    720, 290, 200, 200);
			game.batch.draw(cedricimg,	380, 290, 200, 200);
			game.batch.draw(niklasimg,	30, 40, 200, 200);
			game.batch.draw(robinimg,	380, 40, 200, 200);
			game.batch.draw(larsimg,	720, 40, 200, 200);
			game.batch.draw(dummyimg,	1000, 1000, 1, 1);
			stage.draw();
		}
		game.batch.end();
		timestage+=Gdx.graphics.getDeltaTime();	
	}
	
	public void dispose() {
		stage.dispose();
	}

}

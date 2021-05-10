package de.paluno.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import de.paluno.game.Constants;
import de.paluno.game.GameState;
import de.paluno.game.SEPgame;

public abstract class Hud{
	protected Stage stage;//Gamemode
	protected Viewport vport;
	protected Gamemode playScreen;
	
	protected int time;
	protected long stopTime, stopTableTimer;
	protected float timeCountStart;//timer zum Starten
	protected boolean ready;//tanks werden geupdatet wenn ready true ist
	protected String goOnClickString, stopStringKeyboard, stopStringMouse, stopStringGamepad;
	protected Table tableUp, tableStop, tableFlowers;
	
	//Lables für Tables
	protected Label countdownLabel, levelLabel, gamemodeLabel, stopLabel;//Label für TableUp
	protected Label pauseLabel, leaveClickLabel, goOnClickLabel;//Label für Pause
	protected Label normalFlowerLabel, fastFlowerLabel, bounceFlowerLabel, splittedFlowerLabel;//Label für Pause
	protected Label flowerEffectLabel, flowerTextLabel, damageLabel;//Label für Pause
	
	//Images & Tables für Pause
	protected Texture damage1Tex = new Texture("Leben_100%.png");
	protected Texture damage2Tex = new Texture("Leben_50%.png");
	protected Texture damage3Tex = new Texture("Leben_30%.png");
	protected Image nfImg = new Image(new Texture("Auswahl_Normale_Blume.png"));
	protected Image ffImg = new Image(new Texture("Auswahl_schnelle_Blume.png"));
	protected Image bfImg = new Image(new Texture("Auswahl_Springende_Blume.png"));
	protected Image sfImg = new Image(new Texture("Auswahl_Geteilte_Blume.png"));
	protected Image damageImg = new Image(damage1Tex);//wichtig, da Damages geupdatet werden
	
	protected Texture second1Tex, second2Tex, second3Tex, goTex;
	protected Image second1Img, second2Img, second3Img, goImg;
	
	protected int imageWidth, imageHeight;
	protected SEPgame game;
	
	public Hud(SpriteBatch sb, Gamemode playScreen, SEPgame game) {
		this.playScreen = playScreen;
		this.game = game;
		stopTableTimer = 1000;//1 Sekunde
		time = 4;
		timeCountStart = 0;
		
		setReady(false);
		
		vport = new FitViewport(Gamemode.width, Gamemode.height);
		stage = new Stage(vport, sb);
		
		tableUp = new Table();
		gamemodeLabel = new Label("Gamemode: " + MenuScreen.modename, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		levelLabel =  new Label(MenuScreen.mapToShow, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		tableStop = new Table();
		if(MenuScreen.multiplayer == false) {
			if(MenuScreen.inputPlayer1 == PlayerInput.GAMEPAD) {
				stopStringGamepad = playScreen.getSettings().get(6);
				stopLabel = new Label("Press " + stopStringGamepad + " to pause", new Label.LabelStyle(new BitmapFont(), Color.RED));
				goOnClickLabel = new Label("Click here or press " + stopStringGamepad + " to go on", new Label.LabelStyle(new BitmapFont(), Color.RED));
			}else {
				stopStringKeyboard = playScreen.getSettings().get(11);
				goOnClickString = playScreen.getSettings().get(11);
				stopLabel = new Label("Press " + stopStringKeyboard + " to pause", new Label.LabelStyle(new BitmapFont(), Color.RED));
				goOnClickLabel = new Label("Click here or press " + stopStringKeyboard + " to go on", new Label.LabelStyle(new BitmapFont(), Color.RED));
			}
			
		}else {
			if(MenuScreen.inputPlayer1 == PlayerInput.KEYBOARD && MenuScreen.inputPlayer2 == PlayerInput.MOUSE) {//Keyboard & Maus
				stopStringKeyboard = playScreen.getSettings().get(11);
				stopStringMouse = playScreen.getSettings2().get(11);
				stopLabel = new Label("Press " + stopStringKeyboard + " or " + stopStringMouse + " to pause", new Label.LabelStyle(new BitmapFont(), Color.RED));
				goOnClickLabel = new Label("Click here or press " + stopStringKeyboard + " or " + stopStringMouse + " to go on", new Label.LabelStyle(new BitmapFont(), Color.RED));
			}else if(MenuScreen.inputPlayer1 == PlayerInput.KEYBOARD && MenuScreen.inputPlayer2 == PlayerInput.GAMEPAD) {//Keyboard & Gamepad
				stopStringKeyboard = playScreen.getSettings().get(11);
				stopStringGamepad = playScreen.getSettings2().get(6);
				stopLabel = new Label("Press " + stopStringKeyboard + " or " + stopStringGamepad + " to pause", new Label.LabelStyle(new BitmapFont(), Color.RED));
				goOnClickLabel = new Label("Click here or press " + stopStringKeyboard + " or " + stopStringGamepad + " to go on", new Label.LabelStyle(new BitmapFont(), Color.RED));
			}else if(MenuScreen.inputPlayer1 == PlayerInput.MOUSE && MenuScreen.inputPlayer2 == PlayerInput.KEYBOARD) {//Maus & Keyboard
				stopStringMouse = playScreen.getSettings().get(11);
				stopStringKeyboard = playScreen.getSettings2().get(11);
				stopLabel = new Label("Press " + stopStringMouse + " or " + stopStringKeyboard + " to pause", new Label.LabelStyle(new BitmapFont(), Color.RED));
				goOnClickLabel = new Label("Click here or press " + stopStringMouse + " or " + stopStringKeyboard + " to go on", new Label.LabelStyle(new BitmapFont(), Color.RED));
			}else if(MenuScreen.inputPlayer1 == PlayerInput.MOUSE && MenuScreen.inputPlayer2 == PlayerInput.GAMEPAD) {//Maus & Gamepad
				stopStringMouse = playScreen.getSettings().get(11);
				stopStringGamepad = playScreen.getSettings2().get(6);
				stopLabel = new Label("Press " + stopStringMouse + " or " + stopStringGamepad + " to pause", new Label.LabelStyle(new BitmapFont(), Color.RED));
				goOnClickLabel = new Label("Click here or press " + stopStringMouse + " or " + stopStringGamepad + " to go on", new Label.LabelStyle(new BitmapFont(), Color.RED));
			}else if(MenuScreen.inputPlayer1 == PlayerInput.GAMEPAD && MenuScreen.inputPlayer2 == PlayerInput.KEYBOARD) {//Gamepad & Keyboard
				stopStringGamepad = playScreen.getSettings().get(6);
				stopStringKeyboard = playScreen.getSettings2().get(11);
				stopLabel = new Label("Press " + stopStringGamepad + " or " + stopStringKeyboard + " to pause", new Label.LabelStyle(new BitmapFont(), Color.RED));
				goOnClickLabel = new Label("Click here or press " + stopStringGamepad + " or " + stopStringKeyboard + " to go on", new Label.LabelStyle(new BitmapFont(), Color.RED));
			}else if(MenuScreen.inputPlayer1 == PlayerInput.GAMEPAD && MenuScreen.inputPlayer2 == PlayerInput.MOUSE) {//Gamepad & Maus
				stopStringGamepad = playScreen.getSettings().get(6);
				stopStringMouse = playScreen.getSettings2().get(11);
				stopLabel = new Label("Press " + stopStringGamepad + " or " + stopStringMouse + " to pause", new Label.LabelStyle(new BitmapFont(), Color.RED));
				goOnClickLabel = new Label("Click here or press " + stopStringGamepad + " or " + stopStringMouse + " to go on", new Label.LabelStyle(new BitmapFont(), Color.RED));
			}
		}
		
		pauseLabel = new Label("Pause", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		leaveClickLabel = new Label("Click here to leave", new Label.LabelStyle(new BitmapFont(), Color.RED));
		
		imageWidth = 50;
		imageHeight = 50;
		
		second1Tex = new Texture("Hud_3.png");
		second1Img = new Image(second1Tex);
		
		second2Tex = new Texture("Hud_2.png");
		second2Img = new Image(second2Tex);
		
		second3Tex = new Texture("Hud_1.png");
		second3Img = new Image(second3Tex);
		
		goTex = new Texture("Hud_Go.png");
		goImg = new Image(goTex);
		
		Gdx.input.setInputProcessor(stage);
	}
	
	public abstract void update(float delta);
	
	public abstract void show();
	
	public abstract void createTableUp();
	
	public void actions() {
		second1Img.setWidth(imageWidth);
		second1Img.setHeight(imageHeight);
		second1Img.setPosition(-imageWidth, Constants.BILDSCHIRMHOEHE/2 - (imageHeight/2));
		second2Img.setWidth(imageWidth);
		second2Img.setHeight(imageHeight);
		second2Img.setPosition(-imageWidth, Constants.BILDSCHIRMHOEHE/2 - (imageHeight/2));	
		second3Img.setWidth(imageWidth);
		second3Img.setHeight(imageHeight);
		second3Img.setPosition(-imageWidth, Constants.BILDSCHIRMHOEHE/2 - (imageHeight/2));
		goImg.setWidth(imageWidth);
		goImg.setHeight(imageHeight);
		goImg.setPosition(-imageWidth, Constants.BILDSCHIRMHOEHE/2 - (imageHeight/2));
		
		second1Img.addAction(Actions.delay(0, Actions.sequence(Actions.parallel(Actions.scaleTo(2,2,0.5f), Actions.moveTo(Constants.BILDSCHIRMBREITE/2,Constants.BILDSCHIRMHOEHE/2-(imageHeight/2), 0.5f)),
				(Actions.parallel(Actions.scaleTo(1,1,0.5f), Actions.moveTo(Constants.BILDSCHIRMBREITE-imageWidth,Constants.BILDSCHIRMHOEHE/2-(imageHeight/2), 0.5f))), Actions.removeActor())));
		
		second2Img.addAction(Actions.delay(1, Actions.sequence(Actions.parallel(Actions.scaleTo(2,2,0.5f), Actions.moveTo(Constants.BILDSCHIRMBREITE/2,Constants.BILDSCHIRMHOEHE/2-(imageHeight/2), 0.5f)),
				(Actions.parallel(Actions.scaleTo(1,1,0.5f), Actions.moveTo(Constants.BILDSCHIRMBREITE-imageWidth,Constants.BILDSCHIRMHOEHE/2-(imageHeight/2), 0.5f))), Actions.removeActor())));
		
		second3Img.addAction(Actions.delay(2, Actions.sequence(Actions.parallel(Actions.scaleTo(2,2,0.5f), Actions.moveTo(Constants.BILDSCHIRMBREITE/2,Constants.BILDSCHIRMHOEHE/2-(imageHeight/2), 0.5f)),
				(Actions.parallel(Actions.scaleTo(1,1,0.5f), Actions.moveTo(Constants.BILDSCHIRMBREITE-imageWidth,Constants.BILDSCHIRMHOEHE/2-(imageHeight/2), 0.5f))), Actions.removeActor())));
		
		goImg.addAction(Actions.delay(3, Actions.sequence(Actions.moveTo(Constants.BILDSCHIRMBREITE/2-(imageWidth/2), Constants.BILDSCHIRMHOEHE/2-(imageHeight/2)),Actions.scaleTo(1,1,1f), Actions.removeActor())));
		
		stage.addActor(second1Img);
		stage.addActor(second2Img);
		stage.addActor(second3Img);
		stage.addActor(goImg);
	}
	
	public void createStopTable() {
		stage.addActor(tableStop);
		tableStop.setFillParent(true);
		
		normalFlowerLabel = new Label("Classic Flower", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		fastFlowerLabel = new Label("Fast Flower", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		bounceFlowerLabel = new Label("Bounce Flower", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		splittedFlowerLabel = new Label("Triple Flower", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		flowerEffectLabel = new Label("Effect of Flower:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		flowerTextLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		damageLabel = new Label("Damage", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		tableStop.add(pauseLabel);
		tableStop.add(leaveClickLabel).colspan(2);
		tableStop.add(goOnClickLabel);
		
		tableStop.row();
		tableStop.add(nfImg);
		tableStop.add(normalFlowerLabel);
		tableStop.add(damageLabel);
		tableStop.add(damageImg);
		
		tableStop.row();
		tableStop.add(ffImg);
		tableStop.add(fastFlowerLabel);
		tableStop.add(flowerEffectLabel).colspan(2);
		
		tableStop.row();
		tableStop.add(bfImg);
		tableStop.add(bounceFlowerLabel);
		tableStop.add(flowerTextLabel).colspan(2).width(350);
		
		tableStop.row();
		tableStop.add(sfImg);
		tableStop.add(splittedFlowerLabel);
		
		tableStop.setTouchable(Touchable.enabled); 
	    tableStop.setVisible(false);
	}
	
	public void timerStart(float delta) {
		if(this.isReady()==false) {
			timeCountStart = timeCountStart + delta;
			if(timeCountStart >=1) {
				time = time -1;
				timeCountStart = 0;
			}
			if(time == 0) {
				this.setReady(true);
			}
		}
	}
	
	public void changePauseColor() {
		if(tableStop.isVisible()) {
			if(this.getStopTime() + stopTableTimer < System.currentTimeMillis()) {
				stopTableTimer = stopTableTimer + 1000;
				if(pauseLabel.getColor().equals(Color.WHITE)) {
					pauseLabel.setColor(Color.GREEN);
				}else if(pauseLabel.getColor().equals(Color.GREEN)) {
					pauseLabel.setColor(Color.WHITE);
				}
			}
		}else {
			stopTableTimer = 1000;
		}
	}
	
	public void stop() {
		if(playScreen.state==GameState.PAUSED) {
			tableUp.setVisible(false);
			tableStop.setVisible(true);
		}else if(playScreen.state==GameState.RUNNING) {
			tableUp.setVisible(true);
			tableStop.setVisible(false);
			this.setStopTime(System.currentTimeMillis());
		}
	}
	
	public void addStopTableListeners() {
		leaveClickLabel.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	if(playScreen.state==GameState.PAUSED) {
	        		playScreen.stopSounds();
	        		game.setScreen(new MenuScreen(game));
	        		playScreen.dispose();
	        	}
	        }
	    });
	    
	    goOnClickLabel.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	playScreen.state = GameState.RUNNING;
	        }
	    });
	    
	    normalFlowerLabel.addListener(new InputListener(){   
	        @Override
	        public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
	        	normalFlowerLabel.setColor(Color.RED);
				flowerTextLabel.setText(" This flower has no effect.");
				damageImg.setDrawable(new TextureRegionDrawable(new TextureRegion(damage1Tex)));
	    	}
	        public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor) {
	        	normalFlowerLabel.setColor(Color.WHITE);
				flowerTextLabel.setText(null);
				damageImg.setDrawable(new TextureRegionDrawable(new TextureRegion(damage1Tex)));
	    	}
	        
	    });
	    
	    fastFlowerLabel.addListener(new InputListener(){
	    	@Override
	        public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
	    		fastFlowerLabel.setColor(Color.RED);
				flowerTextLabel.setText(" This flower is fast but does less damage.");
				damageImg.setDrawable(new TextureRegionDrawable(new TextureRegion(damage2Tex)));
	    	}
	        public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				fastFlowerLabel.setColor(Color.WHITE);
				flowerTextLabel.setText(null);
				damageImg.setDrawable(new TextureRegionDrawable(new TextureRegion(damage2Tex)));
	    	}
	    });
	    
	    bounceFlowerLabel.addListener(new InputListener(){
	    	@Override
	        public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				bounceFlowerLabel.setColor(Color.RED);
				flowerTextLabel.setText(" This flower is bounces two times before it explodes.");
				damageImg.setDrawable(new TextureRegionDrawable(new TextureRegion(damage2Tex)));
	    	}
	        public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				bounceFlowerLabel.setColor(Color.WHITE);
				flowerTextLabel.setText(null);
				damageImg.setDrawable(new TextureRegionDrawable(new TextureRegion(damage2Tex)));
	    	}
	    });
	    
	    splittedFlowerLabel.addListener(new InputListener(){
	    	@Override
	        public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				splittedFlowerLabel.setColor(Color.RED);
				flowerTextLabel.setText(" This flower splits up into three parts.");
				damageImg.setDrawable(new TextureRegionDrawable(new TextureRegion(damage3Tex)));
	    	}
	        public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				splittedFlowerLabel.setColor(Color.WHITE);
				flowerTextLabel.setText(null);
				damageImg.setDrawable(new TextureRegionDrawable(new TextureRegion(damage3Tex)));
	    	}
	    });
	}
	
	public int getWorldTimer() {
		return time;
	}

	public void setWorldTimer(int worldTimer) {
		this.time = worldTimer;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public long getStopTime() {
		return stopTime;
	}

	public void setStopTime(long stopTime) {
		this.stopTime = stopTime;
	}
}

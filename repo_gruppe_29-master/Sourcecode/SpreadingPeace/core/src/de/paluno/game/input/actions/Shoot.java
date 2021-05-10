package de.paluno.game.input.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import de.paluno.game.gameobjects.F_L_O_W_E_R;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.screens.Gamemode;

public class Shoot extends Action {

	protected Gamemode playScreen;

	public Shoot(Tank actor, Gamemode playScreen) {
		super(actor);
		this.playScreen = playScreen;
	}

	public void act(float delta) {
		if (actor.getLastTimeShot() < System.currentTimeMillis()) {
			

			if(actor.getBody().isActive()==true) {
			F_L_O_W_E_R flower = actor.useFlower();
			this.playScreen.registerAfterUpdate(flower);
			}
			
			actor.setLastTimeShot(System.currentTimeMillis());
			Sound sound = Gdx.audio.newSound(Gdx.files.internal("tankfire.mp3"));
			sound.play(1.0f);
		}
	}
}

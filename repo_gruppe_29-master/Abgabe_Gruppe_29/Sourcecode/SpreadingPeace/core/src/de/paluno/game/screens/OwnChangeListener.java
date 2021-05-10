package de.paluno.game.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import de.paluno.game.gameobjects.Colour;

public class OwnChangeListener extends ChangeListener{

	private MenuScreen ms;
	
	public OwnChangeListener(MenuScreen menuscreen) {
		this.ms = menuscreen;
	}
	@Override
	public void changed(ChangeEvent event, Actor actor) {
		
	}
	
	public void updateMouseSettings() {
		ms.getMouseSettings().set(0, ms.mouseUp.getText());
		ms.getMouseSettings().set(1, ms.mouseLeft.getText());
		ms.getMouseSettings().set(2, ms.mouseDown.getText());
		ms.getMouseSettings().set(3, ms.mouseRight.getText());
		ms.getMouseSettings().set(4, ms.mouseLeftTurn.getText());
		ms.getMouseSettings().set(5, ms.mouseRightTurn.getText());
		ms.getMouseSettings().set(6, ms.mouseShoot.getText());
		ms.getMouseSettings().set(7, ms.mouseNormalFlower.getText());
		ms.getMouseSettings().set(8, ms.mouseFastFlower.getText());
		ms.getMouseSettings().set(9, ms.mouseBounceFlower.getText());
		ms.getMouseSettings().set(10, ms.mouseTripleFlower.getText());
		ms.getMouseSettings().set(11, ms.mouseStop.getText());
	}
	
	public void updateKeyboardSettings() {
		ms.getKeyboardSettings().set(0, ms.keyboardUp.getText());
		ms.getKeyboardSettings().set(1, ms.keyboardLeft.getText());
		ms.getKeyboardSettings().set(2, ms.keyboardDown.getText());
		ms.getKeyboardSettings().set(3, ms.keyboardRight.getText());
		ms.getKeyboardSettings().set(4, ms.keyboardLeftTurn.getText());
		ms.getKeyboardSettings().set(5, ms.keyboardRightTurn.getText());
		ms.getKeyboardSettings().set(6, ms.keyboardShoot.getText());
		ms.getKeyboardSettings().set(7, ms.keyboardNormalFlower.getText());
		ms.getKeyboardSettings().set(8, ms.keyboardFastFlower.getText());
		ms.getKeyboardSettings().set(9, ms.keyboardBounceFlower.getText());
		ms.getKeyboardSettings().set(10, ms.keyboardTripleFlower.getText());
		ms.getKeyboardSettings().set(11, ms.keyboardStop.getText());
	}
	
	public void updateGamepadSettings() {
		ms.getGamepadSettings().set(0, ms.gamepadDrive.getText());
		ms.getGamepadSettings().set(1, ms.gamepadAim1.getText());
		ms.getGamepadSettings().set(2, ms.gamepadAim2.getText());
		ms.getGamepadSettings().set(3, ms.gamepadFlowerUp.getText());
		ms.getGamepadSettings().set(4, ms.gamepadFlowerDown.getText());
		ms.getGamepadSettings().set(5, ms.gamepadShoot.getText());
		ms.getGamepadSettings().set(6, ms.gamepadStop.getText());
	}
	
	public void updateColourSettings() {
		if(ms.tank1Blue.isChecked()) {
			MenuScreen.colours.set(0, Colour.BLUE);
		}else if(ms.tank1Brown.isChecked()) {
			MenuScreen.colours.set(0, Colour.BROWN);
		}else if(ms.tank1Green.isChecked()) {
			MenuScreen.colours.set(0, Colour.GREEN);
		}else if(ms.tank1Pink.isChecked()) {
			MenuScreen.colours.set(0, Colour.PINK);
		}else if(ms.tank1Red.isChecked()) {
			MenuScreen.colours.set(0, Colour.RED);
		}else if(ms.tank1Yellow.isChecked()) {
			MenuScreen.colours.set(0, Colour.YELLOW);
		}
		if(ms.tank2Blue.isChecked()) {
			MenuScreen.colours.set(1, Colour.BLUE);
		}else if(ms.tank2Brown.isChecked()) {
			MenuScreen.colours.set(1, Colour.BROWN);
		}else if(ms.tank2Green.isChecked()) {
			MenuScreen.colours.set(1, Colour.GREEN);
		}else if(ms.tank2Pink.isChecked()) {
			MenuScreen.colours.set(1, Colour.PINK);
		}else if(ms.tank2Red.isChecked()) {
			MenuScreen.colours.set(1, Colour.RED);
		}else if(ms.tank2Yellow.isChecked()) {
			MenuScreen.colours.set(1, Colour.YELLOW);
		}
		if(ms.tank3Blue.isChecked()) {
			MenuScreen.colours.set(2, Colour.BLUE);
		}else if(ms.tank3Brown.isChecked()) {
			MenuScreen.colours.set(2, Colour.BROWN);
		}else if(ms.tank3Green.isChecked()) {
			MenuScreen.colours.set(2, Colour.GREEN);
		}else if(ms.tank3Pink.isChecked()) {
			MenuScreen.colours.set(2, Colour.PINK);
		}else if(ms.tank3Red.isChecked()) {
			MenuScreen.colours.set(2, Colour.RED);
		}else if(ms.tank3Yellow.isChecked()) {
			MenuScreen.colours.set(2, Colour.YELLOW);
		}
		if(ms.tank4Blue.isChecked()) {
			MenuScreen.colours.set(3, Colour.BLUE);
		}else if(ms.tank4Brown.isChecked()) {
			MenuScreen.colours.set(3, Colour.BROWN);
		}else if(ms.tank4Green.isChecked()) {
			MenuScreen.colours.set(3, Colour.GREEN);
		}else if(ms.tank4Pink.isChecked()) {
			MenuScreen.colours.set(3, Colour.PINK);
		}else if(ms.tank4Red.isChecked()) {
			MenuScreen.colours.set(3, Colour.RED);
		}else if(ms.tank4Yellow.isChecked()) {
			MenuScreen.colours.set(3, Colour.YELLOW);
		}
		if(ms.tank5Blue.isChecked()) {
			MenuScreen.colours.set(4, Colour.BLUE);
		}else if(ms.tank5Brown.isChecked()) {
			MenuScreen.colours.set(4, Colour.BROWN);
		}else if(ms.tank5Green.isChecked()) {
			MenuScreen.colours.set(4, Colour.GREEN);
		}else if(ms.tank5Pink.isChecked()) {
			MenuScreen.colours.set(4, Colour.PINK);
		}else if(ms.tank5Red.isChecked()) {
			MenuScreen.colours.set(4, Colour.RED);
		}else if(ms.tank5Yellow.isChecked()) {
			MenuScreen.colours.set(4, Colour.YELLOW);
		}
	}
	
	public void updateTeamSettings() {
		MenuScreen.teams.set(0, ms.player1Team.getText());
		MenuScreen.teams.set(1, ms.player2Team.getText());
		MenuScreen.teams.set(2, ms.ki1.getText());
		MenuScreen.teams.set(3, ms.ki2.getText());
		MenuScreen.teams.set(4, ms.ki3.getText());
	}
}

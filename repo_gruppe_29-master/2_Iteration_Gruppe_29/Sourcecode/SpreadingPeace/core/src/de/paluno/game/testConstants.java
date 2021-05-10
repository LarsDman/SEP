package de.paluno.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import de.paluno.game.SEPgame;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.input.EmptyInputProvider;
import de.paluno.game.input.actions.Turn;
import de.paluno.game.screens.Deathmatch;
public class testConstants {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		assertEquals(1920,Constants.BILDSCHIRMBREITE);
		assertEquals(-0.785398163, Constants.vectorToAngle(new Vector2(1,1)), 1);
		assertEquals(new Vector2(0,1), Constants.angleToVector(new Vector2(0,0),0f)); //methode funktioniert auch mit 0 winkelwert

	}

}

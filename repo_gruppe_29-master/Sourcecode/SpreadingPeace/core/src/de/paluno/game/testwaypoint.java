package de.paluno.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import de.paluno.game.gameobjects.Waypoint;

public class testwaypoint {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Waypoint point = new Waypoint(100,100);
		Vector2 vec = new Vector2(100,100);
		assertEquals(vec, point.getPosition());
	}

}

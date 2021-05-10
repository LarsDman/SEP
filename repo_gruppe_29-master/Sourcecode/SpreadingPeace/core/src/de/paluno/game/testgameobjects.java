package de.paluno.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import de.paluno.game.gameobjects.F_L_O_W_E_R;
import de.paluno.game.gameobjects.Tank;
import de.paluno.game.input.EmptyInputProvider;
import de.paluno.game.input.actions.Turn;
import de.paluno.game.screens.Deathmatch;
public class testgameobjects {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		SEPgame testgame = new SEPgame();
		testgame.render();
		assertNotNull(testgame);
		assertNotNull(new Tank());
		//SEPgame test2 = new SEPgame();
		//assertSame(testgame, test2);
		//SEPgame testgame = new SEPgame();
//		Deathmatch testmatch = new Deathmatch(testgame, "keyboard", new ArrayList<String>(), new ArrayList<String>());
//		F_L_O_W_E_R testflower = new F_L_O_W_E_R(testmatch, new Vector2(10,10), new Vector2(10,10) );
//		assertEquals(1000, 1000,0);
	}

}

//**
// * 
// */
//package de.paluno.game.input.actions;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import com.badlogic.gdx.math.Vector2;
//
//import de.paluno.game.SEPgame;
//import de.paluno.game.gameobjects.Tank;
//import de.paluno.game.input.EmptyInputProvider;
//import de.paluno.game.screens.Deathmatch;
//
///**
// * @author patrick
// *
// */
//public class testTurn {
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	@Test
//	public void test() {
//		SEPgame testgame = new SEPgame();
//		Deathmatch testmatch = new Deathmatch(testgame, "keyboard", new ArrayList<String>(), new ArrayList<String>());
//		Tank testtank = new Tank(testmatch, new Vector2(10,10), new EmptyInputProvider(),1, new ArrayList<String>() );
//		Turn testturn=new Turn(testtank, 1000);
//		testturn.act(1000);
//		float testvelocity=testtank.getBody().getAngularVelocity();
//		assertEquals(1000, testvelocity,0);
//	}
//
//}

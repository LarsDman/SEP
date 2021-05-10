

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import de.paluno.game.gameobjects.TestTank;
import de.paluno.game.input.actions.AimAt;

public class testAim {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void test() {
//		DrawScreen testding = new DrawScreen(new SEPgame());
//		testding.resize(100,100);
		TestTank test =new TestTank();
		AimAt testding = new AimAt(test, new Vector2(0,0));
		testding.act(1);
		int vergleich = (int)(test.getAim().x);
		assertEquals(-100, vergleich );
	}

}



package escape;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import escape.coordinate.Coordinate;
import escape.gameobserver.Observer;

public class EscapeGameSkewTest {
	
	@Test
	void testRegularSkewMovement() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/SkewTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		//E then NE
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(6, 5);
		assertTrue(manager.move(from, to));
	}
	
	@Test
	void testIncorrectSkewMovement() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/SkewTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		//E then E
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(6, 4);
		assertFalse(manager.move(from, to));
	}
	
	
}

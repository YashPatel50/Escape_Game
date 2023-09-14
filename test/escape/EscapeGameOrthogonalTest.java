package escape;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import escape.coordinate.Coordinate;
import escape.gameobserver.Observer;

public class EscapeGameOrthogonalTest {
	
	@Test
	void testRegularOrthogonalMovement() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/OrthogonalTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(2, 4);
		assertTrue(manager.move(from, to));
	}
	
	@Test
	void testRegularOrthogonalMovement2() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/OrthogonalTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(4, 1);
		assertTrue(manager.move(from, to));
	}
	
	@Test
	void testIncorrectOrthogonalMovement() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/OrthogonalTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(6, 6);
		assertFalse(manager.move(from, to));
	}
	
	@Test
	void testIncorrectOrthogonalMovementBlock() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/OrthogonalTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(4, 8);
		assertFalse(manager.move(from, to));
	}
	
}

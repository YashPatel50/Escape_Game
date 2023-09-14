package escape;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import escape.coordinate.Coordinate;
import escape.gameobserver.Observer;

public class EscapeGameDiagonalTest {
	
	@Test
	void testRegularDiagonalMovement() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/DiagonalTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(6, 6);
		assertTrue(manager.move(from, to));
	}
	
	@Test
	void testRegularDiagonalMovement2() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/DiagonalTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(3, 5);
		assertTrue(manager.move(from, to));
	}
	
	@Test
	void testIncorrectDiagonalMovement() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/DiagonalTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(4, 6);
		assertFalse(manager.move(from, to));
	}
	
	@Test
	void testIncorrectDiagonalMovementBlock() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/DiagonalTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(1, 1);
		assertFalse(manager.move(from, to));
	}
	
}

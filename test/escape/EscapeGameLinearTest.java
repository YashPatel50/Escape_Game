package escape;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import escape.coordinate.Coordinate;

public class EscapeGameLinearTest {
	
	@Test
	void testRegularLinearMovement() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/LinearTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		//Testing to move one away
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(4, 5);
		assertTrue(manager.move(from, to));
	}
	
	@Test
	void testRegularLinearMovement2() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/LinearTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		//Testing to move two away
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(4, 6);
		assertTrue(manager.move(from, to));
	}
	
	@Test
	void testRegularLinearMovement3() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/LinearTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		//Testing to move two away
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(4, 7);
		assertTrue(manager.move(from, to));
	}
	
	@Test
	void testOutOfBounds() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/LinearTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		//Testing to move out of bounds
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(15, 4);
		assertFalse(manager.move(from, to));
	}
	
	@Test
	void testNonLinear() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/LinearTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		//Testing to move out of bounds
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(5, 5);
		assertFalse(manager.move(from, to));
	}
	
	@Test
	void testNonLinear2() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/LinearTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		//Testing to move out of bounds
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(7, 3);
		assertFalse(manager.move(from, to));
	}
	
	@Test
	void testRegularLinearMovementSquare() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/LinearTestSquare.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		//Testing to move one away
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(4, 5);
		assertTrue(manager.move(from, to));
	}
	
	@Test
	void testRegularLinearMovement2Square() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/LinearTestSquare.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		//Testing to move two away
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(4, 6);
		assertTrue(manager.move(from, to));
	}
	
	@Test
	void testRegularLinearMovement3Square() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/LinearTestSquare.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		//Testing to move two away
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(7, 7);
		assertTrue(manager.move(from, to));
	}
	
	@Test
	void testOutOfBoundsSquare() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/LinearTestSquare.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		//Testing to move out of bounds
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(15, 4);
		assertFalse(manager.move(from, to));
	}
	
	@Test
	void testNonLinearSquare() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/LinearTestSquare.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		//Testing to move out of bounds
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(5, 6);
		assertFalse(manager.move(from, to));
	}
	
	@Test
	void testNonLinear2Square() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/LinearTestSquare.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		//Testing to move out of bounds
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(7, 3);
		assertFalse(manager.move(from, to));
	}
	
	@Test
	void testLinearWithBlock() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/LinearTestSquare2.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		//Testing to move past a block space
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(4, 10);
		assertFalse(manager.move(from, to));
		
		//Testing to move past a player space
		from = manager.makeCoordinate(4, 4);
		to = manager.makeCoordinate(8, 4);
		assertFalse(manager.move(from, to));
		
		//Testing to move past a player space
		from = manager.makeCoordinate(4, 4);
		to = manager.makeCoordinate(7, 7);
		assertFalse(manager.move(from, to));
	}
}

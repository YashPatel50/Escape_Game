package escape.pathfinding;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import escape.EscapeGameBuilder;
import escape.EscapeGameManager;
import escape.board.Board;
import escape.coordinate.Coordinate;
import escape.coordinate.CoordinateFactory;
import escape.coordinate.IBaseCoordinate;
public class PathFindingTest {
	
	
	@Test
	void testFilterBoard() throws Exception {
		Board board = new Board();
		board.setDimensions(5, 5);
		
		CoordinateFactory coordinateFactory = CoordinateFactory.getInstance();
		IBaseCoordinate hexCoord = coordinateFactory.makeSquareCoordinate(5, 5);
		ArrayList<IBaseCoordinate> neighbors = hexCoord.getAllNeighbors();
		assertEquals(neighbors.size(), 8);
		
		ArrayList<IBaseCoordinate> filteredNeighbors = board.filterCoordinatesRange(neighbors);
		assertNotNull(filteredNeighbors);
		assertEquals(filteredNeighbors.size(), 3);
	}
	
	@Test
	void testCanMove() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/simpleSquare.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(2, 3);
		Coordinate to = manager.makeCoordinate(2, 4);
		
		assertTrue(manager.move(from, to));
	}
	
	@Test
	void testCanMove2() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/simpleSquare.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(2, 3);
		Coordinate to = manager.makeCoordinate(2, 5);
		
		assertTrue(manager.move(from, to));
	}

	@Test
	void testCanMoveOutOfReach() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/simpleSquare.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(2, 3);
		Coordinate to = manager.makeCoordinate(5, 3);
		
		assertFalse(manager.move(from, to));
	}
	
	@Test
	void testCanMovePlayerInWay() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/simpleSquare.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(2, 3);
		Coordinate to = manager.makeCoordinate(4, 4);
		
		assertFalse(manager.move(from, to));
	}
	
	@Test
	void testOtherPlayerCantMove() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/simpleSquare.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(5, 3);
		
		assertFalse(manager.move(from, to));
	}
	
	@Test
	void testNoPiece() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/simpleSquare.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(2, 4);
		Coordinate to = manager.makeCoordinate(2, 5);
		
		assertFalse(manager.move(from, to));
	}
	
	@Test
	void testNoPiece2() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/simpleSquare.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(5, 5);
		Coordinate to = manager.makeCoordinate(4, 5);
		
		assertFalse(manager.move(from, to));
	}
	
	@Test
	void testPieceToExit() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/simpleSquare2.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(2, 3);
		Coordinate to = manager.makeCoordinate(2, 4);
		
		assertTrue(manager.move(from, to));
	}
}

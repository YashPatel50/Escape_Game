package escape;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import escape.coordinate.Coordinate;

public class EscapeGameFullGames {

	@Test
	void testGamePlayer1Wins() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/simpleSquare2.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(2, 3);
		Coordinate to = manager.makeCoordinate(2, 4);
		assertTrue(manager.move(from, to));
	}
	
	@Test
	void testGamePlayer2Wins() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/simpleSquare2.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(2, 3);
		Coordinate to = manager.makeCoordinate(2, 2);
		assertTrue(manager.move(from, to));
		
		Coordinate from2 = manager.makeCoordinate(4, 4);
		Coordinate to2 = manager.makeCoordinate(2, 4);
		assertTrue(manager.move(from2, to2));
	}
	
	@Test
	void testGameDraw() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/simpleSquare2.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(2, 3);
		Coordinate to = manager.makeCoordinate(2, 2);
		assertTrue(manager.move(from, to));
		
		Coordinate from2 = manager.makeCoordinate(4, 4);
		Coordinate to2 = manager.makeCoordinate(2, 3);
		assertTrue(manager.move(from2, to2));
	}
	
	@Test
	void testScoreGame() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/scoreGame.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(2, 3);
		Coordinate to = manager.makeCoordinate(2, 2);
		assertTrue(manager.move(from, to));
		
		from = manager.makeCoordinate(4, 4);
		to = manager.makeCoordinate(6, 4);
		assertTrue(manager.move(from, to));
		
		from = manager.makeCoordinate(2, 2);
		to = manager.makeCoordinate(2, 3);
		assertTrue(manager.move(from, to));
		
		from = manager.makeCoordinate(6, 5);
		to = manager.makeCoordinate(6, 4);
		assertTrue(manager.move(from, to));
		assertFalse(manager.move(from, to));
	}
	
	@Test
	void testEscapeGameManagerCoordinateOutOfReach() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/basicGame.egc");
		EscapeGameManager manager = egb.makeGameManager();
		Coordinate coord = manager.makeCoordinate(4, 4);
		Coordinate impossibleCoord = manager.makeCoordinate(5, 5);
		
		assertFalse(manager.move(coord, impossibleCoord));
	}
	
	@Test
	void testEscapeGameManagerMoveToSameLocation() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/basicGame.egc");
		EscapeGameManager manager = egb.makeGameManager();
		Coordinate coord = manager.makeCoordinate(4, 4);
		Coordinate impossibleCoord = manager.makeCoordinate(4, 4);
		
		assertFalse(manager.move(coord, impossibleCoord));
	}
}

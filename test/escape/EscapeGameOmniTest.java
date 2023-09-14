package escape;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import escape.coordinate.Coordinate;
import escape.gameobserver.Observer;

public class EscapeGameOmniTest {
	
	@Test
	void testRegularOmniMovement() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/omniTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(4, 5);
		assertTrue(manager.move(from, to));
	}
	
	@Test
	void testMoveToExit() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/omniTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		Observer obs1 = new Observer();
		manager.addObserver(obs1);
		
		Coordinate from = manager.makeCoordinate(5, 8);
		Coordinate to = manager.makeCoordinate(5, 12);
		assertTrue(manager.move(from, to));
		
	}
	
	@Test
	void testMovePlayer2() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/omniTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		Observer obs1 = new Observer();
		manager.addObserver(obs1);
		
		Coordinate from = manager.makeCoordinate(5, 8);
		Coordinate to = manager.makeCoordinate(5, 12);
		assertTrue(manager.move(from, to));
		
		from = manager.makeCoordinate(10, 12);
		to = manager.makeCoordinate(12, 10);
		assertTrue(manager.move(from, to));
	}
	
	@Test
	void testGameOverByPieces() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/omniTest.egc");
		EscapeGameManager manager = egb.makeGameManager();
		Observer obs1 = new Observer();
		manager.addObserver(obs1);
		
		Coordinate from = manager.makeCoordinate(5, 8);
		Coordinate to = manager.makeCoordinate(5, 12);
		assertTrue(manager.move(from, to));
		
		from = manager.makeCoordinate(10, 12);
		to = manager.makeCoordinate(5, 12);
		assertTrue(manager.move(from, to));
		assertEquals(obs1.getMessage(), "PLAYER2 has won");
		
		from = manager.makeCoordinate(4, 4);
		to = manager.makeCoordinate(5, 12);
		assertFalse(manager.move(from, to));
		assertEquals(obs1.getMessage(), "PLAYER2 has won");
	}
	
	@Test
	void testGameOverByScore() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/omniTest2.egc");
		EscapeGameManager manager = egb.makeGameManager();
		Observer obs1 = new Observer();
		manager.addObserver(obs1);
		
		Coordinate from = manager.makeCoordinate(6, 11);
		Coordinate to = manager.makeCoordinate(5, 12);
		assertTrue(manager.move(from, to));
		assertEquals(obs1.getMessage(), "PLAYER1 has won");
		
	}
	
	@Test
	void testGameOverByHighestScore() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/omniTest2.egc");
		EscapeGameManager manager = egb.makeGameManager();
		Observer obs1 = new Observer();
		manager.addObserver(obs1);
		
		//Turn 1
		Coordinate from = manager.makeCoordinate(6, 11);
		Coordinate to = manager.makeCoordinate(6, 20);
		manager.move(from, to);
		
		from = manager.makeCoordinate(20, 20);
		to = manager.makeCoordinate(15, 20);
		manager.move(from, to);
		
		//Turn 2
		from = manager.makeCoordinate(6, 20);
		to = manager.makeCoordinate(12, 20);
		manager.move(from, to);
		
		from = manager.makeCoordinate(10, 12);
		to = manager.makeCoordinate(5, 12);
		manager.move(from, to);
		
		//Turn 3
		from = manager.makeCoordinate(12, 20);
		to = manager.makeCoordinate(6, 20);
		manager.move(from, to);
		
		from = manager.makeCoordinate(15, 20);
		to = manager.makeCoordinate(16, 20);
		manager.move(from, to);
		
		assertEquals(obs1.getMessage(), "PLAYER2 has won");
		
	}
	
	@Test
	void testFromCorrectLocation() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/omniTest2.egc");
		EscapeGameManager manager = egb.makeGameManager();
		Observer obs1 = new Observer();
		manager.addObserver(obs1);
		
		//Player can't move from an exit
		Coordinate from = manager.makeCoordinate(5, 12);
		Coordinate to = manager.makeCoordinate(6, 20);
		assertFalse(manager.move(from, to));
		assertEquals(obs1.getMessage(), "This coordinate does not have a game piece");
	}
	
	@Test
	void testFly() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/omniTestSurrounded.egc");
		EscapeGameManager manager = egb.makeGameManager();
		Observer obs1 = new Observer();
		manager.addObserver(obs1);
		
		//Player can move around players
		Coordinate from = manager.makeCoordinate(4, 4);
		Coordinate to = manager.makeCoordinate(2, 5);
		assertTrue(manager.move(from, to));
		
		//Player can move around block
		from = manager.makeCoordinate(10, 10);
		to = manager.makeCoordinate(15, 10);
		assertTrue(manager.move(from, to));
		
		//Player1 can't land on a player
		from = manager.makeCoordinate(2, 5);
		to = manager.makeCoordinate(4, 5);
		assertFalse(manager.move(from, to));
		
		//Player can't land on Block
		from = manager.makeCoordinate(15, 10);
		to = manager.makeCoordinate(11, 10);
		assertFalse(manager.move(from, to));
	}
	
	@Test
	void testUnblock() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/omniTestSurrounded2.egc");
		EscapeGameManager manager = egb.makeGameManager();
		Observer obs1 = new Observer();
		manager.addObserver(obs1);
		
		//Player can move around block
		Coordinate from = manager.makeCoordinate(10, 10);
		Coordinate to = manager.makeCoordinate(13, 10);
		assertTrue(manager.move(from, to));
		
		//Random Move
		from = manager.makeCoordinate(4, 5);
		to = manager.makeCoordinate(1, 1);
		assertTrue(manager.move(from, to));
		
		//Player can't land on block
		from = manager.makeCoordinate(13, 10);
		to = manager.makeCoordinate(11, 10);
		assertFalse(manager.move(from, to));
		
		//Can move again
		from = manager.makeCoordinate(13, 10);
		to = manager.makeCoordinate(11, 12);
		assertTrue(manager.move(from, to));
		
	}
	
	@Test
	void testBlockLocation() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/omniTestSurrounded2.egc");
		EscapeGameManager manager = egb.makeGameManager();
		Observer obs1 = new Observer();
		manager.addObserver(obs1);
		
		//Player can move around block
		Coordinate from = manager.makeCoordinate(9, 10);
		Coordinate to = manager.makeCoordinate(13, 10);
		assertFalse(manager.move(from, to));
		assertEquals(obs1.getMessage(), "This coordinate does not have a game piece");
	}
	
	@Test
	void testBlock() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/omniTestSurrounded3.egc");
		EscapeGameManager manager = egb.makeGameManager();
		Observer obs1 = new Observer();
		manager.addObserver(obs1);
		
		//Player can'tmove around block
		Coordinate from = manager.makeCoordinate(10, 10);
		Coordinate to = manager.makeCoordinate(13, 10);
		assertFalse(manager.move(from, to));
		
	}
	
	@Test
	void testRemove() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/omniTestRemove.egc");
		EscapeGameManager manager = egb.makeGameManager();
		Observer obs1 = new Observer();
		manager.addObserver(obs1);
		
		//Player1 can remove another player
		Coordinate from = manager.makeCoordinate(6, 11);
		Coordinate to = manager.makeCoordinate(10, 12);
		assertTrue(manager.move(from, to));
		
		//Player2 can't move from old place
		from = manager.makeCoordinate(10, 12);
		to = manager.makeCoordinate(11, 12);
		assertFalse(manager.move(from, to));
		
		//Player 2 removes Player 1
		from = manager.makeCoordinate(2, 9);
		to = manager.makeCoordinate(5, 8);
		assertTrue(manager.move(from, to));
		
		//Player 1 removes Player 2's final piece
		from = manager.makeCoordinate(10, 12);
		to = manager.makeCoordinate(5, 8);
		assertTrue(manager.move(from, to));
		
		//Ensure the game is over
		assertEquals("PLAYER1 has won", obs1.getMessage());
	}
	
	@Test
	void testRemoveLandingOnYourOwnPiece() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/omniTestRemove.egc");
		EscapeGameManager manager = egb.makeGameManager();
		Observer obs1 = new Observer();
		manager.addObserver(obs1);
		
		//Player1 can remove another player
		Coordinate from = manager.makeCoordinate(5, 8);
		Coordinate to = manager.makeCoordinate(4, 4);
		assertFalse(manager.move(from, to));
		
	}
	
	@Test
	void testPathThroughAnExit() throws Exception{
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/omniTestThroughExit.egc");
		EscapeGameManager manager = egb.makeGameManager();
		Observer obs1 = new Observer();
		manager.addObserver(obs1);
		
		//Player1 can remove another player
		Coordinate from = manager.makeCoordinate(10, 10);
		Coordinate to = manager.makeCoordinate(8, 10);
		assertTrue(manager.move(from, to));
		assertEquals("PLAYER1 has won", obs1.getMessage());
	}
	
}

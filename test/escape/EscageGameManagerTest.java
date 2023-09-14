package escape;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import escape.coordinate.Coordinate;
import escape.coordinate.Coordinate.CoordinateType;
import escape.coordinate.IBaseCoordinate;
import escape.coordinate.LocationType;
import escape.coordinate.SquareCoordinate;
import escape.gamepiece.GamePiece;
import escape.gamepiece.EscapePiece;
import escape.gamepiece.EscapePiece.PieceName;
import escape.player.Player;
import escape.util.EscapeGameInitializer;

public class EscageGameManagerTest {
	
	@Test
	void testEscapeGameManagerCreation() {
		EscapeGameManagerImp<IBaseCoordinate> manager = new EscapeGameManagerImp<IBaseCoordinate>(CoordinateType.SQUARE);
		assertNotNull(manager);
		EscapeGameManagerImp<IBaseCoordinate> manager2 = new EscapeGameManagerImp<IBaseCoordinate>(CoordinateType.HEX);
		assertNotNull(manager2);
	}
	
	
	@Test
	void testEscapeGameManagerDimension() {
		EscapeGameManagerImp<IBaseCoordinate> manager = new EscapeGameManagerImp<IBaseCoordinate>(CoordinateType.SQUARE);
		manager.setDimensions(10, 15);
		assertEquals(10, manager.getBoard().getMaxX());
		assertEquals(15, manager.getBoard().getMaxY());
	}
	
	@Test
	void testEscapeGameManagerPieceDescriptors() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/basicGame.egc");
		EscapeGameInitializer initial = egb.getGameInitializer();
		
		EscapeGameManagerImp<IBaseCoordinate> manager = new EscapeGameManagerImp<IBaseCoordinate>(CoordinateType.SQUARE);
		assertTrue(manager.setPieceDescriptors(initial.getPieceTypes()));
		
	}
	
	@Test
	void testAddGamePieces() {
		EscapeGameManagerImp<IBaseCoordinate> manager = new EscapeGameManagerImp<IBaseCoordinate>(CoordinateType.SQUARE);
		GamePiece dogPiece = new GamePiece(PieceName.DOG, null, null);
		GamePiece birdPiece = new GamePiece(PieceName.BIRD, null, null);
		assertTrue(manager.addGamePiece(dogPiece));
		assertFalse(manager.addGamePiece(dogPiece));
		assertTrue(manager.addGamePiece(birdPiece));
		
		assertEquals(manager.getGamePiece(PieceName.DOG), dogPiece);
		assertNull(manager.getGamePiece(PieceName.HORSE));
	}
	
	@Test
	void testMakeCoordinate() {
		EscapeGameManagerImp<IBaseCoordinate> manager = new EscapeGameManagerImp<IBaseCoordinate>(CoordinateType.SQUARE);
		Coordinate squareCoord = manager.makeCoordinate(9, 2);
		assertNotNull(squareCoord);
		
		EscapeGameManagerImp<IBaseCoordinate> manager2 = new EscapeGameManagerImp<IBaseCoordinate>(CoordinateType.HEX);
		Coordinate hexCoord = manager2.makeCoordinate(9, 2);
		assertNotNull(hexCoord);
		
		EscapeGameManagerImp<IBaseCoordinate> manager3 = new EscapeGameManagerImp<IBaseCoordinate>(CoordinateType.TRIANGLE);
		assertNull(manager3.makeCoordinate(0, 0));
	}
	
	@Test
	void testEscapeGameManagerLocations() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/basicGame.egc");
		EscapeGameInitializer initial = egb.getGameInitializer();
		
		EscapeGameManagerImp<SquareCoordinate> manager = new EscapeGameManagerImp<SquareCoordinate>(CoordinateType.SQUARE);
		manager.setPieceDescriptors(initial.getPieceTypes());
		assertTrue(manager.setLocations(initial.getLocationInitializers()));
		
		IBaseCoordinate coord = manager.getBoard().getCoordinate(10, 12);
		assertEquals(coord.getX(), 10);
		assertEquals(coord.getY(), 12);
		assertNotNull(coord.getEscapePiece());
		assertEquals(coord.getEscapePiece().getName(), PieceName.DOG);
		assertEquals(coord.getEscapePiece().getPlayer(), Player.PLAYER2);
		assertEquals(coord.getLocationType(), LocationType.CLEAR);
		
		IBaseCoordinate coord2 = manager.getBoard().getCoordinate(5, 11);
		assertNull(coord2);
		
		IBaseCoordinate coord3 = manager.getBoard().getCoordinate(5, 12);
		assertEquals(coord3.getLocationType(), LocationType.EXIT);
	}
	
	@Test
	void testEscapeGameManagerRules() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/basicGame.egc");
		EscapeGameInitializer initial = egb.getGameInitializer();
		
		EscapeGameManagerImp<IBaseCoordinate> manager = new EscapeGameManagerImp<IBaseCoordinate>(CoordinateType.SQUARE);
		assertTrue(manager.setRules(initial.getRules()));
		assertEquals(manager.rules.getRules().size(), 2);
		
	}
	
	@Test
	void testEscapeGameManagerGetPiece() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/basicGame.egc");
		EscapeGameManager manager = egb.makeGameManager();
		Coordinate coord = manager.makeCoordinate(4, 4);
		EscapePiece piece = manager.getPieceAt(coord);
		assertEquals(piece.getName(), PieceName.SNAIL);
		assertEquals(piece.getPlayer(), Player.PLAYER1);
		
		Coordinate coord2 = manager.makeCoordinate(2, 1);
		assertNull(manager.getPieceAt(coord2));
	}
	
}

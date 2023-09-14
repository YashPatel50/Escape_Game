package escape.coordinate;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import escape.gamepiece.GamePiece;
import escape.coordinate.Coordinate.CoordinateType;
import escape.gamepiece.EscapePiece.PieceName;
import escape.player.Player;
import escape.player.PlayerPiece;

public class CoordinateTest {
	
	@Test
	void testCreateSquareCoordinate() {
		//Create a square coordinate without defining a location type
		SquareCoordinate coord = new SquareCoordinate(3, 4);
		assertNotNull(coord);
		assertEquals(coord.getX(), 3);
		assertEquals(coord.getY(), 4);
		assertEquals(coord.getLocationType(), LocationType.CLEAR);
		
		//Create a square coordinate while defining a location type
		SquareCoordinate coord2 = new SquareCoordinate(3, 4, LocationType.EXIT);
		assertEquals(coord2.getX(), 3);
		assertEquals(coord2.getY(), 4);
		assertEquals(coord2.getLocationType(), LocationType.EXIT);
	}
	
	@Test
	void testCreateHexCoordinate() {
		//Create a hex coordinate without defining a location type
		HexCoordinate coord = new HexCoordinate(3, 4);
		assertNotNull(coord);
		assertEquals(coord.getX(), 3);
		assertEquals(coord.getY(), 4);
		assertEquals(coord.getLocationType(), LocationType.CLEAR);
		
		//Create a hex coordinate while defining a location type
		HexCoordinate coord2 = new HexCoordinate(3, 4, LocationType.EXIT);
		assertEquals(coord2.getX(), 3);
		assertEquals(coord2.getY(), 4);
		assertEquals(coord2.getLocationType(), LocationType.EXIT);
	}
	
	@Test
	void testCoordinateType() {
		//Create a hex coordinate without defining a location type
		HexCoordinate coord = new HexCoordinate(3, 4);
		coord.setLocationType(LocationType.CLEAR);
		assertEquals(coord.getLocationType(), LocationType.CLEAR);
		
		//Create a hex coordinate while defining a location type
		HexCoordinate coord2 = new HexCoordinate(3, 4);
		coord2.setLocationType(LocationType.EXIT);
		assertEquals(coord2.getLocationType(), LocationType.EXIT);
	}
	
	@Test
	void testCoordindateFactory() {
		
		//Get the instance of coordinateFactory
		CoordinateFactory coordinateFactory = CoordinateFactory.getInstance();
		IBaseCoordinate coord = coordinateFactory.makeSquareCoordinate(0, 1);
		assertEquals(coord.getX(), 0);
		assertEquals(coord.getY(), 1);
		assertEquals(coord.getLocationType(), LocationType.CLEAR);
		//Check to ensure the datatype is still SquareCoordinate
		assertTrue(coord.getClass().getName().contains("SquareCoordinate"));
		
		IBaseCoordinate coord2 = coordinateFactory.makeHexCoordinate(-1, 1);
		IBaseCoordinate coord3 = coordinateFactory.makeHexCoordinate(-1, 1, LocationType.EXIT);
		assertEquals(coord2.getX(), -1);
		assertEquals(coord2.getY(), 1);
		assertEquals(coord2.getLocationType(), LocationType.CLEAR);
		assertEquals(coord3.getLocationType(), LocationType.EXIT);
		//Check to ensure the datatype is still HexCoordinate
		assertTrue(coord2.getClass().getName().contains("HexCoordinate"));
	}
	
	@Test
	void testEscapePiece() {
		//Get the coordinateFactory
		CoordinateFactory coordinateFactory = CoordinateFactory.getInstance();
		IBaseCoordinate coord = coordinateFactory.makeSquareCoordinate(0, 1);
		
		//Create a game piece
		GamePiece gamePiece = new GamePiece(PieceName.DOG, null, null);
		PlayerPiece escapePiece = new PlayerPiece(Player.PLAYER1, gamePiece);
		
		//Set the game piece to the coordinate
		coord.setEscapePiece(escapePiece);
		assertEquals(coord.getEscapePiece(), escapePiece);
		
		//Remove the game piece
		coord.removeEscapePiece();
		assertNull(coord.getEscapePiece());
	}
	
	@Test
	void testRawCoordinate() {
		//Get the coordinateFactory
		CoordinateFactory coordinateFactory = CoordinateFactory.getInstance();
		RawCoordinate coord = coordinateFactory.makeRawCoordinate(9, 7);
		assertNotNull(coord);
		assertEquals(coord.getX(), 9);
		assertEquals(coord.getY(), 7);
	}
	
	@Test
	void testRawCoordinateEquals() {
		//Get the coordinateFactory
		CoordinateFactory coordinateFactory = CoordinateFactory.getInstance();
		RawCoordinate coord = coordinateFactory.makeRawCoordinate(9, 7);
		RawCoordinate sameCoord = coordinateFactory.makeRawCoordinate(9, 7);
		RawCoordinate differentCoord = coordinateFactory.makeRawCoordinate(9, 6);
		RawCoordinate differentCoord2 = coordinateFactory.makeRawCoordinate(8, 7);
		assertTrue(coord.equals(sameCoord));
		assertFalse(coord.equals(differentCoord));
		assertFalse(coord.equals(differentCoord2));
		assertFalse(coord.equals(null));
	}
	
	@Test
	void testCoordinateLocations() {
		CoordinateFactory coordinateFactory = CoordinateFactory.getInstance();
		assertNull(coordinateFactory.makeCoordinate(1, 1, LocationType.CLEAR, CoordinateType.TRIANGLE));
		
		assertEquals(LocationType.CLEAR, coordinateFactory.makeCoordinate(1, 1, null, CoordinateType.HEX).getLocationType());
	}
	
	@Test
	void testCoordinateFactory() {
		CoordinateFactory coordinateFactory = CoordinateFactory.getInstance();
		assertNull(coordinateFactory.makeCoordinate(0, 0, CoordinateType.TRIANGLE));
		assertNull(coordinateFactory.makeCoordinate(0, 0, null, CoordinateType.TRIANGLE));
		
		assertNotNull(coordinateFactory.makeCoordinate(1,1, CoordinateType.SQUARE));
		assertNotNull(coordinateFactory.makeCoordinate(1,1, null, CoordinateType.SQUARE));
	}

}

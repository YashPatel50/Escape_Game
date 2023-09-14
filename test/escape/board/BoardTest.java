package escape.board;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import escape.coordinate.CoordinateFactory;
import escape.coordinate.IBaseCoordinate;
import escape.coordinate.LocationType;

public class BoardTest {
	
	@Test
	void testBoardCreation() {
		Board squareBoard = new Board();
		assertNotNull(squareBoard);
	}
	
	@Test
	void testBoardDimension() {
		Board hexBoard = new Board();
		hexBoard.setDimensions(10, 11);
		assertEquals(10, hexBoard.getMaxX());
		assertEquals(11, hexBoard.getMaxY());
		
		Board hexBoard2 = new Board();
		hexBoard2.setDimensions(0, 11);
		assertTrue(hexBoard2.isXInfinite());
		assertFalse(hexBoard2.isYInfinite());
		
		Board hexBoard3 = new Board();
		hexBoard3.setDimensions(4, 0);
		assertFalse(hexBoard3.isXInfinite());
		assertTrue(hexBoard3.isYInfinite());
		
		Board hexBoard4 = new Board();
		hexBoard4.setDimensions(0, 0);
		assertTrue(hexBoard4.isXInfinite());
		assertTrue(hexBoard4.isYInfinite());
	}
	
	@Test
	void testAddingCoordinates() {
		
		CoordinateFactory coordinateFactory = CoordinateFactory.getInstance();
		
		Board hexBoard = new Board();
		hexBoard.setDimensions(10, 11);
		
		IBaseCoordinate correctHex = coordinateFactory.makeHexCoordinate(10, 11);
		assertTrue(hexBoard.addCoordinate(correctHex));
		assertFalse(hexBoard.addCoordinate(correctHex));
		
		IBaseCoordinate hexOutOfBounds = coordinateFactory.makeHexCoordinate(11, 11);
		assertFalse(hexBoard.addCoordinate(hexOutOfBounds));
		
		IBaseCoordinate hexOutOfBounds2 = coordinateFactory.makeHexCoordinate(9, -4);
		assertFalse(hexBoard.addCoordinate(hexOutOfBounds2));
		
		hexBoard.setDimensions(0, 11);
		IBaseCoordinate hexInfiniteX = coordinateFactory.makeHexCoordinate(25, 11);
		assertTrue(hexBoard.addCoordinate(hexInfiniteX));
		
		hexBoard.setDimensions(5, 0);
		IBaseCoordinate hexInfiniteY = coordinateFactory.makeHexCoordinate(2, -10);
		assertTrue(hexBoard.addCoordinate(hexInfiniteY));
		
		hexBoard.setDimensions(0, 0);
		IBaseCoordinate hexInfinite = coordinateFactory.makeHexCoordinate(-1000, 500);
		assertTrue(hexBoard.addCoordinate(hexInfinite));
	}
	
	@Test
	void testRemovingCoordinates() {
		
		CoordinateFactory coordinateFactory = CoordinateFactory.getInstance();
		
		Board hexBoard = new Board();
		hexBoard.setDimensions(10, 11);
		
		IBaseCoordinate correctHex = coordinateFactory.makeHexCoordinate(10, 11);
		hexBoard.addCoordinate(correctHex);
		IBaseCoordinate incorrectHex = coordinateFactory.makeHexCoordinate(3, 4);
		assertTrue(hexBoard.removeCoordinate(correctHex));
		assertFalse(hexBoard.removeCoordinate(incorrectHex));
	}
	
	@Test
	void testGetCoordinates() {
		
		CoordinateFactory coordinateFactory = CoordinateFactory.getInstance();
		
		Board hexBoard = new Board();
		hexBoard.setDimensions(0, 0);
		
		IBaseCoordinate hex1 = coordinateFactory.makeHexCoordinate(6, 2, LocationType.BLOCK);
		hexBoard.addCoordinate(hex1);
		
		assertEquals(hex1, hexBoard.getCoordinate(6, 2));
		assertNull(hexBoard.getCoordinate(0, 2));
		
	}
	
	@Test
	void testExit() {
		
		CoordinateFactory coordinateFactory = CoordinateFactory.getInstance();
		
		Board hexBoard = new Board();
		hexBoard.setDimensions(0, 0);
		
		IBaseCoordinate hex1 = coordinateFactory.makeHexCoordinate(6, 2, LocationType.EXIT);
		hexBoard.addCoordinate(hex1);
		
		assertTrue(hexBoard.isExit(6, 2));
		assertFalse(hexBoard.isExit(5, 5));
		
	}
	
	
}

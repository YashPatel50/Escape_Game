package escape.coordinate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import escape.coordinate.Direction.HexDirection;
import escape.coordinate.Direction.SquareDirection;

public class CoordinateDirectionTest {
	
	@Test
	void testAllNeighborsSquare() {
		CoordinateFactory coordinateFactory = CoordinateFactory.getInstance();
		IBaseCoordinate coord = coordinateFactory.makeSquareCoordinate(5, 5);
		
		ArrayList<IBaseCoordinate> neighbors = coord.getAllNeighbors();
		
		//Ensure the number of neighbors is correct
		assertEquals(8, neighbors.size());
		
		//Ensure the neighbors are correct
		assertEquals(4, neighbors.get(0).getX());
		assertEquals(4, neighbors.get(0).getY());
		
		assertEquals(4, neighbors.get(1).getX());
		assertEquals(5, neighbors.get(1).getY());
		
		assertEquals(4, neighbors.get(2).getX());
		assertEquals(6, neighbors.get(2).getY());
		
		assertEquals(5, neighbors.get(3).getX());
		assertEquals(4, neighbors.get(3).getY());
		
		assertEquals(5, neighbors.get(4).getX());
		assertEquals(6, neighbors.get(4).getY());
		
		assertEquals(6, neighbors.get(5).getX());
		assertEquals(4, neighbors.get(5).getY());
		
		assertEquals(6, neighbors.get(6).getX());
		assertEquals(5, neighbors.get(6).getY());
		
		assertEquals(6, neighbors.get(7).getX());
		assertEquals(6, neighbors.get(7).getY());
	}
	
	@Test
	void testAllNeighborsHex() {
		CoordinateFactory coordinateFactory = CoordinateFactory.getInstance();
		IBaseCoordinate coord = coordinateFactory.makeHexCoordinate(2, 5);
		
		ArrayList<IBaseCoordinate> neighbors = coord.getAllNeighbors();
		
		//Ensure the number of neighbors is correct
		assertEquals(6, neighbors.size());
		
		//Ensure the neighbors are correct
		assertEquals(1, neighbors.get(0).getX());
		assertEquals(5, neighbors.get(0).getY());
		
		assertEquals(1, neighbors.get(1).getX());
		assertEquals(6, neighbors.get(1).getY());
		
		assertEquals(2, neighbors.get(2).getX());
		assertEquals(4, neighbors.get(2).getY());
		
		assertEquals(2, neighbors.get(3).getX());
		assertEquals(6, neighbors.get(3).getY());
		
		assertEquals(3, neighbors.get(4).getX());
		assertEquals(4, neighbors.get(4).getY());
		
		assertEquals(3, neighbors.get(5).getX());
		assertEquals(5, neighbors.get(5).getY());
		
	}
	
	
	@Test
	void testSquareDirection() {
		SquareDirection[] directions = SquareDirection.values();
		assertEquals(directions.length, 8);
		
		assertEquals(SquareDirection.valueOf("N"), SquareDirection.N);
	}
	
	@Test 
	void testSquareCoordinateDirection() {
		CoordinateFactory coordinatefactory = CoordinateFactory.getInstance();
		IBaseCoordinate square = coordinatefactory.makeSquareCoordinate(3, 2);
		square.setDirection(SquareDirection.E);
		assertEquals(square.getDirection(), SquareDirection.E);
	}
	
	@Test
	void testHexDirection() {
		HexDirection[] directions = HexDirection.values();
		assertEquals(directions.length, 6);
		
		assertEquals(HexDirection.valueOf("N"), HexDirection.N);
	}
	
	@Test 
	void testHexCoordinateDirection() {
		CoordinateFactory coordinatefactory = CoordinateFactory.getInstance();
		IBaseCoordinate hex = coordinatefactory.makeHexCoordinate(3, 2);
		hex.setDirection(HexDirection.NW);
		assertEquals(hex.getDirection(), HexDirection.NW);
	}
	
	@Test
	void testSquareNeighborDirections() {
		CoordinateFactory coordinateFactory = CoordinateFactory.getInstance();
		IBaseCoordinate coord = coordinateFactory.makeSquareCoordinate(5, 5);
		
		ArrayList<IBaseCoordinate> neighbors = coord.getAllNeighbors();
		
		assertEquals(SquareDirection.SW, neighbors.get(0).getDirection());
		assertEquals(SquareDirection.W, neighbors.get(1).getDirection());
		assertEquals(SquareDirection.NW, neighbors.get(2).getDirection());
		assertEquals(SquareDirection.S, neighbors.get(3).getDirection());
		assertEquals(SquareDirection.N, neighbors.get(4).getDirection());
		assertEquals(SquareDirection.SE, neighbors.get(5).getDirection());
		assertEquals(SquareDirection.E, neighbors.get(6).getDirection());
		assertEquals(SquareDirection.NE, neighbors.get(7).getDirection());
		
	}
	
	@Test
	void testHexNeighborDirections() {
		CoordinateFactory coordinateFactory = CoordinateFactory.getInstance();
		IBaseCoordinate coord = coordinateFactory.makeHexCoordinate(2, 5);
		
		ArrayList<IBaseCoordinate> neighbors = coord.getAllNeighbors();
		
		assertEquals(HexDirection.SW, neighbors.get(0).getDirection());
		assertEquals(HexDirection.NW, neighbors.get(1).getDirection());
		assertEquals(HexDirection.S, neighbors.get(2).getDirection());
		assertEquals(HexDirection.N, neighbors.get(3).getDirection());
		assertEquals(HexDirection.SE, neighbors.get(4).getDirection());
		assertEquals(HexDirection.NE, neighbors.get(5).getDirection());
		
	}

}

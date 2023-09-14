package escape.board;

import java.util.ArrayList;
import java.util.HashMap;

import escape.coordinate.CoordinateFactory;
import escape.coordinate.IBaseCoordinate;
import escape.coordinate.LocationType;
import escape.coordinate.RawCoordinate;

public class Board implements IBoard{
	
	//Coordinate factory
	CoordinateFactory coordinateFactory;
	
	//Stores the max dimensions of the board
	private int maxX;
	private int maxY;
	
	//Stores the coordinates on the board
	private HashMap<RawCoordinate, IBaseCoordinate> coordinates;
	
	public Board() {
		coordinates = new HashMap<RawCoordinate, IBaseCoordinate>();
		coordinateFactory = CoordinateFactory.getInstance();
	}
	
	public void setDimensions(int x, int y) {
		this.maxX = x;
		this.maxY = y;
	}
	
	public int getMaxX() {
		return this.maxX;
	}
	
	public int getMaxY() {
		return this.maxY;
	}
	
	@Override
	public boolean isXInfinite() {
		return this.getMaxX() == 0;
	}

	@Override
	public boolean isYInfinite() {
		return this.getMaxY() == 0;
	}
	
	/**
	 * Checks to ensure the x coordinate is within
	 * the bounds of the board
	 * @param coordinate
	 */
	private boolean xWithinBounds(IBaseCoordinate coordinate) {
		return ((coordinate.getX() <= this.getMaxX() && coordinate.getX() > 0) || isXInfinite());
	}
	
	/**
	 * Checks to ensure the y coordinate is within
	 * the bounds of the board
	 * @param coordinate
	 */
	private boolean yWithinBounds(IBaseCoordinate coordinate) {
		return ((coordinate.getY() <= this.getMaxY() && coordinate.getY() > 0) || isYInfinite());
	}

	/**
	 * Adds the coordinate to the board if there
	 * is no other coordinate already there
	 * @param coordinate
	 */
	@Override
	public boolean addCoordinate(IBaseCoordinate coordinate) {
		
		//Check to ensure the coordinate is within bounds
		if (xWithinBounds(coordinate) && yWithinBounds(coordinate)){
			RawCoordinate keyCoordinate = coordinateFactory.makeRawCoordinate(coordinate.getX(), coordinate.getY());
			if (!(coordinates.containsKey(keyCoordinate))) {
				coordinates.put(keyCoordinate, coordinate);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean removeCoordinate(IBaseCoordinate coordinate) {
		RawCoordinate keyCoordinate = coordinateFactory.makeRawCoordinate(coordinate.getX(), coordinate.getY());
		if (coordinates.containsKey(keyCoordinate)) {
			coordinates.remove(keyCoordinate);
			return true;
		}

		return false;
	}

	/**
	 * Gets a coordinate at the given x and y
	 * @param x
	 * @param y
	 * @return T The coordinate with all the information
	 */
	@Override
	public IBaseCoordinate getCoordinate(int x, int y) {
		RawCoordinate fakeCoordinate = coordinateFactory.makeRawCoordinate(x, y);
		if (!(coordinates.containsKey(fakeCoordinate))) {
			return null;
		} else {
			return coordinates.get(fakeCoordinate);
		}
	}
	
	/**
	 * Given a list of coordinates
	 * return the list of coordinates that fall within the correct range
	 */
	public ArrayList<IBaseCoordinate> filterCoordinatesRange(ArrayList<IBaseCoordinate> coordinates){
		
		ArrayList<IBaseCoordinate> finalList = new ArrayList<IBaseCoordinate>();
		for(IBaseCoordinate coordinate : coordinates) {
			if (xWithinBounds(coordinate) && yWithinBounds(coordinate)){
				finalList.add(coordinate);
			}
		}
		
		return finalList;
		
	}
	
	@Override
	public boolean isEmpty(int x, int y) {
		IBaseCoordinate coord = getCoordinate(x,y);
		if(coord == null) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isExit(int x, int y) {
		return checkLocationType(x,y,LocationType.EXIT);
	}
	
	@Override
	public boolean isBlock(int x, int y) {
		return checkLocationType(x,y,LocationType.BLOCK);
	}
	
	public boolean containsPiece(int x, int y) {
		return checkLocationType(x,y,LocationType.CLEAR);
	}
	
	private boolean checkLocationType(int x, int y, LocationType locationType) {
		IBaseCoordinate coord = getCoordinate(x,y);
		
		if(isEmpty(x,y)) {
			return false;
		} else {
			if (coord.getLocationType().equals(locationType)) {
				return true;
			}
		}
		
		return false;
	}
	
}

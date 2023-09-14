package escape.coordinate;

import java.util.ArrayList;

public class SquareCoordinate extends BaseCoordinate{
	
	
	/**
	 * Creates a Square Coordinate. Default LocationType is Clear
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	SquareCoordinate(int x, int y){
		super(x,y);
	}
	
	/**
	 * Creates a Square Coordinate
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param locationType type of coordinate
	 */
	SquareCoordinate(int x, int y, LocationType locationType){
		super(x,y,locationType);
	}

	/**
	 * Returns a list of all the neighbors of the coordinate,
	 * without looking at the board itself. So it doesn't check
	 * if it is out of bounds, or special
	 * @return
	 */
	
	@Override
	public ArrayList<IBaseCoordinate> getAllNeighbors() {
		ArrayList<IBaseCoordinate> neighbors = new ArrayList<IBaseCoordinate>();
		
		CoordinateFactory coordinateFactory = CoordinateFactory.getInstance();
		
		//Get the list of directions
		SquareDirection[] directions = SquareDirection.values();
		int directionsIndex = 0;
		
		//Go through the eight neighbors of a coordinate
		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {
				
				//Don't return the same coordinate
				if (!(x == 0 && y==0)) {
					IBaseCoordinate s = coordinateFactory.makeSquareCoordinate(this.getX() + x, this.getY() + y);
					//Give the correct direction to the coordinate
					s.setDirection(directions[directionsIndex]);
					directionsIndex++;
					//Add the coordinate to the neighbors
					neighbors.add(s);
				}
			}
		}
		
		return neighbors;
	}
}

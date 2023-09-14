package escape.coordinate;

import escape.coordinate.Coordinate.CoordinateType;

public class CoordinateFactory {
	
	private static CoordinateFactory instance = null;
    
    private CoordinateFactory() { 
        // Intentionally empty
    }
    
    public static CoordinateFactory getInstance() {
        if (instance == null) {  // lazy instantiation
            instance = new CoordinateFactory();
        }
        return instance;
    }
    
    public IBaseCoordinate makeCoordinate(int x, int y, CoordinateType type) {
    	switch (type) {
		case HEX:
			return makeHexCoordinate(x, y);
		case SQUARE:
			return makeSquareCoordinate(x, y);
		default:
			break;
    	}
    	return null;
    }
    
    public IBaseCoordinate makeCoordinate(int x, int y, LocationType location, CoordinateType type) {
    	switch (type) {
		case HEX:
			return makeHexCoordinate(x, y, location);
		case SQUARE:
			return makeSquareCoordinate(x, y, location);
		default:
			break;
    	}
    	return null;
    }
    
    public IBaseCoordinate makeSquareCoordinate(int x, int y) {
    	return makeSquareCoordinate(x,y,LocationType.CLEAR);
    }
    
    public IBaseCoordinate makeSquareCoordinate(int x, int y, LocationType locationType) {
    	return new SquareCoordinate(x,y, locationType);
    }
    
    public IBaseCoordinate makeHexCoordinate(int x, int y) {
    	return makeHexCoordinate(x,y,LocationType.CLEAR);
    }
    
    public IBaseCoordinate makeHexCoordinate(int x, int y, LocationType locationType) {
    	return new HexCoordinate(x,y, locationType);
    }
    
    public RawCoordinate makeRawCoordinate(int x, int y) {
    	return new RawCoordinate(x,y);
    }

}

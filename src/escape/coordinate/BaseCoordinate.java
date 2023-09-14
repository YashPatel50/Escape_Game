package escape.coordinate;

import escape.player.IPlayerPiece;

public abstract class BaseCoordinate extends RawCoordinate implements IBaseCoordinate, Direction{

	
	
	//Stores the type of Coordinate
	LocationType type = LocationType.CLEAR;
	
	//Stores the game piece
	IPlayerPiece escapePiece = null;
	
	//Stores the direction of where this coordinate came from
	Direction direction = null;
	
	//For the pathfinding algorithm
	int distance = 0;
	
	/**
	 * Creates a Coordinate. Default LocationType is Clear
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	BaseCoordinate(int x, int y){
		super(x, y);
	}
	
	/**
	 * Creates a Coordinate
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param locationType type of coordinate
	 */
	BaseCoordinate(int x, int y, LocationType locationType){
		super(x,y);
		setLocationType(locationType);
	}

	@Override
	public LocationType getLocationType() {
		return this.type;
	}
	
	@Override
	public void setLocationType(LocationType type) {
		if (type == null) {
			this.type = LocationType.CLEAR;
		} else {
			this.type = type;
		}
	}
	
	@Override
	public void setEscapePiece(IPlayerPiece escapePiece) {
		this.escapePiece = escapePiece;
	}
	
	@Override
	public void removeEscapePiece() {
		this.escapePiece = null;
	}
	
	@Override
	public IPlayerPiece getEscapePiece() {
		return this.escapePiece;
	}
	
	@Override
	public Direction getDirection() {
		return this.direction;
	}
	
	@Override
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public int getDistance() {
		return this.distance;
	}
	
	public void setDistance(int newDistance) {
		this.distance = newDistance;
	}
}

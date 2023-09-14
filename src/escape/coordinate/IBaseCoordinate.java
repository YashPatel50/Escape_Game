package escape.coordinate;

import java.util.ArrayList;

import escape.player.IPlayerPiece;

public interface IBaseCoordinate extends Coordinate{
	
	int getX();
	int getY();
	LocationType getLocationType();
	void setLocationType(LocationType type);
	
	IPlayerPiece getEscapePiece();
	void setEscapePiece(IPlayerPiece escapePiece);
	void removeEscapePiece();
	
	ArrayList<IBaseCoordinate> getAllNeighbors();
	Direction getDirection();
	void setDirection(Direction direction);
	int getDistance();
	void setDistance(int newDistance);
	
}

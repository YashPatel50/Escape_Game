package escape.board;

import java.util.ArrayList;

import escape.coordinate.IBaseCoordinate;

public interface IBoard {
	
	//Dimensions for the board
	void setDimensions(int x, int y);
	int getMaxX();
	int getMaxY();
	boolean isXInfinite();
	boolean isYInfinite();
	
	//Coordinates
	boolean addCoordinate(IBaseCoordinate coordinate);
	boolean removeCoordinate(IBaseCoordinate coordinate);
	IBaseCoordinate getCoordinate(int x, int y);
	
	boolean isExit(int x, int y);
	boolean isBlock(int x, int y);
	boolean isEmpty(int x, int y);
	ArrayList<IBaseCoordinate> filterCoordinatesRange(ArrayList<IBaseCoordinate> neighbors);
	boolean containsPiece(int x, int y);
}

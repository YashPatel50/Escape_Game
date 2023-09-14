package escape.gamepiece;

import java.util.ArrayList;

import escape.board.IBoard;
import escape.coordinate.IBaseCoordinate;
import escape.gamepiece.EscapePiece.MovementPattern;
import escape.gamepiece.EscapePiece.PieceName;
import escape.player.IPlayerPiece;
import escape.rules.AllRules;

public interface IGamePiece{
	
	//Name
	PieceName getName();
	
	//MovementPattern
	MovementPattern getMovementPattern();
	
	//Points
	int getPointValue();
	
	//Distance
	int getDistanceValue();
	boolean canFly();
	
	//Jump
	boolean canJump();
	
	//Unblock
	boolean canUnblock();
	
	//Filter
	ArrayList<IBaseCoordinate> filterNeighbors(IBaseCoordinate startingCoord, ArrayList<IBaseCoordinate> neighbors,
			IBoard board, IPlayerPiece gamePiece, AllRules rules);
}

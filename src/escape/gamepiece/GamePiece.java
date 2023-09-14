package escape.gamepiece;

import java.util.ArrayList;

import escape.board.IBoard;
import escape.coordinate.IBaseCoordinate;
import escape.gamepiece.EscapePiece.MovementPattern;
import escape.gamepiece.EscapePiece.PieceName;
import escape.pathfinding.NeighborValidator;
import escape.pathfinding.NeighborValidator.NeighborFilter;
import escape.player.IPlayerPiece;
import escape.rules.AllRules;
import escape.util.PieceAttribute;

public class GamePiece implements IGamePiece{
	
	//Name of the piece
	private PieceName name;
	
	//Stores the MovementType of the piece
	private MovementPattern movementPattern;
		
	//Stores the value of the piece
	private int pointValue = 1;
	
	//Stores whether the piece can fly
	private boolean fly;
	
	//Stores the distance the piece can travel
	private int distance = 1;
	
	//Whether the piece can jump
	private boolean jump = false;
	
	//Whether the piece can jump
	private boolean unblock = false;
	
	//NeihborFilter for Pathfinding
	private NeighborFilter filter;
	
	
	public GamePiece(PieceName name, MovementPattern movement, PieceAttribute[] attributes) {
		
		this.name = name;
		
		//Set the movement pattern the default is OMNI
		if(movement == null) {
			this.movementPattern = MovementPattern.OMNI;
		} else {
			this.movementPattern = movement;
		}
		
		//Set the attributes
		if (attributes != null) {
			for(PieceAttribute attribute : attributes) {
				switch(attribute.getId()) {
				case VALUE:
					pointValue = attribute.getValue();
					break;
				case FLY:
					fly = true;
					distance = attribute.getValue();
					break;
				case DISTANCE:
					distance = attribute.getValue();
					break;
				case JUMP:
					jump = true;
					break;
				case UNBLOCK:
					unblock = true;
					break;
				default:
					break;
				}
			}
		}
		
		switch (this.movementPattern) {
			case OMNI:
				this.filter = NeighborValidator.OmniFilter;
				break;
			case LINEAR:
				this.filter = NeighborValidator.LinearFilter;
				break;
			case DIAGONAL:
				this.filter = NeighborValidator.DiagonalFilter;
				break;
			case ORTHOGONAL:
				this.filter = NeighborValidator.OrthogonalFilter;
				break;
			case SKEW:
				this.filter = NeighborValidator.SkewFilter;
				break;
			default:
				break;
		}
		
	}
	
	@Override
	public PieceName getName() {
		return this.name;
	}
	
	@Override
	public MovementPattern getMovementPattern() {
		return this.movementPattern;
	}

	@Override
	public int getPointValue() {
		return this.pointValue;
	}

	@Override
	public int getDistanceValue() {
		return this.distance;
	}

	@Override
	public boolean canFly() {
		return this.fly;
	}

	@Override
	public boolean canJump() {
		return this.jump;
	}

	@Override
	public boolean canUnblock() {
		return this.unblock;
	}
	
	public ArrayList<IBaseCoordinate> filterNeighbors(IBaseCoordinate startingCoord, ArrayList<IBaseCoordinate> neighbors,
			IBoard board, IPlayerPiece gamePiece, AllRules rules){
		return this.filter.filter(startingCoord, neighbors, board, gamePiece, rules);
	}
}

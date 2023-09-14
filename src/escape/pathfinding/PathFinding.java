package escape.pathfinding;

import java.util.ArrayList;
import java.util.LinkedList;
import escape.board.IBoard;
import escape.coordinate.CoordinateFactory;
import escape.coordinate.IBaseCoordinate;
import escape.coordinate.LocationType;
import escape.coordinate.RawCoordinate;
import escape.player.IPlayerPiece;
import escape.rules.AllRules;

public class PathFinding {
	
	/**
	 * Using Breadth-First Search to see if there is a path to the end coordinate
	 * @param board
	 * @param startingCoordinate
	 * @param endingCoordinate
	 * @param piece
	 */
	public PathFinding(){		
	}
	
	/**
	 * Returns true if there is a move
	 * to the coordinate
	 * @param board
	 * @param startingCoordinate
	 * @param endingCoordinate
	 * @param piece
	 * @return
	 */
	public RawCoordinate findPath(IBoard board, IBaseCoordinate startingCoordinate, RawCoordinate endingCoordinate,
								IPlayerPiece piece, AllRules rules, boolean hasToGoThroughExit) {
		//Coordinate factory
		CoordinateFactory coordinateFactory = CoordinateFactory.getInstance();
		
		//Create a queue for the BFS
		LinkedList<IBaseCoordinate> queue = new LinkedList<IBaseCoordinate>();
		
		//Create a visited list
		LinkedList<IBaseCoordinate> visited = new LinkedList<IBaseCoordinate>();
		
		//Stores the distance the piece can travel
		int maxDistance = piece.getGamePiece().getDistanceValue();
		
		//Add the first coordinate to the queue
		queue.add(startingCoordinate);
		visited.add(startingCoordinate);
		
		//Stores the exit coordinate if it has to go through an exit
		RawCoordinate exitCoordinate = null;
		
		while(!queue.isEmpty()) {
			
			//Dequeue a coordinate
			IBaseCoordinate coordinate = queue.poll();
			
			//If the distance is past the distance the piece can travel then move on
			if (coordinate.getDistance() > maxDistance) {
				continue;
			} 
			
			RawCoordinate rawCoordinate = coordinateFactory.makeRawCoordinate(coordinate.getX(), coordinate.getY());
			
			//If the coordinate is the ending then return true
			if(rawCoordinate.equals(endingCoordinate)) {
				//Ensure the ending location is viable
				if (endingSpaceViable(startingCoordinate, endingCoordinate, board, rules)) {
					//If there is an exit coordinate, then go through it
					if(exitCoordinate != null) {
						return exitCoordinate;
					}
					//Return the final destination
					return rawCoordinate;
				}
				
				//Return null if the exit is not viable
				return null;
			}
			
			//Store the exit coordinate if it has to go through it
			if (hasToGoThroughExit) {
				if(endsOnExit(rawCoordinate, board)) {
					exitCoordinate = rawCoordinate;
				}
			}
			
			//Get all the neighbors
			ArrayList<IBaseCoordinate> neighbors = coordinate.getAllNeighbors();
			
			//Only within the bounds
			neighbors = board.filterCoordinatesRange(neighbors);
			
			//Filter to places is reachable by the game piece
			neighbors = piece.getGamePiece().filterNeighbors(coordinate, neighbors, board, piece, rules);
			
			if (!hasToGoThroughExit) {
				neighbors = NeighborValidator.filterWithoutExit(neighbors, board);
			}
			
			for (IBaseCoordinate neighbor : neighbors) {
				neighbor.setDistance(coordinate.getDistance() + 1);
				if (!visited.contains(neighbor)) {
					queue.add(neighbor);
					visited.add(neighbor);
				}
			}
		}
		
		return null;
	}
	
	public static boolean endingSpaceViable(IBaseCoordinate start, RawCoordinate end, IBoard board, AllRules rules) {
		
		//If the space is empty then it is viable
		if(board.isEmpty(end.getX(), end.getY())) {
			return true;
		//Able to land on an Exit
		} else if (endsOnExit(end, board)) {
			return true;
		//Check to see if can land on opposing player
		} else if (ableToLandOnPlayer(end, board, rules)) {
			return true;
		}
		return false;
	}
	
	public static boolean sameCoord(IBaseCoordinate start, RawCoordinate end) {
		return start.equals(end);
	}
	
	public static boolean hasPiece(IBaseCoordinate coord) {
		return coord.getLocationType().equals(LocationType.CLEAR);
	}
	
	public static boolean endsOnBlock(RawCoordinate end, IBoard board) {
		return board.isBlock(end.getX(), end.getY());
	}
	
	public static boolean endsOnExit(RawCoordinate end, IBoard board) {
		return board.isExit(end.getX(), end.getY());
	}
	
	public static boolean ableToLandOnPlayer(RawCoordinate end, IBoard board, AllRules rules) {
		IBaseCoordinate realEndCoord = board.getCoordinate(end.getX(), end.getY());
		if(hasPiece(realEndCoord)) {
			if(rules.canLandOnOpposingPlayers()) {
				return true;
			}
		}
		return false;
	}

}

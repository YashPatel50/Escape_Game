package escape.pathfinding;

import java.util.ArrayList;
import java.util.stream.Collectors;

import escape.board.IBoard;
import escape.coordinate.IBaseCoordinate;
import escape.coordinate.LocationType;
import escape.gamepiece.IGamePiece;
import escape.player.IPlayerPiece;
import escape.rules.AllRules;

public class NeighborValidator {
	
	@FunctionalInterface
    public interface NeighborFilter
    {
		ArrayList<IBaseCoordinate> filter(IBaseCoordinate startingCoord, ArrayList<IBaseCoordinate> neighbors,
				IBoard board, IPlayerPiece gamePiece, AllRules rules);
    }
	
	//All NeighborFilters
	public static NeighborFilter OmniFilter = (startingCoord, neighbors,board, gamePiece, rules) ->
											OmniMoves(startingCoord, neighbors,board, gamePiece, rules);
	
	public static NeighborFilter LinearFilter = (startingCoord, neighbors,board, gamePiece, rules) ->
											LinearMoves(startingCoord, neighbors,board, gamePiece, rules);
											
	public static NeighborFilter DiagonalFilter = (startingCoord, neighbors,board, gamePiece, rules) ->
											DiagonalMoves(startingCoord, neighbors,board, gamePiece, rules);
											
	public static NeighborFilter OrthogonalFilter = (startingCoord, neighbors,board, gamePiece, rules) ->
											OrthogonalMoves(startingCoord, neighbors,board, gamePiece, rules);
											
	public static NeighborFilter SkewFilter = (startingCoord, neighbors,board, gamePiece, rules) ->
											SkewMoves(startingCoord, neighbors,board, gamePiece, rules);
				
	
											
	//Body of the Neighbor Filters					
	private static ArrayList<IBaseCoordinate> OmniMoves(IBaseCoordinate startingCoord, ArrayList<IBaseCoordinate> neighbors,
			IBoard board, IPlayerPiece playerPiece, AllRules rules) {
		//No need to check for surrounding coordinates
		return filterAttributes(startingCoord, neighbors, board, playerPiece, rules);
	}
					
	private static ArrayList<IBaseCoordinate> LinearMoves(IBaseCoordinate startingCoord, ArrayList<IBaseCoordinate> neighbors,
			IBoard board, IPlayerPiece playerPiece, AllRules rules) {
		
		ArrayList<IBaseCoordinate> tempList = neighbors;
		
		//Only use coordinates that are in the same direction as the previous coordinate
		if(startingCoord.getDirection() != null) {
			tempList = neighbors.stream()
			.filter(neighbor -> neighbor.getDirection().equals(startingCoord.getDirection()))
			.collect(Collectors.toCollection(ArrayList<IBaseCoordinate>::new));
		}
		
		//Use the new list for the filterAttribures
		return filterAttributes(startingCoord, tempList, board, playerPiece, rules);
	}
						
	private static ArrayList<IBaseCoordinate> DiagonalMoves(IBaseCoordinate startingCoord, ArrayList<IBaseCoordinate> neighbors,
			IBoard board, IPlayerPiece playerPiece, AllRules rules) {
		
		ArrayList<IBaseCoordinate> tempList = neighbors;
		
		//Only use coordinates that are in the same direction as the previous coordinate
		if(startingCoord.getDirection() != null) {
			tempList = neighbors.stream()
			.filter(neighbor -> neighbor.getDirection().equals(startingCoord.getDirection()))
			.collect(Collectors.toCollection(ArrayList<IBaseCoordinate>::new));
		} else {
			tempList = neighbors.stream()
					.filter(neighbor -> getDiagonalDirection(neighbor))
					.collect(Collectors.toCollection(ArrayList<IBaseCoordinate>::new));
		}
		
		//Use the new list for the filterAttribures
		return filterAttributes(startingCoord, tempList, board, playerPiece, rules);
	}
	
	private static ArrayList<IBaseCoordinate> OrthogonalMoves(IBaseCoordinate startingCoord, ArrayList<IBaseCoordinate> neighbors,
			IBoard board, IPlayerPiece playerPiece, AllRules rules) {
		
		ArrayList<IBaseCoordinate> tempList = neighbors;
		
		//Only use coordinates that are in the same direction as the previous coordinate
		if(startingCoord.getDirection() != null) {
			tempList = neighbors.stream()
			.filter(neighbor -> neighbor.getDirection().equals(startingCoord.getDirection()))
			.collect(Collectors.toCollection(ArrayList<IBaseCoordinate>::new));
		} else {
			tempList = neighbors.stream()
					.filter(neighbor -> getOrthogonalDirection(neighbor))
					.collect(Collectors.toCollection(ArrayList<IBaseCoordinate>::new));
		}
		
		//Use the new list for the filterAttribures
		return filterAttributes(startingCoord, tempList, board, playerPiece, rules);
	}
	
	private static ArrayList<IBaseCoordinate> SkewMoves(IBaseCoordinate startingCoord, ArrayList<IBaseCoordinate> neighbors,
			IBoard board, IPlayerPiece playerPiece, AllRules rules) {
		
		ArrayList<IBaseCoordinate> tempList = neighbors;
		
		//Only use coordinates that are in the same direction as the previous coordinate
		if(startingCoord.getDirection() != null) {
			tempList = neighbors.stream()
			.filter(neighbor -> !neighbor.getDirection().equals(startingCoord.getDirection()))
			.collect(Collectors.toCollection(ArrayList<IBaseCoordinate>::new));
		}
		
		//Use the new list for the filterAttributes
		return filterAttributes(startingCoord, tempList, board, playerPiece, rules);
	}
	
	private static ArrayList<IBaseCoordinate> filterAttributes(IBaseCoordinate startingCoord, ArrayList<IBaseCoordinate> neighbors,
			IBoard board, IPlayerPiece playerPiece, AllRules rules){
		//Make the final list of neighbors
		ArrayList<IBaseCoordinate> finalList = new ArrayList<IBaseCoordinate>();
		
		//Check the current piece if we should be continuing
		IBaseCoordinate realStartingCoord = board.getCoordinate(startingCoord.getX(), startingCoord.getY());
		
		//Get the Game Piece
		IGamePiece gamePiece = playerPiece.getGamePiece();
		
		//If another game piece exists on this tile
		if (otherGamePieceExists(realStartingCoord, playerPiece)) {
			//If the piece can't fly then no more neighbors
			if (!(gamePiece.canFly())) {
				return finalList;
			}
		}
		
		//Check each of the neighbors
		for(IBaseCoordinate neighbor : neighbors) {
			IBaseCoordinate realNeighbor = board.getCoordinate(neighbor.getX(), neighbor.getY());
			
			//If the coord is not a special location we can add it
			if (realNeighbor == null) {
				finalList.add(neighbor);
				continue;
			}
			
			//If the coord is an exit then we add it
			if(board.isExit(realNeighbor.getX(), realNeighbor.getY())) {
				finalList.add(neighbor);
				continue;
			}
			
			//If the piece can fly then we add it
			if(gamePiece.canFly()) {
				finalList.add(neighbor);
				continue;
			}
			
			//Checking to go on BLOCK neighbors
			if (gamePiece.canUnblock() && board.isBlock(realNeighbor.getX(), realNeighbor.getY())) {
				finalList.add(neighbor);
				continue;
			}
			
			//Checking to see if we can on spaces with other players
			if(rules.canLandOnOpposingPlayers()) {
				if(!samePlayerWithGamePiece(realNeighbor, playerPiece)) {
					finalList.add(neighbor);
					continue;
				}
			}
		}
		return finalList;
	}
	
	
	//Other filters
	public static ArrayList<IBaseCoordinate> filterWithoutExit(ArrayList<IBaseCoordinate> neighbors, IBoard board){
		//Make the final list of neighbors
		ArrayList<IBaseCoordinate> finalList = new ArrayList<IBaseCoordinate>();
		for(IBaseCoordinate neighbor : neighbors) {
			if (!board.isExit(neighbor.getX(), neighbor.getY())) {
				finalList.add(neighbor);
			}
		}
		return finalList;
	}
	
	//Helper functions
	
	private static boolean otherGamePieceExists(IBaseCoordinate coord, IPlayerPiece gamePiece) {
		//Check to see if the coord doesn't exist or it can have Game Pieces on it
		if(coord == null || !canHavePieces(coord)) {
			return false;
		//Check to see if the coord is a place with the same player on it
		} else if (samePlayerWithGamePiece(coord, gamePiece)){
			return false;
		} else {
			return true;
		}
	}
	
	private static boolean canHavePieces(IBaseCoordinate coord) {
		return coord.getLocationType().equals(LocationType.CLEAR);
	}
	
	private static boolean samePlayerWithGamePiece(IBaseCoordinate coord, IPlayerPiece playerPiece) {
		return coord.getEscapePiece().getPlayer().equals(playerPiece.getPlayer());
	}
	
	private static boolean getDiagonalDirection(IBaseCoordinate neighbor) {
		switch(neighbor.getDirection().toString()) {
		case "NW":
		case "SW":
		case "SE":
		case "NE":
			return true;
		default:
			return false;
		}
	}

	private static boolean getOrthogonalDirection(IBaseCoordinate neighbor) {
		switch(neighbor.getDirection().toString()) {
		case "N":
		case "S":
		case "E":
		case "W":
			return true;
		default:
			return false;
		}
	}
}

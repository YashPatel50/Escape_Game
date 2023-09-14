package escape;

import java.util.ArrayList;
import java.util.HashMap;

import escape.board.Board;
import escape.board.IBoard;
import escape.coordinate.Coordinate.CoordinateType;
import escape.coordinate.CoordinateFactory;
import escape.coordinate.IBaseCoordinate;
import escape.coordinate.LocationType;
import escape.coordinate.RawCoordinate;
import escape.gameobserver.GameObserver;
import escape.gamepiece.EscapePiece;
import escape.gamepiece.GamePiece;
import escape.pathfinding.PathFinding;
import escape.player.IPlayerPiece;
import escape.player.Player;
import escape.player.PlayerInfo;
import escape.player.PlayerPiece;
import escape.rules.AllRules;
import escape.rules.BaseRule;
import escape.gamepiece.EscapePiece.PieceName;
import escape.util.LocationInitializer;
import escape.util.PieceTypeDescriptor;
import escape.util.RuleDescriptor;

public class EscapeGameManagerImp<T extends IBaseCoordinate> implements EscapeGameManager<T>{

	//Stores the type of coordinates
	private CoordinateType type = null;
	
	//Coordinate factory
	private CoordinateFactory coordinateFactory;
	
	//Stores the board
	private IBoard board;
	
	//Stores the game pieces
	private HashMap<PieceName, GamePiece> gamePieces;
	
	//Players
	PlayerInfo player1;
	PlayerInfo player2;
	
	//Current Player
	Player currentPlayer;
	
	//Rules
	AllRules rules = new AllRules();
	
	//All Observers
	ArrayList<GameObserver> allObservers;
	
	public EscapeGameManagerImp(CoordinateType type) {
		coordinateFactory = CoordinateFactory.getInstance();
		this.type = type; 
		board = new Board();
		gamePieces = new HashMap<PieceName, GamePiece>();
		player1 = new PlayerInfo(Player.PLAYER1);
		player2 = new PlayerInfo(Player.PLAYER2);
		currentPlayer = Player.PLAYER1;
		allObservers = new ArrayList<GameObserver>();
	}
	

	
	public IBoard getBoard(){
		return this.board;
	}
	
	/**
	 * Sets the dimensions of the board
	 * @param x
	 * @param y
	 */
	public void setDimensions(int x, int y) {
		board.setDimensions(x, y);
	}
	
	public boolean setPieceDescriptors(PieceTypeDescriptor[] pieces) {
		for(PieceTypeDescriptor piece : pieces) {
			GamePiece gamePiece = new GamePiece(piece.getPieceName(), piece.getMovementPattern(), piece.getAttributes());
			addGamePiece(gamePiece);
		}
		return true;
	}
	
	
	public boolean addGamePiece(GamePiece piece) {
		if (!(gamePieces.containsKey(piece.getName()))) {
			gamePieces.put(piece.getName(), piece);
			return true;
		}
		return false;
	}

	public GamePiece getGamePiece(PieceName name) {
		if (!(gamePieces.containsKey(name))) {
			return null;
		} else {
			return gamePieces.get(name);
		}
	}
	
	public boolean setLocations(LocationInitializer[] locations) {
		
		//Make one for each location
		for (LocationInitializer location : locations) {
			
			//Get the attributes of a location
			int x = location.x;
			int y = location.y;
			LocationType locationType = location.locationType;
			Player player = location.player;
			PieceName pieceName = location.pieceName;
			
			//Set the coords and location type
			IBaseCoordinate coord = makeIBaseCoordinateLocation(x, y, locationType);
			
			//Get the game piece
			GamePiece gamePiece = getGamePiece(pieceName);
			
			//Create a player's piece
			PlayerPiece piece = new PlayerPiece(player, gamePiece);
			
			//Place it on the coord
			coord.setEscapePiece(piece);
			
			//Place it on the board
			board.addCoordinate(coord);
			
			//Set the player's info
			if (player != null) {
				switch(player) {
				case PLAYER1:
					player1.addPlayerPiece();
					break;
				case PLAYER2:
					player2.addPlayerPiece();
					break;
				
				}
			}
			
		}
		
		return true;
	}
	
	public boolean setRules(RuleDescriptor[] rules) {
		
		for (RuleDescriptor rule : rules) {
			BaseRule base = new BaseRule(rule.ruleId, rule.ruleValue);
			this.rules.addRule(base);
		}
		return true;
	}
	
	/**
	 * Make the move in the current game.
	 * @param from starting location
	 * @param to ending location
	 * @return true if the move was legal, false otherwise
	 */
	@Override
	public boolean move(T from, T to) {
		
		if (rules.isGameOver()) {
			notifyAll(rules.getWinnerMessage());
			return false;
		}
		
		//Get the raw coordinates of to
		RawCoordinate rawTo = coordinateFactory.makeRawCoordinate(to.getX(), to.getY());
		
		//Get the actual coordinate of from
		IBaseCoordinate realFromCoord = board.getCoordinate(from.getX(), from.getY());
		
		//If the coordinate doesn't exist on the board return null
		if(realFromCoord == null) {
			notifyAll("Coordinate isn't available on the board");
			return false;
		}
		
		//Ensures that the coordinate is not a special coordinate
		if(!PathFinding.hasPiece(realFromCoord)) {
			notifyAll("This coordinate does not have a game piece");
			return false;
		}
		
		//Ensure the locations are not the same
		if(PathFinding.sameCoord(realFromCoord, rawTo)){
			notifyAll("Can't move to the same location");
			return false;
		}
		
		//If the ending location is a BLOCK space then return false
		if(PathFinding.endsOnBlock(rawTo, board)) {
			notifyAll("Can't end on a Block space");
			return false;
		}
		
		//Get the piece on the location
		IPlayerPiece piece = realFromCoord.getEscapePiece();
		
		//If the piece does not belong to the current player
		if (piece.getPlayer() != currentPlayer) {
			notifyAll("Piece does not belong to the player or Location does not have a Piece");
			return false;
		}
		
		//Create PathFinding object
		PathFinding pathFinder = new PathFinding();
		
		//Check to see if the piece can move to the new location
		//Check to find path without exit
		boolean hasToUseExit = false;
		RawCoordinate finalTo = pathFinder.findPath(board, realFromCoord, rawTo, piece, rules, hasToUseExit);
		
		if (finalTo == null) {
			hasToUseExit = true;
			finalTo = pathFinder.findPath(board, realFromCoord, rawTo, piece, rules, hasToUseExit);
		}
		
		//If the piece can move, then move it
		if (finalTo != null) {
			movePiece(finalTo, piece, realFromCoord);
		} else {
			notifyAll("Could not move to the location");
			return false;
		}
		
		PlayerInfo currentPlayerInfo = getPlayerInfo();
		
		//If the piece landed on an Exit space then change state
		if(board.isExit(finalTo.getX(), finalTo.getY())) {
			movePieceToExit(finalTo, currentPlayerInfo, piece);
		}
		
		//Check to see if the game has ended
		if (checkRules(currentPlayerInfo)) {
			return true;
		}
		
		//Update the turns
		rules.updateTurns(currentPlayer);
		
		//Switch the players
		switchPlayers();
		
		return true;
	}
	
	/**
	 * Moves a piece to the coordinate to
	 * @param to
	 * @param piece
	 * @param realFromCoord
	 */
	private void movePiece(RawCoordinate to, IPlayerPiece piece, IBaseCoordinate realFromCoord) {
		//Make the new coordinate
		T newCoord = makeCoordinate(to.getX(), to.getY());
		newCoord.setLocationType(LocationType.CLEAR);
		newCoord.setEscapePiece(piece);
		//Remove any piece if it exists
		if(board.containsPiece(to.getX(), to.getY())) {
			//Switches the players to get the right player to remove piece
			switchPlayers();
			PlayerInfo opposingPlayer = getPlayerInfo();
			opposingPlayer.removePlayerPiece();
			//Switch back
			switchPlayers();
			board.removeCoordinate(newCoord);
		}
		board.addCoordinate(newCoord);
		board.removeCoordinate(realFromCoord);
	}
	
	/**
	 * If the piece landed on an exit space
	 * Add the points for the player and ensure
	 * the exit has no pieces
	 * @param to
	 * @param playerInfo
	 * @param piece
	 */
	private void movePieceToExit(RawCoordinate to, PlayerInfo playerInfo, IPlayerPiece piece) {
		playerInfo.updateScore(piece.getGamePiece().getPointValue());
		playerInfo.removePlayerPiece();
		IBaseCoordinate exitCoord = board.getCoordinate(to.getX(), to.getY());
		exitCoord.setEscapePiece(null);
	}
	
	/**
	 * Gets the PlayerInfo from the player
	 * @param player
	 * @return
	 */
	private PlayerInfo getPlayerInfo() {
		if (currentPlayer.equals(Player.PLAYER1)) {
			return player1;
		} else {
			return player2;
		}
	}
	
	private PlayerInfo getOpposingPlayerInfo() {
		switchPlayers();
		PlayerInfo opposingInfo = getPlayerInfo();
		switchPlayers();
		return opposingInfo;
	}
	
	public void switchPlayers() {
		if (currentPlayer.equals(Player.PLAYER1)) {
			currentPlayer = Player.PLAYER2;
		} else {
			currentPlayer = Player.PLAYER1;
		}
	}
	
	/**
	 * Goes through the available rules and see
	 * if the game has ended
	 * @param currentPlayerInfo
	 * @return
	 */
	private boolean checkRules(PlayerInfo currentPlayerInfo) {
		//Check to see if the turn limit has been reached
		if (rules.turnLimitReached(currentPlayer)) {
			Player winner = rules.getHighestScore(player1, player2);
			rules.setGameOver(winner);
			notifyAll(rules.getWinnerMessage());
			return true;
		}
		//Check to see if the score has been reached
		if (rules.scoreReached(currentPlayerInfo.getScore())) {
			rules.setGameOver(currentPlayer);
			notifyAll(rules.getWinnerMessage());
			return true;
		}
		//Check to see if the other player has no pieces left
		PlayerInfo opposingPlayerInfo = getOpposingPlayerInfo();
		//If either player has no pieces left then the game is over
		if (currentPlayerInfo.hasNoPieces() || opposingPlayerInfo.hasNoPieces()) {
			rules.setGameOver(currentPlayer);
			notifyAll(rules.getWinnerMessage());
			return true;
		}
		
		//Game is not over
		return false;
	}
	
	

	/**
	 * Return the piece located at the specified coordinate. If executing
	 * this method in the game instance causes an exception, then this method
	 * returns null and sets the status message appropriately. The status message
	 * is not used in the initial release(s) of the game.
	 * @param coordinate the location to probe
	 * @return the piece at the specified location or null if there is none
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EscapePiece getPieceAt(T coordinate) {
		T coord = (T) board.getCoordinate(coordinate.getX(), coordinate.getY());
		//If the coordinate doesn't exist on the board return null
		if (coord == null){
			return null;
		}
		//Get the piece at the coordinate
		EscapePiece piece = coord.getEscapePiece();
		return piece;
	}

	/**
	 * Returns a coordinate of the appropriate type. If the coordinate cannot be
	 * created, then null is returned and the status message is set appropriately
	 * if the observer is used.
	 * @param x the x component
	 * @param y the y component
	 * @return the coordinate or null if the coordinate cannot be implemented
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T makeCoordinate(int x, int y) {
		return (T) coordinateFactory.makeCoordinate(x, y, type);
	}
	
	/**
	 * Returns a coordinate of the appropriate type. If the coordinate cannot be
	 * created, then null is returned and the status message is set appropriately
	 * if the observer is used.
	 * @param x the x component
	 * @param y the y component
	 * @return the coordinate or null if the coordinate cannot be implemented
	 */
	public IBaseCoordinate makeIBaseCoordinateLocation(int x, int y, LocationType locationType) {
		return coordinateFactory.makeCoordinate(x, y, locationType, type);
	}
	
	/**
	 * Add an observer to this manager. Whever the move() method returns
	 * false, the observer will be notified with a message indication the
	 * problem.
	 * @param observer
	 * @return the observer
	 */
	public GameObserver addObserver(GameObserver observer)
	{
		if(allObservers.contains(observer)) {
			return null;
		} else {
			allObservers.add(observer);
			return observer;
		}
	}
	
	/**
	 * Remove an observer from this manager. The observer will no longer
	 * receive notifications from this game manager.
	 * @param observer
	 * @return the observer that was removed or null if it had not previously
	 *     been registered
	 */
	public GameObserver removeObserver(GameObserver observer)
	{
		if(allObservers.contains(observer)) {
			allObservers.remove(observer);
			return observer;
		} else {
			return null;
		}
	}
	
	/**
	 * Notify all the observers with the message
	 * @param message
	 */
	public void notifyAll(String message) {
		for (GameObserver obs : allObservers) {
			obs.notify(message);
		}
	}
	
	/**
	 * Notify all the observers with the message and exception
	 * @param message
	 */
	public void notifyAll(String message, Throwable exception) {
		for (GameObserver obs : allObservers) {
			obs.notify(message, exception);
		}
	}

}

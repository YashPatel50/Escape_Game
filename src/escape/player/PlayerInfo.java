package escape.player;


public class PlayerInfo {
	
	private Player player;
	private int score;
	private int playerPieces = 0;
	
	public PlayerInfo(Player player) {
		this.player = player;
		score = 0;
		playerPieces = 0;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void updateScore(int points) {
		this.score += points;
	}
	
	public int getScore() {
		return this.score;
	}
	/**
	 * Adds a piece for the player
	 * @param playerPiece
	 * @return
	 */
	public boolean addPlayerPiece() {
		playerPieces++;
		return true;
	}
	
	/**
	 * Removes a piece from the player
	 * @param playerPiece
	 * @return
	 */
	public boolean removePlayerPiece() {
		playerPieces = playerPieces - 1;
		return true ;
	}
	
	public int getNumPlayerPieces() {
		return this.playerPieces;
	}
	
	/**
	 * Checks to see if the player has pieces left
	 * @return
	 */
	public boolean hasNoPieces() {
		return playerPieces == 0;
	}

}

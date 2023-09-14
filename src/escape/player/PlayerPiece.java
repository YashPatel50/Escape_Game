package escape.player;

import escape.gamepiece.IGamePiece;

public class PlayerPiece implements IPlayerPiece{

	//Stores the owner of the piece
	private Player owner;
	//Stores the piece
	private IGamePiece gamePiece;
	
	public PlayerPiece(Player player, IGamePiece gamePiece) {
		this.owner = player;
		this.gamePiece = gamePiece;
	}
	
	public Player getPlayer() {
		return this.owner;
	}

	@Override
	public IGamePiece getGamePiece() {
		return gamePiece;
	}
	
	@Override
	public PieceName getName() {
		return this.gamePiece.getName();
	}
}

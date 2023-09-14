package escape.player;

import escape.gamepiece.EscapePiece;
import escape.gamepiece.IGamePiece;

public interface IPlayerPiece extends EscapePiece{
	
	IGamePiece getGamePiece();
}

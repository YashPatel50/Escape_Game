package escape.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import escape.gamepiece.GamePiece;
import escape.gamepiece.EscapePiece.PieceName;

public class PlayerPieceTest {

	@Test
	void testPlayerPieceCreation() {
		GamePiece pieceGame = new GamePiece(PieceName.DOG, null, null);
		PlayerPiece piece = new PlayerPiece(Player.PLAYER1, pieceGame);
		assertNotNull(piece);
	}
	
	@Test
	void testPlayerPieceName() {
		GamePiece pieceGame = new GamePiece(PieceName.DOG, null, null);
		PlayerPiece piece = new PlayerPiece(Player.PLAYER1, pieceGame);
		assertEquals(PieceName.DOG, piece.getName());
	}
	
	@Test
	void testPlayerPiecePlayer() {
		GamePiece pieceGame = new GamePiece(PieceName.DOG, null, null);
		PlayerPiece piece = new PlayerPiece(Player.PLAYER1, pieceGame);
		assertEquals(Player.PLAYER1, piece.getPlayer());
	}
	
	@Test
	void testPlayerPieceGamePiece() {
		GamePiece pieceGame = new GamePiece(PieceName.DOG, null, null);
		PlayerPiece piece = new PlayerPiece(Player.PLAYER1, pieceGame);
		assertNotNull(piece.getGamePiece());
	}
}

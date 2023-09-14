package escape.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PlayerInfoTest {
	
	@Test
	void testPlayerInfoCreation() {
		PlayerInfo player1 = new PlayerInfo(Player.PLAYER1);
		assertNotNull(player1);
	}
	
	@Test
	void testPlayerInfoName() {
		PlayerInfo player2 = new PlayerInfo(Player.PLAYER2);
		assertEquals(Player.PLAYER2, player2.getPlayer());
		assertNotEquals(Player.PLAYER1, player2.getPlayer());
	}
	
	@Test
	void testPlayerScore() {
		PlayerInfo player = new PlayerInfo(Player.PLAYER2);
		player.updateScore(20);
		assertEquals(player.getScore(), 20);
		player.updateScore(13);
		assertEquals(player.getScore(), 33);
	}
	
	@Test
	void testPlayerInfoPieces() {
		PlayerInfo player1 = new PlayerInfo(Player.PLAYER1);
		assertTrue(player1.addPlayerPiece());
		assertEquals(player1.getNumPlayerPieces(), 1);
		assertFalse(player1.hasNoPieces());
		assertTrue(player1.removePlayerPiece());
		assertTrue(player1.hasNoPieces());
	}

}

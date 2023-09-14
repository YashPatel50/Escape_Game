package escape.gamepiece;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import escape.gamepiece.EscapePiece.MovementPattern;
import escape.gamepiece.EscapePiece.PieceAttributeID;
import escape.gamepiece.EscapePiece.PieceName;
import escape.util.PieceAttribute;

public class GamePieceTest {

	@Test
	void testGamePieceName() {
		GamePiece piece = new GamePiece(PieceName.DOG, null, null);
		assertEquals(piece.getName(), PieceName.DOG);
	}
	
	@Test
	void testGamePieceMovement() {
		GamePiece piece = new GamePiece(PieceName.DOG, MovementPattern.OMNI, null);
		assertEquals(MovementPattern.OMNI, piece.getMovementPattern());
		
		GamePiece piece2 = new GamePiece(PieceName.DOG, MovementPattern.LINEAR, null);
		assertEquals(MovementPattern.LINEAR, piece2.getMovementPattern());
		
		GamePiece piece3 = new GamePiece(PieceName.DOG, null, null);
		assertEquals(MovementPattern.OMNI, piece3.getMovementPattern());
	}
	
	@Test
	void testGamePieceAttributeValue() {
		
		PieceAttribute[] attributes = new PieceAttribute[1];
		PieceAttribute attribute = new PieceAttribute();
		attribute.setId(PieceAttributeID.VALUE);
		attribute.setValue(20);
		attributes[0] = attribute;
		GamePiece piece = new GamePiece(PieceName.DOG, null, attributes);
		assertEquals(piece.getPointValue(), 20);
	}
	
	@Test
	void testGamePieceAttributeDistance() {
		
		PieceAttribute[] attributes = new PieceAttribute[1];
		PieceAttribute attribute = new PieceAttribute();
		attribute.setId(PieceAttributeID.DISTANCE);
		attribute.setValue(4);
		attributes[0] = attribute;
		GamePiece piece = new GamePiece(PieceName.DOG, null, attributes);
		assertEquals(piece.getDistanceValue(), 4);
	}
	
	@Test
	void testGamePieceAttributeFly() {
		
		PieceAttribute[] attributes = new PieceAttribute[1];
		PieceAttribute attribute = new PieceAttribute();
		attribute.setId(PieceAttributeID.FLY);
		attribute.setValue(2);
		attributes[0] = attribute;
		GamePiece piece = new GamePiece(PieceName.DOG, null, attributes);
		assertEquals(piece.getDistanceValue(), 2);
		assertTrue(piece.canFly());
	}
	
	@Test
	void testGamePieceAttributeJump() {
		
		PieceAttribute[] attributes = new PieceAttribute[1];
		PieceAttribute attribute = new PieceAttribute();
		attribute.setId(PieceAttributeID.JUMP);
		attribute.setValue(2);
		attributes[0] = attribute;
		GamePiece piece = new GamePiece(PieceName.DOG, null, attributes);
		assertTrue(piece.canJump());
	}
	
	@Test
	void testGamePieceAttributeUnblock() {
		
		PieceAttribute[] attributes = new PieceAttribute[1];
		PieceAttribute attribute = new PieceAttribute();
		attribute.setId(PieceAttributeID.UNBLOCK);
		attribute.setValue(2);
		attributes[0] = attribute;
		GamePiece piece = new GamePiece(PieceName.DOG, null, attributes);
		assertTrue(piece.canUnblock());
	}
	
	@Test
	void testGamePieceAttributeStack() {
		
		PieceAttribute[] attributes = new PieceAttribute[1];
		PieceAttribute attribute = new PieceAttribute();
		attribute.setId(PieceAttributeID.STACK);
		attribute.setValue(2);
		attributes[0] = attribute;
		GamePiece piece = new GamePiece(PieceName.DOG, null, attributes);
		assertNotNull(piece);
	}
	
	@Test
	void testGamePieceAttributeMultiple() {
		
		PieceAttribute[] attributes = new PieceAttribute[2];
		PieceAttribute attribute = new PieceAttribute();
		attribute.setId(PieceAttributeID.UNBLOCK);
		attribute.setValue(2);
		PieceAttribute attribute2 = new PieceAttribute();
		attribute2.setId(PieceAttributeID.DISTANCE);
		attribute2.setValue(4);
		attributes[0] = attribute;
		attributes[1] = attribute2;
		GamePiece piece = new GamePiece(PieceName.DOG, null, attributes);
		assertTrue(piece.canUnblock());
		assertEquals(piece.getDistanceValue(), 4);
		assertEquals(piece.getPointValue(), 1);
	}
}

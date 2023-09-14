/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package escape;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import escape.coordinate.LocationType;
import escape.gamepiece.EscapePiece.MovementPattern;
import escape.gamepiece.EscapePiece.PieceAttributeID;
import escape.gamepiece.EscapePiece.PieceName;
import escape.player.Player;
import escape.rules.Rule.RuleID;
import escape.util.*;

/**
 * This is a simple test, not really a unit test, to make sure tht the
 * EscapeGameBuilder, in the starting code, is actually working.
 * 
 * @version May 30, 2020
 */
class EscapeGameBuilderTest
{

	@Test
	void test() throws Exception
	{
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/test1.egc");
		assertNotNull(egb);
	}
	
	@Test
	void testGameInitializer() throws Exception
	{
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/test1.egc");
		EscapeGameInitializer gm = egb.getGameInitializer();
		
		//Test the coordinate type
		assertEquals(gm.getCoordinateType().toString(), "SQUARE");
		
		//Test the bounds
		assertEquals(gm.getxMax(), 25);
		assertEquals(gm.getyMax(), 20);
		
		//Test the locations
		LocationInitializer[] locations = gm.getLocationInitializers();
		LocationInitializer firstLocation = locations[0];
		
		assertEquals(firstLocation.toString(), "LocationInitializer [x=3, y=5, locationType=BLOCK, player=null, pieceName=null]");
		
		//Test the Pieces
		PieceTypeDescriptor[] pieceTypes =  gm.getPieceTypes();
		PieceTypeDescriptor firstPieceType = pieceTypes[0];
		
		assertEquals(firstPieceType.toString(), "PieceTypeInitializer [pieceName=SNAIL, movementPattern=OMNI, attributes=[PieceAttribute [id=DISTANCE, value=1]]]");
		
		//Test the Rules
		RuleDescriptor[] rules = gm.getRules();
		RuleDescriptor firstRule = rules[0];
		
		assertEquals(firstRule.toString(),"RuleDescriptor [ruleId=REMOVE, ruleValue=0]" );
		
		//Test the overall gameInitializer
		assertEquals(gm.toString(), "EscapeGameInitializer [xMax=25, yMax=20, coordinateType=SQUARE, locationInitializers=[LocationInitializer [x=3, y=5, locationType=BLOCK, player=null, pieceName=null], LocationInitializer [x=4, y=4, locationType=CLEAR, player=PLAYER1, pieceName=SNAIL], LocationInitializer [x=10, y=12, locationType=null, player=PLAYER2, pieceName=HORSE], LocationInitializer [x=5, y=12, locationType=EXIT, player=null, pieceName=null]], types=[PieceTypeInitializer [pieceName=SNAIL, movementPattern=OMNI, attributes=[PieceAttribute [id=DISTANCE, value=1]]], PieceTypeInitializer [pieceName=DOG, movementPattern=LINEAR, attributes=[PieceAttribute [id=DISTANCE, value=5]]], PieceTypeInitializer [pieceName=HORSE, movementPattern=DIAGONAL, attributes=[PieceAttribute [id=DISTANCE, value=7], PieceAttribute [id=JUMP, value=0]]], PieceTypeInitializer [pieceName=BIRD, movementPattern=LINEAR, attributes=[PieceAttribute [id=FLY, value=5]]], PieceTypeInitializer [pieceName=FROG, movementPattern=OMNI, attributes=[PieceAttribute [id=DISTANCE, value=3], PieceAttribute [id=UNBLOCK, value=0]]]]]");
		
	}
	
	@Test
	void testLocationInitializer(){
		
		LocationInitializer li = new LocationInitializer(7, 0, LocationType.BLOCK , Player.PLAYER1, PieceName.BIRD);
		assertEquals(li.toString(), "LocationInitializer [x=7, y=0, locationType=BLOCK, player=PLAYER1, pieceName=BIRD]");
		
	}
	
	@Test
	void testPieceAttribute() {
		PieceAttribute pa = new PieceAttribute();
		pa.setId(PieceAttributeID.DISTANCE);
		pa.setValue(2);
		assertEquals(pa.getId(),PieceAttributeID.DISTANCE);
		assertEquals(pa.getValue(), 2);
	}
	
	@Test
	void testPieceTypeDescriptor() {
		PieceTypeDescriptor pt = new PieceTypeDescriptor();
		PieceAttribute pa = new PieceAttribute();
		pa.setId(PieceAttributeID.DISTANCE);
		pa.setValue(2);
		
		pt.setAttributes(pa);
		pt.setMovementPattern(MovementPattern.OMNI);
		pt.setPieceName(PieceName.DOG);
		
		assertEquals(pt.getAttribute(PieceAttributeID.DISTANCE), pa);
		assertEquals(pt.getAttributes()[0], pa);
		assertEquals(pt.getMovementPattern(), MovementPattern.OMNI);
		assertEquals(pt.getPieceName(), PieceName.DOG);
		
		//Null case
		PieceTypeDescriptor pt2 = new PieceTypeDescriptor();
		pt2.setAttributes(pa);
		assertEquals(pt2.getAttribute(PieceAttributeID.FLY), null);
	}
	
	@Test
	void testRuleDescriptor() {
		RuleDescriptor rule = new RuleDescriptor(RuleID.SCORE, 20);
		assertEquals(rule.toString(), "RuleDescriptor [ruleId=SCORE, ruleValue=20]");
		
	}
	
	@Test
	void testBasicConfig() throws Exception
	{
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/basicGame.egc");
		assertNotNull(egb);
	}
	
	@Test
	void testGameManager() throws Exception
	{
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/basicGame.egc");
		assertNotNull(egb);
		EscapeGameManager manager = egb.makeGameManager();
		assertNotNull(manager);
	}
	
	@Test
	void testGameManager2() throws Exception
	{
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/simpleSquare.egc");
		assertNotNull(egb);
		EscapeGameManager manager = egb.makeGameManager();
		assertNotNull(manager);
	}
	
	@Test
	void testGameManager3() throws Exception
	{
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/manyPieces.egc");
		assertNotNull(egb);
		EscapeGameManager manager = egb.makeGameManager();
		assertNotNull(manager);
	}

}

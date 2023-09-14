package escape.rules;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import escape.player.Player;
import escape.player.PlayerInfo;
import escape.rules.Rule.RuleID;

public class RulesTest {
	
	@Test
	void testBaseRuleCreation() {
		BaseRule rule = new BaseRule(RuleID.TURN_LIMIT, 40);
		assertNotNull(rule);
		assertEquals(RuleID.TURN_LIMIT, rule.getId());
		assertEquals(40, rule.getIntValue());
	}
	
	@Test
	void testAllRuleCreation() {
		BaseRule rule = new BaseRule(RuleID.TURN_LIMIT, 40);
		BaseRule rule2 = new BaseRule(RuleID.SCORE, 100);
		AllRules rules = new AllRules();
		assertNotNull(rules);
		assertTrue(rules.addRule(rule));
		assertTrue(rules.addRule(rule2));
	
	}
	
	@Test
	void testTurnLimit() {
		BaseRule rule = new BaseRule(RuleID.TURN_LIMIT, 3);
		BaseRule rule2 = new BaseRule(RuleID.SCORE, 40);
		AllRules rules = new AllRules();
		assertNotNull(rules);
		assertTrue(rules.addRule(rule2));
		assertTrue(rules.addRule(rule));
		
		assertFalse(rules.turnLimitReached(Player.PLAYER2));
		rules.updateTurns(Player.PLAYER2);
		assertFalse(rules.turnLimitReached(Player.PLAYER2));
		rules.updateTurns(Player.PLAYER2);
		assertTrue(rules.turnLimitReached(Player.PLAYER2));
		assertFalse(rules.turnLimitReached(Player.PLAYER1));
		
	
	}
	
	@Test
	void testScore() {
		BaseRule rule = new BaseRule(RuleID.TURN_LIMIT, 40);
		BaseRule rule2 = new BaseRule(RuleID.SCORE, 40);
		AllRules rules = new AllRules();
		assertTrue(rules.addRule(rule));
		assertTrue(rules.addRule(rule2));
		
		assertFalse(rules.scoreReached(20));
		assertTrue(rules.scoreReached(40));
		assertTrue(rules.scoreReached(60));
		
		AllRules rules2 = new AllRules();
		assertFalse(rules2.scoreReached(2));
	}
	
	@Test
	void testHighestScore() {
		BaseRule rule2 = new BaseRule(RuleID.SCORE, 40);
		BaseRule rule = new BaseRule(RuleID.TURN_LIMIT, 40);
		AllRules rules = new AllRules();
		
		//Works with no players
		assertNull(rules.getHighestScore(null, null));
		
		//Add rules
		rules.addRule(rule);
		rules.addRule(rule2);
		
		PlayerInfo player1 = new PlayerInfo(Player.PLAYER1);
		PlayerInfo player2 = new PlayerInfo(Player.PLAYER2);
		player1.updateScore(10);
		player2.updateScore(20);
		
		assertEquals(Player.PLAYER2, rules.getHighestScore(player1, player2));
	}
	
	@Test
	void testHighestScore2() {
		BaseRule rule2 = new BaseRule(RuleID.SCORE, 40);
		BaseRule rule = new BaseRule(RuleID.TURN_LIMIT, 40);
		AllRules rules = new AllRules();
		
		//Add rules
		rules.addRule(rule2);
		rules.addRule(rule);
		
		PlayerInfo player1 = new PlayerInfo(Player.PLAYER1);
		PlayerInfo player2 = new PlayerInfo(Player.PLAYER2);
		player1.updateScore(20);
		player2.updateScore(10);
		
		assertEquals(Player.PLAYER1, rules.getHighestScore(player1, player2));
	}
	
	@Test
	void testHighestScoreDraw() {
		BaseRule rule2 = new BaseRule(RuleID.SCORE, 40);
		BaseRule rule = new BaseRule(RuleID.TURN_LIMIT, 40);
		AllRules rules = new AllRules();		
		//Add rules
		rules.addRule(rule2);
		rules.addRule(rule);
		
		PlayerInfo player1 = new PlayerInfo(Player.PLAYER1);
		PlayerInfo player2 = new PlayerInfo(Player.PLAYER2);
		player1.updateScore(10);
		player2.updateScore(10);
		
		assertNull(rules.getHighestScore(player1, player2));
	}
}

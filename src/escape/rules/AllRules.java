package escape.rules;

import java.util.ArrayList;

import escape.player.Player;
import escape.player.PlayerInfo;
import escape.rules.Rule.RuleID;

public class AllRules {
	
	ArrayList<BaseRule> ruleList = new ArrayList<BaseRule>();
	
	int turns = 1;
	boolean gameOver = false;
	Player winner = null;
	String winningMessage = "";
	
	public AllRules() {
		
	}
	
	/**
	 * Add a rule
	 * @param rule
	 * @return
	 */
	public boolean addRule(BaseRule rule) {
		return ruleList.add(rule);
	}
	
	public ArrayList<BaseRule> getRules() {
		return this.ruleList;
	}
	
	/**
	 * If the turns is greater than or equal
	 * to the turn limit then return true
	 * @param turns
	 * @return
	 */
	public boolean turnLimitReached(Player player) {
		if (player.equals(Player.PLAYER2)) {
			for (BaseRule rule : ruleList){
				if(rule.getId().equals(RuleID.TURN_LIMIT)) {
					return rule.getIntValue() <= turns;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Update the number of turns if the second
	 * player made their turn
	 * @param player
	 */
	public void updateTurns(Player player) {
		if (player.equals(Player.PLAYER2)) {
			turns++;
		}
	}
	
	/**
	 * Returns true if a player's score is greater
	 * than the score goal
	 * @param score
	 * @return
	 */
	public boolean scoreReached(int score) {
		for (BaseRule rule : ruleList) {
			if(rule.getId().equals(RuleID.SCORE)) {
				return rule.getIntValue() <= score;
			}
		}
		return false;
	}
	
	/**
	 * Return the player with the largest score
	 * @param players
	 * @return
	 */
	public Player getHighestScore(PlayerInfo player1, PlayerInfo player2) {
		
		//Only check if the TurnLimit exists 
		for (BaseRule rule : ruleList) {
			if(rule.getId().equals(RuleID.TURN_LIMIT)) {				
				if(player1.getScore() > player2.getScore()) {
					return Player.PLAYER1;
				} else if (player1.getScore() == player2.getScore()){
					return null;
				} else {
					return Player.PLAYER2;
				}
			}
		}
		return null;
	}
	
	public boolean canLandOnOpposingPlayers() {
		//Check to see if Remove is a viable option 
		for (BaseRule rule : ruleList) {
			if(rule.getId().equals(RuleID.REMOVE)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Change the state that the game is over
	 * and the winner is the player
	 * @param player
	 */
	public void setGameOver(Player player) {
		gameOver = true;
		winner = player;
		if (winner == null) {
			winningMessage = "Game is over and results in a draw";
		} else {
			winningMessage = winner.toString() + " has won";
		}
	}
	
	/**
	 * Says if the game is over and prints who won
	 */
	public boolean isGameOver() {
		return gameOver;
	}
	
	public String getWinnerMessage() {
		return winningMessage;
	}
	
}

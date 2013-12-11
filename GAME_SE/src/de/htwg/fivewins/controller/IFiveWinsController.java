package de.htwg.fivewins.controller;

public interface IFiveWinsController {
	
	/*
	 * Sets the value for the field[column][row]
	 * @param column includes which column is meant
	 * @param row includes which column is meant
	 * @param value includes the player sign
	 */
	boolean setValue(int column, int row, String value);

	/*
	 * @return the variable statusMessage
	 */
	String getStatus();
	
	/*
	 * @return the gamefield
	 */
	String getFieldString();
	
	/*
	 * counts a turn up
	 * @return current turn
	 */
	int countTurn();
	
	/*
	 * @return current player sign
	 */
	String getPlayerSign();
	
	/*
	 * @return the player who won
	 */
	String winRequest();
	
	/*
	 * @return true if somebody won
	 */
	boolean getWinner();
}

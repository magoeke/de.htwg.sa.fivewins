package de.htwg.fivewins.controller;

import de.htwg.fivewins.model.ai.AIAdapter;
import de.htwg.util.observer.IObservable;

/**
 * Interface for gamecontroller.
 */
public interface IFiveWinsController extends IObservable {

	/**
	 * Sets the value for the field[column][row]
	 * 
	 * @param column
	 *            includes which column is meant
	 * @param row
	 *            includes which column is meant
	 * @param value
	 *            includes the player sign
	 */
	boolean setValue(int column, int row, String value);

	/**
	 * @return the variable statusMessage
	 */
	String getStatus();

	/**
	 * @return the gamefield
	 */
	String getFieldString();

	/**
	 * counts a turn up
	 * 
	 * @return current turn
	 */
	int countTurn();

	/**
	 * @return current player sign
	 */
	String getPlayerSign();

	/**
	 * @return the player who won
	 */
	String winRequest();

	/**
	 * @return true if somebody won
	 */
	boolean getWinner();

	/**
	 * return NPC Oject if exist
	 */
	AIAdapter getSecondPlayer();

	/**
	 * return number of truns
	 */
	int getTurn();

	/**
	 * return winner sign
	 */
	String getWinnerSign();

	/**
	 * reset the game
	 */
	void reset();

	/**
	 * @return true if it's a draw
	 */
	boolean getDraw();

	/**
	 * @return gamefield array
	 */
	String[][] getField();

	/**
	 * Handles user command.
	 * 
	 * @param line
	 * @return true if game ends(quit or win game)
	 */
	boolean handleInputOrQuit(String line);

	/**
	 * Resizes gamefield.
	 * 
	 * @param fieldsize
	 */
	void resizeGameField(int fieldsize);

	/**
	 * Creates an AI.
	 */
	void createAI(String difficulty);

}

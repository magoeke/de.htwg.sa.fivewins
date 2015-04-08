package de.htwg.fivewins.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.fivewins.model.ai.AIAdapter;
import de.htwg.fivewins.model.ai.VerySillyAI;
import de.htwg.fivewins.model.field.IField;
import de.htwg.fivewins.model.field.IFieldFactory;
import de.htwg.fivewins.persistence.IFieldDAO;
import de.htwg.fivewins.persistence.db4o.FieldDb4oDAO;
import de.htwg.util.observer.Observable;

/**
 * 
 */
@Singleton
public class FiveWinsController extends Observable implements
		IFiveWinsController {

	public static final int FIVEWINS = 5;
	public static final int TWO = 2;
	private String statusMessage = "Welcome to HTWG Five Wins!";
	private IField field;
	private int turn = 0;
	private int needToWin;
	private int lastx;
	private int lasty;
	private boolean win = false;
	private String winner = null;
	// player2 possible values: pvp = null, NPC vs. Player = AI Object
	private AIAdapter player2 = null;
	private boolean draw = false;
	private IFieldFactory fieldFactory;
	private IFieldDAO db;

	/**
	 * initialize a Controller for a Player vs. Player game
	 */
	@Inject
	public FiveWinsController(IFieldFactory fieldFactory) {
		this.fieldFactory = fieldFactory;
		this.field = fieldFactory.create(FIVEWINS);
		db = new FieldDb4oDAO();
		calculateNeedToWin();
	}

	/*
	 * set the variable needToWin. set need to win 5 if the gamefield is bigger
	 * than 4x4 otherwise it set needToWin like the game field size
	 */
	private void calculateNeedToWin() {
		if (field.getSize() < FIVEWINS) {
			needToWin = field.getSize();
			// after calculate
			return;
		}
		needToWin = FIVEWINS;
	}

	public boolean setValue(int column, int row, String value) {
		// input must be right
		// calculate array position from user input
		lastx = column - 1;
		lasty = row - 1;

		String cellVal = field.getCellValue(lastx, lasty);
		boolean result = false;

		if ("-".equals(cellVal)) {
			// cell is empty
			field.setValue(lastx, lasty, value);
			setStatusMessage("The cell " + column + " " + row
					+ " was successfully set.");
			result = true;
		} else {
			// cell is alreaedy set.
			setStatusMessage("The cell " + column + " " + row
					+ " is already set.");
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#getStatus()
	 */
	public String getStatus() {
		return statusMessage;
	}

	/*
	 * change the value of statusMessage
	 */
	private void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#getFieldString()
	 */
	public String getFieldString() {
		return field.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#getField()
	 */
	public String[][] getField() {
		return field.getGameField();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#countTurn()
	 */
	public int countTurn() {
		return turn++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#getPlayerSign()
	 */
	public String getPlayerSign() {
		int result = turn % TWO;
		if (result == 0) {
			return "X";
		} else {
			return "O";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#getWinner()
	 */
	public boolean getWinner() {
		return win;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#getWinnerSign()
	 */
	public String getWinnerSign() {
		return winner;
	}

	// ---------- Win request start

	/*
	 * for better understanding look at the picture in the readme
	 */
	public String winRequest() {
		int horizontal = winRequestHorizontal(lastx, lasty, 0, getPlayerSign(),
				true)
				+ winRequestHorizontal(lastx, lasty, 0, getPlayerSign(), false)
				+ 1;
		int vertical = winRequestVertical(lastx, lasty, 0, getPlayerSign(),
				true)
				+ winRequestVertical(lastx, lasty, 0, getPlayerSign(), false)
				+ 1;
		int diagonal = winRequestDiagonal(lastx, lasty, 0, getPlayerSign(),
				true)
				+ winRequestDiagonal(lastx, lasty, 0, getPlayerSign(), false)
				+ 1;
		int diagonalReflected = winRequestDiagonalReflected(lastx, lasty, 0,
				getPlayerSign(), true)
				+ winRequestDiagonalReflected(lastx, lasty, 0, getPlayerSign(),
						false) + 1;

		if (vertical >= needToWin || horizontal >= needToWin
				|| diagonal >= needToWin || diagonalReflected >= needToWin) {
			win = true;
			winner = getPlayerSign();
			return winner;
		}
		if (isItADraw()) {
			draw = true;
			return "draw";
		}

		return "";
	}

	/*
	 * test the horizontal win request if operator true use minus
	 */
	private int winRequestHorizontal(int value, int fixValue, int n,
			String currentPlayer, boolean operator) {
		int result = 0;
		if (value < 0 || value >= field.getSize()
				|| !field.getCellValue(value, fixValue).equals(currentPlayer)) {
			return n - 1;
		}

		if (operator) {
			result = winRequestHorizontal(value - 1, fixValue, n + 1,
					currentPlayer, operator);
		} else {
			result = winRequestHorizontal(value + 1, fixValue, n + 1,
					currentPlayer, operator);
		}
		return result;
	}

	/*
	 * test the vertical win request if operator true use minus
	 */
	private int winRequestVertical(int fixValue, int value, int n,
			String currentPlayer, boolean operator) {
		int result = 0;
		if (value < 0 || value >= field.getSize()
				|| !field.getCellValue(fixValue, value).equals(currentPlayer)) {
			return n - 1;
		}

		if (operator) {
			result = winRequestVertical(fixValue, value - 1, n + 1,
					currentPlayer, operator);
		} else {
			result = winRequestVertical(fixValue, value + 1, n + 1,
					currentPlayer, operator);
		}
		return result;
	}

	/*
	 * test the diagonal win request if operator true use double minus (- -)
	 */
	private int winRequestDiagonal(int value1, int value2, int n,
			String currentPlayer, boolean operator) {
		int result = 0;
		if (value1 < 0 || value1 >= field.getSize() || value2 < 0
				|| value2 >= field.getSize()) {
			return n - 1;
		}
		if (!field.getCellValue(value1, value2).equals(currentPlayer)) {
			return n - 1;
		}

		if (operator) {
			result = winRequestDiagonal(value1 - 1, value2 - 1, n + 1,
					currentPlayer, operator);
		} else {
			result = winRequestDiagonal(value1 + 1, value2 + 1, n + 1,
					currentPlayer, operator);
		}
		return result;
	}

	/*
	 * test the other diagonal win request if operator true use plus minus (+ -)
	 */
	private int winRequestDiagonalReflected(int value1, int value2, int n,
			String currentPlayer, boolean operator) {
		int result = 0;
		if (value1 < 0 || value1 >= field.getSize() || value2 < 0
				|| value2 >= field.getSize()) {
			return n - 1;
		}
		if (!field.getCellValue(value1, value2).equals(currentPlayer)) {
			return n - 1;
		}

		if (operator) {
			result = winRequestDiagonalReflected(value1 + 1, value2 - 1, n + 1,
					currentPlayer, operator);
		} else {
			result = winRequestDiagonalReflected(value1 - 1, value2 + 1, n + 1,
					currentPlayer, operator);
		}
		return result;
	}

	private boolean isItADraw() {
		boolean returnValue = true;
		for (int i = 0; i < field.getSize(); i++) {
			for (int j = 0; j < field.getSize(); j++) {
				if (field.getCellValue(i, j).equals("-")) {
					i = field.getSize();
					j = field.getSize();
					returnValue = false;
				}
			}
		}

		return returnValue;
	}

	// ---------- Win request end ------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#reset()
	 */
	public void reset() {
		field.reset();
		setStatusMessage("Welcome to HTWG Five Wins!");
		turn = 0;
		win = false;
		winner = null;
		draw = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#getSecondPlayer()
	 */
	public AIAdapter getSecondPlayer() {
		return player2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#getTurn()
	 */
	public int getTurn() {
		return turn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#getDraw()
	 */
	public boolean getDraw() {
		return draw;
	}

	/*
	 * handel inputed command reset, update or set value
	 */
	public boolean handleInputOrQuit(String line) {
		db.saveField(this.field);
		System.out.println("From DB:"+db.getFieldById("hallo"));
		boolean quit = false;
		if (line.equalsIgnoreCase("q")) {
			quit = true;
		}
		if (line.equalsIgnoreCase("u")) {
			// Do nothing, just redraw the updated grid
			notifyObservers();
		}
		if (line.equalsIgnoreCase("n")) {
			// Restart game
			reset();
		}

		handlePlayerInput(line);

		if (!win && player2 != null
				&& player2.getWhichPlayer().equals(getPlayerSign())) {
			handlePlayerInput(player2.getCommand());
		}

		return quit;
	}

	/*
	 * Checks if player input is a correct turn. If the input is correct it
	 * calls the winrequest.
	 */
	private void handlePlayerInput(String line) {
		if (line.matches("[0-9]{1,2}?,[0-9]{1,2}?")) {
			String[] numbers = line.split(",");
			int arg0 = Integer.parseInt(numbers[0]);
			int arg1 = Integer.parseInt(numbers[1]);
			boolean successfulFieldChange = setValue(arg0, arg1,
					getPlayerSign());

			winRequest();
			isItADraw();

			if (successfulFieldChange) {
				countTurn();
			}
			notifyObservers();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#resizeGameField(int)
	 */
	public void resizeGameField(int fieldsize) {
		field = fieldFactory.create(fieldsize);
		if (player2 != null) {
			player2.updateField(field);
		}
		turn = 0;
		calculateNeedToWin();
		notifyObservers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#createAI()
	 */
	public void createAI(String difficulty) {
		// At the moment only the silly ai works. But it's planned that more ais
		// work.
		player2 = new VerySillyAI("O", field);
		// strategy pattern?
	}

}

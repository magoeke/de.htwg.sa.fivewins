package de.htwg.fivewins.controller;

import de.htwg.fivewins.field.AIAdapter;
import de.htwg.fivewins.field.Field;
import de.htwg.util.observer.Observable;

/*
 * @author Max
 */
public class FiveWinsController extends Observable implements IFiveWinsController{

	public static final int FIVEWINS = 5;
	private String statusMessage = "Welcome to HTWG Five Wins!";
	private Field field;
	private int turn =  0;
	private int needToWin;
	private int lastx;
	private int lasty;
    private boolean win = false;
    private String winner = null;
    private AIAdapter player2 = null;
    private boolean draw = false;

	/*
	 * initialize a Controller for a Player vs. Player game
	 */
	public FiveWinsController(Field field) {
		this.field = field;
		calculateNeedToWin();
	}
	
	/*
	 * initialize a Controller for a NPC vs. Player game
	 */
	public FiveWinsController(Field field, AIAdapter ai) {
		this.field = field;
		calculateNeedToWin();
		this.player2 = ai;
	}
	
	/*
	 * set the variable needToWin.
	 * set need to win 5 if the gamefield is bigger than
	 * 4x4 otherwise it set needToWin like the game field size
	 */
	private void calculateNeedToWin() {
		if(field.getSize() < FIVEWINS) {
			needToWin = field.getSize();
			//after calculate
			return;	
		} 
		needToWin = FIVEWINS;
	}
	
	public boolean setValue(int column, int row, String value) {
		//input must be right
		lastx = column-1;
		lasty = row-1;
		String cellVal = field.getCellValue(lastx, lasty);
		boolean result = false;
		
		if(cellVal.equals("-")) {
			field.setValue(lastx, lasty, value);
			setStatusMessage("The cell "+column+" "+row+" was successfully set.");
			result = true;
		} else {
			setStatusMessage("The cell "+column+" "+row+" is already set.");
		}
		notifyObservers();
		return result;
	}

	public String getStatus() {
		return statusMessage;
	}

	/*
	 * change the value of statusMessage
	 */
	private void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getFieldString() {
		return field.toString();
	}
	
	public String[][] getField() {
		return field.getGameField();
	}
	
	public int countTurn() {
		return turn++;
	}
	
	public String getPlayerSign() {
		int result = turn % 2;
		if(result == 0) {
			return "X";
		} else {
			return "O";
		} 
	}
	
	public boolean getWinner() {
		return win;
	}
	
	public String getWinnerSign() {
		return winner;
	}
	
	/*
	 * for better understanding look at the picture
	 * in the readme
	 */
	public String winRequest() {
		int horizontal = winRequestHorizontal(lastx, lasty, 0, getPlayerSign(), true) 
				+ winRequestHorizontal(lastx, lasty, 0, getPlayerSign(), false) + 1;
		int vertical = winRequestVertical(lastx, lasty, 0, getPlayerSign(), true) 
				+ winRequestVertical(lastx, lasty, 0, getPlayerSign(), false) + 1;
		int diagonal = winRequestDiagonal(lastx, lasty, 0, getPlayerSign(), true) 
				+ winRequestDiagonal(lastx, lasty, 0, getPlayerSign(), false) + 1;
		int diagonalReflected = winRequestDiagonalReflected(lastx, lasty, 0, getPlayerSign(), true) 
				+ winRequestDiagonalReflected(lastx, lasty, 0, getPlayerSign(), false) + 1;
		
		if(vertical >= needToWin || horizontal >= needToWin || diagonal >= needToWin ||
				diagonalReflected >= needToWin) {
			win = true;
			winner = getPlayerSign();
			return winner;
		}
		if(isItADraw()) {
			draw = true;
			return "draw";
		}
		
		return "";
	}
	
	/*
	 * test the horizontal win request
	 * if operator true use minus
	 */
	private int winRequestHorizontal(int value, int fixValue, int n, String currentPlayer, 
			boolean operator) {
		int result = 0;
		if(value < 0 || value >= field.getSize() || 
				!field.getCellValue(value, fixValue).equals(currentPlayer) ) {
			return n-1;
		}
		
		if(operator) {
			result = winRequestHorizontal(value-1, fixValue,n+1, currentPlayer, operator);
		} else {
			result = winRequestHorizontal(value+1, fixValue,n+1, currentPlayer, operator);
		}
		return result;
	}
	
	/*
	 * test the vertical win request
	 * if operator true use minus
	 */
	private int winRequestVertical(int fixValue, int value, int n, String currentPlayer, 
			boolean operator) {
		int result = 0;
		if(value < 0 || value >= field.getSize() || 
				!field.getCellValue(fixValue, value).equals(currentPlayer) ) {
			return n-1;
		}
		
		if(operator) {
			result = winRequestVertical(fixValue, value-1, n+1, currentPlayer, operator);
		} else {
			result = winRequestVertical(fixValue, value+1, n+1, currentPlayer, operator);
		}
		return result;
	}
	
	/*
	 * test the diagonal win request
	 * if operator true use double minus (- -)
	 */
	private int winRequestDiagonal(int value1, int value2, int n, String currentPlayer, 
			boolean operator) {
		int result = 0;
		if(value1 < 0 || value1 >= field.getSize() || value2 < 0 || value2 >= field.getSize() ) {
			return n-1;
		}
		if(!field.getCellValue(value1, value2).equals(currentPlayer)) {
			return n-1;
		}
		
		if(operator) {
			result = winRequestDiagonal(value1-1, value2-1,n+1, currentPlayer, operator);
		} else {
			result = winRequestDiagonal(value1+1, value2+1,n+1, currentPlayer, operator);
		}
		return result;
	}
	
	/*
	 * test the other diagonal win request
	 * if operator true use plus minus (+ -)
	 */
	private int winRequestDiagonalReflected(int value1, int value2, int n, String currentPlayer, 
			boolean operator) {
		int result = 0;
		if(value1 < 0 || value1 >= field.getSize() || value2 < 0 || value2 >= field.getSize() ) {
			return n-1;
		}
		if(!field.getCellValue(value1, value2).equals(currentPlayer)) {
			return n-1;
		}
		
		if(operator) {
			result = winRequestDiagonalReflected(value1+1, value2-1,n+1, currentPlayer, operator);
		} else {
			result = winRequestDiagonalReflected(value1-1, value2+1,n+1, currentPlayer, operator);
		}
		return result;
	}
	
	private boolean isItADraw() {
		boolean returnValue = true;
		for(int i = 0; i < field.getSize(); i++) {
			for(int j = 0; j < field.getSize(); j++) {
				if(field.getCellValue(i, j).equals("-")) {
					i = field.getSize();
					j = field.getSize();
					returnValue = false;
				}
			}
		}
		
		return returnValue;
	}
	
	public void reset() {
		field.reset();
		setStatusMessage("Welcome to HTWG Five Wins!");
		turn = 0;
		win = false;
		winner = null;
		draw = false;
	}
		
	public AIAdapter getSecondPlayer() {
		return player2;
	}
		
	public int getTurn() {
		return turn;
	}
	
	public boolean getDraw() {
		return draw;
	}
	
	/*
	 * handel inputed command reset, update or set value
	 */
	public boolean handleInputOrQuit(String line) {
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

		if (line.matches("[0-9]{1,2}?,[0-9]{1,2}?")) {
			String[] numbers = line.split(",");
			int arg0 = Integer.parseInt(numbers[0]);
			int arg1 = Integer.parseInt(numbers[1]);
			boolean successfulFieldChange = setValue(arg0, arg1,
					getPlayerSign());
			if (successfulFieldChange) {
				countTurn();
			}

		}

		return quit;
	}
	
	public void resizeGameField(int fieldsize) {
		field = new Field(fieldsize);
		turn = 0;
		calculateNeedToWin();
		notifyObservers();
	}

}

package de.htwg.fivewins.controller;

import de.htwg.fivewins.field.Field;
import de.htwg.util.observer.Observable;

public class FiveWinsController extends Observable implements IFiveWinsController{

	public final int FiveWins = 5;
	private String statusMessage = "Welcome to HTWG Five Wins!";
	private Field field;
	private int turn =  0;
	private int needToWin;
	private int last_x;
	private int last_y;
    private boolean win = false;
    private String winner = null;

	
	public FiveWinsController(Field field) {
		this.field = field;
		calculateNeedToWin();
	}
	
	private void calculateNeedToWin() {
		if(field.getSize() < FiveWins) {
			needToWin = field.getSize();
			//after calculate
			return;	
		} 
		needToWin = FiveWins;
	}
	
	public boolean setValue(int column, int row, String value) {
		//input must be right
		last_x = column-1;
		last_y = row-1;
		String cellVal = field.getCellValue(last_x, last_y);
		boolean result = false;
		
		if(cellVal.equals("-")) {
			field.setValue(last_x, last_y, value);
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

	private void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getFieldString() {
		return field.toString();
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
	
	public String winRequest() {
		int horizontal = winRequestHorizontal(last_x, last_y, 0, getPlayerSign(), true) 
				+ winRequestHorizontal(last_x, last_y, 0, getPlayerSign(), false) + 1; //waagerecht
		int vertical = winRequestVertical(last_x, last_y, 0, getPlayerSign(), true) 
				+ winRequestVertical(last_x, last_y, 0, getPlayerSign(), false) + 1;
		int diagonal = winRequestDiagonal(last_x, last_y, 0, getPlayerSign(), true) 
				+ winRequestDiagonal(last_x, last_y, 0, getPlayerSign(), false) + 1;
		int diagonalReflected = winRequestDiagonalReflected(last_x, last_y, 0, getPlayerSign(), true) 
				+ winRequestDiagonalReflected(last_x, last_y, 0, getPlayerSign(), false) + 1;
		
		if(vertical >= needToWin || horizontal >= needToWin || diagonal >= needToWin ||
				diagonalReflected >= needToWin) {
			win = true;
			winner = getPlayerSign();
			return winner;
		}
		
		return "";
	}
	
	//operator true => Minus
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
	
	//operator true => Minus
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
	
	//operator true => doppel --
	private int winRequestDiagonal(int value1, int value2, int n, String currentPlayer, 
			boolean operator) {
		int result = 0;
		if(value1 < 0 || value1 >= field.getSize() || value2 < 0 || value2 >= field.getSize() || 
				!field.getCellValue(value1, value2).equals(currentPlayer) ) {
			return n-1;
		}
		
		if(operator) {
			result = winRequestDiagonal(value1-1, value2-1,n+1, currentPlayer, operator);
		} else {
			result = winRequestDiagonal(value1+1, value2+1,n+1, currentPlayer, operator);
		}
		return result;
	}
		
	//operator true => +-
		private int winRequestDiagonalReflected(int value1, int value2, int n, String currentPlayer, 
				boolean operator) {
			int result = 0;
			if(value1 < 0 || value1 >= field.getSize() || value2 < 0 || value2 >= field.getSize() || 
					!field.getCellValue(value1, value2).equals(currentPlayer) ) {
				return n-1;
			}
			
			if(operator) {
				result = winRequestDiagonalReflected(value1+1, value2-1,n+1, currentPlayer, operator);
			} else {
				result = winRequestDiagonalReflected(value1-1, value2+1,n+1, currentPlayer, operator);
			}
			return result;
		}
}





































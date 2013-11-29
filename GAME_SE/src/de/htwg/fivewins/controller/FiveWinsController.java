package de.htwg.fivewins.controller;

import de.htwg.fivewins.field.Field;
import de.htwg.util.observer.Observable;

public class FiveWinsController extends Observable {

	private String statusMessage = "Welcome to HTWG Five Wins!";
	private Field field;
	private int turn =  0;
	private int needToWin;
	private int last_x;
	private int last_y;


	
	public FiveWinsController(Field field) {
		this.field = field;
		calculateNeedToWin();
	}
	
	private void calculateNeedToWin() {
		if(field.getSize() < 5) {
			needToWin = field.getSize();
			return;	//after calculate
		} 
		needToWin = 5;
	}
	
	public boolean setValue(int column, int row, String value) {
		//input must be right
		last_x = column;
		last_y = row;
		String cellVal = field.getCellValue(column, row);
		boolean result = false;
		
		if(cellVal.equals("-")) {
			field.setValue(column, row, value);
			setStatusMessage("The cell "+column+" "+row+" was successfully set");
			result = true;
		} else {
			setStatusMessage("The cell "+column+" "+row+" is already set");
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
	
	//ToDo playerCurrentPlayerDign
	public String getPlayerSign() {
		int result = turn % 2;
		if(result == 0) {
			return "X";
		} else {
			return "O";
		}
	}
	
	public String winRequest() {
		winRequestR(last_x, last_y, 1, field.getCellValue(last_x, last_y));
		return field.getCellValue(last_x, last_y);
	}
	
	public int winRequestR(int column, int row, int number,String latestPlayerSign) {
		if(row > field.getSize() || row < 0) {
			return 0;
		}
		if(column > field.getSize() || row < 0) {
			return 0;
		}
		
		if(field.getCellValue(column, row).equals(latestPlayerSign)) {
			
		}
		if(needToWin <= 2) {
			
		}
		
		return 0;
	}

}

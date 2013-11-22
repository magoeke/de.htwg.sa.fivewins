package de.htwg.fivewins.controller;

import de.htwg.fivewins.field.Field;
import de.htwg.util.observer.Observable;

public class FiveWinsController extends Observable {

	private String statusMessage = "Welcome to HTWG Five Wins!";
	private Field field;
	private int turn =  0;
	final int FIVE = 5;


	
	public FiveWinsController(Field field) {
		this.field = field;
	}
	
	public void setValue(int column, int row, String value) {
		//input must be right
		String cellVal = field.getCellValue(column, row);
		
		if(cellVal.equals("-")) {
			field.setValue(column, row, value);
			setStatusMessage("The cell "+column+" "+row+" was successfully set");
		} else {
			setStatusMessage("The cell "+column+" "+row+" is already set");
		}
		notifyObservers();
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
	
	
	public String winRequest() {
		if(!verticalWinRequest().equals("")) {
			return verticalWinRequest();
		}
		return "";
	}
	
	
	public String verticalWinRequest() {
		int size = field.getSize();
		int[] countPlayerSign = {0, 0}; //{waagrecht, senkrecht}
		String[] currentPlayerSign = new String[2];
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				currentPlayerSign[0] = testVerticalWinRequest(i,j, countPlayerSign[0], currentPlayerSign[0]);
				if (countPlayerSign[0] >= FIVE){
					return currentPlayerSign[0];
				}
				currentPlayerSign[1] = testVerticalWinRequest(j,i, countPlayerSign[1], currentPlayerSign[1]);
				if (countPlayerSign[1] >= FIVE){
					return currentPlayerSign[1];
				}
			}
		}
		return "";
	}
	
	
	public String testVerticalWinRequest(int i, int j, int countPlayerSign, String currentPlayerSign) {
			
		if(field.getCellValue(i, j).equals(currentPlayerSign)) {
			countPlayerSign++;
		} else {
			if(!field.getCellValue(i, j).equals("")) {
				countPlayerSign = 1;
			} else {
				countPlayerSign = 0;
			}
			
		}
		
		return field.getCellValue(i, j);
	}
}

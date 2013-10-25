package de.htwg.fivewins.controller;

import de.htwg.fivewins.field.Field;
import de.htwg.util.observer.Observable;

public class FiveWinsController extends Observable {

	private String statusMessage = "Welcome to HTWG Five Wins!";
	private Field field;

	
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
	
	public String getPlayerSign() {
		return null;
	}
}

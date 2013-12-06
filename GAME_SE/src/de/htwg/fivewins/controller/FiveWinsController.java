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
    private int[] currentCell = new int[2];

	
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
		column--;
		row--;
		last_x = column;
		last_y = row;
		currentCell[0] = column;
		currentCell[1] = row;
		String cellVal = field.getCellValue(column, row);
		boolean result = false;
		
		if(cellVal.equals("-")) {
			field.setValue(column, row, value);
			column++; row++;
			setStatusMessage("The cell "+column+" "+row+" was successfully set.");
			column--; row--;
			result = true;
		} else {
			column++; row++;
			setStatusMessage("The cell "+column+" "+row+" is already set.");
			column--; row--;
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
	
	//ToDo getCurrentPlayer
	public String getCurrentPlayer(){ //getPlayerSign() {
		int result = turn % 2;
		if(result == 0) {
			return "X";
		} else {
			return "O";
		} 
	}
	
	public String winRequest() {
		boolean direction = true;
		boolean H = false;
		if(winRequestH(currentCell, last_x, last_y, field.getCellValue(last_x, last_y), direction, H, 0)){
			return field.getCellValue(last_x, last_y);
		}
		return "";
	}
	
	
	public boolean winRequestH(int[] cC, int last_x, int last_y, String s, boolean d, boolean b,int i) {
		while (field.getCellValue(cC[0], cC[1]) == field.getCellValue(last_x, last_y)) {         // ist das aktuelle feld gleich dem derzeit Abgefragtem?
			i++;
			if (i >= needToWin){
				b = true;
				break;
			}
			if(last_x - 1 >= 0 && cC[0] - last_x <= needToWin && d){                           // ist das Feld das gleich abgefragt überhaupt relevant?
				last_x--;
				winRequestH(cC, last_x, last_y, s, d, b,i);                                    // request an das feld über dem letzten.
				break;
			}
			if(d){			                                                                   // war das Feld eben nichtmehr relevant,
			    last_x = cC[0];                                                                // wird das abgefragte zurückgesetzt.
			    d = false;
			}                                                                                  //
			if(last_x + 1 < field.getSize() && last_x - cC[0] <= needToWin && !d){             //
				last_x++;
				winRequestH(cC, last_x, last_y, s, d, b, i);                                   //
				break;
			}
		}
	    return b;
	}
	
	
	
	
	
	
	
}





































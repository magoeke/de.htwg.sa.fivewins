package de.htwg.fivewins.field;

import java.util.Random;

public class VerySillyAI extends AIAdapter {
	
	/*
	 * initialize random generator
	 */
	private Random randomNumber = new Random();

	public VerySillyAI(String sign, Field field) {
		if(sign.equals("X") || sign.equals("O")) {
			this.whichPlayer = sign;
			this.field = field;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/*
	 * must be override because it's an abstract method in the
	 * parent class
	 */
	@Override
	public String calculateCommand() {
		boolean quit = false;
		int column = 0; 
		int row = 0;
		
		while(!quit) {
			column = randomNumber.nextInt(field.getSize()) + 1;
			row = randomNumber.nextInt(field.getSize()) + 1;
			quit = isFree(column, row);
		}
		
		//Number,Number is expected
		return column + "," + row;
	}
	
	
	/*
	 * is used for calculateCommand. It's test if the field is free
	 * or taken.
	 */
	private boolean isFree(int column, int row){
		boolean returnValue = false;
		--column;
		--row;
		if(field.getCellValue(column, row).equals("-")) {
			returnValue = true;
		}
		return returnValue;
	}
	
}

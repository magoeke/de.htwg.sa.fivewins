package de.htwg.fivewins.model.ai;

import java.util.Random;

import de.htwg.fivewins.model.field.IField;

/**
 * Dumb as straw. But it works :)
 */
public class VerySillyAI extends AIAdapter {

	/*
	 * initialize random generator
	 */
	private Random randomNumber = new Random();

	/**
	 * Constructor
	 * 
	 * @param sign
	 * @param field
	 */
	public VerySillyAI(String sign, IField field) {
		if ("X".equals(sign) || "O".equals(sign)) {
			this.whichPlayer = sign;
			this.field = field;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/*
	 * must be override because it's an abstract method in the parent class
	 */
	@Override
	public String calculateCommand() {
		boolean quit = false;
		int column = 0;
		int row = 0;

		while (!quit) {
			column = randomNumber.nextInt(field.getSize()) + 1;
			row = randomNumber.nextInt(field.getSize()) + 1;
			quit = isFree(column, row);
		}

		// Number,Number is expected
		return column + "," + row;
	}
	
	/**
	 * Only for test purposes
	 */
	public IField getField() {
		return field;
	}

	/**
	 * Only for test purposes
	 */
	public String getOpponent() {
		return opponent;
	}
}

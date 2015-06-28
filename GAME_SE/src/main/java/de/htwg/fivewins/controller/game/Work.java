package de.htwg.fivewins.controller.game;

import de.htwg.fivewins.model.field.IField;

public class Work {

	private int value1;
	private int value2;
	private int n;
	private String currentPlayer;
	private boolean operator;
	private IField field;

	public Work(int value1, int value2, int n, String currentPLayer,
			boolean operator, IField field) {
		this.value1 = value1;
		this.value2 = value2;
		this.currentPlayer = currentPLayer;
		this.operator = operator;
		this.field = field;
		this.n = n;
	}

	public int getValue1() {
		return value1;
	}

	public int getValue2() {
		return value2;
	}

	public int getN() {
		return n;
	}

	public String getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean isOperator() {
		return operator;
	}

	public IField getField() {
		return field;
	}

}

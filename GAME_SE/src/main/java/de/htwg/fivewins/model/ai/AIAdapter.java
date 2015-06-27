package de.htwg.fivewins.model.ai;

import de.htwg.fivewins.model.field.IField;

/*
 * template method pattern is used.
 * Because the methods should be the same
 * only the algorithm should be different
 */
public abstract class AIAdapter {
	private String opponent;
	private String whichPlayer;
	private IField field;
	
	/*
	 * @return the play sign of the ai
	 */
	public String getWhichPlayer() {
		return whichPlayer;
	}

	/**
	 * @return the command that the game should do
	 */
	public String getCommand() {
		return calculateCommand();
	}

	/**
	 * the children classes should overide these method, because the methode
	 * getcommand returns the result of this methode to the controller
	 */
	abstract String calculateCommand();

	/**
	 * Updates IField.
	 * @param field
	 */
	public void updateField(IField field) {
		this.field = field;
	}
	
	/*
	 * is used for calculateCommand. It's test if the field is free or taken.
	 */
	protected boolean isFree(int column, int row) {
		boolean returnValue = false;
		int column2 = column - 1;
		int row2 = row - 1;
		if ("-".equals(field.getCellValue(column2, row2))) {
			returnValue = true;
		}
		return returnValue;
	}
	
	/*
	 * is needed to avoid protected variables
	 */
	protected void setOpponent(String playerSign) {
		if("X".equals(playerSign)) {
			opponent = "O";
		} else {
			opponent = "X";
		}
	}
	
	/*
	 * is needed to avoid protected variables
	 */
	protected String getParentOpponent() {
		return opponent;
	}
	
	/*
	 * is needed to avoid protected variables
	 */
	protected void setWhichPlayer(String whichPlayer) {
		this.whichPlayer = whichPlayer;
	}
	
	/*
	 * is needed to avoid protected variables
	 */
	protected IField getParentField() {
		return field;
	}
	
	/*
	 * is needed to avoid protected variables
	 */
	protected void setParentField(IField field) {
		this.field =  field;
	}
	
}

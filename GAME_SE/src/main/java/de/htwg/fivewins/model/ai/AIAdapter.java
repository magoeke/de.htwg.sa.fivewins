package de.htwg.fivewins.model.ai;

import de.htwg.fivewins.model.field.Field;
import de.htwg.fivewins.model.field.IField;

/*
 * template method pattern is used.
 * Because the methods should be the same
 * only the algorithm should be different
 */
public abstract class AIAdapter {
	protected String whichPlayer;
	protected IField field;

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

	abstract public void updateField(IField field);
}

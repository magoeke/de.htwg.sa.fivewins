package de.htwg.fivewins.field;

//template methode pattern
public abstract class AIAdapter {
	protected String whichPlayer;
	protected IField field;
	
	public String getWhichPlayer() {
		return whichPlayer;
	}
	
	public String getCommand() {
		return calculateCommand();
	}
	
	abstract String calculateCommand();
}

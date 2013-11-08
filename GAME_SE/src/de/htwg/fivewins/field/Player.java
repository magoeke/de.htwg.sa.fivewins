package de.htwg.fivewins.field;

public class Player {
	private String character;
	
	public Player(String c) {
		this.character = c;
	}
	
	@Override
	public String toString() {
		return character;
	}

}

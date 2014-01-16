package de.htwg.fivewins;

import de.htwg.fivewins.controller.FiveWinsController;
import de.htwg.fivewins.field.Field;
import de.htwg.fivewins.gui.GameFrame;

/*
 * The start class for FiveWins.
 * Singelton is used.
 */
public final class FiveWins {
	public static FiveWins fivewins = null;
	GameFrame gf;
	
	private FiveWins() {
		gf= new GameFrame(new FiveWinsController(new Field(1)));
	}
	
	
	public static void main(String[] args) {
		fivewins = getInstance();
	}
	
	public static FiveWins getInstance() {
		if(fivewins == null) {
			fivewins = new FiveWins();
		}
		return fivewins;
	}

}

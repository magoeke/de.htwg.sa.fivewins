package de.htwg.fivewins;

import de.htwg.fivewins.controller.FiveWinsController;
import de.htwg.fivewins.field.Field;
import de.htwg.fivewins.tui.TextUI;

public final class FiveWins {
	private FiveWins() { }
	public static final int THREE = 3;
	private static TextUI tui;
	
	public static void main(String[] args) {
		tui = new TextUI(new FiveWinsController(new Field(THREE)));
		tui.printTUI();
		// continue until the user decides to quit
		boolean quit = false;
		while (!quit) {
		    quit = tui.iterate();
		    
		}

	}

	public static void reset() {
		tui = new TextUI(new FiveWinsController(new Field(THREE)));
	}
	
	public static void reset(int size) {
		tui = new TextUI(new FiveWinsController(new Field(size)));
	}
	
}

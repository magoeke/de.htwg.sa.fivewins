package de.htwg.fivewins;

import de.htwg.fivewins.controller.FiveWinsController;
import de.htwg.fivewins.field.Field;
import de.htwg.fivewins.tui.TextUI;

public final class FiveWins {
	private FiveWins() { }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TextUI tui = new TextUI(new FiveWinsController(new Field(3)));
		tui.printTUI();
		// continue until the user decides to quit
		boolean quit = false;
		while (!quit) {
		    quit = tui.iterate();		
		}

	}

}

package de.htwg.fivewins;

import java.util.Scanner;

import de.htwg.fivewins.controller.FiveWinsController;
import de.htwg.fivewins.controller.IFiveWinsController;
import de.htwg.fivewins.field.AIAdapter;
import de.htwg.fivewins.field.Field;
import de.htwg.fivewins.field.VerySillyAI;
import de.htwg.fivewins.gui.GameFrame;
import de.htwg.fivewins.tui.TextUI;

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

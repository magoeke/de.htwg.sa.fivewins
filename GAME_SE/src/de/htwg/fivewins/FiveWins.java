package de.htwg.fivewins;

import java.util.Scanner;

import de.htwg.fivewins.controller.FiveWinsController;
import de.htwg.fivewins.field.AIAdapter;
import de.htwg.fivewins.field.Field;
import de.htwg.fivewins.field.VerySillyAI;
import de.htwg.fivewins.tui.TextUI;

public final class FiveWins {
	private FiveWins() { }
	private static int size;
	private static TextUI tui;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); 
		System.out.println("Please insert size of the gamefield: ");
		size = sc.nextInt();
		//tui = new TextUI(new FiveWinsController(new Field(size)));
		//tui.printTUI();
		Field field = new Field(size);
		AIAdapter ai = new VerySillyAI("X", field);
		tui = new TextUI(new FiveWinsController(field, ai));
		tui.printTUI();
		// continue until the user decides to quit
		boolean quit = false;
		while (!quit) {
		    quit = tui.iterate();
		    
		}
		sc.close();
	}

}

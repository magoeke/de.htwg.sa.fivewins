package de.htwg.fivewins;

import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;

import de.htwg.fivewins.controller.FiveWinsController;
import de.htwg.fivewins.controller.IFiveWinsController;
import de.htwg.fivewins.model.field.Field;
import de.htwg.fivewins.view.gui.GameFrame;
import de.htwg.fivewins.view.tui.TextUI;

/**
 * The start class for FiveWins.
 * Singelton is used.
 */
public final class FiveWins {
	private static FiveWins fivewins = null;
	private IFiveWinsController controller;
	private static TextUI tui;
	
	private FiveWins() {
		controller = new FiveWinsController(new Field(1));
		tui = new TextUI(controller);
		new GameFrame(controller);
		//tui.printTUI();
	}
	
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		fivewins = getInstance();
		Scanner scanner;
        boolean continu = false;
        scanner = new Scanner(System.in);
        while (!continu) {  	
            continu = tui.iterate(scanner.next());
            System.out.println(continu);
        }
	}
	
	public static FiveWins getInstance() {
		if(fivewins == null) {
			fivewins = new FiveWins();
		}
		return fivewins;
	}

}

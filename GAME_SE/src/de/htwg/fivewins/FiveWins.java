package de.htwg.fivewins;

import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.htwg.fivewins.controller.FiveWinsController;
import de.htwg.fivewins.controller.IFiveWinsController;
import de.htwg.fivewins.model.field.Field;
import de.htwg.fivewins.view.gui.GameFrame;
import de.htwg.fivewins.view.tui.TextUI;

/**
 * The start class for FiveWins.
 */
public final class FiveWins {
	private static Scanner scanner;
	
	public static void main(String[] args) {
		// Setting up log4j
		PropertyConfigurator.configure("log4j.properties");
		
		// Set up google guice
		Injector injector = Guice.createInjector(new FiveWinsModule());
		
		// Build up application
		@SuppressWarnings("unused")
		IFiveWinsController controller = injector.getInstance(IFiveWinsController.class);
		@SuppressWarnings("unused")
		GameFrame gui = injector.getInstance(GameFrame.class);
		TextUI tui = injector.getInstance(TextUI.class);
		tui.printTUI();
		
		// continue until game ends
		boolean continu = false;
        scanner = new Scanner(System.in);
        while (!continu) {  	
            continu = tui.iterate(scanner.next());
        }
	}
	

}

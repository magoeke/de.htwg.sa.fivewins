package de.htwg.fivewins.view.tui;

import java.util.Scanner;
import java.util.logging.Logger;

import de.htwg.fivewins.controller.IFiveWinsController;
import de.htwg.fivewins.model.ai.AIAdapter;
import de.htwg.util.observer.IObserver;

/**
 * TextUI implementation for FiveWins.
 */
public class TextUI implements IObserver {

	private IFiveWinsController controller;

	private static Logger TextUIlogger = Logger
			.getLogger("de.htwg.fivewins.tui");

	/**
	 * Constructor.
	 * @param controller
	 */
	public TextUI(IFiveWinsController controller) {
		this.controller = controller;
		controller.addObserver(this);
	}

	/*
	 * (non-Javadoc)
	 * @see de.htwg.util.observer.IObserver#update()
	 */
	@Override
	public void update() {
		printTUI();
	}

	/**
	 * Is used for TUI inputs.
	 */
	public boolean iterate(String line) {
		return controller.handleInputOrQuit(line);
	}

	/**
	 * print tui on the console
	 */
	private void printTUI() {
		TextUIlogger.info("\n" + controller.getFieldString() + "\n");
		TextUIlogger.info(controller.getStatus() + "\n");
		TextUIlogger.info("\n");
		TextUIlogger
				.info("Please enter a command( q - quit, u - update, n - new, x,y - set cell(x,y)):\n");
		String winnerSign = controller.winRequest();
		if (winnerSign.equals("X") || winnerSign.equals("O")) {
			TextUIlogger.info("Der Gewinner ist " + winnerSign + "\n");
		} else if (winnerSign.equals("draw")) {
			TextUIlogger.info("It's a draw!");
		}
	}

	/*
	 * resets the controller and update the tui
	 */
	private void reset() {
		controller.reset();
		update();
	}

}

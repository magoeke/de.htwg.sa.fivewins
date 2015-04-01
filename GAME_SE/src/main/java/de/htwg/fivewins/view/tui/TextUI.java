package de.htwg.fivewins.view.tui;

import java.util.logging.Logger;

import com.google.inject.Inject;

import de.htwg.fivewins.controller.IFiveWinsController;
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
	 * 
	 * @param controller
	 */
	@Inject
	public TextUI(IFiveWinsController controller) {
		this.controller = controller;
		controller.addObserver(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.util.observer.IObserver#update()
	 */
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
	public void printTUI() {
		TextUIlogger.info("\n" + controller.getFieldString() + "\n");
		TextUIlogger.info(controller.getStatus() + "\n");
		TextUIlogger.info("\n");
		TextUIlogger
				.info("Please enter a command( q - quit, u - update, n - new, x,y - set cell(x,y)):\n");

		if (controller.getWinner()) {
			TextUIlogger.info("The winner is " + controller.getWinnerSign()
					+ "\n");
		}

		if (controller.getDraw()) {
			TextUIlogger.info("It's a draw!");
		}

	}

	/*
	 * resets the controller and update the tui
	 */
	public void reset() {
		controller.reset();
		update();
	}

}

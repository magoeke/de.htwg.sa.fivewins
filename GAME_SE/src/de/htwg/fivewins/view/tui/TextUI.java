package de.htwg.fivewins.view.tui;

import java.util.Scanner;
import java.util.logging.Logger;

import de.htwg.fivewins.controller.IFiveWinsController;
import de.htwg.fivewins.model.ai.AIAdapter;
import de.htwg.util.observer.IObserver;

/*
 * @author Max, Manuel
 */
public class TextUI implements IObserver {

	private IFiveWinsController controller;
	private Scanner scanner;

	private static Logger TextUIlogger = Logger
			.getLogger("de.htwg.fivewins.tui");

	public TextUI(IFiveWinsController controller) {
		this.controller = controller;
		controller.addObserver(this);
		scanner = new Scanner(System.in);
	}

	/*
	 * needed for the observer
	 */
	@Override
	public void update() {
		printTUI();
	}

	/*
	 * was used when the gui wasn't implemented
	 */
	public boolean iterate(String line) {
		boolean returnValue = false;
//		AIAdapter npc = controller.getSecondPlayer();
//		if (npc != null
//				&& npc.getWhichPlayer().equals(controller.getPlayerSign())) {
//			returnValue = controller.handleInputOrQuit(npc.getCommand());
//		} else {
			returnValue = controller.handleInputOrQuit(line);
//		}
		return returnValue;
	}

	/*
	 * print tui on the console
	 */
	public void printTUI() {
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

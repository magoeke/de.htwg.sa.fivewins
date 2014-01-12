package de.htwg.fivewins.tui;

import java.util.Scanner;
import java.util.logging.Logger;

import de.htwg.fivewins.controller.FiveWinsController;
import de.htwg.fivewins.controller.IFiveWinsController;
import de.htwg.fivewins.field.AIAdapter;
import de.htwg.util.observer.IObserver;

public class TextUI implements IObserver{
	
	private IFiveWinsController controller;
	private Scanner scanner;
	
	private Logger logger = Logger.getLogger("de.htwg.fivewins.tui");
	
	public TextUI(IFiveWinsController controller){
		this.controller = controller;
		//controller.addObserver(this);
		scanner = new Scanner (System.in);	
	}

	@Override
	public void update() {
		printTUI();
	}

	public boolean iterate() {
		boolean returnValue = false;
		AIAdapter npc = controller.getSecondPlayer();
		if(npc != null && npc.getWhichPlayer().equals(controller.getPlayerSign())) {
			returnValue = handleInputOrQuit(npc.getCommand());
		} else {
			returnValue = handleInputOrQuit(scanner.next());
		}
		return returnValue;
	}

	public void printTUI() {
		logger.info("\n" + controller.getFieldString() + "\n");
		logger.info(controller.getStatus() + "\n");
		logger.info("\n");
		logger.info("Please enter a command( q - quit, u - update, n - new, x,y - set cell(x,y)):\n"); 
	}
	
	public boolean handleInputOrQuit(String line) {
		boolean quit=false;
		if (line.equalsIgnoreCase("q")) {
			quit=true;
		}
		if (line.equalsIgnoreCase("u")) {
			//Do nothing, just redraw the updated grid
			update();
		}
		if (line.equalsIgnoreCase("n")) {
			//Restart game
			reset();
		}
		
		if (line.matches("[0-9]{1,2}?,[0-9]{1,2}?")){
			String[] numbers = line.split(",");
			int arg0 = Integer.parseInt(numbers[0]);
			int arg1 = Integer.parseInt(numbers[1]);
			boolean successfulFieldChange = controller.setValue(arg0, arg1, controller.getPlayerSign());
			update();
			if(successfulFieldChange) {
				String winnerSign = controller.winRequest();
				if(winnerSign.equals("X") || winnerSign.equals("O")) {
					logger.info("Der Gewinner ist " + winnerSign + "\n");
					quit = true;
				}
				controller.countTurn();
			}
			
		}

		return quit;
	}

	private void reset() {
		controller.reset();
		update();
	}
	

}

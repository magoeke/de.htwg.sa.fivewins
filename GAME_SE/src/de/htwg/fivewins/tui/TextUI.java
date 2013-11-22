package de.htwg.fivewins.tui;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.htwg.fivewins.controller.FiveWinsController;
import de.htwg.fivewins.field.Field;
import de.htwg.util.observer.IObserver;

public class TextUI implements IObserver{

	
	private FiveWinsController controller;
	private Scanner scanner;
	

	public TextUI(FiveWinsController controller){
		this.controller = controller;
		controller.addObserver(this);
		scanner = new Scanner (System.in);	
	}

	@Override
	public void update() {
		printTUI();
	}

	public boolean iterate() {
		return handleInputOrQuit(scanner.next());
	}

	public void printTUI() {
		System.out.print(controller.getFieldString() + "\n");
		System.out.print(controller.getStatus() + "\n");
		System.out.print("Please enter a command( q - quit, u - update, n - new, 1 - 15 - set size, x y - set cell(x,y)):\n");
	}
	
	public boolean handleInputOrQuit(String line) {
		boolean quit=false;
		if (line.equalsIgnoreCase("q")) {
			quit=true;
		}
		if (line.equalsIgnoreCase("u")) {
			//Do nothing, just redraw the updated grid
		}
		if (line.equalsIgnoreCase("n")) {
			//Restart game
			new TextUI(new FiveWinsController(new Field(3)));
		}
		// if the command line has the form 12, set the cell (1,2) to value 3
		if (line.matches("[0-9]{1,2} [0-9]{1,2}")){
			Pattern p = Pattern.compile("[0-50]");
			Matcher m = p.matcher(line);
			int[] arg = new int[2];
			for (int i = 0; i < arg.length; i++) {
				m.find();
				arg[i] = Integer.parseInt(m.group());
			} 
			controller.setValue(arg[0], arg[1], controller.getPlayerSign());   
		}
		
		return quit;
	}

	
	

}

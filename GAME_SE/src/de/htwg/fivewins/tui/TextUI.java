package de.htwg.fivewins.tui;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.htwg.fivewins.controller.FiveWinsController;
import de.htwg.util.observer.IObserver;

public class TextUI implements IObserver{

	
	private FiveWinsController controller;
	Scanner scanner;
	String line = "";
	

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
		System.out.println(controller.getFieldString());
		System.out.println(controller.getStatus());
		System.out.println("Please enter a command( q - quit, u - update, s - solve, r - reset, n - new, 1 - 50 - set size, x y - show candidates at (x,y), xyz - set cell(x,y) to z):");
	}
	
	public boolean handleInputOrQuit(String line) {
		boolean quit=false;
		if (line.equalsIgnoreCase("q")) {
			quit=true;
		}
		if (line.equalsIgnoreCase("u")) {
			//Do nothing, just redraw the updated grid
		}

		// if the command line has the form 123, set the cell (1,2) to value 3
		if (line.matches("[0-50][0-50]")){
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

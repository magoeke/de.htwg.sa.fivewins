package de.htwg.fivewins.plugin;

import java.util.Random;

import com.google.inject.Inject;

import de.htwg.fivewins.controller.IFiveWinsController;

public class RandomPlugin implements IPlugin{

	private static final int RANGE_TOP = 10;
	private static final int RANGE_BOTTOM = 0;
	private static final int PROBABILITY = 2;
	
	private IFiveWinsController gameController;
	private Random rand;
	private boolean active;
	
	@Inject
	public RandomPlugin(IFiveWinsController gameController) {
		this.gameController = gameController;
		this.rand = new Random();
	}
	
	@Override
	public String getName() {
		return "RandomPlugin";
	}

	@Override
	public void work() {
		int randNum = rand.nextInt((RANGE_TOP - RANGE_BOTTOM) + 1) + RANGE_BOTTOM;
		System.out.println(randNum);
		if((randNum % PROBABILITY) == 0) {
			int randCol =1;
			int randRow =1;
			while(!isFree(randCol, randRow)) {
				randCol =rand.nextInt((getSize()))+1;
				randRow =rand.nextInt((getSize()))+1;
			}
			System.out.println(randCol);
			System.out.println(randRow);
			gameController.setValue(randCol, randRow, gameController.getPlayerSign());
			gameController.setTurn(gameController.getTurn() + 1);
		}
	}
	
	private int getSize() {
		return gameController.getField().length;
	}
	
	private boolean isFree(int column, int row) {
		String[][] field = gameController.getField();
		boolean returnValue = false;
		if ("-".equals(field[column - 1][row - 1])) {
			returnValue = true;
		}
		return returnValue;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

}

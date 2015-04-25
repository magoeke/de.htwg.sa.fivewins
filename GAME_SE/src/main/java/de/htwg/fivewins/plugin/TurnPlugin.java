package de.htwg.fivewins.plugin;

import java.util.Observable;

import com.google.inject.Inject;

import de.htwg.fivewins.controller.IFiveWinsController;

public class TurnPlugin implements IPlugin {

	private static final int MODULO = 4;
	private IFiveWinsController gameController;
	private boolean active = false;
	private int internalCount = 0;

	@Inject
	public TurnPlugin(IFiveWinsController gameController) {
		this.gameController = gameController;;
	}

	@Override
	public String getName() {
		return "2TurnPlugin";
	}

	@Override
	public void work() {
		internalCount++;
		String player = whichPlayer();
		while(!player.equals(gameController.getPlayerSign())) {
			gameController.setTurn(gameController.getTurn() + 1);
		}
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	private String whichPlayer() {
		int result = internalCount % MODULO;
		if (result < 2) {
			return "X";
		} else {
			return "O";
		}
	}

}

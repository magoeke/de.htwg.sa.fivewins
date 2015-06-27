package de.htwg.fivewins.controller.game;

import akka.actor.UntypedActor;
import de.htwg.fivewins.model.field.IField;

public class Horizontal extends UntypedActor {
	
	
	public void onReceive(Object message) {
		if (message instanceof Work) {
			Work work = (Work) message;
			int result = winRequestHorizontal(work.getValue1(), work.getValue2(),
					work.getN(), work.getCurrentPlayer(), work.isOperator(),
					work.getField());

			getSender().tell(new Result(result), getSelf());
		} else {
			unhandled(message);
		}
	}
	
	/*
	 * test the horizontal win request if operator true use minus
	 */
	private int winRequestHorizontal(int value, int fixValue, int n,
			String currentPlayer, boolean operator, IField field) {
		int result = 0;
		if (value < 0 || value >= field.getSize()
				|| !field.getCellValue(value, fixValue).equals(currentPlayer)) {
			return n - 1;
		}

		if (operator) {
			result = winRequestHorizontal(value - 1, fixValue, n + 1,
					currentPlayer, operator, field);
		} else {
			result = winRequestHorizontal(value + 1, fixValue, n + 1,
					currentPlayer, operator, field);
		}
		return result;
	}

}

package de.htwg.fivewins.controller.game;

import de.htwg.fivewins.model.field.IField;
import akka.actor.UntypedActor;


public class DiagonalReflected extends UntypedActor {
	
	public void onReceive(Object message) {
		if (message instanceof Work) {
			Work work = (Work) message;
			int result = winRequestDiagonalReflected(work.getValue1(), work.getValue2(),
					work.getN(), work.getCurrentPlayer(), work.isOperator(),
					work.getField());

			getSender().tell(new Result(result), getSelf());
		} else {
			unhandled(message);
		}
	}
	
	/*
	 * test the other diagonal win request if operator true use plus minus (+ -)
	 */
	private int winRequestDiagonalReflected(int value1, int value2, int n,
			String currentPlayer, boolean operator, IField field) {
		int result = 0;
		if (value1 < 0 || value1 >= field.getSize() || value2 < 0
				|| value2 >= field.getSize()) {
			return n - 1;
		}
		if (!field.getCellValue(value1, value2).equals(currentPlayer)) {
			return n - 1;
		}

		if (operator) {
			result = winRequestDiagonalReflected(value1 + 1, value2 - 1, n + 1,
					currentPlayer, operator, field);
		} else {
			result = winRequestDiagonalReflected(value1 - 1, value2 + 1, n + 1,
					currentPlayer, operator, field);
		}
		return result;
	}
	
}

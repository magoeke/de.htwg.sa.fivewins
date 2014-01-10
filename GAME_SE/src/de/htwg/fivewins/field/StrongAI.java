package de.htwg.fivewins.field;

public class StrongAI extends AIAdapter{
	
	public StrongAI(String sign, Field field) {
		if(sign.equals("X") || sign.equals("O")) {
			this.whichPlayer = sign;
			this.field = field;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	String calculateCommand() {
		// TODO Auto-generated method stub
		return null;
	}

}

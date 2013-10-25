package de.htwg.fivewins.field;

public class Field {

	private int size;
	String[][] gamefield;
	
	public Field(int size) {
		this.size = size;
		gamefield = new String[size][size];
		
		//fill array with default value
		for(int i = 0; i < size; i++) {
			for(int a = 0; a < size; a++) {
				gamefield[i][a] = "0";
			}
		}
	}
	
	@Override
	public String toString() {
		//calculate size for i
		int end = size*2 +1;
		StringBuilder s = new StringBuilder();
		
		for(int i=0; i < end; i++) {
			if((i % 2) == 0) {
				s.append("+-+-+-+-+-+").append( '\n' );
			} else {
				for(int a = 0; a < size; a++) {
					if(gamefield[i][a].equals("0")) {
						s.append("| |");
					} else {
						s.append("|" + gamefield[i][a] + "|");
					}
				}
				s.append( '\n' );
			}
		}
		
		
		return s.toString();
	}
	
}

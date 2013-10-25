package de.htwg.fivewins.field;

public class Field {

	private int size;
	String[][] gamefield;
	String newLine = System.getProperty("line.separator");
	
	public Field(int size) {
		this.size = size;
		gamefield = new String[size][size];
		
		//fill array with default value
		for(int i = 0; i < size; i++) {
			for(int a = 0; a < size; a++) {
				gamefield[i][a] = "-";
			}
		}
	}
	
	@Override
	public String toString() {
		//calculate size for i
		StringBuilder s = new StringBuilder();
		
		for(int i = 0; i < size; i++) {
			for(int a = 0; a < size; a++) {
				s.append(" " + gamefield[i][a]);
			}
			s.append(newLine);
		}
		
		
		return s.toString();
	}
	
	public String getCellValue(int column, int row) {
		//input must be right
		return gamefield[column][row];
	}
	
	public void setValue(int column, int row, String value) {
		gamefield[column][row] = value;
	}
	
}

package de.htwg.fivewins.field;

public class Field implements IField {

	private int size;
	private String[][] gamefield;
	private String newLine = System.getProperty("line.separator");
	public final int bottomBorder = 0;
	public final int topBorder = 20;
	
	public Field(int size) {
		if(size < bottomBorder || size > topBorder) {
			throw new IllegalArgumentException("Size has to be between 1 and 20.");
		}
		
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
		StringBuilder s = new StringBuilder();
		
		for(int i = 0; i < size; i++) {
			for(int a = 0; a < size; a++) {
				s.append(" " + gamefield[a][i]);
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
	
	public int getSize() {
		return size;
	}
}

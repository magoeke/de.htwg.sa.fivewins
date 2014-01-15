package de.htwg.fivewins.field;

public interface IField {
	
	/*
	 * @param column
	 * @param row
	 * @return specific cell value
	 */
	String getCellValue(int column, int row);
	
	/*
	 * set value in cell [column] [row]
	 * @param column
	 * @param row
	 */
	void setValue(int column, int row, String value);
	
	/*
	 * @return returns the size of the gamefield
	 */
	int getSize();
	
	/*
	 * 
	 */
	String[][] getGameField();
}

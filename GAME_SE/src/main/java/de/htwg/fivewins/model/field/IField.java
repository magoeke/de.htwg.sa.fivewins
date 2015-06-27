package de.htwg.fivewins.model.field;

/**
 * Interface for a FiveWins gamefield.
 */
public interface IField {

	/**
	 * @param column
	 * @param row
	 * @return specific cell value
	 */
	String getCellValue(int column, int row);

	/**
	 * set value in cell [column] [row]
	 * 
	 * @param column
	 * @param row
	 */
	void setValue(int column, int row, String value);

	/**
	 * @return the size of the gamefield
	 */
	int getSize();

	/**
	 * @return the gamefield(a 2 dimensional array).
	 */
	String[][] getGameField();

	/**
	 * Resets gamefield. Fills in default value.
	 */
	void reset();

	/**
	 * 
	 * @return
	 */
	String getId();
	
	/**
	 * Sets id. Is needed for database.
	 * @param id
	 */
	void setId(String id);
	
	/**
	 * Sets field. Is needed for loading a field from database.
	 * @param field
	 */
	void setField(String[][] field);
}

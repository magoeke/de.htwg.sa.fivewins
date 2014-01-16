package de.htwg.fivewins.field;

/*
 * @author Max, Manuel
 */
public class Field implements IField {

	private int size;
	private String[][] gamefield;
	//used for the wordwrap
	private String newLine = System.getProperty("line.separator");
	//min. size is 1
	public static final int BOTTOMBORDER = 0;
	//max. size is 20
	public static final int TOPBORDER = 20;
	
	/*
	 * initialized Object 
	 */
	public Field(int size) {
		if(size < BOTTOMBORDER || size > TOPBORDER) {
			throw new IllegalArgumentException("Size has to be between 1 and 20.");
		}
		
		this.size = size;
		gamefield = new String[size][size];
		
		/*
		 * fill array with default value '-'
		 * so never use '-' as a player sign
		 */
		for(int i = 0; i < size; i++) {
			for(int a = 0; a < size; a++) {
				gamefield[i][a] = "-";
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 
	 * @return the whole gamefield in a String ready to print out on the console
	 */
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
	
	public void reset() {		
		//fill array with default value
		for(int i = 0; i < size; i++) {
			for(int a = 0; a < size; a++) {
				gamefield[i][a] = "-";
			}
		}
	}

	public String[][] getGameField() {
		return gamefield;
	}
}

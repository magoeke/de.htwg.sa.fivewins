package de.htwg.fivewins.model.field;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/*
 * @author Max, Manuel
 */
public class Field implements IField {

	private int size;
	private String[][] gamefield;
	//used for the wordwrap
	private String newLine = System.getProperty("line.separator");
	public static final int BOTTOMBORDER = 0;
	public static final int TOPBORDER = 20;
	
	/**
	 * Constructor.
	 * @param size
	 */
	@Inject
	public Field(@Named("fieldSize")int size) {
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
	
	/** 
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
	
	/*
	 * (non-Javadoc)
	 * @see de.htwg.fivewins.model.field.IField#getCellValue(int, int)
	 */
    public String getCellValue(int column, int row) {
		//input must be right
		return gamefield[column][row];
	}
	
    /*
     * (non-Javadoc)
     * @see de.htwg.fivewins.model.field.IField#setValue(int, int, java.lang.String)
     */
	public void setValue(int column, int row, String value) {
		gamefield[column][row] = value;
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.htwg.fivewins.model.field.IField#getSize()
	 */
	public int getSize() {
		return size;
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.htwg.fivewins.model.field.IField#reset()
	 */
	public void reset() {		
		//fill array with default value
		for(int i = 0; i < size; i++) {
			for(int a = 0; a < size; a++) {
				gamefield[i][a] = "-";
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see de.htwg.fivewins.model.field.IField#getGameField()
	 */
	public String[][] getGameField() {
		return gamefield;
	}
}

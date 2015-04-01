package de.htwg.fivewins.model.field;

public class FieldFactory implements IFieldFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.model.field.IFieldFactory#create(int)
	 */
	public IField create(int fieldsize) {
		return new Field(fieldsize);
	}

}

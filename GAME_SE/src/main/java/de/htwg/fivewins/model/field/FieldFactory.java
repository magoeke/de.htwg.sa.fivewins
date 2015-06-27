package de.htwg.fivewins.model.field;

public class FieldFactory implements IFieldFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.model.field.IFieldFactory#create(int)
	 */
	@Override
	public IField create(int fieldsize) {
		return new Field(fieldsize);
	}

}

package de.htwg.fivewins.model.field;

public interface IFieldFactory {

	/**
	 * Creates an IField Object.
	 * 
	 * @param fieldesize
	 * @return
	 */
	IField create(int fieldesize);

}

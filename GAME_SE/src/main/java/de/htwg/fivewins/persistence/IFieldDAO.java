package de.htwg.fivewins.persistence;

import de.htwg.fivewins.model.field.IField;
import java.util.List;

public interface IFieldDAO {

	/**
	 * Saves gamefield.
	 * @param field
	 */
	void saveField(final IField field);

	/**
	 * Check if field with id exist.
	 * @param id
	 * @return
	 */
	boolean containsFieldById(final String id);

	/**
	 * Returns a field with the id or null
	 * @param id
	 * @return
	 */
	IField getFieldById(final String id);

	/**
	 * Deletes a field if field is saved
	 * @param id
	 */
	void deleteFieldById(final String id);

	/**
	 * Returns a List with all saved games.
	 * @return
	 */
	List<IField> getAllFields();

}

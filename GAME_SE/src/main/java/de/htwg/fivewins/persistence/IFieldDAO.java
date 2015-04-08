package de.htwg.fivewins.persistence;

import de.htwg.fivewins.model.field.IField;
import java.util.List;

public interface IFieldDAO {

	void saveField(final IField Field);

	boolean containsFieldById(final String id);

	IField getFieldById(final String id);

	void deleteFieldById(final String id);

	List<IField> getAllFields();

}

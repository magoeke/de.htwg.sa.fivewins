package de.htwg.fivewins.persistence.db4o;

import java.util.List;

import de.htwg.fivewins.model.field.IField;
import de.htwg.fivewins.persistence.IFieldDAO;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

public class FieldDb4oDAO implements IFieldDAO {
	
	private ObjectContainer db;
	
	/**
	 * Constructor.
	 */
	public  FieldDb4oDAO() {
		this.db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
				"field.data");
	}

	/*
	 * (non-Javadoc)
	 * @see de.htwg.fivewins.persistence.IFieldDAO#saveField(de.htwg.fivewins.model.field.IField)
	 */
	@Override
	public void saveField(IField field) {
		db.store(field);
		
	}

	/*
	 * (non-Javadoc)
	 * @see de.htwg.fivewins.persistence.IFieldDAO#containsFieldById(java.lang.String)
	 */
	@Override
	public boolean containsFieldById(final String id) {
		List<IField> fields = db.query(new Predicate<IField>() {

			private static final long serialVersionUID = 1L;

			public boolean match(IField grid) {
				return (id.equals(grid.getId()));
			}
		});

		if (fields.size() > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see de.htwg.fivewins.persistence.IFieldDAO#getFieldById(java.lang.String)
	 */
	@Override
	public IField getFieldById(final String id) {

		List<IField> fields = db.query(new Predicate<IField>() {

			private static final long serialVersionUID = 1L;

			public boolean match(IField grid) {
				return (id.equals(grid.getId()));
			}
		});

		if (fields.size() > 0) {
			return fields.get(0);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see de.htwg.fivewins.persistence.IFieldDAO#deleteFieldById(java.lang.String)
	 */
	@Override
	public void deleteFieldById(String id) {
		db.delete(getFieldById(id));
		
	}

	/*
	 * (non-Javadoc)
	 * @see de.htwg.fivewins.persistence.IFieldDAO#getAllFields()
	 */
	@Override
	public List<IField> getAllFields() {
		return db.query(IField.class);
	}

}

package de.htwg.fivewins.persistence.db4o;

import java.util.List;

import de.htwg.fivewins.model.field.IField;
import de.htwg.fivewins.persistence.IFieldDAO;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

public class FieldDb4oDAO implements IFieldDAO {
	
	private ObjectContainer db;
	
	public  FieldDb4oDAO() {
		this.db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
				"field.data");
	}

	@Override
	public void saveField(IField field) {
		db.store(field);
		
	}

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

	@Override
	public void deleteFieldById(String id) {
		db.delete(getFieldById(id));
		
	}

	@Override
	public List<IField> getAllFields() {
		return db.query(IField.class);
	}

}

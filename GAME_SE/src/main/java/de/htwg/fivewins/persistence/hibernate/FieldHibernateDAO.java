package de.htwg.fivewins.persistence.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.Gson;

import de.htwg.fivewins.model.field.FieldFactory;
import de.htwg.fivewins.model.field.IField;
import de.htwg.fivewins.model.field.IFieldFactory;
import de.htwg.fivewins.persistence.IFieldDAO;

public class FieldHibernateDAO implements IFieldDAO{
	
	/*
	 * Converts a PersistenField to IField.
	 */
	private IField copyField(PersistentField pfield) {
		if(pfield == null) {
			return null;
		}
		IFieldFactory fieldFactory = new FieldFactory();
		
		IField field = fieldFactory.create(pfield.getSize());
		field.setId(pfield.getID());
		// JSON to Array
		Gson jsonField = new Gson();
		
		field.setField(jsonField.fromJson(pfield.getField(), String[][].class));

		return field;
	}
	
	/*
	 * Converts a IFlied to a PersistentField.
	 */
	private PersistentField copyField(IField field) {
		if(field == null) {
			return null;
		}
		
		String fieldId = field.getId();
		PersistentField pfield;
		if(containsFieldById(fieldId)) {
			// The Object already exists within the session
			Session session = HibernateUtil.getInstance().getCurrentSession();
			pfield = (PersistentField) session.get(PersistentField.class, fieldId);
									
		} else {
			// A new database entry
			pfield = new PersistentField();			
		}
		
		pfield.setID(field.getId());
		Gson jsonField = new Gson();
		pfield.setField(jsonField.toJson(field.getGameField()));
		pfield.setSize(field.getSize());
		return pfield;
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.htwg.fivewins.persistence.IFieldDAO#saveField(de.htwg.fivewins.model.field.IField)
	 */
	@Override
	public void saveField(IField field) {
		Transaction tx = null;
		Session session = null;

		try {
			session = HibernateUtil.getInstance().getCurrentSession();
			tx = session.beginTransaction();
			
			PersistentField pfield = copyField(field);
			
			session.saveOrUpdate(pfield);

			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see de.htwg.fivewins.persistence.IFieldDAO#containsFieldById(java.lang.String)
	 */
	@Override
	public boolean containsFieldById(String id) {
		if(getFieldById(id) != null) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see de.htwg.fivewins.persistence.IFieldDAO#getFieldById(java.lang.String)
	 */
	@Override
	public IField getFieldById(String id) {
		Session session = HibernateUtil.getInstance().getCurrentSession();
		session.beginTransaction();
		
		return copyField((PersistentField) session.get(PersistentField.class, id));
	}

	/*
	 * (non-Javadoc)
	 * @see de.htwg.fivewins.persistence.IFieldDAO#deleteFieldById(java.lang.String)
	 */
	@Override
	public void deleteFieldById(String id) {
		Transaction tx = null;
		Session session = null;

		try {
			session = HibernateUtil.getInstance().getCurrentSession();
			tx = session.beginTransaction();
			
			PersistentField pfield = (PersistentField) session.get(PersistentField.class, id);
			session.delete(pfield);

			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see de.htwg.fivewins.persistence.IFieldDAO#getAllFields()
	 */
	@Override
	public List<IField> getAllFields() {
		Session session = HibernateUtil.getInstance().getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(PersistentField.class);
		
		@SuppressWarnings("unchecked")
		List<PersistentField> results = criteria.list();

		List<IField> fields = new ArrayList<IField>();
		for (PersistentField pfield : results) {
			IField field = copyField(pfield);
			fields.add(field);
		}
		return fields;
	}

}

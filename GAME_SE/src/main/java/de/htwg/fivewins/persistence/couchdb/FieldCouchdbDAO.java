package de.htwg.fivewins.persistence.couchdb;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import com.google.gson.Gson;

import de.htwg.fivewins.model.field.FieldFactory;
import de.htwg.fivewins.model.field.IField;
import de.htwg.fivewins.model.field.IFieldFactory;
import de.htwg.fivewins.persistence.IFieldDAO;
import de.htwg.fivewins.persistence.couchdb.PersistentField;

public class FieldCouchdbDAO implements IFieldDAO{

	private CouchDbConnector db = null;
	private Logger logger = Logger.getLogger("de.htwg.fivewins.persistence.couchdb");
	
	/**
	 * Constructor.
	 * Sets up connection to couchdb.
	 */
	public FieldCouchdbDAO() {
		HttpClient client = null;
		try {
			client = new StdHttpClient.Builder().url(
					"http://lenny2.in.htwg-konstanz.de:5984").build();

		} catch (MalformedURLException e) {
		    logger.error("Malformed URL", e);
		}
		CouchDbInstance dbInstance = new StdCouchDbInstance(client);
		db = dbInstance.createConnector("fivewins_db", true);
		db.createDatabaseIfNotExists();
	}
	
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
	
	private PersistentField copyField(IField field) {
		if(field == null) {
			return null;
		}
		
		String fieldId = field.getId();
		PersistentField pfield;
		if(containsFieldById(fieldId)) {
			// The Object already exists
			pfield = (PersistentField) db.find(PersistentField.class, fieldId);
									
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

	
	@Override
	public void saveField(IField field) {
		if (containsFieldById(field.getId())) {
			db.update(copyField(field));
		} else {
			db.create(field.getId(), copyField(field));
		}
	}

	@Override
	public boolean containsFieldById(String id) {
		if (getFieldById(id) == null) {
			return false;
		}
		return true;
	}

	@Override
	public IField getFieldById(String id) {
		PersistentField pfield = db.find(PersistentField.class, id);
		if (pfield == null) {
			return null;
		}
		return copyField(pfield);
	}

	@Override
	public void deleteFieldById(String id) {
		db.delete(copyField(getFieldById(id)));
	}

	@Override
	public List<IField> getAllFields() {
		ViewQuery query = new ViewQuery().allDocs().includeDocs(true);
		
		List<IField> fields = new ArrayList<IField>();
		for (PersistentField pfield : db.queryView(query, PersistentField.class)) {
			fields.add(copyField(pfield));
		}

		return fields;
	}

}

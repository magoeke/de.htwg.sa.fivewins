package de.htwg.fivewins.persistence.couchdb;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

public class PersistentField extends CouchDbDocument{

	private static final long serialVersionUID = 1L;
	
	// PRIMARY KEY
	@TypeDiscriminator
	private String id;
	private int size;
	private String field;

	public PersistentField() {
	}

	public String getID() {
		return id;
	}
	
	public int getSize() {
		return size;
	}
	
	public String getField() {
		return field;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	

	public void setField(String field) {
		this.field = field;
	}

}

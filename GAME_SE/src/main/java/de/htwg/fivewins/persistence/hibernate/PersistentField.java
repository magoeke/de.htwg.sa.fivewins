package de.htwg.fivewins.persistence.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fivewinsfield")
public class PersistentField implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name= "id")
	private String id;
	
	@Column(name="field")
	private String field;
	
	@Column(name="size")
	private int size;
	
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

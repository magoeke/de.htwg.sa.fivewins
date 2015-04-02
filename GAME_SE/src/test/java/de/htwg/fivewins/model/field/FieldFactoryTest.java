package de.htwg.fivewins.model.field;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FieldFactoryTest {

	IFieldFactory fieldFactory;
	
	
	@Before
	public void setUp() {
		fieldFactory = new FieldFactory();	
	}
	
	@Test
	public void testCreate() {
		final int fieldsize = 1;
		assertTrue(fieldFactory.create(fieldsize) instanceof Field);
	}
}

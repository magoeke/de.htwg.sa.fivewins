package de.htwg.fivewins.field;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.fivewins.field.Field;

public class FieldTest {
	
	Field field1, field2, field3;
	String newLine = System.getProperty("line.separator");
	
	@Before
	public void setUp()  {
		field1 = new Field(1);
		field2 = new Field(2);
		field3 = new Field(3);
	}

	@Test
	public void testToString() {
		assertEquals(" -"+newLine, field1.toString());
		assertEquals(" - -"+newLine+" - -"+newLine, field2.toString());
	}
	
	@Test
	public void testGetCellValue() {
		assertEquals("-", field1.getCellValue(0, 0));
	}

	@Test
	public void testSetValue() {
		field1.setValue(0, 0, "x");
		assertEquals("x", field1.getCellValue(0, 0));
	}
}

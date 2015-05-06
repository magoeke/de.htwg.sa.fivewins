package de.htwg.fivewins.model.ai;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.htwg.fivewins.model.ai.VerySillyAI;
import de.htwg.fivewins.model.field.Field;
import de.htwg.fivewins.model.field.IField;

/*
 * @author max
 */
public class VerySillyAITest {
	
	VerySillyAI vs1, vs2;
	Field field1;
	String newLine = System.getProperty("line.separator");
	
	@Before
	public void setUp()  {
		field1 = new Field(1);
		vs1 = new VerySillyAI("X", field1);
	}
	
	@Test
	public void testCalculateCommand() {
		assertEquals("1,1", vs1.calculateCommand());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFalseStringConstructor() {  
	     vs2 = new VerySillyAI("i", field1);
	}
	
	@Test
	public void testGetWhichPlayer() {
		assertEquals("X", vs1.getWhichPlayer());
	}
	
	@Test
	public void testGetCommand() {
		assertEquals("1,1", vs1.getCommand());
	}
	
	// Tests for AIAdapter
	@Test
	public void testUpdateField() {
		IField field = new Field(3);
		vs1.updateField(field);
		assertEquals(field, vs1.getField());
	}
	
	@Test
	public void testSetOpponent() {
		// First opponent
		vs1.setOpponent("X");
		assertEquals("O", vs1.getOpponent());
		
		// Second opponent
		vs1.setOpponent("O");
		assertEquals("X", vs1.getOpponent());
	}

	
}

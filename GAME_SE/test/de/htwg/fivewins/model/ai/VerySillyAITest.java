package de.htwg.fivewins.model.ai;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.htwg.fivewins.model.ai.VerySillyAI;
import de.htwg.fivewins.model.field.Field;

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

	
}

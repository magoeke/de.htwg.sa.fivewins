package de.htwg.fivewins.model.ai;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.htwg.fivewins.model.ai.StrongAI;
import de.htwg.fivewins.model.field.Field;
/*
 * @author manuel
 */
public class StrongAITest {

	StrongAI s1, s2, s3;
	Field field1;
	String newLine = System.getProperty("line.separator");
	
	@Before
	public void setUp()  {
		field1 = new Field(1);
		s1 = new StrongAI("X", field1);
		s3 = new StrongAI("X", new Field(10));
	}
	
	@Test
	public void testGetCommand() {
		assertEquals("1,1", s1.getCommand());
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testFalseStringConstructor() {  
	     s2 = new StrongAI("i", field1);
	}
	



}

package de.htwg.fivewins.field;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class StrongAITest {

	StrongAI s1, s2, s3;
	Field field1;
	String newLine = System.getProperty("line.separator");
	
	@Before
	public void setUp()  {
		field1 = new Field(1);
		s1 = new StrongAI("X", field1);
		s3 = new StrongAI("X", new Field(2));
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

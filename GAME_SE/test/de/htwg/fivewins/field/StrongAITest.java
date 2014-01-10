package de.htwg.fivewins.field;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class StrongAITest {

	StrongAI s1, s2;
	Field field1;
	String newLine = System.getProperty("line.separator");
	
	@Before
	public void setUp()  {
		field1 = new Field(1);
		s1 = new StrongAI("X", field1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFalseStringConstructor() {  
	     s2 = new StrongAI("i", field1);
	}
	
	@Test
	public void testCalculateCommand() {
		assertEquals(null, s1.calculateCommand());
	}

}

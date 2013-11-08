package de.htwg.fivewins.field;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

	Player p1;
	Player p2;
	
	@Before
	public void setUp()  {
		p1 = new Player("x");
		p2 = new Player("y");
	}
	
	@Test
	public void testToString() {
		assertEquals("x", p1.toString());
		assertEquals("y", p2.toString());
	}
	
	

}

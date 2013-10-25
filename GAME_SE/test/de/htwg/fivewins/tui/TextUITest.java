package de.htwg.fivewins.tui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.fivewins.controller.FiveWinsController;
import de.htwg.fivewins.field.Field;

public class TextUITest {

	private Field field;
	private FiveWinsController controller1;
	private TextUI tui1;

	@Before
	public void setUp() throws Exception {
		field = new Field(1);
		controller1 = new FiveWinsController(field);
		tui1 = new TextUI(controller1);
	}

	@Test
	public void testHandleInput() {
		tui1.handleInputOrQuit("00");
		assertEquals(1,field.getCellValue(0, 0));
		
		assertTrue(tui1.handleInputOrQuit("q"));
	}

}

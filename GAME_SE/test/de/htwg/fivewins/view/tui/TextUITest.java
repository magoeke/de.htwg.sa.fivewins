package de.htwg.fivewins.view.tui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.fivewins.controller.FiveWinsController;
import de.htwg.fivewins.model.ai.VerySillyAI;
import de.htwg.fivewins.model.field.Field;
import de.htwg.fivewins.view.tui.TextUI;

/*
 * @author max
 */
public class TextUITest {

	private Field field, field2;
	private FiveWinsController controller1, controller2, controllerAI;
	private TextUI tui1, tui2, tuiAI;

	@Before
	public void setUp() throws Exception {
		field = new Field(1);
		field2 = new Field(3);
		controller1 = new FiveWinsController(field);
		controller2 = new FiveWinsController(field2);
		controllerAI = new FiveWinsController(field, new VerySillyAI("X", field));
		tui1 = new TextUI(controller1);
		tui2 = new TextUI(controller2);
		tuiAI = new TextUI(controllerAI);
	}

//	@Test
//	public void testHandleInputOrQuit() {
//		assertTrue(tui1.handleInputOrQuit("q"));
//		assertFalse(tui1.handleInputOrQuit("u"));
//		assertFalse(tui1.handleInputOrQuit("r"));
//		tui1.handleInputOrQuit("1,1");
//		assertEquals("X", field.getCellValue(0, 0));
//	}
//	
//	@Test
//	public void testReset() {
//		field2.setValue(0, 0, "X");
//		tui2.handleInputOrQuit("n");
//		assertEquals("-", field.getCellValue(0, 0));
//	}
//	
//	public void prepareDraw() {
//		field2.setValue(0, 0, "A");
//		field2.setValue(0, 1, "B");
//		field2.setValue(0, 2, "C");
//		field2.setValue(1, 0, "D");
//		field2.setValue(1, 1, "E");
//		field2.setValue(1, 2, "F");
//		field2.setValue(2, 0, "G");
//		field2.setValue(2, 1, "H");
//	}
//	
//	@Test
//	public void testDraw() {
//		prepareDraw();
//		tui2.handleInputOrQuit("3,3");
//	}
//	
//	@Test
//	public void testIterateAI() {
//		tuiAI.iterate();
//	}
//

}

package de.htwg.fivewins.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.fivewins.field.Field;
import de.htwg.fivewins.field.VerySillyAI;
/*
 * @author max,manuel
 */
public class FiveWinsControllerTest {

	VerySillyAI vsai;
	FiveWinsController controller, controller2, controllerAI;
	Field field1, field3;
	String newLine = System.getProperty("line.separator");
	
	@Before
	public void setUp() throws Exception {
		field1  = new Field(1);
		field3 = new Field(3);
		controller = new FiveWinsController(field1);
		controller2 = new FiveWinsController(new Field(6));
		vsai = new VerySillyAI("X", field3);
		controllerAI = new FiveWinsController(field3, vsai);
	}

	@Test
	public void testGetStatus() {
		assertEquals("Welcome to HTWG Five Wins!", controller.getStatus());
	}
	
	@Test
	public void testSetValue() {
		controller2.setValue(1, 1, "x");
		assertEquals("The cell 1 1 was successfully set.", controller2.getStatus());
		controller2.setValue(1, 1, "x");
		assertEquals("The cell 1 1 is already set.", controller2.getStatus());
	}
	
	@Test
	public void testGetCurrentPlayer() {
		assertEquals("X", controller.getPlayerSign());
		controller.countTurn();
		assertEquals("O", controller.getPlayerSign());
	}
	
	@Test
	public void testGetFieldString() {
		assertEquals(" -"+newLine, controller.getFieldString());
	}
	
	@Test
	public void testGetWinner() {
		assertFalse(controller.getWinner());
	}
	
	@Test
	public void testGetWinnerSign() {
		assertEquals(null, controller.getWinnerSign());
	}
	
	@Test
	public void testWinRequest() {
		assertEquals("", controller.winRequest());
		controller.setValue(1, 1, "X");
		assertEquals("X", controller.winRequest());
	}
	
	@Test
	public void testReset() {
		controller.reset();
		assertEquals("Welcome to HTWG Five Wins!", controller.getStatus());
	}
	
	@Test
	public void testGetSecondPlayer() {
		assertEquals(vsai, controllerAI.getSecondPlayer());
	}
	
	public void prepareDraw() {
		field3.setValue(0, 0, "A");
		field3.setValue(0, 1, "B");
		field3.setValue(0, 2, "C");
		field3.setValue(1, 0, "D");
		field3.setValue(1, 1, "E");
		field3.setValue(1, 2, "F");
		field3.setValue(2, 0, "G");
		field3.setValue(2, 1, "H");
		field3.setValue(2, 2, "I");
	}

	@Test
	public void testGetTurn() {
		assertEquals(controller.getTurn(), 0);
	}
	
	@Test
	public void testGetDraw() {
		assertFalse(controller.getDraw());
		prepareDraw();
		controllerAI.winRequest();
		assertTrue(controllerAI.getDraw());
	}
}

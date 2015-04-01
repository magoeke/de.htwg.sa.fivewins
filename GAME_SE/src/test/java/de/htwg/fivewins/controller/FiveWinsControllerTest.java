package de.htwg.fivewins.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.fivewins.model.ai.VerySillyAI;
import de.htwg.fivewins.model.field.Field;
import de.htwg.fivewins.model.field.FieldFactory;
import de.htwg.fivewins.model.field.IFieldFactory;
/*
 * @author max,manuel
 */
public class FiveWinsControllerTest {

	VerySillyAI vsai;
	FiveWinsController controller, controller2, controllerAI;
	Field field1, field3;
	IFieldFactory fieldFactory1;
	String newLine = System.getProperty("line.separator");
	
	@Before
	public void setUp() throws Exception {
		field1  = new Field(1);
		field3 = new Field(3);
		fieldFactory1 = new FieldFactory();
		controller = new FiveWinsController(fieldFactory1);
		controller2 = new FiveWinsController(new FieldFactory());
		vsai = new VerySillyAI("X", field3);
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
		controller.resizeGameField(1);
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
		controller.resizeGameField(1);
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
		assertEquals(null, controller.getSecondPlayer());
		controller.createAI("silly");
		assertNotEquals(null, controller.getSecondPlayer());
		
	}
	
	@Test
	public void testGetTurn() {
		assertEquals(controller.getTurn(), 0);
	}
	
}

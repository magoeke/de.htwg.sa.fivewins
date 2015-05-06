package de.htwg.fivewins.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.htwg.fivewins.controller.game.FiveWinsController;
import de.htwg.fivewins.controller.game.IFiveWinsController;
import de.htwg.fivewins.model.field.Field;
import de.htwg.fivewins.model.field.FieldFactory;
import de.htwg.fivewins.model.field.IField;
import de.htwg.fivewins.model.field.IFieldFactory;
import de.htwg.fivewins.persistence.IFieldDAO;


public class FiveWinsControllerTest {

	FiveWinsController controller, controller2, controllerAI;
	IFieldFactory fieldFactory1;
	String newLine = System.getProperty("line.separator");
	IFieldDAO mockFieldDAO;
	
	@Before
	public void setUp() throws Exception {
		mockFieldDAO = Mockito.mock(IFieldDAO.class);
		fieldFactory1 = new FieldFactory();
		controller = new FiveWinsController(fieldFactory1, mockFieldDAO);
		controller2 = new FiveWinsController(new FieldFactory(), mockFieldDAO);
		controllerAI = new FiveWinsController(new FieldFactory(), mockFieldDAO);
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
	
	@Test
	public void testGetField() {
		IField field = new Field(1);
		IFieldFactory mockFieldFactory = Mockito.mock(IFieldFactory.class);
		Mockito.when(mockFieldFactory.create(Mockito.anyInt())).thenReturn(field);
		IFiveWinsController tmpController = new FiveWinsController(mockFieldFactory, mockFieldDAO);
		assertEquals(field.getGameField(), tmpController.getField());	
	}
	
	@Test
	public void testGetDraw() {
		assertFalse(controller.getDraw());
	}
	
	@Test
	public void testHandleInputOrQuit() {
		// quit game
		assertTrue(controller.handleInputOrQuit("q"));
		// set game stone
		assertFalse(controller.handleInputOrQuit("1,1"));
		assertFalse(controller.handleInputOrQuit("1,1"));
		// create AI and handle input
		controllerAI.createAI("silly");
		assertFalse(controllerAI.handleInputOrQuit("1,1"));
		
	}
	
}

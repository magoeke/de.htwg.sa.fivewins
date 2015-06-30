package de.htwg.fivewins.plugin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.htwg.fivewins.controller.game.FiveWinsController;
import de.htwg.fivewins.controller.game.IFiveWinsController;
import de.htwg.fivewins.model.field.FieldFactory;
import de.htwg.fivewins.model.field.IFieldFactory;
import de.htwg.fivewins.persistence.IFieldDAO;

public class TurnPluginTest {
	
	TurnPlugin turnPlugin = null;
	IFieldFactory fieldFactory1;
	IFieldDAO mockFieldDAO;
	IFiveWinsController gameController;

	@Before
	public void setUp() {
		mockFieldDAO = Mockito.mock(IFieldDAO.class);
		fieldFactory1 = new FieldFactory();
		gameController = new FiveWinsController(fieldFactory1, mockFieldDAO);
		turnPlugin = new TurnPlugin(gameController);
	}
	
	@Test
	public void testGetName() {
		assertEquals("2TurnPlugin", turnPlugin.getName());
	}
	
	@Test
	public void testActive() {
		assertFalse(turnPlugin.isActive());
		
		turnPlugin.setActive(true);
		
		assertTrue(turnPlugin.isActive());
	}
	
	@Test
	public void testWork() {
		int oldTurn = gameController.getTurn();
		turnPlugin.work();
		assertTrue(oldTurn == gameController.getTurn());
	}
}

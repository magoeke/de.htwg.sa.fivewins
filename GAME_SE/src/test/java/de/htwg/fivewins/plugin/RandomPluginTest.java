package de.htwg.fivewins.plugin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.htwg.fivewins.controller.game.FiveWinsController;
import de.htwg.fivewins.controller.game.IFiveWinsController;
import de.htwg.fivewins.model.field.FieldFactory;
import de.htwg.fivewins.model.field.IFieldFactory;
import de.htwg.fivewins.persistence.IFieldDAO;

public class RandomPluginTest {

	RandomPlugin randomPlugin = null;
	IFieldFactory fieldFactory1;
	IFieldDAO mockFieldDAO;
	IFiveWinsController gameController;

	@Before
	public void setUp() {
		mockFieldDAO = Mockito.mock(IFieldDAO.class);
		fieldFactory1 = new FieldFactory();
		gameController = new FiveWinsController(fieldFactory1, mockFieldDAO);
		randomPlugin = new RandomPlugin(gameController);
	}
	
	@Test
	public void testGetName() {
		assertEquals("RandomPlugin", randomPlugin.getName());
	}
	
	@Test
	public void testActive() {
		assertFalse(randomPlugin.isActive());
		
		randomPlugin.setActive(true);
		
		assertTrue(randomPlugin.isActive());
	}
	
	@Test
	public void testWork() {
		int oldTurn = gameController.getTurn();
		randomPlugin.work();
		assertTrue(oldTurn != gameController.getTurn());
	}
	
}

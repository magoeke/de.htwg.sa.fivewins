package de.htwg.fivewins.view.tui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.htwg.fivewins.controller.game.FiveWinsController;
import de.htwg.fivewins.controller.game.IFiveWinsController;
import de.htwg.fivewins.controller.plugin.PluginController;
import de.htwg.fivewins.model.field.FieldFactory;
import de.htwg.fivewins.model.field.IFieldFactory;
import de.htwg.fivewins.persistence.IFieldDAO;

public class TextUITest {

	TextUI textUI;
	PluginController mockPluginController;
	
	@Before
	public void setUp() {
		// GameController
		IFieldDAO mockFieldDAO = Mockito.mock(IFieldDAO.class);
		IFieldFactory tmpFieldFactory = new FieldFactory();
		IFiveWinsController tmpController = new FiveWinsController(tmpFieldFactory, mockFieldDAO);
		// Plugins list
		mockPluginController = Mockito.mock(PluginController.class);
		textUI = new TextUI(tmpController, mockPluginController);
	}
	
	@Test
	public void testIterate() {
		final String testCommand1 = "1,1";
		assertFalse(textUI.iterate(testCommand1));
		final String testCommand2 = "q";
		assertTrue(textUI.iterate(testCommand2));
	}
}
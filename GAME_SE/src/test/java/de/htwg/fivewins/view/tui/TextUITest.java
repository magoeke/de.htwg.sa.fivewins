package de.htwg.fivewins.view.tui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.htwg.fivewins.controller.FiveWinsController;
import de.htwg.fivewins.controller.IFiveWinsController;
import de.htwg.fivewins.model.field.FieldFactory;
import de.htwg.fivewins.model.field.IFieldFactory;
import de.htwg.fivewins.persistence.IFieldDAO;

public class TextUITest {

	TextUI textUI;
	
//	@Before
//	public void setUp() {
//		IFieldDAO mockFieldDAO = Mockito.mock(IFieldDAO.class);
//		IFieldFactory tmpFieldFactory = new FieldFactory();
//		IFiveWinsController tmpController = new FiveWinsController(tmpFieldFactory, mockFieldDAO);
//		textUI = new TextUI(tmpController);
//	}
//	
//	@Test
//	public void testIterate() {
//		final String testCommand = "1,1";
//		assertFalse(textUI.iterate(testCommand));
//	}
}
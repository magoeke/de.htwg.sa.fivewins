package de.htwg.fivewins.view.tui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.fivewins.controller.FiveWinsController;
import de.htwg.fivewins.controller.IFiveWinsController;
import de.htwg.fivewins.model.field.FieldFactory;
import de.htwg.fivewins.model.field.IFieldFactory;

public class TextUITest {

	TextUI textUI;
	
	@Before
	public void setUp() {
		IFieldFactory tmpFieldFactory = new FieldFactory();
		IFiveWinsController tmpController = new FiveWinsController(tmpFieldFactory);
		textUI = new TextUI(tmpController);
	}
	
	@Test
	public void testIterate() {
		final String testCommand = "1,1";
		assertFalse(textUI.iterate(testCommand));
	}
}
package de.htwg.fivewins;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import de.htwg.fivewins.controller.IFiveWinsController;
import de.htwg.fivewins.model.field.IField;

public class FiveWinsModule extends AbstractModule{

	// mhm is there a better solution?
	private static int SIZE = 5;
	
	@Override
	protected void configure() {
		bind(Integer.class).annotatedWith(Names.named("fieldSize")).toInstance(SIZE);
		bind(IField.class).to(de.htwg.fivewins.model.field.Field.class);
		bind(IFiveWinsController.class).to(de.htwg.fivewins.controller.FiveWinsController.class);
	}

}

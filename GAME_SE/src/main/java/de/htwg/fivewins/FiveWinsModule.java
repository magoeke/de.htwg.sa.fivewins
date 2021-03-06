package de.htwg.fivewins;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import de.htwg.fivewins.controller.game.IFiveWinsController;
import de.htwg.fivewins.controller.plugin.IPluginController;
import de.htwg.fivewins.model.field.IFieldFactory;
import de.htwg.fivewins.persistence.IFieldDAO;
import de.htwg.fivewins.plugin.IPlugin;
import de.htwg.fivewins.plugin.RandomPlugin;
import de.htwg.fivewins.plugin.TurnPlugin;

public class FiveWinsModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IFieldFactory.class).to(
				de.htwg.fivewins.model.field.FieldFactory.class);
		bind(IFiveWinsController.class).to(
				de.htwg.fivewins.controller.game.FiveWinsController.class);
		// persistence method
		bind(IFieldDAO.class).to(
				de.htwg.fivewins.persistence.couchdb.FieldCouchdbDAO.class);
		// Plugin
		Multibinder<IPlugin> plugins = Multibinder.newSetBinder(binder(),
				IPlugin.class);
		plugins.addBinding().to(RandomPlugin.class);
		plugins.addBinding().to(TurnPlugin.class);
		// Plugin Controller
		bind(IPluginController.class).to(
				de.htwg.fivewins.controller.plugin.PluginController.class);
	}

}

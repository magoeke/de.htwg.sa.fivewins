package de.htwg.fivewins;

import com.google.inject.AbstractModule;

import de.htwg.fivewins.controller.IFiveWinsController;
import de.htwg.fivewins.model.field.IFieldFactory;
import de.htwg.fivewins.persistence.IFieldDAO;

public class FiveWinsModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IFieldFactory.class).to(
				de.htwg.fivewins.model.field.FieldFactory.class);
		bind(IFiveWinsController.class).to(
				de.htwg.fivewins.controller.FiveWinsController.class);
//		bind(IFieldDAO.class).to(de.htwg.fivewins.persistence.db4o.FieldDb4oDAO.class);
//		bind(IFieldDAO.class).to(
//				de.htwg.fivewins.persistence.hibernate.FieldHibernateDAO.class);
		bind(IFieldDAO.class).to(
				de.htwg.fivewins.persistence.couchdb.FieldCouchdbDAO.class);
	}

}

package de.htwg.fivewins.persistence.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public final class HibernateUtil {
	private static final SessionFactory SESSION_FACTORY;

	static {
		final AnnotationConfiguration cfg = new AnnotationConfiguration();
		cfg.configure("/hibernate.cfg.xml");
		SESSION_FACTORY = cfg.buildSessionFactory();
	}

	private HibernateUtil() {
	}

	public static SessionFactory getInstance() {
		return SESSION_FACTORY;
	}
}

package de.htwBerlin.ai.kbe.config;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import de.htwBerlin.ai.kbe.security.AuthContainer;
import de.htwBerlin.ai.kbe.security.IAuthContainer;
import de.htwBerlin.ai.kbe.storage.DBSongListsDAO;
import de.htwBerlin.ai.kbe.storage.DBSongsDAO;
import de.htwBerlin.ai.kbe.storage.DBUserDao;
import de.htwBerlin.ai.kbe.storage.ISongListsDAO;
import de.htwBerlin.ai.kbe.storage.ISongsDAO;
import de.htwBerlin.ai.kbe.storage.IUserDao;



public class DependencyBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind (Persistence.createEntityManagerFactory("contactsDB-PU")).to(EntityManagerFactory.class);
        bind(DBSongsDAO.class).to(ISongsDAO.class).in(Singleton.class);
        bind(DBSongListsDAO.class).to(ISongListsDAO.class).in(Singleton.class);
        bind(AuthContainer.class).to(IAuthContainer.class).in(Singleton.class);
        bind(DBSongsDAO.class).to(ISongsDAO.class).in(Singleton.class);
        bind(DBUserDao.class).to(IUserDao.class).in(Singleton.class);
        
    }
}

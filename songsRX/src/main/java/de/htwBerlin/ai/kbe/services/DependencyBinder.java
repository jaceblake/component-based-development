package de.htwBerlin.ai.kbe.services;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import de.htwBerlin.ai.kbe.storage.DBSongListsDAO;
import de.htwBerlin.ai.kbe.storage.DBSongsDAO;
import de.htwBerlin.ai.kbe.storage.SongListsDAO;
import de.htwBerlin.ai.kbe.storage.SongsDAO;


public class DependencyBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind (Persistence.createEntityManagerFactory("contactsDB-PU")).to(EntityManagerFactory.class);
        bind(DBSongsDAO.class).to(SongsDAO.class).in(Singleton.class);
        bind(DBSongListsDAO.class).to(SongListsDAO.class).in(Singleton.class);
    }
}

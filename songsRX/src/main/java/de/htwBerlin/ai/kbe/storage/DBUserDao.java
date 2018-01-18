package de.htwBerlin.ai.kbe.storage;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;

@Singleton
public class DBUserDao implements IUserDao {
	
    private EntityManagerFactory emf;

    @Inject
    public DBUserDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

}

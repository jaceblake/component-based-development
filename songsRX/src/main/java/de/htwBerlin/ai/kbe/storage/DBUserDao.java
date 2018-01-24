package de.htwBerlin.ai.kbe.storage;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import de.htwBerlin.ai.kbe.bean.SongLists;
import de.htwBerlin.ai.kbe.bean.User;

@Singleton
public class DBUserDao implements IUserDao {
	
    private EntityManagerFactory emf;

    @Inject
    public DBUserDao(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    @Override
    public User findUserById(String userid) {
        EntityManager em = emf.createEntityManager();
        User entity = null;
        try {
            entity = em.find(User.class, userid);
        } finally {
            em.close();
        }
        return entity;
    }

}

package de.htwBerlin.ai.kbe.storage;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import de.htwBerlin.ai.kbe.bean.SongLists;
import de.htwBerlin.ai.kbe.bean.User;

@Singleton
public class DBSongListsDAO implements ISongListsDAO {

    private EntityManagerFactory emf;

    @Inject
    public DBSongListsDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    @Override
    public SongLists findSongListsById(Integer id) {
        EntityManager em = emf.createEntityManager();
        SongLists entity = null;
        try {
            entity = em.find(SongLists.class, id);
        } finally {
            em.close();
        }
        return entity;
    }

    @Override
    public Collection<SongLists> findAllSongLists(String id) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<SongLists> query = em.createQuery("SELECT c FROM SongLists c where owner = id ", SongLists.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Integer saveSongLists(SongLists SongLists) throws PersistenceException {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            // MUST set the SongLists in every address
  
            em.persist(SongLists);
            transaction.commit();
            return SongLists.getId();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding SongLists: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteSongLists(Integer id) throws PersistenceException {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        SongLists SongLists = null;
        try {
            SongLists = em.find(SongLists.class, id);
            if (SongLists != null) {
                System.out.println("Deleting: " + SongLists.getId() + " with firstName: " );
                transaction.begin();
                em.remove(SongLists);
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error removing SongLists: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not remove entity: " + e.toString());
        } finally {
            em.close();
        }
    }
}

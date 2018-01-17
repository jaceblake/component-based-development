package de.htwBerlin.ai.kbe.storage;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import de.htwBerlin.ai.kbe.bean.Song;
import de.htwBerlin.ai.kbe.bean.User;

@Singleton
public class DBSongsDAO implements SongsDAO {

    private EntityManagerFactory emf;

    @Inject
    public DBSongsDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    @Override
    public Song findSongById(Integer id) {
        EntityManager em = emf.createEntityManager();
        Song entity = null;
        try {
            entity = em.find(Song.class, id);
        } finally {
            em.close();
        }
        return entity;
    }

    @Override
    public Collection<Song> findAllSongs() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Song> query = em.createQuery("SELECT c FROM Song c", Song.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Integer saveSong(Song Song) throws PersistenceException {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            // MUST set the Song in every address
  
            em.persist(Song);
            transaction.commit();
            return Song.getId();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding Song: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteSong(Integer id) throws PersistenceException {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Song Song = null;
        try {
            Song = em.find(Song.class, id);
            if (Song != null) {
                System.out.println("Deleting: " + Song.getId() + " with firstName: " );
                transaction.begin();
                em.remove(Song);
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error removing Song: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not remove entity: " + e.toString());
        } finally {
            em.close();
        }
    }
}

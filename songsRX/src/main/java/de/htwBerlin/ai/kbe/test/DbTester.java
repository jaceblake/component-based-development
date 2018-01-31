package de.htwBerlin.ai.kbe.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.htwBerlin.ai.kbe.bean.Song;
import de.htwBerlin.ai.kbe.bean.SongLists;
import de.htwBerlin.ai.kbe.bean.User;
import de.htwBerlin.ai.kbe.storage.DBUserDao;
import de.htwBerlin.ai.kbe.storage.IUserDao;


public class DbTester {

    public static void main(String[] args) {
        
        // Datei persistence.xml wird automatisch eingelesen, beim Start der Applikation
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("contactsDB-PU");

        // EntityManager bietet Zugriff auf Datenbank
        EntityManager em = factory.createEntityManager();
        
        try {
            em.getTransaction().begin();
            // Create: neuen Contact anlegen
            
            User user1 = new User(2, "max", "müller", "mm");
            
            User user2 = new User(3, "mmuster3", "mustafa", "sabbir");
            
            Song song1 = new Song("halu1","me1","byan1",2001);
            
            Song song2 = new Song("halu4","me4","byan4",2004);
            
            List<Song> songs = new ArrayList<>();
            songs.add(song1);
            songs.add(song2);
            SongLists listsong = new SongLists(true, user1,songs);
            //listsong.setSongs(songs);
//            List<SongLists> songList = new ArrayList<>();
//            songList.add(listsong);
//            user1.setSonglists(songList);

            // Wir persistieren nur contact, 
            // wegen cascade=CascadeType.ALL werden auch address1, address 2 persistiert
            em.persist(listsong);
            
            // commit transaction
            em.getTransaction().commit();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // EntityManager nach Datenbankaktionen wieder freigeben
            em.close();
            // Freigabe am Ende der Applikation
            factory.close();
        }
    }


}
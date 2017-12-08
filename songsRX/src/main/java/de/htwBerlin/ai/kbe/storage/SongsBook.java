package de.htwBerlin.ai.kbe.storage;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import de.htwBerlin.ai.kbe.bean.Song;

import java.util.Map;



public class SongsBook {
	
	//This class implement Singleton design patterns

	private static Map<Integer,Song> storage;
	private static SongsBook instance = null;
	
	private SongsBook() {
		storage = new ConcurrentHashMap<Integer,Song>();
		initSomeContacts();
	}
	
	public synchronized static SongsBook getInstance() {
		if (instance == null) {
			instance = new SongsBook();
		}
		return instance;
	}
	
	private static void initSomeContacts() {
      // TO DO 
	 //Load songs.json file
	}
	
	public Song getContact(Integer id) {
		return storage.get(id);
	}
	
	public Collection<Song> getAllContacts() {
		return storage.values();
	}
	
	public Integer addContact(Song song) {
		// Fuer Beleg 3: Das koennen Sie im Songs' store NICHT machen!
		song.setId((int)storage.keySet().stream().count() + 1);
		storage.put(song.getId(), song);
		return song.getId();
	}
	
	// returns true (success), when contact exists in map and was updated
	// returns false, when contact does not exist in map
	public boolean updateContact(Song song) {
		throw new UnsupportedOperationException("updateContact: not yet implemented");
	}
	
	// returns deleted contact
	public Song deleteContact(Integer id) {
		throw new UnsupportedOperationException("deleteContact: not yet implemented");
	}
	
}

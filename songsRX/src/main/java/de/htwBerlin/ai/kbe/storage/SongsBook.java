package de.htwBerlin.ai.kbe.storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htwBerlin.ai.kbe.bean.Song;

import java.util.Map;



public class SongsBook {
	
	//This class implement Singleton design patterns

	private static Map<Integer,Song> storage;
	private static SongsBook instance = null;
	
	private final String SONGFILENAME = "songs.json";
	
	private AtomicInteger currentID = null;
	
	private List<Song> songList = new ArrayList<Song>();
	
	private SongsBook() {
		try {
			initializeSongStore(SONGFILENAME);
		} catch (IOException e) {
			System.out.println("Can't create Instance....");
		}
	}
	
	public synchronized static SongsBook getInstance() {
		if (instance == null) {
			instance = new SongsBook();
		}
		return instance;
	}
	
	private void initializeSongStore(String songFilename) throws IOException {

		if (songFilename == null || songFilename.equals("")) {
			songFilename = "songs.json";
		}
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(songFilename);

		songList = new ObjectMapper()
				.readValue(input, new TypeReference<ArrayList<Song>>() {});

		storage = new ConcurrentHashMap<Integer,Song>();

		songList.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
		
		songList.forEach(e -> storage.put(e.getId(), e));
		
		//ID starts after the biggest ID in the list
		currentID = new AtomicInteger( songList.get(songList.size()-1).getId());
	
			
		//storage.entrySet().stream().sorted();
		
		

	}
	
	public Song getSong(Integer id) {
		return storage.get(id);
	}
	
	public Collection<Song> getAllSongs() {
		System.out.println(storage);
		System.out.println(songList);
		
		return storage.values();
	}
	
	public Integer addSong(Song song) {
		song.setId(currentID.incrementAndGet());
		storage.put(song.getId(), song);
		return song.getId();
	}
	
	// returns true (success), when contact exists in map and was updated
	// returns false, when contact does not exist in map
	public boolean updateSong(Song song,Integer id) {
		
		if(storage.get(id) != null) {
			storage.replace(id, song);
			return true;
		}
		return false;
	}
	
	// returns deleted song
	public Song deleteSong(Integer id) {
		Song song = storage.get(id);
		if(song != null) {
			storage.remove(id);
			currentID.decrementAndGet();
			return song;
		}else {
			return song;
		}
	}
	
}

package de.htwBerlin.ai.kbe.storage;

import java.util.Collection;

import de.htwBerlin.ai.kbe.bean.Song;

public interface SongsDAO {

	/**
	 * Retrieves a Song
	 * 
	 * @param id
	 * @return
	 */
	public Song findSongById(Integer id);

	/**
	 * Retrieves all Songs
	 * 
	 * @return
	 */
	public Collection<Song> findAllSongs();

	/**
	 * Save a new Song
	 * 
	 * @param Song
	 * @return the Id of the new Songs
	 */
	public Integer saveSong(Song Song);

	/**
	 * Deletes the Song for the provided id
	 * 
	 * @param id
	 */
	public void deleteSong(Integer id);
}

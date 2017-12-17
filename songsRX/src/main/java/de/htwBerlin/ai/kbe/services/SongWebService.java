package de.htwBerlin.ai.kbe.services;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.htwBerlin.ai.kbe.bean.Song;
import de.htwBerlin.ai.kbe.storage.SongsBook;


//URL fuer diesen Service ist: http://localhost:8080/songsRx/rest/songs 
@Path("/songs")
public class SongWebService {
	
	//GET http://localhost:8080/songsRx/rest/songs
	//Returns all songs
	@GET 
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Collection<Song> getAllSongs() {
		System.out.println("getAllSongs: Returning all Songs!");
		return SongsBook.getInstance().getAllSongs();
	}

	//GET http://localhost:8080/songsRx/rest/songs/1
	//Returns: 200 and song with id 1
	//Returns: 404 on provided id not found
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getSong(@PathParam("id") Integer id) {
		Song song = SongsBook.getInstance().getSong(id);
		if (song != null) {
			System.out.println("getsong: Returning song for id " + id);
			return Response.ok(song).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("No Song found with id " + id).build();
		}
	}
	
	// POST http://localhost:8080/songsRx/rest/songs with song in payload
	// Returns: Status Code 201 and the new id of the song in the payload 
	// (temp. solution)
	//
	// Besser: Status Code 201 und URI fuer den neuen Eintrag im http-header 'Location' zurueckschicken, also:
	// Location: /songsRx/rest/songs/neueID
	// Aber dazu brauchen wir "dependency injection", also spaeter
	// @Context UriInfo uriInfo
	// return Response.created(uriInfo.getAbsolutePath()+<newId>).build(); 
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	public Response createSong(Song song) {
		if (song != null) {
		System.out.println("createsong: Received Song: " + song.toString());
		     return Response.status(Response.Status.CREATED).entity(SongsBook.getInstance().addSong(song)).build();
		}else {
		     return Response.status(Response.Status.NOT_FOUND).entity("Can't create a this Song bad Payload " ).build();
		}
	}
	
	//PUT http://localhost:8080/songsRx/rest/song/1 with updated song in Payload
    //Returns 204 on successful update
	//Returns 404 on song with provided id not found
	@PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
    public Response updateSong(@PathParam("id") Integer id, Song song) {
		
		if(song != null) {
			if(id == song.getId()) {
				boolean check = SongsBook.getInstance().updateSong(song,id);
				if(check) {
					return Response.status(Response.Status.CREATED).
							entity("Sucessfully updated Song ").build();
				}else {
					return Response.status(Response.Status.NOT_FOUND).
							entity("Can't update this song. Song doesn't exists ").build();
				}
			}else {
				Song tmp = SongsBook.getInstance().getSong(id);
				if(tmp != null) {
					SongsBook.getInstance().updateSong(song,id);
					return Response.status(Response.Status.CREATED).
							entity("Sucessfully updated Song ").build();
				}else {
					return Response.status(Response.Status.NOT_FOUND)
							.entity("Can't update this song. Song doesn't exists ").build();
				}
			}

		}else {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("Can't update this song bad payload ").build();
		}
        
    }
	
	//DELETE http://localhost:8080/songsRx/rest/songs/1
    //Returns 204 on successful delete
	//Returns 404 on provided id not found
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response delete(@PathParam("id") Integer id) {
		Song song = SongsBook.getInstance().deleteSong(id);
		if(song != null) {
			return Response.status(Response.Status.CREATED).
					entity("Sucessfully deleted Song").build();
		}else {
			return Response.status(Response.Status.NOT_FOUND).
					entity("Can't delete this Song. Song doesn't exists").build();
		}
	}

}

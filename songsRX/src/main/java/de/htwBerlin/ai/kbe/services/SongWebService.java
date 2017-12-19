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

	@GET 
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Collection<Song> getAllSongs() {
		System.out.println("getAllSongs: Returning all Songs!");
		return SongsBook.getInstance().getAllSongs();
	}


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
	

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	public Response createSong(Song song) {
		//only id and released field can be null or empty
		if (song != null && song.getTitle() != null && song.getArtist() != null && song.getAlbum() != null ) {
		System.out.println("createsong: Received Song: " + song.toString());
		     return Response.status(Response.Status.CREATED).entity(SongsBook.getInstance().addSong(song)).build();
		}else {
		     return Response.status(Response.Status.NOT_FOUND).entity("Can't create a this Song bad Payload " ).build();
		}
	}
	

	@PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
    public Response updateSong(@PathParam("id") Integer id, Song song) {
		
		//if client want to update a file ,album name ,title,Artist should be given
		if(song != null && song.getTitle() != null && song.getArtist() != null && song.getAlbum() != null ) {
			if(id == song.getId()) {
				boolean check = SongsBook.getInstance().updateSong(song,id);
				if(check) {
					return Response.status(Response.Status.ACCEPTED).
							entity("Sucessfully updated Song ").build();
				}else {
					return Response.status(Response.Status.NOT_FOUND).
							entity("Can't update this song. Song doesn't exists ").build();
				}
			}else {
				Song tmp = SongsBook.getInstance().getSong(id);
				if(tmp != null) {
					SongsBook.getInstance().updateSong(song,id);
					return Response.status(Response.Status.ACCEPTED).
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
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response delete(@PathParam("id") Integer id) {
		Song song = SongsBook.getInstance().deleteSong(id);
		if(song != null) {
			return Response.status(Response.Status.NO_CONTENT).
					entity("Sucessfully deleted Song").build();
		}else {
			return Response.status(Response.Status.NOT_FOUND).
					entity("Can't delete this Song. Song doesn't exists").build();
		}
	}

}

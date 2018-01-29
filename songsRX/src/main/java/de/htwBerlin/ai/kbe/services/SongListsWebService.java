package de.htwBerlin.ai.kbe.services;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import de.htwBerlin.ai.kbe.bean.SongLists;
import de.htwBerlin.ai.kbe.security.IAuthContainer;
import de.htwBerlin.ai.kbe.storage.ISongListsDAO;
import de.htwBerlin.ai.kbe.storage.IUserDao;

//URL fuer diesen Service ist: http://localhost:8080/songsRX/rest/userId 
@Path("/userId")
public class SongListsWebService {

	private ISongListsDAO SongListsDao;
	@Inject
	private IUserDao UserDao;
	@Inject
	private IAuthContainer authContainer;

	@Inject
	public SongListsWebService(ISongListsDAO dao) {
		this.SongListsDao = dao;

	}

	@GET
	@Path("/{id}/songLists")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Collection<SongLists> getAllSongLists(@HeaderParam("Authorization") String token,
			@PathParam("id") String id) {

		System.out.println("getAllSongLists: Returning all SongLists!");

		// return all if same user
		System.out.println(SongListsDao.findAllSongLists(id, false));
		if (authContainer.getUserIdByToken(token).equals(id)) {

			return SongListsDao.findAllSongLists(id, false);

		}
		return SongListsDao.findAllSongLists(id, true);
		// @SuppressWarnings("rawtypes")

		// GenericEntity entity = new
		// GenericEntity<Collection<SongLists>>(SongListsBook.getInstance().getAllSongLists())
		// {};
		// GenericEntity entity = new
		// GenericEntity<Collection<SongLists>>(SongListsBook.getInstance().getAllSongLists())
		// {};

		// return Response.ok(entity).build();

	}

	@GET
	@Path("/{id}/songLists/{songList_id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getSongListById(@HeaderParam("Authorization") String token, @PathParam("id") String id,
			@PathParam("songList_id") Integer songListId) {

		System.out.println("getAllSongLists: Returning all SongLists!");

		// return all if same user
		// System.ou t.println(SongListsDao.findAllSongLists(id,false));

		SongLists s;
		if (authContainer.getUserIdByToken(token).equals(id)) {

			s = SongListsDao.findSongListById(id, songListId, false);

		} else {
			s = SongListsDao.findSongListById(id, songListId, true);
		}
		if (s == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("No Song found with id " + songListId).build();
		} else {
			return Response.ok(s).build();
		}

		// @SuppressWarnings("rawtypes")

		// GenericEntity entity = new
		// GenericEntity<Collection<SongLists>>(SongListsBook.getInstance().getAllSongLists())
		// {};
		// GenericEntity entity = new
		// GenericEntity<Collection<SongLists>>(SongListsBook.getInstance().getAllSongLists())
		// {};

		// return Response.ok(entity).build();

	}


	 @POST
	 @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	 @Produces(MediaType.TEXT_PLAIN)
	 public Response createSongLists(SongLists SongLists) {
	 System.out.println(SongLists);

	 if (SongLists != null  && SongLists.getSongs() != null) {
	  SongListsDao.saveSongLists(SongLists);
	 return Response.status(Response.Status.CREATED).entity("New SongLists Created").build();
	 } else {
	 return Response.status(Response.Status.NOT_FOUND).entity("Can't create  this SongLists bad Payload" ).build();
	 }
	
	 }
	

	// @DELETE
	// @Path("/{id}")
	// @Produces(MediaType.TEXT_PLAIN)
	// public Response delete(@PathParam("id") Integer id) {
	// SongLists SongLists = SongListsBook.getInstance().deleteSongLists(id);
	// if (SongLists != null) {
	// return Response.status(Response.Status.NO_CONTENT).entity("Sucessfully
	// deleted SongLists").build();
	// } else {
	// return Response.status(Response.Status.NOT_FOUND).entity("Can't delete this
	// SongLists. SongLists doesn't exists")
	// .build();
	// }
	// }

}

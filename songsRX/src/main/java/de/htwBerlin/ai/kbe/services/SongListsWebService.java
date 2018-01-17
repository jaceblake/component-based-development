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

import de.htwBerlin.ai.kbe.bean.SongLists;

import de.htwBerlin.ai.kbe.storage.SongListsDAO;

//URL fuer diesen Service ist: http://localhost:8080/songsRX/rest/userId 
@Path("/userId")
public class SongListsWebService {

	private SongListsDAO SongListsDao;

	@Inject
	public SongListsWebService(SongListsDAO dao) {
		this.SongListsDao = dao;

	}

	private AuthWebService auth = new AuthWebService();

	@GET
	@Path("/{id}/songLists")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Collection<SongLists> getAllSongLists(@PathParam("id") String id) {

		System.out.println("getAllSongLists: Returning all SongLists!");
		// @SuppressWarnings("rawtypes")

		// GenericEntity entity = new
		// GenericEntity<Collection<SongLists>>(SongListsBook.getInstance().getAllSongLists()) {};
		// GenericEntity entity = new
		// GenericEntity<Collection<SongLists>>(SongListsBook.getInstance().getAllSongLists()) {};

		// return Response.ok(entity).build();

		return SongListsDao.findAllSongLists(id);
	}

//	@GET
//	@Path("/{id}")
//	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
//	public Response getSongLists(@PathParam("id") Integer id) {
//
//		SongLists SongLists = SongListsBook.getInstance().getSongLists(id);
//		if (SongLists != null) {
//			System.out.println("getSongLists: Returning SongLists for id " + id);
//			return Response.ok(SongLists).build();
//		} else {
//			return Response.status(Response.Status.NOT_FOUND).entity("No SongLists found with id " + id).build();
//		}
//	}
//
//	@POST
//	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
//	@Produces(MediaType.TEXT_PLAIN)
//	public Response createSongLists(SongLists SongLists) {
//		System.out.println(SongLists);
//		// only id and released field can be null or empty
//		// if (SongLists != null && SongLists.getTitle() != null && SongLists.getArtist() != null &&
//		// SongLists.getAlbum() != null ) {
//		// System.out.println("createSongLists: Received SongLists: " + SongLists.toString());
//		// return
//		// Response.status(Response.Status.CREATED).entity(SongListsBook.getInstance().addSongLists(SongLists)).build();
//		// }else {
//		// return Response.status(Response.Status.NOT_FOUND).entity("Can't create a this
//		// SongLists bad Payload " ).build();
//		// }
//
//		if (SongLists != null && SongLists.getTitle() != null && SongLists.getArtist() != null) {
//			int newId = SongListsDao.saveSongLists(SongLists);
//			return Response.status(Response.Status.CREATED).entity("New SongLists Created ").build();
//		} else {
//			return Response.status(Response.Status.NOT_FOUND).entity("Can't create a this SongLists bad Payload ").build();
//		}
//		
//	}
//
//	@PUT
//	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
//	@Path("/{id}")
//	@Produces(MediaType.TEXT_PLAIN)
//	public Response updateSongLists(@PathParam("id") Integer id, SongLists SongLists) {
//
//		// if client want to update a file ,album name ,title,Artist should be given
//		if (SongLists != null && SongLists.getTitle() != null && SongLists.getArtist() != null && SongLists.getAlbum() != null) {
//			if (id == SongLists.getId()) {
//				boolean check = SongListsBook.getInstance().updateSongLists(SongLists, id);
//				if (check) {
//					return Response.status(Response.Status.ACCEPTED).entity("Sucessfully updated SongLists ").build();
//				} else {
//					return Response.status(Response.Status.NOT_FOUND)
//							.entity("Can't update this SongLists. SongLists doesn't exists ").build();
//				}
//			} else {
//				SongLists tmp = SongListsBook.getInstance().getSongLists(id);
//				if (tmp != null) {
//					SongListsBook.getInstance().updateSongLists(SongLists, id);
//					return Response.status(Response.Status.ACCEPTED).entity("Sucessfully updated SongLists ").build();
//				} else {
//					return Response.status(Response.Status.NOT_FOUND)
//							.entity("Can't update this SongLists. SongLists doesn't exists ").build();
//				}
//			}
//
//		} else {
//			return Response.status(Response.Status.NOT_FOUND).entity("Can't update this SongLists bad payload ").build();
//		}
//
//	}
//
//	@DELETE
//	@Path("/{id}")
//	@Produces(MediaType.TEXT_PLAIN)
//	public Response delete(@PathParam("id") Integer id) {
//		SongLists SongLists = SongListsBook.getInstance().deleteSongLists(id);
//		if (SongLists != null) {
//			return Response.status(Response.Status.NO_CONTENT).entity("Sucessfully deleted SongLists").build();
//		} else {
//			return Response.status(Response.Status.NOT_FOUND).entity("Can't delete this SongLists. SongLists doesn't exists")
//					.build();
//		}
//	}

}

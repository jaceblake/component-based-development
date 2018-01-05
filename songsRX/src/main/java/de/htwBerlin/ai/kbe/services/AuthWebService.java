package de.htwBerlin.ai.kbe.services;



import java.util.Collection;


import javax.ws.rs.core.Response;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import de.htwBerlin.ai.kbe.bean.User;
import de.htwBerlin.ai.kbe.storage.UserBook;

@Path("/auth")
public class AuthWebService {
	

	
	
    @Context
    private HttpServletRequest request;
	
	@GET
	@Path("/user")
	@Produces({MediaType.APPLICATION_JSON})
	public Collection<User> getUsers() {
      
		return UserBook.getInstance().getAllUsers();

	}
	
	@GET
	@Path("/")
	@Produces({ MediaType.TEXT_PLAIN})
	public Response getSong(@QueryParam("userId") String userId) {
		
		User user = UserBook.getInstance().getUser(userId);
		
		if(user != null ) {
		String session = request.getSession().getId();
		UserBook.getInstance().updateStorage(userId, session);
		return Response
				   .status(200)
				   .entity(session).build();
		}else {
			return Response.status(Response.Status.FORBIDDEN).entity("No User found with id " + userId).build();
		}
	}

		  	
}

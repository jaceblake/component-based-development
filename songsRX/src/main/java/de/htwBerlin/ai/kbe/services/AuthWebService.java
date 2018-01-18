package de.htwBerlin.ai.kbe.services;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import de.htwBerlin.ai.kbe.bean.User;
import de.htwBerlin.ai.kbe.book.UserBook;
import de.htwBerlin.ai.kbe.security.AuthContainer;
import de.htwBerlin.ai.kbe.security.IAuthContainer;

import java.util.concurrent.ConcurrentHashMap;

@Path("/auth")
public class AuthWebService {
	
	private IAuthContainer authContainer;
	
	@Inject
	public AuthWebService(IAuthContainer authContainer) {
		this.authContainer = authContainer;
	}
	
	@Context 
	HttpServletRequest request;

	/*
	 * Only for test, 
	 */
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
        String token = request.getSession().getId();
        authContainer.setUserIdByToken(token, userId);
		return Response
				   .status(200)
				   .entity("Your Token is "+ token).build();
		}else {
			return Response.status(Response.Status.FORBIDDEN).entity("No User found with id " + userId).build();
		}
	}

	public String getUserIdByToken(String token) {
		return authContainer.getUserIdByToken(token);
	}

	public boolean authenticate(String authToken) {
		String userId = authContainer.getUserIdByToken(authToken);
		if(userId != null) {
			return true;
		}
		return false;
	}

		  	
}

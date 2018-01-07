package de.htwBerlin.ai.kbe.services;

import java.util.Collection;
import java.util.Map;

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
import java.util.concurrent.ConcurrentHashMap;

@Path("/auth")
public class AuthWebService {
	
    
	private static Map<String,String> tokenMap = new ConcurrentHashMap<String,String>();
	
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
        tokenMap.put(token, userId);
		return Response
				   .status(200)
				   .entity("Your Token is "+ token).build();
		}else {
			return Response.status(Response.Status.FORBIDDEN).entity("No User found with id " + userId).build();
		}
	}

	public String getUserIdByToken(String token) {
		return tokenMap.get(token);
	}


		  	
}

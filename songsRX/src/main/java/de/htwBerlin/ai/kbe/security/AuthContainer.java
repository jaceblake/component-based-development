package de.htwBerlin.ai.kbe.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;

@Singleton
public class AuthContainer  implements IAuthContainer{
	
	/**
	 * key = token, value = userId
	 */
	private static Map<String,String> tokenMap = new ConcurrentHashMap<String,String>();
	

	@Override
	public boolean authenticate(String authToken) {
		if(tokenMap.containsKey(authToken)) {
			return true;
		}
		return false;
	}
	
	@Override
	public String getUserIdByToken(String token) {
		return tokenMap.get(token);
	}
	
	@Override
	public String setUserIdByToken(String token,String userId) {
		return tokenMap.put(token,userId);
	}

}

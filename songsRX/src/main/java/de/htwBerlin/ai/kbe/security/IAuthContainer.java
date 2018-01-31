package de.htwBerlin.ai.kbe.security;

public interface IAuthContainer {
	
	
	/**
	 * 
	 * @param authToken
	 * @return true when authToken found in tokenMap
	 */
	public boolean authenticate(String authToken);
	
	/**
	 * 
	 * @param token
	 * @return userId given token
	 */
	public String getUserIdByToken(String token);
	
	/**
	 * put token in tokenMap
	 * @param token
	 * @param userId
	 * @return value,if successfully put in map
	 */
	public String setUserIdByToken(String token,String userId);

}

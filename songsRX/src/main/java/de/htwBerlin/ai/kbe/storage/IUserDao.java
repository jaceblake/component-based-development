package de.htwBerlin.ai.kbe.storage;

import de.htwBerlin.ai.kbe.bean.User;

/**
 * R -> only read user
 * @author 
 *
 */
public interface IUserDao {
	
	/**
	 * find a user by id
	 * @param userid
	 * @return User 
	 */
	public User findUserById(String userid);

}

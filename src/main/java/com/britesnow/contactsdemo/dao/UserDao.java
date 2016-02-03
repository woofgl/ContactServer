package com.britesnow.contactsdemo.dao;

import com.britesnow.contactsdemo.entity.User;
import com.google.inject.Singleton;
import org.j8ql.query.Query;

@Singleton
public class UserDao extends BaseDao<User,Long> {

	// --------- Get --------- //
	// TODO: needs to return Option<User>
	public User getByUsername(String username){
		return daoHelper.first(Query.select(entityClass).where("username;ilike", username)).orElse(null);
	}

	public User getByEqualName(String username){
		return daoHelper.first(Query.select(entityClass).where("username; =", username)).orElse(null);
	}

	public User getByPasswordToken(String passwordToken){
		return daoHelper.first(Query.select(entityClass).where("resetPasswordToken; =", passwordToken)).orElse(null);
	}

	public User getById(Long id){
		return daoHelper.first(Query.select(entityClass).where("id",id)).orElse(null);
	}
	// --------- /Get --------- //

	// --------- Create --------- //
	/**
	 * Higher level methods to create a user.
	 * @param username
	 * @param password
	 * @return
	 */
	public User createUser(String username, String password){
		 User user = new User();
		 user.setPassword(username);
		 user.setPassword(password);
		 // for User, we can create new ones without an existing User
		 Long id = super.create(null, user);
		 return super.get(null,id).get();
	}
	// --------- /Create --------- //


}

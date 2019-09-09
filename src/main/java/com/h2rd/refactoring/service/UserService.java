package com.h2rd.refactoring.service;

import java.util.ArrayList;
import java.util.List;

import com.h2rd.refactoring.exception.CustomException;
import com.h2rd.refactoring.exception.CustomExceptionMapper;
import com.h2rd.refactoring.model.User;
import com.sun.jersey.api.NotFoundException;

public class UserService {

	private static final String CLASSNAME = "UserService";
	public List<User> users;

	public static UserService userDao;

	public static UserService getUserDao() {
		if (userDao == null) {
			userDao = new UserService();
		}
		return userDao;
	}

	/**
	 * Adds a new user into list Checks for email id must not be blank Checks for
	 * user must have atleast one role
	 * 
	 * @param user an object of User class
	 * @author MEERA
	 */
	public void saveUser(User user) {

		String methodName = "saveUser";
		if (user == null) {
			throw new CustomException("User is Null");
		} else if (user.getName() == null || user.getEmail().isEmpty()) {
			throw new CustomException("EmailId is Null or Empty");
		} else if (user.getRoles() == null || user.getRoles().isEmpty()) {
			throw new CustomException("User must have atleast one role");
		}

		if (users == null) {
			users = new ArrayList<User>();
		}
		boolean duplicate = isDuplicateEmail(user.getEmail());

		if (duplicate == true) {
			System.err.println(CLASSNAME + " " + methodName + " " + "User with given email already exists");
			throw new CustomException("User with given email already exists" + user.getEmail());
		}

		users.add(user);
	}

	/**
	 * Returns all users in list
	 * 
	 * @return Response all user entities or error code with message
	 * @author MEERA
	 */
	public List<User> getUsers() {
		String methodName = "getUsers";
		try {
			return users;
		} catch (Exception e) {
			System.err.println(CLASSNAME + " " + methodName + " " + " Exception in getting users" + e);
			throw new NotFoundException();
		}
	}

	/**
	 * Deletes a user based on email Checks email id must not be blank Checks user
	 * with given email exist in system
	 * 
	 * @param email of user to be deleted
	 * @return Response with success or error code with message
	 * @author MEERA
	 */
	public void deleteUser(String email) throws Exception {
		String methodName = "deleteUser";
		boolean exists = false;
		try {
			int index = 0;
			for (User user : users) {
				if (user.getEmail().equals(email)) {
					exists = true;
					break;
				}
				index++;
			}
			if (exists) {
				users.remove(index);
			} else {
				throw new CustomException("User with given email does not exist");
			}

		} catch (Exception e) {
			System.err.println(CLASSNAME + " " + methodName + " " + " Exception in delete user" + e);
			throw e;
		}
	}

	/**
	 * Updates a user based on email checks if user with given email exist in system
	 * checks user must have atleast one role
	 * 
	 * @param user  User object
	 * @param email String emailId of user to be updated
	 * @author MEERA
	 */
	public void updateUser(User userToUpdate) throws Exception {
		String methodName = "updateUser";
		boolean exists = false;
		if (userToUpdate.getRoles() == null || userToUpdate.getRoles().isEmpty()) {
			throw new NotFoundException("User must have atleast one role");
		}
		try {
			for (User user : users) {
				if (user.getEmail().equals(userToUpdate.getEmail())) {
					user.setName(userToUpdate.getEmail());
					user.setRoles(userToUpdate.getRoles());
					exists = true;
				}
			}
			if (!exists) {
				throw new CustomException("User with given email does not exist");
			}
		} catch (Exception e) {
			System.err.println(CLASSNAME + " " + methodName + " " + " Exception in update user" + e);
			throw e;
		}
	}

	/**
	 * Returns all users with given name in list
	 * 
	 * @param name
	 * @return list of all users of given name or error code with message
	 * @author MEERA
	 */
	public List<User> findUser(String name) throws Exception {
		List<User> result = new ArrayList<User>();
		String methodName = "findUser";
		try {
			for (User user : users) {
				if (user.getName() == name) {
					result.add(user);
				}
			}
		} catch (Exception e) {
			System.err.println(CLASSNAME + " " + methodName + " " + " Exception in delete user" + e);
			throw e;
		}
		if (result.isEmpty()) {
			return null;
		} else {
			return result;
		}
	}

	/**
	 * Checks if user with given email exist in system
	 * 
	 * @param email
	 * @return boolean
	 * @author MEERA
	 */
	public boolean isDuplicateEmail(String email) {
		for (User us : users) {
			if (us.getEmail().equals(email)) {
				return true;
			}
		}
		return false;

	}
}

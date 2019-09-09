package com.h2rd.refactoring.resource;

import com.h2rd.refactoring.exception.CustomException;
import com.h2rd.refactoring.exception.CustomExceptionMapper;
import com.h2rd.refactoring.model.ErrorMessage;
import com.h2rd.refactoring.model.User;
import com.h2rd.refactoring.service.UserService;
import com.sun.jersey.api.NotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Path("/users")
@Repository
public class UserResource {

	public UserService userService;

	/**
	 * Adds a new user into list
	 * 
	 * @param user an object of User class
	 * @return Response with user entity or error code with message
	 * @author MEERA
	 */
	@POST
	public Response addUser(User user) {

		if (userService == null) {
			userService = UserService.getUserDao();
		}
		try {
			userService.saveUser(user);
		} catch (CustomException e) {
			ErrorMessage errMsg = new ErrorMessage(e.getMessage(), 404, "");
			return Response.status(Status.NOT_FOUND).entity(errMsg).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.status(Status.CREATED).entity(user).build();
	}

	/**
	 * Updates a user based on email
	 * 
	 * @param user  User object
	 * @param email String emailId of user to be updated
	 * @return Response with user entity or error code with message
	 * @author MEERA
	 */
	@PUT
	@Path("/{email}")
	public Response updateUser(@PathParam("email") String email, User user) {

		user.setEmail(email);

		if (userService == null) {
			userService = UserService.getUserDao();
		}
		try {
			userService.updateUser(user);
			return Response.status(Status.OK).entity(user).build();
		} catch (CustomException e) {
			ErrorMessage errMsg = new ErrorMessage(e.getMessage(), 404, "");
			return Response.status(Status.NOT_FOUND).entity(errMsg).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Deletes a user based on email
	 * 
	 * @param email of user to be deleted
	 * @return Response with success or error code with message
	 * @author MEERA
	 */
	@DELETE
	@Path("/{email}")
	public Response deleteUser(@PathParam("email") String email) {

		if (userService == null) {
			userService = UserService.getUserDao();
		}

		try {
			userService.deleteUser(email);
			return Response.ok().entity("User deleted").build();
		} catch (CustomException e) {
			ErrorMessage errMsg = new ErrorMessage(e.getMessage(), 404, "");
			return Response.status(Status.NOT_FOUND).entity(errMsg).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Returns all users in list
	 * 
	 * @return Response all user entities or error code with message
	 * @author MEERA
	 */
	@GET
	public Response getUsers() {

		if (userService == null) {
			userService = UserService.getUserDao();
		}
		try {
			List<User> users = userService.getUsers();
			if (users == null) {
				users = new ArrayList<User>();
			}

			GenericEntity<List<User>> usersEntity = new GenericEntity<List<User>>(users) {
			};
			return Response.status(200).entity(usersEntity).build();
		} catch (NotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Returns all users with given name in list
	 * 
	 * @param name
	 * @return Response with all users of given name or error code with message
	 * @author MEERA
	 */
	@GET
	@Path("/{name}")
	public Response findUser(@PathParam("name") String name) {

		if (userService == null) {
			userService = UserService.getUserDao();
		}

		try {
			List<User> user = userService.findUser(name);
			if (user == null) {
				return Response.status(Status.NOT_FOUND).build();
			}
			GenericEntity<List<User>> usersEntity = new GenericEntity<List<User>>(user) {
			};
			return Response.status(200).entity(usersEntity).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}

package com.h2rd.refactoring.resource;

import com.h2rd.refactoring.model.User;
import com.h2rd.refactoring.service.UserService;
import com.sun.jersey.api.NotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Path("/users")
@Repository
public class UserResource {

	public UserService userService;

	@POST
	public Response addUser(User user) throws DuplicateEmailException {

		if (userService == null) {
			userService = UserService.getUserDao();
		}
		try {
			userService.saveUser(user);
		} catch (NotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		} catch (DuplicateEmailException e) {
			throw e;
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.status(Status.CREATED).entity(user).build();
	}

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
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DELETE
	@Path("/{email}")
	public Response deleteUser(@PathParam("email") String email) {

		if (userService == null) {
			userService = UserService.getUserDao();
		}
		try {
			userService.deleteUser(email);
			return Response.ok().entity(email).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	public Response getUsers() {

		/*
		 * ApplicationContext context = new ClassPathXmlApplicationContext(new String[]
		 * { "classpath:/application-config.xml" });
		 */
		// userService = context.getBean(UserService.class);
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

	@GET
	@Path("/{name}")
	public Response findUser(@PathParam("name") String name) {

		if (userService == null) {
			userService = UserService.getUserDao();
		}

		try {
			User user = userService.findUser(name);
			return Response.ok().entity(user).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}

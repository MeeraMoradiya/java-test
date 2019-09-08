package com.h2rd.refactoring.resource;

import com.h2rd.refactoring.model.User;
import com.h2rd.refactoring.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Path("/users")
@Repository
public class UserResource{

    public UserService userService;

    @POST
    public Response addUser(User user) {

        if (userService == null) {
            userService = UserService.getUserDao();
        }

        userService.saveUser(user);
        return Response.ok().entity(user).build();
    }

    @PUT
    @Path("/{email}")
    public Response updateUser(@PathParam("email") String email, User user) {

       user.setEmail(email);

        if (userService == null) {
            userService = UserService.getUserDao();
        }

        userService.updateUser(user);
        return Response.ok().entity(user).build();
    }

    @DELETE
    @Path("/{email}")
    public Response deleteUser(@PathParam("email") String email) {
       
        if (userService == null) {
            userService = UserService.getUserDao();
        }

        userService.deleteUser(email);
        return Response.ok().entity(email).build();
    }

    @GET
    public Response getUsers() {
    	
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
    		"classpath:/application-config.xml"	
    	});
    	userService = context.getBean(UserService.class);
    	List<User> users = userService.getUsers();
    	if (users == null) {
    		users = new ArrayList<User>();
    	}

        GenericEntity<List<User>> usersEntity = new GenericEntity<List<User>>(users) {};
        return Response.status(200).entity(usersEntity).build();
    }

    @GET
    @Path("/{name}")
    public Response findUser(@PathParam("name") String name) {

        if (userService == null) {
            userService = UserService.getUserDao();
        }

        User user = userService.findUser(name);
        return Response.ok().entity(user).build();
    }
}
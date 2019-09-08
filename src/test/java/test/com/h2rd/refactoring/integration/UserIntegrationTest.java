package test.com.h2rd.refactoring.integration;

import java.util.ArrayList;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import junit.framework.Assert;

import org.junit.Test;

import com.h2rd.refactoring.model.User;
import com.h2rd.refactoring.resource.UserResource;

public class UserIntegrationTest {
	
	@Test
	public void createUserTest() {
		UserResource userResource = new UserResource();
		
		User integration = new User();
        integration.setName("integration");
        integration.setEmail("initial@integration.com");
        integration.setRoles(new ArrayList<String>());
        try {
        Response response = userResource.addUser(integration);
        Assert.assertEquals(201, response.getStatus());
        }catch(Exception e) {
        	
        }
       
	}

	@Test
	public void updateUserTest() {
		UserResource userResource = new UserResource();
		
		createUserTest();
        
        User updated = new User();
        updated.setName("integration");
        updated.setEmail("updated@integration.com");
        updated.setRoles(new ArrayList<String>());
        
        Response response = userResource.updateUser(updated.getEmail(),updated);
        Assert.assertEquals(200, response.getStatus());
	}
}

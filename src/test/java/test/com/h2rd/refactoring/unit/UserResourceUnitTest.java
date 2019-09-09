package test.com.h2rd.refactoring.unit;

import com.h2rd.refactoring.model.User;
import com.h2rd.refactoring.resource.UserResource;
import com.h2rd.refactoring.service.UserService;

import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.core.Response;

public class UserResourceUnitTest {
	UserResource userResource = new UserResource();
    UserService userDao;

    /**
     * Test: Get all users
     */
    @Test
    public void getUsersTest() {

        
        userDao = UserService.getUserDao();

        User user = new User();
        user.setName("fake user");
        user.setEmail("fake@user.com");
        user.setRoles(Arrays.asList("admin"));
        try {
        	userDao.saveUser(user);
        	Response response = userResource.getUsers();
        	Assert.assertEquals(200, response.getStatus());
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
    }
    
    /**
     * Test: Add new User
     */
    @Test
    public void addNewUser() {

        User user = new User();

        user.setName("Meera");
        user.setEmail("meera1@sap.com");
        user.setRoles(Arrays.asList("admin", "user"));

        Response response=userResource.addUser(user);
        Assert.assertEquals(201, response.getStatus());
       
    }
    /**
     * Test: Add new User without role
     */
    @Test
    public void addNewUserWithoutRole() {
    	User user = new User();

        user.setName("Meera1");
        user.setEmail("meera@sap.com");
        user.setRoles(new ArrayList<String>());

        Response response=userResource.addUser(user);
        Assert.assertEquals(404, response.getStatus());
    }
    
    /**
     * Test: Add new User with existing email
     */
    @Test
    public void addNewUserWithDuplicateEmail() {
    	User user = new User();

        user.setName("MeeraNew");
        user.setEmail("meera@sap.com");
        user.setRoles(Arrays.asList("admin", "user"));

        Response response=userResource.addUser(user);
        Response responseNew=userResource.addUser(user);
        
        Assert.assertEquals(404, responseNew.getStatus());
    }
    
    /**
     * Test: update User 
     */
    @Test
    public void updateUser() {
    	User user = new User();

        user.setName("updated");
        user.setEmail("meera1@sap.com");
        user.setRoles(Arrays.asList("owner"));

        Response response=userResource.updateUser(user.getEmail(), user);
 
        Assert.assertEquals(200, response.getStatus());
    }
    
    /**
     * Test: update User with non existing email
     */
    @Test
    public void updateUserWithWrongEmail() {
    	User user = new User();

        user.setName("updated");
        user.setEmail("aaa@sap.com");
        user.setRoles(Arrays.asList("owner"));

        Response response=userResource.updateUser(user.getEmail(), user);
 
        Assert.assertEquals(304, response.getStatus());
    }
    
    /**
     * Test: update User without role
     */
    @Test
    public void updateUserWithoutRole() {
    	User user = new User();

        user.setName("updated");
        user.setEmail("meera1@sap.com");
        user.setRoles(new ArrayList<String>());

        Response response=userResource.updateUser(user.getEmail(), user);
 
        Assert.assertEquals(304, response.getStatus());
    }
    
   
    
    
    /**
     * Test: findUser by Name
     */
    @Test
    public void findUser() {
    	
    	String name="updated";

        Response response=userResource.findUser(name);
 
        Assert.assertEquals(200, response.getStatus());
    }
   
    
    /**
     * Test: findUser by invalid Name
     */
    @Test
    public void findUserInvalidName() {
    	
    	String name="invalid";

        Response response=userResource.findUser(name);
 
        Assert.assertEquals(404, response.getStatus());
    }
   
}

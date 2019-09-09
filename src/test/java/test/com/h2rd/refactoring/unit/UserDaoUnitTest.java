package test.com.h2rd.refactoring.unit;

import com.h2rd.refactoring.model.User;
import com.h2rd.refactoring.service.UserService;

import org.junit.Test;

import java.util.Arrays;

public class UserDaoUnitTest {

    UserService userDao;

   /* @Test
    public void saveUserTest() {
        userDao = UserService.getUserDao();

        User user = new User();
        user.setName("Fake Name");
        user.setEmail("fake@email.com");
        user.setRoles(Arrays.asList("admin", "master"));
        try {
        	userDao.saveUser(user);
        }catch(Exception e) {
        	
        }
    }

    @Test
    public void deleteUserTest() {
        userDao = UserService.getUserDao();

        try {
            userDao.deleteUser("fake@email.com");
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }*/
}
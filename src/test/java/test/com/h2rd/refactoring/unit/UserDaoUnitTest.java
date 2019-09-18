package test.com.h2rd.refactoring.unit;

import com.h2rd.refactoring.model.User;
import com.h2rd.refactoring.service.UserService;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDaoUnitTest {

	UserService userDao = new UserService();

	/**
	 * Test: Get all users
	 */
	@Test
	public void getUsers() {

		String email = "abc@def.com";
		String name = "abc";
		String role1 = "admin";

		User user = new User();

		user.setName(name);
		user.setEmail(email);
		user.setRoles(Arrays.asList(role1));

		userDao.saveUser(user);

		List<User> users = new ArrayList<User>(userDao.getUsers());

		assert users.size() == 1;
		assert users.get(0).equals(user);
	}

	/**
	 * Test: Save User
	 */
	@Test
	public void saveUser() {

		String email = "abc@def.com";
		String name = "abc";
		String role1 = "admin";

		User user = new User();

		user.setName(name);
		user.setEmail(email);
		user.setRoles(Arrays.asList(role1));

		userDao.saveUser(user);
		assert 1 == userDao.getUsers().size();
		try {
			List<User> foundUser = userDao.findUser(user.getName());

			assert user.getName().equals(foundUser.get(0).getName());
			assert user.getEmail().equals(foundUser.get(0).getEmail());
			assert user.getRoles().equals(foundUser.get(0).getRoles());

			userDao.saveUser(user);
			assert 1 == userDao.getUsers().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test: Delete Users
	 */
	@Test
	public void deleteUser() {
		String email = "abc@xyz.com";
		String name = "abc";
		String role1 = "admin";

		User user = new User();

		user.setName(name);
		user.setEmail(email);
		user.setRoles(Arrays.asList(role1));

		userDao.saveUser(user);
		try {
			userDao.deleteUser(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(0, userDao.getUsers().size());

	}

	/**
	 * Test: Find user by name
	 */
	@Test
	public void findUserByName() {

		String email = "abc@def.com";
		String name = "abc";
		String role1 = "admin";
		User user = new User();

		user.setName(name);
		user.setEmail(email);
		user.setRoles(Arrays.asList(role1));

		userDao.saveUser(user);
		try {
			List<User> foundUser = userDao.findUser(name);
			assertEquals(user.getEmail(), foundUser.get(0).getEmail());
			assertEquals(user.getName(), foundUser.get(0).getName());
			assertEquals(user.getRoles(), foundUser.get(0).getRoles());
			assertNull("expecting null", userDao.findUser("nonexisting"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test: Update Users
	 */
	@Test
	public void updateUser() {

		String email = "abc@def.com";
		String name = "abc";
		String role1 = "admin";

		User user = new User();

		user.setName(name);
		user.setEmail(email);
		user.setRoles(Arrays.asList(role1));

		userDao.saveUser(user);

		String newName = "xyz";
		String newRole1 = "owner";

		User newUser = new User();
		newUser.setName(newName);
		newUser.setEmail(email);
		newUser.setRoles(Arrays.asList(newRole1));
		try {
			userDao.updateUser(newUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			List<User> users = new ArrayList(userDao.findUser(newName));

			assert users.size() == 1;

			User foundUser = users.get(0);

			assertEquals(newUser.getEmail(), foundUser.getEmail());
			assertEquals(newUser.getName(), foundUser.getName());
			assertEquals(newUser.getRoles(), foundUser.getRoles());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

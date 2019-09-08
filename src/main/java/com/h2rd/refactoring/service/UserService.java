package com.h2rd.refactoring.service;

import java.util.ArrayList;

import com.h2rd.refactoring.model.User;
import com.h2rd.refactoring.resource.DuplicateEmailException;
import com.sun.jersey.api.NotFoundException;

public class UserService {

	private static final String CLASSNAME="UserService";
    public ArrayList<User> users;

    public static UserService userDao;

    public static UserService getUserDao() {
        if (userDao == null) {
            userDao = new UserService();
        }
        return userDao;
    }

    public void saveUser(User user) throws DuplicateEmailException {
    	
    	String methodName="saveUser";
    	if(user == null ) {
    		System.err.println(CLASSNAME+ " " +methodName+" "+"User is Null" );
    		throw new NotFoundException();
    	}else if(user.getName() == null || user.getEmail().isEmpty()) {
    		System.err.println(CLASSNAME+ " " +methodName+" "+"EmailId is Null or Empty" );
    		throw new NotFoundException();
    	}
    	
        if (users == null) {
            users = new ArrayList<User>();
        }

    	// To check if email already exists
    	String email=user.getEmail();
    	boolean duplicate= false;
    	for (User us : users) {
    		if(us.getEmail().equals(email)) {
    			duplicate=true;
    			break;
    		}
    	}
    	
    	if(duplicate==true) {
    		System.err.println(CLASSNAME+ " " +methodName+" "+"User with given email already exists");
    		throw new DuplicateEmailException();
    	}
        
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        try {
            return users;
        } catch (Throwable e) {
            System.out.println("error");
            return null;
        }
    }

    public void deleteUser(String email) {
        try {
        	int index=0;
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    break;
                }
                index++;
            }
            users.remove(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User userToUpdate) {
        try {
            for (User user : users) {
                if (user.getName() == userToUpdate.getName()) {
                    user.setEmail(userToUpdate.getEmail());
                    user.setRoles(userToUpdate.getRoles());
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public User findUser(String name) {
        try {
            for (User user : users) {
                if (user.getName() == name) {
                    return user;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
}

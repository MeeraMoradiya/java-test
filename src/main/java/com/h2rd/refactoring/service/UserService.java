package com.h2rd.refactoring.service;

import java.util.ArrayList;
import java.util.List;

import com.h2rd.refactoring.exception.CustomException;
import com.h2rd.refactoring.exception.CustomExceptionMapper;
import com.h2rd.refactoring.model.User;
import com.sun.jersey.api.NotFoundException;

public class UserService {

	private static final String CLASSNAME="UserService";
    public List<User> users;

    public static UserService userDao;

    public static UserService getUserDao() {
        if (userDao == null) {
            userDao = new UserService();
        }
        return userDao;
    }

    public void saveUser(User user) {
    	
    	String methodName="saveUser";
    	if(user == null ) {
    		throw new CustomException("User is Null");
    	}else if(user.getName() == null || user.getEmail().isEmpty()) {
    		throw new CustomException("EmailId is Null or Empty");
    	}else if(user.getRoles() == null || user.getRoles().isEmpty()) {
    		throw new CustomException("User must have atleast one role");
    	}
    	
        if (users == null) {
            users = new ArrayList<User>();
        }
    	boolean duplicate= isDuplicateEmail(user.getEmail());
    	
    	if(duplicate==true) {
    		System.err.println(CLASSNAME+ " " +methodName+" "+"User with given email already exists");
    		throw new CustomException("User with given email already exists"+user.getEmail());
    	}
        
        users.add(user);
    }

    public List<User> getUsers() {
    	String methodName="getUsers";
        try {
            return users;
        } catch (Exception e) {
            System.err.println(CLASSNAME+" "+methodName+" "+" Exception in getting users"+e);
            throw new NotFoundException();
        }
    }

    public void deleteUser(String email) throws Exception{
    	String methodName="deleteUser";
    	boolean exists=false;
        try {
        	int index=0;
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                	exists=true;
                    break;
                }
                index++;
            }
            if(exists) {
            	users.remove(index);
            }else {
            	throw new CustomException("User with given email does not exist");
            }
            
        } catch (Exception e) {
        	 System.err.println(CLASSNAME+" "+methodName+" "+" Exception in delete user"+e);
             throw e;
        }
    }

    public void updateUser(User userToUpdate) throws Exception{
    	String methodName="updateUser";
    	boolean exists=false;
    	if(userToUpdate.getRoles() == null || userToUpdate.getRoles().isEmpty()) {
    		throw new NotFoundException("User must have atleast one role");
    	}
        try {
            for (User user : users) {
                if (user.getEmail().equals(userToUpdate.getEmail())) {
                    user.setName(userToUpdate.getEmail());
                    user.setRoles(userToUpdate.getRoles());
                    exists=true;
                }
            }
            if(!exists) {
            	throw new CustomException("User with given email does not exist");
            }
        } catch (Exception e) {
        	System.err.println(CLASSNAME+" "+methodName+" "+" Exception in delete user"+e);
            throw e;
        }
    }

    public User findUser(String name) throws Exception{
    	
    	String methodName="findUser";
        try {
            for (User user : users) {
                if (user.getName() == name) {
                    return user;
                }
            }
        } catch (Exception e) {
        	System.err.println(CLASSNAME+" "+methodName+" "+" Exception in delete user"+e);
            throw e;
        }
        return null;
    }
    
    public boolean isDuplicateEmail(String email) {
    	for (User us : users) {
    		if(us.getEmail().equals(email)) {
    			return true;
    		}
    	}
    	return false;
    	
    }
}

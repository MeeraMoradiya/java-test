package com.h2rd.refactoring.resource;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DuplicateEmailException extends Exception implements ExceptionMapper<DuplicateEmailException>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DuplicateEmailException(){
			super("User with Given email already exists");
	}
	
	public DuplicateEmailException(String str){
		super(str);
	}

	public Response toResponse(DuplicateEmailException exception) {
		return Response.status(404).entity(exception.getMessage())
                .type("text/plain").build();
	}

}

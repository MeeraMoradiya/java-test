package com.h2rd.refactoring.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.h2rd.refactoring.model.ErrorMessage;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<CustomException>{

	
	private static final long serialVersionUID = 1L;

	
	public Response toResponse(CustomException exception) {
		// TODO Auto-generated method stub
		ErrorMessage errMsg=new ErrorMessage(exception.getMessage(),404,"");
		return Response.status(Status.NOT_FOUND).entity(errMsg)
                .type("text/plain").build();
	}
	
	
	
	
	

}

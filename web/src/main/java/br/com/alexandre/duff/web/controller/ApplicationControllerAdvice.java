package br.com.alexandre.duff.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.alexandre.duff.infrastructure.error.ErrorInfo;
import br.com.alexandre.duff.infrastructure.exception.ApplicationException;

@ControllerAdvice
public class ApplicationControllerAdvice {
	
	@Autowired
	private MessageSourceAccessor accessor;
	
	private Logger logger = LoggerFactory.getLogger(ApplicationControllerAdvice.class);
	
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    public ErrorInfo handleApplicationException(final ApplicationException e) {
    	logger.info("Application Exception: ", e);
    	final String CODE = e.getCode() == null ? "DUFF.001" : e.getCode();
    	return new ErrorInfo(CODE, accessor.getMessage(CODE));
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ErrorInfo handleIllegalArgumentException(final IllegalArgumentException e) {
    	logger.info("Illegal Argument Exception: ", e);
    	final String CODE = "DUFF.002";
    	return new ErrorInfo(CODE, accessor.getMessage(CODE));
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ErrorInfo handleRuntimeException(final RuntimeException e) {
    	logger.info("Runtime Exception: ", e);
    	final String CODE = "DUFF.003";
    	return new ErrorInfo(CODE, accessor.getMessage(CODE));
    }
}

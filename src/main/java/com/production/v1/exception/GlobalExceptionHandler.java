package com.production.v1.exception;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
	//specific exceptions

	 private Log logger=LogFactory.getLog(GlobalExceptionHandler.class);
	//Global exception
	@ExceptionHandler(Exception.class)
	  public String handleException(HttpServletRequest request,Exception ex) {
	   logger.error("Request "+request.getRequestURL() +" threw an exception ", ex);
		return "admin/error";
	  }
	
	@ExceptionHandler(FileStorageException.class)
	public ModelAndView handleFileException(FileStorageException exception) {
		ModelAndView mav = new ModelAndView();
        mav.addObject("message", exception.getMsg());
        mav.setViewName("error");
        return mav;
	}
}

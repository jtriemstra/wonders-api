package com.jtriemstra.wonders.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jtriemstra.wonders.api.log.LogService;

@ControllerAdvice
public class ExceptionLoggingAdvice {
	@Autowired
	LogService logService1;
	
	@ExceptionHandler({ RuntimeException.class})
	private void handleError(RuntimeException e, HttpServletRequest request) {
		
		String card = request.getParameter("card");
		String[] options = request.getParameterValues("options");
		logService1.logError(request, 
				"request body has been consumed", 
				"{\"error\":\"" + e.getMessage() + "\"}",
				request.getParameter("gameName"),
				request.getParameter("playerId"));
	}
}

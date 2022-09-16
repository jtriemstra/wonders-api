package com.jtriemstra.wonders.api.log;

import javax.servlet.http.HttpServletRequest;

public interface LogService {
	void logRequest(HttpServletRequest request, String body, String response);
}

package com.jtriemstra.wonders.api.log;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface LogService {
	void logRequest(HttpServletRequest request, String body, String response, String gameName, String playerId);
	void logError(HttpServletRequest request, String body, String response, String gameName, String playerId);
	List<RequestLogItem> getAll();
	List<RequestLogItem> getAll(String gameName);
	List<RequestLogItem> getAll(String gameName, String playerId);
}

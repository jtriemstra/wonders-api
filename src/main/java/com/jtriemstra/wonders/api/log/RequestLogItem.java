package com.jtriemstra.wonders.api.log;

import java.time.Instant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import lombok.Data;

@Data
public class RequestLogItem {
	private String url;
	private String body;
	private Cookie[] cookies;
	private String response;
	private Instant timestamp;
	private String gameName;
	private String playerId;
	
	public RequestLogItem(HttpServletRequest request, String body, String response, Instant timestamp, String gameName, String playerName) {
		this.response = response;
		this.timestamp = timestamp;
		this.url = request.getRequestURI();
		this.cookies = request.getCookies();
		this.body = body;
		this.gameName = gameName;
		this.playerId = playerName;
	}
}

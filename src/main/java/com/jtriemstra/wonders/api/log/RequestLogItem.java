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
	
	public RequestLogItem(HttpServletRequest request, String body, String response, Instant timestamp) {
		this.response = response;
		this.timestamp = timestamp;
		this.url = request.getRequestURI();
		this.cookies = request.getCookies();
		this.body = body;
	}
}

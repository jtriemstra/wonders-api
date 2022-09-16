package com.jtriemstra.wonders.api.log;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class MemoryLogService implements LogService {
	private List<RequestLogItem> requests;
	private Clock clock;	

	public MemoryLogService(Clock clock) {
		requests = new ArrayList<>();
		this.clock = clock;
	}
	
	public void logRequest(HttpServletRequest request, String body, String response) {
		requests.add(new RequestLogItem(request, body, response, clock.instant()));
	}
}

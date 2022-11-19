package com.jtriemstra.wonders.api.log;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import lombok.Setter;

@Component
public class MemoryLogService implements LogService {
	private List<RequestLogItem> requests;
	private List<RequestLogItem> unRepeatedRequests;
	private Clock clock;
	@Setter
	private boolean returnOnlyUnique;

	public MemoryLogService(Clock clock) {
		requests = new ArrayList<>();
		unRepeatedRequests = new ArrayList<>();
		this.clock = clock;
		this.returnOnlyUnique = false;
	}
	
	@Override
	public void logRequest(HttpServletRequest request, String body, String response, String gameName, String playerId) {
		Instant timestamp = clock.instant();
		logUnRepeatedRequest(request, body, response, gameName, playerId, timestamp, false);
		// this is a potential memory problem in non-test
		requests.add(new RequestLogItem(request, body, response, timestamp, gameName, playerId, false));		
	}

	@Override
	public void logError(HttpServletRequest request, String body, String response, String gameName, String playerId) {
		Instant timestamp = clock.instant();
		logUnRepeatedRequest(request, body, response, gameName, playerId, timestamp, true);
		// this is a potential memory problem in non-test
		requests.add(new RequestLogItem(request, body, response, timestamp, gameName, playerId, true));		
	}
	
	private void logUnRepeatedRequest(HttpServletRequest request, String body, String response, String gameName, String playerId, Instant timestamp, boolean isError) {
		synchronized(this) {
			List<RequestLogItem> thisPlayer = requests
			.stream()
			.filter(r -> r.getGameName().equals(gameName) && (r.getPlayerId() == null || r.getPlayerId().equals(playerId)))
			.sorted(Comparator.comparing(RequestLogItem::getTimestamp))
			.collect(Collectors.toList());
			
			if (thisPlayer.size() == 0 || !thisPlayer.get(thisPlayer.size() - 1).getUrl().equals(request.getRequestURI())) {
				unRepeatedRequests.add(new RequestLogItem(request, body, response, timestamp, gameName, playerId, isError));
			}
		}
	}
	
	@Override
	public List<RequestLogItem> getAll() {
		List<RequestLogItem> sourceList = returnOnlyUnique ? unRepeatedRequests : requests;
		return sourceList
			.stream()
			.sorted(Comparator.comparing(RequestLogItem::getTimestamp))
			.collect(Collectors.toList());
	}

	@Override
	public List<RequestLogItem> getAll(String gameName) {
		List<RequestLogItem> sourceList = returnOnlyUnique ? unRepeatedRequests : requests;
		
		return sourceList
			.stream()
			.filter(r -> r.getGameName().equals(gameName))
			.sorted(Comparator.comparing(RequestLogItem::getTimestamp))
			.collect(Collectors.toList());
	}

	@Override
	public List<RequestLogItem> getAll(String gameName, String playerId) {
		List<RequestLogItem> sourceList = returnOnlyUnique ? unRepeatedRequests : requests;
		
		return sourceList
			.stream()
			.filter(r -> r.getGameName().equals(gameName) && r.getPlayerId().equals(playerId))
			.sorted(Comparator.comparing(RequestLogItem::getTimestamp))
			.collect(Collectors.toList());
	}
}

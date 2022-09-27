package com.jtriemstra.wonders.api.log;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MemoryLogServiceUnitTests {
	
	private MemoryLogService logServiceUnderTest;
	private Clock mockClock;
	
	private HttpServletRequest getMockRequest() {
		HttpServletRequest mock = Mockito.mock(HttpServletRequest.class);
		
		Mockito.doReturn("test-uri").when(mock).getRequestURI();
		
		return mock;
	}
	
	private HttpServletRequest getMockRequest(String uri) {
		HttpServletRequest mock = getMockRequest();
		
		Mockito.doReturn(uri).when(mock).getRequestURI();
		
		return mock;
	}
	
	private Clock getMockClock(String... instants) {
		Clock mock = Mockito.mock(Clock.class);
		
		if (instants.length == 0) {
			Instant i = Instant.parse("2022-01-01T00:00:00Z");
			Mockito.when(mock.instant()).thenReturn(i);
		}
		else {
			Instant i = Instant.parse("2022-01-01T00:00:00Z");
			Instant[] additionalInstants = new Instant[instants.length - 1];
			for (int x=0; x<additionalInstants.length; x++) {
				additionalInstants[x] = Instant.parse(instants[x+1]);
			}
			Mockito.when(mock.instant()).thenReturn(i, additionalInstants);
		}
		
		return mock;
	}
	
	@BeforeEach
	public void setupTest() {
		mockClock = getMockClock();
		logServiceUnderTest = new MemoryLogService(mockClock);
	}
	
	@Test
	public void when_requests_are_duplicate_then_can_return_all() {
		logServiceUnderTest.logRequest(getMockRequest(), "test-body", "test-response", "test-game", "test-player");
		logServiceUnderTest.logRequest(getMockRequest(), "test-body", "test-response", "test-game", "test-player");
		
		Assertions.assertEquals(2, logServiceUnderTest.getAll().size());
	}

	@Test
	public void when_requests_are_duplicate_then_unique_only_returns_one() {
		logServiceUnderTest.logRequest(getMockRequest(), "test-body", "test-response", "test-game", "test-player");
		logServiceUnderTest.logRequest(getMockRequest(), "test-body-1", "test-response-2", "test-game", "test-player");
		
		logServiceUnderTest.setReturnOnlyUnique(true);
		
		Assertions.assertEquals(1, logServiceUnderTest.getAll().size());
	}
	
	@Test
	public void when_requests_are_not_duplicate_then_unique_only_returns_two() {
		logServiceUnderTest.logRequest(getMockRequest(), "test-body", "test-response", "test-game", "test-player");
		logServiceUnderTest.logRequest(getMockRequest("test-uri-2"), "test-body-1", "test-response-2", "test-game", "test-player");
		
		logServiceUnderTest.setReturnOnlyUnique(true);
		
		Assertions.assertEquals(2, logServiceUnderTest.getAll().size());
	}
	
	@Test
	public void when_requests_are_added_out_of_order_are_returned_in_order() {
		mockClock = getMockClock("2022-01-01T00:00:00Z", "2022-01-03T00:00:00Z", "2022-01-02T00:00:00Z");
		logServiceUnderTest = new MemoryLogService(mockClock);
		
		logServiceUnderTest.logRequest(getMockRequest(), "test-body", "test-response", "test-game", "test-player");
		logServiceUnderTest.logRequest(getMockRequest("test-uri-2"), "test-body-1", "test-response-2", "test-game", "test-player");
		logServiceUnderTest.logRequest(getMockRequest("test-uri-3"), "test-body-1", "test-response-3", "test-game", "test-player");
		
		List<RequestLogItem> results = logServiceUnderTest.getAll();
		Assertions.assertEquals("test-uri", results.get(0).getUrl());
		Assertions.assertEquals("test-uri-3", results.get(1).getUrl());
		Assertions.assertEquals("test-uri-2", results.get(2).getUrl());
	}
	
	@Test
	public void when_requests_alternate_then_unique_returns_all() {
		mockClock = getMockClock("2022-01-01T00:00:00Z", "2022-01-02T00:00:00Z", "2022-01-03T00:00:00Z");
		logServiceUnderTest = new MemoryLogService(mockClock);
		
		logServiceUnderTest.logRequest(getMockRequest(), "test-body", "test-response", "test-game", "test-player");
		logServiceUnderTest.logRequest(getMockRequest("test-uri-2"), "test-body-1", "test-response-2", "test-game", "test-player");
		logServiceUnderTest.logRequest(getMockRequest(), "test-body", "test-response", "test-game", "test-player");
		
		logServiceUnderTest.setReturnOnlyUnique(true);
		
		Assertions.assertEquals(3, logServiceUnderTest.getAll().size());
	}
}

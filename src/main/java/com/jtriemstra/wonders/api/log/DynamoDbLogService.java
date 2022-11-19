package com.jtriemstra.wonders.api.log;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Primary
@Profile("!test")
public class DynamoDbLogService implements LogService {
	private static final String AWS_REGION = "us-east-2";
	private static final String LOG_TABLE_NAME = "wonders-api-log";
	private Clock clock;
	private Table logTable;
	
	public DynamoDbLogService(Clock clock) {
		this.clock = clock;
	}
	
	@PostConstruct
	public void init() {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(AWS_REGION).build();
		DynamoDB dynamoDB = new DynamoDB(client);
		
		logTable = dynamoDB.getTable(LOG_TABLE_NAME);
	}
	
	@Override
	public void logRequest(HttpServletRequest request, String body, String response, String gameName, String playerId) {
		Instant timestamp = clock.instant();
		Map<String, String> cookies = new HashMap<>();
		
		if (request.getCookies() != null) {
			Arrays.asList(request.getCookies()).forEach(c -> cookies.put(c.getName(), c.getValue()));	
		}
		
		Item item = new Item()
		    .withPrimaryKey("rowid", UUID.randomUUID().toString())
		    .withString("timestamp", DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss.SSS").withZone( ZoneId.systemDefault() ).format(timestamp))
		    .withString("url", request.getRequestURI())
		    .withMap("cookies", cookies)
		    .withString("gameName", gameName)
		    .withString("playerId", playerId)
		    .withString("requestBody", body)
		    .withString("responseBody", response)
		    .withString("isError","false");
		
		logTable.putItem(item);	
	}

	@Override
	public void logError(HttpServletRequest request, String body, String response, String gameName, String playerId) {
		Instant timestamp = clock.instant();
		Map<String, String> cookies = new HashMap<>();
		
		if (request.getCookies() != null) {
			Arrays.asList(request.getCookies()).forEach(c -> cookies.put(c.getName(), c.getValue()));	
		}
		
		Item item = new Item()
		    .withPrimaryKey("rowid", UUID.randomUUID().toString())
		    .withString("timestamp", DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss.SSS").withZone( ZoneId.systemDefault() ).format(timestamp))
		    .withString("url", request.getRequestURI())
		    .withMap("cookies", cookies)
		    .withString("gameName", gameName)
		    .withString("playerId", playerId)
		    .withString("requestBody", body)
		    .withString("responseBody", response)
		    .withString("isError","true");
		
		logTable.putItem(item);	
	}
			
	public List<RequestLogItem> getAll() {return null;}
	public List<RequestLogItem> getAll(String gameName) {return null;}
	public List<RequestLogItem> getAll(String gameName, String playerId) {return null;}
}

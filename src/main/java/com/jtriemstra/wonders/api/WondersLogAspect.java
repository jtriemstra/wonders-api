package com.jtriemstra.wonders.api;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.request.BuildRequest;
import com.jtriemstra.wonders.api.dto.request.ChooseGuildRequest;
import com.jtriemstra.wonders.api.dto.request.ChooseScienceRequest;
import com.jtriemstra.wonders.api.dto.request.CreateRequest;
import com.jtriemstra.wonders.api.dto.request.DiscardRequest;
import com.jtriemstra.wonders.api.dto.request.GetEndOfAgeRequest;
import com.jtriemstra.wonders.api.dto.request.GetEndOfGameRequest;
import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.dto.request.PlayFreeRequest;
import com.jtriemstra.wonders.api.dto.request.PlayRequest;
import com.jtriemstra.wonders.api.dto.request.StartAgeRequest;
import com.jtriemstra.wonders.api.dto.request.WaitRequest;
import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.model.deprecated.Player;

@Aspect
@Component
public class WondersLogAspect {
	private Logger logger1 = LoggerFactory.getLogger("requests");
	private Logger logger2 = LoggerFactory.getLogger("state");
	private Logger logger3 = LoggerFactory.getLogger("tests");
	
	@AfterReturning("@annotation(WondersLogger) && args(createRequest)")
	public void doLogging(CreateRequest createRequest) throws Throwable {
		logger1.info(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
	}
	
	@AfterReturning(pointcut = "@annotation(WondersLogger) && args(baseRequest)", returning="baseResponse")
	public void doLogging(BaseRequest baseRequest, BaseResponse baseResponse) throws Throwable {
		logger1.info(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
		logger2.info(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
		logger2.info(serializeResponse(baseResponse));
		logger2.info("");
		
		logger3.info(generateTestLog(baseRequest));
	}
	
	//TODO: make this environment-sensitive in some way
	private String serializeResponse(BaseResponse r) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(r);	
		}
		catch (Exception e) {
			System.out.println("Exception writing JSON for log");
			e.printStackTrace();
			
			return "";
		}
	}
	
	private String generateTestLog(BaseRequest r) {
		if (r instanceof StartAgeRequest) {
			return r.getPlayerId() + ".doAction(new StartAgeRequest(), g);";
		}
		else if (r instanceof BuildRequest) {
			return r.getPlayerId() + ".doAction(createBuildRequest(\"" + ((BuildRequest) r).getCardName() + "), g);";
		}
		else if (r instanceof DiscardRequest) {
			return r.getPlayerId() + ".doAction(createDiscardRequest(\"" + ((DiscardRequest) r).getCardName() + "), g);";
		}
		else if (r instanceof GetEndOfAgeRequest) {
			return r.getPlayerId() + ".doAction(new GetEndOfAgeRequest(), g);";
		}
		else if (r instanceof GetEndOfGameRequest) {
			return r.getPlayerId() + ".doAction(new GetEndOfGameRequest(), g);";
		}
		else if (r instanceof OptionsRequest) {
			return r.getPlayerId() + ".doAction(new OptionsRequest(), g);";
		}
		else if (r instanceof PlayRequest) {
			return r.getPlayerId() + ".doAction(createPlayRequest(\"" + ((PlayRequest) r).getCardName() + "\"), g);";	
		}
		else if (r instanceof WaitRequest) {
			return r.getPlayerId() + ".doAction(new WaitRequest(), g);";	
		}
		else if (r instanceof PlayFreeRequest) {
			return r.getPlayerId() + ".doAction(createPlayFreeRequest(\"" + ((PlayFreeRequest) r).getCardName() + "\"), g);";
		}
		else if (r instanceof ChooseGuildRequest) {
			return r.getPlayerId() + ".doAction(createChooseGuildRequest(\"" + ((ChooseGuildRequest) r).getOptionName() +  "\"), g);";
		}
		else if (r instanceof ChooseScienceRequest) {
			return r.getPlayerId() + ".doAction(createChooseScienceRequest(\"" + ((ChooseScienceRequest) r).getChoice() +"\"), g);";
		}
		else {
			return "";
		}
	}	
}

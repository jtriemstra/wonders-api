package com.jtriemstra.wonders.api.log;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/log")
@AllArgsConstructor
public class LogController
{
	
	private LogService logService;
	
	@GetMapping("/")
    public List<RequestLogItem> list(String gameName, String playerId) {
		if (!StringUtils.isEmpty(gameName) && !StringUtils.isEmpty(playerId)) {
			return logService.getAll(gameName, playerId);
		}
		else if (!StringUtils.isEmpty(gameName)) {
			return logService.getAll(gameName);	
		}
		else {
			return logService.getAll();
		}
    }
}

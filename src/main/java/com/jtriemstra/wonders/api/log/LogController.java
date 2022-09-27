package com.jtriemstra.wonders.api.log;

import java.util.List;

import org.springframework.stereotype.Controller;
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
    public List<RequestLogItem> list() {
        return logService.getAll();
    }

	@GetMapping("/")
    public List<RequestLogItem> list(String gameName) {
        return logService.getAll(gameName);
    }

	@GetMapping("/")
    public List<RequestLogItem> list(String gameName, String playerId) {
        return logService.getAll(gameName, playerId);
    }
}

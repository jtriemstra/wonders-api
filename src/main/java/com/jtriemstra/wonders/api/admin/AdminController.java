package com.jtriemstra.wonders.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jtriemstra.wonders.api.state.StateService;

@RestController
@CrossOrigin(origins = {"http://localhost:8001", "https://master.d1rb5aud676z7x.amplifyapp.com"})
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private StateService stateService;
	
	@RequestMapping(value="/current", produces="application/json", method = RequestMethod.GET)
	public String currentState(String game) {
		return stateService.getCurrentGameState(game);
	}
}

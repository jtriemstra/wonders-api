package com.jtriemstra.wonders.api.model.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.PlayerFactory;

public class BoardTestBase {

	@Autowired
	PlayerFactory playerFactory;
	
	@Autowired
	GameFactory gameFactory;
	
	@Autowired
	@Qualifier("createNamedBoardFactory")
	BoardFactory boardFactory;
}

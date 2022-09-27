package com.jtriemstra.wonders.api;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import com.jtriemstra.wonders.api.model.CardList;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.action.ActionList;
import com.jtriemstra.wonders.api.model.board.BoardSource;
import com.jtriemstra.wonders.api.model.board.BoardSourceLeadersDecorator;
import com.jtriemstra.wonders.api.model.deck.leaders.LeaderDeck;
import com.jtriemstra.wonders.api.model.leaders.PlayerLeaders;
import com.jtriemstra.wonders.api.notifications.NotificationService;
import com.jtriemstra.wonders.api.state.StateService;

@TestConfiguration
public class LeadersTestConfiguration {
	@Autowired
	LeaderDeck leaderDeck;		

	@Bean
	@Scope("prototype")
	@Primary
	public BoardSource leaderBoardSource(@Qualifier("boardSource") BoardSource input) {
		return new BoardSourceLeadersDecorator(input, leaderDeck);
	} 

	@Bean
	@Primary
	public PlayerFactory createPlayerLeadersFactory(@Autowired NotificationService notifications, @Autowired StateService stateService) {
		return (name) -> createRealPlayerLeaders(name, notifications, stateService);
	}

	@Bean
	@Scope("prototype")
	@Primary
	public IPlayer createRealPlayerLeaders(String playerName, NotificationService notifications, StateService stateService) {
		return new PlayerLeaders(new Player(playerName, new ActionList(), new ArrayList<>(), new ArrayList<>(), new CardList(), notifications, stateService));
	}
}

package com.jtriemstra.wonders.api.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;

import com.jtriemstra.wonders.api.model.board.BoardSource;
import com.jtriemstra.wonders.api.model.board.BoardSourceLeadersDecorator;
import com.jtriemstra.wonders.api.model.deck.CardFactory;
import com.jtriemstra.wonders.api.model.deck.leaders.GuildCardFactoryLeaders;
import com.jtriemstra.wonders.api.model.deck.leaders.LeaderDeck;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactory;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactoryLeader;
import com.jtriemstra.wonders.api.model.phases.PostTurnActionFactoryLeader;
import com.jtriemstra.wonders.api.state.MemoryStateService;

@Configuration
@RequestScope
public class LeaderExpansion implements Expansion {
	
	@Autowired
	LeaderDeck leaderDeck;

	@Override
	public String getName() {
		return "leaders";
	}
	
	@Bean
	@Scope("prototype")
	public BoardSource decorateBoardSource(BoardSource input) {
		return new BoardSourceLeadersDecorator(input, leaderDeck);
	}
	
	@Bean
	@Scope("prototype")
	public CardFactory decorateGuilds(CardFactory input) {
		return new GuildCardFactoryLeaders(input);
	}
	
	@Bean
	@Scope("prototype")
	public GamePhaseFactory decoratePhases(GamePhaseFactory input, MemoryStateService stateService) {
		return new GamePhaseFactoryLeader(input, leaderDeck, new PostTurnActionFactoryLeader(stateService));
	}
}

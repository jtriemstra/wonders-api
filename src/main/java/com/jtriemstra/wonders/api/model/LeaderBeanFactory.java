package com.jtriemstra.wonders.api.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;

import com.jtriemstra.wonders.api.model.board.BoardSource;
import com.jtriemstra.wonders.api.model.deck.leaders.LeaderCardFactory;
import com.jtriemstra.wonders.api.model.deck.leaders.LeaderDeck;

@Configuration
public class LeaderBeanFactory {

	/*@Bean
	@Scope("prototype")
	public LeaderExpansion leaderExpansion() {
		return new LeaderExpansion();
	}*/
	
	@Bean
	@Scope("prototype")
	public LeaderCardFactory leaderCardFactory() {
		return new LeaderCardFactory();
	}
	
	@Bean
	@RequestScope
	public LeaderDeck leaderDeck(@Autowired LeaderCardFactory cardFactory) {
		return new LeaderDeck(cardFactory);
	}
	

}

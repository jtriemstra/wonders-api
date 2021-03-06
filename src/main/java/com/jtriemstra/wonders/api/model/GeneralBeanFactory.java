package com.jtriemstra.wonders.api.model;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import com.jtriemstra.wonders.api.model.action.ActionList;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.board.BoardStrategy;
import com.jtriemstra.wonders.api.model.board.NamedBoardStrategy;
import com.jtriemstra.wonders.api.model.board.RandomBoardStrategy;
import com.jtriemstra.wonders.api.model.deck.AgeCardFactory;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;
import com.jtriemstra.wonders.api.model.deck.GuildCardFactoryBasic;

@Configuration
public class GeneralBeanFactory {
	
	@Bean
	public GameList gameListFactory() {
		return new GameList();
	}
	
	@Bean
	public GameFactory createGameFactory() {
		return (name, boardStrategy) -> createRealGame(name, boardStrategy);
	}
	
	@Bean
	@Scope("prototype")
	public Game createRealGame(String gameName, BoardStrategy boardStrategy) {
		PostTurnActions postTurnActions = new PostTurnActions();
		
		//TODO: this is done to simplify test setup, but maybe should be moved there or all Game dependencies re-evaluated
		DeckFactory deckFactory = new DefaultDeckFactory(new AgeCardFactory(), new GuildCardFactoryBasic());
		Game g = new Game(gameName, boardStrategy, new Ages(), deckFactory, postTurnActions, new PostTurnActions());
		
		//TODO: this was originally in the Game class. Putting it here makes that more flexible in testing situations. Worth it?
		postTurnActions.add(null, g.new PlayCardsAction());
		postTurnActions.add(null, g.new ResolveCommerceAction());
		postTurnActions.add(null, g.new DiscardFinalCardAction());
		postTurnActions.add(null, g.new ResolveConflictAction());
		
		return g;
	}
	
	@Bean
	public PlayerFactory createPlayerFactory() {
		return name -> createRealPlayer(name);
	}
	
	@Bean
	@Scope("prototype")
	public Player createRealPlayer(String playerName) {
		return new Player(playerName, new ActionList(), new ArrayList<>(), new ArrayList<>(), new CardList());
	}
	
	@Value("${boardNames:}")
	private String boardNames;
	
	@Bean
	@Scope("prototype")
	@ConditionalOnProperty(name="boardNames", matchIfMissing=false)
	public BoardStrategy createNamedBoardStrategy() {
		return new NamedBoardStrategy(boardNames);
	}
	
	@Bean
	@Scope("prototype")
	@Profile("!test")
	@ConditionalOnMissingBean
	public BoardStrategy createRandomBoardStrategy() {
		return new RandomBoardStrategy();
	}
	
}

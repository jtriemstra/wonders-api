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
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.board.NamedBoardFactory;
import com.jtriemstra.wonders.api.model.board.RandomBoardFactory;

@Configuration
public class GeneralBeanFactory {
	
	@Bean
	public GameList gameListFactory() {
		return new GameList();
	}
	
	@Bean
	public GameFactory createGameFactory() {
		return (name, boardFactory) -> createRealGame(name, boardFactory);
	}
	
	@Bean
	@Scope("prototype")
	public Game createRealGame(String gameName, BoardFactory boardFactory) {
		PostTurnActions postTurnActions = new PostTurnActions();
		
		Game g = new Game(gameName, boardFactory, new Ages(), new DefaultDeckFactory(), postTurnActions, new PostTurnActions());
		
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
	public BoardFactory createNamedBoardFactory() {
		return new NamedBoardFactory(boardNames);
	}
	
	@Bean
	@Scope("prototype")
	@Profile("!test")
	@ConditionalOnMissingBean
	public BoardFactory createRandomBoardFactory() {
		return new RandomBoardFactory();
	}
	

	
	
	
	@Autowired
	com.jtriemstra.wonders.api.model.deprecated.ActionListFactory actionListFactory;
	
	@Bean
	@Scope("prototype")
	public com.jtriemstra.wonders.api.model.deprecated.Player createDeprecatedPlayer(String playerName) {
		return new com.jtriemstra.wonders.api.model.deprecated.Player(playerName, actionListFactory.createActionList());
	}
	
	@Bean
	public com.jtriemstra.wonders.api.model.deprecated.ActionListFactory createActionListFactory() {
		return () -> createDeprecatedActionList();
	}
	
	@Bean
	@Scope("prototype")
	public com.jtriemstra.wonders.api.model.deprecated.ActionList createDeprecatedActionList() {
		return new com.jtriemstra.wonders.api.model.deprecated.ActionList();
	}
	
	@Bean
	public com.jtriemstra.wonders.api.model.deprecated.PlayerFactory createPlayerFactory2() {
		return name -> createDeprecatedPlayer(name);
	}
}

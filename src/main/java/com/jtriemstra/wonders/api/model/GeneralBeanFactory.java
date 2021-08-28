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
import com.jtriemstra.wonders.api.model.board.BoardManager;
import com.jtriemstra.wonders.api.model.board.BoardSide;
import com.jtriemstra.wonders.api.model.board.BoardSource;
import com.jtriemstra.wonders.api.model.board.BoardStrategy;
import com.jtriemstra.wonders.api.model.board.NamedBoardStrategy;
import com.jtriemstra.wonders.api.model.board.RandomBoardStrategy;
import com.jtriemstra.wonders.api.model.phases.GameFlow;

@Configuration
public class GeneralBeanFactory {

	@Bean
	public GameList gameListFactory() {
		return new GameList();
	}

	@Bean
	public GameFactory createGameFactory(
			@Autowired PlayerList players) {
		return (name, numberOfPlayers, phases, boardManager, discard) -> createRealGame(name, numberOfPlayers, discard, players, phases, boardManager);
	}

	@Bean
	@Scope("prototype")
	public Game createRealGame(String gameName, int numberOfPlayers, DiscardPile discard, PlayerList players, GameFlow phases, BoardManager boardManager) {
		Game g = new Game(gameName, numberOfPlayers, discard, players, phases, boardManager);
		return g;
	}
	
	@Bean
	public BoardManagerFactory createBoardManagerFactory(@Autowired BoardStrategy paramBoardStrategy) {
		return (boardSource, sideOptions) -> {
			return new BoardManager(boardSource, paramBoardStrategy, sideOptions);
		};
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
	@ConditionalOnProperty(name = "boardNames", matchIfMissing = false)
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
	
	public interface BoardManagerFactory {
		public BoardManager getManager(BoardSource boardSource, BoardSide sideOptions);
	}

}

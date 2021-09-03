package com.jtriemstra.wonders.api.model;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;

import com.jtriemstra.wonders.api.model.action.ActionList;
import com.jtriemstra.wonders.api.model.board.BoardManager;
import com.jtriemstra.wonders.api.model.board.BoardSide;
import com.jtriemstra.wonders.api.model.board.BoardSource;
import com.jtriemstra.wonders.api.model.board.BoardSourceBasic;
import com.jtriemstra.wonders.api.model.board.BoardStrategy;
import com.jtriemstra.wonders.api.model.board.NamedBoardStrategy;
import com.jtriemstra.wonders.api.model.board.RandomBoardStrategy;
import com.jtriemstra.wonders.api.model.deck.AgeCardFactory;
import com.jtriemstra.wonders.api.model.deck.CardFactory;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;
import com.jtriemstra.wonders.api.model.deck.GuildCardFactoryBasic;
import com.jtriemstra.wonders.api.model.phases.GameFlow;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactory;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactoryBasic;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactoryBoard;
import com.jtriemstra.wonders.api.model.phases.PostTurnActionFactoryDefault;
import com.jtriemstra.wonders.api.model.points.VictoryPointFacadeLeaders;

@Configuration
public class GeneralBeanFactory {
	
	@Autowired Map<String, Expansion> expansions;
	
	@Bean
	public GameList gameListFactory() {
		return new GameList();
	}
	
	@Bean
	@Scope("prototype")
	public DiscardPile discardPile() {
		return new DiscardPile();
	}
	
	@Bean
	@Scope("prototype")
	public CardFactory guildFactory() {
		return new GuildCardFactoryBasic();
	}

	@Bean
	@Scope("prototype")
	public BoardSource boardSource() {
		return new BoardSourceBasic();
	}
	
	@Bean
	@Scope("prototype")
	public CardFactory ageCardFactory() {
		return new AgeCardFactory();
	}
	
	@Bean
	@Scope("prototype")
	public DeckFactoryFactory createDefaultDeckFactory() {
		return (ageCardFactory, guildFactory) -> new DefaultDeckFactory(ageCardFactory, guildFactory); 
	}
	
	@Bean
	@Scope("prototype")
	public PostTurnActionFactoryDefaultFactory createPtaFactory() {
		return discard -> new PostTurnActionFactoryDefault(discard);
	}
	
	@Bean
	@Scope("prototype")
	public GamePhaseFactoryFactory createPhaseFactory() {
		return (deckFactory, numberOfPlayers, ptaFactory) -> new GamePhaseFactoryBasic(deckFactory, numberOfPlayers, ptaFactory);
	}
	
	@Bean
	@Scope("prototype")
	public GameFlowFactory gameFlowFactory() {
		return phaseFactory -> new GameFlow(phaseFactory);
	}
	
	@Bean
	public GameFactory createGameFactory(
			@Autowired PlayerList players,
			@Autowired DiscardPile discard,
			@Autowired BoardManagerFactory boardManagerFactory,
			@Autowired CardFactory guildFactory,
			@Autowired BoardSource boardSource,
			@Autowired CardFactory ageCardFactory,
			@Autowired DeckFactoryFactory deckFactoryFactory,
			@Autowired GamePhaseFactoryFactory phaseFactoryFactory,
			@Autowired GameFlowFactory gameFlowFactory,
			@Autowired PostTurnActionFactoryDefaultFactory ptaFactoryFactory
			) {
		return (name, numberOfPlayers, isLeaders, sideOptions, chooseBoard) -> 
			createRealGame(boardManagerFactory, 
					guildFactory, 
					boardSource, 
					ageCardFactory, 
					deckFactoryFactory, 
					phaseFactoryFactory,
					gameFlowFactory,
					ptaFactoryFactory,
					name, 
					numberOfPlayers, 
					discard, 
					players, 
					isLeaders, 
					sideOptions, 
					chooseBoard);
	}

	@Bean
	@Scope("prototype")
	public Game createRealGame(BoardManagerFactory boardManagerFactory, 
			CardFactory guildFactory, 
			BoardSource boardSource, 
			CardFactory ageCardFactory, 
			DeckFactoryFactory deckFactoryFactory,
			GamePhaseFactoryFactory phaseFactoryFactory,
			GameFlowFactory gameFlowFactory,
			PostTurnActionFactoryDefaultFactory ptaFactoryFactory,
			String gameName, 
			int numberOfPlayers, 
			DiscardPile discard, 
			PlayerList players, 
			boolean isLeaders, 
			BoardSide sideOption, 
			boolean chooseBoard) {
				
		//TODO: can I untangle this leader behavior better?
		
		if (isLeaders) {
			LeaderExpansion leaderExpansion = (LeaderExpansion) expansions.get("leaderExpansion");
			boardSource = leaderExpansion.decorateBoardSource(boardSource);
			guildFactory = leaderExpansion.decorateGuilds(guildFactory);
		}
		
		BoardManager boardManager = boardManagerFactory.getManager(boardSource, sideOption);
		DeckFactory deckFactory = deckFactoryFactory.getFactory(ageCardFactory, guildFactory);
		PostTurnActionFactoryDefault ptaFactory = ptaFactoryFactory.getFactory(discard);
		GamePhaseFactory phaseFactory = phaseFactoryFactory.getFactory(deckFactory, numberOfPlayers, ptaFactory);
				
		if (isLeaders) {
			LeaderExpansion leaderExpansion = (LeaderExpansion) expansions.get("leaderExpansion");
			phaseFactory = leaderExpansion.decoratePhases(phaseFactory);
		}
		
		if (chooseBoard) {
			phaseFactory = new GamePhaseFactoryBoard(phaseFactory, boardManager);
		}
		
		Game g = new Game(gameName, numberOfPlayers, discard, players, gameFlowFactory.getFlow(phaseFactory), boardManager);

		if (isLeaders) {
			g.setInitialCoins(6);
			g.setDefaultCalculation(() -> new VictoryPointFacadeLeaders());
		}
		
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

	public interface DeckFactoryFactory {
		public DeckFactory getFactory(CardFactory ageCardFactory, CardFactory guildFactory);
	}
	
	public interface GamePhaseFactoryFactory {
		public GamePhaseFactory getFactory(DeckFactory deckFactory, int numberOfPlayers, PostTurnActionFactoryDefault ptaFactory);
	}
	
	public interface GameFlowFactory {
		public GameFlow getFlow(GamePhaseFactory phaseFactory);
	}
	
	public interface PostTurnActionFactoryDefaultFactory {
		public PostTurnActionFactoryDefault getFactory(DiscardPile discard); 
	}
}

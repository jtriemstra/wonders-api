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
import com.jtriemstra.wonders.api.model.gamelist.MemoryGameListService;
import com.jtriemstra.wonders.api.model.gamelist.GameListService;
import com.jtriemstra.wonders.api.model.phases.GameFlow;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactory;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactoryBasic;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactoryBoard;
import com.jtriemstra.wonders.api.model.phases.PostTurnActionFactoryDefault;
import com.jtriemstra.wonders.api.model.points.VictoryPointFacadeLeaders;
import com.jtriemstra.wonders.api.notifications.NotificationService;
import com.jtriemstra.wonders.api.state.MemoryStateService;
import com.jtriemstra.wonders.api.state.StateService;

@Configuration
public class GeneralBeanFactory {
	
	@Autowired Map<String, Expansion> expansions;
	
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
	public PostTurnActionFactoryDefaultFactory createPtaFactory(@Autowired StateService stateService) {
		return discard -> new PostTurnActionFactoryDefault(discard, stateService);
	}
	
	@Bean
	@Scope("prototype")
	public GamePhaseFactoryFactory createPhaseFactory(@Autowired StateService stateService) {
		return (deckFactory, numberOfPlayers, ptaFactory) -> new GamePhaseFactoryBasic(deckFactory, numberOfPlayers, ptaFactory, stateService);
	}
	
	@Bean
	@Scope("prototype")
	public GameFlowFactory gameFlowFactory() {
		return phaseFactory -> new GameFlow(phaseFactory);
	}
	
	@Bean
	@Scope("prototype")
	public DiscardPileFactory discardPileFactory() {
		return () -> new DiscardPile();
	}
	
	@Bean
	@Scope("prototype")
	public PlayerListFactory playerListFactory() {
		return () -> new PlayerList();
	}
	
	@Bean
	@Scope("prototype")
	public GameFactory createGameFactory(
			@Autowired PlayerListFactory playerListFactory,
			@Autowired DiscardPileFactory discardFactory,
			@Autowired BoardManagerFactory boardManagerFactory,
			@Autowired CardFactory guildFactory,
			@Autowired BoardSource boardSource,
			@Autowired CardFactory ageCardFactory,
			@Autowired DeckFactoryFactory deckFactoryFactory,
			@Autowired GamePhaseFactoryFactory phaseFactoryFactory,
			@Autowired GameFlowFactory gameFlowFactory,
			@Autowired PostTurnActionFactoryDefaultFactory ptaFactoryFactory,
			@Autowired StateService stateService
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
					discardFactory, 
					playerListFactory, 
					isLeaders, 
					sideOptions, 
					chooseBoard,
					stateService);
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
			DiscardPileFactory discardFactory, 
			PlayerListFactory playerListFactory, 
			boolean isLeaders, 
			BoardSide sideOption, 
			boolean chooseBoard,
			StateService stateService) {
				
		//TODO: can I untangle this leader behavior better?
		
		if (isLeaders) {
			LeaderExpansion leaderExpansion = (LeaderExpansion) expansions.get("leaderExpansion");
			boardSource = leaderExpansion.decorateBoardSource(boardSource);
			guildFactory = leaderExpansion.decorateGuilds(guildFactory);
		}
		
		BoardManager boardManager = boardManagerFactory.getManager(boardSource, sideOption);
		DeckFactory deckFactory = deckFactoryFactory.getFactory(ageCardFactory, guildFactory);
		DiscardPile discard = discardFactory.getDiscard();
		PlayerList players = playerListFactory.getPlayerList();
		PostTurnActionFactoryDefault ptaFactory = ptaFactoryFactory.getFactory(discard);
		GamePhaseFactory phaseFactory = phaseFactoryFactory.getFactory(deckFactory, numberOfPlayers, ptaFactory);
				
		if (isLeaders) {
			LeaderExpansion leaderExpansion = (LeaderExpansion) expansions.get("leaderExpansion");
			phaseFactory = leaderExpansion.decoratePhases(phaseFactory, stateService);
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
	public PlayerFactory createPlayerFactory(@Autowired NotificationService notifications, @Autowired StateService stateService) {
		return (name) -> createRealPlayer(name, notifications, stateService);
	}

	@Bean
	@Scope("prototype")
	public Player createRealPlayer(String playerName, NotificationService notifications, StateService stateService) {
		return new Player(playerName, new ActionList(), new ArrayList<>(), new ArrayList<>(), new CardList(), notifications, stateService);
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
	
	public interface DiscardPileFactory {
		public DiscardPile getDiscard();
	}
	
	public interface PlayerListFactory {
		public PlayerList getPlayerList();
	}
}

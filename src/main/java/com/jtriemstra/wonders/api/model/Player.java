package com.jtriemstra.wonders.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jtriemstra.wonders.api.dto.request.ActionRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.NeighborInfo;
import com.jtriemstra.wonders.api.model.action.ActionList;
import com.jtriemstra.wonders.api.model.action.BaseAction;
import com.jtriemstra.wonders.api.model.action.PossibleActions;
import com.jtriemstra.wonders.api.model.action.provider.DefaultOptionsProvider;
import com.jtriemstra.wonders.api.model.action.provider.OptionsProvider;
import com.jtriemstra.wonders.api.model.board.Board;
import com.jtriemstra.wonders.api.model.board.WonderStage;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.provider.ResourceProvider;
import com.jtriemstra.wonders.api.model.card.provider.ScienceProvider;
import com.jtriemstra.wonders.api.model.card.provider.TradingProvider;
import com.jtriemstra.wonders.api.model.card.provider.TradingProviderList;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildable;
import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildableResult;
import com.jtriemstra.wonders.api.model.playbuildrules.Rule;
import com.jtriemstra.wonders.api.model.playbuildrules.RuleChain;
import com.jtriemstra.wonders.api.model.points.VictoryPointFacade;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;
import com.jtriemstra.wonders.api.notifications.NotificationService;
import com.jtriemstra.wonders.api.state.MemoryStateService;
import com.jtriemstra.wonders.api.state.StateService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Player implements IPlayer {
	@Getter @Setter
	private int coins;
	
	@Getter
	private String name;
	private ActionList actions;
	
	//TODO: this only supports Olympia - could it be pushed into PlayRules
	@Getter @Setter
	private OptionsProvider optionsFactory;
	@Setter
	protected Board board;
	@Getter @Setter
	private CardList hand;	
	private List<ResourceProvider> publicResourceProviders;
	private List<ResourceProvider> privateResourceProviders;
	private List<ScienceProvider> scienceProviders;
	private List<VictoryPointProvider> victoryPoints;
	@Getter
	private TradingProviderList tradingProviders;	
	private CardList cardsPlayed;
	private NotificationService notifications;
	@Getter
	private PlayerArmyFacade armyFacade;
	private StateService stateService;

	//TODO: maybe this is an injected dependency
	@Getter @Setter
	private VictoryPointFacade pointCalculations = new VictoryPointFacade();
	
	public Player(String playerName, 
			ActionList actions,
			List<ResourceProvider> publicResourceProviders,
			List<ResourceProvider> privateResourceProviders,
			CardList cardsPlayed,
			NotificationService notifications,
			StateService stateService) {
		this.name = playerName;
		this.actions = actions;
		this.optionsFactory = new DefaultOptionsProvider();
		this.hand = new CardList();
		this.cardsPlayed = cardsPlayed;
		this.publicResourceProviders = publicResourceProviders;
		this.privateResourceProviders = privateResourceProviders;
		this.scienceProviders = new ArrayList<>();	
		this.tradingProviders = new TradingProviderList();
		this.victoryPoints = new ArrayList<>();
		this.notifications = notifications;
		this.armyFacade = new PlayerArmyFacade();
		this.stateService = stateService;
	}

	@Override
	public boolean equals(Object p1) {
		if (!(p1 instanceof Player)) return false;
		return this.getName().equals(((Player)p1).getName());
	}

	@Override
	public Board.StartingBenefit getStartingBenefit() {
		return board.getStartingBenefit();
	}

	@Override
	public String getBoardName() {
		return board.getName();
	}

	@Override
	public String getBoardSide() {
		return board.getSide();
	}
	
	@Override
	public String[] getBoardHelp() {
		return board.getHelpText();
	}

	@Override
	public ResourceType getBoardResourceName() { 
		return board.getStartingResource() == null ? null : board.getStartingResource().getSingle();
	}

	@Override
	public Board getBoard() {
		return board;
	}

	
	
	
	@Override
	public int getHandSize() {
		return hand.size();
	}

	@Override
	public Card[] getHandCards() {
		return hand.getAll();
	}
	
	@Override
	public Card[] getPlayedCards() {
		return cardsPlayed.getAll();
	}

	@Override
	public boolean hasPlayedCard(Card c) {
		return Stream.of(cardsPlayed.getAll()).anyMatch(c1 -> c.getName().equals(c1.getName()));		
	}

	@Override
	public List<Card> getCardsOfTypeFromBoard(Class clazz){
		return cardsPlayed.getByType(clazz);
	}

	@Override
	public void putCardOnBoard(Card c) {
		cardsPlayed.add(c);
	}

	@Override
	public void discardHand(DiscardPile discard) {
		discard.add(hand.getAll());
		hand.clear();
	}

	@Override
	public void receiveCard(Card c) {
		hand.add(c);
	}
	
	@Override
	public Card removeCardFromHand(String cardName) {
		return hand.remove(cardName);			
	}
	
	private ScheduledAction scheduledAction;
	@Override
	public void scheduleTurnAction(ScheduledAction action) {
		this.scheduledAction = action;
	}
	@Override
	public void doScheduledAction() {
		if (this.scheduledAction != null) {
			this.scheduledAction.execute(notifications);
			this.scheduledAction = null;
		}
	}

	
	
	
	
	@Override
	public Map<VictoryPointType, Integer> getFinalVictoryPoints() {
		return pointCalculations.getFinalVictoryPoints(this);
	}
	
	@Override
	public List<VictoryPointProvider> getVictoryPoints(){
		return victoryPoints.stream().collect(Collectors.toList());
	}
	
	
	
	
	
	private RuleChain playRules = RuleChain.getPlayableRuleChain();
	
	@Override
	public void addPlayRule(Rule pr) {
		playRules.addRule(pr);
	}
	
	private RuleChain buildRules = RuleChain.getBuildableRuleChain();
	
	@Override
	public void addBuildRule(Rule pr) {
		buildRules.addRule(pr);
	}
	
	@Override
	public PlayableBuildableResult canPlay(Card c, IPlayer leftNeighbor, IPlayer rightNeighbor) {
		PlayableBuildable actionEvaluating = new PlayableBuildable(c, this, leftNeighbor, rightNeighbor);
		
		return playRules.evaluate(actionEvaluating);		
	}
	
	@Override
	public PlayableBuildableResult canBuild(WonderStage stage, IPlayer leftNeighbor, IPlayer rightNeighbor) {
		PlayableBuildable actionEvaluating = new PlayableBuildable(stage, this, leftNeighbor, rightNeighbor);
		
		return buildRules.evaluate(actionEvaluating);	
	}
	
	@Override
	public List<CardPlayable> getPlayableCards(IPlayer leftNeighbor, IPlayer rightNeighbor, Card[] cardsToEvaluate){
		List<CardPlayable> playableCards = new ArrayList<>();
		for (Card c : cardsToEvaluate) {
			PlayableBuildableResult result = canPlay(c, leftNeighbor, rightNeighbor);
			
			CardPlayable cp = new CardPlayable(result.getCard(), result.getStatus(), result.getCostOptions(), result.getCost()); 
			cp.setPaymentFunction(result.getPaymentFunction());
			playableCards.add(cp);			
		}
		
		return playableCards;
	}
		
	@Override
	public boolean canPlayByChain(String cardName){
		for (Card c : cardsPlayed) {
			if (c.getFreebies() != null) {
				for (String s : c.getFreebies()) {
					if (cardName.equals(s)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	
	
	
	
	
	@Override
	public void addResourceProvider(ResourceProvider in, boolean isPublic) {
		if (isPublic) {
			publicResourceProviders.add(in);	
		}
		else {
			privateResourceProviders.add(in);
		}
	}

	@Override
	public List<ResourceSet> getResources(boolean isMe) {
		List<ResourceSet> l = new ArrayList<>();
		
		publicResourceProviders.stream().forEach(rp -> l.add(rp.getResources()));
		
		if (isMe) {
			privateResourceProviders.stream().forEach(rp -> l.add(rp.getResources()));
		}
		
		return l;
	}	

	@Override
	public List<ScienceProvider> getScienceProviders() {
		return scienceProviders.stream().collect(Collectors.toList());
	}

	@Override
	public void addScienceProvider(ScienceProvider in) {
		this.scienceProviders.add(in);
	}

	@Override
	public void addTradingProvider(TradingProvider t) {
		this.tradingProviders.add(t);
	}

	@Override
	public void addVPProvider(VictoryPointProvider v) {
		victoryPoints.add(v);
	}

		

	
	
	
	
	@Override
	public PossibleActions popAction() {
		return actions.pop();
	}
	
	@Override
	public void addNextAction(BaseAction... a) {
		actions.push(a);
	}

	@Override
	public PossibleActions getNextAction() {
		return actions.getNext();
	}
	
	@Override
	public BaseAction getCurrentActionByName(String name) {
		return actions.getCurrentByName(name);
	}
	
	public ActionResponse doAction(ActionRequest a, Game game) {
		BaseAction action = this.getCurrentActionByName(a.getActionName());
		ActionResponse r = action.execute(a, this, game);
		
		r.setNextActions(this.getNextAction());
		
		//TODO: (low) these are probably not always needed
		r.setCardsOnBoard(this.getPlayedCards());
		r.setCoins(this.getCoins());
		r.setLeftNeighbor(new NeighborInfo(game.getLeftOf(this)));
		r.setRightNeighbor(new NeighborInfo(game.getRightOf(this)));
		r.setAge(game.getFlow().getCurrentAge());
		r.setBuildState(this.getBuildState());
		r.setDiscards(game.getDiscardAges());
		return r;
	}

	@Override
	public Object[] getOptions() {
		return this.getNextAction().getOptions();
	}

	
	
	
	
	@Override
	public int getNumberOfBuiltStages() {
		return board == null ? 0 : board.getNumberOfBuiltStages();
	}
	
	@Override
	public WonderStage build(Game game) {
		return board.build(this, game);
	}

	@Override
	public String[] getBuildState() {
		return board.getBuildState();
	}

	@Override
	public WonderStage getNextStage() {
		return board.getNextStage();
	}
		

	
	
	
	
	
	
	
	

	
	@Override
	public void gainCoins(int i) {
		coins += i;
	}
	
	
	
	

	
	
	
	
	
	
	private HashMap<String, EventAction> eventListeners = new HashMap<>();
	
	@Override
	public void registerEvent(String name, EventAction action) {
		eventListeners.put(name, action);
	}
	
	@Override
	public void eventNotify(String name) {
		if (eventListeners.containsKey(name)) {
			eventListeners.get(name).execute(this);
		}
	}
	
	public interface EventAction {
		public void execute(IPlayer p);
	}
	
	public interface ScheduledAction {
		public void execute(NotificationService notifications);
	}
	
}

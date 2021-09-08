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
import com.jtriemstra.wonders.api.model.card.provider.CoinProvider;
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
import com.jtriemstra.wonders.api.model.resource.Payment;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;
import com.jtriemstra.wonders.api.notifications.NotificationService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Player {
	@Getter @Setter
	private int coins;
	
	@Getter
	private String name;
	private ActionList actions;
	private OptionsProvider optionsFactory;
	@Setter
	private Board board;
	@Getter @Setter
	private CardList hand;
	private List<Integer> shields;
	private List<ResourceProvider> publicResourceProviders;
	private List<ResourceProvider> privateResourceProviders;
	private List<ScienceProvider> scienceProviders;
	@Getter
	private TradingProviderList tradingProviders;
	private List<CoinProvider> coinProviders = new ArrayList<>();
	private List<Payment> payments;
	private Card cardToPlay;
	private CardList cardsPlayed;
	private Map<Integer, List<Integer>> defeats;
	private Map<Integer, List<Integer>> victories;
	private List<VictoryPointProvider> victoryPoints;
	private Card cardToBuild;
	private Card cardToDiscard;
	private NotificationService notifications;

	//TODO: maybe this is an injected dependency
	@Getter @Setter
	private VictoryPointFacade pointCalculations = new VictoryPointFacade();
	
	public Player(String playerName, 
			ActionList actions,
			List<ResourceProvider> publicResourceProviders,
			List<ResourceProvider> privateResourceProviders,
			CardList cardsPlayed,
			NotificationService notifications) {
		this.name = playerName;
		this.actions = actions;
		this.optionsFactory = new DefaultOptionsProvider();
		this.hand = new CardList();
		this.cardsPlayed = cardsPlayed;
		this.leaderCards = new CardList();
		this.shields = new ArrayList<>();
		this.publicResourceProviders = publicResourceProviders;
		this.privateResourceProviders = privateResourceProviders;
		this.scienceProviders = new ArrayList<>();	
		this.tradingProviders = new TradingProviderList();
		this.payments = new ArrayList<Payment>();
		this.victoryPoints = new ArrayList<>();
		this.defeats = new HashMap<>();
		this.victories = new HashMap<>();
		this.notifications = notifications;
	}

	@Override
	public boolean equals(Object p1) {
		if (!(p1 instanceof Player)) return false;
		return this.getName().equals(((Player)p1).getName());
	}

	public void claimStartingBenefit(Game g) {
		board.addStartingBenefit(this, g);
	}

	public String getBoardName() {
		return board.getName();
	}

	public String getBoardSide() {
		return board.getSide();
	}

	public ResourceType getBoardResourceName() { 
		return board.getStartingResource() == null ? null : board.getStartingResource().getSingle();
	}


	
	
	
	public int getHandSize() {
		return hand.size();
	}

	public Card[] getHandCards() {
		return hand.getAll();
	}
	
	public Card[] getPlayedCards() {
		return cardsPlayed.getAll();
	}

	public boolean hasPlayedCard(Card c) {
		return Stream.of(cardsPlayed.getAll()).anyMatch(c1 -> c.getName().equals(c1.getName()));		
	}

	public List<Card> getCardsOfTypeFromBoard(Class clazz){
		return cardsPlayed.getByType(clazz);
	}

	public void putCardOnBoard(Card c) {
		cardsPlayed.add(c);
	}

	public void playScheduledCard(Game g) {
		if (this.cardToPlay != null) {
			this.cardToPlay.play(this, g);
			putCardOnBoard(this.cardToPlay);	
			hand.remove(this.cardToPlay.getName());
			notifications.addNotification(name + " played " + this.cardToPlay.getName());
			this.cardToPlay = null;
		}
	}
	
	public void buildScheduledCard(Game g) {
		if (this.cardToBuild != null) {
			hand.remove(this.cardToBuild.getName());
			build(g);
			notifications.addNotification(name + " built a stage");
			this.cardToBuild = null;
		}
	}
	
	public void discardScheduledCard(Game g) {
		if (this.cardToDiscard != null) {
			removeCardFromHand(cardToDiscard.getName());
			
			notifications.addNotification(name + " discarded");
			this.cardToDiscard = null;
		}
	}

	public void discardHand(DiscardPile discard) {
		discard.add(hand.getAll());
		hand.clear();
	}

	public void receiveCard(Card c) {
		hand.add(c);
	}
	
	public Card removeCardFromHand(String cardName) {
		return hand.remove(cardName);
	}

	public Card getCardFromHand(String cardName) {
		return hand.get(cardName);
	}

	public void scheduleCardToPlay(Card c) {
		this.cardToPlay = c;
	}

	public void scheduleCardToBuild(Card c) {
		this.cardToBuild = c;
	}

	public void scheduleCardToDiscard(Card c) {
		this.cardToDiscard = c;
	}

	
	
	
	
	public Map<VictoryPointType, Integer> getFinalVictoryPoints() {
		return pointCalculations.getFinalVictoryPoints(this);
	}
	
	public List<VictoryPointProvider> getVictoryPoints(){
		return victoryPoints.stream().collect(Collectors.toList());
	}
	
	//TODO: (low) feels a little odd to expose this - may not need if I can move uses into the playable rule chain
	public void setOptionsFactory(OptionsProvider newFactory) {
		optionsFactory = newFactory;
	}
			
	public void startTurn() {
		log.info("starting turn for " + this.getName());
		this.cardToPlay = null;
		this.cardToBuild = null;
		this.coinProviders.clear();
		this.payments.clear();
		addNextAction(optionsFactory.createGetOptions());
		log.info("action count " + actions.size());
	}
	
	
	
	
	
	
	private RuleChain playRules = RuleChain.getPlayableRuleChain();
	
	public void addPlayRule(Rule pr) {
		playRules.addRule(pr);
	}
	
	private RuleChain buildRules = RuleChain.getBuildableRuleChain();
	
	public void addBuildRule(Rule pr) {
		buildRules.addRule(pr);
	}
	
	public PlayableBuildableResult canPlay(Card c, Player leftNeighbor, Player rightNeighbor) {
		PlayableBuildable actionEvaluating = new PlayableBuildable(c, this, leftNeighbor, rightNeighbor);
		
		return playRules.evaluate(actionEvaluating);		
	}
	
	public PlayableBuildableResult canBuild(WonderStage stage, Player leftNeighbor, Player rightNeighbor) {
		PlayableBuildable actionEvaluating = new PlayableBuildable(stage, this, leftNeighbor, rightNeighbor);
		
		return buildRules.evaluate(actionEvaluating);	
	}
	
	public List<CardPlayable> getPlayableCards(Player leftNeighbor, Player rightNeighbor){
		List<CardPlayable> playableCards = new ArrayList<>();
		for (Card c : hand) {
			PlayableBuildableResult result = canPlay(c, leftNeighbor, rightNeighbor);
			if (result.getCostOptions() == null) {
				CardPlayable cp = new CardPlayable(result.getCard(), result.getStatus(), result.getCost() + result.getLeftCost() + result.getRightCost(), result.getLeftCost(), result.getRightCost(), result.getCost());
				playableCards.add(cp);
			}
			else {
				CardPlayable cp = new CardPlayable(result.getCard(), result.getStatus(), result.getCostOptions(), result.getCost()); 
				playableCards.add(cp);
			}
			
		}
		
		return playableCards;
	}
		
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

	
	
	
	
	
	public void addResourceProvider(ResourceProvider in, boolean isPublic) {
		if (isPublic) {
			publicResourceProviders.add(in);	
		}
		else {
			privateResourceProviders.add(in);
		}
	}

	public List<ResourceSet> getResources(boolean isMe) {
		List<ResourceSet> l = new ArrayList<>();
		
		publicResourceProviders.stream().forEach(rp -> l.add(rp.getResources()));
		
		if (isMe) {
			privateResourceProviders.stream().forEach(rp -> l.add(rp.getResources()));
		}
		
		return l;
	}	

	public List<ScienceProvider> getScienceProviders() {
		return scienceProviders.stream().collect(Collectors.toList());
	}

	public void addScienceProvider(ScienceProvider in) {
		this.scienceProviders.add(in);
	}

	public void addCoinProvider(CoinProvider c) {
		this.coinProviders.add(c);
	}
	public void addTradingProvider(TradingProvider t) {
		this.tradingProviders.add(t);
	}

	public void addVPProvider(VictoryPointProvider v) {
		victoryPoints.add(v);
	}

		

	
	
	
	
	public PossibleActions popAction() {
		return actions.pop();
	}
	
	public void addNextAction(BaseAction... a) {
		actions.push(a);
	}

	public PossibleActions getNextAction() {
		return actions.getNext();
	}
	
	public ActionResponse doAction(ActionRequest a, Game game) {
		BaseAction action = actions.getCurrentByName(a.getActionName());
		ActionResponse r = action.execute(a, this, game);
		
		log.info("doAction complete, action count " + actions.size());
		r.setNextActions(actions.getNext());
		
		//TODO: (low) these are probably not always needed
		r.setCardsOnBoard(getPlayedCards());
		r.setCoins(getCoins());
		r.setLeftNeighbor(new NeighborInfo(game.getLeftOf(this)));
		r.setRightNeighbor(new NeighborInfo(game.getRightOf(this)));
		r.setAge(game.getFlow().getCurrentAge());
		r.setBuildState(getBuildState());
		r.setDiscards(game.getDiscardAges());
		return r;
	}

	public Object[] getOptions() {
		return this.getNextAction().getOptions();
	}

	
	
	
	
	public int getNumberOfBuiltStages() {
		return board == null ? 0 : board.getNumberOfBuiltStages();
	}
	
	public WonderStage build(Game game) {
		return board.build(this, game);
	}

	public String[] getBuildState() {
		return board.getBuildState();
	}

	public WonderStage getNextStage() {
		return board.getNextStage();
	}
		

	
	
	
	
	

	
	

	public void addShields(int shields) {
		this.shields.add(shields);
	}
	
	public int getArmies() {
		return shields.stream().mapToInt(Integer::intValue).sum();
	}

	public void addDefeat(int age) {
		if (!defeats.containsKey(age)) {
			defeats.put(age, new ArrayList<>());
		}
		defeats.get(age).add(-1);
	}

	public void addVictory(int age, int victory) {
		if (!victories.containsKey(age)) {
			victories.put(age, new ArrayList<>());
		}
		victories.get(age).add(victory);
		this.eventNotify("conflict.victory");
	}
	
	public int getNumberOfDefeats() {
		return defeats.values().stream().mapToInt(l -> l.size()).reduce(0, Integer::sum);
	}
	
	public Map<Integer, List<Integer>> getVictories() {
		return victories;
	}
		
	public int getNumberOfDefeats(int age) {
		return (defeats == null || defeats.get(age) == null) ? 0 : defeats.get(age).size();
	}
	
	public int getNumberOfVictories(int age) {
		return (victories == null || victories.get(age) == null) ? 0 : victories.get(age).size();
	}
	
	
	
	
	
	


	public void resolveCommerce() {
		for (Payment t : payments) {
			t.execute();
		}
		gainCoinsFromCardOrBoard();	
		this.coinProviders.clear();
		this.payments.clear();
	}
	
	public void gainCoins(int i) {
		coins += i;
	}

	public void spendCoins(int coinCost) {
		coins -= coinCost;
	}

	public void schedulePayment(Payment p) {
		this.payments.add(p);
	}
	
	public void gainCoinsFromCardOrBoard() {
		if (coinProviders != null) {
			for (CoinProvider c : coinProviders) {
				coins += c.getCoins();	
			}			
		}
	}
	
	
	
	
	//TODO: extract the leader functionality somewhere. Inheriting from Player is the only thing coming to mind. Also possibly take an approach where the publicly visible "hand" concept could point to either ages or leaders.
	// could make Player an interface, then LeaderPlayer has a BasicPlayer instance, and delegates calls to it. That would be more appealing if this were a smaller class.
	//TODO: is there a better way to handle this? Maybe a Hand and LeaderHand? Remember that Leaders is using the normal cards field for the ones you are choosing. Maybe LeaderPlayer, since there are additional victory point calculation as well
	
	private CardList leaderCards;
	
	public void keepLeader(Card c) {
		leaderCards.add(c);
	}

	
	private CardList tempAgeCards = new CardList();
	
	public void moveLeadersToHand() {
		tempAgeCards.clear();
		hand.forEach(c -> tempAgeCards.add(c));
		hand.clear();
		
		for (Card c : leaderCards) {
			hand.add(c);
		}
	}
	
	public void restoreAgeCards() {
		tempAgeCards.forEach(c -> hand.add(c));
		tempAgeCards.clear();
	}
	
	//TODO: this is only used for tests, better way to handle?
	public int getNumberOfLeaderCards() {
		return leaderCards.size();
	}

	public void clearHand() {
		leaderCards.clear();
		for (Card c : hand) {
			leaderCards.add(c);
		}
		hand.clear();
	}

	
	
	
	
	
	
	private HashMap<String, EventAction> eventListeners = new HashMap<>();
	
	public void registerEvent(String name, EventAction action) {
		eventListeners.put(name, action);
	}
	
	public void eventNotify(String name) {
		if (eventListeners.containsKey(name)) {
			eventListeners.get(name).execute(this);
		}
	}
	
	public interface EventAction {
		public void execute(Player p);
	}
}

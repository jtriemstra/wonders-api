package com.jtriemstra.wonders.api.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.jtriemstra.wonders.api.model.card.ScienceType;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.provider.CoinProvider;
import com.jtriemstra.wonders.api.model.card.provider.ResourceProvider;
import com.jtriemstra.wonders.api.model.card.provider.ScienceProvider;
import com.jtriemstra.wonders.api.model.card.provider.TradingProvider;
import com.jtriemstra.wonders.api.model.card.provider.TradingProviderList;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.LocalResourceEvaluator;
import com.jtriemstra.wonders.api.model.resource.Payment;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;
import com.jtriemstra.wonders.api.model.resource.TradingResourceEvaluator;

public class Player {
	private int coins;
	
	private String name;
	private ActionList actions;
	private OptionsProvider optionsFactory;
	private Board board;
	private CardList cards;
	private List<Integer> shields;
	private List<ResourceProvider> publicResourceProviders;
	private List<ResourceProvider> privateResourceProviders;
	private List<ScienceProvider> scienceProviders;
	private TradingProviderList tradingProviders;
	private CoinProvider currentCoinProvider;
	private List<Payment> payments;
	private Card cardToPlay;
	private CardList cardsPlayed;
	//TODO: is this being used?
	private ActionResponse cachedLastResponse;
	private Map<Integer, List<Integer>> defeats;
	private Map<Integer, List<Integer>> victories;
	private List<VictoryPointProvider> victoryPoints;
	
	public Player(String playerName, 
			ActionList actions,
			List<ResourceProvider> publicResourceProviders,
			List<ResourceProvider> privateResourceProviders,
			CardList cardsPlayed) {
		this.name = playerName;
		this.actions = actions;
		this.optionsFactory = new DefaultOptionsProvider();
		this.cards = new CardList();
		this.cardsPlayed = cardsPlayed;
		this.shields = new ArrayList<>();
		this.publicResourceProviders = publicResourceProviders;
		this.privateResourceProviders = privateResourceProviders;
		this.scienceProviders = new ArrayList<>();	
		this.tradingProviders = new TradingProviderList();
		this.coins = 3;
		this.payments = new ArrayList<Payment>();
		this.victoryPoints = new ArrayList<>();
		this.defeats = new HashMap<>();
		this.victories = new HashMap<>();
	}

	public int getCoins() {
		return coins;
	}

	public CardList getHand() {
		return cards;
	}

	public void setHand(CardList c) {
		cards = c;
	}

	public Card[] getPlayedCards() {
		return cardsPlayed.getAll();
	}
	
	public List<VictoryPointProvider> getVictoryPoints(){
		// TODO: make this a copy for immutability
		return victoryPoints;
	}
	
	//TODO: (low) feels a little odd to expose this 
	public void setOptionsFactory(OptionsProvider newFactory) {
		optionsFactory = newFactory;
	}
	
	public void putCardOnBoard(Card c) {
		cardsPlayed.add(c);
	}
	
	public void startTurn() {
		this.cardToPlay = null;
		this.currentCoinProvider = null;
		this.payments.clear();
		addNextAction(optionsFactory.createGetOptions());
	}
	
	public List<CardPlayable> getPlayableCards(Player leftNeighbor, Player rightNeighbor){
		List<CardPlayable> playableCards = new ArrayList<>();
		for (Card c : cards) {
			CardPlayable cp = canPlay(c, leftNeighbor, rightNeighbor);
			playableCards.add(cp);
		}
		
		return playableCards;
	}
	
	//TODO: possibly unify logic between build and play
	public CardPlayable canPlay(Card c, Player leftNeighbor, Player rightNeighbor) {
		if (hasPlayedCard(c)) {
			return new CardPlayable(c, Status.ERR_DUPLICATE, 0, 0, 0);
		}
		
		if (canPlayForFree(c.getName())) {
			return new CardPlayable(c, Status.OK, 0, 0, 0);
		}
		
		//can't play if you can't afford the $
		if (c.getCoinCost() > coins) {
			return new CardPlayable(c, Status.ERR_COINS, 0, 0, 0);
		}
		
		//it costs nothing
		if (c.getResourceCost() == null) {
			return new CardPlayable(c, Status.OK, 0, 0, 0);
		}
		
		ResourceCost cost = new ResourceCost(c.getResourceCost());
		
		List<ResourceSet> boardResourceAvailable = getResources(true);
		for (int i=0; i<boardResourceAvailable.size();) {
			if (boardResourceAvailable.get(i).isSingle()) {
				ResourceType currentAvailableType = boardResourceAvailable.get(i).getSingle();
				if (cost.isNeeded(currentAvailableType)) {
					cost.reduce(currentAvailableType);
				}
				boardResourceAvailable.remove(i);
			}
			else if (!cost.isNeeded(boardResourceAvailable.get(i))) {
				boardResourceAvailable.remove(i);
			}
			else {
				i++;
			}
			
			if (cost.isComplete()) {
				return new CardPlayable(c, Status.OK, 0, 0, 0);
			}
		}
		
		// if we get here only potentially useful combo cards are left in boardResourceAvailable
		
		// try using just my combo cards
		LocalResourceEvaluator eval = new LocalResourceEvaluator(boardResourceAvailable);
		if (eval.test(cost)) {
			return new CardPlayable(c, Status.OK, 0, 0, 0);
		}
		
		int coinsAvailableForTrade = coins - c.getCoinCost();
		
		if (leftNeighbor != null && rightNeighbor != null) {
			List<ResourceSet> leftResources = filterNeighborResources(leftNeighbor.getResources(false), cost);
			List<ResourceSet> rightResources = filterNeighborResources(rightNeighbor.getResources(false), cost);
			TradingResourceEvaluator eval1 = new TradingResourceEvaluator(boardResourceAvailable, leftResources, rightResources, coinsAvailableForTrade, cost, this.tradingProviders);
			int minCost = eval1.findMinCost();
			//TODO: (low) better flag for too expensive
			if (minCost < 100) {
				return new CardPlayable(c, Status.OK, minCost, eval1.getLeftCost(), eval1.getRightCost());
			}
		}
		
		
		
		return new CardPlayable(c, Status.ERR_RESOURCE, 0, 0, 0);
	}
	
	public Buildable canBuild(Player leftNeighbor, Player rightNeighbor) {
		WonderStage stage = board.getNextStage();
		
		//can't build if no more stages
		if (stage == null) {
			return new Buildable(null, Status.ERR_FINISHED, 0, 0, 0);
		}
		//can't play if you can't afford the $
		if (stage.getCoinCost() > coins) {
			return new Buildable(null, Status.ERR_COINS, 0, 0, 0);
		}
		
		//it costs nothing
		if (stage.getResourceCost() == null) {
			return new Buildable(stage, Status.OK, 0, 0, 0);
		}
		
		ResourceCost cost = new ResourceCost(stage.getResourceCost());
		
		List<ResourceSet> boardResourceAvailable = getResources(true);
		for (int i=0; i<boardResourceAvailable.size();) {
			if (boardResourceAvailable.get(i).isSingle()) {
				ResourceType currentAvailableType = boardResourceAvailable.get(i).getSingle();
				if (cost.isNeeded(currentAvailableType)) {
					cost.reduce(currentAvailableType);
				}
				boardResourceAvailable.remove(i);
			}
			else if (!cost.isNeeded(boardResourceAvailable.get(i))) {
				boardResourceAvailable.remove(i);
			}
			else {
				i++;
			}
		}
		
		if (cost.isComplete()) {
			return new Buildable(stage, Status.OK, 0, 0, 0);
		}
		// if we get here only potentially useful combo cards are left in boardResourceAvailable
		
		// try using just my combo cards
		LocalResourceEvaluator eval = new LocalResourceEvaluator(boardResourceAvailable);
		if (eval.test(cost)) {
			return new Buildable(stage, Status.OK, 0, 0, 0);
		}
		
		int coinsAvailableForTrade = coins - stage.getCoinCost();
		
		if (leftNeighbor != null && rightNeighbor != null) {
			List<ResourceSet> leftResources = filterNeighborResources(leftNeighbor.getResources(false), cost);
			List<ResourceSet> rightResources = filterNeighborResources(rightNeighbor.getResources(false), cost);
			TradingResourceEvaluator eval1 = new TradingResourceEvaluator(boardResourceAvailable, leftResources, rightResources, coinsAvailableForTrade, cost, this.tradingProviders);
			int minCost = eval1.findMinCost();
			//TODO: (low) better flag for too expensive
			if (minCost < 100) {
				return new Buildable(stage, Status.OK, minCost, eval1.getLeftCost(), eval1.getRightCost());
			}
		}
		
		return new Buildable(null, Status.ERR_RESOURCE, 0, 0, 0);
	}
	
	public boolean hasPlayedCard(Card c) {
		for (Card c1 : cardsPlayed) {
			if (c.getName().equals(c1.getName())){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean canPlayForFree(String cardName){
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

	public List<ResourceSet> getResources(boolean isMe) {
		List<ResourceSet> l = new ArrayList<>();
		l.add(board.getStartingResource());
		for (ResourceProvider rp : publicResourceProviders) {
			l.add(rp.getResources());
		}
		
		if (isMe) {
			for (ResourceProvider rp : privateResourceProviders) {
				l.add(rp.getResources());
			}	
		}
		
		return l;
	}	
	
	private List<ResourceSet> filterNeighborResources(List<ResourceSet> input, ResourceCost resourcesNeeded){
		return input.stream().filter(r -> resourcesNeeded.isNeeded(r)).collect(Collectors.toList());
	}

	public void playCard(Game g) {
		if (this.cardToPlay != null) {
			this.cardToPlay.play(this, g);
			playCardToBoard(this.cardToPlay);	
			this.cardToPlay = null;
		}
	}
	
	private void playCardToBoard(Card c) {
		cardsPlayed.add(c);
		cards.remove(c.getName());
	}

	private int getArmyVPs() {
		int result = 0;
		for (List<Integer> l : defeats.values()) {
			result += l.stream().mapToInt(Integer::intValue).sum();
		}
		for (List<Integer> l : victories.values()) {
			result += l.stream().mapToInt(Integer::intValue).sum();
		}
		return result;
	}
	
	private long getScienceVPs() {
		long gears = this.scienceProviders.stream().filter(s -> s.getScience().getScienceOptions()[0] == ScienceType.GEAR).count();
		long compass = this.scienceProviders.stream().filter(s -> s.getScience().getScienceOptions()[0] == ScienceType.COMPASS).count();
		long tablets = this.scienceProviders.stream().filter(s -> s.getScience().getScienceOptions()[0] == ScienceType.TABLET).count();
		
		List<Long> all = Arrays.asList(gears, compass, tablets);
		long sets = all.stream().min(Long::compareTo).get();
		return sets * 7 + (long)Math.pow(gears, 2) + (long)Math.pow(compass,  2) + (long)Math.pow(tablets, 2);
	}
	
	public Map<VictoryPointType, Integer> getFinalVictoryPoints() {
		Map<VictoryPointType, Integer> result = new HashMap<>();
		result.put(VictoryPointType.ARMY, getArmyVPs());
		result.put(VictoryPointType.COIN, getCoins() / 3);
		result.put(VictoryPointType.COMMERCE, this.victoryPoints
				.stream()
				.filter(vpp -> vpp.getType() == VictoryPointType.COMMERCE)
				.mapToInt(VictoryPointProvider::getPoints)
				.sum());
		result.put(VictoryPointType.GUILD, this.victoryPoints.stream().filter(vpp -> vpp.getType() == VictoryPointType.GUILD).mapToInt(VictoryPointProvider::getPoints).sum());
		result.put(VictoryPointType.STAGES, this.victoryPoints.stream().filter(vpp -> vpp.getType() == VictoryPointType.STAGES).mapToInt(VictoryPointProvider::getPoints).sum());
		result.put(VictoryPointType.VICTORY, this.victoryPoints.stream().filter(vpp -> vpp.getType() == VictoryPointType.VICTORY).mapToInt(VictoryPointProvider::getPoints).sum());
		result.put(VictoryPointType.SCIENCE, (int) getScienceVPs());
		
		return result;
	}
	
	public String getName() {
		return this.name;
	}

	public void setBoard(Board b) {
		board = b;
	}

	public String getBoardName() {
		return board.getName();
	}

	public String getBoardSide() {
		return board.getSide();
	}

	public void setCoinProvider(CoinProvider c) {
		this.currentCoinProvider = c;
	}

	public int getHandSize() {
		return cards.size();
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

	public void receiveCard(Card c) {
		cards.add(c);
	}

	public void addShields(int shields) {
		this.shields.add(shields);
	}

	public void addResourceProvider(ResourceProvider in, boolean isPublic) {
		if (isPublic) {
			publicResourceProviders.add(in);	
		}
		else {
			privateResourceProviders.add(in);
		}
	}

	public void addScienceProvider(ScienceProvider in) {
		this.scienceProviders.add(in);
	}

	public void addTradingProvider(TradingProvider t) {
		this.tradingProviders.add(t);
	}

	public List<Card> getCardsOfTypeFromBoard(Class clazz){
		return cardsPlayed.getByType(clazz);
	}

	public int getNumberOfBuiltStages() {
		if (board == null) {
			return 0;
		}
		
		return board.getNumberOfBuiltStages();
	}
	
	public void addVPProvider(VictoryPointProvider v) {
		victoryPoints.add(v);
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
	}
	
	public ActionResponse doAction(ActionRequest a, Game game) {
		BaseAction action = actions.getCurrentByName(a.getActionName());
		ActionResponse r = action.execute(a, this, game);
		
		r.setNextActions(actions.getNext());
		
		//TODO: (low) these are probably not always needed
		r.setCardsOnBoard(getPlayedCards());
		r.setCoins(getCoins());
		r.setLeftNeighbor(new NeighborInfo(game.getLeftOf(this)));
		r.setRightNeighbor(new NeighborInfo(game.getRightOf(this)));
		r.setAge(game.getCurrentAge());
		cachedLastResponse = r;
		return r;
	}
	
	public int getNumberOfDefeats() {
		int result=0;
		for (List<Integer> l : defeats.values()) {
			result += l.size();
		}
		return result;
	}

	public void discard(DiscardPile discard) {
		discard.add(cards.getAll());
		cards.clear();
	}

	public void resolveCommerce() {
		for (Payment t : payments) {
			t.execute();
		}
		gainCoinsFromCardOrBoard();		
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
	
	public Card removeCardFromHand(String cardName) {
		return cards.remove(cardName);
	}

	public Card getCardFromHand(String cardName) {
		return cards.get(cardName);
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
	
	public void scheduleCardToPlay(Card c) {
		this.cardToPlay = c;
	}
	
	public WonderStage build(Game game) {
		return board.build(this, game);
	}

	public int[] getBuildState() {
		return board.getBuildState();
	}

	public void gainCoinsFromCardOrBoard() {
		if (currentCoinProvider != null) {
			coins += currentCoinProvider.getCoins();
		}
	}
	
	public ResourceType getBoardResourceName() { 
		return board.getStartingResource().getSingle();
	}

	@Override
	public boolean equals(Object p1) {
		if (!(p1 instanceof Player)) return false;
		return this.name.equals(((Player)p1).getName());
	}
	
	public Object[] getOptions() {
		return this.getNextAction().getOptions();
	}
}

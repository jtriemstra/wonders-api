package com.jtriemstra.wonders.api.model.leaders;

import java.util.List;
import java.util.Map;

import com.jtriemstra.wonders.api.dto.request.ActionRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.CardList;
import com.jtriemstra.wonders.api.model.DiscardPile;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.Player.EventAction;
import com.jtriemstra.wonders.api.model.Player.ScheduledAction;
import com.jtriemstra.wonders.api.model.PlayerArmyFacade;
import com.jtriemstra.wonders.api.model.action.BaseAction;
import com.jtriemstra.wonders.api.model.action.PossibleActions;
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
import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildableResult;
import com.jtriemstra.wonders.api.model.playbuildrules.Rule;
import com.jtriemstra.wonders.api.model.points.VictoryPointFacade;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class PlayerLeaders implements IPlayer {
	
	private IPlayer innerPlayer;
	
	public PlayerLeaders(IPlayer innerPlayer) {
		this.innerPlayer = innerPlayer;
	}

	@Override
	public void claimStartingBenefit(Game g) {
		innerPlayer.claimStartingBenefit(g);
	}

	@Override
	public String getBoardName() {
		return innerPlayer.getBoardName();
	}

	@Override
	public String getBoardSide() {
		return innerPlayer.getBoardSide();
	}

	@Override
	public ResourceType getBoardResourceName() {
		return innerPlayer.getBoardResourceName();
	}

	@Override
	public int getHandSize() {
		return innerPlayer.getHandSize();
	}

	@Override
	public Card[] getHandCards() {
		return innerPlayer.getHandCards();
	}

	@Override
	public Card[] getPlayedCards() {
		return innerPlayer.getPlayedCards();
	}

	@Override
	public boolean hasPlayedCard(Card c) {
		return innerPlayer.hasPlayedCard(c);
	}

	@Override
	public List<Card> getCardsOfTypeFromBoard(Class clazz) {
		return innerPlayer.getCardsOfTypeFromBoard(clazz);
	}

	@Override
	public void putCardOnBoard(Card c) {
		innerPlayer.putCardOnBoard(c);
	}

	@Override
	public void discardHand(DiscardPile discard) {
		innerPlayer.discardHand(discard);
	}

	@Override
	public void receiveCard(Card c) {
		innerPlayer.receiveCard(c);
	}

	@Override
	public Card removeCardFromHand(String cardName) {
		return innerPlayer.removeCardFromHand(cardName);
	}

	@Override
	public void scheduleTurnAction(ScheduledAction action) {
		innerPlayer.scheduleTurnAction(action);
	}

	@Override
	public void doScheduledAction() {
		innerPlayer.doScheduledAction();
	}

	@Override
	public Map<VictoryPointType, Integer> getFinalVictoryPoints() {
		return innerPlayer.getFinalVictoryPoints();
	}

	@Override
	public List<VictoryPointProvider> getVictoryPoints() {
		return innerPlayer.getVictoryPoints();
	}

	@Override
	public void addPlayRule(Rule pr) {
		innerPlayer.addPlayRule(pr);
	}

	@Override
	public void addBuildRule(Rule pr) {
		innerPlayer.addBuildRule(pr);
	}

	@Override
	public PlayableBuildableResult canPlay(Card c, IPlayer leftNeighbor, IPlayer rightNeighbor) {
		return innerPlayer.canPlay(c, leftNeighbor, rightNeighbor);
	}

	@Override
	public PlayableBuildableResult canBuild(WonderStage stage, IPlayer leftNeighbor, IPlayer rightNeighbor) {
		return innerPlayer.canBuild(stage, leftNeighbor, rightNeighbor);
	}

	@Override
	public List<CardPlayable> getPlayableCards(IPlayer leftNeighbor, IPlayer rightNeighbor, Card[] cardsToEvaluate) {
		return innerPlayer.getPlayableCards(leftNeighbor, rightNeighbor, cardsToEvaluate);
	}

	@Override
	public boolean canPlayByChain(String cardName) {
		return innerPlayer.canPlayByChain(cardName);
	}

	@Override
	public void addResourceProvider(ResourceProvider in, boolean isPublic) {
		innerPlayer.addResourceProvider(in, isPublic);
	}

	@Override
	public List<ResourceSet> getResources(boolean isMe) {
		return innerPlayer.getResources(isMe);
	}

	@Override
	public List<ScienceProvider> getScienceProviders() {
		return innerPlayer.getScienceProviders();
	}

	@Override
	public void addScienceProvider(ScienceProvider in) {
		innerPlayer.addScienceProvider(in);
	}

	@Override
	public void addTradingProvider(TradingProvider t) {
		innerPlayer.addTradingProvider(t);
	}

	@Override
	public void addVPProvider(VictoryPointProvider v) {
		innerPlayer.addVPProvider(v);
	}

	@Override
	public PossibleActions popAction() {
		return innerPlayer.popAction();
	}

	@Override
	public void addNextAction(BaseAction... a) {
		innerPlayer.addNextAction(a);
	}

	@Override
	public PossibleActions getNextAction() {
		return innerPlayer.getNextAction();
	}

	@Override
	public ActionResponse doAction(ActionRequest a, Game game) {
		return innerPlayer.doAction(a, game);
	}

	@Override
	public Object[] getOptions() {
		return innerPlayer.getOptions();
	}

	@Override
	public int getNumberOfBuiltStages() {
		return innerPlayer.getNumberOfBuiltStages();
	}

	@Override
	public WonderStage build(Game game) {
		return innerPlayer.getBoard().build(this, game);
	}

	@Override
	public String[] getBuildState() {
		return innerPlayer.getBuildState();
	}

	@Override
	public WonderStage getNextStage() {
		return innerPlayer.getNextStage();
	}

	@Override
	public void gainCoins(int i) {
		innerPlayer.gainCoins(i);
	}


	private CardList leaderCards = new CardList();
	
	public void keepLeader(Card c) {
		leaderCards.add(c);
	}

	public Card[] getLeaderCards() {
		return leaderCards.getAll();
	}
	
	public Card removeCardFromLeaders(String cardName) {
		return leaderCards.remove(cardName);
	}
	

	@Override
	public void registerEvent(String name, EventAction action) {
		innerPlayer.registerEvent(name, action);
	}

	@Override
	public void eventNotify(String name) {
		innerPlayer.eventNotify(name);
	}

	@Override
	public PlayerArmyFacade getArmyFacade() {
		return innerPlayer.getArmyFacade();
	}

	@Override
	public void setPointCalculations(VictoryPointFacade v) {
		innerPlayer.setPointCalculations(v);
	}

	@Override
	public VictoryPointFacade getPointCalculations() {
		return innerPlayer.getPointCalculations();
	}

	@Override
	public TradingProviderList getTradingProviders() {
		return innerPlayer.getTradingProviders();
	}

	@Override
	public void setHand(CardList cl) {
		innerPlayer.setHand(cl);
	}

	@Override
	public CardList getHand() {
		return innerPlayer.getHand();
	}

	@Override
	public void setBoard(Board b) {
		innerPlayer.setBoard(b);
	}

	@Override
	public void setOptionsFactory(OptionsProvider op) {
		innerPlayer.setOptionsFactory(op);
	}

	@Override
	public OptionsProvider getOptionsFactory() {
		return innerPlayer.getOptionsFactory();
	}

	@Override
	public String getName() {
		return innerPlayer.getName();
	}

	@Override
	public void setCoins(int i) {
		innerPlayer.setCoins(i);
	}

	@Override
	public int getCoins() {
		return innerPlayer.getCoins();
	}

	@Override
	public Board getBoard() {
		return innerPlayer.getBoard();
	}

}

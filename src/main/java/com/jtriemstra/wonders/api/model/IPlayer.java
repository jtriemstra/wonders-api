package com.jtriemstra.wonders.api.model;

import java.util.List;
import java.util.Map;

import com.jtriemstra.wonders.api.dto.request.ActionRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Player.EventAction;
import com.jtriemstra.wonders.api.model.Player.ScheduledAction;
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

import lombok.Getter;

public interface IPlayer {

	boolean equals(Object p1);

	void claimStartingBenefit(Game g);

	String getBoardName();

	String getBoardSide();

	ResourceType getBoardResourceName();

	int getHandSize();

	Card[] getHandCards();

	Card[] getPlayedCards();

	boolean hasPlayedCard(Card c);

	List<Card> getCardsOfTypeFromBoard(Class clazz);

	void putCardOnBoard(Card c);

	void discardHand(DiscardPile discard);

	void receiveCard(Card c);

	Card removeCardFromHand(String cardName);

	void scheduleTurnAction(ScheduledAction action);

	void doScheduledAction();

	Map<VictoryPointType, Integer> getFinalVictoryPoints();

	List<VictoryPointProvider> getVictoryPoints();

	void addPlayRule(Rule pr);

	void addBuildRule(Rule pr);

	PlayableBuildableResult canPlay(Card c, IPlayer leftNeighbor, IPlayer rightNeighbor);

	PlayableBuildableResult canBuild(WonderStage stage, IPlayer leftNeighbor, IPlayer rightNeighbor);

	List<CardPlayable> getPlayableCards(IPlayer leftNeighbor, IPlayer rightNeighbor, Card[] cardsToEvaluate);

	boolean canPlayByChain(String cardName);

	void addResourceProvider(ResourceProvider in, boolean isPublic);

	List<ResourceSet> getResources(boolean isMe);

	List<ScienceProvider> getScienceProviders();

	void addScienceProvider(ScienceProvider in);

	void addTradingProvider(TradingProvider t);

	void addVPProvider(VictoryPointProvider v);

	PossibleActions popAction();

	void addNextAction(BaseAction... a);

	PossibleActions getNextAction();

	ActionResponse doAction(ActionRequest a, Game game);

	Object[] getOptions();

	int getNumberOfBuiltStages();

	WonderStage build(Game game);

	String[] getBuildState();

	WonderStage getNextStage();

	void gainCoins(int i);

	void registerEvent(String name, EventAction action);

	void eventNotify(String name);

	PlayerArmyFacade getArmyFacade();

	void setPointCalculations(VictoryPointFacade v);

	VictoryPointFacade getPointCalculations();

	TradingProviderList getTradingProviders();

	void setHand(CardList cl);

	CardList getHand();

	void setBoard(Board b);

	void setOptionsFactory(OptionsProvider op);

	OptionsProvider getOptionsFactory();

	String getName();

	void setCoins(int i);

	int getCoins();

	Board getBoard();

}
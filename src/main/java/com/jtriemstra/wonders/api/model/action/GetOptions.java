package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.OptionsResponse;
import com.jtriemstra.wonders.api.model.Buildable;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.board.WonderStage;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.CardPlayableComparator;
import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildableResult;

public class GetOptions implements BaseAction {

	@Override
	public String getName() {
		return "options";
	}

	public interface ActionFactory {
		public BaseAction getAction(IPlayer player, Game game);
	}
	
	protected ActionFactory[] getValidActionFactories() {
		return new ActionFactory[] {
				(p, g) -> createPlayAction(p, g),
				(p, g) -> createDiscardAction(p, g),
				(p, g) -> createBuildAction(p, g)
		};
	}
	
	protected OptionsResponse buildResponse(IPlayer player, Game game) {
		List<CardPlayable> playableCards = getPlayableCards(player, game);
		playableCards.sort(new CardPlayableComparator());
				
		List<BaseAction> validActions = populateValidActions(player, game);
		
		player.addNextAction(validActions.toArray(new BaseAction[validActions.size()]));
		
		OptionsResponse r = new OptionsResponse();
		//TODO: (low) if i can get this as "action data" can avoid repeat calls to getPlayableCards()
		r.setCards(playableCards);
		r.setNextActions(player.getNextAction());
		
		if (player.getNextAction().toString().contains("build")) {
			Build b = (Build) player.getNextAction().getByName("build");
			r.setBuildCost(b.getBuildable());
		}
		
		return r;
	}
		
	protected List<BaseAction> populateValidActions(IPlayer player, Game game) {
		List<BaseAction> validActions = new ArrayList<>();

		for (ActionFactory ac : getValidActionFactories()) {
			BaseAction a = ac.getAction(player, game);
			if (a != null) validActions.add(a);
		}

		return validActions;
	}
	
	protected BaseAction createDiscardAction(IPlayer player, Game game) {
		return new Discard(getRemoval(player));
	}
	
	protected BaseAction createBuildAction(IPlayer player, Game game) {
		IPlayer leftNeighbor = game.getLeftOf(player);
		IPlayer rightNeighbor = game.getRightOf(player);
		
		WonderStage stage = player.getNextStage();
		
		PlayableBuildableResult result;
		//TODO: (low) test for this condition
		if (stage == null) {
			result = new PlayableBuildableResult((WonderStage) null, Status.ERR_FINISHED, new ArrayList<>());
		}
		else {
			result = player.canBuild(stage, leftNeighbor, rightNeighbor);
		}
		
		if (result.getStatus() == Status.OK) {
			Buildable buildable = new Buildable(result.getStage(), result.getStatus(), result.getCostOptions());
			Build b = new Build(buildable, getRemoval(player));
			return b;
		}
		
		return null;
	}
	
	protected BaseAction createPlayAction(IPlayer player, Game game) {
		List<CardPlayable> playableCards = getPlayableCards(player, game);
		if (playableCards.stream().filter(c -> c.getStatus() == Status.OK).count() > 0) {
			return new Play(playableCards, getRemoval(player));
		}
		
		return null;
	}
	
	protected CardRemoveStrategy getRemoval(IPlayer player) {
		return cardName -> player.removeCardFromHand(cardName);
	}
	
	protected List<CardPlayable> getPlayableCards(IPlayer player, Game game){
		IPlayer leftNeighbor = game.getLeftOf(player);
		IPlayer rightNeighbor = game.getRightOf(player);
		Card[] cardsToEvaluate = getCardsToEvaluate(player);
		List<CardPlayable> playableCards = player.getPlayableCards(leftNeighbor, rightNeighbor, cardsToEvaluate);
		
		return playableCards;		
	}
	
	protected Card[] getCardsToEvaluate(IPlayer p) {
		return p.getHandCards();
	}
	
	@Override
	public ActionResponse execute(BaseRequest request, IPlayer player, Game game) {
		
		player.popAction();
		
		return buildResponse(player, game);
		
	}

}

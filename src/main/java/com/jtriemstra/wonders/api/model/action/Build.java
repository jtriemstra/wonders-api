package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.ActionRequest;
import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Buildable;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.notifications.NotificationService;

public class Build implements BaseAction {
	private Buildable buildable;
	private CardRemoveStrategy removal;
	
	public Build(Buildable buildable, CardRemoveStrategy removal) {
		this.buildable = buildable;
		this.removal = removal;
	}

	@Override
	public String getName() {
		return "build";
	}
	
	public Buildable getBuildable() {
		return buildable;
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		ActionRequest actionRequest = (ActionRequest) request;

		int selectedPlayableOptionIndex = actionRequest.getTradingInfo() != null ? actionRequest.getTradingInfo().getPlayableIndex() : 0;
		
		player.scheduleTurnAction(notifications -> doBuild(player, game, actionRequest.getCardName(), notifications, selectedPlayableOptionIndex));
		
		player.popAction();
				
		ActionResponse r = new ActionResponse();
		return r;
	}

	public void doBuild(Player p, Game g, String cardName, NotificationService notifications, int playableIndex) {
		Card c = removal.removeFromSource(cardName);
		
		p.build(g);
		
		if (p.getNextStage().getCoinCost() > 0) {
			p.gainCoins(-1 * p.getNextStage().getCoinCost());	
		}
		
		int leftCost = 0, rightCost = 0;
		if (buildable.getCostOptions() == null || buildable.getCostOptions().size() == 0) {
			leftCost = buildable.getLeftCost();
			rightCost = buildable.getRightCost();
		}
		else {
			leftCost = buildable.getCostOptions().get(playableIndex).left;
			rightCost = buildable.getCostOptions().get(playableIndex).right;
		}
		
		if (leftCost > 0) {
			p.gainCoins(-1 * leftCost);
			g.getLeftOf(p).gainCoins(leftCost);
			
			p.eventNotify("trade.neighbor");
		}
		if (rightCost > 0) {
			p.gainCoins(-1 * rightCost);
			g.getRightOf(p).gainCoins(rightCost);
			
			p.eventNotify("trade.neighbor");
		}
		
		notifications.addNotification(p.getName() + " built a stage");
				
	}
}

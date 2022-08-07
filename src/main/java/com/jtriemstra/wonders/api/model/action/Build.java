package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.ActionRequest;
import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Buildable;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.board.WonderStage;
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
	public ActionResponse execute(BaseRequest request, IPlayer player, Game game) {
		ActionRequest actionRequest = (ActionRequest) request;

		int selectedPlayableOptionIndex = actionRequest.getTradingInfo() != null ? actionRequest.getTradingInfo().getPlayableIndex() : 0;
		
		player.scheduleTurnAction(notifications -> doBuild(player, game, actionRequest.getCardName(), notifications, selectedPlayableOptionIndex));
		
		player.popAction();
				
		ActionResponse r = new ActionResponse();
		return r;
	}

	public void doBuild(IPlayer p, Game g, String cardName, NotificationService notifications, int playableIndex) {
		Card c = removal.removeFromSource(cardName);
		
		WonderStage thisStage = p.build(g);
		
		if (thisStage.getCoinCost() > 0) {
			p.gainCoins(-1 * p.getNextStage().getCoinCost());	
		}
		
		if (buildable.getCostOptions() != null && buildable.getCostOptions().size() > 0) {
			int leftCost = 0, rightCost = 0, bankCost = 0;
			
			leftCost = buildable.getCostOptions().get(playableIndex).get("Left");
			rightCost = buildable.getCostOptions().get(playableIndex).get("Right");
			bankCost = buildable.getCostOptions().get(playableIndex).get("Bank");
			
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
			if (bankCost > 0) {
				p.gainCoins(-1 * bankCost);	
			}
		}
		
		
		notifications.addNotification(p.getName() + " built a stage");
				
	}
}

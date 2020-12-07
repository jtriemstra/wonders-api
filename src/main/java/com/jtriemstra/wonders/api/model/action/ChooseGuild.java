package com.jtriemstra.wonders.api.model.action;

import java.util.List;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.request.ChooseGuildRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;

public class ChooseGuild implements BaseAction {
	
	private List<Card> guilds;
	
	public ChooseGuild(List<Card> guilds) {
		this.guilds = guilds;
	}

	@Override
	public String getName() {
		return "chooseGuild";
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		ChooseGuildRequest chooseRequest = (ChooseGuildRequest) request;
		
		player.popAction();
		
		for (Card c : guilds) {
			if (c.getName().equals(chooseRequest.getOptionName())) {
				c.play(player, game);
				break;
			}
		}
		
		return new ActionResponse();
	}

}

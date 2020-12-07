package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.OptionsGuildResponse;
import com.jtriemstra.wonders.api.dto.response.OptionsResponse;
import com.jtriemstra.wonders.api.dto.response.WaitResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.GuildCard;

public class GetOptionsGuildCard implements BaseAction, PostTurnAction {

	@Override
	public String getName() {
		return "options";
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		player.popAction();

		if (!game.isFinalTurn() || !game.isFinalAge()) {
			return new WaitResponse();
		}
		
		Player left = game.getLeftOf(player);
		Player right = game.getRightOf(player);
		List<Card> guilds = new ArrayList<>();
		
		guilds.addAll(left.getCardsOfTypeFromBoard(GuildCard.class));
		guilds.addAll(right.getCardsOfTypeFromBoard(GuildCard.class));
				
		if (guilds.size() > 0) {
			player.addNextAction(new ChooseGuild(guilds));
			
			OptionsGuildResponse r = new OptionsGuildResponse();
			r.setOptions(guilds);
			
			return r;
		}
		else {
			player.addNextAction(new Wait(Wait.For.TURN));
			OptionsResponse r = new OptionsResponse();
			r.setMessage("Neither neighbor has guild cards to copy");
			
			return r;
		}
	}

	@Override
	public double getOrder() {
		//I think this needs to precede GetOptionsScience in case you pick the Scientists Guild
		return 4;
	}

}

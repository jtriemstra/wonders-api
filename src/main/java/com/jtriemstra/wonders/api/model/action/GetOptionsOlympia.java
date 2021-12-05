package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.Card;

public class GetOptionsOlympia extends GetOptions {
	
	private HashSet<Integer> usedInAges;
	
	public GetOptionsOlympia(HashSet<Integer> usedInAges) {
		this.usedInAges = usedInAges;
	}
	
	@Override
	protected ActionFactory[] getValidActionFactories() {
		return new ActionFactory[] {
				(p, g) -> createPlayAction(p, g),
				(p, g) -> createDiscardAction(p, g),
				(p, g) -> createBuildAction(p, g),
				(p, g) -> createPlayFreeAction(p, g)
		};
	}
	
	protected BaseAction createPlayFreeAction(IPlayer player, Game game) {
		int age = game.getFlow().getCurrentAge();
		if (!usedInAges.contains(age)) {
			List<String> allCardNames = new ArrayList<>();
			for (Card c : player.getHand()) {
				if (!player.hasPlayedCard(c)) {
					allCardNames.add(c.getName());
				}
			}
			if (allCardNames.size() > 0) {
				String[] cardNamesArray = new String[allCardNames.size()];
				PlayOlympia pf = new PlayOlympia(allCardNames.toArray(cardNamesArray), usedInAges);
				return pf;
			}
		}
		
		return null;
	}

}

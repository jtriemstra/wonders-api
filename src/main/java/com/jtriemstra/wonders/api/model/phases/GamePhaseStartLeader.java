package com.jtriemstra.wonders.api.model.phases;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.action.GetOptionsLeaders;
import com.jtriemstra.wonders.api.model.deck.leaders.LeaderCardFactory;
import com.jtriemstra.wonders.api.model.deck.leaders.LeaderDeck;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GamePhaseStartLeader implements GamePhaseStart {
	
	private LeaderDeck deck = new LeaderDeck(new LeaderCardFactory());

	@Override
	public void start(Game g) {
		log.info("GamePhaseStartLeader");
		
		//TODO: unify approach with Game.dealCards
		for (int i=0; i<4; i++) {
			g.doForEachPlayer(p -> {
				p.receiveCard(deck.draw());
			});
		}
		
		g.setUnusedLeaders(deck);
		
		//TODO: possibly unify with startTurn
		g.doForEachPlayer(p -> {
			log.info("adding GetOptionsLeaders to " + p.getName());
			p.addNextAction(new GetOptionsLeaders());
		});		
	}

}

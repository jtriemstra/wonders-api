package com.jtriemstra.wonders.api.model.phases;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.action.GetOptionsLeaders;
import com.jtriemstra.wonders.api.model.deck.leaders.LeaderDeck;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class GamePhaseStartLeader implements GamePhaseStart {
	
	private LeaderDeck deck;

	@Override
	public void start(Game g) {
		log.info("GamePhaseStartLeader");
		
		for (int i=0; i<4; i++) {
			g.doForEachPlayer(p -> {
				p.receiveCard(deck.draw());
			});
		}
				
		g.doForEachPlayer(p -> {
			log.info("adding GetOptionsLeaders to " + p.getName());
			p.addNextAction(new GetOptionsLeaders());
		});		
	}

}

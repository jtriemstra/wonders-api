package com.jtriemstra.wonders.api.dto.response;

import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

import lombok.Data;

@Data
public class NeighborInfo {
	private ResourceType boardResource;
	private String[] cardsOnBoard;
	private String name;
	private int stagesBuilt;
	private String boardName;
	private String boardSide;
	
	public NeighborInfo(Player p) {
		cardsOnBoard = getNeighborCardNames(p);
		boardResource = p.getBoardResourceName();
		name = p.getName();
		stagesBuilt = p.getNumberOfBuiltStages();
		boardName = p.getBoardName();
		boardSide = p.getBoardSide();
	}
	
	private String[] getNeighborCardNames(Player p) {
		Card[] cards = p.getPlayedCards();
		String[] cardNames = new String[cards.length];
		for (int i=0; i<cards.length; i++) {
			cardNames[i] = cards[i].getName();
		}
		
		return cardNames;		
	}
}

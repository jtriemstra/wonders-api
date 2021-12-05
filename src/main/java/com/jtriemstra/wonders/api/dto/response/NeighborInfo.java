package com.jtriemstra.wonders.api.dto.response;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
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
	
	public NeighborInfo(IPlayer p) {
		cardsOnBoard = getNeighborCardNames(p);
		boardResource = p.getBoardResourceName();
		name = p.getName();
		stagesBuilt = p.getNumberOfBuiltStages();
		boardName = p.getBoardName();
		boardSide = p.getBoardSide();
	}
	
	private String[] getNeighborCardNames(IPlayer p) {		
		return Arrays.stream(p.getPlayedCards()).sorted((c1, c2) -> c1.getType().compareTo(c2.getType())).map(c -> c.getName()).toArray(String[]::new);		
	}
}

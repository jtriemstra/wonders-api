package com.jtriemstra.wonders.api.dto.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.jtriemstra.wonders.api.model.action.PossibleActions;
import com.jtriemstra.wonders.api.model.card.Card;

import lombok.Data;

@Data
public class BaseResponse {
	protected PossibleActions nextActions;
	private String message;
	private int age;
	private String boardName;
	private String boardSide;
	private String[] boardHelp;
	private Card[] cardsOnBoard;
	private int coins;
	private NeighborInfo leftNeighbor;
	private NeighborInfo rightNeighbor;
	private String[] buildState;
	private int[] discards;
	private int allDefeats;
	private Map<Integer, List<Integer>> allVictories;
	
	// TODO: this gets used by the front-end to differentiate a refresh of a game in progress from a new game - probably a better way to do that
	private boolean playerFound;
	
	public String getNextActions() {
		if (nextActions != null) {
			return nextActions.toString();
		}
		return "";
	}
}

package com.jtriemstra.wonders.api.dto.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.jtriemstra.wonders.api.model.Buildable;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;

import lombok.Data;

@Data
public class RefreshResponse extends BaseResponse {
	private List<CardPlayable> cards;
	private Card[] cardsOnBoard;
	private int coins;
	private String[] buildState;
	private String boardSide;
	private String[] boardHelp;
	private boolean playerFound;
	private Object[] options;
	private String boardName;
	private NeighborInfo leftNeighbor;
	private NeighborInfo rightNeighbor;
	private int allDefeats;
	private Map<Integer, List<Integer>> allVictories;
	private Buildable buildCost;
}

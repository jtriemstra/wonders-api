package com.jtriemstra.wonders.api.dto.response;

import java.util.List;

import com.jtriemstra.wonders.api.model.card.Card;

import lombok.Data;

@Data
public class ActionResponse extends BaseResponse {
	private Card[] cardsOnBoard;
	private int coins;
	private NeighborInfo leftNeighbor;
	private NeighborInfo rightNeighbor;
	private String[] buildState;
	private int[] discards;
}

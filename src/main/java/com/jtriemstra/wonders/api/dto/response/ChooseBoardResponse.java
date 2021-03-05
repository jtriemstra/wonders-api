package com.jtriemstra.wonders.api.dto.response;

import lombok.Data;

@Data
public class ChooseBoardResponse extends ActionResponse {
	private String boardName;
	private String boardSide;
	
	public ChooseBoardResponse(ActionResponse r) {
		this.setAge(r.getAge());
		this.setCardsOnBoard(r.getCardsOnBoard());
		this.setCoins(r.getCoins());
		this.setLeftNeighbor(r.getLeftNeighbor());
		this.setRightNeighbor(r.getRightNeighbor());
		this.setMessage(r.getMessage());
		this.setNextActions(r.nextActions);
	}
}

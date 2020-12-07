package com.jtriemstra.wonders.api.dto.request;

import lombok.Data;

@Data
public class UpdateGameRequest extends ActionRequest {
	private int numberOfPlayers;

	@Override
	public String getActionName() {
		return "updateGame";
	}
}

package com.jtriemstra.wonders.api.dto.request;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;

import lombok.Data;

@Data
public class UpdateGameRequest extends ActionRequest {
	private int numberOfPlayers;
	private boolean chooseBoard;
	private Game.BoardSide sideOptions;
	private List<Game.Expansions> expansions;

	@Override
	public String getActionName() {
		return "updateGame";
	}
}

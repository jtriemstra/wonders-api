package com.jtriemstra.wonders.api.dto.request;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.board.BoardSide;

import lombok.Data;

@Data
public class UpdateGameRequest extends ActionRequest {
	private int numberOfPlayers;
	private boolean chooseBoard;
	private BoardSide sideOptions;
	private List<Game.Expansions> expansions;
	private boolean leaders;

	@Override
	public String getActionName() {
		return "updateGame";
	}
}

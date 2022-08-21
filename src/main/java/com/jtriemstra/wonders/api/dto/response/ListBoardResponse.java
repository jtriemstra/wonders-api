package com.jtriemstra.wonders.api.dto.response;

import java.util.Map;

import com.jtriemstra.wonders.api.model.board.BoardSide;

import lombok.Data;

@Data
public class ListBoardResponse extends ActionResponse {
	private Map<String, Boolean> boards;
	private BoardSide sideAllowed;
}

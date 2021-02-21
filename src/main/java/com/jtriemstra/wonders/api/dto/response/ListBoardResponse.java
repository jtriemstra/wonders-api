package com.jtriemstra.wonders.api.dto.response;

import java.util.Map;

import lombok.Data;

@Data
public class ListBoardResponse extends ActionResponse {
	private Map<Integer, Boolean> boards;
}

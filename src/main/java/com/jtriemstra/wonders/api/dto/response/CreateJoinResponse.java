package com.jtriemstra.wonders.api.dto.response;

import lombok.Data;

@Data
public class CreateJoinResponse extends ActionResponse {
	private String boardName;
	private String boardSide;
	private String[] boardHelp;
}

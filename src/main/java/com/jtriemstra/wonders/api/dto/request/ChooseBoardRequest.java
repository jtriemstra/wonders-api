package com.jtriemstra.wonders.api.dto.request;

import lombok.Data;

@Data
public class ChooseBoardRequest extends ActionRequest {

	private boolean skip;
	private int newBoardId;
	
	@Override
	public String getActionName() {
		return "chooseBoard";
	}
}

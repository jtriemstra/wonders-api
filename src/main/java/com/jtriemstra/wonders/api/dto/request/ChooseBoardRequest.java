package com.jtriemstra.wonders.api.dto.request;

import lombok.Data;

@Data
public class ChooseBoardRequest extends ActionRequest {

	public final String FINISH = "finish";
			
	private boolean skip;
	private String boardName;
	private String boardSide;
	
	@Override
	public String getActionName() {
		return "chooseBoard";
	}
}

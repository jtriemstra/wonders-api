package com.jtriemstra.wonders.api.dto.request;

public class GetEndOfGameRequest extends ActionRequest {
	@Override
	public String getActionName() {
		return "finishGame";
	}
}

package com.jtriemstra.wonders.api.dto.request;

public class CardNameRequest extends ActionRequest {
	//TODO: don't remember what this action name was used for, but would like to be more generic
	@Override
	public String getActionName() {
		return "keepLeader";
	}
}

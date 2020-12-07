package com.jtriemstra.wonders.api.dto.request;

public class DiscardRequest extends ActionRequest {
	@Override
	public String getActionName() {
		return "discard";
	}
}

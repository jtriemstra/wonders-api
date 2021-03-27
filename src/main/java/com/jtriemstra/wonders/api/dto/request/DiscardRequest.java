package com.jtriemstra.wonders.api.dto.request;

//TODO: may be able to refactor into card name request?
public class DiscardRequest extends ActionRequest {
	@Override
	public String getActionName() {
		return "discard";
	}
}

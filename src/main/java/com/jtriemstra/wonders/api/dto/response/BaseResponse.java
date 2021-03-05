package com.jtriemstra.wonders.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.jtriemstra.wonders.api.model.action.PossibleActions;

import lombok.Data;

@Data
public class BaseResponse {
	protected PossibleActions nextActions;
	private String message;
	private int age;
		
	public String getNextActions() {
		if (nextActions != null) {
			return nextActions.toString();
		}
		return "";
	}
}

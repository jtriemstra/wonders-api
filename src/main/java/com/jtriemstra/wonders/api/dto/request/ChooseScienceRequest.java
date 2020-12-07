package com.jtriemstra.wonders.api.dto.request;

import com.jtriemstra.wonders.api.model.card.ScienceType;

import lombok.Data;

@Data
public class ChooseScienceRequest extends ActionRequest {
	private ScienceType choice;
	
	@Override
	public String getActionName() {
		return "chooseScience";
	}
}

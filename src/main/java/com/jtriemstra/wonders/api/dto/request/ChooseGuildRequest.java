package com.jtriemstra.wonders.api.dto.request;

import lombok.Data;

@Data
public class ChooseGuildRequest extends ActionRequest {
	private String optionName;

	@Override
	public String getActionName() {
		return "chooseGuild";
	}
}

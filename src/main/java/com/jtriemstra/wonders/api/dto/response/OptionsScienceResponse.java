package com.jtriemstra.wonders.api.dto.response;

import com.jtriemstra.wonders.api.model.card.ScienceType;

import lombok.Data;

@Data
public class OptionsScienceResponse extends ActionResponse {
	private ScienceType[] options;
}

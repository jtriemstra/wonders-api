package com.jtriemstra.wonders.api.model.card;

import lombok.Data;

@Data
public class Science {
	private ScienceType[] scienceOptions;

	public Science(ScienceType...scienceTypes) {
		scienceOptions = scienceTypes;
	}
}

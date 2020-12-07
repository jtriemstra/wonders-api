package com.jtriemstra.wonders.api.model.card;

import lombok.Data;

@Data
public class Science {
	private ScienceType[] scienceOptions;
	//TODO: pretty sure this can be simplified down to a single ScienceType, since multiple ones are handled with a post-game action
	public Science(ScienceType...scienceTypes) {
		scienceOptions = scienceTypes;
	}
}

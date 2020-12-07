package com.jtriemstra.wonders.api.dto.response;

import java.util.Map;

import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

import lombok.Data;

@Data
public class GetEndOfGameResponse extends ActionResponse {
	private int victories;
	private int defeats;	
	private Map<VictoryPointType, Integer> allVictoryPoints;
}

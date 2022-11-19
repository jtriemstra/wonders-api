package com.jtriemstra.wonders.api.dto.response;

import java.util.List;
import java.util.Map;

import com.jtriemstra.wonders.api.model.MilitaryResult;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

import lombok.Data;

@Data
public class GetEndOfGameResponse extends ActionResponse {
	private List<MilitaryResult> victories;
	private List<MilitaryResult> defeats;	
	private Map<VictoryPointType, Integer> allVictoryPoints;
}

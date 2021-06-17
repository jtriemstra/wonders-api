package com.jtriemstra.wonders.api.dto.response;

import java.util.List;

import com.jtriemstra.wonders.api.model.Buildable;
import com.jtriemstra.wonders.api.model.card.CardPlayable;

import lombok.Data;

@Data
public class OptionsResponse extends ActionResponse {
	private List<CardPlayable> cards;
	private Buildable buildCost;
}

package com.jtriemstra.wonders.api.dto.response;

import java.util.List;

import com.jtriemstra.wonders.api.model.card.Card;

import lombok.Data;

@Data
public class OptionsGuildResponse extends ActionResponse {
	private List<Card> options;
}

package com.jtriemstra.wonders.api.dto.response;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameList;

import lombok.Data;

@Data
public class ListGameResponse {
	private GameList games;
}

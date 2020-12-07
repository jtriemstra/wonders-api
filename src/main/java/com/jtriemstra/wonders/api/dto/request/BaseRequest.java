package com.jtriemstra.wonders.api.dto.request;

import lombok.Data;

@Data
public class BaseRequest {
	private String gameName;
	//TODO: distinguish between player ID and name
	private String playerId;
}

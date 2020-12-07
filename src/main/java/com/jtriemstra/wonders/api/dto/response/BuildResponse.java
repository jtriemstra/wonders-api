package com.jtriemstra.wonders.api.dto.response;

import lombok.Data;

@Data
public class BuildResponse extends ActionResponse {
	private int[] buildState;
}

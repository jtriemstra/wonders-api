package com.jtriemstra.wonders.api.dto.response;

import com.jtriemstra.wonders.api.model.action.Wait;

import lombok.Data;

@Data
public class WaitResponse extends ActionResponse {
	private Wait.For waitFor;
}

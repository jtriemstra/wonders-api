package com.jtriemstra.wonders.api.dto.request;

import lombok.Data;

@Data
public abstract class ActionRequest extends BaseRequest {
	private String cardName;
	private TradingInfo tradingInfo;
	
	public abstract String getActionName();
}

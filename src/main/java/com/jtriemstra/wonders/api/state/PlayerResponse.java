package com.jtriemstra.wonders.api.state;

import java.time.LocalTime;

import lombok.Data;

@Data
public class PlayerResponse {
	private String name;
	private String response;
	private LocalTime time;
}

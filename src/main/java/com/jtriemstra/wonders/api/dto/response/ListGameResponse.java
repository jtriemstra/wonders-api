package com.jtriemstra.wonders.api.dto.response;

import java.util.Set;

import lombok.Data;

@Data
public class ListGameResponse {
	private Set<String> games;
}

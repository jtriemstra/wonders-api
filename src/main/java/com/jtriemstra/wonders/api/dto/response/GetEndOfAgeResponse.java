package com.jtriemstra.wonders.api.dto.response;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class GetEndOfAgeResponse extends WaitResponse {
	private int victories;
	private int defeats;
	private int age;
}

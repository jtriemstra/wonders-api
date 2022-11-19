package com.jtriemstra.wonders.api.dto.response;

import java.util.List;

import com.jtriemstra.wonders.api.model.MilitaryResult;

import lombok.Data;

@Data
public class GetEndOfAgeResponse extends WaitResponse {
	private List<MilitaryResult> victories;
	private List<MilitaryResult> defeats;
	private int age;
}

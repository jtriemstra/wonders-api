package com.jtriemstra.wonders.api.model.action.provider;

import java.util.HashSet;

import com.jtriemstra.wonders.api.model.action.GetOptions;
import com.jtriemstra.wonders.api.model.action.GetOptionsOlympia;

public class OlympiaOptionsProvider extends OptionsProvider {

	private HashSet<Integer> usedInAges = new HashSet<>();
	
	@Override
	public GetOptions createGetOptions() {
		return new GetOptionsOlympia(usedInAges);
	}

}

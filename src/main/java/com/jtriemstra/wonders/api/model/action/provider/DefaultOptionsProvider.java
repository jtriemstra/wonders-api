package com.jtriemstra.wonders.api.model.action.provider;

import com.jtriemstra.wonders.api.model.action.GetOptions;

public class DefaultOptionsProvider extends OptionsProvider {

	@Override
	public GetOptions createGetOptions() {
		return new GetOptions();
	}

}

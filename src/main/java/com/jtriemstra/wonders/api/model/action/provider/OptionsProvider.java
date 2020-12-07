package com.jtriemstra.wonders.api.model.action.provider;

import com.jtriemstra.wonders.api.model.action.GetOptions;

public abstract class OptionsProvider {
	public abstract GetOptions createGetOptions();
}

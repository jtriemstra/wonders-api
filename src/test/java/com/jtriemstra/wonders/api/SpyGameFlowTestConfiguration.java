package com.jtriemstra.wonders.api;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import com.jtriemstra.wonders.api.model.GeneralBeanFactory.GameFlowFactory;
import com.jtriemstra.wonders.api.model.phases.GameFlow;

public class SpyGameFlowTestConfiguration {
	@Bean
	@Scope("prototype")
	@Primary
	public GameFlowFactory spyGameFlowFactory() {
		return phaseFactory -> {
			GameFlow spyFlow = Mockito.spy(new GameFlow(phaseFactory));
					
			return spyFlow;
		};
	}
}

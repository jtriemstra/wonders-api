package com.jtriemstra.wonders.api;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import com.jtriemstra.wonders.api.model.GeneralBeanFactory.GameFlowFactory;
import com.jtriemstra.wonders.api.model.GeneralBeanFactory.PostTurnActionFactoryDefaultFactory;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.phases.GameFlow;
import com.jtriemstra.wonders.api.model.phases.PostTurnActionFactoryDefault;

@TestConfiguration
public class FinalTurnTestConfiguration {
		
	@Bean
	@Scope("prototype")
	@Primary
	public GameFlowFactory spyGameFlowFactory() {
		return phaseFactory -> {
			GameFlow spyFlow = Mockito.spy(new GameFlow(phaseFactory));
			Mockito.doReturn(true).when(spyFlow).isFinalTurn();
			Mockito.doReturn(1).when(spyFlow).getCurrentAge();
			Mockito.when(spyFlow.isPhaseStarted()).thenReturn(true);		
			return spyFlow;
		};
	}

}

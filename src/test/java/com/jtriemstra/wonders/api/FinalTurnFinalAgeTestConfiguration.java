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
public class FinalTurnFinalAgeTestConfiguration {
		
	@Bean
	@Scope("prototype")
	@Primary
	public GameFlowFactory spyGameFlowFactory() {
		return phaseFactory -> {
			GameFlow spyFlow = Mockito.spy(new GameFlow(phaseFactory));
			Mockito.doReturn(true).when(spyFlow).isFinalTurn();
			Mockito.doReturn(3).when(spyFlow).getCurrentAge();
			Mockito.doReturn(true).when(spyFlow).isFinalAge();
			Mockito.when(spyFlow.isPhaseStarted()).thenReturn(true);		
			return spyFlow;
		};
	}
	

	@Bean
	@Scope("prototype")
	@Primary
	public PostTurnActionFactoryDefaultFactory spyPtaFactory() {
		return discard -> {
			PostTurnActionFactoryDefault realFactory = new PostTurnActionFactoryDefault(discard);
			PostTurnActionFactoryDefault spyFactory = Mockito.spy(realFactory);
			Mockito.doReturn(new PostTurnActions()).when(spyFactory).getPostTurnActions();
			return spyFactory;
		};
	}

}

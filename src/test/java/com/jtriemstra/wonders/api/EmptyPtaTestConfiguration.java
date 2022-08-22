package com.jtriemstra.wonders.api;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import com.jtriemstra.wonders.api.model.GeneralBeanFactory.PostTurnActionFactoryDefaultFactory;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.phases.PostTurnActionFactoryDefault;
import com.jtriemstra.wonders.api.state.MemoryStateService;

public class EmptyPtaTestConfiguration {

	@Bean
	@Scope("prototype")
	@Primary
	public PostTurnActionFactoryDefaultFactory spyPtaFactory(@Autowired MemoryStateService stateService) {
		return discard -> {
			PostTurnActionFactoryDefault realFactory = new PostTurnActionFactoryDefault(discard, stateService);
			PostTurnActionFactoryDefault spyFactory = Mockito.spy(realFactory);
			Mockito.doReturn(new PostTurnActions(stateService)).when(spyFactory).getPostTurnActions();
			return spyFactory;
		};
	}
}

package com.jtriemstra.wonders.api.model.action;

import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.model.DiscardPile;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.GeneralBeanFactory.GameFlowFactory;
import com.jtriemstra.wonders.api.model.card.Palace;
import com.jtriemstra.wonders.api.model.phases.GameFlow;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GetOptionsOlympiaAge2Tests extends TestBase {

	public HashSet<Integer> firstAgeUsed(){
		HashSet<Integer> h = new HashSet<>();
		h.add(1);
		return h;
	}
	
	@Test
	public void when_used_in_one_age_can_use_later() {
		setupTest();
		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		testPlayer.discardHand(Mockito.mock(DiscardPile.class));
		testPlayer.receiveCard(new Palace(3,3));
		
		testPlayer.addNextAction(new GetOptionsOlympia(firstAgeUsed()));
				
		OptionsRequest r = new OptionsRequest();
		
		BaseResponse r1 = testPlayer.doAction(r, gameWithThreePlayers);
		
		Assertions.assertEquals("discard;playFree", testPlayer.getNextAction().toString());		
	}
	
	@TestConfiguration
	public static class TestConfig {
		
		@Bean
		@Scope("prototype")
		@Primary
		public GameFlowFactory spyGameFlowFactory() {
			return phaseFactory -> {
				GameFlow spyFlow = Mockito.spy(new GameFlow(phaseFactory));
				Mockito.doReturn(2).when(spyFlow).getCurrentAge();
				Mockito.when(spyFlow.isPhaseStarted()).thenReturn(true);		
				return spyFlow;
			};
		}
	}
}

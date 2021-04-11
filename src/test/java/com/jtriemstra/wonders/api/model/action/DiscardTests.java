package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.dto.request.DiscardRequest;
import com.jtriemstra.wonders.api.model.Ages;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.card.StonePit;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
public class DiscardTests extends TestBase {
	
	@Test
	public void when_discarding_then_get_coins_and_lose_card() {
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(new StonePit(3,1), g);
		
		p1.addNextAction(new Discard());
		
		Assertions.assertEquals(0, g.getDiscardCards().length);
		Assertions.assertEquals(1, p1.getHandSize());
		Assertions.assertEquals(3, p1.getCoins());
		
		DiscardRequest dr = new DiscardRequest();
		dr.setCardName("Stone Pit");
		p1.doAction(dr, g);
		
		Assertions.assertEquals(1, g.getDiscardCards().length);
		Assertions.assertEquals(0, p1.getHandSize());
		Assertions.assertEquals(3, p1.getCoins());
		Mockito.verify(p1, Mockito.times(1)).setCoinProvider(Mockito.any());
	}	
}

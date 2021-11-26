package com.jtriemstra.wonders.api.model.action;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.dto.request.ChooseGuildRequest;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.MagistratesGuild;
import com.jtriemstra.wonders.api.model.card.Palace;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

public class ChooseGuildUnitTests {
	
	public ChooseGuildRequest getRequest(Card c) {
		ChooseGuildRequest r = new ChooseGuildRequest();
		r.setOptionName(c.getName());
		return r;
	}
		
	@Test
	public void when_choosing_magistrates_guild_then_get_points_from_neighbor() {
		Card guildCard = new MagistratesGuild(3,3);
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerCardsOnBoard("test2", new Palace(3,3))
				.withPlayerNextAction("test1", new ChooseGuild(Arrays.asList(guildCard)))
				.build();
		
		ChooseGuildRequest r = getRequest(guildCard);
		testGame.getPlayer("test1").doAction(r, testGame);
		
		Assertions.assertEquals(1, testGame.getPlayer("test1").getFinalVictoryPoints().get(VictoryPointType.GUILD));
	}	
}

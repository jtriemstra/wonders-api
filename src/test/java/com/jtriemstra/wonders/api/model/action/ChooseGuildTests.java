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
import com.jtriemstra.wonders.api.dto.request.ChooseGuildRequest;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.MagistratesGuild;
import com.jtriemstra.wonders.api.model.card.Palace;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ChooseGuildTests extends TestBase {
	
	public void addChooseGuildToPlayer(Player p, Card c) {
		List<Card> guilds = Arrays.asList(c);
		
		ChooseGuild cg = new ChooseGuild(guilds);
		p.addNextAction(cg);
	}
	
	public ChooseGuildRequest getRequest(Card c) {
		ChooseGuildRequest r = new ChooseGuildRequest();
		r.setOptionName(c.getName());
		return r;
	}
		
	@Test
	public void when_choosing_magistrates_guild_then_vp_provider_added() {
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		
		Card c = new MagistratesGuild(3,3);
		addChooseGuildToPlayer(p1, c);
		
		Assertions.assertFalse(g.isFinalTurn());
		
		ChooseGuildRequest r = getRequest(c);
		p1.doAction(r, g);
		
		Mockito.verify(p1, Mockito.times(1)).addVPProvider(Mockito.any(CardVPProvider.class));
	}
	
	@Test
	public void when_choosing_magistrates_guild_then_get_points_from_neighbor() {
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		Player p2 = g.getPlayer("test2");
		fakePreviousCard(p2, new Palace(3,3), g);

		Card c = new MagistratesGuild(3,3);
		addChooseGuildToPlayer(p1, c);
		
		ChooseGuildRequest r = getRequest(c);
		p1.doAction(r, g);
		
		Assertions.assertEquals(1, p1.getFinalVictoryPoints().get(VictoryPointType.GUILD));
	}	
}

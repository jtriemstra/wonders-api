package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.dto.request.ChooseGuildRequest;
import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.model.CardList;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.MagistratesGuild;
import com.jtriemstra.wonders.api.model.card.Palace;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class ChooseGuildTests {
	
	@Autowired
	GameFactory realGameFactory;
	
	@Autowired
	@Qualifier("createMockPlayerFactory")
	PlayerFactory mockPlayerFactory;
	
	@Autowired
	@Qualifier("createNamedBoardFactory")
	BoardFactory boardFactory;	
	
	@Test
	public void when_choosing_magistrates_guild_then_vp_provider_added() {
		Game g = realGameFactory.createGame("test1", boardFactory);
		Player p1 = mockPlayerFactory.createPlayer("test1");
		g.addPlayer(p1);
		List<Card> guilds = Arrays.asList(new MagistratesGuild(3,3));
		
		ChooseGuild cg = new ChooseGuild(guilds);
		p1.addNextAction(cg);
		
		Assertions.assertFalse(g.isFinalTurn());
		
		ChooseGuildRequest r = new ChooseGuildRequest();
		r.setOptionName("Magistrates Guild");
		BaseResponse r1 = p1.doAction(r, g);
		
		Mockito.verify(p1, Mockito.times(1)).addVPProvider(Mockito.any(CardVPProvider.class));
	}
	
	@Test
	public void when_choosing_magistrates_guild_then_get_points_from_neighbor() {
		Game g = realGameFactory.createGame("test1", boardFactory);
		Player p1 = mockPlayerFactory.createPlayer("test1");
		Player p2 = mockPlayerFactory.createPlayer("test2");
		Player p3 = mockPlayerFactory.createPlayer("test3");
		g.addPlayer(p1);
		g.addPlayer(p2);
		g.addPlayer(p3);
		
		List<Card> guilds = Arrays.asList(new MagistratesGuild(3,3));
		
		ChooseGuild cg = new ChooseGuild(guilds);
		p1.addNextAction(cg);
		
		ChooseGuildRequest r = new ChooseGuildRequest();
		r.setOptionName("Magistrates Guild");
		BaseResponse r1 = p1.doAction(r, g);
		
		Assertions.assertEquals(1, p1.getFinalVictoryPoints().get(VictoryPointType.GUILD));
	}
	
	@TestConfiguration
	static class TestConfig {
		
		@Autowired
		PlayerFactory realPlayerFactory;
		
		@Bean
		@Profile("test")
		@Primary
		public PlayerFactory createMockPlayerFactory() {
			return (name) -> createMockPlayer(name);
		}
		
		@Bean
		@Scope("prototype")
		public Player createMockPlayer(String name) {
			CardList cl = new CardList();
			if (name.equals("test2")) {
				cl.add(new Palace(3,3));
			}
			Player p = new Player(name, new ActionList(), new ArrayList<>(), new ArrayList<>(), cl);
			
			return Mockito.spy(p);
		}
		
	}
}

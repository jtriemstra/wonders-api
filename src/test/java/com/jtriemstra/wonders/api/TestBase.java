package com.jtriemstra.wonders.api;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.jtriemstra.wonders.api.dto.request.PlayRequest;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.action.Play;
import com.jtriemstra.wonders.api.model.action.WaitTurn;
import com.jtriemstra.wonders.api.model.board.BoardSide;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.phases.AgePhase;
import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildableResult;
import com.jtriemstra.wonders.api.model.points.VictoryPointFacadeLeaders;

public class TestBase {
	
	@Autowired @Qualifier("gameWithThreePlayers")
	protected Game gameWithThreePlayers;
	
	protected Player testPlayer;
	
	protected void setupTest(Card... testCards) {
		testPlayer = gameWithThreePlayers.getPlayer("test1");
		for (Card testCard : testCards) {
			fakePlayingCard(testPlayer, testCard, gameWithThreePlayers);
		}
		
		gameWithThreePlayers.startNextPhase();
		gameWithThreePlayers.startNextPhase();
	}

	protected void fakePlayingCard(Player testPlayer, Card c, Game g) {
		testPlayer.receiveCard(c);
		testPlayer.scheduleCardToPlay(c);
		testPlayer.playScheduledCard(g);
	}
	
	protected void setUpTestByActionIgnoringCosts(Card testCard, Card... previousCards) {
		setupTest(previousCards);
		
		testPlayer.receiveCard(testCard);
		CardPlayable cp = new CardPlayable(testCard, Status.OK, 0, 0, 0);
		List<CardPlayable> cps = new ArrayList<>();
		cps.add(cp);
		
		Play play = new Play(cps);
		testPlayer.addNextAction(new WaitTurn());
		testPlayer.addNextAction(play);
		
	}
	
	protected void setUpTestByActionWithCosts(Card testCard, Card... previousCards) {
		testPlayer = gameWithThreePlayers.getPlayer("test1");
		
		for (Card prevCard : previousCards) {
			fakePlayingCard(testPlayer, prevCard, gameWithThreePlayers);
		}
		
		gameWithThreePlayers.startNextPhase();
		gameWithThreePlayers.startNextPhase();
		
		testPlayer.receiveCard(testCard);
		PlayableBuildableResult result = testPlayer.canPlay(testCard, gameWithThreePlayers.getLeftOf(testPlayer), gameWithThreePlayers.getRightOf(testPlayer));
		CardPlayable cp = new CardPlayable(result.getCard(), result.getStatus(), result.getCost() + result.getLeftCost() + result.getRightCost(), result.getLeftCost(), result.getRightCost(), result.getCost());
		List<CardPlayable> cps = new ArrayList<>();
		cps.add(cp);
		
		Play play = new Play(cps);
		testPlayer.addNextAction(new WaitTurn());
		testPlayer.addNextAction(play);
		
	}
	
	protected void fakePlayingCardWithAction(Card c) {
		PlayRequest r = new PlayRequest();
		r.setCardName(c.getName());
		
		testPlayer.doAction(r, gameWithThreePlayers);

		//TODO: this is pretty ugly
		((AgePhase) gameWithThreePlayers.getFlow().getCurrentPhase()).handlePostTurnActions(gameWithThreePlayers);
	}
	
	protected void fakeVictoryTokens(Player p, int age) {
		int amount = 2*age - 1;
		p.addVictory(age, amount);
	}
	
	protected void fakeBuildingStage(Player p, Game g) {
		p.build(g);
	}
	
	@TestConfiguration
	public static class TestConfig {

		@Autowired
		private GameFactory gameFactory;

		@Autowired
		protected PlayerFactory playerFactory;
		
		@Bean
		@Primary
		public Game gameWithThreePlayers() {
			Game g = gameFactory.createGame("test2", 3, false, BoardSide.A_OR_B, false);
			Player p = Mockito.spy(playerFactory.createPlayer("test1"));
			g.addPlayer(p);
			p.setPointCalculations(new VictoryPointFacadeLeaders());
			p.addNextAction(new WaitTurn());
			
			Player p2 = Mockito.spy(playerFactory.createPlayer("test2"));
			Player p3 = Mockito.spy(playerFactory.createPlayer("test3"));
			
			g.addPlayer(p2);
			g.addPlayer(p3);
			
			return g;
		}
	}
}

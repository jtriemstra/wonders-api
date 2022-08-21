package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.dto.response.OptionsResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.board.WonderStage;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.ClayPit;
import com.jtriemstra.wonders.api.model.card.ClayPool;
import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildableResult;

public class GetOptionsUnitTests {
	
	@Test
	public void when_one_playable_card_then_two_options() {
		
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerNextAction("test1", new GetOptions())
				.withPlayerPlayableCards("test1", new CardPlayable[] {
						new CardPlayable(new ClayPit(3,1), Status.OK, new ArrayList<>(), 0)
				})
				.buildGame();
		
		OptionsRequest r = new OptionsRequest();
		OptionsResponse r1 = (OptionsResponse) Player.doAction(r, testGame.getPlayer("test1"), testGame);
		
		Assertions.assertEquals(1, r1.getCards().size());
		Assertions.assertEquals("play;discard", r1.getNextActions());
	}

	@Test
	public void when_two_playable_card_then_two_options() {
		
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerNextAction("test1", new GetOptions())
				.withPlayerPlayableCards("test1", new CardPlayable[] {
						new CardPlayable(new ClayPit(3,1), Status.OK, new ArrayList<>(), 0),
						new CardPlayable(new ClayPool(3,1), Status.OK, new ArrayList<>(), 0)
				})
				.buildGame();
		
		OptionsRequest r = new OptionsRequest();
		OptionsResponse r1 = (OptionsResponse) Player.doAction(r, testGame.getPlayer("test1"), testGame);
		
		Assertions.assertEquals(2, r1.getCards().size());
		Assertions.assertEquals("play;discard", r1.getNextActions());
	}

	@Test
	public void when_no_playable_card_then_one_option() {
		
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerNextAction("test1", new GetOptions())
				.withPlayerPlayableCards("test1", new CardPlayable[] {})
				.buildGame();
		
		OptionsRequest r = new OptionsRequest();
		OptionsResponse r1 = (OptionsResponse) Player.doAction(r, testGame.getPlayer("test1"), testGame);
		
		Assertions.assertEquals(0, r1.getCards().size());
		Assertions.assertEquals("discard", r1.getNextActions());
	}

	@Test
	public void when_playable_card_and_buildable_then_three_options() {
		WonderStage y = null;
		PlayableBuildableResult x = new PlayableBuildableResult(y, CardPlayable.Status.OK, new ArrayList<>());
		
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerNextAction("test1", new GetOptions())
				.withPlayerPlayableCards("test1", new CardPlayable[] {
						new CardPlayable(new ClayPit(3,1), Status.OK, new ArrayList<>(), 0)
				})
				.withPlayerCanBuild("test1", x)
				.buildGame();
		
		OptionsRequest r = new OptionsRequest();
		OptionsResponse r1 = (OptionsResponse) Player.doAction(r, testGame.getPlayer("test1"), testGame);
		
		Assertions.assertEquals(1, r1.getCards().size());
		Assertions.assertEquals("play;discard;build", r1.getNextActions());
	}

	@Test
	public void when_playable_card_and_buildable_error_then_two_options() {
		WonderStage y = null;
		PlayableBuildableResult x = new PlayableBuildableResult(y, CardPlayable.Status.ERR_RESOURCE, new ArrayList<>());
		
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerNextAction("test1", new GetOptions())
				.withPlayerPlayableCards("test1", new CardPlayable[] {
						new CardPlayable(new ClayPit(3,1), Status.OK, new ArrayList<>(), 0)
				})
				.withPlayerCanBuild("test1", x)
				.buildGame();
		
		OptionsRequest r = new OptionsRequest();
		OptionsResponse r1 = (OptionsResponse) Player.doAction(r, testGame.getPlayer("test1"), testGame);
		
		Assertions.assertEquals(1, r1.getCards().size());
		Assertions.assertEquals("play;discard", r1.getNextActions());
	}
	
}

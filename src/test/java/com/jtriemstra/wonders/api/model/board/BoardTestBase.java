package com.jtriemstra.wonders.api.model.board;

import org.junit.jupiter.api.Assertions;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildableResult;

public class BoardTestBase extends TestBase {

	protected void assertHasResourcesToPlay(IPlayer p, Card c, Game g) {
		PlayableBuildableResult result = p.canPlay(c, g.getLeftOf(p), g.getRightOf(p));
		CardPlayable cp = new CardPlayable(result.getCard(), result.getStatus(), result.getCostOptions(), result.getCost());
		
		Assertions.assertEquals(Status.OK, cp.getStatus());
		Assertions.assertEquals(0, cp.getCostOptions().size());
	}
	
	protected void assertBankCosts(IPlayer p, Card c, Game g, int cost) {
		PlayableBuildableResult result = p.canPlay(c, g.getLeftOf(p), g.getRightOf(p));
		CardPlayable cp = new CardPlayable(result.getCard(), result.getStatus(), result.getCostOptions(), result.getCost());
		
		Assertions.assertEquals(Status.OK, cp.getStatus());
		Assertions.assertEquals(cost, cp.getBankCost());
	}
}

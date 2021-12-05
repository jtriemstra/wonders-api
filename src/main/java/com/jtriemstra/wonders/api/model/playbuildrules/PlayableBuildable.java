package com.jtriemstra.wonders.api.model.playbuildrules;

import java.util.List;

import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.board.WonderStage;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.provider.TradingProviderList;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

public class PlayableBuildable {
	private Card card;
	private WonderStage stage;
	private IPlayer p;
	private IPlayer leftNeighbor;
	private IPlayer rightNeighbor;
	private ResourceCost cost;
	private List<ResourceSet> resourcesAvailable;
	private int coinCost;
	
	public PlayableBuildable(Card c, IPlayer p, IPlayer left, IPlayer right) {
		this.card = c; 
		this.p = p;
		this.leftNeighbor = left;
		this.rightNeighbor = right;
		this.cost = c.getResourceCost() == null ? null : new ResourceCost(c.getResourceCost());
		resourcesAvailable = p.getResources(true);
		this.coinCost = c.getCoinCost();
	}
	
	public PlayableBuildable(WonderStage stage, IPlayer p, IPlayer left, IPlayer right) {
		this.stage = stage; 
		this.p = p;
		this.leftNeighbor = left;
		this.rightNeighbor = right;
		this.cost = stage.getResourceCost() == null ? null : new ResourceCost(stage.getResourceCost());
		resourcesAvailable = p.getResources(true);
		this.coinCost = stage.getCoinCost();
	}
	
	public Card getCard() {
		//if (card == null) throw new RuntimeException("card is expected but is null");
		return card;
	}
	
	public WonderStage getStage() {
		//if (stage == null) throw new RuntimeException("stage is expected but is null");
		return stage;
	}
	
	public void discountCoinCost(int discount) {
		coinCost = Math.max(0, coinCost - discount);
	}
	
	public int getCoinCost() {
		return coinCost;
	}
	
	public ResourceCost getResourceCost() {
		return cost;
	}
	
	public IPlayer getPlayer() {
		return p;
	}
	
	public int getAvailableCoins() {
		return p.getCoins();
	}
	
	public List<ResourceSet> getUnusedResources() {
		return resourcesAvailable;
	}

	public IPlayer getLeftNeighbor() {
		return this.leftNeighbor;
	}

	public IPlayer getRightNeighbor() {
		return this.rightNeighbor;
	}
	
	public int getCoinsAvailableForTrade() {
		return p.getCoins() - coinCost;
	}
	
	public TradingProviderList getTradingProviders() {
		return p.getTradingProviders();
	}
}

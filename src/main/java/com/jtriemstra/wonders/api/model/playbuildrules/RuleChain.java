package com.jtriemstra.wonders.api.model.playbuildrules;

public class RuleChain {

	private Rule first;
	
	public RuleChain() {
		
	}
	
	public static RuleChain getPlayableRuleChain() {
		
		RuleChain result = new RuleChain();
		result.first = new NoDuplicateCards();
		result.first.insert(new CardChaining());
		result.first.insert(new MustHaveCoins());
		result.first.insert(new ActionWithoutCost());
		result.first.insert(new LocalSingleResource());
		result.first.insert(new LocalComboResource());
		result.first.insert(new Trading());
		result.first.insert(new CantExecute());
		
		return result;
	}
	
	public static RuleChain getBuildableRuleChain() {
		
		RuleChain result = new RuleChain();
		result.first = new StagesComplete();
		result.first.insert(new MustHaveCoins());
		result.first.insert(new ActionWithoutCost());
		result.first.insert(new LocalSingleResource());
		result.first.insert(new LocalComboResource());
		result.first.insert(new Trading());
		result.first.insert(new CantExecute());
		
		return result;
	}
	
	public PlayableBuildableResult evaluate(PlayableBuildable actionEvaluating) {
		return first.evaluate(actionEvaluating);
	}
	
	public void addRule(Rule pr) {
		first.insert(pr);
	}
}

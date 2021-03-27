package com.jtriemstra.wonders.api.model.points;

import java.util.Arrays;
import java.util.List;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.ScienceType;

public class SciencePointStrategyAristotle implements PointStrategy {

	@Override
	public int getPoints(Player p) {
		
		long gears = p.getScienceProviders().stream().filter(s -> s.getScience().getScienceOptions()[0] == ScienceType.GEAR).count();
		long compass = p.getScienceProviders().stream().filter(s -> s.getScience().getScienceOptions()[0] == ScienceType.COMPASS).count();
		long tablets = p.getScienceProviders().stream().filter(s -> s.getScience().getScienceOptions()[0] == ScienceType.TABLET).count();
		
		List<Long> all = Arrays.asList(gears, compass, tablets);
		long sets = all.stream().min(Long::compareTo).get();
		return (int) (sets * 10 + (long)Math.pow(gears, 2) + (long)Math.pow(compass,  2) + (long)Math.pow(tablets, 2));
	}

}

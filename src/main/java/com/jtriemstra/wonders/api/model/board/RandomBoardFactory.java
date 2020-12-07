package com.jtriemstra.wonders.api.model.board;

import java.util.Random;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class RandomBoardFactory implements BoardFactory {
	@Override
	public Board getBoard() {
		Random r = new Random();
		boolean sideA = r.nextBoolean();
		switch (r.nextInt(6)) {
		case 0:	return new Ephesus(sideA);
		case 1:	return new Olympia(sideA);
		case 2:	return new Halikarnassos(sideA);
		case 3: return new Giza(sideA);
		case 4: return new Rhodes(sideA);
		case 5: return new Alexandria(sideA);
		case 6: return new Babylon(sideA);
		default: throw new RuntimeException("board not found");
		}		
	}
}

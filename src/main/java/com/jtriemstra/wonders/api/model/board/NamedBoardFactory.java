package com.jtriemstra.wonders.api.model.board;

import java.util.Random;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jtriemstra.wonders.api.model.Game.BoardSide;

public class NamedBoardFactory implements BoardFactory {
	
	private String boardNames;
	private int numberCreated;
	
	public NamedBoardFactory(String boardNames) {
		this.boardNames = boardNames;
		this.numberCreated = 0;
	}
	
	@Override
	public Board getBoard() {
		String[] namesAndSides = boardNames.split(";");
		String s = namesAndSides[numberCreated];
		boolean sideA = s.split("-")[1].equals("A") ? true : false;
		
		numberCreated++;
		
		switch (s.split("-")[0]) {
		case "Ephesus":	return new Ephesus(sideA);
		case "Olympia":	return new Olympia(sideA);
		case "Halikarnassos":	return new Halikarnassos(sideA);
		case "Giza": return new Giza(sideA);
		case "Rhodes": return new Rhodes(sideA);
		case "Alexandria": return new Alexandria(sideA);
		case "Babylon": return new Babylon(sideA);
		default: throw new RuntimeException("board not found");
		}		
	}

	@Override
	public void setSideOptions(BoardSide sideOptions) {
		// not supported
	}
}

package com.jtriemstra.wonders.api.model.exceptions;

public class BoardInUseException extends Exception {
	public BoardInUseException() {
		super("this board is already chosen");
	}
}

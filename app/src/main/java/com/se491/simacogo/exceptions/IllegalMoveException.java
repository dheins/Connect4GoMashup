package com.se491.simacogo.exceptions;

public class IllegalMoveException extends Exception {
	private static final long serialVersionUID = 1L;

	public IllegalMoveException(){
		super("That is an illegal move");
	}
	public IllegalMoveException(String s){
		super("That is an illegal move - " + s);
	}

}
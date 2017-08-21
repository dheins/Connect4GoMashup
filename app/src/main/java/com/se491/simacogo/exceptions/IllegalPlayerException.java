package com.se491.simacogo.exceptions;

public class IllegalPlayerException extends Exception {
	private static final long serialVersionUID = 1L;

	public IllegalPlayerException(){
		super("Invalid Player");
	}
	public IllegalPlayerException(String s){
		super("Invalid Player - " + s);
	}

}
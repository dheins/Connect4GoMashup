package com.se491.simacogo.model;

//  Enumeration to define the games players.

public enum Player {
	WHITE (-1), BLACK (1);

	private int value;
	Player(int num){
		this.value = num;
	}
	public int getValue(){
		return value;
	}
	public static Player getOpponenet(Player p){
		return (p == Player.BLACK) ? Player.WHITE : Player.BLACK;
	}
}

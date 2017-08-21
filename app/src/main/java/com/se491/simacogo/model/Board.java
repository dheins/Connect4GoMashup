package com.se491.simacogo.model;


/*
 * The Board class stores the state of the Simacogo game.  The game board is
 * represented by a 9x9 matrix and the players score is calculated when their
 * piece is dropped into the board.
 */

import android.util.Pair;

import com.se491.simacogo.exceptions.IllegalMoveException;
import com.se491.simacogo.exceptions.IllegalPlayerException;

public class Board {
	private final int[][] board;
	private int scoreWhite = 0;
	private int scoreBlack = 0;
	private int moves = 0;
	private Pair<Integer,Integer> lastMove;

    // Initial constructor for new game board
	public Board() {
		board = new int[9][9];
	}
    // Constructor for created a board based on another boards state. Used by
    // the MiniMax algorithm for exploring future moves
	public Board(Board b){
		board = new int[9][9];
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				board[i][j] = b.getBoard()[i][j];
			}
		}
		scoreWhite = b.getWhiteScore();
		scoreBlack = b.getBlackScore();
		moves = b.getMoves();
	}
    // returns the number of turns taken on the board. Used to determine when
    // the board is full and play should stop.
	private int getMoves() {
		return moves;
	}
    // return the current state of the game board
	private int[][] getBoard() {
		return board;
	}
    // The drop method adds a players piece to the game board. It checks to
    // ensure that a legal player(Black or White) is adding a piece to the board
    // and that the move is at a legal position (1-9) and that the player is not
    // trying to add a piece to a full column. It then sinks the piece to the
    // first available position in the column and calculates the score for that
    // piece.
	public int drop(Player player, int position) throws IllegalMoveException, IllegalPlayerException {
        // Validity checks
		if (position < 1 || position > 9){
			System.out.println("Illegal Move ->" + position);
			throw new IllegalMoveException();
		}
		if (player == null)
			throw new IllegalPlayerException();
		if (board[0][position-1] != 0){
			//System.out.println("IllegalMove -> " + player);
			throw new IllegalMoveException("That column is full");
		}


		int pos = position - 1;
		moves++;

        // Find the deepest available position in the column
		int depth = 0;
		for (int i = 1; i < 9; i++) {
			int temp = board[i][pos];
			if (temp != 0) {
				depth = i - 1;
				break;
			}else if(i==8){
				depth = 8;
			}
		}
		try{
            lastMove = new Pair<>(pos, depth);
			board[depth][pos] = player.getValue();
            //Log.d(TAG, lastMove.first + " " + lastMove.second);
		}catch (ArrayIndexOutOfBoundsException e ){
			System.out.println(depth + " " + pos);
			throw new ArrayIndexOutOfBoundsException();
		}


		// Calculate the moves score
		int score = 0;
		// check up
		if (depth > 0) {
			score += calcScore(pos, depth, pos, depth - 1);
			// check up right
			if (pos < 8) {
				score += calcScore(pos, depth, pos + 1, depth - 1);
			}
			// check up left
			if (pos > 0) {
				score += calcScore(pos, depth, pos - 1, depth - 1);
			}
		}
		// check down
		if (depth < 8) {
			score += calcScore(pos, depth, pos, depth + 1);
			// check up right
			if (pos < 8) {
				score += calcScore(pos, depth, pos+1, depth + 1);
			}
			// check up left
			if (pos > 0) {
				score += calcScore(pos, depth, pos-1, depth + 1);
			}
		}
		// check left
		if (pos > 0) {
			score += calcScore(pos, depth, pos - 1, depth);
		}
		// check right
		if (pos < 8) {
			score += calcScore(pos, depth, pos + 1, depth);
		}

		if(player == Player.WHITE){
			scoreWhite += score;
		}else{
			scoreBlack += score;
		}
		return score;

	}
    // Helper method for the drop method to calculate the pieces score
    private int calcScore(int position, int depth, int checkPos, int checkDepth) {
        // Check if the position is up, down, left, or right of the new pieces
        // position
        if (position == checkPos || depth == checkDepth) {
            // If an adjoining piece belongs to the player return 2 otherwise
            // return 0
            return (board[depth][position] == board[checkDepth][checkPos]) ? 2 : 0;
            // Check diagonal positions
        } else {
            // If a diagonal adjoining piece belongs to the player return 1
            // otherwise return 0
            return (board[depth][position] == board[checkDepth][checkPos]) ? 1 : 0;
        }

    }
    // returns the white players score (Human)
	public int getWhiteScore(){
		return scoreWhite;
	}
    // returns the black players score (AI)
	public int getBlackScore(){
		return scoreBlack;
	}
    // returns true if there are no open spaces on the board, false otherwise
    public Pair getLastMove()  { return  lastMove;  }

	public boolean isFull(){
		return moves==81;
	}
    // returns a string representation of the current game board with O's for
    // the white player, X's for the black player and dots for open spaces.
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(" 1  2  3  4  5  6  7  8  9 \n");
		sb.append("---------------------------\n");
		for(int i = 0; i< 9; i++){
			for(int j = 0; j < 9; j++){
				if(board[i][j] == 0){
					sb.append(" \u00B7 ");
				}else if(board[i][j] < 0){
					sb.append(" O ");
				}else{
					sb.append(" X ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}

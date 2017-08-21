package com.se491.simacogo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.se491.simacogo.exceptions.IllegalMoveException;
import com.se491.simacogo.exceptions.IllegalPlayerException;

public class Node {

	private NodeType type;
	private Board state;
	private List<Node> children;
	private int value;
	private int move; // the move that got us to this state

	// Initial constructor for the Node class. Takes a NodeType (Min, Max) and a
	// game board.
	public Node(NodeType t, Board b) {
		type = t;
		state = b;
		children = new ArrayList<Node>();
	}

	// Constructor for creating a new Node including the move on the game board
	// that created the new Node.
	public Node(NodeType t, Board b, int move) {
		type = t;
		state = b;
		this.move = move;
		children = new ArrayList<Node>();
	}

	// sets the value as determined from the evaluation function in MiniMax
	public void setValue(int v) {
		value = v;
	}

	// returns the nodes value from minimax
	public int getValue() {
		return value;
	}

	// returns the move that created the current game board state
	public int getMove() {
		return move;
	}

	// returns a list of children nodes. If the list is empty the helper
	// function discover children is called to generate the list.
	public List<Node> getChildren() {
		if (children.size() == 0)
			children = discoverChildren();
		return children;
	}

	// Helper function to find all of the children nodes from the current state.
	private List<Node> discoverChildren() {
		List<Node> temp = new ArrayList<Node>();
		// Get the player for of the current node. If max node player is black,
		// if min node player is white.
		Player p = (this.type == NodeType.MAX) ? Player.BLACK : Player.WHITE;
		// attempt to add a piece at each position in the game board 1-9.
		for (int i = 1; i <= 9; i++) {
			Board b = null;
			try {
				// get a deep copy of the current game board
				b = new Board(state);
				// Add a piece at position i
				b.drop(p, i);
				// Create a new child node based on the new state
				temp.add(new Node((this.type == NodeType.MAX) ? NodeType.MIN : NodeType.MAX, b, i));
			} catch (IllegalMoveException | IllegalPlayerException e) {
				// System.out.println(i + " ->" + e.getMessage() );
				// Do nothing no valid move at this position
			}
		}
		// randomly shuffle the list of nodes to bring the worst case search from O(b^d) to O(b^3d/4)
		Collections.shuffle(temp, new Random(System.currentTimeMillis()));
		return temp;
	}

	// Get the node type of the current node (MIN or MAX)
	public NodeType getType() {
		return type;
	}

	// Get the current state of the game board
	public Board getState() {
		return state;
	}

	// Return true if the children can be created from the current state or
	// false if the game board is full
	public boolean hasChildren() {
		return !state.isFull();
	}

}

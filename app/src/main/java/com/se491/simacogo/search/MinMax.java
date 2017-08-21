package com.se491.simacogo.search;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.se491.simacogo.exceptions.IllegalPlayerException;
import com.se491.simacogo.model.Node;
import com.se491.simacogo.model.NodeType;

public class MinMax {
	// if O(b^d) nodes visited = 531441 @ depth 6 no pruning
	private int nextMove = -1;
	private int nodesVisited = 0;
	private Comparator<Node> max = new MaxComparator();
	//private Comparator<Node> min = new MinComparator();

	// Constructor for the MiniMax class. It takes a node where the search will
	// start and the depth to search to. The search is automatically started at
	// construction and the next move is set.
	public MinMax(Node root, int depth) throws IllegalPlayerException {

		//for(int i = 1; i<= depth; i++){
		root.setValue(minimax(root, depth, Integer.MIN_VALUE, Integer.MAX_VALUE));
		//}

		List<Node> children = root.getChildren();
		Collections.sort(children, max);
		nextMove = children.get(0).getMove();

		System.out.println("Nodes visited -> " + nodesVisited);
	}

	// MiniMax algorithm. It recursively traverses the depth of the search tree
	// bubbling up the highest or lowest value at each level based on the value
	// returned by the evaluation function at the leaf nodes.
	private int minimax(Node root, int depth, int alpha, int beta) {
		List<Node> children = root.getChildren();
		nodesVisited++;
		// If you are at a leaf or there are no more moves evaluate the value
		// for the node
		if (depth <= 0 || children.size() == 0 || root.getState().isFull()) {
			// The evaluation heuristic. It utilizes the difference of the Black
			// players score and the White players score
			int score = root.getState().getBlackScore() - root.getState().getWhiteScore();
			root.setValue(score);
			return score;
		}
		// Evaluate Max nodes
		if (root.getType() == NodeType.MAX) {
			//Collections.sort(children, max);
			int value = Integer.MIN_VALUE;
			for (Node n : children) {
				int temp = minimax(n, depth - 1, alpha, beta);
				if (temp > value) {
					value = temp;
				}
				alpha = Math.max(alpha, value);

				// Check Beta cutoff
				if (alpha >= beta) {
					break;
				}
			}
			root.setValue(value);
			return alpha;
			// Evaluate Min nodes
		} else {
			//Collections.sort(children, min);
			int value = Integer.MAX_VALUE;
			for (Node n : children) {
				int temp = minimax(n, depth - 1, alpha, beta);
				if (temp < value) {
					value = temp;
				}
				beta = Math.min(beta, value);

				// Check Alpha cutoff
				if (beta <= alpha) {
					break;
				}
			}
			root.setValue(value);
			return beta;
		}
	}

	// returns the next move as determined by the MiniMax algorithm
	public int getNextMove() {
		return nextMove;
	}

}

// Comparator used to sort Nodes in increasing order based on their value
class MinComparator implements Comparator<Node> {

	@Override
	public int compare(Node o1, Node o2) {
		if (o1.getValue() < o2.getValue()) {
			return -1;
		} else if (o1.getValue() > o2.getValue()) {
			return 1;
		} else {
			return 0;
		}
	}

}

//Comparator used to sort Nodes in decreasing order based on their value
class MaxComparator implements Comparator<Node> {

	@Override
	public int compare(Node o1, Node o2) {
		if (o1.getValue() < o2.getValue()) {
			return 1;
		} else if (o1.getValue() > o2.getValue()) {
			return -1;
		} else {
			return 0;
		}
	}

}

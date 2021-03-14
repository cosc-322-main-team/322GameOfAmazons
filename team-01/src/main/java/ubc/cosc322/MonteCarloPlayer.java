package ubc.cosc322;

import java.util.ArrayList;

/*
 * MONTE-CARLO TREE SEARCH
 *
 * Tree Node Structure: Contains board state, wins, visits, parent and children (list of actions)
 *
 *
 * Initialization: Store the current state in the root node
 *
 *
 * Algorithm:
 *
 * 1. Get the leaf node with the max UCB value
 *
 * 2. If the current node is unvisited, playthrough + backpropagate
 *
 * 3. Otherwise, expand the current node and add its children to the tree
 *
 * 4. Get any child of the current node, playthrough + backpropagate
 *
 * 5. Repeat steps 1-4 a set amount of times (defined by us)
 *
 * 6. Select the child of the root with the best win rate as our move
 *
 *
 * References:
 *
 * 1. John Levine, https://www.youtube.com/watch?v=UXW2yZndl7U
 *
 * 2. Fullstack Academy, https://www.youtube.com/watch?v=Fbs4lnGLS8M
 */
public class MonteCarloPlayer extends LocalPlayer {
	private final float MAX_RUNTIME = 10;

	private TreeNode root;

	public MonteCarloPlayer() {
		super("jaden_mc", "montecarlo");
	}

	@Override
	protected void onMoveReceived() {
		root = new TreeNode(board);

		long startTime = System.currentTimeMillis() / 1000;
		long currentTime = startTime;

		//Added loop counter for debugging / optimizing so we can see how effective this will be
		int iterations = 0;
		while (currentTime < startTime + MAX_RUNTIME) {
			TreeNode current = getMaxLeaf(root);
			if (current.getVisits() == 0) {
				int winner = playthrough(current);
				backpropagate(current, winner);
			} else {
				TreeNode child = current.expand();
				int winner = playthrough(child);
				backpropagate(child, winner);
			}
			iterations++;
			currentTime = System.currentTimeMillis() / 1000;
		}

		root = getBestNode(root);
		AmazonsAction move = root.getAction();
		sendMove(move.queenCurrent, move.queenTarget, move.arrowTarget);
	}

	// TODO
	private TreeNode getBestNode(TreeNode root) {
		return null;
	}

	private void backpropagate(TreeNode current, int winner) {
		while (current != null) {
			if (current.state.localPlayer == winner) {
				current.wins++;
			}
			current.visits++;
			current = current.parent;
		}
	}

	private int playthrough(TreeNode current) {
		AmazonsActionFactory af = new AmazonsActionFactory();
		AmazonsLocalBoard b = current.state.copy();

		int winner = Integer.MIN_VALUE;
		//while nobody has won, run the simulation
		while (winner < 0) {
			ArrayList<AmazonsAction> actions = af.getActions(b);
			//Check win conditions
			//If node color loses
			if (actions.size() == 0) {
				winner = b.getOpponent();
				break;
			}
			// Pick a random move
			int moveIndex = (int) (Math.random() * (actions.size() - 1));
			AmazonsAction move = actions.get(moveIndex);
			//Make the move
			TreeNode temp = null;
			if (root.children.size() == 0) {
				root.expand();
			}
			for (TreeNode node : root.children) {
				// We find the node corresponding to the move we want to make and rebase the tree
				if (node.getAction().equals(move)) {
					root = node;
					root.parent = null;
				}
			}
			b.updateState(move.queenCurrent, move.queenTarget,move.arrowTarget);
			b.localPlayer = b.getOpponent();
		}
		return winner;
	}

	// TODO
	private TreeNode getMaxLeaf(TreeNode root) {
		return null;
	}

	// TODO
	private float getUCB() {
		return 0;
	}

	private class TreeNode {
		private AmazonsLocalBoard state;
		private AmazonsAction action;
		private int wins = 0;
		private int visits = 0;
		private TreeNode parent;
		private ArrayList<TreeNode> children;

		public TreeNode(AmazonsLocalBoard state) {
			this(state, null, null);
		}

		public TreeNode(AmazonsLocalBoard state, AmazonsAction action, TreeNode parent) {
			this.state = state; // TODO: deepcopy
			this.action = action;
			this.parent = parent;
			children = new ArrayList<TreeNode>();
		}

		// TODO

		/**
		 * Returns the first child expanded.
		 */
		public TreeNode expand() {
			// Get list of possible actions

			// Make new state node for each action

			// Add each node as a child of this node

			// Return the first child expanded
			return null;
		}

		public int getVisits() {
			return visits;
		}

		public AmazonsAction getAction() {
			return action;
		}
	}
}

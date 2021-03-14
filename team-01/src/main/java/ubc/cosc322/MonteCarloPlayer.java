package ubc.cosc322;

import java.util.ArrayList;
import ygraph.ai.smartfox.games.Amazon;

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

	// The constant used for UCB function. Same one chosen in the John Levine video.
	private final double EXPLORATION_FACTOR = Math.sqrt(2);

	private TreeNode root;

	public MonteCarloPlayer() {
		super("jaden_mc", "montecarlo");
	}

	@Override
	protected void onMoveReceived() {
		root = new TreeNode(board);

		long startTime = System.currentTimeMillis() / 1000;
		long currentTime = startTime;

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
			currentTime = System.currentTimeMillis() / 1000;
		}

		root = getBestMove(root);
		AmazonsAction action = root.getAction();
		sendMove(action.queenCurrent, action.queenTarget, action.arrowTarget);
	}

	private TreeNode getBestMove(TreeNode root) {
		int maxWins = -1;
		TreeNode best = null;

		for (TreeNode node : root.children) {
			int wins = node.getWins();
			if (wins > maxWins) {
				maxWins = wins;
				best = node;
			}
		}

		return best;
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
		AmazonsLocalBoard state = current.state.copy();
		int winner = -1;

		while (winner < 0) {
			ArrayList<AmazonsAction> actions = actionFactory.getActions(state);

			// Check win conditions
			if (actions.size() == 0) {
				return state.getOpponent();
			}

			// Pick a random move
			int moveIndex = (int) (Math.random() * actions.size());
			AmazonsAction move = actions.get(moveIndex);

			// Apply the selected move to the state
			state.updateState(move.queenCurrent, move.queenTarget, move.arrowTarget);
			state.localPlayer = state.getOpponent();
		}

		return winner;
	}

	private TreeNode getMaxLeaf(TreeNode root) {
		ArrayList<TreeNode> leaves = getLeaves(root);

		int leafIndex = 0;
		double maxUCB = leaves.get(0).getUCB();
		for (int i = 1; i < leaves.size(); i++) {
			double ucb = leaves.get(i).getUCB();
			if (ucb > maxUCB) {
				leafIndex = i;
				maxUCB = ucb;
			}
		}

		return leaves.get(leafIndex);
	}

	private ArrayList<TreeNode> getLeaves(TreeNode root) {
		ArrayList<TreeNode> leaves = new ArrayList<>();

		for (int i = 0; i < root.children.size(); i++) {
			TreeNode current = root.children.get(i);
			// Check if the child has children or is a leaf
			if (current.children.isEmpty())
				leaves.add(current);
			else
				// Recursively add current's leaves to the list of leaves
				leaves.addAll(getLeaves(current));
		}

		return leaves;
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

		/**
		 * Returns the first child expanded.
		 */
		public TreeNode expand() {
			// Get list of possible actions
			ArrayList<AmazonsAction> actions = actionFactory.getActions(state);

			// Make new state node for each action
			for (int i = 0; i < actions.size(); i++) {
				AmazonsAction childAction = actions.get(i);

				AmazonsLocalBoard childState = state.copy();
				childState.localPlayer = state.localPlayer == 1 ? 2 : 1;
				childState.updateState(childAction);

				// Add each node as a child of this node
				children.add(new TreeNode(childState, childAction, this));
			}

			// Return the first child expanded
			return children.get(0);
		}

		public int getWins() {
			return wins;
		}

		public int getVisits() {
			return visits;
		}

		public AmazonsAction getAction() {
			return action;
		}

		// TODO
		private double getUCB() {
			// EXPLORATION_FACTOR = constant defined at the top of the class.
			// If we hit 0, then the unvisited node should return infinity.
			if (this.getVisits() == 0) {
				return Double.MAX_VALUE;
			}

			// uct = v = total score / number of visits == avg value of the state.
			float uct = wins / visits;

			// apply the UCB1 function for that state
			if (parent != null) {
				uct += EXPLORATION_FACTOR * Math.sqrt(Math.log(parent.visits) / visits);
			}
			// Return ucb1 score.
			return uct;
		}
	}
}

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

  //The constant used for UCB function. Same one chosen in the John Levine video.
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
        boolean won = playthrough(current);
        backpropagate(current, won);
      } else {
        TreeNode child = current.expand();
        boolean won = playthrough(child);
        backpropagate(child, won);
      }
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

  // TODO
  private void backpropagate(TreeNode current, boolean won) {
    return;
  }

  // TODO
  private boolean playthrough(TreeNode current) {
    return true;
  }

  // TODO
  private TreeNode getMaxLeaf(TreeNode root) {
    return null;
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
    /** Returns the first child expanded. */
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

    // TODO
    private double getUCB() {
      // EXPLORATION_FACTOR = constant defined at the top of the class.
      // If we hit 0, then the unvisited node should return infinity.
      if (this.getVisits() == 0){
        return Double.MAX_VALUE;
      }

      // uct = v = total score / number of visits == avg value of the state.
      float uct =  this.wins / this.visits;

      // apply the UCB1 function for that state
      if (this.parent != null) {
        uct += EXPLORATION_FACTOR * Math.sqrt(Math.log(this.parent.getVisits()) / this.getVisits());
      }
      // Return ucb1 score.
      return uct;
    }
  }
}

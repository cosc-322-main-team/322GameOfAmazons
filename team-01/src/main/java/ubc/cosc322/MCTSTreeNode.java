package ubc.cosc322;

import java.util.ArrayList;

public class MCTSTreeNode {
    private AmazonsActionFactory actionFactory = new AmazonsActionFactory();
    private AmazonsLocalBoard board;
    private AmazonsAction move;
    private MCTSTreeNode parent;
    private ArrayList<MCTSTreeNode> children;
    // Multiplicitive value applied for the UCT calculation,
    // theoretically equal to this, may change with experiment
    private final int explorations = (int) Math.sqrt(2);
    //Number of simulations executed (total games played)
    private int simulations = 0;
    // Player values take on 1 and 2 (1 is white, 2 is black)
    private final int player;
    private final int opponent;

    private int wins = 0;
    private int opponent_wins = 0;

    public MCTSTreeNode(AmazonsLocalBoard board, AmazonsAction move, int player) {
        this.board = board.deepCopy();
        this.move = move;
        this.player = board.localPlayer;
        opponent = (player == 1) ? 2 : 1;

        children = new ArrayList<MCTSTreeNode>();
        parent = null;
    }

    public MCTSTreeNode expandTree() {
        ArrayList<AmazonsAction> all_possible_moves = actionFactory.getActions(board);
        // edge case
        if (children.size() == all_possible_moves.size()) {
            return null;
        }
        for (AmazonsAction move : all_possible_moves) {
            boolean moveFound = false;
            //Prevent duplicate nodes <3
            for (MCTSTreeNode node : this.children) {
                if (node.move == move) {
                    moveFound = true;
                    break;
                }
            }
            //If the move (edge) does not have a corresponding node that is a child of the node we are looking at,
            //add this move as a child of the current node
            if (!moveFound) {
                AmazonsLocalBoard b = board.deepCopy();
                //update board with new move here
                b.updateState(move.queenCurrent, move.queenTarget, move.arrowTarget);
                MCTSTreeNode n = new MCTSTreeNode(b, move, this.opponent);
                n.setParent(this);
                this.children.add(n);
            }
        }
        // Pick a random child node to return, might want to change this
        // later but it shouldn't really matter which node is returned
        int idx;
        if (this.getChildren().size() > 1) {
            idx = (int) (Math.random() * this.getChildren().size() - 1);
        } else if (this.getChildren().size() == 1) {
            idx = 0;
        } else {
            return null;
        }
        return this.children.get(idx);
    }

    public float getUCT() {
        if (this.simulations == 0) {
            return this.explorations;
        }
        //Calculate base win rate
        float uct = (float) (this.wins / this.simulations);

        //Make sure not root node
        if (this.parent != null) {
            // Formula for upper confidence bound. Found easily with google
            uct += this.explorations * Math.sqrt(Math.log(this.parent.getSimulations()) / this.simulations);
        }
        return uct;
    }

    public int getSimulations() {
        return simulations;
    }

    public MCTSTreeNode getParent() {
        return parent;
    }

    public ArrayList<MCTSTreeNode> getChildren() {
        return children;
    }

    public AmazonsAction getMove() {
        return move;
    }

    public void setParent(MCTSTreeNode parent) {
        this.parent = parent;
    }

    // Would like to move this method to the localboard class but I didn't wanna add a reference to
    // action factory in there as well so it will chill here for now
    public boolean isGameOver() {
        int numActions = actionFactory.getActions(board).size();
        return numActions == 0;
    }
}

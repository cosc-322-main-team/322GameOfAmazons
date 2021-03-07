package ubc.cosc322;

public class MCTSTree {
    /*
     * TODO figure out how long we have per move
     * TODO implement back propogation - sums up total wins and opponent wins for the nodes
     * TODO implement simulateGame - simulates game down a tree
     * TODO implement select move - selects the overall best move
     * Doing this will get us our base MCTS implementation,
     * I'm pretty happy to do all this and then walk everyone
     * through it for thursday (mar. 11)
     * Once this is done we will need to add specifics for
     * Game of amazons, the research paper we found has a lot
     * of good resources for this. Additionally, we may want to make it
     * so rather than choosing a random move for our opponent player in our
     * simulation, we may want to use a minmax algorithm or something like that
     * also i'd like to try implementing threading on this if we have time, as the more
     * simulations we can run, the better our results will be
     *
     */
    private AmazonsLocalBoard board;
    private MCTSTreeNode root;

    public MCTSTree(AmazonsLocalBoard board, int player) {
        this.board = board.deepCopy();
        this.root = new MCTSTreeNode(this.board, null, player);
    }

    public MCTSTree(AmazonsLocalBoard board, MCTSTreeNode root, int player) {
        this.board = board.deepCopy();
        this.root = root;
    }

    // Makes our move, and then rebases tree to new
    // root so we can eliminate unreachable game states
    // this function should make a move within our tree
    public void makeMove(AmazonsAction move) {
        // if we have an empty tree, we need to make it not that
        //MCTSTreeNode temp;
        if (this.root.getChildren().size() == 0) {
            this.root.expandTree();
        }
        for (MCTSTreeNode node : this.root.getChildren()) {
            if (node.getMove().equals(move)) {
                //temp = this.root;
                this.root = node;
                this.root.setParent(null);
                break;
            }
        }
        //We may need garbage collection here, hence the temp
        //but i think java does this??
    }

    //Select node in tree with highest UCT score to expand
    public MCTSTreeNode selectNode() {
        MCTSTreeNode node = this.root;
        // This should exit on leaf node as there should be no children in that node
        while (node.getChildren().size() != 0) {
            float maxUCT = Float.MIN_VALUE;
            MCTSTreeNode nodeToExpand = null;
            for (MCTSTreeNode n : node.getChildren()) {
                float uct = n.getUCT();
                if (uct > maxUCT) {
                    maxUCT = uct;
                    nodeToExpand = n;
                }
            }
            node = nodeToExpand;
        }
        return node;
    }

    // recursive function to print trees
    // Untested :)
    public void debug(MCTSTreeNode node) {
        if (node.getChildren() != null) {
            for (MCTSTreeNode child : node.getChildren()) {
                debug(child);
                System.out.println(node.getUCT());
            }
        }

    }
}

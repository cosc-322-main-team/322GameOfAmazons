package ubc.cosc322;


import java.util.Arrays;
import java.util.ArrayList;

public class AmazonsActionFactory {
    public ArrayList<AmazonsAction> getActions(AmazonsLocalBoard board) {
        ArrayList<AmazonsAction> list = new ArrayList();
        //Double array list initialized to store queen current positions
        ArrayList<ArrayList<Integer>> allQueens = getAllQueenCurrents(board);

        while (!allQueens.isEmpty()) {
            //Iterating through the allQueens arrayList removing them one by one.
            ArrayList<Integer> queenCurrent = allQueens.remove(0);

            //Creating a double array list of all possible moves for the current queen.
            ArrayList<ArrayList<Integer>> allQueenTargets = getTargets(queenCurrent.get(0), queenCurrent.get(1), board);

            while (!allQueenTargets.isEmpty()) {
                //Iterating through all the possible moves and removing them one by one
                ArrayList<Integer> queenTarget = allQueenTargets.remove(0);

                //Creating a double array list of all possible arrow moves for the current queen target position
                ArrayList<ArrayList<Integer>> allArrowTargets = getTargets(queenTarget.get(0), queenTarget.get(1), board);

                while (!allArrowTargets.isEmpty()) {
                    //Iterating through all the arrow target locations and removing them.
                    ArrayList<Integer> arrowTarget = allArrowTargets.remove(0);

                    //Adding the current arrow target, the queen target position, and the current queen to the list of AmazonsActions
                    AmazonsAction newAction = new AmazonsAction(queenCurrent, queenTarget, arrowTarget);
                    list.add(newAction);
                }
            }
        }
        return list;
    }

    private boolean isWithinMoves(int queenX, int queenY, int x, int y) {
        //1 Calculate vertical and horizontal moves
        //2 Calculate diagonal moves
        //3 Make sure path is clear.
        return true;
    }

    private ArrayList<ArrayList<Integer>> getAllQueenCurrents(AmazonsLocalBoard board) {
        ArrayList<ArrayList<Integer>> queenCurrents = new ArrayList();
        //Iterating through the entire board finding each queen position.
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                ArrayList<Integer> position = new ArrayList(Arrays.asList(i, j));
                //The queen position value can be 3 or 2 depending on if our queens are white or black.
                if (board.getPositionValue(position) == 2) {
                    queenCurrents.add(position);
                }
            }
        }
        return queenCurrents;
    }

    private ArrayList<ArrayList<Integer>> getTargets(int x, int y, AmazonsLocalBoard board) {
        ArrayList<ArrayList<Integer>> targets = new ArrayList();
        //Iterating a board finding all open target positions for either a queen move or an arrow move.
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                ArrayList<Integer> position = new ArrayList(Arrays.asList(i, j));
                //Checking if the target position is open and if it is within moves.
                if (board.getPositionValue(position) == 0 && withinMoves(x, y, i, j)) {
                    targets.add(position);
                }
            }
        }
        return targets;
    }
}

package ubc.cosc322;


import java.util.Arrays;
import java.util.ArrayList;

public class AmazonsActionFactory {
  public ArrayList<AmazonsAction> getActions(AmazonsLocalBoard board) {
    ArrayList<AmazonsAction> list = new ArrayList();
    ArrayList<ArrayList<Integer>> allQueens= getAllQueenCurrents(board);

    while(!allQueens.isEmpty()) {

      ArrayList<Integer> queenCurrent= allQueens.remove(0);

      ArrayList<ArrayList<Integer>> allQueenTargets = getTargets(queenCurrent.get(0), queenCurrent.get(1), board);

      while(!allQueenTargets.isEmpty()) {

        ArrayList<Integer> queenTarget = allQueenTargets.remove(0);

        ArrayList<ArrayList<Integer>> allArrowTargets = getTargets(queenTarget.get(0), queenTarget.get(1), board);

        while(!allArrowTargets.isEmpty()){

          ArrayList<Integer> arrowTarget = allArrowTargets.remove(0);

          AmazonsAction newAction = new AmazonsAction(queenCurrent, queenTarget, arrowTarget);
          list.add(newAction);
        }
      }
      //Add these to the Amazons action lists
    }
    return list;
  }
  private boolean withinMoves(int queenX, int queenY, int x, int y){
    //1 Calculate vertical and horizontal moves
    //2 Calculate diagonal moves
    return true;
  }
  private ArrayList<ArrayList<Integer>> getAllQueenCurrents(AmazonsLocalBoard board){
    ArrayList<ArrayList<Integer>>  queenCurrents = new ArrayList();
    for(int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        ArrayList<Integer> position = new ArrayList(Arrays.asList(i,j));
        if(board.getPositionValue(position) == 2){
          queenCurrents.add(position);
        }
      }
    }
    return queenCurrents;
  }
  private ArrayList<ArrayList<Integer>> getTargets(int x, int y, AmazonsLocalBoard board) {
    //Possible queen moves, is gonna call get arrow
    ArrayList<ArrayList<Integer>> targets = new ArrayList();
    for(int i = 0; i < 10; i++){
      for(int j = 0; j < 10; j++){
        ArrayList<Integer> position = new ArrayList(Arrays.asList(i,j));
        if(board.getPositionValue(position) == 0 && withinMoves(x, y, i,j)) {
          targets.add(position);
        }
      }
    }
    return targets;
  }
}

package ubc.cosc322;


import java.util.ArrayList;

public class AmazonsActionFactory {
  public ArrayList<AmazonsAction> getActions(ArrayList<Integer> state) {
    ArrayList<AmazonsAction> list = new ArrayList();
    ArrayList<ArrayList<Integer>> allQueens= getAllQueenCurrents();

    while(!allQueens.isEmpty()) {

      ArrayList<Integer> queenCurrent= allQueens.remove(0);

      ArrayList<ArrayList<Integer>> allQueenTargets = getTargets(queenCurrent.get(0), queenCurrent.get(1));

      while(!allQueenTargets.isEmpty()) {

        ArrayList<Integer> queenTarget = allQueenTargets.remove(0);

        ArrayList<ArrayList<Integer>> allArrowTargets = getTargets(queenTarget.get(0), queenTarget.get(1));

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
    return false;
  }
  private ArrayList<ArrayList<Integer>> getAllQueenCurrents(){
    ArrayList<ArrayList<Integer>>  queenCurrents = new ArrayList();
    for(int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; i++) {
        if(state.position(i,j) == 2){
          //Create an arraylist for the queens current position
          ArrayList<Integer> queenPosition = new ArrayList();
          queenPosition.add(i);
          queenPosition.add(j);
          queenCurrents.add(queenPosition);

        }
      }
    }
    return queenCurrents;
  }
  private ArrayList<ArrayList<Integer>> getTargets(int startPositionX, int startPositionY) {
    //Possible queen moves, is gonna call get arrow
    ArrayList<ArrayList<Integer>> targets = new ArrayList();
    for(int targetPositionX = 0; targetPositionX < 10; targetPositionX++){
      for(int targetPositionY = 0; targetPositionY < 10; targetPositionY++){
        if(state.position(targetPositionX, targetPositionY) == 0 && withinMoves(startPositionX, startPositionY, targetPositionX,targetPositionY)) {
          ArrayList<Integer> targetPosition = new ArrayList();
          targetPosition.add(targetPositionX);
          targetPosition.add(targetPositionY);
          targets.add(targetPosition);
        }
      }
    }
    return targets;
  }
}

package ubc.cosc322;


import java.util.ArrayList;

public class AmazonsActionFactory {
  public ArrayList<AmazonsAction> getActions(ArrayList<Integer> state) {
    ArrayList<AmazonsAction> list = new ArrayList();
    ArrayList<Integer> allQueens= getAllQueenCurrents();

    while(!allQueens.isEmpty()) {
      int queenX = allQueens.remove(0);
      int queenY = allQueens.remove(0);

      ArrayList<Integer> queenCurrent = new ArrayList();
      queenCurrent.add(queenX);
      queenCurrent.add(queenY);

      ArrayList<Integer> allQueenTargets = getTargets(queenX, queenY);
      while(!allQueenTargets.isEmpty()) {
        int queenTargetX = allQueenTargets.remove(0);
        int queenTargetY = allQueenTargets.remove(0);

        ArrayList<Integer> queenTarget = new ArrayList();
        queenTarget.add(queenTargetX);
        queenTarget.add(queenTargetY);

        ArrayList<Integer> allArrowTargets = getTargets(queenTargetX, queenTargetY);
        while(!allArrowTargets.isEmpty()){
          int arrowTargetX = allArrowTargets.remove(0);
          int arrowTargetY = allArrowTargets.remove(0);

          ArrayList<Integer> arrowTarget = new ArrayList();
          arrowTarget.add(arrowTargetX);
          arrowTarget.add(arrowTargetY);

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
  private ArrayList<Integer> getAllQueenCurrents(){
    ArrayList<Integer> queenCurrents = new ArrayList();
    for(int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; i++) {
        if(state.position(i,j) == 2){
          //Create an arraylist for the queens current position
          queenCurrents.add(i);
          queenCurrents.add(j);
        }
      }
    }
    return queenCurrents;
  }
  private ArrayList<Integer> getTargets(int startPositionX, int startPositionY) {
    //Possible queen moves, is gonna call get arrow
    ArrayList<Integer> targets = new ArrayList();
    for(int targetPositionX = 0; targetPositionX < 10; targetPositionX++){
      for(int targetPositionY = 0; targetPositionY < 10; targetPositionY++){
        if(state.position(targetPositionX, targetPositionY) == 0 && withinMoves(startPositionX, startPositionY, targetPositionX,targetPositionY)) {
          targets.add(targetPositionX);
          targets.add(targetPositionY);
        }
      }
    }
    return targets;
  }
}

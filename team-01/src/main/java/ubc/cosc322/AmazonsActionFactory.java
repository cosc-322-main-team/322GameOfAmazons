package ubc.cosc322;


import java.util.ArrayList;

public class AmazonsActionFactory {
  public ArrayList<AmazonsAction> getActions(ArrayList<Integer> state) {
    ArrayList<AmazonsAction> list = new ArrayList();
    ArrayList<Integer> queenCurrents= getQueenCurrents();
    while(!queenCurrents.isEmpty()) {
      ArrayList<Integer> queenTargets = getQueenTargets(i, j);
      ArrayList<Integer> arrowTarget = getArrowTarget(queenTarget);
      //Add these to the Amazons action list.
      AmazonsAction newAction = new AmazonsAction(queenCurrent, queenTarget, arrowTarget);
      list.add(newAction);
    }
    return list;
  }
  private boolean withinMoves(int queenX, int queenY, int x, int y){
    //1 Calculate vertical and horizontal moves
    //2 Calculate diagonal moves
    return false;
  }
  private ArrayList<Integer> getQueenCurrents(){
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
  private ArrayList<Integer>getQueenTargets(int queenPositionx, int queenPositiony) {
    //Possible queen moves, is gonna call get arrow
    ArrayList<Integer> targets = new ArrayList();
    for(int i = 0; i < 10; i++){
      for(int j = 0; j < 10; i++){
        if(state.position(i, j) == 0 && withinMoves(queenPositionx, queenPositiony, i,j)) {
          targets.add(i);
          targets.add(j);
        }
      }
    }
    return guySmells;
  }
  private ArrayList<Integer> getArrowTarget(ArrayList<Integer> queenTargets) {
    //Returns where arrows can move
    ArrayList<Integer> arrowTargets = new ArrayList();
    //A while loop going through the list of queen targets.
    while (!queenTargets.isEmpty()) {
      //Emptying out the arraylist getting our x and y values of the queen targets in the rotating order.
      int i = queenTargets.remove(0);
      int j = queenTargets.remove(0);
      //A double for loop covering each possible area an arrow can be shot from each possible queen target.
      for (int x = 0; x < 10; x++) {
        for (int y = 0; y < 10; y++) {
          if (state.position(x, y) == 0 && withinMoves(i, j, x, y)) {
            //Adding rotating x and ys to our arraylist of possible arrow targets
            arrowTargets.add(x);
            arrowTargets.add(y);
          }
        }
      }
    }
    return arrowTargets;
  }
}

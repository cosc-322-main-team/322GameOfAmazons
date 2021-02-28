package ubc.cosc322;


import java.util.ArrayList;

public class AmazonsActionFactory {
  public ArrayList<AmazonsAction> getActions(ArrayList<Integer> state) {
    ArrayList<AmazonsAction> list = new ArrayList();
    //Find queen Positions
    for(int i = 0; i <= 9; i++) {
      for (int j = 0; j <= 9; i++) {

      }
    }
    //Turn these into different methods.
    return list;
  }
  private boolean withinMoves(int x, int y){
    //1 Calculate vertical and horizontal moves
    //2 Calculate diagonal moves
    return false;
  }
  private AmazonsAction getQueenMove() {
    //Possible queen moves, is gonna call get arrow
    for(int i = 0; i <= 9; i++){
      for(int j = 0; j <= 9; i++){
        if(state.postion(i, j) == 0 && withinMoves(queenPosition.x, queenPosition.y, i,j)) {
        }
      }
    }
  }
  private AmazonsAction getArroveMove(){
    //Returns where arrows can move
    for(int x = 0; x <= 9; x++){
      for(int y = 0; y <= 9; y++){
        if(state.postion(x, y) == 0 && withinMoves(i,j,x,y)) {

        }
      }
    }
  }
}

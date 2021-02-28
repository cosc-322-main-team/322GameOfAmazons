package ubc.cosc322;

import java.util.ArrayList;

public class AmazonsAction {
  public ArrayList<Integer> queenCurrent;
  public ArrayList<Integer> queenTarget;
  public ArrayList<Integer> arrowTarget;

  public AmazonsAction(ArrayList<Integer> queenCurrent, ArrayList<Integer> queenTarget,
      ArrayList<Integer> arrowTarget) {
    this.queenCurrent = queenCurrent;
    this.queenTarget = queenTarget;
    this.arrowTarget = arrowTarget;
  }
}

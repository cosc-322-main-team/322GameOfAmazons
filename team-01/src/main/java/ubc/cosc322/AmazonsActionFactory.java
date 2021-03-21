package ubc.cosc322;

import java.util.Arrays;
import java.util.ArrayList;

public class AmazonsActionFactory {
	public ArrayList<AmazonsAction> getActions(AmazonsLocalBoard board) {
		ArrayList<AmazonsAction> list = new ArrayList<AmazonsAction>();
		// Double array list initialized to store queen current positions
		ArrayList<ArrayList<Integer>> allQueens = getAllQueenCurrents(board);

		while (!allQueens.isEmpty()) {
			// Iterating through the allQueens arrayList removing them one by one.
			ArrayList<Integer> queenCurrent = allQueens.remove(0);

			// Creating a double array list of all possible moves for the current queen.
			ArrayList<ArrayList<Integer>> allQueenTargets = getTargets(queenCurrent.get(0), queenCurrent.get(1), board);

			while (!allQueenTargets.isEmpty()) {
				// Iterating through all the possible moves and removing them one by one
				ArrayList<Integer> queenTarget = allQueenTargets.remove(0);

				// Creating a double array list of all possible arrow moves for the current queen target position
				ArrayList<ArrayList<Integer>> allArrowTargets = getTargets(queenTarget.get(0), queenTarget.get(1), board);

				ArrayList<Integer> arrowToCurrentQueen = new ArrayList<Integer>();
				arrowToCurrentQueen.add(queenCurrent.get(0));
				arrowToCurrentQueen.add(queenCurrent.get(1));
				allArrowTargets.add(arrowToCurrentQueen);

				while (!allArrowTargets.isEmpty()) {
					// Iterating through all the arrow target locations and removing them.
					ArrayList<Integer> arrowTarget = allArrowTargets.remove(0);

					// Adding the current arrow target, the queen target position, and the current queen to the list of AmazonsActions
					AmazonsAction newAction = new AmazonsAction(queenCurrent, queenTarget, arrowTarget);
					list.add(newAction);
				}
			}
		}
		//toString(list);
		return list;
	}

	/*private boolean isWithinMoves(int queenX, int queenY, int targetX, int targetY, AmazonsLocalBoard board) {
		// Check if position is within reach of queen
		boolean isInOrthogonalReach = queenX == targetX || queenY == targetY;
		boolean isInDiagonalReach = Math.abs(queenX - targetX) == Math.abs(queenY - targetY);
		boolean isWithinReach = isInOrthogonalReach || isInDiagonalReach;

		// Check if path is clear and return
		return isWithinReach && isPathClear(queenX, queenY, targetX, targetY, board);
	}*/

	private boolean isPathClear(int queenX, int queenY, int targetX, int targetY, AmazonsLocalBoard board) {
		// Initialize currentX and currentY to queen position
		int currX = queenX;
		int currY = queenY;

		// While we haven't reached the target
		while (currX != targetX || currY != targetY) {
			// Advance towards the target by 1, regardless of whether target is
			// above, below, on the right or on the left of queen, or how far the target is
			currX += Math.signum(targetX - currX);
			currY += Math.signum(targetY - currY);

			// If we find an obstacle, return false
			if (board.getPositionValue(currX, currY) != 0)
				return false;
		}

		// No obstacle found! The path is clear.
		return true;
	}

	private ArrayList<ArrayList<Integer>> getAllQueenCurrents(AmazonsLocalBoard board) {
		ArrayList<ArrayList<Integer>> queenCurrents = new ArrayList<ArrayList<Integer>>();
		// Iterating through the entire board finding each queen position.
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++) {
				ArrayList<Integer> position = new ArrayList<Integer>(Arrays.asList(i, j));
				// The queen position value can be 1 or 2 depending on if our queens are white or black.
				if (board.getPositionValue(position) == board.localPlayer) {
					queenCurrents.add(position);
				}
			}
		}
		return queenCurrents;
	}

	/*private ArrayList<ArrayList<Integer>> getTargets(int x, int y, AmazonsLocalBoard board) {
		ArrayList<ArrayList<Integer>> targets = new ArrayList<ArrayList<Integer>>();
		// Iterating a board finding all open target positions for either a queen move or an arrow move.
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++) {
				if (i == x && j == y)
					continue;

				ArrayList<Integer> position = new ArrayList<Integer>(Arrays.asList(i, j));
				// Checking if the target position is open and if it is within moves.
				if (board.getPositionValue(position) == 0 && isWithinMoves(x, y, i, j, board)) {
					targets.add(position);
				}
			}
		}
		return targets;
	}*/
	private ArrayList<ArrayList<Integer>> getTargets(int x, int y, AmazonsLocalBoard board) {
		ArrayList<ArrayList<Integer>> targets = new ArrayList<ArrayList<Integer>>();

		// Iterate vertically Up
		if (y != 10) {
			for (int i = y + 1; i <= 10; i++) {
				if (isPathClear(x,y,x,i,board)) {
					ArrayList<Integer> position = new ArrayList<Integer>(Arrays.asList(x, i));
					targets.add(position);
				} else
					break;
			}
		}
		// Iterate vertically Down
		if (y != 1) {
			for (int i = y - 1; i >= 1; i--) {
				if (isPathClear(x,y,x,i,board)) {
					ArrayList<Integer> position = new ArrayList<Integer>(Arrays.asList(x, i));
					targets.add(position);
				} else
					break;
			}
		}
		// Iterate horizontally Right
		if (x != 10) {
			for (int i = x + 1; i <= 10; i++) {
				if (isPathClear(x,y,i,y,board)) {
					ArrayList<Integer> position = new ArrayList<Integer>(Arrays.asList(i, y));
					targets.add(position);
				} else
					break;
			}
		}
		// Iterate horizontally Left
		if (x != 0) {
			for (int i = x - 1; i >= 1; i--) {
				if (isPathClear(x,y,i,y,board)) {
					ArrayList<Integer> position = new ArrayList<Integer>(Arrays.asList(i, y));
					targets.add(position);
				} else
					break;
			}
		}
		// Iterate diagonally Right up
		int i = x + 1;
		int j = y + 1;
		if(x != 10 && y != 10) {
			while (i <= 10 && j <= 10) {
				if (isPathClear(x,y,i,j,board)) {
					ArrayList<Integer> position = new ArrayList<Integer>(Arrays.asList(i, j));
					targets.add(position);
				} else
					break;
				i++;
				j++;
			}
		}
		// Iterate diagonally Right Down
		i = x + 1;
		j = y - 1;
		if(x != 10 && y != 1) {
			while (i <= 10 && j >= 1) {
				if (isPathClear(x,y,i,j,board)) {
					ArrayList<Integer> position = new ArrayList<Integer>(Arrays.asList(i, j));
					targets.add(position);
				} else
					break;
				i++;
				j--;
			}
		}
		// Iterate diagonally Left Up
		i = x - 1;
		j = y + 1;
		if(x != 1 && y != 10) {
			while (i >= 1 && j <= 10) {
				if (isPathClear(x,y,i,j,board)) {
					ArrayList<Integer> position = new ArrayList<Integer>(Arrays.asList(i, j));
					targets.add(position);
				} else
					break;
				i--;
				j++;
			}
		}
		// Iterate diagonally left Down
		i = x - 1;
		j = y - 1;
		if(x != 1 && y != 1) {
			while (i >= 1 && j >= 1) {
				if (isPathClear(x,y,i,j,board)) {
					ArrayList<Integer> position = new ArrayList<Integer>(Arrays.asList(i, j));
					targets.add(position);
				} else
					break;
				i--;
				j--;
			}
		}
		return targets;
	}
	public void toString(ArrayList<AmazonsAction> list){
		for(int i = 0; i < list.size(); i++){
			System.out.print(list.get(i).queenCurrent + " " + list.get(i).queenTarget + " "+ list.get(i).arrowTarget);
			System.out.print("\n");
		}
	}
}

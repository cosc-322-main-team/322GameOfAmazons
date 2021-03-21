package ubc.cosc322;

import java.util.Arrays;
import java.util.ArrayList;

public class AmazonsActionFactory {
	public ArrayList<AmazonsAction> getActions(AmazonsLocalBoard board) {
		ArrayList<AmazonsAction> list = new ArrayList<>();
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
		return list;
	}

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
		ArrayList<ArrayList<Integer>> queenCurrents = new ArrayList<>();
		// Iterating through the entire board finding each queen position.
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++) {
				ArrayList<Integer> position = new ArrayList<>(Arrays.asList(i, j));
				// The queen position value can be 1 or 2 depending on if our queens are white or black.
				if (board.getPositionValue(position) == board.localPlayer) {
					queenCurrents.add(position);
				}
			}
		}
		return queenCurrents;
	}

	private ArrayList<ArrayList<Integer>> getTargets(int x, int y, AmazonsLocalBoard board) {
		ArrayList<ArrayList<Integer>> targets = new ArrayList<>();

		for (int dist = 1; dist < 11; dist++) {
			int up = y + dist;
			int down = y - dist;
			int right = x + dist;
			int left = x - dist;

			// Check vertically
			if (up <= 10 && isPathClear(x, y, x, up, board))
				targets.add(new ArrayList<>(Arrays.asList(x, up)));
			if (down >= 1 && isPathClear(x, y, x, down, board))
				targets.add(new ArrayList<>(Arrays.asList(x, down)));

			// Check horizontally
			if (right <= 10 && isPathClear(x, y, right, y, board))
				targets.add(new ArrayList<>(Arrays.asList(right, y)));
			if (left >= 1 && isPathClear(x, y, left, y, board))
				targets.add(new ArrayList<>(Arrays.asList(left, y)));

			// Check diagonally
			if (right <= 10 && up <= 10 && isPathClear(x, y, right, up, board))
				targets.add(new ArrayList<>(Arrays.asList(right, up)));
			if (right <= 10 && down >= 1 && isPathClear(x, y, right, down, board))
				targets.add(new ArrayList<>(Arrays.asList(right, down)));
			if (left >= 1 && up <= 10 && isPathClear(x, y, left, up, board))
				targets.add(new ArrayList<>(Arrays.asList(left, down)));
			if (left >= 1 && down >= 1 && isPathClear(x, y, left, down, board))
				targets.add(new ArrayList<>(Arrays.asList(left, down)));
		}

		return targets;
	}
}

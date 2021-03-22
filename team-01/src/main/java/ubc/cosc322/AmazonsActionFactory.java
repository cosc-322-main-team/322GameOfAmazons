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

			boolean isUpBlocked = false;
			if (!isUpBlocked) {
				if (up > 10 || board.getPositionValue(x, up) != 0) {
					isUpBlocked = true;
				} else {
					targets.add(new ArrayList<>(Arrays.asList(x, up)));
				}
			}

			boolean isDownBlocked = false;
			if (!isDownBlocked) {
				if (down < 1 || board.getPositionValue(x, down) != 0) {
					isDownBlocked = true;
				} else {
					targets.add(new ArrayList<>(Arrays.asList(x, down)));
				}
			}

			boolean isRightBlocked = false;
			if (!isRightBlocked) {
				if (right > 10 || board.getPositionValue(right, y) != 0) {
					isRightBlocked = true;
				} else {
					targets.add(new ArrayList<>(Arrays.asList(right, y)));
				}
			}

			boolean isLeftBlocked = false;
			if (!isLeftBlocked) {
				if (left < 1 || board.getPositionValue(left, y) != 0) {
					isLeftBlocked = true;
				} else {
					targets.add(new ArrayList<>(Arrays.asList(left, y)));
				}
			}

			boolean isRightUpBlocked = false;
			if (!isRightUpBlocked) {
				if (right > 10 || up > 10 || board.getPositionValue(right, up) != 0) {
					isRightUpBlocked = true;
				} else {
					targets.add(new ArrayList<>(Arrays.asList(right, up)));
				}
			}

			boolean isRightDownBlocked = false;
			if (!isRightDownBlocked) {
				if (right > 10 || down < 1 || board.getPositionValue(right, down) != 0) {
					isRightDownBlocked = true;
				} else {
					targets.add(new ArrayList<>(Arrays.asList(right, down)));
				}
			}

			boolean isLeftUpBlocked = false;
			if (!isLeftUpBlocked) {
				if (left < 1 || up > 10 || board.getPositionValue(left, up) != 0) {
					isLeftUpBlocked = true;
				} else {
					targets.add(new ArrayList<>(Arrays.asList(left, up)));
				}
			}

			boolean isLeftDownBlocked = false;
			if (!isLeftDownBlocked) {
				if (left < 1 || down < 1 || board.getPositionValue(left, down) != 0) {
					isLeftDownBlocked = true;
				} else {
					targets.add(new ArrayList<>(Arrays.asList(left, down)));
				}
			}
		}

		return targets;
	}
}

package ubc.cosc322;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class AmazonsActionFactory {
	public ArrayList<AmazonsAction> getActions(AmazonsLocalBoard board) {
		ArrayList<AmazonsAction> list = new ArrayList<>();
		// Double array list initialized to store queen current positions
		ArrayList<List<Integer>> allQueens = getAllQueenCurrents(board);

		while (!allQueens.isEmpty()) {
			// Iterating through the allQueens arrayList removing them one by one.
			List<Integer> queenCurrent = allQueens.remove(0);

			// Creating a double array list of all possible moves for the current queen.
			ArrayList<List<Integer>> allQueenTargets = getTargets(queenCurrent.get(0), queenCurrent.get(1), board);

			while (!allQueenTargets.isEmpty()) {
				// Iterating through all the possible moves and removing them one by one
				List<Integer> queenTarget = allQueenTargets.remove(0);

				// Creating a double array list of all possible arrow moves for the current queen target position
				ArrayList<List<Integer>> allArrowTargets = getTargets(queenTarget.get(0), queenTarget.get(1), board);

				// Add the queen's original position as a potential arrow target
				allArrowTargets.add(queenCurrent);

				while (!allArrowTargets.isEmpty()) {
					// Iterating through all the arrow target locations and removing them.
					List<Integer> arrowTarget = allArrowTargets.remove(0);

					// Adding the current arrow target, the queen target position, and the current queen to the list of AmazonsActions
					AmazonsAction newAction = new AmazonsAction(queenCurrent, queenTarget, arrowTarget);
					list.add(newAction);
				}
			}
		}
		return list;
	}

	private ArrayList<List<Integer>> getAllQueenCurrents(AmazonsLocalBoard board) {
		ArrayList<List<Integer>> queenCurrents = new ArrayList<>();
		// Iterating through the entire board finding each queen position.
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++) {
				List<Integer> position = Arrays.asList(i, j);
				// The queen position value can be 1 or 2 depending on if our queens are white or black.
				if (board.getPositionValue(position) == board.localPlayer) {
					queenCurrents.add(position);
				}
			}
		}
		return queenCurrents;
	}

	private ArrayList<List<Integer>> getTargets(int x, int y, AmazonsLocalBoard board) {
		ArrayList<List<Integer>> targets = new ArrayList<>();

		boolean isUpBlocked = false;
		boolean isDownBlocked = false;
		boolean isRightBlocked = false;
		boolean isLeftBlocked = false;
		boolean isRightUpBlocked = false;
		boolean isRightDownBlocked = false;
		boolean isLeftUpBlocked = false;
		boolean isLeftDownBlocked = false;

		for (int dist = 1; dist < 11; dist++) {
			int up = y + dist;
			int down = y - dist;
			int right = x + dist;
			int left = x - dist;

			if (!isUpBlocked) {
				if (up > 10 || board.getPositionValue(x, up) != 0) {
					isUpBlocked = true;
				} else {
					targets.add(Arrays.asList(x, up));
				}
			}

			if (!isDownBlocked) {
				if (down < 1 || board.getPositionValue(x, down) != 0) {
					isDownBlocked = true;
				} else {
					targets.add(Arrays.asList(x, down));
				}
			}

			if (!isRightBlocked) {
				if (right > 10 || board.getPositionValue(right, y) != 0) {
					isRightBlocked = true;
				} else {
					targets.add(Arrays.asList(right, y));
				}
			}

			if (!isLeftBlocked) {
				if (left < 1 || board.getPositionValue(left, y) != 0) {
					isLeftBlocked = true;
				} else {
					targets.add(Arrays.asList(left, y));
				}
			}

			if (!isRightUpBlocked) {
				if (right > 10 || up > 10 || board.getPositionValue(right, up) != 0) {
					isRightUpBlocked = true;
				} else {
					targets.add(Arrays.asList(right, up));
				}
			}

			if (!isRightDownBlocked) {
				if (right > 10 || down < 1 || board.getPositionValue(right, down) != 0) {
					isRightDownBlocked = true;
				} else {
					targets.add(Arrays.asList(right, down));
				}
			}

			if (!isLeftUpBlocked) {
				if (left < 1 || up > 10 || board.getPositionValue(left, up) != 0) {
					isLeftUpBlocked = true;
				} else {
					targets.add(Arrays.asList(left, up));
				}
			}

			if (!isLeftDownBlocked) {
				if (left < 1 || down < 1 || board.getPositionValue(left, down) != 0) {
					isLeftDownBlocked = true;
				} else {
					targets.add(Arrays.asList(left, down));
				}
			}
		}

		return targets;
	}
}

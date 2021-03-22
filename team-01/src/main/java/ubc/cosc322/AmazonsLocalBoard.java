package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AmazonsLocalBoard {
	/*
	 * ADVENTURERS BE WARNED: Gao stores the game state as a list of integers representing a 2D grid. But don't be fooled: this foul grid also includes 11 spaces of unused tiles at the start of the
	 * array, and 1 blasphemous unused tile at the start of each row. In fact, the unholy rituals required to access a specific index of this grid are so unspeakable that attempting to even calculate
	 * such an index requires divine intervention. Proceed with caution.
	 */

	private static final int TOTAL_LENGTH = 121;
	private static final int ROW_LENGTH = 11;

	public int localPlayer = -1;

	private ArrayList<Integer> state = new ArrayList<>();

	public ArrayList<Integer> getState() {
		return state;
	}

	public void setState(ArrayList<Integer> state) {
		this.state = state;
	}

	public void updateState(AmazonsAction action) {
		updateState(action.queenCurrent, action.queenTarget, action.arrowTarget);
	}

	public void updateState(List<Integer> queenCurrent, List<Integer> queenTarget, List<Integer> arrowTarget) {
		int playerColor = getPositionValue(queenCurrent);
		setPositionValue(queenCurrent, 0);
		setPositionValue(queenTarget, playerColor);
		setPositionValue(arrowTarget, 3);
	}

	public int getPositionValue(List<Integer> position) {
		return state.get(getIndex(position));
	}

	public int getPositionValue(int x, int y) {
		return state.get(getIndex(x, y));
	}

	public void setPositionValue(List<Integer> position, int value) {
		state.set(getIndex(position), value);
	}

	public void printState() {
		for (int i = 0; i < 10; i++) {
			// Oh Lord, please forgive my sins
			System.out.println(state.subList(i * ROW_LENGTH + 12, (i + 1) * ROW_LENGTH + 11));
		}
		System.out.println();
	}

	private int getIndex(List<Integer> position) {
		int row = position.get(0);
		int col = position.get(1);
		return getIndex(row, col);
	}

	private int getIndex(int x, int y) {
		return TOTAL_LENGTH - x * ROW_LENGTH + y;
	}

	/**
	 * Returns a deep copy of this board.
	 */
	public AmazonsLocalBoard copy() {
		AmazonsLocalBoard copy = new AmazonsLocalBoard();
		copy.localPlayer = localPlayer;
		copy.state = new ArrayList<>(state);
		return copy;
	}

	public int getOpponent() {
		return localPlayer == 2 ? 1 : 2;
	}
}

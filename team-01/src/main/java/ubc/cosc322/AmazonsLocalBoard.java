package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;
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
		printState();
	}

	public void updateState(ArrayList<Integer> queenCurrent, ArrayList<Integer> queenTarget, ArrayList<Integer> arrowTarget) {
		int playerColor = getPositionValue(queenCurrent);
		setPositionValue(queenCurrent, 0);
		setPositionValue(queenTarget, playerColor);
		setPositionValue(arrowTarget, 3);
		printState();
	}

	public int getPositionValue(ArrayList<Integer> position) {
		return state.get(getIndex(position));
	}

	public int getPositionValue(int x, int y) {
		return getPositionValue(new ArrayList<>(Arrays.asList(x, y)));
	}

	public void setPositionValue(ArrayList<Integer> position, int value) {
		state.set(getIndex(position), value);
	}

	public void printState() {
		for (int i = 0; i < 10; i++) {
			// Oh Lord, please forgive my sins
			System.out.println(state.subList(i * ROW_LENGTH + 12, (i + 1) * ROW_LENGTH + 11));
		}
		System.out.println();
	}

	private int getIndex(ArrayList<Integer> position) {
		int row = position.get(0);
		int col = position.get(1);
		return TOTAL_LENGTH - row * ROW_LENGTH + col;
	}

	public AmazonsLocalBoard copy() {
		AmazonsLocalBoard copy = new AmazonsLocalBoard();
		copy.localPlayer = localPlayer;
		copy.state = state.stream().collect(Collectors.toCollection(ArrayList::new));
		return copy;
	}
}

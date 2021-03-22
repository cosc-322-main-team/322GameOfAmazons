package ubc.cosc322;

import java.util.ArrayList;

public class RandomPlayer extends LocalPlayer {
	public RandomPlayer() {
		super("RANDOM_PLAYER", "RANDOM_PASS");
	}

	@Override
	protected void move() {
		ArrayList<AmazonsAction> actions = getAvailableActions();
		int randomIndex = (int) (Math.random() * actions.size());

		ArrayList<Integer> queenCurrent = actions.get(randomIndex).queenCurrent;
		ArrayList<Integer> queenTarget = actions.get(randomIndex).queenTarget;
		ArrayList<Integer> arrowTarget = actions.get(randomIndex).arrowTarget;

		sendMove(queenCurrent, queenTarget, arrowTarget);
	}
}

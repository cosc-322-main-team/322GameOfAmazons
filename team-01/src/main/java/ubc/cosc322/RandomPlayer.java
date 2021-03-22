package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;

public class RandomPlayer extends LocalPlayer {
	public RandomPlayer() {
		super("RANDOM_PLAYER", "RANDOM_PASS");
	}

	@Override
	protected void move() {
		ArrayList<AmazonsAction> actions = getAvailableActions();
		int randomIndex = (int) (Math.random() * actions.size());

		List<Integer> queenCurrent = actions.get(randomIndex).queenCurrent;
		List<Integer> queenTarget = actions.get(randomIndex).queenTarget;
		List<Integer> arrowTarget = actions.get(randomIndex).arrowTarget;

		sendMove(queenCurrent, queenTarget, arrowTarget);
	}
}

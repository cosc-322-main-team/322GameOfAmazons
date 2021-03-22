package ubc.cosc322;

import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.amazons.HumanPlayer;

public class Main {
	/**
	 * @param args for username and password (any string is allowed)
	 */
	public static void main(String[] args) {
		// Uncomment one of the following lines based on the desired player type:
		HumanPlayer player = new HumanPlayer();
		// Spectator player = new Spectator(args[0], args[1]);
		// RandomPlayer player = new RandomPlayer();
		// MonteCarloPlayer player = new MonteCarloPlayer();

		if (player.getGameGUI() == null) {
			player.Go();
		} else {
			BaseGameGUI.sys_setup();
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					player.Go();
				}
			});
		}
	}
}

package ubc.cosc322;

import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;

import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GamePlayer;

public class RandomPlayer extends GamePlayer {
  private final String userName = "RANDOM_PLAYER";
  private final String password = "RANDOM_PASS";

  private ArrayList<Integer> gameState = new ArrayList<>();
  private GameClient gameClient = null;
  private BaseGameGUI gameGUI = null;

  public RandomPlayer() {
    gameGUI = new BaseGameGUI(this);
  }

  @Override
  public void onLogin() {
    // userName = gameClient.getUserName();
    if (gameGUI != null) {
      gameGUI.setRoomInformation(gameClient.getRoomList());
    }
  }

  @Override
  public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    if (messageType.equals("cosc322.game-state.board")) {
      gameState = (ArrayList<Integer>) msgDetails.get("game-state");
      gameGUI.setGameState(gameState);
    } else if (messageType.equals("cosc322.game-action.move")) {
      ArrayList<Integer> queenCurrent = (ArrayList<Integer>) msgDetails.get("queen-position-current");
      ArrayList<Integer> queenNew = (ArrayList<Integer>) msgDetails.get("queen-position-next");
      ArrayList<Integer> arrow = (ArrayList<Integer>) msgDetails.get("arrow-position");

      gameGUI.updateGameState(queenCurrent, queenNew, arrow);

      // Move the player
      moveRandom();
    } else if (messageType.equals("cosc322.game-action.start")) {
      // Not handling GameMessage.Game_Action_Start yet.
    }
    return true;
  }

  private void moveRandom() {
    // Board Format: [Row, Column], bottom left corner is [1, 1]

    ArrayList<Integer> myQueenCurrent = new ArrayList<Integer>(Arrays.asList(10, 4));
    ArrayList<Integer> myQueenTarget = new ArrayList<Integer>(Arrays.asList(7, 4));
    ArrayList<Integer> myArrowTarget = new ArrayList<Integer>(Arrays.asList(7, 7));

    gameClient.sendMoveMessage(myQueenCurrent, myQueenTarget, myArrowTarget);
    gameGUI.updateGameState(myQueenCurrent, myQueenTarget, myArrowTarget);
  }

  @Override
  public String userName() {
    return userName;
  }

  @Override
  public GameClient getGameClient() {
    return gameClient;
  }

  @Override
  public BaseGameGUI getGameGUI() {
    return gameGUI;
  }

  @Override
  public void connect() {
    gameClient = new GameClient(userName, password, this);
  }
}

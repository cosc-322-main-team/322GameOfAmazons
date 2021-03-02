package ubc.cosc322;

import java.util.Map;
import java.util.ArrayList;

import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GamePlayer;

public class RandomPlayer extends GamePlayer {
  private final String userName = "RANDOM_PLAYER";
  private final String password = "RANDOM_PASS";

  private AmazonsLocalBoard board = new AmazonsLocalBoard();
  private GameClient gameClient = null;
  private BaseGameGUI gameGUI = null;

  private AmazonsActionFactory actionFactory = new AmazonsActionFactory();

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
    System.out.println(msgDetails);
    if (messageType.equals("cosc322.game-state.board")) {
      ArrayList<Integer> gameState = (ArrayList<Integer>) msgDetails.get("game-state");
      board.setState(gameState);
      gameGUI.setGameState(gameState);
    } else if (messageType.equals("cosc322.game-action.move")) {
      ArrayList<Integer> queenCurrent = (ArrayList<Integer>) msgDetails.get("queen-position-current");
      ArrayList<Integer> queenTarget = (ArrayList<Integer>) msgDetails.get("queen-position-next");
      ArrayList<Integer> arrowTarget = (ArrayList<Integer>) msgDetails.get("arrow-position");

      board.updateState(queenCurrent, queenTarget, arrowTarget);
      gameGUI.updateGameState(queenCurrent, queenTarget, arrowTarget);

      moveRandom();
    } else if (messageType.equals("cosc322.game-action.start")) {
      // Not handling GameMessage.Game_Action_Start yet.
    }
    return true;
  }

  private void moveRandom() {
    // Board Format: [Row, Column], bottom left corner is [1, 1]

    AmazonsAction[] actions = actionFactory.getActions(board.getState());
    int randomIndex = (int) (Math.random() * (actions.length + 1));

    ArrayList<Integer> queenCurrent = actions[randomIndex].queenCurrent;
    ArrayList<Integer> queenTarget = actions[randomIndex].queenTarget;
    ArrayList<Integer> arrowTarget = actions[randomIndex].arrowTarget;

    board.updateState(queenCurrent, queenTarget, arrowTarget);
    gameGUI.updateGameState(queenCurrent, queenTarget, arrowTarget);

    gameClient.sendMoveMessage(queenCurrent, queenTarget, arrowTarget);
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

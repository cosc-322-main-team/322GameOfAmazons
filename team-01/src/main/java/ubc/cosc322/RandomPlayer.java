package ubc.cosc322;

import java.util.Map;
import java.util.ArrayList;

import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GamePlayer;

public class RandomPlayer extends GamePlayer {
  private final String userName = "RANDOM_PLAYER";
  private final String password = "RANDOM_PASS";

  private ArrayList<Integer> gameState = new ArrayList<>();
  private GameClient gameClient = null;
  private BaseGameGUI gameGUI = null;

  @Override
  public void onLogin() {
    // userName = gameClient.getUserName();
    if (gameGUI != null) {
      gameGUI.setRoomInformation(gameClient.getRoomList());
    }
  }

  @Override
  public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    if (messageType.equals("GameMessage.GAME_STATE_BOARD")) {
      gameState = (ArrayList<Integer>) msgDetails.get("AmazonsGameMessage.GAME_STATE");
      gameGUI.setGameState(gameState);
    } else if (messageType.equals("GameMessage.GAME_ACTION_MOVE")) {
      /*
       * In a game player, upon receiving this message about your opponent's move, you
       * will also need to calculate your move and send your move to the server using
       * the method GameClient.sendMoveMessage(...) (these are the core tasks of this
       * project you will have to by the middle of March) - Gao
       */
      ArrayList<Integer> queenCurrent = (ArrayList<Integer>) msgDetails.get("AmazonsGameMessage.QUEEN_POS_CURR");
      ArrayList<Integer> queenNew = (ArrayList<Integer>) msgDetails.get("AmazonsGameMessage.QUEEN_POS_NEXT");
      ArrayList<Integer> arrow = (ArrayList<Integer>) msgDetails.get("AmazonsGameMessage.ARROW_POS");

      gameGUI.updateGameState(queenCurrent, queenNew, arrow);

      // TODO: implement move
      // gameClient.sendMoveMessage(queenCurrent, queenPosNew, arrowPos);
    }
    // Not handling GameMessage.Game_Action_Start yet.
    return true;
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

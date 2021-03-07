package ubc.cosc322;

import java.util.Map;
import java.util.ArrayList;

import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GamePlayer;

public abstract class LocalPlayer extends GamePlayer {
  private String username;
  private String password;

  private GameClient gameClient = null;
  private BaseGameGUI gameGUI = null;

  private AmazonsLocalBoard board = new AmazonsLocalBoard();
  private AmazonsActionFactory actionFactory = new AmazonsActionFactory();

  public LocalPlayer(String username, String password) {
    this.username = username;
    this.password = password;
    gameGUI = new BaseGameGUI(this);
  }

  // ===== LocalPlayer API ===== //

  /** Called when the player receives a move message from the server. */
  protected abstract void onMoveReceived();

  /** Returns the list of actions that can be taken from the current state. */
  protected ArrayList<AmazonsAction> getAvailableActions() {
    return actionFactory.getActions(board);
  }

  /** Used to send a move to the server and update the local state accordingly. */
  protected void sendMove(ArrayList<Integer> queenCurrent, ArrayList<Integer> queenTarget, ArrayList<Integer> arrowTarget) {
    board.updateState(queenCurrent, queenTarget, arrowTarget);
    gameGUI.updateGameState(queenCurrent, queenTarget, arrowTarget);
    gameClient.sendMoveMessage(queenCurrent, queenTarget, arrowTarget);
  }

  // ===== GamePlayer Overrides ===== //

  @Override
  public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    switch (messageType) {
      case "cosc322.game-state.board":
        ArrayList<Integer> gameState = getMessageByTag(msgDetails, "game-state");

        board.setState(gameState);
        gameGUI.setGameState(gameState);

        break;

      case "cosc322.game-action.move":
        ArrayList<Integer> queenCurrent = getMessageByTag(msgDetails, "queen-position-current");
        ArrayList<Integer> queenTarget = getMessageByTag(msgDetails, "queen-position-next");
        ArrayList<Integer> arrowTarget = getMessageByTag(msgDetails, "arrow-position");

        board.updateState(queenCurrent, queenTarget, arrowTarget);
        gameGUI.updateGameState(queenCurrent, queenTarget, arrowTarget);

        onMoveReceived();

        break;

      case "cosc322.game-action.start":
        String whitePlayer = getMessageByTag(msgDetails, "player-white");

        board.localPlayer = whitePlayer.equals(this.username) ? 1 : 2;

        break;
    }

    return true;
  }

  @Override
  public void onLogin() {
    if (gameGUI != null) {
      gameGUI.setRoomInformation(gameClient.getRoomList());
    }
  }

  @Override
  public String userName() {
    return username;
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
    gameClient = new GameClient(username, password, this);
  }

  // ===== Helper Methods ===== //

  @SuppressWarnings("unchecked") // Yes, Java gives no proper way to handle this warning. You just have to suppress it.
  private <T extends Object> T getMessageByTag(Map<String, Object> messages, String tag) {
    return (T) messages.get(tag);
  }
}
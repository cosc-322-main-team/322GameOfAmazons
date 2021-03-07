package ubc.cosc322;

public class Spectator extends LocalPlayer {
  public Spectator(String username, String password) {
    super(username, password);
  }

  @Override
  protected void onMoveReceived() {
    // Spectators do nothing when receiving a move
  }
}

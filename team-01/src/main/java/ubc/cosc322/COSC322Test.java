
package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GamePlayer;

/**
 * An example illustrating how to implement a GamePlayer
 *
 * @author Yong Gao (yong.gao@ubc.ca)
 * Jan 5, 2021
 */
public class COSC322Test extends GamePlayer {

    private GameClient gameClient = null;
    private BaseGameGUI gamegui = null;

    private String userName = null;
    private String passwd = null;

    /**
     * The main method
     *
     * @param args for name and passwd (current, any string would work)
     */
    public static void main(String[] args) {
        COSC322Test player = new COSC322Test(args[0], args[1]);

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

    /**
     * Any name and passwd
     *
     * @param userName
     * @param passwd
     */
    public COSC322Test(String userName, String passwd) {
        this.userName = userName;
        this.passwd = passwd;

        //To make a GUI-based player, create an instance of BaseGameGUI
        //and implement the method getGameGUI() accordingly
    }


    @Override
    public void onLogin() {
        // Warmup-02
        userName = gameClient.getUserName();
        if (gamegui != null) {
            gamegui.setRoomInformation(gameClient.getRoomList());
        }

    }

    @Override
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    	/*
    	This method will be called by the GameClient when it receives a game-related message
    	from the server.
		System.out.println("The message details are "+ msgDetails);
    	For a detailed description of the message types and format,
    	see the method GamePlayer.handleGameMessage() in the game-client-api document.
    	- Gao
		*/
        if (messageType.equals("GameMessage.GAME_STATE_BOARD")) {
            ArrayList<Integer> gameState = (ArrayList<Integer>) msgDetails.get("AmazonsGameMessage.GAME_STATE");
            gamegui.setGameState(gameState);
        } else if (messageType.equals("GameMessage.GAME_ACTION_MOVE")) {
			/*
			In a game player, upon receiving this message about your opponent's move, you will also need to calculate
			your move and send your move to the server using the method GameClient.sendMoveMessage(...) (these are the
			core tasks of this project you will have to by the middle of March)
			- Gao
		 	*/
            ArrayList<Integer> queenCurrent = (ArrayList<Integer>) msgDetails.get("AmazonsGameMessage.QUEEN_POS_CURR");
            ArrayList<Integer> queenNew = (ArrayList<Integer>) msgDetails.get("AmazonsGameMessage.QUEEN_POS_CURR");
            ArrayList<Integer> arrow = (ArrayList<Integer>) msgDetails.get("AmazonsGameMessage.QUEEN_POS_CURR");

            gamegui.updateGameState(queenCurrent, queenNew, arrow);
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
        // TODO Auto-generated method stub
        return this.gameClient;
    }

    @Override
    public BaseGameGUI getGameGUI() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void connect() {
        // TODO Auto-generated method stub
        gameClient = new GameClient(userName, passwd, this);
    }


}//end of class

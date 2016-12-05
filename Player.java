package Yatzy;

import javax.swing.JOptionPane;

/**
 *
 * @author Maxie
 */
public class Player {
    
    private String playerName;
    
    public Player() {

    }
    
    public String getPlayerName() {
        return playerName;
    }

    /*
    prompt input from player to set a name
    currently only does A-Z
    prints error if input is out of range
    */
    public void setPlayerName() {
        String message = "Enter playername";
        do {
        this.playerName = JOptionPane.showInputDialog(message);
        message = "<html><b style='color:red'>Enter playername:</b><br>"
                +"Use a-z";
        } while(playerName != null && !playerName.matches("[a-zA-Z]+"));
    }
    
    public String toString() {
        return playerName;
    }
    
}

package Yatzy;

import javax.swing.JOptionPane;

/**
 * Class that creates a player
 *
 * @author Max Nelson
 * @since v0.2
 */
public class Player {

    private String playerName;

    /**
     * Constructor that creates an empty object Player
     *
     */
    public Player() {

    }

    /**
     * Method to return the name of the player
     * @return The playername specified in setPlayerName()
     * @see #setPlayerName()
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * prompt input from player to set a name currently only does A-Z prints
     * error if input is out of range.
     */
    public void setPlayerName() {
        String message = "Enter playername";
        do {
            this.playerName = JOptionPane.showInputDialog(message);
            message = "<html><b style='color:red'>Enter playername:</b><br>"
                    + "Use a-z";
        } while (playerName != null && !playerName.matches("[a-zA-Z]+"));
    }

    /**
     * Method for printing out a playerName
     * @return A string with the player name.
     */
    public String toString() {
        return playerName;
    }

}

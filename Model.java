package Yatzy;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Created by bartek on 28/10/16. 
 * Modified by Max on 1/12/16
 */
public class Model {

    View view;
    PlayerGUI playerGUI;
    private int numberOfPlayers;
    private ArrayList<Player> playerList = new ArrayList<>(numberOfPlayers);

    public Model(View view, PlayerGUI playerGUI) {

        this.view = view;
        this.playerGUI = playerGUI;

    }

    /**
     * Prompts the user to input how many will play the game. Keeps asking until
     * a valid number 1-6 is entered
     */
    public void setNumberOfPlayers() {
        String message = "Enter number of players: 1-6";
        int choice;
        boolean keepGoing = true;
        while (keepGoing) {
            do {
                try {
                    numberOfPlayers = Integer.parseInt(JOptionPane.showInputDialog(
                            null, message, "Enter number of Players",
                            JOptionPane.OK_OPTION));
                } catch (NumberFormatException | NullPointerException e) {

                }
                message = "<html><b style='color:red'>Invalid number of players:</b><br>"
                        + "Enter 1-6";
                System.out.println("input was: " + numberOfPlayers);

            } while (numberOfPlayers > 6 || numberOfPlayers < 1
                    && keepGoing);

            choice = JOptionPane.showConfirmDialog(null,
                    ("Starting game with : "
                    + numberOfPlayers + " players\nIs that correct?"),
                    "Confirm number of players",
                    JOptionPane.YES_NO_OPTION);
            switch (choice) {
                case JOptionPane.YES_OPTION:
                    keepGoing = false;
                    break;
                case JOptionPane.NO_OPTION:
                    keepGoing = true;
                    numberOfPlayers = 0;
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * getter for int numberOfPlayers
     *
     * @return Specified number of players set in setNumberOfPlayers()
     * @see #setNumberOfPlayers() 
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Method that creates a list of players depending on number of players
     * specified
     *
     * @param numberOfPlayers  The number specified in setNumberOfPlayers()
     * @see #setNumberOfPlayers() 
     */
    public void fillPlayerList(int numberOfPlayers) {
        for (int i = 0; i < numberOfPlayers; i++) {
            Player p;
            p = new Player();
            p.setPlayerName();
            playerList.add(p);
            System.out.println("Added player: " + p.getPlayerName());
        }
        int playerNumber = 1;
        for (Player p : playerList) {
            System.out.println("Player " + playerNumber++ + ": " + p);
        }
    }

    /**
     * Method for returning the playerList for use
     *
     * @return ArrayList playerList containing the number of players created
     * in fillPlayerList()
     * @see #fillPlayerList(int) 
     */
    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void showValueDiceButton() {
        // Show value if button is enabled
        for (int i = 0; i < playerGUI.dices.length; i++) {
            if (view.diceButtons[i].getModel().isEnabled()) {
                view.getDiceButton(i).setText(String.valueOf(playerGUI.dices[i].diceValue));
            }
        }
    }

    public void showDiceValue(int i) {
        view.getLabel().setText(String.valueOf(playerGUI.dices[i].diceValue));
    }

    public void enableDiceButtons() {
        for (int i = 0; i < 5; i++) {
            view.diceButtons[i].getModel().setEnabled(true);
            toggleDice(i);
        }
    }

    public void saveDiceValues() {
        for (int i = 0; i < playerGUI.dices.length; i++) {
            if (view.diceButtons[i].getModel().isSelected() && view.diceButtons[i].getModel().isEnabled()) {
                // Add value of cast dice to vector that contains saved dice values
                playerGUI.savedValuesDiceCast.add(playerGUI.dices[i].diceValue);
                // Disabled selected buttons
                view.diceButtons[i].getModel().setEnabled(false);
                // Set dice value to 0
                playerGUI.dices[i].diceValue = 0;
            }
        }

        // Play again (withut creating dices)
        playerGUI.castDices();
        showValueDiceButton();
    }

    // Chosing where to add score
    public void setScore(int m) {
        // Disabled all scoreButtons
        for (int i = 0; i < view.scoreButtons.length; i++) {
            view.scoreButtons[i].getModel().setEnabled(false);
        }
        // Save pokerhand
        playerGUI.savedPokerHands[m] = playerGUI.pokerHands[m];
        // Display chosen value
        view.getLabel().setText(String.valueOf(playerGUI.pokerHands[m]));
    }

    public void showScore() {
        for (int i = 0; i < playerGUI.savedPokerHands.length; i++) {
            System.out.println("Hand " + i + 1 + ": " + playerGUI.savedPokerHands[i]);
        }

        // Play again (new dices)
        playerGUI.createDices();
        playerGUI.savedValuesDiceCast.removeAllElements();
        enableDiceButtons();
        playerGUI.castDices();
        showValueDiceButton();

    }

    // Display score for each number or poker hand (in boxes)
    public void displayScore() {
        for (int i = 0; i < playerGUI.savedPokerHands.length; i++) {
            if (playerGUI.savedPokerHands[i] == 0) {
                view.boxes[i][1].setText("");
            } else {
                view.boxes[i][1].setText(String.valueOf(playerGUI.pokerHands[i]));
            }
        }
    }

    public void displayNumbersScore() {
        playerGUI.numbersScore();
        view.numbersTotalBoxes[0][1].setText(String.valueOf(playerGUI.numbersScore));
    }

    public void displayBonus() {
        playerGUI.bonus();
        view.numbersTotalBoxes[1][1].setText(String.valueOf(playerGUI.bonus));
    }

    public void displayNumbersTotalScore() {
        playerGUI.numbersTotalScore();
        view.numbersTotalBoxes[2][1].setText(String.valueOf(playerGUI.numbersTotalScore));
    }

    public void displayPokerTotalScore() {
        playerGUI.pokerScore();
        view.pokerTotalBoxes[0][1].setText(String.valueOf(playerGUI.pokerScore));
    }

    public void displayGrandTotal() {
        playerGUI.grandTotal();
        view.pokerTotalBoxes[1][1].setText(String.valueOf(playerGUI.grandTotal));
    }

    public void displayAllScores() {
        displayNumbersScore();
        displayBonus();
        displayNumbersTotalScore();
        displayPokerTotalScore();
        displayGrandTotal();
    }

    public boolean isAvailablePokerHand(int m) {
        playerGUI.isAvailablePokerHand = false;
        if (playerGUI.savedPokerHands[m] == 0) {
            playerGUI.isAvailablePokerHand = true;
        }
        return playerGUI.isAvailablePokerHand;
    }

    public void isPoker() {

        //Check if ones
        if (playerGUI.isOne() && isAvailablePokerHand(0)) {
            // Enabled scoreButton if onePair
            view.scoreButtons[0].getModel().setEnabled(true);
            System.out.println("Is ones: " + playerGUI.numberOnes);
        }
        //Check if twos
        if (playerGUI.isTwo() && isAvailablePokerHand(1)) {
            // Enabled scoreButton if onePair
            view.scoreButtons[1].getModel().setEnabled(true);
            System.out.println("Is twos: " + playerGUI.numberTwos);
        }
        //Check if threes
        if (playerGUI.isThree() && isAvailablePokerHand(2)) {
            // Enabled scoreButton if onePair
            view.scoreButtons[2].getModel().setEnabled(true);
            System.out.println("Is threes: " + playerGUI.numberThrees);
        }
        //Check if fours
        if (playerGUI.isFour() && isAvailablePokerHand(3)) {
            // Enabled scoreButton if onePair
            view.scoreButtons[3].getModel().setEnabled(true);
            System.out.println("Is fours: " + playerGUI.numberFours);
        }
        //Check if fives
        if (playerGUI.isFive() && isAvailablePokerHand(4)) {
            // Enabled scoreButton if onePair
            view.scoreButtons[4].getModel().setEnabled(true);
            System.out.println("Is fives: " + playerGUI.numberFives);
        }
        //Check if sixes
        if (playerGUI.isSix() && isAvailablePokerHand(5)) {
            // Enabled scoreButton if onePair
            view.scoreButtons[5].getModel().setEnabled(true);
            System.out.println("Is sixes: " + playerGUI.numberSixes);
        }
        //Check if one pair
        if (playerGUI.isOnePair() && isAvailablePokerHand(6)) {
            // Enabled scoreButton if onePair
            view.scoreButtons[6].getModel().setEnabled(true);
            System.out.println("Is one pair: " + playerGUI.onePair);
        }
        if (playerGUI.isTwoPair() && isAvailablePokerHand(7)) {
            // Enabled scoreButton if twoPair
            view.scoreButtons[7].getModel().setEnabled(true);
            System.out.println("Is two pair: " + playerGUI.twoPair);
        }
        //Check if 3-kind
        if (playerGUI.isThreeOfAKind() && isAvailablePokerHand(8)) {
            // Enabled scoreButton if twoPair
            view.scoreButtons[8].getModel().setEnabled(true);
            System.out.println("Is 3-kind: " + playerGUI.threeOfAKind);
        }
        //Check if 4-kind
        if (playerGUI.isFourOfAKind() && isAvailablePokerHand(9)) {
            // Enabled scoreButton if twoPair
            view.scoreButtons[9].getModel().setEnabled(true);
            System.out.println("Is 4-kind: " + playerGUI.fourOfAKind);
        }
        //Check if small straight
        if (playerGUI.isSmallStraight() && isAvailablePokerHand(10)) {
            // Enabled scoreButton if twoPair
            view.scoreButtons[10].getModel().setEnabled(true);
            System.out.println("Is small straight: " + playerGUI.smallStraight);
        }
        //Check if large straight
        if (playerGUI.isLargeStraight() && isAvailablePokerHand(11)) {
            // Enabled scoreButton if twoPair
            view.scoreButtons[11].getModel().setEnabled(true);
            System.out.println("Is large straight: " + playerGUI.largeStraight);
        }
        //Check if full house
        if (playerGUI.isFullHouse() && isAvailablePokerHand(12)) {
            // Enabled scoreButton if twoPair
            view.scoreButtons[12].getModel().setEnabled(true);
            System.out.println("Is Full House: " + playerGUI.fullHouse);
        }
        //Check if chance
        if (playerGUI.isChance() && isAvailablePokerHand(13)) {
            // Enabled scoreButton if twoPair
            view.scoreButtons[13].getModel().setEnabled(true);
            System.out.println("Is chance-kind: " + playerGUI.chance);
        }
        //Check if 4-yatzy
        if (playerGUI.isYatzy() && isAvailablePokerHand(14)) {
            // Enabled scoreButton if twoPair
            view.scoreButtons[14].getModel().setEnabled(true);
            System.out.println("Is yatzy: " + playerGUI.yatzy);
        }
    }

    public void showSavedDiceValues() {
        view.getLabel().setText(String.valueOf(playerGUI.savedValuesDiceCast));
        System.out.println("Saved values: " + playerGUI.savedValuesDiceCast);
    }

    public void toggleDice(int m) {

        // If selected. Set selected to false
        if (view.diceButtons[m].getModel().isSelected()) {
            view.diceButtons[m].setFont(new Font("Helvetica", Font.PLAIN, 50));
            view.diceButtons[m].getModel().setSelected(false);
        } // If not selected. Set selected to true
        else if (!view.diceButtons[m].getModel().isSelected()) {
            view.diceButtons[m].setFont(new Font("Helvetica", Font.BOLD, 50));
            view.diceButtons[m].getModel().setSelected(true);
        }
    }

}

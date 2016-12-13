package Yatzy;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by bartek on 28/10/16.
 */
public class Model {

    View view;
    Score score;
    Player currentPlayer;
    int counter = 0;
    int round;
    boolean forcedPlay;

    /** Max variabler **/
    private int numberOfPlayers;
    private ArrayList<Player> playerList = new ArrayList<>(numberOfPlayers);
    /****/

    public Model(View view) {
        this.view = view;
        Score score = new Score(currentPlayer);
        this.score = score;
        newGame();
    }

    public void newGame() {
        emptyPlayerList();
        setNumberOfPlayers();
        fillPlayerList(getNumberOfPlayers());
        playMode();
        removePlayerScoreTables();
        view.createBoxes(getNumberOfPlayers(), getPlayerList());
        // Set first player to current player (in Model and Score)
        setPlayer(0);
        currentPlayer.createDices();
        // If currenRoll = 0, then buttons are disabled
        showValueDiceButtons();
    }


    public int newTurn() {
        if(counter < numberOfPlayers - 1) {
            counter++;
        }
        else {
            counter = 0;
        }
        return counter;
    }
    
    public void setPlayer(int i) {

        // Set player in Model
        currentPlayer = playerList.get(i);
        // Set player in Score
        score.currentPlayer = playerList.get(i);
    }

    public void checkPlayer() {
        round = 0;
        do {
            newTurn();
            setPlayer(counter);
            System.out.println("Changed player: " + currentPlayer.getPlayerName());
            pokerHandsToBeMade();
            round++;
            System.out.println("Round: " + round);
        }
        while(currentPlayer.pokerHandsToBeMade == false && round < numberOfPlayers);
        System.out.println("Playing..");
    }

    public void removePlayerScoreTables() {
        view.centerE.removeAll();
    }


    public void showValueDiceButtons() {
        // Show value if button is enabled
        if(currentPlayer.currentRoll == 0) {
            view.zeroButton.getModel().setEnabled(false);
            for (int i = 0; i < currentPlayer.dices.length; i++) {
                view.diceButtons[i].getModel().setEnabled(false);
                view.diceButtons[i].setFont(new Font("Helvetica", Font.PLAIN, 50));
                view.getDiceButton(i).setText(String.valueOf(currentPlayer.dices[i].defaultValue));
            }
        }
        else {
            for (int i = 0; i < currentPlayer.dices.length; i++) {
                if (view.diceButtons[i].getModel().isEnabled() && !view.diceButtons[i].getModel().isSelected()) {
                    view.getDiceButton(i).setText(String.valueOf(currentPlayer.dices[i].castValue));
                }
            }
            System.out.println("Show values: " + currentPlayer.savedValuesDiceCast);
        }
    }

    public void showDiceValue(int i) {
        view.getLabel().setText(String.valueOf(currentPlayer.dices[i].castValue));
    }

    public void enableDiceButtons() {
        for (int i = 0; i < 5; i++) {
            view.diceButtons[i].getModel().setEnabled(true);
            view.diceButtons[i].getModel().setSelected(false);
        }
    }


    public void copyMarkedArrayToList() {
        if(currentPlayer.markedDices != null) {
            for (int i = 0; i < currentPlayer.markedDicesArray.length; i++) {
                if (currentPlayer.markedDicesArray[i] != 0) {
                    currentPlayer.markedDices.add(currentPlayer.markedDicesArray[i]);
                }
            }
        }
    }

    public void addDiceValues() {
        if(currentPlayer.dices.length > 0) {
            for (int i = 0; i < currentPlayer.dices.length; i++) {
                if(currentPlayer.dices[i].castValue != 0) {
                    currentPlayer.savedValuesDiceCast.add(currentPlayer.dices[i].castValue);
                }
            }
        }
        if(currentPlayer.markedDices != null) {
            for (int i = 0; i < currentPlayer.markedDices.size(); i++) {
                currentPlayer.savedValuesDiceCast.add(currentPlayer.markedDices.get(i));
            }
        }
    }

    public void emptySavedValuesDiceCast() {
        currentPlayer.savedValuesDiceCast.removeAllElements();
    }

    public void emptyMarkedDices() {
        currentPlayer.markedDices.removeAllElements();
    }

    public void emptyMarkedDicesArray() {
        for(int i = 0; i < currentPlayer.markedDicesArray.length; i++) {
            currentPlayer.markedDicesArray[i] = 0;
        }
    }

    public void emptyPokerHands() {
        for(int i = 0; i < currentPlayer.pokerHands.length; i++) {
            currentPlayer.pokerHands[i] = 0;
        }
        currentPlayer.numberOnes = 0;
        currentPlayer.numberTwos = 0;
        currentPlayer.numberThrees = 0;
        currentPlayer.numberFours = 0;
        currentPlayer.numberFives = 0;
        currentPlayer.numberSixes = 0;
        currentPlayer.onePair = 0;
        currentPlayer.twoPair = 0;
        currentPlayer.threeOfAKind = 0;
        currentPlayer.fourOfAKind = 0;
        currentPlayer.smallStraight = 0;
        currentPlayer.largeStraight = 0;
        currentPlayer.chance = 0;
        currentPlayer.yatzy = 0;
    }

    public void resetScoreButtons() {
        for(int i = 0; i < view.scoreButtons.length; i++) {
            view.scoreButtons[i].getModel().setEnabled(false);
        }
    }

    public void roll() {
        // Rolling dices
        // Toggle Zero button (when currentRoll == 0)
        toggleZeroButton();
        currentPlayer.currentRoll++;
        System.out.println("Current roll: " + currentPlayer.currentRoll);
        if(currentPlayer.currentRoll == 1) {
            resetScoreButtons();
            emptySavedValuesDiceCast();
            emptyMarkedDices();
            emptyMarkedDicesArray();
            enableDiceButtons();
            System.out.println("Enabled dice buttons");
            currentPlayer.castDices();
            showValueDiceButtons();
            addDiceValues();
            playedTop();
            isPoker();
            madeHandsAvailableForSave();
        }
        if(currentPlayer.currentRoll == 2 || currentPlayer.currentRoll == 3) {
            emptyPokerHands();
            resetScoreButtons();
            emptySavedValuesDiceCast();
            emptyMarkedDices();
            currentPlayer.castDices();
            showValueDiceButtons();
            copyMarkedArrayToList();
            addDiceValues();
            System.out.println("Saved values: " + currentPlayer.savedValuesDiceCast);
            playedTop();
            isPoker();
            madeHandsAvailableForSave();
            if(currentPlayer.currentRoll == 3) {
                toggleOkButton();
            }
        }
    }

    public boolean pokerHandsToBeMade() {
        // Check if player still has poker hands to make
        currentPlayer.pokerHandsToBeMade = false;
        for(int i = 0; i < currentPlayer.savedPokerHands.length; i++) {
            if(currentPlayer.savedPokerHands[i] == 100) {
                currentPlayer.pokerHandsToBeMade = true;
                break;
            }
        }
        return currentPlayer.pokerHandsToBeMade;
    }

    // Check if made hands are available to save (= slots for hands are empty)
    public boolean madeHandsAvailableForSave() {
        currentPlayer.madeHandsAvailableForSave = false;
        for(int i = 0; i < currentPlayer.pokerHands.length; i++) {
           if(currentPlayer.pokerHands[i] != 0 && currentPlayer.savedPokerHands[i] == 100) {
               currentPlayer.madeHandsAvailableForSave = true;
               break;
           }
       }
       return currentPlayer.madeHandsAvailableForSave;
    }

    public void setHandsAvailableForZero() {
        toggleOkButton();
        toggleZeroButton();
        System.out.print("Set hands to zero!");
        // Disable all score buttons
        for(int i = 0; i < view.scoreButtons.length; i++) {
            view.scoreButtons[i].getModel().setEnabled(false);
            System.out.println("Disable button");
        }
        // Enable score buttons for which poker hands have not been set
        for(int i = 0; i < currentPlayer.pokerHands.length; i++) {
            if(currentPlayer.savedPokerHands[i] == 100) {
                currentPlayer.pokerHands[i] = 0;
                view.scoreButtons[i].getModel().setEnabled(true);
                System.out.println("Setting hand to 0: " + i);
            }
        }
    }


    public boolean isAvailablePokerHand(int m) {
        currentPlayer.isAvailablePokerHand = false;
        if(currentPlayer.savedPokerHands[m] == 100) {
            currentPlayer.isAvailablePokerHand = true;
        }
        return currentPlayer.isAvailablePokerHand;
    }

    public void toggleOkButton() {
        if(view.rollButton.getModel().isEnabled()) {
            view.rollButton.setEnabled(false);
        }
        else {
            view.rollButton.setEnabled(true);
        }
    }

    public void toggleZeroButton() {
        if(view.zeroButton.getModel().isEnabled()) {
            view.zeroButton.setEnabled(false);
        }
        else {
            view.zeroButton.setEnabled(true);
        }
    }

    public void changePlayer() {
        // Empty temporary poker hands (which have been saved) for current player
        emptyPokerHands();

        // Check player if hands still can be made, go to next player otherwise
        if(numberOfPlayers > 1) {
            checkPlayer();
        }
        // If player switch
        if(round < numberOfPlayers) {
            if (view.rollButton.getModel().isEnabled() == false) {
                toggleOkButton();
            }
            if (view.zeroButton.getModel().isEnabled() == false) {
                toggleZeroButton();
            }
            currentPlayer.currentRoll = 0;
            currentPlayer.createDices();
            showValueDiceButtons();
            System.out.println("Player: " + currentPlayer);
            System.out.println("Counter: " + counter);
        }
        // If player not switch (means all hands saved for every player)
        else {
            if (view.rollButton.getModel().isEnabled() == true) {
                toggleOkButton();
            }
            if (view.zeroButton.getModel().isEnabled() == true) {
                toggleZeroButton();
            }
            System.out.println("GAME ENDED!");
        }
    }

    // Chosing where to add score
    public void setScore(int m) {
        // Disabled all scoreButtons
        for (int i = 0; i < view.scoreButtons.length; i++) {
            view.scoreButtons[i].getModel().setEnabled(false);
        }
        // Save pokerhand
        currentPlayer.savedPokerHands[m] = currentPlayer.pokerHands[m];
        // Display chosen value
        view.getLabel().setText(String.valueOf(currentPlayer.pokerHands[m]));
    }

    public void showScore() {
        for(int i = 0; i < currentPlayer.savedPokerHands.length; i++) {
            System.out.println("Hand " + i + ": " + currentPlayer.savedPokerHands[i]);
        }
    }

    // Display score for each number or poker hand (in boxes)
    public void displayScore() {
        int index = 0;
        for(Player player : playerList) {
            for (int i = 0; i < player.savedPokerHands.length; i++) {
                if (player.savedPokerHands[i] == 100) {
                    view.boxes[i][1][index].setText("");
                } else {
                    view.boxes[i][1][index].setText(String.valueOf(player.savedPokerHands[i]));
                }
            }
            index++;
        }
    }

    public void displayNumbersScore() {
        int index = 0;
        for(Player player : playerList) {
            setPlayer(index);
            score.numbersScore();
            view.numbersTotalBoxes[0][1][index].setText(String.valueOf(player.numbersScore));
            index++;
        }
    }

    public void displayBonus() {
        int index = 0;
        for(Player player : playerList) {
            setPlayer(index);
            score.bonus();
            view.numbersTotalBoxes[1][1][index].setText(String.valueOf(player.bonus));
            index++;
        }
    }

    public void displayNumbersTotalScore() {
        int index = 0;
        for(Player player : playerList) {
            setPlayer(index);
            score.numbersTotalScore();
            view.numbersTotalBoxes[2][1][index].setText(String.valueOf(player.numbersTotalScore));
            index++;
        }
    }

    public void displayPokerTotalScore() {
        int index = 0;
        for(Player player : playerList) {
            setPlayer(index);
            score.pokerScore();
            view.pokerTotalBoxes[0][1][index].setText(String.valueOf(player.pokerScore));
            index++;
        }
    }

    public void displayGrandTotal() {
        int index = 0;
        for(Player player : playerList) {
            setPlayer(index);
            score.grandTotal();
            view.pokerTotalBoxes[1][1][index].setText(String.valueOf(player.grandTotal));
            index++;
        }
    }

    public void displayAllScores() {
        displayNumbersScore();
        displayBonus();
        displayNumbersTotalScore();
        displayPokerTotalScore();
        displayGrandTotal();
    }

    public boolean playedTop() {
        currentPlayer.playedTop = true;
        if(forcedPlay) {
            for (int i = 0; i < 6; i++) {
                if (currentPlayer.savedPokerHands[i] == 100) {
                    currentPlayer.playedTop = false;
                    break;
                }
            }
        }
        return currentPlayer.playedTop;
    }

    public void isPoker() {
        //Check if ones
        if (score.isOne()) {
            // Enabled scoreButton if onePair
            if (isAvailablePokerHand(0)) {
                view.scoreButtons[0].getModel().setEnabled(true);
                System.out.println("AVAILABLE!");
            }
            System.out.println("Is ones: " + currentPlayer.numberOnes);
        }
        //Check if twos
        if (score.isTwo()) {
            // Enabled scoreButton if onePair
            if (isAvailablePokerHand(1)) {
                view.scoreButtons[1].getModel().setEnabled(true);
                System.out.println("AVAILABLE!");
            }
            System.out.println("Is twos: " + currentPlayer.numberTwos);
        }
        //Check if threes
        if (score.isThree()) {
            // Enabled scoreButton if onePair
            // Enabled scoreButton if onePair
            if (isAvailablePokerHand(2)) {
                view.scoreButtons[2].getModel().setEnabled(true);
                System.out.println("AVAILABLE!");
            }
            System.out.println("Is threes: " + currentPlayer.numberThrees);
        }
        //Check if fours
        if (score.isFour()) {
            // Enabled scoreButton if onePair
            if (isAvailablePokerHand(3)) {
                view.scoreButtons[3].getModel().setEnabled(true);
                System.out.println("AVAILABLE!");
            }
            System.out.println("Is fours: " + currentPlayer.numberFours);
        }
        //Check if fives
        if (score.isFive()) {
            // Enabled scoreButton if onePair
            if (isAvailablePokerHand(4)) {
                view.scoreButtons[4].getModel().setEnabled(true);
                System.out.println("AVAILABLE!");
            }
            System.out.println("Is fives: " + currentPlayer.numberFives);
        }
        //Check if sixes
        if (score.isSix()) {
            // Enabled scoreButton if onePair
            if (isAvailablePokerHand(5)) {
                view.scoreButtons[5].getModel().setEnabled(true);
                System.out.println("AVAILABLE!");
            }
            System.out.println("Is sixes: " + currentPlayer.numberSixes);
        }

        if(!forcedPlay || forcedPlay && currentPlayer.playedTop) {
            //Check if one pair
            if (score.isOnePair()) {
                // Enabled scoreButton if onePair
                if (isAvailablePokerHand(6)) {
                    view.scoreButtons[6].getModel().setEnabled(true);
                    System.out.println("AVAILABLE!");
                }
                System.out.println("Is one pair: " + currentPlayer.onePair);
            }
            if (score.isTwoPair()) {
                // Enabled scoreButton if twoPair
                if (isAvailablePokerHand(7)) {
                    view.scoreButtons[7].getModel().setEnabled(true);
                    System.out.println("AVAILABLE!");
                }
                System.out.println("Is two pair: " + currentPlayer.twoPair);
            }
            //Check if 3-kind
            if (score.isThreeOfAKind()) {
                // Enabled scoreButton if twoPair
                if (isAvailablePokerHand(8)) {
                    view.scoreButtons[8].getModel().setEnabled(true);
                    System.out.println("AVAILABLE!");
                }
                System.out.println("Is 3-kind: " + currentPlayer.threeOfAKind);
            }
            //Check if 4-kind
            if (score.isFourOfAKind()) {
                // Enabled scoreButton if twoPair
                if (isAvailablePokerHand(9)) {
                    view.scoreButtons[9].getModel().setEnabled(true);
                    System.out.println("AVAILABLE!");
                }
                System.out.println("Is 4-kind: " + currentPlayer.fourOfAKind);
            }
            //Check if small straight
            if (score.isSmallStraight()) {
                // Enabled scoreButton if twoPair
                if (isAvailablePokerHand(10)) {
                    view.scoreButtons[10].getModel().setEnabled(true);
                    System.out.println("AVAILABLE!");
                }
                System.out.println("Is small straight: " + currentPlayer.smallStraight);
            }
            //Check if large straight
            if (score.isLargeStraight()) {
                // Enabled scoreButton if twoPair
                if (isAvailablePokerHand(11)) {
                    view.scoreButtons[11].getModel().setEnabled(true);
                    System.out.println("AVAILABLE!");
                }
                System.out.println("Is large straight: " + currentPlayer.largeStraight);
            }
            //Check if full house
            if (score.isFullHouse()) {
                // Enabled scoreButton if twoPair
                if (isAvailablePokerHand(12)) {
                    view.scoreButtons[12].getModel().setEnabled(true);
                    System.out.println("AVAILABLE!");
                }
                System.out.println("Is Full House: " + currentPlayer.fullHouse);
            }
            //Check if chance
            if (score.isChance()) {
                // Enabled scoreButton if twoPair
                if (isAvailablePokerHand(13)) {
                    view.scoreButtons[13].getModel().setEnabled(true);
                    System.out.println("AVAILABLE!");
                }
                System.out.println("Is chance-kind: " + currentPlayer.chance);
            }
            //Check if 4-yatzy
            if (score.isYatzy()) {
                // Enabled scoreButton if twoPair
                if (isAvailablePokerHand(14)) {
                    view.scoreButtons[14].getModel().setEnabled(true);
                    System.out.println("AVAILABLE!");
                }
                System.out.println("Is yatzy: " + currentPlayer.yatzy);
            }
        }
    }

    public void showSavedDiceValues() {
        view.getLabel().setText(String.valueOf(currentPlayer.savedValuesDiceCast));
        System.out.println("Saved values: " + currentPlayer.savedValuesDiceCast);
    }


    public void toggleDice(int m) {
        //Toggle button (enabled/disabled) and saves/removes dice value
        // If selected. Set selected to false
        if(view.diceButtons[m].getModel().isSelected()) {
            currentPlayer.dices[m].castValue = currentPlayer.markedDicesArray[m];
            currentPlayer.markedDicesArray[m] = 0;
            view.diceButtons[m].getModel().setSelected(false);
            displayMarked();
            view.setImageToOriginal(view.diceButtons[m]);
        }
        // If not selected. Set selected to true
        else if(!view.diceButtons[m].getModel().isSelected()) {
            currentPlayer.markedDicesArray[m] = currentPlayer.dices[m].castValue;
            currentPlayer.dices[m].castValue = 0;
            view.diceButtons[m].getModel().setSelected(true);
            displayMarked();
            view.setImageToClicked(view.diceButtons[m]);
        }
    }

    public void displayMarked() {
        for(int i = 0; i < currentPlayer.markedDicesArray.length; i++) {
            System.out.println("Marked " + i + ": " + currentPlayer.markedDicesArray[i]);
        }
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
                    String s = JOptionPane.showInputDialog(null, message,
                            "Enter number of Players",
                            JOptionPane.OK_OPTION);
                    if (s == null) {
                        int confirmExit = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to exit?", null,
                                JOptionPane.YES_NO_OPTION);
                        if (confirmExit == JOptionPane.YES_OPTION) {
                            System.exit(0);
                        }
                    }
                    numberOfPlayers = Integer.parseInt(s);
                } catch (NumberFormatException e) {

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
    public void playMode() {
        int choice;
        choice = JOptionPane.showConfirmDialog(null,
                ("Play free mode (YES) or forced play (NO) ?"),
                "Play Mode",
                JOptionPane.YES_NO_OPTION);
        switch (choice) {
            case JOptionPane.YES_OPTION:
                forcedPlay = false;
                System.out.println("Forced play: " + forcedPlay);
                break;
            case JOptionPane.NO_OPTION:
                forcedPlay = true;
                System.out.println("Forced play: " + forcedPlay);
                break;
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

    public void emptyPlayerList() {
        playerList.clear();
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

}

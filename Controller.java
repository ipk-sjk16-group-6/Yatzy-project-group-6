package Yatzy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by bartek on 28/10/16.
 * Modified by Max on 01/12/16.
 */
public class Controller {

    public Controller() {
        View view = new View();
        PlayerGUI playerGUI = new PlayerGUI();
        Model model = new Model(view, playerGUI);
        model.setNumberOfPlayers();
        model.fillPlayerList(model.getNumberOfPlayers());
        /*
        ArrayList<Player> playerList = model.getPlayerList();
        System.out.println(Arrays.toString(playerList.toArray()));
        Test to see if arrayList playerList is filled
        */
        System.out.println("Numbers of players: "+model.getNumberOfPlayers());
        DiceListener diceListener = new DiceListener(view, model);
        ScoreListener scoreListener = new ScoreListener(view, model);

        view.getOkButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                model.saveDiceValues();
                model.showSavedDiceValues();
                model.isPoker();
            }

        });

        // Add listener to all dice buttons
        for (int i = 0; i < view.diceButtons.length; i++) {
            view.diceButtons[i].addActionListener(diceListener);
        }

        // Add listener to all dice buttons
        for (int i = 0; i < view.scoreButtons.length; i++) {
            view.scoreButtons[i].addActionListener(scoreListener);
        }

        // Play
        playerGUI.createDices();
        playerGUI.castDices();
        model.showValueDiceButton();
    }

    public class DiceListener implements ActionListener {

        View view;
        Model model;

        public DiceListener(View view, Model model) {
            this.view = view;
            this.model = model;
        }

        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < view.diceButtons.length; i++) {
                if (view.diceButtons[i].getModel().isEnabled()) {
                    if (e.getSource() == view.diceButtons[i]) {
                        model.toggleDice(i);
                        model.showDiceValue(i);
                    }
                }
            }
        }

    }

    public class ScoreListener implements ActionListener {

        View view;
        Model model;

        public ScoreListener(View view, Model model) {
            this.view = view;
            this.model = model;
        }

        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < view.scoreButtons.length; i++) {
                if (view.scoreButtons[i].getModel().isEnabled()) {
                    if (e.getSource() == view.scoreButtons[i]) {
                        model.setScore(i);
                        model.showScore();
                        model.displayScore();
                        model.displayAllScores();
                    }
                }
            }
        }

    }
    
    

    public static void main(String[] arg) {
        Controller controller = new Controller();
        
    }
}

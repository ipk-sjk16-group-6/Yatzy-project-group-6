package Yatzy;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by bartek on 26/10/16.
 */
public class View {

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    JLabel label;
    JButton okButton;
    JButton exitButton;
    JButton diceButtons[];
    JButton scoreButtons[];
    JLabel boxes[][];
    JLabel numbersTotalBoxes[][];
    JLabel pokerTotalBoxes[][];

    public View() {
        // Create JFrame (container window)
        mainFrame = new JFrame("Yatzy");
        mainFrame.setSize(1100, 1000);
        mainFrame.setLayout(new GridLayout(1, 3));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        // Create JPanel
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        //Create JLabel
        headerLabel = new JLabel("HeaderLabel", JLabel.CENTER);
        headerLabel.setSize(400, 100);
        statusLabel = new JLabel("StatusLabel", JLabel.CENTER);
        statusLabel.setSize(400, 100);

        // Add borders to labels
        headerLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        statusLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        label = new JLabel("Label is here");
        label.setVisible(true);

        // Create button and add to JPanel
        okButton = new JButton("Click here");
        controlPanel.add(okButton);
        
        // Add JPanel and JLabels to JFrame
        mainFrame.add(controlPanel);
        //mainFrame.add(headerLabel);
        //mainFrame.add(statusLabel);
        mainFrame.add(label);
        mainFrame.setVisible(true);

        // Create dice buttons
        diceButtons = new JButton[5];

        //mainFrame.add(new JPanel());	// blank - for formatting
        for (int i = 0; i < 5; i++) {
            diceButtons[i] = new JButton();
            diceButtons[i].setFont(new Font("Helvetica", Font.PLAIN, 50));
            controlPanel.add(diceButtons[i]);
        }
        //mainFrame.add(new JPanel());	// blank - for formatting

        //Create scoreButtons and scoreBoxes
        scoreButtons = new JButton[15];

        for (int i = 0; i < scoreButtons.length; i++) {
            scoreButtons[i] = new JButton();
            scoreButtons[i].setFont(new Font("Helvetica", Font.PLAIN, 50));
            scoreButtons[i].putClientProperty("id", i);
            scoreButtons[i].getModel().setEnabled(false);
            controlPanel.add(scoreButtons[i]);
        }
        // Set names for scoreButtons
        scoreButtons[0].setText("Ones");
        scoreButtons[1].setText("Twos");
        scoreButtons[2].setText("Threes");
        scoreButtons[3].setText("Fours");
        scoreButtons[4].setText("Fives");
        scoreButtons[5].setText("Sixes");
        scoreButtons[6].setText("One Pair");
        scoreButtons[7].setText("Two Pair");
        scoreButtons[8].setText("Three of a kind");
        scoreButtons[9].setText("Four of a kind");
        scoreButtons[10].setText("Small Straight");
        scoreButtons[11].setText("Large Straight");
        scoreButtons[12].setText("Full House");
        scoreButtons[13].setText("Chance");
        scoreButtons[14].setText("Yatzy");

        // Create boxes
        boxes = new JLabel[15][2];
        JLabel l0;
        JLabel l1;
        for (int i = 0; i < boxes.length; i++) {
            l0 = new JLabel("  #" + i);
            l1 = new JLabel();
            l0.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
            l1.setBorder(new MatteBorder(0, 1, 1, 1, Color.BLACK));
            boxes[i][0] = l0;
            boxes[i][1] = l1;
            controlPanel.add(boxes[i][0]);
            controlPanel.add(boxes[i][1]);
        }

        // Create numbers score box
        // Create bonus box
        // Create numbers total score box
        numbersTotalBoxes = new JLabel[3][2];
        JLabel l2;
        JLabel l3;
        for (int i = 0; i < numbersTotalBoxes.length; i++) {
            l2 = new JLabel();
            l3 = new JLabel();
            l2.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
            l3.setBorder(new MatteBorder(0, 1, 1, 1, Color.BLACK));
            numbersTotalBoxes[i][0] = l2;
            numbersTotalBoxes[i][1] = l3;
            controlPanel.add(numbersTotalBoxes[i][0]);
            controlPanel.add(numbersTotalBoxes[i][1]);
        }

        numbersTotalBoxes[0][0].setText("Numbers Score");
        numbersTotalBoxes[1][0].setText("Bonus");
        numbersTotalBoxes[2][0].setText("Total Numbers Score");

        // Create poker total score box
        // Crate grand total box
        pokerTotalBoxes = new JLabel[2][2];
        JLabel l4;
        JLabel l5;
        for (int i = 0; i < pokerTotalBoxes.length; i++) {
            l4 = new JLabel();
            l5 = new JLabel();
            l4.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
            l5.setBorder(new MatteBorder(0, 1, 1, 1, Color.BLACK));
            pokerTotalBoxes[i][0] = l4;
            pokerTotalBoxes[i][1] = l5;
            controlPanel.add(pokerTotalBoxes[i][0]);
            controlPanel.add(pokerTotalBoxes[i][1]);
        }

        pokerTotalBoxes[0][0].setText("Poker Score");
        pokerTotalBoxes[1][0].setText("Grand Total");
        
        //create exit button
        exitButton = new JButton("Click here to exit game");
        controlPanel.add(exitButton);
        exitButton.addActionListener(e -> {
        int confirmExit = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to exit?", null, JOptionPane.YES_NO_OPTION);
        if(confirmExit == JOptionPane.YES_OPTION)
            System.exit(0);
        });
    }

    JLabel getLabel() {
        return label;
    }

    JButton getOkButton() {
        return okButton;
    }

    JButton getDiceButton(int i) {

        return diceButtons[i];
    }

}

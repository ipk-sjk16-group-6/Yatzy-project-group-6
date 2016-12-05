package Yatzy;

import java.util.Random;

public class Dice {

    int diceValue;

    public Dice() {

        // Initiate value of 100, only used for output reasons
        diceValue = 100;

    }

    int throwDice() {
        Random generator = new Random();
        int rnd = generator.nextInt(6) + 1;

        diceValue = rnd;
        return diceValue;
    }

}

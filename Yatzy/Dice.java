package Yatzy;

import java.util.Random;

public class Dice {

    int castValue;
    String defaultValue;

    public Dice() {

        // Initiate value of 100, only used for output reasons
        defaultValue = "?";
        castValue = 100;

    }

    int kastaTärning() {
        Random generator = new Random();
        int rnd = generator.nextInt(6) + 1;

        castValue = rnd;
        return castValue;
    }

}

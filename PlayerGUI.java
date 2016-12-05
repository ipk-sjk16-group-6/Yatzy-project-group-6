package Yatzy;

import java.util.Collections;
import java.util.Vector;

public class PlayerGUI {

    Dice[] dices;
    Vector savedValuesDiceCast;
    int[] pokerHands;
    int[] savedPokerHands;

    int numberOnes;
    int numberTwos;
    int numberThrees;
    int numberFours;
    int numberFives;
    int numberSixes;
    int onePair;
    int twoPair;
    int threeOfAKind;
    int fourOfAKind;
    int smallStraight;
    int largeStraight;
    int fullHouse;
    int chance;
    int yatzy;
    boolean isOne;
    boolean isTwo;
    boolean isThree;
    boolean isFour;
    boolean isFive;
    boolean isSix;
    boolean isOnePair;
    boolean isTwoPair;
    boolean isThreeOfAKind;
    boolean isFourOfAKind;
    boolean isSmallStraight;
    boolean isLargeStraight;
    boolean isFullHouse;
    boolean isChance;
    boolean isYatzy;

    boolean isAvailablePokerHand;

    int numbersScore;
    int bonus;
    int numbersTotalScore;
    int pokerScore;
    int grandTotal;

    public PlayerGUI() {

        savedValuesDiceCast = new Vector();
        pokerHands = new int[15];
        savedPokerHands = new int[15];

    }

    // Used in MVC
    void createDices() {
        // Create dices and add to array
        dices = new Dice[5];
        for (int i = 0; i < dices.length; i++) {
            dices[i] = new Dice();
            System.out.println(dices[i]);
        }
    }

    // Used in MVC
    void castDices() {
        for (int i = 0; i < dices.length; i++) {
            // Kasta täring (returnerar "kastatvärde")
            if (dices[i].diceValue != 0) {
                int värde = dices[i].throwDice();
                // Skriv ut värdet
                System.out.println(värde);
            }
        }
        System.out.println("----------------");
    }

    boolean isOne() {
        isOne = false;
        Collections.sort(savedValuesDiceCast);
        if (savedValuesDiceCast.size() == 5) {
            for (int i = 0; i < savedValuesDiceCast.size(); i++) {
                if ((Integer) savedValuesDiceCast.get(i) == 1) {
                    numberOnes += ((Integer) savedValuesDiceCast.get(i));
                }
            }
            if (numberOnes != 0) {
                pokerHands[0] = numberOnes;
                isOne = true;
            }
        }
        return isOne;
    }

    boolean isTwo() {
        isTwo = false;
        Collections.sort(savedValuesDiceCast);
        if (savedValuesDiceCast.size() == 5) {
            for (int i = 0; i < savedValuesDiceCast.size(); i++) {
                if ((Integer) savedValuesDiceCast.get(i) == 2) {
                    numberTwos += ((Integer) savedValuesDiceCast.get(i));
                }
            }
            if (numberTwos != 0) {
                pokerHands[1] = numberTwos;
                isTwo = true;
            }
        }
        return isTwo;
    }

    boolean isThree() {
        isThree = false;
        Collections.sort(savedValuesDiceCast);
        if (savedValuesDiceCast.size() == 5) {
            for (int i = 0; i < savedValuesDiceCast.size(); i++) {
                if ((Integer) savedValuesDiceCast.get(i) == 3) {
                    numberThrees += ((Integer) savedValuesDiceCast.get(i));
                }
            }
            if (numberThrees != 0) {
                pokerHands[2] = numberThrees;
                isThree = true;
            }
        }
        return isThree;
    }

    boolean isFour() {
        isFour = false;
        Collections.sort(savedValuesDiceCast);
        if (savedValuesDiceCast.size() == 5) {
            for (int i = 0; i < savedValuesDiceCast.size(); i++) {
                if ((Integer) savedValuesDiceCast.get(i) == 4) {
                    numberFours += ((Integer) savedValuesDiceCast.get(i));
                }
            }
            if (numberFours != 0) {
                pokerHands[3] = numberFours;
                isFour = true;
            }
        }
        return isFour;
    }

    boolean isFive() {
        isFive = false;
        Collections.sort(savedValuesDiceCast);
        if (savedValuesDiceCast.size() == 5) {
            for (int i = 0; i < savedValuesDiceCast.size(); i++) {
                if ((Integer) savedValuesDiceCast.get(i) == 5) {
                    numberOnes += ((Integer) savedValuesDiceCast.get(i));
                }
            }
            if (numberFives != 0) {
                pokerHands[4] = numberFives;
                isFive = true;
            }
        }
        return isFive;
    }

    boolean isSix() {
        isSix = false;
        Collections.sort(savedValuesDiceCast);
        if (savedValuesDiceCast.size() == 5) {
            for (int i = 0; i < savedValuesDiceCast.size(); i++) {
                if ((Integer) savedValuesDiceCast.get(i) == 6) {
                    numberSixes += ((Integer) savedValuesDiceCast.get(i));
                }
            }
            if (numberSixes != 0) {
                pokerHands[5] = numberSixes;
                isSix = true;
            }
        }
        return isSix;
    }

    boolean isOnePair() {
        isOnePair = false;
        Vector onePairTemp = new Vector();
        Collections.sort(savedValuesDiceCast);
        if (savedValuesDiceCast.size() == 5) {
            for (int i = 0; i < savedValuesDiceCast.size() - 1; i++) {
                if (savedValuesDiceCast.get(i) == savedValuesDiceCast.get(i + 1)) {
                    onePairTemp.add((Integer) savedValuesDiceCast.get(i) + 
                            (Integer) savedValuesDiceCast.get(i + 1));
                }
            }
            //Find highest pair
            if (onePairTemp.size() > 0) {
                Object highestPair = Collections.max(onePairTemp);
                onePair = (Integer) highestPair;
                pokerHands[6] = onePair;
                isOnePair = true;
            }
        }
        return isOnePair;
    }

    boolean isTwoPair() {
        isTwoPair = false;
        Object highestPair = null;
        Object nextHighestPair = null;
        Vector twoPairTemp = new Vector();
        Collections.sort(savedValuesDiceCast);
        if (savedValuesDiceCast.size() == 5) {
            for (int i = 0; i < savedValuesDiceCast.size() - 1; i++) {
                if (savedValuesDiceCast.get(i) == savedValuesDiceCast.get(i + 1)) {
                    twoPairTemp.add((Integer) savedValuesDiceCast.get(i) + 
                            (Integer) savedValuesDiceCast.get(i + 1));
                }
            }
            //Find highest pair
            if (twoPairTemp.size() > 0) {
                highestPair = Collections.max(twoPairTemp);
                // Remove highest pair
                for (int i = 0; i < twoPairTemp.size(); i++) {
                    twoPairTemp.removeElement(highestPair);
                }
                // If there are still more values (pairs)
                if (twoPairTemp.size() > 0) {
                    // Find highest pair (now next highest pair)
                    nextHighestPair = Collections.max(twoPairTemp);
                }
            }
            // If both objects "highestPair" and "nextHighestPair" exist and  highestPair != nextHighestPair (i.e three-of-a-kind makes two identical pairs)
            if (highestPair != null && nextHighestPair != null && highestPair != nextHighestPair) {
                // Calculate sum of pair
                twoPair = (Integer) highestPair + (Integer) nextHighestPair;
                pokerHands[7] = twoPair;
                isTwoPair = true;
            }
        }
        return isTwoPair;
    }

    boolean isThreeOfAKind() {
        isThreeOfAKind = false;
        Collections.sort(savedValuesDiceCast);
        // Check if 5 dice values are saved
        if (savedValuesDiceCast.size() == 5) {
            // First three dices are alike
            if (savedValuesDiceCast.get(0) == savedValuesDiceCast.get(1) && 
                    savedValuesDiceCast.get(1) == savedValuesDiceCast.get(2)) {
                threeOfAKind = (Integer) savedValuesDiceCast.get(0) + 
                        (Integer) savedValuesDiceCast.get(1) + 
                        (Integer) savedValuesDiceCast.get(2);
                pokerHands[8] = threeOfAKind;
                isThreeOfAKind = true;
            } // Dices 2-4 are alike
            else if (savedValuesDiceCast.get(1) == savedValuesDiceCast.get(2) && 
                    savedValuesDiceCast.get(2) == savedValuesDiceCast.get(3)) {
                threeOfAKind = (Integer) savedValuesDiceCast.get(1) + 
                        (Integer) savedValuesDiceCast.get(2) + 
                        (Integer) savedValuesDiceCast.get(3);
                pokerHands[8] = threeOfAKind;
                isThreeOfAKind = true;
            } // Last three dices are alike
            else if (savedValuesDiceCast.get(2) == savedValuesDiceCast.get(3) && 
                    savedValuesDiceCast.get(3) == savedValuesDiceCast.get(4)) {
                threeOfAKind = (Integer) savedValuesDiceCast.get(2) + 
                        (Integer) savedValuesDiceCast.get(3) + 
                        (Integer) savedValuesDiceCast.get(4);
                pokerHands[8] = threeOfAKind;
                isThreeOfAKind = true;
            }
        }
        return isThreeOfAKind;
    }

    boolean isFourOfAKind() {
        isFourOfAKind = false;
        Collections.sort(savedValuesDiceCast);
        if (savedValuesDiceCast.size() == 5) {
            if (savedValuesDiceCast.get(0) == savedValuesDiceCast.get(1) && 
                    savedValuesDiceCast.get(1) == savedValuesDiceCast.get(2) && 
                    savedValuesDiceCast.get(2) == savedValuesDiceCast.get(3)) {
                fourOfAKind = (Integer) savedValuesDiceCast.get(0) + 
                        (Integer) savedValuesDiceCast.get(1) + 
                        (Integer) savedValuesDiceCast.get(2)
                        + (Integer) savedValuesDiceCast.get(3);
                pokerHands[9] = fourOfAKind;
                isFourOfAKind = true;
            } 
            else if (savedValuesDiceCast.get(1) == savedValuesDiceCast.get(2) && 
                    savedValuesDiceCast.get(2) == savedValuesDiceCast.get(3) && 
                    savedValuesDiceCast.get(3) == savedValuesDiceCast.get(4)) {
                fourOfAKind = (Integer) savedValuesDiceCast.get(1) + (
                        Integer) savedValuesDiceCast.get(2) + 
                        (Integer) savedValuesDiceCast.get(3)
                        + (Integer) savedValuesDiceCast.get(4);
                pokerHands[9] = fourOfAKind;
                isFourOfAKind = true;
            }
        }
        return isFourOfAKind;

    }

    boolean isSmallStraight() {
        Collections.sort(savedValuesDiceCast);
        if (savedValuesDiceCast.size() == 5) {
            isSmallStraight = true;
            for (int i = 0; i < savedValuesDiceCast.size(); i++) {
                if ((Integer) savedValuesDiceCast.get(i) != i + 1) {
                    isSmallStraight = false;
                    break;
                }
            }
        }
        if (isSmallStraight) {
            for (int i = 0; i < savedValuesDiceCast.size(); i++) {
                smallStraight += (Integer) savedValuesDiceCast.get(i);
            }
            pokerHands[10] = smallStraight;
        }
        return isSmallStraight;
    }

    boolean isLargeStraight() {
        Collections.sort(savedValuesDiceCast);
        if (savedValuesDiceCast.size() == 5) {
            isLargeStraight = true;
            for (int i = 0; i < savedValuesDiceCast.size(); i++) {
                if ((Integer) savedValuesDiceCast.get(i) != i + 2) {
                    isLargeStraight = false;
                    break;
                }
            }
        }
        if (isLargeStraight) {
            for (int i = 0; i < savedValuesDiceCast.size(); i++) {
                largeStraight += (Integer) savedValuesDiceCast.get(i);
            }
            pokerHands[11] = largeStraight;
        }
        return isLargeStraight;
    }

    boolean isFullHouse() {
        isFullHouse = false;
        Collections.sort(savedValuesDiceCast);
        if (savedValuesDiceCast.size() == 5) {
            if (savedValuesDiceCast.get(0) == savedValuesDiceCast.get(1) && 
                    savedValuesDiceCast.get(1) == savedValuesDiceCast.get(2) && 
                    savedValuesDiceCast.get(3) == savedValuesDiceCast.get(4)) {
                fullHouse = (Integer) savedValuesDiceCast.get(0) + 
                        (Integer) savedValuesDiceCast.get(1) + 
                        (Integer) savedValuesDiceCast.get(2)
                        + (Integer) savedValuesDiceCast.get(3) + 
                        (Integer) savedValuesDiceCast.get(4);
                pokerHands[12] = fullHouse;
                isFullHouse = true;
            } 
            else if (savedValuesDiceCast.get(0) == savedValuesDiceCast.get(1) && 
                    savedValuesDiceCast.get(2) == savedValuesDiceCast.get(3) && 
                    savedValuesDiceCast.get(3) == savedValuesDiceCast.get(4)) {
                fullHouse = (Integer) savedValuesDiceCast.get(0) + 
                        (Integer) savedValuesDiceCast.get(1) + 
                        (Integer) savedValuesDiceCast.get(2)
                        + (Integer) savedValuesDiceCast.get(3) + 
                        (Integer) savedValuesDiceCast.get(4);
                pokerHands[12] = fullHouse;
                isFullHouse = true;
            }
        }
        return isFullHouse;
    }

    boolean isChance() {
        isChance = false;
        Collections.sort(savedValuesDiceCast);
        if (savedValuesDiceCast.size() == 5) {
            for (int i = 0; i < savedValuesDiceCast.size(); i++) {
                chance += (Integer) savedValuesDiceCast.get(i);
            }
            pokerHands[13] = chance;
            isChance = true;
        }
        return isChance;
    }

    boolean isYatzy() {
        isYatzy = false;
        if (savedValuesDiceCast.size() == 5) {
            if (savedValuesDiceCast.get(0) == savedValuesDiceCast.get(1) && savedValuesDiceCast.get(1) == savedValuesDiceCast.get(2)
                    && savedValuesDiceCast.get(2) == savedValuesDiceCast.get(3) && savedValuesDiceCast.get(3) == savedValuesDiceCast.get(4)) {
                isYatzy = true;
                pokerHands[14] = 50;
            }
        }
        return isYatzy;
    }

    int numbersScore() {
        for (int i = 0; i < 6; i++) {
            numbersScore += savedPokerHands[i];
        }
        return numbersScore;
    }

    int bonus() {
        if (numbersScore >= 63) {
            bonus = 50;
        }
        return bonus;
    }

    int numbersTotalScore() {
        numbersTotalScore = numbersScore + bonus;
        return numbersTotalScore;
    }

    int pokerScore() {
        for (int i = 7; i < 15; i++) {
            pokerScore += savedPokerHands[i];
        }
        return pokerScore;
    }

    int grandTotal() {
        for (int i = 0; i < savedPokerHands.length; i++) {
            grandTotal += savedPokerHands[i];
        }
        grandTotal += bonus;
        return grandTotal;
    }

}

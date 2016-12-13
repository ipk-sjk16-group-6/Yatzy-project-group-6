package Yatzy;

/**
 *
 * @author Anneli, Marcus
 */
public class Values {

    public static String tellItLikeItIs(int value) {

        switch (value) {

            case 0:
                return "ONES";
            case 1:
                return "TWOS";
            case 2:
                return "THREES";
            case 3:
                return "FOURS";
            case 4:
                return "FIVES";
            case 5:
                return "SIXES";
            case 6:
                return "ONE_PAIR";
            case 7:
                return "TWO_PAIR";
            case 8:
                return "THREE_OF_A_KIND";
            case 9:
                return "FOUR_OF_A_KIND";
            case 10:
                return "SMALL_STRAIGHT";
            case 11:
                return "LARGE_STRAIGHT";
            case 12:
                return "FULL_HOUSE";
            case 13:
                return "CHANCE";
            case 14:
                return "YATZY";
            default:

        }
        return "something went wrong";
    }

}

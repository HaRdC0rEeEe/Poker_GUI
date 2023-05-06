package Logic;

public class CardEnums{
    public enum cValue{
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(11),
        QUEEN(12),
        KING(13),
        ACE(14);

        private final int rank;

        cValue(int rank) {
            this.rank = rank;
        }

        public int getRank() {
            return rank;
        }
    }

    public enum cColor{
        RED, BLACK
    }

    public enum cSymbol{
        SPADES, CLUBS, HEARTS, DIAMONDS
    }
}

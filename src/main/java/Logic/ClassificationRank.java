package Logic;


public enum ClassificationRank{



        ROYAL_FLUSH(11),
        STRAIGHT_FLUSH(10),
        FOURS(9),
        FULLHOUSE(8),
        FLUSH(7),
        STRAIGHT(6),
        LOWERSTRAIGHT(5),
        TRIPLE(4),
        DOUBLE_PAIR(3),
        PAIR(2),
        HIGH_CARD(1);

        private final int rank;

        ClassificationRank(int rank) {
            this.rank = rank;
        }

        public int getValue() {
            return rank;
        }



}





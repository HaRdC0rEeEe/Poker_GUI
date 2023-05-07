package Logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorByResult implements Comparator<Player>{

    //https://upswingpoker.com/who-wins-if-two-players-have-the-same-hand-in-poker/
    //when players have same pair, their second card in hand wins if it is higher
    //https://www.adda52.com/poker/poker-rules/cash-game-rules/tie-breaker-rules
    //o1 < o2 == -1,
    //o1 > o2 == 1,
    @Override
    public int compare(Player o1, Player o2) {
        //first compare by rank results
        int o1Rank = o1.getRank();
        int o2Rank = o2.getRank();

        if(o1Rank > o2Rank)
            return 1;
        else if(o1Rank < o2Rank)
            return -1;
        else{

            ///compare rare cases when players have same ranks

            var cRank = o1.getcRank();

            // Compare the hands based on the classification rank

            //straight flush - highest card of same suit will win, review
            return switch(cRank){
                case STRAIGHT_FLUSH, STRAIGHT, FLUSH -> compareStraight(o1, o2);
                case FOURS -> compareFourOfAKind(o1, o2);
                case FULLHOUSE -> compareFullHouse(o1, o2);
                case TRIPLE -> compareThreeOfAKind(o1, o2);
                case DOUBLE_PAIR -> compareTwoPairs(o1, o2);
                case PAIR -> comparePair(o1, o2);
                case HIGH_CARD -> compareHighestCards(o1, o2);
                default -> throw new IllegalStateException("Invalid classification rank.");
            };
        }
    }

    public int compareStraight(Player o1, Player o2) {
        return Integer.compare(o1.getHighestStraight().getRank(), o2.getHighestStraight().getRank());
    }







    public int compareFourOfAKind(Player o1, Player o2) {
        int rank1 = o1.getFourKind().getRank();
        int rank2 = o2.getFourKind().getRank();
        int cmp = Integer.compare(rank2, rank1);
        if(cmp != 0){
            return cmp;
        }
        return compareHighCards(o1, o2, 1);
    }

    public int compareHighCards(Player o1, Player o2, int numCardsToCompare) {
        List<Card> hand1 = o1.getHand();
        List<Card> hand2 = o2.getHand();

        for(int i = 0; i < numCardsToCompare; i++){
            int cmp = Integer.compare(hand2.get(i).getRank(), hand1.get(i).getRank());
            if(cmp != 0){
                return cmp;
            }
        }

        return 0;
    }

    public int compareFullHouse(Player o1, Player o2) {
        int o1ThreeKindRank = o1.getThreeKind().getRank();
        int o2ThreeKindRank = o2.getThreeKind().getRank();

        if(o1ThreeKindRank != o2ThreeKindRank)
            return Integer.compare(o2ThreeKindRank, o1ThreeKindRank);

        int o1PairRank = o1.getPair().getRank();
        int o2PairRank = o2.getPair().getRank();

        return Integer.compare(o2PairRank, o1PairRank);
    }



    public int comparePairOrTriple(Card player1Pairs, Card player2Pairs, Player o1, Player o2) {
        int cmp = Integer.compare(player1Pairs.getRank(), player2Pairs.getRank());
        //return if pairs or triples are not same
        if(cmp != 0){
            return cmp;
        }

        // Compare the kickers
        List<Card> hand1 = o1.getHand();
        List<Card> hand2 = o2.getHand();
        List<Card> remaining1 = new ArrayList<>(hand1);
        List<Card> remaining2 = new ArrayList<>(hand2);
        remaining1.remove(player1Pairs);
        remaining2.remove(player2Pairs);


        for(int i = 0; i < 2; i++){
            cmp = Integer.compare(remaining1.get(i).getRank(), remaining2.get(i).getRank());
            if(cmp != 0){
                return cmp;
            }
        }

        return 0;

    }
    public int compareThreeOfAKind(Player o1, Player o2) {
        Card threeOfAKind1 = o1.getThreeKind();
        Card threeOfAKind2 = o2.getThreeKind();

        return comparePairOrTriple(threeOfAKind1, threeOfAKind2, o1, o2);
    }

    public int compareTwoPairs(Player o1, Player o2) {
        // Sort the pairs and kickers in each hand
        o1.getHand().sort(Collections.reverseOrder());
        o2.getHand().sort(Collections.reverseOrder());

        // Find the higher pair in each hand
        var cmp = Integer.compare(o2.getPair().getRank(), o1.getPair().getRank());
        if(cmp != 0){
            return cmp;
        }

        // Find the lower pair in each hand
        cmp = Integer.compare(o2.getHand().get(2).getRank(), o1.getHand().get(2).getRank());
        if(cmp != 0){
            return cmp;
        }

        // Compare the kickers
        cmp = compareHighestCards(o1, o2);
        return cmp;

    }

    public int comparePair(Player o1, Player o2) {

        Card pair1 = o1.getPair();
        Card pair2 = o2.getPair();

        return comparePairOrTriple(pair1, pair2, o1, o2);

    }

    public int compareHighestCards(Player o1, Player o2) {
        return Integer.compare(o1.getHighestCard(), o2.getHighestCard());

    }
}


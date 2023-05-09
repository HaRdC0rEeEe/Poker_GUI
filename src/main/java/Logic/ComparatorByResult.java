package Logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorByResult implements Comparator<Player>{

    //https://upswingpoker.com/who-wins-if-two-players-have-the-same-hand-in-poker/
    //when players have same pair, their second card in hand wins if it is higher
    //https://www.adda52.com/poker/poker-rules/cash-game-rules/tie-breaker-rules
    //p1 < p2 == -1,
    //p1 > p2 == 1,
    @Override
    public int compare(Player p1, Player p2) {
        //first compare by rank results
        int p1Rank = p1.getRank();
        int p2Rank = p2.getRank();

        if(p1Rank > p2Rank)
            return 1;
        else if(p1Rank < p2Rank)
            return -1;
        else{

            ///compare rare cases when players have same ranks

            var cRank = p1.getcRank();

            // Compare the hands based on the classification rank

            //straight flush - highest card of same suit will win, review
            return switch(cRank){
                case STRAIGHT_FLUSH, STRAIGHT, FLUSH -> compareStraight(p1, p2);
                case FOURS -> compareFourOfAKind(p1, p2);
                case FULLHOUSE -> compareFullHouse(p1, p2);
                case TRIPLE -> compareThreeOfAKind(p1, p2);
                case DOUBLE_PAIR -> compareTwoPairs(p1, p2);
                case PAIR -> comparePair(p1, p2);
                case HIGH_CARD -> compareHighestCards(p1, p2);
                default -> throw new IllegalStateException("Invalid classification rank.");
            };
        }
    }

    public int compareStraight(Player p1, Player p2) {
        return Integer.compare(p1.getHighestStraight().getRank(), p2.getHighestStraight().getRank());
    }

    public int compareFourOfAKind(Player p1, Player p2) {
        int rank1 = p1.getFourKind().getRank();
        int rank2 = p2.getFourKind().getRank();
        int cmp = Integer.compare(rank2, rank1);
        if(cmp != 0){
            return cmp;
        }
        return compareHighCards(p1, p2, 1);
    }

    public int compareHighCards(Player p1, Player p2, int numCardsToCompare) {
        List<Card> hand1 = p1.getHand();
        List<Card> hand2 = p2.getHand();

        for(int i = 0; i < numCardsToCompare; i++){
            int cmp = Integer.compare(hand2.get(i).getRank(), hand1.get(i).getRank());
            if(cmp != 0){
                return cmp;
            }
        }

        return 0;
    }

    public int compareFullHouse(Player p1, Player p2) {
        int p1ThreeKindRank = p1.getThreeKind().getRank();
        int p2ThreeKindRank = p2.getThreeKind().getRank();

        if(p1ThreeKindRank != p2ThreeKindRank)
            return Integer.compare(p2ThreeKindRank, p1ThreeKindRank);

        int p1PairRank = p1.getPair().getRank();
        int p2PairRank = p2.getPair().getRank();

        return Integer.compare(p2PairRank, p1PairRank);
    }



    public int comparePairOrTriple(Card player1Pairs, Card player2Pairs, Player p1, Player p2) {
        int cmp = Integer.compare(player1Pairs.getRank(), player2Pairs.getRank());
        //return if pairs or triples are not same
        if(cmp != 0){
            return cmp;
        }

        // Compare the kickers
        List<Card> hand1 = p1.getHand();
        List<Card> hand2 = p2.getHand();
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
    public int compareThreeOfAKind(Player p1, Player p2) {
        Card threeOfAKind1 = p1.getThreeKind();
        Card threeOfAKind2 = p2.getThreeKind();

        return comparePairOrTriple(threeOfAKind1, threeOfAKind2, p1, p2);
    }

    public int compareTwoPairs(Player p1, Player p2) {
        // Sort the pairs and kickers in each hand
        p1.getHand().sort(Collections.reverseOrder());
        p2.getHand().sort(Collections.reverseOrder());

        // Find the higher pair in each hand
        var cmp = Integer.compare(p2.getPair().getRank(), p1.getPair().getRank());
        if(cmp != 0){
            return cmp;
        }

        // Find the lower pair in each hand
        cmp = Integer.compare(p2.getHand().get(2).getRank(), p1.getHand().get(2).getRank());
        if(cmp != 0){
            return cmp;
        }

        // Compare the kickers
        cmp = compareHighestCards(p1, p2);
        return cmp;

    }

    public int comparePair(Player p1, Player p2) {

        Card pair1 = p1.getPair();
        Card pair2 = p2.getPair();

        return comparePairOrTriple(pair1, pair2, p1, p2);

    }

    public int compareHighestCards(Player p1, Player p2) {
        return Integer.compare(p1.getHighestCard(), p2.getHighestCard());

    }
}


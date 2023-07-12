package Logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ComparatorByResult implements Comparator<Player>{

    final List<Card> communityCards = HandEvaluator.communityCards;
    List<Card> hand1;
    List<Card> hand2;
    private Player p1;
    private Player p2;

    @Override
    public int compare(Player p1, Player p2) {

        this.p1 = p1;
        this.p2 = p2;

        //first compare by rank results
        int p1Rank = p1.getRank();
        int p2Rank = p2.getRank();

        if(p1Rank > p2Rank)
            return 1;
        else if(p1Rank < p2Rank)
            return -1;
        else{
            ///compare rare cases when players have same ranks
            hand1 = p1.getHand();
            hand2 = p2.getHand();

            var cRank = p1.getcRank();

            // Compare the hands based on the classification rank
            return switch(cRank){
                case ROYAL_FLUSH -> compareRoyalFlush();
                case STRAIGHT_FLUSH, STRAIGHT, LOWERSTRAIGHT -> compareStraight();
                case FOURS -> compareFourOfAKind();
                case FULLHOUSE -> compareFullHouse();
                case FLUSH -> compareFlush();
                case TRIPLE -> compareThreeOfAKind();
                case DOUBLE_PAIR -> compareTwoPairs();
                case PAIR -> comparePair();
                case HIGH_CARD -> compareHighCards();
                case HIGH_COMMUNITY_CARD -> 0;
                default -> throw new IllegalStateException("Invalid classification rank.");
            };
        }
    }

    private int compareRoyalFlush() {
        return 0;
    }

    private int compareFlush() {

        for(int i = 0; i < communityCards.size(); i++){
            int p1Card = p1.getFlush().get(i);
            int p2Card = p2.getFlush().get(i);

            int cmp = Integer.compare(p1Card, p2Card);
            if(cmp != 0)
                return cmp;

        }
        return 0;
    }


    //if players have identical hand with High card, it will always be a tie
    private int compareHighCards() {
        int FIRST = 0;
        int cmp = compareNthCards(FIRST);
        if(cmp != 0)
            return cmp;
        int SECOND = 1;
        return compareNthCards(SECOND);

    }

    /***
     * @param pos 0 will compare first card, 1 will compare second card
     * @return returns comparison of first or second card in each player hand
     */
    private int compareNthCards(int pos) {

        return Integer.compare(p1.getHand().get(pos).getRank(), p2.getHand().get(pos).getRank());
    }

    public int compareStraight() {
        return Integer.compare(p1.getHighestStraight().getRank(), p2.getHighestStraight().getRank());
    }

    public int compareFourOfAKind() {
        int rank1 = p1.getFourKind().getRank();
        int rank2 = p2.getFourKind().getRank();
        int cmp = Integer.compare(rank1, rank2);
        //higher fours wins
        if(cmp != 0)
            return cmp;

        int p1HandRank = p1.getHand().get(0).getRank();
        int p2HandRank = p2.getHand().get(0).getRank();

        //keep only last card on table which is not part of fours combination
        List<Card> excludedFromCommunity = excludeCards(communityCards, List.of(p1.getFourKind(), p1.getFourKind(), p1.getFourKind(), p1.getFourKind()));

        Card lastCardOnBoard = excludedFromCommunity.get(0);
        int lastCardRank = lastCardOnBoard.getRank();

        //If The “Kicker On The Board,” The Fifth Community Card, Is Greater Than Any Of The Participants' Hole Cards, The Pot Is Divided.
        if(p1HandRank <= lastCardRank && p2HandRank <= lastCardRank){
            return 0;
        } else
            return Integer.compare(p1HandRank, p2HandRank);


    }

    public int compareFullHouse() {
        Card p1Triple = p1.getThreeKind();
        Card p2Triple = p2.getThreeKind();

        int cmp = Integer.compare(p1Triple.getRank(), p2Triple.getRank());
        //return if pairs or triples are not same
        if(cmp != 0){
            return cmp;
        }

        Card pair1 = p1.getPair();
        Card pair2 = p2.getPair();

        return Integer.compare(pair1.getRank(), pair2.getRank());

    }

    public int comparePairOrTriple(Card player1Pair, Card player2Pair) {
        int cmp = compareKickers();
        if(cmp == 0){
            return cmp;
        }
        cmp = Integer.compare(player1Pair.getRank(), player2Pair.getRank());
        //return if pairs or triples are not same
        if(cmp != 0){
            return cmp;
        }

        // Compare the kickers
        return compareKickers();

    }

    public int compareThreeOfAKind() {
        //there can't be same triples against each other in showdown
        Card threeOfAKind1 = p1.getThreeKind();
        Card threeOfAKind2 = p2.getThreeKind();

        return comparePairOrTriple(threeOfAKind1, threeOfAKind2);
    }

    public int compareTwoPairs() {

        // Find the higher pair in each hand
        var cmp = compareKickers();
        if(cmp == 0){
            return cmp;
        }
        cmp = Integer.compare(p1.getPair().getRank(), p2.getPair().getRank());
        if(cmp != 0){
            return cmp;
        }

        // Find the lower pair
        cmp = Integer.compare(p1.getSecondPair().getRank(), p2.getSecondPair().getRank());
        if(cmp != 0){
            return cmp;
        }

        // Compare the kickers
        return compareKickers();

    }

    public int comparePair() {

        Card pair1 = p1.getPair();
        Card pair2 = p2.getPair();

        return comparePairOrTriple(pair1, pair2);

    }

    private int compareKickers() {
        List<Card> tableWithHandp1 = new ArrayList<>(communityCards);
        tableWithHandp1.addAll(p1.getHand());
        tableWithHandp1.sort(Comparator.comparingInt(Card::getRank).reversed());

        List<Card> tableWithHand = new ArrayList<>(communityCards);
        tableWithHand.addAll(p2.getHand());
        tableWithHand.sort(Comparator.comparingInt(Card::getRank).reversed());

        //compare rest of cards, higher card wins, up to five cards (size of community cards)
        for(int i = 0; i < communityCards.size(); i++){
            Card p1card = tableWithHandp1.get(i);
            Card p2card = tableWithHand.get(i);

            if(p1card.getRank() != p2card.getRank())
                return Integer.compare(p1card.getRank(), p2card.getRank());

        }
        return 0;
    }

    private List<Card> excludeCards(List<Card> cards, List<Card> excludedCards) {
        List<Card> result = new ArrayList<>();
        for(Card card : cards){
            if(!excludedCards.contains(card))
                result.add(card);

        }
        return result;
    }


}



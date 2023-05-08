package Logic;

import java.util.*;
import java.util.stream.Collectors;


public class HandEvaluator implements Comparator<Player>{

    private final int MIN_FLUSH_SIZE = 5;
    private final ArrayList<Card> handAndTable;

    private final Player player;
    private Card previousDouble;
    private boolean isPair;
    private boolean isTriple;

    public HandEvaluator(Player evaulatingPlayer, ArrayList<Card> cardsOnTable) {
        player = evaulatingPlayer;
        handAndTable = new ArrayList<>(cardsOnTable);
        handAndTable.addAll(evaulatingPlayer.getHand());
        // sort the cards by rank
        handAndTable.sort(Comparator.comparingInt(Card::getRank));

        //System.out.println("cards on table and hand:");
        //System.out.println(handAndTable);
        setDefaultState();
    }

    private void setDefaultState() {
        player.setWinner(false);
        player.setPair(null);
        isPair = false;
        isTriple = false;
    }



    public void evaulateClassificationRank() {
        for(Card card : handAndTable
        ){
            checkForPairs(card);
        }
        checkForStronger();

    }

    private void checkForPairs(Card currentCard) {
        int freq = (int) handAndTable.stream().filter(card -> card.getRank() == currentCard.getRank()).count();

        if(freq == 1)
            return;

        if(player.getcRank().getValue() < ClassificationRank.FOURS.getValue()){
            if(freq == 4){
                player.setFourKind(currentCard);
                player.setClassificationRank(ClassificationRank.FOURS);
                player.setPair(currentCard);
            }
            if(freq == 3){
                isTriple = true;
                player.setThreeKind(currentCard);
                checkForFullhouse();

                if(player.getcRank().getValue() < ClassificationRank.TRIPLE.getValue()){
                    player.setClassificationRank(ClassificationRank.TRIPLE);

                    if(previousDouble == null)
                        previousDouble = currentCard;
                    else if(currentCard.getRank() != previousDouble.getRank())
                        checkForFullhouse();

                    Card biggerPair = currentCard.getRank() > previousDouble.getRank() ? currentCard : previousDouble;
                    player.setPair(biggerPair);
                }
            }
            checkForTwoOfAKind(currentCard, freq);
        }
    }


    /*private void checkForHighestCard() {
        ArrayList<Player> winners = new ArrayList<>();

        //sort players by their highest cards in hands
        listOfPlayers.sort(this);

        int currHighCard = 0;
        for(Player p : listOfPlayers
        ){
            int highestCardOfPlayer = p.getHand().get(0).getRank();
            //check if current card is highest
            if(highestCardOfPlayer >= currHighCard){
                currHighCard = highestCardOfPlayer;
                //set winning player
                winners.add(p);
            }
        }

        if(winners.size() > 1){

            for(int i = 1; i < winners.size(); i++){
                //highest first card here
                Player p = winners.get(i);
                //highest card of previous player
                Player prev_p = winners.get(i - 1);
                //result of compareCards method
                int result = compare(p, prev_p);

                //if cards are not same, we can delete other player safely, must have lower card after sortion
                if(result > 0){
                    winners.remove(p);
                    i--;
                } else{
                    System.out.println("BOTH HAVE SAME HANDS BROOOO");
                }
            }
        }

        winners.forEach(this::setWinner);

    }*/

    private void checkForFullhouse() {
        if(player.getcRank().getValue() < ClassificationRank.FULLHOUSE.getValue())
            if(isTriple && isPair)
                player.setClassificationRank(ClassificationRank.FULLHOUSE);
    }

    private void checkForFlush() {
        List<Card> cards = new ArrayList<>(handAndTable);

        for (int i = 0; i <= cards.size() - MIN_FLUSH_SIZE; i++) {
            boolean isStraight = true;
            for (int j = i; j < i + MIN_FLUSH_SIZE - 1; j++) {
                if (cards.get(j).getRank() - cards.get(j + 1).getRank() != 1) {
                    isStraight = false;
                    break;
                }
            }
            if (isStraight) {
                // set the player's biggest straight card to the last card in the straight
                player.setBiggestStraight(cards.get(i));
                player.setClassificationRank(ClassificationRank.STRAIGHT);
                return;
            }
        }
    }

    private void checkForRoyalFlush() {

        List<Integer> ROYAL_FLUSH_VALUES = Arrays.asList(14, 13, 12, 11, 10);
        ArrayList<Integer> values = new ArrayList<>();

        handAndTable.forEach(card -> values.add(card.getRank()));

        if(values.containsAll(ROYAL_FLUSH_VALUES))
            player.setClassificationRank(ClassificationRank.ROYAL_FLUSH);

    }


    private void checkForStronger() {

        ArrayList<CardEnums.cSymbol> straightFlush = new ArrayList<>();
        // count the number of consecutive cards
        int counter = 1;
        for(int i = 1; i < handAndTable.size(); i++){
            if(handAndTable.get(i).getRank() == handAndTable.get(i - 1).getRank() + 1){
                counter++;
                straightFlush.add(handAndTable.get(i).getCardSymbol());
                straightFlush.add(handAndTable.get(i - 1).getCardSymbol());
            } else if(handAndTable.get(i).getRank() != handAndTable.get(i - 1).getRank()){
                counter = 1;
                straightFlush.clear();
            }

            // if we have 5 consecutive cards, we have a straight
            if(counter >= MIN_FLUSH_SIZE){

                player.setClassificationRank(ClassificationRank.STRAIGHT);
                player.setBiggestStraight(handAndTable.get(i));

                Set<CardEnums.cSymbol> symbolSet = new HashSet<>(straightFlush);

                //The HashSet only stores unique elements, so if you try to add an element that's already in the set, it won't be added again.
                // Therefore, you can use the size of the set to check if all the elements are the same
                if(symbolSet.size() == 1){
                    player.setClassificationRank(ClassificationRank.STRAIGHT_FLUSH);
                    checkForRoyalFlush();
                }
            }
        }

        if(player.getcRank().getValue() < ClassificationRank.STRAIGHT_FLUSH.getValue())
            checkForLowestStraight();

        if(player.getcRank().getValue() < ClassificationRank.FLUSH.getValue())
            checkForFlush();


    }


    private void checkForLowestStraight() {

        final List<Integer> LOWER_STRAIGHT_VALUES = Arrays.asList(14, 5, 4, 3, 2);
        ArrayList<Integer> values = new ArrayList<>();

        handAndTable.forEach(card -> values.add(card.getRank()));

        if(values.containsAll(LOWER_STRAIGHT_VALUES)){

            Set<CardEnums.cSymbol> symbolsSet = handAndTable.stream().map(Card::getCardSymbol).collect(Collectors.toSet());
            boolean containsSameSymbols = symbolsSet.size() == 1;

            if(containsSameSymbols && LOWER_STRAIGHT_VALUES.containsAll(handAndTable.stream().map(Card::getRank).toList())){
                // The list contains only cards with the same symbol and is a lower straight
                player.setClassificationRank(ClassificationRank.STRAIGHT_FLUSH);
            } else if(player.getcRank().getValue() < ClassificationRank.LOWERSTRAIGHT.getValue())
                player.setClassificationRank(ClassificationRank.LOWERSTRAIGHT);

        }

    }

    private void checkForTwoOfAKind(Card currentCard, int freq) {

        int MIN_DOUBLES_SIZE = 2;
        if(isPair){
            if(player.getcRank().getValue() < ClassificationRank.DOUBLE_PAIR.getValue()){
                if(currentCard.getRank() != previousDouble.getRank()){
                    Card biggerPair = currentCard.getRank() > previousDouble.getRank() ? currentCard : previousDouble;
                    player.setPair(biggerPair);
                    previousDouble = biggerPair;
                    player.setClassificationRank(ClassificationRank.DOUBLE_PAIR);
                }
            }
        } else if(freq == MIN_DOUBLES_SIZE){
            isPair = true;
            checkForFullhouse();

            if(player.getcRank().getValue() < ClassificationRank.TRIPLE.getValue()){
                previousDouble = currentCard;
                player.setClassificationRank(ClassificationRank.PAIR);
                player.setPair(currentCard);
                isPair = true;
            }
        }

    }

    ///compare two symbols of cards (ACE, KING, THREE...) and sort which one is bigger in ranking
    ///cards are already sorted he
    @Override
    public int compare(Player o1, Player o2) {
        int c1 = o1.getHand().get(0).getRank();
        int c2 = o2.getHand().get(0).getRank();

        return c1 == c2 ? compareSecondCard(o1, o2) : Integer.compare(c1, c2);

    }


    private int compareSecondCard(Player o1, Player o2) {
        int c1 = o1.getHand().get(1).getRank();
        int c2 = o2.getHand().get(1).getRank();

        return c1 == c2 ? 0 : Integer.compare(c1, c2);

    }

}





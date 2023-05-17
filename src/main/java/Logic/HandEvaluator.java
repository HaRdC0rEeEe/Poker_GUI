package Logic;

import Enums.CardEnums;
import Enums.ClassificationRank;

import java.util.*;
import java.util.stream.Collectors;


public class HandEvaluator{

    public static ArrayList<Card> communityCards;
    private final ArrayList<Card> handAndTable;
    private final Player player;
    private Card previousDouble;
    private Card previousTriple;
    private boolean isPair;
    private boolean isTriple;

    public HandEvaluator(Player evaulatingPlayer, ArrayList<Card> cardsOnTable) {
        player = evaulatingPlayer;
        communityCards = cardsOnTable;
        communityCards.sort(Comparator.comparingInt(Card::getRank));
        player.getHand().sort(Comparator.comparingInt(Card::getRank).reversed());

        handAndTable = new ArrayList<>(communityCards);
        handAndTable.addAll(evaulatingPlayer.getHand());
        handAndTable.sort(Comparator.comparingInt(Card::getRank));

        setDefaultState();
    }

    private void setDefaultState() {
        player.setWinner(false);
        player.setClassificationRank(ClassificationRank.HIGH_CARD);
        player.setPair(null);
        player.setBiggestStraight(null);
        player.setThreeKind(null);
        player.setFlush(null);
        player.setFourKind(null);
        previousDouble = null;
        previousTriple = null;
        isPair = false;
        isTriple = false;
    }


    public void evaulateClassificationRank() {

        for(Card card : handAndTable){
            checkForPairs(card);
        }

        checkForStronger();
        checkForWeakest();

    }

    private void checkForWeakest() {
        //case where player has no combo and his card is not bigger than the smallest community card
        //there can only be tied in this scenario if every other player has same rank
        if(player.getcRank() == ClassificationRank.HIGH_CARD && !communityCards.isEmpty()){
            List<Card> tmpList = new ArrayList<>(player.getHand());
            tmpList.sort(Comparator.comparingInt(Card::getRank));
            if(tmpList.get(1).getRank() < communityCards.get(0).getRank())
                player.setClassificationRank(ClassificationRank.HIGH_COMMUNITY_CARD);
        }
    }

    private void checkForPairs(Card currentCard) {
        int freq = (int) handAndTable.stream().filter(card -> card.getRank() == currentCard.getRank()).count();

        if(freq == 1)
            return;

        if(player.getcRank().getValue() < ClassificationRank.FOURS.getValue()){
            if(freq == 4){
                player.setClassificationRank(ClassificationRank.FOURS);
                player.setFourKind(currentCard);
            }
            if(freq == 3){
                if(player.getcRank().getValue() < ClassificationRank.TRIPLE.getValue())
                    player.setClassificationRank(ClassificationRank.TRIPLE);

                if(previousTriple != null){
                    //condition will trigger only if player has another triple in community cards hence triggering Full house
                    if(previousTriple.getRank() != currentCard.getRank()){
                        isPair = true;
                        previousTriple = currentCard.getRank() < previousTriple.getRank() ? currentCard : previousTriple;
                        //set lower triple as pair
                        player.setPair(previousTriple);
                    }
                    previousTriple = currentCard.getRank() > previousTriple.getRank() ? currentCard : previousTriple;

                } else
                    previousTriple = currentCard;

                player.setThreeKind(previousTriple);
                isTriple = true;


            } else
                checkForTwoOfAKind(currentCard, freq);

            checkForFullhouse();

        }
    }

    private void checkForFullhouse() {
        if(isTriple && isPair)
            if(player.getcRank().getValue() < ClassificationRank.FULLHOUSE.getValue())
                player.setClassificationRank(ClassificationRank.FULLHOUSE);
    }

    private void checkForRoyalFlush() {

        List<Integer> ROYAL_FLUSH_VALUES = Arrays.asList(14, 13, 12, 11, 10);

        ArrayList<Integer> values = new ArrayList<>(player.getFlush());

        if(values.containsAll(ROYAL_FLUSH_VALUES))
            player.setClassificationRank(ClassificationRank.ROYAL_FLUSH);

    }

    private void checkForStronger() {

        Hashtable<CardEnums.cSymbol, Integer> symbolsTable = new Hashtable<>();
        for(CardEnums.cSymbol symbol : CardEnums.cSymbol.values()){
            symbolsTable.put(symbol, 0);
        }


        //add first symbol to flush map
        symbolsTable.put(handAndTable.get(0).getCardSymbol(), 1);

        checkForStraight(symbolsTable);

        checkForFlush(symbolsTable);

        if(player.getcRank().getValue() < ClassificationRank.STRAIGHT.getValue())
            checkForLowestStraight();

    }

    private void checkForStraight(Hashtable<CardEnums.cSymbol, Integer> symbolsTable) {
        // count the number of consecutive cards
        int counter = 1;
        for(int i = 1; i < handAndTable.size(); i++){
            CardEnums.cSymbol currSymbol = handAndTable.get(i).getCardSymbol();
            int currCard = handAndTable.get(i).getRank();
            int prevCard = handAndTable.get(i - 1).getRank();

            //increment current symbol in map
            symbolsTable.put(currSymbol, symbolsTable.get(currSymbol) + 1);

            if(currCard == prevCard + 1)
                counter++;

            else if(currCard != prevCard)
                counter = 1; //skip for same cards

            // if we have 5 consecutive cards, we have a straight
            int MIN_FLUSH_SIZE = 5;
            if(counter >= MIN_FLUSH_SIZE){
                player.setClassificationRank(ClassificationRank.STRAIGHT);
                player.setBiggestStraight(handAndTable.get(i));

            }
        }
    }

    private void checkForFlush(Hashtable<CardEnums.cSymbol, Integer> symbolsTable) {
        //keep only valid symbol count for flush
        symbolsTable.values().removeIf(count -> count < 5);

        if(symbolsTable.size() == 1){
            //collection which will be sorted, will include cards and their respective symbols
            ArrayList<Integer> flushList = new ArrayList<>();

            CardEnums.cSymbol symbol = symbolsTable.keys().nextElement();
            for(Card card : handAndTable){
                if(card.getCardSymbol() == symbol)
                    flushList.add(card.getRank());
            }

            //highest card comes first
            Collections.reverse(flushList);
            player.setFlush(flushList);

            //if symbolSet contains only 1, player has flush and if he already has a straight he must have straight flush
            if(player.getcRank().getValue() == ClassificationRank.STRAIGHT.getValue()){
                player.setClassificationRank(ClassificationRank.STRAIGHT_FLUSH);
                checkForRoyalFlush();
            } else if(player.getcRank().getValue() < ClassificationRank.FLUSH.getValue()){
                player.setClassificationRank(ClassificationRank.FLUSH);

            }
        }
    }


    private void checkForLowestStraight() {

        final List<Integer> LOWER_STRAIGHT_VALUES = Arrays.asList(14, 5, 4, 3, 2);
        ArrayList<Integer> values = new ArrayList<>();

        handAndTable.forEach(card -> values.add(card.getRank()));

        Set<CardEnums.cSymbol> symbolsSet = handAndTable.stream().map(Card::getCardSymbol).collect(Collectors.toSet());
        boolean containsSameSymbols = symbolsSet.size() == 1;

        if(values.containsAll(LOWER_STRAIGHT_VALUES)){

            for(int i = 1; i < handAndTable.size(); i++){

                int currCard = handAndTable.get(i).getRank();
                int prevCard = handAndTable.get(i - 1).getRank();

                if(currCard == prevCard + 1)
                    //if(containsSameSymbols)
                    player.setBiggestStraight(handAndTable.get(i));

            }


            //if(containsSameSymbols && LOWER_STRAIGHT_VALUES.containsAll(handAndTable.stream().map(Card::getRank).toList())){
            if(containsSameSymbols){
                // The list contains only cards with the same symbol and is a lower straight
                if(player.getcRank().getValue() < ClassificationRank.STRAIGHT_FLUSH.getValue())
                    player.setClassificationRank(ClassificationRank.STRAIGHT_FLUSH);
            } else if(player.getcRank().getValue() < ClassificationRank.LOWERSTRAIGHT.getValue())
                player.setClassificationRank(ClassificationRank.LOWERSTRAIGHT);

        }

    }

    private void checkForTwoOfAKind(Card currentCard, int freq) {

        int MIN_DOUBLES_SIZE = 2;
        if(isPair){
            if(currentCard.getRank() != previousDouble.getRank()){
                if(player.getcRank().getValue() <= ClassificationRank.DOUBLE_PAIR.getValue())
                    player.setClassificationRank(ClassificationRank.DOUBLE_PAIR);
                Card biggerPair = currentCard.getRank() > previousDouble.getRank() ? currentCard : previousDouble;
                player.setPair(biggerPair);
                player.setSecondPair(currentCard);

            }
        } else if(freq >= MIN_DOUBLES_SIZE){
            if(player.getcRank().getValue() <= ClassificationRank.PAIR.getValue())
                player.setClassificationRank(ClassificationRank.PAIR);

            if(previousDouble != null)
                previousDouble = currentCard.getRank() > previousDouble.getRank() ? currentCard : previousDouble;

            else
                previousDouble = currentCard;

            player.setPair(previousDouble);
            isPair = true;
        }
    }

}





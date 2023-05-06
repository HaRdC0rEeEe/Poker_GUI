package Logic;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class HighCardTest{

    @Test
    void compareHighCards() {

        Card c1 = new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.SPADES);
        Card c2 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.SPADES);
        Card c3 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.CLUBS);
        Card c4 = new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.HEARTS);
        Card c5 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        ArrayList<Card> communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        // create a player with the hand
        Player player1 = new Player("Test player 1");
        player1.drawCard(new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.HEARTS));

        Player player2 = new Player("Test player 2");
        player2.drawCard(new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS));

        // create an instance of the evaluator
        var ev = new HandEvaluator(player1, communityCards);
        ev.setDefaultState();
        ev.evaulateClassificationRank();
        ev = new HandEvaluator(player2, communityCards);
        ev.setDefaultState();
        ev.evaulateClassificationRank();

        setWinners(player1, player2);

        assertTrue(player1.isWinner());
        assertTrue(player2.isWinner());
        //assertFalse(player2.isWinner());
        //assertFalse(player2.isWinner());
        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());


    }

    //If both players have the same high card in Texas Hold'em poker, the next step is to compare the second-highest card, then the third-highest card, and so on until a winner is determined. If the two players have identical hands, the pot is split between them.

    @Test
    void HighCardSameHandsPotSplit(){

        Card c1 = new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.SPADES);
        Card c2 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.SPADES);
        Card c3 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.CLUBS);
        Card c4 = new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.HEARTS);
        Card c5 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        ArrayList<Card> communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        Player player1 = new Player("Test player 1");
        player1.drawCard(new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.HEARTS));

        Player player2 = new Player("Test player 2");
        player2.drawCard(new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS));

        var ev = new HandEvaluator(player1, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaulateClassificationRank();

        setWinners(player1, player2);

        assertTrue(player1.isWinner());
        assertTrue(player2.isWinner());
        //assertFalse(player2.isWinner());
        //assertFalse(player2.isWinner());
        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());
    }

    //In another scenario, both players have an Ace and a King as their hole cards, and the community cards are Two, Three, Four, Five, and Six.
    // Both players have a King-high hand, and their next highest cards are both Aces.
    // In this case, the pot is split between the two players since they have identical hands.
    @Test
    void HighCardSameHighCardCompareUpToFourHighestCardsIfTruePotSplit(){

        Card c1 = new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.SPADES);
        Card c2 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.SPADES);
        Card c3 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.CLUBS);
        Card c4 = new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.HEARTS);
        Card c5 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        ArrayList<Card> communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        Player player1 = new Player("Test player 1");
        player1.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS));

        Player player2 = new Player("Test player 2");
        player2.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));

        var ev = new HandEvaluator(player1, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaulateClassificationRank();

        setWinners(player1, player2);

        assertTrue(player1.isWinner());
        assertTrue(player2.isWinner());
        //assertFalse(player2.isWinner());
        //assertFalse(player2.isWinner());
        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());
    }


    //In another scenario, both players have an Ace and a King as their hole cards, and the community cards are Two, Three, Four, Five, and Six.
    // Both players have a King-high hand, and their next highest cards are both Aces.
    // In this case, the pot is split between the two players since they have identical hands.
    @Test
    void FirstPlayerHasHigherCard(){

        Card c1 = new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.SPADES);
        Card c2 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.SPADES);
        Card c3 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.CLUBS);
        Card c4 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        Card c5 = new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.HEARTS);
        ArrayList<Card> communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        Player player1 = new Player("Test player 1");
        player1.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.HEARTS));

        Player player2 = new Player("Test player 2");
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.CLUBS));

        var ev = new HandEvaluator(player1, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaulateClassificationRank();

        setWinners(player1, player2);

        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());

        assertTrue(player1.isWinner());
        assertFalse(player2.isWinner());
        //assertFalse(player2.isWinner());
        //assertFalse(player2.isWinner());


    }

    @Test
    void SecondPlayerHasHigherCard(){

        Card c1 = new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.SPADES);
        Card c2 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.SPADES);
        Card c3 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.CLUBS);
        Card c4 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        Card c5 = new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.HEARTS);
        ArrayList<Card> communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        Player player1 = new Player("Test player 1");
        player1.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.HEARTS));

        Player player2 = new Player("Test player 2");
        player2.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.CLUBS));

        var ev = new HandEvaluator(player1, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaulateClassificationRank();

        setWinners(player1, player2);

        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());

        assertFalse(player1.isWinner());
        assertTrue(player2.isWinner());
        //assertFalse(player2.isWinner());
        //assertFalse(player2.isWinner());


    }

    //Both players have the same high card and the remaining community cards do not make any valid hand.
    // In this case, the pot is split between the two players.
    //Player 1 has a hand of 8 of spades and 4 of diamonds, and Player 2 has a hand of 7 of hearts and 2 of clubs.
    // The community cards on the board are 10 of diamonds, 6 of spades, 3 of hearts, Ace of clubs, and King of spades.
    //A K 10 8 6 4 3
    //A K 10 7 6 3 2
    @Test
    void HighCardNotSameHighCardCompareUpToFourHighestCardsIfTruePotSplit(){

        Card c1 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.SPADES);
        Card c2 = new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.SPADES);
        Card c3 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.CLUBS);
        Card c4 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS);
        Card c5 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS);
        ArrayList<Card> communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        Player player1 = new Player("Test player 1");
        player1.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.HEARTS));

        Player player2 = new Player("Test player 2");
        player2.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS));

        var ev = new HandEvaluator(player1, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaulateClassificationRank();

        setWinners(player1, player2);

        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());

        assertTrue(player1.isWinner());
        assertFalse(player2.isWinner());


    }



    //TODO gotta fix setWinners method, player with bigger pair must be added to table then delete everyone with 0 and revalidate it
    private void setWinners(Player... playersA) {
        Hashtable<Player, Integer> resultTable = new Hashtable<>();
        List<Player> players = new ArrayList<>(List.of(playersA));


        for(int i = 1; i < players.size(); i++){
            //comparator will compare first player with player which was added to list last
            int result = new ComparatorByResult().compare(players.get(i), players.get(i-1));

            //Either winner will be one with the highest combo (1) or multiple players with same combo (multiple 0)
            if(result == 0)
                resultTable.put(players.get(i), result);
            else if(result > 0)
                resultTable.put(players.get(i-1), result);
            else
                resultTable.put(players.get(i-1), result);

            if(resultTable.contains(0)){
                if(resultTable.contains(1)){
                    resultTable.values().removeIf(value -> value == 0);
                    i = 0;
                }
            }

        }
        List<Player> winners = new ArrayList<>(resultTable.keySet());
        winners.forEach(p -> p.setWinner(true));
    }
}
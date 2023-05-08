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
        ev.evaulateClassificationRank();
        ev = new HandEvaluator(player2, communityCards);
        ev.evaulateClassificationRank();
        var players = new ArrayList<>(List.of(player1, player2));
        setWinners(players);

        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());

        assertTrue(player1.isWinner());
        assertTrue(player2.isWinner());



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

        var players = new ArrayList<>(List.of(player1, player2));
        setWinners(players);

        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());

        assertTrue(player1.isWinner());
        assertTrue(player2.isWinner());


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

        var players = new ArrayList<>(List.of(player1, player2));
        setWinners(players);

        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());

        assertTrue(player1.isWinner());
        assertTrue(player2.isWinner());

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

        var players = new ArrayList<>(List.of(player1, player2));
        setWinners(players);

        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());

        assertTrue(player1.isWinner());
        assertFalse(player2.isWinner());


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

        var players = new ArrayList<>(List.of(player1, player2));
        setWinners(players);

        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());

        assertFalse(player1.isWinner());
        assertTrue(player2.isWinner());


    }


    //Player 1 has a hand of 8 of spades and 4 of diamonds, and Player 2 has a hand of 7 of hearts and 2 of clubs.
    // The community cards on the board are 10 of diamonds, 6 of spades, 3 of hearts, Ace of clubs, and King of spades.
    //A K 10 8 6 4 3
    //A K 10 7 6 3 2
    //player 1 should win since he has third highest card
    @Test
    void FirstPlayerHasThirdHighestCard(){

        //was TP5, TP6, TP4, TP3....
        //should be TP6, TP5, TP4, TP3, etc...
        Card c1 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.SPADES);
        Card c2 = new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.SPADES);
        Card c3 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.CLUBS);
        Card c4 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS);
        Card c5 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS);
        ArrayList<Card> communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        Player player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.HEARTS));

        Player player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS));

        Player player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS));

        Player player4 = new Player("TP4");
        player4.drawCard(new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.CLUBS));
        player4.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS));

        Player player5 = new Player("TP5");
        player5.drawCard(new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.CLUBS));
        player5.drawCard(new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.CLUBS));

        Player player6 = new Player("TP6");
        player6.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS));
        player6.drawCard(new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.CLUBS));

        var ev = new HandEvaluator(player1, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player4, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player5, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player6, communityCards);
        ev.evaulateClassificationRank();
        var players = new ArrayList<>(List.of(player1, player2, player3, player4, player6));
        setWinners(players);

        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());

        //assertTrue(player1.isWinner());
        //assertFalse(player2.isWinner());


    }

    //Both players have the same high card and the remaining community cards do not make any valid hand.
    // In this case, the pot is split between the two players.
    @Test
    void NeitherHasHighestCard(){

        Card c1 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.SPADES);
        Card c2 = new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.SPADES);
        Card c3 = new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.CLUBS);
        Card c4 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS);
        Card c5 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS);
        ArrayList<Card> communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        Player player1 = new Player("Test player 1");
        player1.drawCard(new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.HEARTS));

        Player player2 = new Player("Test player 2");
        player2.drawCard(new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS));

        var ev = new HandEvaluator(player1, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaulateClassificationRank();

        var players = new ArrayList<>(List.of(player1, player2));
        setWinners(players);

        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());

        assertTrue(player1.isWinner());
        assertTrue(player2.isWinner());


    }

    //TODO gotta fix setWinners method, player with stronger combo must be added to table
    // then delete everyone with 0 and revalidate it until either everyone has 0 or only one with 1 is left
    //WINNERS:
    //[TP6 [9:3] PAIR |, TP4 [10:2] PAIR |]
    //expected: TP4 [10:2] PAIR |
    private void setWinners(ArrayList<Player> players) {


        //sorted order should be p4 p3 p1 p2
        System.out.println(players+"\n-------------");
        players.sort(new ComparatorByResult());
        System.out.println("Sorted:");
        System.out.println(players);

        ArrayList<Player> winners = new ArrayList<>();
        for(int i = players.size()-1; i > 0; i--){

                if(players.get(i).getcRank().getValue() > players.get(i-1).getcRank().getValue())
                {
                    winners.add(players.get(i));
                    break;
                }
                else
                    winners.add(players.get(i));
            }

        if(winners.size()>1){
            for(int i = winners.size()-1; i > 0; i--){

                Player highestRankedPlayer = winners.get(i);
                Player currentPlayer = winners.get(i-1);
                int result = new ComparatorByResult().compare(highestRankedPlayer, currentPlayer);

                if (result == 0) {
                    winners.add(currentPlayer);
                } else if (result > 0) {
                    winners.clear();
                    winners.add(currentPlayer);
                } else {
                    winners.clear();
                    winners.add(highestRankedPlayer);
                    winners.add(currentPlayer);
                }

            }
        }





        winners.forEach(p -> p.setWinner(true));
        System.out.println("WINNERS:");
        System.out.println(winners);
    }
}

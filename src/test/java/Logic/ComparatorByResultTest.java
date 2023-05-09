package Logic;


import Enums.CardEnums;
import Enums.ClassificationRank;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class ComparatorByResultTest{

    //https://automaticpoker.com/poker-basics/what-happens-if-you-have-the-same-hand-in-poker/
    static List<List<Card>> playerCardCases() {
        return List.of(
                List.of(
                        new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS),
                        new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS)),
                List.of(
                        new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.CLUBS),
                        new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS)),
                List.of(
                        new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.CLUBS),
                        new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS)),
                List.of(
                        new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.CLUBS),
                        new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.CLUBS)),
                List.of(
                        new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.CLUBS),
                        new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.CLUBS)),
                List.of(
                        new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.CLUBS),
                        new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.CLUBS)),
                List.of(
                        new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.CLUBS),
                        new Card(CardEnums.cValue.JACK, CardEnums.cSymbol.CLUBS)),
                List.of(
                        new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS),
                        new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.CLUBS))
        );
    }

    @Test
    void compare() {
    }

    @Test
    void compareStraight() {
    }

    @Test
    void compareFourOfAKind() {
    }










    //If two or more players have the same pair, you use the same methodology as you do for high-card hands.
    // The next highest non-tie card determines the winner.

    //KK753, p1 94, p2 82 -
    //KK10 75 beats KK942
    //88652 Beats 88642
    //AAK83 Beats AAK82

    //KK753 94 82
    @Test
    void HighestPairShouldWin(){

        Card c1 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        Card c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        Card c3 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.CLUBS);
        Card c4 = new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.HEARTS);
        Card c5 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.HEARTS);
        ArrayList<Card> communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        Player player1 = new Player("Test player 1");
        player1.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.HEARTS));

        Player player2 = new Player("Test player 2");
        player2.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS));

        var ev = new HandEvaluator(player1, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaulateClassificationRank();

        setWinners(player1, player2);

        assertTrue(player1.isWinner());
        assertFalse(player2.isWinner());

        assertSame(ClassificationRank.PAIR, player1.getcRank());
        assertSame(ClassificationRank.PAIR, player2.getcRank());
    }

    @Test
    void HighestPairShouldWin2(){

        Card c1 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        Card c2 = new Card(CardEnums.cValue.JACK, CardEnums.cSymbol.SPADES);
        Card c3 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.CLUBS);
        Card c4 = new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.HEARTS);
        Card c5 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.HEARTS);
        ArrayList<Card> communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        Player player1 = new Player("Test player 1");
        player1.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.HEARTS));

        Player player2 = new Player("Test player 2");
        player2.drawCard(new Card(CardEnums.cValue.JACK, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS));

        var ev = new HandEvaluator(player1, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaulateClassificationRank();

        setWinners(player1, player2);

        assertTrue(player1.isWinner());
        assertFalse(player2.isWinner());

        assertSame(ClassificationRank.PAIR, player1.getcRank());
        assertSame(ClassificationRank.PAIR, player2.getcRank());
    }


    @Test
    void HighestPairShouldWin3(){

        Card c1 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        Card c2 = new Card(CardEnums.cValue.JACK, CardEnums.cSymbol.SPADES);
        Card c3 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.CLUBS);
        Card c4 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS);
        Card c5 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.HEARTS);
        ArrayList<Card> communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        Player player1 = new Player("Test player 1");
        player1.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.HEARTS));

        Player player2 = new Player("Test player 2");
        player2.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS));

        var ev = new HandEvaluator(player1, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaulateClassificationRank();

        setWinners(player1, player2);

        assertFalse(player1.isWinner());
        assertTrue(player2.isWinner());

        assertSame(ClassificationRank.PAIR, player1.getcRank());
        assertSame(ClassificationRank.PAIR, player2.getcRank());
    }


    /*


Both players have the same high card and the remaining community cards make a valid hand for both players, such as a straight or flush. In this case, the player with the highest ranking hand wins the pot.

Both players have the same high card and the remaining community cards make a valid hand for only one player. In this case, the player with the valid hand wins the pot.

Both players have the same high card and the remaining community cards make a valid hand for both players, but the hands have the same ranking. In this case, the pot is split between the two players.
    *
    *
    *
    *
Both players have the same high card and the same second-highest card. the pot is split between the two players.

Both players have the same high card and the same second-highest card, but their third-highest cards are different. In this case, the player with the higher third-highest card wins the pot.

Both players have the same high card and the same second-highest card, but their third-highest cards are the same and their fourth-highest cards are different. In this case, the player with the higher fourth-highest card wins the pot.

Both players have the same high card and the same second-highest card, but their third-highest and fourth-highest cards are the same and their fifth-highest cards are different. In this case, the player with the higher fifth-highest card wins the pot.

Both players have the same high card, but their second-highest cards are different. In this case, the player with the higher second-highest card wins the pot.

Both players have the same high card and the same second-highest card, but their third-highest and fourth-highest cards are different. In this case, the player with the higher fifth-highest card wins the pot.

Both players have the same high card, but their second-highest, third-highest, fourth-highest, and fifth-highest cards are all different. In this case, the player with the higher second-highest card wins the pot.
    * */




    void testAdd(Player a, Player b, int expected) {

        List<List<Card>> playerCardCases = playerCardCases();

        for(List<Card> cards : playerCardCases){
            for(Card card : cards){
                a.drawCard(card);
                b.drawCard(card);
                //assertEquals(expected, result);
                assertSame(ClassificationRank.HIGH_CARD, a.getcRank());
                assertSame(ClassificationRank.HIGH_CARD, b.getcRank());
                a.Fold();
                b.Fold();
            }
        }


    }

    @Test
    void compareFullHouse() {
    }

    @Test
    void comparePairOrTriple() {
    }

    @Test
    void compareThreeOfAKind() {
    }

    @Test
    void compareTwoPairs() {
    }

    @Test
    void comparePair() {
    }

    @Test
    void compareHighestCards() {
    }

    //P1 compared to P2
    private void setWinners(Player... playersA) {
        List<Player> players = new ArrayList<>(List.of(playersA));
        List<Player> winners = new ArrayList<>();
        winners.add(players.get(0)); // initialize the list with the first player

        for(int i = 1; i < players.size(); i++){
            //comparator will compare first player with player which was added to list last
            int result = new ComparatorByResult().compare(winners.get(0), players.get(i));
            //Either winner will be one with the highest combo (1) or multiple players with same combo (multiple 0)
            if(result > 0){
                winners.clear();
                winners.add(players.get(i-1));
            } else if(result == 0){
                winners.add(players.get(i));
            }
            else{
                winners.clear();
                winners.add(players.get(i));
            }

        }
        winners.forEach(p -> p.setWinner(true));
    }
}


/*
Both players have the same high card:
Player 1: Ace of Hearts, King of Clubs
Player 2: Ace of Clubs, Queen of Diamonds
Community Cards: 2 of Spades, 4 of Hearts, 6 of Diamonds, 9 of Clubs, King of Hearts

Both players have the same pair:
Player 1: King of Hearts, King of Clubs
Player 2: King of Spades, Queen of Diamonds
Community Cards: Ace of Hearts, 2 of Spades, 5 of Hearts, 8 of Clubs, King of Diamonds

Both players have the same two pairs:
Player 1: King of Hearts, King of Clubs, Queen of Diamonds, Queen of Clubs
Player 2: King of Spades, King of Diamonds, Queen of Spades, Queen of Hearts
Community Cards: 2 of Spades, 5 of Hearts, 7 of Clubs, 8 of Diamonds, 10 of Hearts

Both players have the same three of a kind:
Player 1: King of Hearts, King of Clubs, King of Diamonds
Player 2: King of Spades, King of Hearts, King of Clubs
Community Cards: Ace of Diamonds, 3 of Hearts, 4 of Diamonds, 8 of Clubs, 9 of Spades

Both players have the same straight:
Player 1: 10 of Spades, 9 of Hearts
Player 2: 10 of Clubs, 9 of Diamonds
Community Cards: Jack of Hearts, Queen of Diamonds, King of Clubs, Ace of Spades, 8 of Hearts

Both players have the same flush:
Player 1: Ace of Hearts, 5 of Hearts
Player 2: Ace of Spades, 5 of Spades
Community Cards: 2 of Hearts, 8 of Hearts, 10 of Hearts, Queen of Hearts, King of Diamonds

Both players have the same full house:
Player 1: King of Hearts, King of Clubs, King of Spades, Queen of Hearts, Queen of Clubs
Player 2: King of Diamonds, King of Clubs, King of Hearts, Queen of Diamonds, Queen of Spades
Community Cards: 2 of Hearts, 2 of Clubs, 2 of Diamonds, Ace of Spades, Ace of Hearts

Both players have the same four of a kind:
Player 1: King of Hearts, King of Clubs, King of Spades, King of Diamonds
Player 2: King of Clubs, King of Diamonds, King of Spades, King of Hearts
Community Cards: 2 of Hearts, 5 of Clubs, 7 of Diamonds, 10 of Hearts, Ace of Spades

Both players have the same straight flush:
Player 1: 10 of Hearts, 9 of Hearts
Player 2: 10 of Diamonds, 9 of Diamonds
Community Cards: Jack of Hearts, Queen of Hearts, King of Hearts, Ace of Hearts, 8 of Clubs

Both players have a flush (e.g. 10H, 8H in hand and 6H, 2H, 9H, 5H, AH on the table)
Both players have a straight flush (e.g. 6C, 7C in hand and 5C, 4C, 8C, 9C, TC on the table)
Both players have a royal flush (e.g. AC, KC in hand and QC, JC, TC, 9C, AC on the table)
Both players have a full house with the same triple and pair (e.g. 5D, 5S in hand and 5H, 5C, 6S, 6H, 6D on the table)
Both players have a four-of-a-kind with the same value (e.g. 8D, 8S in hand and 8C, 8H, 8S, 2C, 3D on the table)
Both players have a straight flush and the same highest card (e.g. 8H, 9H in hand and 6H, 7H, TH, JH, QH on the table)
Both players have a royal flush and the same highest card (e.g. AH, KH in hand and QH, JH, TH, 9H, AH on the table)
Both players have a full house with the same triple but different pairs (e.g. 7S, 7D in hand and 7H, QS, QD, QH, 6C on the table)
Both players have a four-of-a-kind with the same value but different kicker cards (e.g. 9C, 9D in hand and 9S, 9H, AC, QC, 2S on the table)
Both players have a straight flush and the same highest card, but different second highest cards (e.g. 7D, 8D in hand and 5D, 6D, 9D, TD, JD on the table)
Both players have a royal flush and the same highest card, but different second highest cards (e.g. KC, QC in hand and JC, TC, 9C, AC, KC on the table)*/

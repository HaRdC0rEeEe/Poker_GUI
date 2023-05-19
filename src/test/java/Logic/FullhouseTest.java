package Logic;

import Enums.CardEnums;
import Enums.ClassificationRank;
import jdk.jfr.Label;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FullhouseTest{

    ArrayList<Card> communityCards;
    ArrayList<Player> players;
    private Player player1, player2, player3;
    private HandEvaluator ev;
    private Card c1, c2, c3, c4, c5;

    @Test
    @Label("Test case 1")
    @DisplayName("Second player biggest pair hence winning the pot")
    void SecondPlayerBiggestTripleTest() {

        c1 = new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.FULLHOUSE, player1.getcRank());
        assertSame(ClassificationRank.FULLHOUSE, player2.getcRank());
        assertSame(ClassificationRank.FULLHOUSE, player3.getcRank());

        assertFalse(player1.isWinner());
        assertTrue(player2.isWinner());
        assertFalse(player3.isWinner());

    }

    @Test
    @Label("Test case 2")
    @DisplayName("Second player biggest pair hence winning the pot")
    void SecondPlayerBiggestTripleTest2() {

        c1 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.FULLHOUSE, player1.getcRank());
        assertSame(ClassificationRank.FULLHOUSE, player2.getcRank());
        assertSame(ClassificationRank.FULLHOUSE, player3.getcRank());

        assertFalse(player1.isWinner());
        assertTrue(player2.isWinner());
        assertFalse(player3.isWinner());

    }

    @Test
    @Label("Test case 3")
    @DisplayName("Fullhouse in community cards")
    void FullhouseInCommunityCardsPotSplit() {

        c1 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.FULLHOUSE, player1.getcRank());
        assertSame(ClassificationRank.FULLHOUSE, player2.getcRank());
        assertSame(ClassificationRank.FULLHOUSE, player3.getcRank());

        assertTrue(player1.isWinner());
        assertTrue(player2.isWinner());
        assertTrue(player3.isWinner());

    }

    @Test
    @Label("Test case 4")
    @DisplayName("Second and third player have same triple and same double")
    void SecondPlayerBiggestDoubleTest() {

        c1 = new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.FULLHOUSE, player1.getcRank());
        assertSame(ClassificationRank.FULLHOUSE, player2.getcRank());
        assertSame(ClassificationRank.FULLHOUSE, player3.getcRank());

        assertFalse(player1.isWinner());
        assertTrue(player2.isWinner());
        assertTrue(player3.isWinner());

    }

    @Test
    @Label("Test case 5")
    @DisplayName("Third player has biggest triple")
    void FourthPlayerBiggestTripleTest() {

        c1 = new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));

        Player player4 = new Player("TP4");
        player4.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS));
        player4.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player4, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3, player4));
        Utils.setWinners(players);

        assertSame(ClassificationRank.FULLHOUSE, player1.getcRank());
        assertSame(ClassificationRank.FULLHOUSE, player2.getcRank());
        assertSame(ClassificationRank.FULLHOUSE, player3.getcRank());
        assertSame(ClassificationRank.FULLHOUSE, player4.getcRank());

        assertFalse(player1.isWinner());
        assertFalse(player2.isWinner());
        assertFalse(player3.isWinner());
        assertTrue(player4.isWinner());

    }

    @Test
    @Label("Test case 6")
    @DisplayName("Fullhouse in community cards, first player has higher pair than community hence he wins")
    void FullhouseInCommunityCardsFirstPlayerPlayerHighPair() {

        c1 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.FULLHOUSE, player1.getcRank());
        assertSame(ClassificationRank.FULLHOUSE, player2.getcRank());
        assertSame(ClassificationRank.FULLHOUSE, player3.getcRank());

        assertTrue(player1.isWinner());
        assertFalse(player2.isWinner());
        assertFalse(player3.isWinner());

    }

    @Test
    @Label("Test case 7")
    @DisplayName("Fullhouse in community cards, first player has higher triple than community hence he wins")
    void FullhouseInCommunityCardsFirstPlayerHighTriple() {

        c1 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.FULLHOUSE, player1.getcRank());
        assertSame(ClassificationRank.FULLHOUSE, player2.getcRank());
        assertSame(ClassificationRank.FULLHOUSE, player3.getcRank());

        assertTrue(player1.isWinner());
        assertFalse(player2.isWinner());
        assertFalse(player3.isWinner());

    }

    @Test
    @Label("Test case 8")
    @DisplayName("first player has higher triple from pocket pairs")
    void FullhousePocketPairsTest() {

        c1 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.DOUBLE_PAIR, player1.getcRank());
        assertSame(ClassificationRank.DOUBLE_PAIR, player2.getcRank());
        assertSame(ClassificationRank.FULLHOUSE, player3.getcRank());

        assertFalse(player1.isWinner());
        assertFalse(player2.isWinner());
        assertTrue(player3.isWinner());

    }

    @Test
    @Label("Test case 9")
    @DisplayName("Community Full house, should be tie")
    void FailedTest() {

        c1 = new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.DIAMONDS);
        c2 = new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.HEARTS);
        c3 = new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.DIAMONDS);
        c5 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.SPADES);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2));
        Utils.setWinners(players);

        assertSame(ClassificationRank.FULLHOUSE, player1.getcRank());
        assertSame(ClassificationRank.FULLHOUSE, player2.getcRank());

        assertTrue(player1.isWinner());
        assertTrue(player2.isWinner());


    }


    /*
     * [SIX CLUBS, SIX DIAMONDS, FOUR DIAMONDS, FOUR SPADES, SIX HEARTS]
     * 6 6 6 4 4
     * [Eve [12:8] FULLHOUSE, Pepr [4:3] FULLHOUSE]
     *
     * pair1 was null*/
}

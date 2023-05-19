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

public class StraightTest{
    ArrayList<Card> communityCards;
    ArrayList<Player> players;
    private Player player1, player2, player3;
    private HandEvaluator ev;
    private Card c1, c2, c3, c4, c5;

    @Test
    @Label("Test case 1")
    @DisplayName("Second player biggest straight hence winning")
    void SecondPlayerBiggestStraightTest() {

        c1 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.JACK, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.PAIR, player1.getcRank());
        assertSame(ClassificationRank.STRAIGHT, player2.getcRank());
        assertSame(ClassificationRank.STRAIGHT, player3.getcRank());

        assertFalse(player1.isWinner());
        assertTrue(player2.isWinner());
        assertFalse(player3.isWinner());

    }

    @Test
    @Label("Test case 2")
    @DisplayName("Second and third player same straight, split pot")
    void SecondAndThirdPlayerSameStraightTest() {

        c1 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.JACK, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.PAIR, player1.getcRank());
        assertSame(ClassificationRank.STRAIGHT, player2.getcRank());
        assertSame(ClassificationRank.STRAIGHT, player3.getcRank());

        assertFalse(player1.isWinner());
        assertTrue(player2.isWinner());
        assertTrue(player3.isWinner());

    }

    @Test
    @Label("Test case 3")
    @DisplayName("Community straight hence, split pot")
    void CommunityStraightTest() {

        c1 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.JACK, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.STRAIGHT, player1.getcRank());
        assertSame(ClassificationRank.STRAIGHT, player2.getcRank());
        assertSame(ClassificationRank.STRAIGHT, player3.getcRank());

        assertTrue(player1.isWinner());
        assertTrue(player2.isWinner());
        assertTrue(player3.isWinner());

    }

    @Test
    @Label("Test case 4")
    @DisplayName("First player lowest straight but highest card")
    void CommunityStraightTest2() {

        c1 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.JACK, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.STRAIGHT, player1.getcRank());
        assertSame(ClassificationRank.STRAIGHT, player2.getcRank());
        assertSame(ClassificationRank.STRAIGHT, player3.getcRank());

        assertTrue(player1.isWinner());
        assertFalse(player2.isWinner());
        assertFalse(player3.isWinner());

    }

    @Test
    @Label("Test case 5")
    @DisplayName("Community straight and nobody has bigger card than highest straight card, split")
    void CommunityStraightTest3() {

        c1 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.JACK, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.JACK, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.STRAIGHT, player1.getcRank());
        assertSame(ClassificationRank.STRAIGHT, player2.getcRank());
        assertSame(ClassificationRank.STRAIGHT, player3.getcRank());

        assertTrue(player1.isWinner());
        assertTrue(player2.isWinner());
        assertTrue(player3.isWinner());

    }

    @Test
    @Label("Test case 6")
    @DisplayName("Community lower straight and nobody has bigger card than highest straight card, split")
    void CommunityStraightTest4() {

        c1 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.LOWERSTRAIGHT, player1.getcRank());
        assertSame(ClassificationRank.LOWERSTRAIGHT, player2.getcRank());
        assertSame(ClassificationRank.LOWERSTRAIGHT, player3.getcRank());

        assertTrue(player1.isWinner());
        assertTrue(player2.isWinner());
        assertTrue(player3.isWinner());

    }

    @Test
    @Label("Test case 7")
    @DisplayName("Community lower straight should result in a tie for p2 and p3 regardless of smaller card in hand")
    void CommunityStraightTest5() {

        c1 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.TRIPLE, player1.getcRank());
        assertSame(ClassificationRank.LOWERSTRAIGHT, player2.getcRank());
        assertSame(ClassificationRank.LOWERSTRAIGHT, player3.getcRank());

        assertFalse(player1.isWinner());
        assertTrue(player2.isWinner());
        assertTrue(player3.isWinner());

    }

    @Test
    @Label("Test case 8")
    @DisplayName("P2 has higher straight than everyone else, p2 wins")
    void CommunityStraightTest6() {

        c1 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.TRIPLE, player1.getcRank());
        assertSame(ClassificationRank.STRAIGHT, player2.getcRank());
        assertSame(ClassificationRank.LOWERSTRAIGHT, player3.getcRank());

        assertFalse(player1.isWinner());
        assertTrue(player2.isWinner());
        assertFalse(player3.isWinner());

    }

    @Test
    @Label("Test case 9")
    @DisplayName("P2 and P3 has higher straight than everyone else, both wins")
    void CommunityStraightTest7() {

        c1 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.TRIPLE, player1.getcRank());
        assertSame(ClassificationRank.STRAIGHT, player2.getcRank());
        assertSame(ClassificationRank.STRAIGHT, player3.getcRank());

        assertFalse(player1.isWinner());
        assertTrue(player2.isWinner());
        assertTrue(player3.isWinner());

    }
}

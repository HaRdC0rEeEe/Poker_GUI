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

public class PairsTest{

    ArrayList<Card> communityCards;
    ArrayList<Player> players;
    private Player player1, player2;
    private HandEvaluator ev;
    private Card c1, c2, c3, c4, c5;

    @Test
    @Label("Test case 1")
    @DisplayName("First player has bigger pair, hence winning pot")
    void FirstPlayerBiggerPair() {

        c1 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.CLUBS));

        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        players = new ArrayList<>(List.of(player1, player2));
        Utils.setWinners(players);

        assertSame(ClassificationRank.PAIR, player1.getcRank());
        assertSame(ClassificationRank.PAIR, player2.getcRank());

        assertTrue(player1.isWinner());
        assertFalse(player2.isWinner());

    }

    @Test
    @Label("Test case 2")
    @DisplayName("Second player has bigger pair as his second card, hence winning pot")
    void SecondPlayerBiggerPairSecondCard() {

        c1 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS));

        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        players = new ArrayList<>(List.of(player1, player2));
        Utils.setWinners(players);

        assertSame(ClassificationRank.PAIR, player1.getcRank());
        assertSame(ClassificationRank.PAIR, player2.getcRank());

        assertFalse(player1.isWinner());
        assertTrue(player2.isWinner());

    }

    @Test
    @Label("Test case 3")
    @DisplayName("Both players have same pair, second player will have bigger kicker hence winning the pot")
    void FirstPlayerBiggerKicker() {

        c1 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.JACK, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));

        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        players = new ArrayList<>(List.of(player1, player2));
        Utils.setWinners(players);

        assertSame(ClassificationRank.PAIR, player1.getcRank());
        assertSame(ClassificationRank.PAIR, player2.getcRank());

        assertFalse(player1.isWinner());
        assertTrue(player2.isWinner());

    }

    @Test
    @Label("Test case 4")
    @DisplayName("Both players have same pair, neither have bigger kicker hence splitting the pot")
    void BothHaveSamePairNeitherHasBiggerKicker() {

        c1 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));

        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        players = new ArrayList<>(List.of(player1, player2));
        Utils.setWinners(players);

        assertSame(ClassificationRank.PAIR, player1.getcRank());
        assertSame(ClassificationRank.PAIR, player2.getcRank());

        assertTrue(player1.isWinner());
        assertTrue(player2.isWinner());

    }

    @Test
    @Label("Test case 5")
    @DisplayName("Both players have same pair on table")
    void NeitherHasBiggerKicker() {

        c1 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.CLUBS));

        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        players = new ArrayList<>(List.of(player1, player2));
        Utils.setWinners(players);

        assertSame(ClassificationRank.PAIR, player1.getcRank());
        assertSame(ClassificationRank.PAIR, player2.getcRank());

        assertTrue(player1.isWinner());
        assertTrue(player2.isWinner());

    }

}

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

public class FlushTest{
    ArrayList<Card> communityCards;
    ArrayList<Player> players;
    private Player player1, player2, player3;
    private HandEvaluator ev;
    private Card c1, c2, c3, c4, c5;

    @Test
    @Label("Test case 1")
    @DisplayName("Second player biggest flush hence winning")
    void SecondPlayerBiggestFlushTest() {

        c1 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.HEARTS);
        c3 = new Card(CardEnums.cValue.JACK, CardEnums.cSymbol.HEARTS);
        c4 = new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.CLUBS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS));
        player2.drawCard(new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.HEARTS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.HEARTS));
        player3.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.HEARTS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.FLUSH, player1.getcRank());
        assertSame(ClassificationRank.FLUSH, player2.getcRank());
        assertSame(ClassificationRank.FLUSH, player3.getcRank());

        assertFalse(player1.isWinner());
        assertTrue(player2.isWinner());
        assertFalse(player3.isWinner());

    }

    @Test
    @Label("Test case 2")
    @DisplayName("Third player biggest flush hence winning")
    void SecondPlayerBiggestFlushTest2() {

        c1 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS);
        c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS);
        c3 = new Card(CardEnums.cValue.JACK, CardEnums.cSymbol.HEARTS);
        c4 = new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS);
        c5 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.CLUBS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.HEARTS));
        player2.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.HEARTS));
        player3.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.HEARTS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.FLUSH, player1.getcRank());
        assertSame(ClassificationRank.FLUSH, player2.getcRank());
        assertSame(ClassificationRank.FLUSH, player3.getcRank());

        assertFalse(player1.isWinner());
        assertTrue(player2.isWinner());
        assertFalse(player3.isWinner());

    }


    @Test
    @Label("Test case 3")
    @DisplayName("Third player biggest flush using community hence winning")
    void SecondPlayerBiggestFlushTest3() {

        c1 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS);
        c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS);
        c3 = new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.HEARTS);
        c4 = new Card(CardEnums.cValue.JACK, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS));
        player2.drawCard(new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.HEARTS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.HEARTS));
        player3.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.HEARTS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.FLUSH, player1.getcRank());
        assertSame(ClassificationRank.FLUSH, player2.getcRank());
        assertSame(ClassificationRank.FLUSH, player3.getcRank());

        assertFalse(player1.isWinner());
        assertFalse(player2.isWinner());
        assertTrue(player3.isWinner());

    }

    @Test
    @Label("Test case 4")
    @DisplayName("Second player biggest flush from hand hence winning")
    void SecondPlayerBiggestFlushTest4() {

        c1 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.HEARTS);
        c3 = new Card(CardEnums.cValue.JACK, CardEnums.cSymbol.HEARTS);
        c4 = new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.CLUBS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS));
        player2.drawCard(new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.HEARTS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.HEARTS));
        player3.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.HEARTS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.FLUSH, player1.getcRank());
        assertSame(ClassificationRank.FLUSH, player2.getcRank());
        assertSame(ClassificationRank.FLUSH, player3.getcRank());

        assertFalse(player1.isWinner());
        assertTrue(player2.isWinner());
        assertFalse(player3.isWinner());

    }
}

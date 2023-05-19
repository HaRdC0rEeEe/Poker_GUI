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

public class FoursTest{
    ArrayList<Card> communityCards;
    ArrayList<Player> players;
    private Player player1, player2, player3;
    private HandEvaluator ev;
    private Card c1, c2, c3, c4, c5;

    @Test
    @Label("Test case 1")
    @DisplayName("Second player biggest pair hence winning the pot")
    void SecondPlayerBiggestFoursTest() {

        c1 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.FOURS, player1.getcRank());
        assertSame(ClassificationRank.FOURS, player2.getcRank());
        assertSame(ClassificationRank.FULLHOUSE, player3.getcRank());

        assertFalse(player1.isWinner());
        assertTrue(player2.isWinner());
        assertFalse(player3.isWinner());

    }

    @Test
    @Label("Test case 2")
    @DisplayName("Neither have bigger fours, hence split")
    void SplitPot() {

        c1 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.FOURS, player1.getcRank());
        assertSame(ClassificationRank.FOURS, player2.getcRank());
        assertSame(ClassificationRank.FOURS, player3.getcRank());

        assertTrue(player1.isWinner());
        assertTrue(player2.isWinner());
        assertTrue(player3.isWinner());

    }

    @Test
    @Label("Test case 3")
    @DisplayName("Fours community, second player low card, hence first and third tie win")
    void SecondPlayerLowestCard() {

        c1 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.FOURS, player1.getcRank());
        assertSame(ClassificationRank.FOURS, player2.getcRank());
        assertSame(ClassificationRank.FOURS, player3.getcRank());

        assertTrue(player1.isWinner());
        assertFalse(player2.isWinner());
        assertTrue(player3.isWinner());

    }

    @Test
    @Label("Test case 4")
    @DisplayName("Fours community, second and third player low card, hence first  win")
    void SecondAndThirdPlayerLowestCard() {

        c1 = new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.CLUBS));

        player3 = new Player("TP3");
        player3.drawCard(new Card(CardEnums.cValue.JACK, CardEnums.cSymbol.CLUBS));
        player3.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS));


        ev = new HandEvaluator(player1, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaluateClassificationRank();

        ev = new HandEvaluator(player3, communityCards);
        ev.evaluateClassificationRank();


        players = new ArrayList<>(List.of(player1, player2, player3));
        Utils.setWinners(players);

        assertSame(ClassificationRank.FOURS, player1.getcRank());
        assertSame(ClassificationRank.FOURS, player2.getcRank());
        assertSame(ClassificationRank.FOURS, player3.getcRank());

        assertTrue(player1.isWinner());
        assertFalse(player2.isWinner());
        assertFalse(player3.isWinner());

    }
}

package Logic;

import Enums.CardEnums;
import Enums.ClassificationRank;
import jdk.jfr.Label;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HighCardTest{

    ArrayList<Card> communityCards;
    ArrayList<Player> players;
    private Player player1, player2;
    private HandEvaluator ev;
    private Card c1, c2, c3, c4, c5;

    @Test
    @Label("Same high cards, pot split")
    void HighCardSameHandsPotSplit() {

        c1 = new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.FIVE, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.CLUBS));

        ev = new HandEvaluator(player1, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaulateClassificationRank();

        players = new ArrayList<>(List.of(player1, player2));
        Utils.setWinners(players);

        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());

        assertTrue(player1.isWinner());
        assertTrue(player2.isWinner());


    }

    @Test
    @Label("Same high cards case 2, pot split")
    void HighCardSameHighCardCompareUpToFourHighestCardsIfTruePotSplit() {

        c1 = new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));

        ev = new HandEvaluator(player1, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaulateClassificationRank();

        players = new ArrayList<>(List.of(player1, player2));
        Utils.setWinners(players);

        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());

        assertTrue(player1.isWinner());
        assertTrue(player2.isWinner());

    }

    @Test
    @Label("Both have smaller card than there is on table, still same hand will result in split")
    void HighCardOnTableTest() {

        c1 = new Card(CardEnums.cValue.JACK, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));

        ev = new HandEvaluator(player1, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaulateClassificationRank();

        players = new ArrayList<>(List.of(player1, player2));
        Utils.setWinners(players);

        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());

        assertTrue(player1.isWinner());
        assertTrue(player2.isWinner());

    }

    @Test
    @Label("First player has higher card, therefore he will win the pot")
    void FirstPlayerHasHigherCard() {

        c1 = new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.CLUBS));

        ev = new HandEvaluator(player1, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaulateClassificationRank();

        players = new ArrayList<>(List.of(player1, player2));
        Utils.setWinners(players);

        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());

        assertTrue(player1.isWinner());
        assertFalse(player2.isWinner());


    }

    @Test
    @Label("Both have same first high card, but First player has higher second card, therefore he will win the pot")
    void FirstPlayerHasHigherSecondCard() {

        c1 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS));

        ev = new HandEvaluator(player1, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaulateClassificationRank();

        players = new ArrayList<>(List.of(player1, player2));
        Utils.setWinners(players);

        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());

        assertTrue(player1.isWinner());
        assertFalse(player2.isWinner());


    }

    @Test
    @Label("Second player has higher card, therefore he will win the pot")
    void SecondPlayerHasHigherCard() {

        c1 = new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.SEVEN, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.CLUBS));

        ev = new HandEvaluator(player1, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaulateClassificationRank();

        players = new ArrayList<>(List.of(player1, player2));
        Utils.setWinners(players);

        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());

        assertFalse(player1.isWinner());
        assertTrue(player2.isWinner());


    }

    @Test
    @Label("Both have same first high card, but Second player has higher second card, therefore he will win the pot")
    void SecondPlayerHasHigherSecondCard() {

        c1 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.CLUBS));

        ev = new HandEvaluator(player1, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaulateClassificationRank();

        players = new ArrayList<>(List.of(player1, player2));
        Utils.setWinners(players);

        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());

        assertFalse(player1.isWinner());
        assertTrue(player2.isWinner());


    }

    @Test
    void SortTest() {

        //was TP5, TP6, TP4, TP3....
        //should be TP6, TP5, TP4, TP3, etc...
        c1 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.SPADES);
        c2 = new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.SPADES);
        c3 = new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.CLUBS);
        c4 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS);
        c5 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS);
        communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.HEARTS));

        player2 = new Player("TP2");
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

        ev = new HandEvaluator(player1, communityCards);
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
        players = new ArrayList<>(List.of(player1, player2, player3, player4, player5, player6));
        Utils.setWinners(players);

        assertSame(ClassificationRank.HIGH_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_CARD, player2.getcRank());

        assertFalse(player1.isWinner());
        assertFalse(player2.isWinner());
        assertFalse(player3.isWinner());
        assertFalse(player4.isWinner());
        assertTrue(player5.isWinner());
        assertFalse(player6.isWinner());

    }

    @Test
    @Label("Both players should win, neither has higher card than lowest community card")
    void NeitherHasHighestCard(){

        Card c1 = new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.SPADES);
        Card c2 = new Card(CardEnums.cValue.NINE, CardEnums.cSymbol.SPADES);
        Card c3 = new Card(CardEnums.cValue.EIGHT, CardEnums.cSymbol.CLUBS);
        Card c4 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS);
        Card c5 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS);
        ArrayList<Card> communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        Player player1 = new Player("TP1");
        player1.drawCard(new Card(CardEnums.cValue.SIX, CardEnums.cSymbol.HEARTS));
        player1.drawCard(new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.HEARTS));

        Player player2 = new Player("TP2");
        player2.drawCard(new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.CLUBS));
        player2.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS));

        var ev = new HandEvaluator(player1, communityCards);
        ev.evaulateClassificationRank();

        ev = new HandEvaluator(player2, communityCards);
        ev.evaulateClassificationRank();

        var players = new ArrayList<>(List.of(player1, player2));
        Utils.setWinners(players);

        assertSame(ClassificationRank.HIGH_COMMUNITY_CARD, player1.getcRank());
        assertSame(ClassificationRank.HIGH_COMMUNITY_CARD, player2.getcRank());

        assertTrue(player1.isWinner());
        assertTrue(player2.isWinner());


    }


}

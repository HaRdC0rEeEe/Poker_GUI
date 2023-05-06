package Logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComparatorByResultTest{

    @Test
    void compare() {

    }

    @Test
    void compareHighCards() {
        // create a straight flush hand
        Card c1 = new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS);
        Card c2 = new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS);
        Card c3 = new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.HEARTS);
        Card c4 = new Card(CardEnums.cValue.JACK, CardEnums.cSymbol.HEARTS);
        Card c5 = new Card(CardEnums.cValue.FOUR, CardEnums.cSymbol.HEARTS);
        ArrayList<Card> communityCards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));

        // create a player with the hand
        Player player = new Player("Pepr");
        player.drawCard(new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS));
        player.drawCard(new Card(CardEnums.cValue.THREE, CardEnums.cSymbol.HEARTS));

        Player player2 = new Player("John");
        player.drawCard(new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.CLUBS));
        player.drawCard(new Card(CardEnums.cValue.TWO, CardEnums.cSymbol.CLUBS));

        // create an instance of the evaluator
        var ev = new HandEvaluator(player, communityCards);
        ev.setDefaultState();
        ev.evaulateClassificationRank();
        ev = new HandEvaluator(player2, communityCards);
        ev.setDefaultState();
        ev.evaulateClassificationRank();

        setWinners(player, player2);

        assertTrue(player.isWinner());
        //assertTrue(player2.isWinner());
        assertFalse(player2.isWinner());
        //assertFalse(player2.isWinner());
        assertSame(ClassificationRank.STRAIGHT_FLUSH,player.getcRank());
    }

    private void setWinners(Player player, Player player2) {
        List<Player> players = new ArrayList<>(Arrays.asList(player, player2));
        List<Player> winners = new ArrayList<>();
        winners.add(players.get(0)); // initialize the list with the first player

        for (int i = 1; i < players.size(); i++) {
            int result = new ComparatorByResult().compare(winners.get(0), players.get(i));
            if (result > 0) {
                winners.clear();
                winners.add(players.get(i));
            } else if (result == 0) {
                winners.add(players.get(i));
            }
        }
        winners.forEach(p -> p.setWinner(true));
    }


    @Test
    public void testEvaluateStraightFlush() {

    }
}


/*Both players have high card hands
Player 1: 2D, 4H, 6S, 8C, TH
Player 2: 3C, 5D, 7H, 9S, JC
Both players have a pair, but player 1 has a higher pair
Player 1: 8H, 8D, 3S, 5C, 9H
Player 2: 6D, 6S, 4C, 7S, KH
Both players have a pair, but player 2 has a higher pair
Player 1: AD, AC, 5S, 6C, 8H
Player 2: KD, KS, 4C, 7S, QH
Both players have two pair, but player 1 has a higher pair
Player 1: 9H, 9D, 5S, 5C, 3H
Player 2: 8D, 8C, 6S, 6C, 2H
Both players have two pair, but player 2 has a higher pair
Player 1: TD, TS, 6S, 6C, 3H
Player 2: JD, JS, 5C, 5S, AH
Both players have three of a kind, but player 1 has a higher three of a kind
Player 1: 7C, 7D, 7S, 4H, 2S
Player 2: 5H, 5D, 5S, 8C, JH
Both players have three of a kind, but player 2 has a higher three of a kind
Player 1: 4D, 4C, 4S, 9H, QH
Player 2: 8S, 8D, 8H, 2C, 3S
Both players have a straight, but player 1 has a higher straight
Player 1: 9H, TD, JS, QC, KH
Player 2: 5D, 6C, 7S, 8H, 9C
Both players have a straight, but player 2 has a higher straight
Player 1: 6D, 7C, 8S, 9H, TH
Player 2: QD, JC, TS, 9C, 8H
Both players have a flush, but player 1 has a higher flush
Player 1: 2H, 4H, 6H, 8H, TH
Player 2: 3C, 5C, 7C, 9C, JC
Both players have a flush, but player 2 has a higher flush
Player 1: AD, KD, QD, JD, 9D
Player 2: AH, KH, QH, JH, 8H
Both players have a full house, but player 1 has a higher three of a kind
Player 1: 8C, 8D, 8H, 4S, 4C
Player 2: 7S, 7D, 7H, 5S, 5D*/
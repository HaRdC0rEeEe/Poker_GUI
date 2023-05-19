package Logic;

import Enums.CardEnums;

import java.util.Collections;
import java.util.Stack;

public class Deck{

    private final Stack<Card> deck;
    public Deck() {
        deck = new Stack<>();

        createDeck();
        Collections.shuffle(deck);

    }

    private void createDeck() {

        for(CardEnums.cValue value : CardEnums.cValue.values()){

            for(CardEnums.cSymbol symbol : CardEnums.cSymbol.values()){
                deck.push(new Card(value, symbol));
            }
        }
    }
    protected Card getTopCard() {

        return deck.pop();
    }

    protected void burnCard() {
        deck.pop();
    }
}
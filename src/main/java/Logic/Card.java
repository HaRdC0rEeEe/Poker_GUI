package Logic;

import Enums.CardEnums;

public class Card implements Comparable<Card>{

    private final CardEnums.cValue cValue;
    private final CardEnums.cSymbol cSymbol;

    /**
     * @param value  Value of a Card [2 to A]
     * @param symbol Symbol of Card [clubs ♣, diamonds ♦, hearts ♥, and spades ♠]
     */
    public Card(CardEnums.cValue value, CardEnums.cSymbol symbol) {
        cValue = value;
        cSymbol = symbol;
        CardEnums.cColor cColor = setColorDependingOnSymbol(cSymbol);
    }

    public CardEnums.cValue getCardValue() {
        return cValue;
    }

    public CardEnums.cSymbol getCardSymbol() {
        return cSymbol;
    }

    protected int getRank() {
        return cValue.getRank();
    }

    //although Color of a card symbol is not needed in Poker, it could be useful in different card games
    private CardEnums.cColor setColorDependingOnSymbol(CardEnums.cSymbol sym) {
        if(sym.equals(CardEnums.cSymbol.SPADES) || sym.equals(CardEnums.cSymbol.CLUBS)){
            return CardEnums.cColor.BLACK;
        } else{
            return CardEnums.cColor.RED;
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s", cValue.toString(), cSymbol.toString());
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == this){
            return true;
        }

        if(!(obj instanceof Card other)){
            return false;
        }

        return this.cValue.getRank() == other.cValue.getRank();

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + cValue.hashCode();

        return result;
    }

    //compareTo method must implement every Comparable interface
    @Override
    public int compareTo(Card card) {

        return Integer.compare(this.cValue.getRank(), card.cValue.getRank());
    }
}


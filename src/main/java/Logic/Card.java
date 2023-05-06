package Logic;

public class Card{

    private final CardEnums.cValue cValue;
    private final CardEnums.cColor cColor;
    private final CardEnums.cSymbol cSymbol;

    /**
     * @param value  Value of a Card [2 to A]
     * @param symbol Symbol of Card [clubs ♣, diamonds ♦, hearts ♥, and spades ♠]
     */
    public Card(CardEnums.cValue value, CardEnums.cSymbol symbol) {
        cValue = value;
        cSymbol = symbol;
        cColor = setColorDependingOnSymbol(cSymbol);
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


    private CardEnums.cColor setColorDependingOnSymbol(CardEnums.cSymbol sym) {
        if(sym.equals(CardEnums.cSymbol.SPADES) || sym.equals(CardEnums.cSymbol.CLUBS)){
            return CardEnums.cColor.BLACK;
        } else{
            return CardEnums.cColor.RED;
        }
    }

    @Override
    public String toString() {
        //return value_of_card.toString();
        return String.format("%s %s", cValue.toString(), cSymbol.toString());
    }

    public boolean equals(Card obj) {
        return (getRank() == obj.getRank());
    }

}


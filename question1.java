import java.util.*;

class Card {
    private String symbol;

    public Card(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}

class CardCollection {
    private Map<String, Collection<Card>> cardsBySymbol;

    public CardCollection() {
        cardsBySymbol = new HashMap<>();
    }

    public void addCard(Card card) {
        String symbol = card.getSymbol();
        cardsBySymbol.computeIfAbsent(symbol, k -> new ArrayList<>()).add(card);
    }

    public Collection<Card> getCardsForSymbol(String symbol) {
        return cardsBySymbol.getOrDefault(symbol, Collections.emptyList());
    }

    public void printAllCards() {
        for (String symbol : cardsBySymbol.keySet()) {
            System.out.println("Cards for symbol '" + symbol + "':");
            for (Card card : cardsBySymbol.get(symbol)) {
                System.out.println(card);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        CardCollection cardCollection = new CardCollection();

        cardCollection.addCard(new Card("Heart"));
        cardCollection.addCard(new Card("Spade"));
        cardCollection.addCard(new Card("Diamond"));
        cardCollection.addCard(new Card("Club"));
        cardCollection.addCard(new Card("Heart"));
        cardCollection.addCard(new Card("Diamond"));

        cardCollection.printAllCards();

        String symbolToSearch = "Heart";
        Collection<Card> heartCards = cardCollection.getCardsForSymbol(symbolToSearch);
        System.out.println("\nCards for symbol '" + symbolToSearch + "': " + heartCards);
    }
}

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Carte> deck;

    public Deck() {
        creareDeck();
    }

    public void creareDeck() {
        deck = new ArrayList<>();

        String[] numar = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
        String[] tip = { "C", "D", "H", "S" };

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 13; j++) {
                Carte card = new Carte(numar[j], tip[i]);
                deck.add(card);
            }

        Collections.shuffle(deck);
    }

    public void stergeUltimaCarte() {
        deck.remove(deck.size() - 1);
    }

    public Carte getUltimaCarte() {
        return deck.get(deck.size() - 1);
    }

    @Override
    public String toString() {
        return deck.toString();
    }

}
import java.util.ArrayList;

public class Dealer {
    private ArrayList<Carte> manaDealer;

    public Dealer() {
        manaDealer = new ArrayList<>();
    }

    public void iaCarte(Carte carte) {
        manaDealer.add(carte);
    }

    public int getSize() {
        return manaDealer.size();
    }

    public Carte getCard(int index) {
        return manaDealer.get(index);
    }

    @Override
    public String toString() {
        return manaDealer.toString();
    }
}

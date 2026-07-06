import java.util.ArrayList;

public class Player {
    private ArrayList<Carte> manaPlayer;

    public Player() {
        manaPlayer = new ArrayList<>();
    }

    public void iaCarte(Carte carte) {
        manaPlayer.add(carte);
    }

    public int getSize() {
        return manaPlayer.size();
    }

    public Carte getCard(int index) {
        return manaPlayer.get(index);
    }

    @Override
    public String toString() {
        return manaPlayer.toString();
    }

}

public class Carte {
    private String numar;
    private String tip;

    public Carte(String numar, String tip) {
        this.numar = numar;
        this.tip = tip;
    }

    public int getNumar() {
        if ("AJQK".contains(numar)) {
            if (numar == "A")
                return 11;
            else
                return 10;
        } else
            return Integer.parseInt(numar);
    }

    public boolean As() {
        return numar == "A";
    }

    public String getImagePath() {
        return "./carti/" + toString() + ".png";
    }

    @Override
    public String toString() {
        return numar + "-" + tip;
    }

}
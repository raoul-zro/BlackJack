public class BlackJack {
    private Deck deck;

    public Dealer manaDealer;
    public Integer carteAscunsa;
    public Integer valoareManaDealer = 0;
    public Integer asDealer = 0;

    public Player manaPlayer;
    public Integer valoareManaPlayer = 0;
    public Integer asPlayer = 0;

    public BlackJack() {
        new LoginRegister(this);
    }

    public void startGame() {
        deck = new Deck();
        System.out.println("Deck:");
        System.out.println(deck);

        manaDealer = new Dealer();

        hitDealer();
        carteAscunsa = valoareManaDealer;
        hitDealer();

        System.out.println("Dealer: ");
        System.out.println(manaDealer);
        System.out.println(valoareManaDealer);
        System.out.println(asDealer);

        manaPlayer = new Player();

        hitPlayer();
        hitPlayer();

        System.out.println("Player: ");
        System.out.println(manaPlayer);
        System.out.println(valoareManaPlayer);
        System.out.println(asPlayer);
    }

    public void hitDealer() {
        manaDealer.iaCarte(deck.getUltimaCarte());
        valoareManaDealer += deck.getUltimaCarte().getNumar();
        if (deck.getUltimaCarte().As())
            asDealer++;
        deck.stergeUltimaCarte();
    }

    public void hitPlayer() {
        manaPlayer.iaCarte(deck.getUltimaCarte());
        valoareManaPlayer += deck.getUltimaCarte().getNumar();
        if (deck.getUltimaCarte().As())
            asPlayer++;
        deck.stergeUltimaCarte();
    }

    public void scadereValoareAsiDealer() {
        while (valoareManaDealer > 21 && asDealer > 0) {
            valoareManaDealer -= 10;
            asDealer--;
        }
    }

    public void scadereValoareAsiPlayer() {
        while (valoareManaPlayer > 21 && asPlayer > 0) {
            valoareManaPlayer -= 10;
            asPlayer--;
        }
    }

    public void reset() {
        deck = new Deck();

        manaDealer = new Dealer();
        valoareManaDealer = 0;
        asDealer = 0;
        hitDealer();
        carteAscunsa = valoareManaDealer;
        hitDealer();

        System.out.println("Dealer: ");
        System.out.println(manaDealer);
        System.out.println(valoareManaDealer);
        System.out.println(asDealer);

        manaPlayer = new Player();
        valoareManaPlayer = 0;
        asPlayer = 0;
        hitPlayer();
        hitPlayer();

        System.out.println("Player: ");
        System.out.println(manaPlayer);
        System.out.println(valoareManaPlayer);
        System.out.println(asPlayer);
    }

}
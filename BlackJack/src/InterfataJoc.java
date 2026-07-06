import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.*;

public class InterfataJoc {
    private BlackJack blackJack;
    private LoginRegister loginRegister;
    private int balanta;
    private int bet;

    JButton resetButton = new JButton("Reset");
    JButton bet50$Button = new JButton("Add 50$");
    JButton bet100$Button = new JButton("Add 100$");
    JButton bet250$Button = new JButton("Add 250$");
    JButton betButton = new JButton("Bet");
    JButton allinButton = new JButton("All In");

    public InterfataJoc(BlackJack blackJack, LoginRegister loginRegister) {
        this.blackJack = blackJack;
        this.loginRegister = loginRegister;

        JButton playButton = new JButton("Play");
        JPanel startJocPanel = new JPanel();
        JFrame startJocFrame = new JFrame();

        playButton.setFont(new Font("Arial", Font.BOLD, 60));
        playButton.setBackground(Color.black);
        playButton.setForeground(Color.white);
        playButton.setFocusable(false);

        startJocPanel.setLayout(new GridBagLayout());
        startJocPanel.setBackground(new Color(135, 0, 0));
        startJocPanel.add(playButton, new GridBagConstraints());

        startJocFrame.setTitle("BlackJack");
        startJocFrame.setSize(1280, 720);
        startJocFrame.setLocationRelativeTo(null);
        startJocFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startJocFrame.setVisible(true);
        startJocFrame.add(startJocPanel);

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startJocFrame.dispose();
                blackJack.startGame();
                gameFrame();
            }
        });
    }

    public void gameFrame() {
        this.bet = 0;
        this.balanta = DB.getBalantaUser(loginRegister.nume);

        int cardWidth = 150;
        int cardHeight = 210;

        JFrame gameFrame = new JFrame();

        JButton hitButton = new JButton("Hit");
        JButton standButton = new JButton("Stand");
        JButton playAgainButton = new JButton("Play Again");

        JPanel buttonPanel = new JPanel();
        JPanel gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                Image carteImg;
                Carte carte;

                Image carteAscunsa = new ImageIcon(getClass().getResource("./carti/BACK.png")).getImage();
                Image jeton50 = new ImageIcon(getClass().getResource("./jetoane/jeton50.png")).getImage();
                Image jeton100 = new ImageIcon(getClass().getResource("./jetoane/jeton100.png")).getImage();
                Image jeton250 = new ImageIcon(getClass().getResource("./jetoane/jeton250.png")).getImage();
                Image jeton500 = new ImageIcon(getClass().getResource("./jetoane/jeton500.png")).getImage();
                Image jeton1000 = new ImageIcon(getClass().getResource("./jetoane/jeton1000.png")).getImage();

                if (!betButton.isVisible()) {
                    if (playAgainButton.isVisible()) {
                        carte = blackJack.manaDealer.getCard(0);
                        carteAscunsa = new ImageIcon(getClass().getResource(carte.getImagePath())).getImage();

                        String castigator = "";

                        if ((blackJack.valoareManaPlayer == 21 && blackJack.manaPlayer.getSize() == 2)
                                && (blackJack.valoareManaDealer == 21 && blackJack.manaDealer.getSize() == 2)) {
                            castigator = "Egalitate!";
                            balanta += bet;
                        } else if ((blackJack.valoareManaPlayer == 21 && blackJack.manaPlayer.getSize() == 2)
                                && blackJack.manaDealer.getSize() > 2) {
                            castigator = "BlackJack!! Ai Castigat!";
                            balanta += bet * 3;
                        } else if (blackJack.valoareManaPlayer == blackJack.valoareManaDealer)
                            castigator = "Egalitate, Dealerul Castiga!";
                        else if (blackJack.valoareManaPlayer < 22
                                && (blackJack.valoareManaPlayer > blackJack.valoareManaDealer
                                        || blackJack.valoareManaDealer > 21)) {
                            castigator = "Ai castigat!";
                            balanta += bet * 2;
                        } else
                            castigator = "Ai pierdut!";

                        DB.actualizeazaBalantaUser(loginRegister.nume, balanta);

                        g.setFont(new Font("Arial", Font.PLAIN, 30));
                        g.setColor(Color.white);
                        if (castigator.equals("Egalitate, Dealerul Castiga!")
                                || castigator.equals("BlackJack!! Ai Castigat!"))
                            g.drawString(castigator, 480, 330);
                        else
                            g.drawString(castigator, 555, 330);

                        g.setFont(new Font("Arial", Font.BOLD, 34));
                        g.setColor(Color.black);
                        g.drawString(String.valueOf(blackJack.valoareManaDealer),
                                75 + (cardWidth + 9) * blackJack.manaDealer.getSize(), 135);
                    } else {
                        g.setFont(new Font("Arial", Font.BOLD, 34));
                        g.setColor(Color.black);
                        g.drawString(String.valueOf(blackJack.valoareManaDealer - blackJack.carteAscunsa),
                                75 + (cardWidth + 9) * blackJack.manaDealer.getSize(), 135);
                    }

                    if (blackJack.asPlayer == 0) {
                        g.setFont(new Font("Arial", Font.BOLD, 34));
                        g.setColor(Color.black);
                        g.drawString(String.valueOf(blackJack.valoareManaPlayer),
                                75 + (cardWidth + 9) * blackJack.manaPlayer.getSize(), 520);
                    } else {
                        g.setFont(new Font("Arial", Font.BOLD, 34));
                        g.setColor(Color.black);
                        g.drawString(
                                String.valueOf(blackJack.valoareManaPlayer) + " / "
                                        + String.valueOf(blackJack.valoareManaPlayer - 10),
                                75 + (cardWidth + 9) * blackJack.manaPlayer.getSize(), 520);
                    }

                    g.drawImage(carteAscunsa, 50, 25, cardWidth, cardHeight, null);

                    for (int i = 1; i < blackJack.manaDealer.getSize(); i++) {
                        carte = blackJack.manaDealer.getCard(i);
                        carteImg = new ImageIcon(getClass().getResource(carte.getImagePath())).getImage();
                        g.drawImage(carteImg, 51 + (cardWidth + 9) * i, 25, cardWidth, cardHeight, null);
                    }

                    for (int i = 0; i < blackJack.manaPlayer.getSize(); i++) {
                        carte = blackJack.manaPlayer.getCard(i);
                        carteImg = new ImageIcon(getClass().getResource(carte.getImagePath())).getImage();
                        g.drawImage(carteImg, 51 + (cardWidth + 9) * i, 390, cardWidth, cardHeight, null);
                    }
                } else {
                    for (int i = 0; i < 2; i++) {
                        g.drawImage(carteAscunsa, 51 + (cardWidth + 9) * i, 25, cardWidth, cardHeight, null);
                        g.drawImage(carteAscunsa, 51 + (cardWidth + 9) * i, 390, cardWidth, cardHeight, null);
                    }
                }

                if (bet != 0) {
                    if (bet == 50)
                        g.drawImage(jeton50, 1050, 240, 125, 125, null);
                    else if (bet == 100)
                        g.drawImage(jeton100, 1050, 240, 125, 125, null);
                    else if (bet == 150) {
                        g.drawImage(jeton100, 1025, 265, 125, 125, null);
                        g.drawImage(jeton50, 1065, 240, 125, 125, null);
                    } else if (bet == 200) {
                        g.drawImage(jeton100, 1025, 265, 125, 125, null);
                        g.drawImage(jeton100, 1065, 240, 125, 125, null);
                    } else if (bet == 250)
                        g.drawImage(jeton250, 1050, 240, 125, 125, null);
                    else if (bet == 300) {
                        g.drawImage(jeton250, 1025, 265, 125, 125, null);
                        g.drawImage(jeton50, 1065, 240, 125, 125, null);
                    } else if (bet == 350) {
                        g.drawImage(jeton250, 1025, 265, 125, 125, null);
                        g.drawImage(jeton100, 1065, 240, 125, 125, null);
                    } else if (bet == 400 || bet == 450) {
                        g.drawImage(jeton250, 1020, 275, 125, 125, null);
                        g.drawImage(jeton100, 1075, 245, 125, 125, null);
                        g.drawImage(jeton50, 1040, 215, 125, 125, null);
                    } else if (bet == 500)
                        g.drawImage(jeton500, 1050, 240, 125, 125, null);
                    else if (bet == 550) {
                        g.drawImage(jeton500, 1025, 265, 125, 125, null);
                        g.drawImage(jeton50, 1065, 240, 125, 125, null);
                    } else if (bet == 600) {
                        g.drawImage(jeton500, 1025, 265, 125, 125, null);
                        g.drawImage(jeton100, 1065, 240, 125, 125, null);
                    } else if (bet == 650) {
                        g.drawImage(jeton500, 1020, 275, 125, 125, null);
                        g.drawImage(jeton100, 1075, 245, 125, 125, null);
                        g.drawImage(jeton50, 1040, 215, 125, 125, null);
                    } else if (bet == 700) {
                        g.drawImage(jeton500, 1020, 275, 125, 125, null);
                        g.drawImage(jeton100, 1075, 245, 125, 125, null);
                        g.drawImage(jeton100, 1040, 215, 125, 125, null);
                    } else if (bet == 750 || bet < 1000) {
                        g.drawImage(jeton500, 1025, 265, 125, 125, null);
                        g.drawImage(jeton250, 1065, 240, 125, 125, null);
                    } else if (bet == 1000)
                        g.drawImage(jeton1000, 1050, 240, 125, 125, null);
                    else if (bet == 1050) {
                        g.drawImage(jeton1000, 1025, 265, 125, 125, null);
                        g.drawImage(jeton50, 1065, 240, 125, 125, null);
                    } else if (bet == 1100) {
                        g.drawImage(jeton1000, 1025, 265, 125, 125, null);
                        g.drawImage(jeton100, 1065, 240, 125, 125, null);
                    } else if (bet == 1150) {
                        g.drawImage(jeton1000, 1020, 275, 125, 125, null);
                        g.drawImage(jeton100, 1075, 245, 125, 125, null);
                        g.drawImage(jeton50, 1040, 215, 125, 125, null);
                    } else if (bet == 1200) {
                        g.drawImage(jeton1000, 1020, 275, 125, 125, null);
                        g.drawImage(jeton100, 1075, 245, 125, 125, null);
                        g.drawImage(jeton100, 1040, 215, 125, 125, null);
                    } else if (bet == 1250) {
                        g.drawImage(jeton1000, 1025, 265, 125, 125, null);
                        g.drawImage(jeton250, 1065, 240, 125, 125, null);
                    } else if (bet == 1300 || bet < 1500) {
                        g.drawImage(jeton1000, 1020, 275, 125, 125, null);
                        g.drawImage(jeton250, 1075, 245, 125, 125, null);
                        g.drawImage(jeton50, 1040, 215, 125, 125, null);
                    } else if (bet == 1500) {
                        g.drawImage(jeton1000, 1025, 265, 125, 125, null);
                        g.drawImage(jeton500, 1065, 240, 125, 125, null);
                    } else if (bet == 1550 || bet < 1750) {
                        g.drawImage(jeton1000, 1020, 275, 125, 125, null);
                        g.drawImage(jeton500, 1075, 245, 125, 125, null);
                        g.drawImage(jeton50, 1040, 215, 125, 125, null);
                    } else if (bet == 1750 || bet < 2000) {
                        g.drawImage(jeton1000, 1020, 275, 125, 125, null);
                        g.drawImage(jeton500, 1075, 245, 125, 125, null);
                        g.drawImage(jeton250, 1040, 215, 125, 125, null);
                    } else if (bet == 2000 || bet < 2250) {
                        g.drawImage(jeton1000, 1025, 265, 125, 125, null);
                        g.drawImage(jeton1000, 1065, 240, 125, 125, null);
                    } else if (bet == 2250 || bet < 2500) {
                        g.drawImage(jeton1000, 1020, 275, 125, 125, null);
                        g.drawImage(jeton1000, 1075, 245, 125, 125, null);
                        g.drawImage(jeton250, 1040, 215, 125, 125, null);
                    } else if (bet == 2500 || bet < 3000) {
                        g.drawImage(jeton1000, 1020, 275, 125, 125, null);
                        g.drawImage(jeton1000, 1075, 245, 125, 125, null);
                        g.drawImage(jeton500, 1040, 215, 125, 125, null);
                    } else if (bet >= 3000) {
                        g.drawImage(jeton1000, 1020, 275, 125, 125, null);
                        g.drawImage(jeton1000, 1075, 245, 125, 125, null);
                        g.drawImage(jeton1000, 1040, 215, 125, 125, null);
                    }
                }

                g.setFont(new Font("Arial", Font.BOLD, 34));
                g.setColor(Color.black);
                g.drawString("Balance: " + String.valueOf(balanta) + "$", 15, 635);
                g.drawString("Bet: " + String.valueOf(bet) + "$", 1080, 635);

            }
        };

        resetButton.setBackground(Color.black);
        resetButton.setForeground(Color.white);
        resetButton.setFocusable(false);

        bet50$Button.setBackground(Color.black);
        bet50$Button.setForeground(Color.white);
        bet50$Button.setFocusable(false);
        if (bet > balanta || bet + 50 > balanta)
            bet50$Button.setEnabled(false);

        bet100$Button.setBackground(Color.black);
        bet100$Button.setForeground(Color.white);
        bet100$Button.setFocusable(false);
        if (bet > balanta || bet + 100 > balanta)
            bet100$Button.setEnabled(false);

        bet250$Button.setBackground(Color.black);
        bet250$Button.setForeground(Color.white);
        bet250$Button.setFocusable(false);
        if (bet > balanta || bet + 250 > balanta)
            bet250$Button.setEnabled(false);

        betButton.setBackground(Color.black);
        betButton.setForeground(Color.white);
        betButton.setFocusable(false);
        betButton.setEnabled(false);

        allinButton.setBackground(Color.black);
        allinButton.setForeground(Color.white);
        allinButton.setFocusable(false);
        if (balanta == 0)
            allinButton.setEnabled(false);

        //

        hitButton.setBackground(Color.black);
        hitButton.setForeground(Color.white);
        hitButton.setFocusable(false);
        hitButton.setVisible(false);

        standButton.setBackground(Color.black);
        standButton.setForeground(Color.white);
        standButton.setFocusable(false);
        standButton.setVisible(false);

        playAgainButton.setBackground(Color.black);
        playAgainButton.setForeground(Color.white);
        playAgainButton.setFocusable(false);
        playAgainButton.setVisible(false);

        buttonPanel.setBackground(new Color(153, 0, 0));
        buttonPanel.add(resetButton);
        buttonPanel.add(bet50$Button);
        buttonPanel.add(bet100$Button);
        buttonPanel.add(bet250$Button);
        buttonPanel.add(betButton);
        buttonPanel.add(allinButton);
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(playAgainButton);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53, 101, 77));

        gameFrame.setTitle("BlackJack");
        gameFrame.setSize(1280, 720);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
        gameFrame.add(gamePanel);
        gameFrame.add(buttonPanel, BorderLayout.SOUTH);

        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bet = 0;
                actualizareBet();
                gamePanel.repaint();
            }
        });

        bet50$Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bet += 50;
                adaugaSunet("D:\\Faculta\\An2\\Sem1\\BlackJack\\src\\sunete\\sunet_jeton.wav");
                actualizareBet();
                gamePanel.repaint();
            }
        });

        bet100$Button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                bet += 100;
                adaugaSunet("D:\\Faculta\\An2\\Sem1\\BlackJack\\src\\sunete\\sunet_jeton.wav");
                actualizareBet();
                gamePanel.repaint();
            }
        });

        bet250$Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bet += 250;
                adaugaSunet("D:\\Faculta\\An2\\Sem1\\BlackJack\\src\\sunete\\sunet_jeton.wav");
                actualizareBet();
                gamePanel.repaint();
            }
        });

        betButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                resetButton.setVisible(false);
                bet50$Button.setVisible(false);
                bet100$Button.setVisible(false);
                bet250$Button.setVisible(false);
                betButton.setVisible(false);
                allinButton.setVisible(false);

                hitButton.setVisible(true);
                standButton.setVisible(true);

                balanta -= bet;
                DB.actualizeazaBalantaUser(loginRegister.nume, balanta);
                gameFrame.repaint();
            }
        });

        allinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bet = balanta;
                adaugaSunet("D:\\Faculta\\An2\\Sem1\\BlackJack\\src\\sunete\\sunet_jeton.wav");
                actualizareBet();
                gameFrame.repaint();
            }
        });

        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adaugaSunet("D:\\Faculta\\An2\\Sem1\\BlackJack\\src\\sunete\\sunet_carte.wav");
                blackJack.hitPlayer();

                if (blackJack.valoareManaPlayer > 21) {
                    blackJack.scadereValoareAsiPlayer();

                    if (blackJack.valoareManaPlayer > 21) {
                        hitButton.setVisible(false);
                        standButton.setVisible(false);
                        playAgainButton.setVisible(true);
                    }
                }

                gamePanel.repaint();
            }
        });

        standButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitButton.setVisible(false);
                standButton.setVisible(false);
                playAgainButton.setVisible(true);

                while ((blackJack.manaDealer.getSize() == 2 && blackJack.asDealer == 1
                        && blackJack.valoareManaDealer == 17)
                        || (blackJack.valoareManaDealer < blackJack.valoareManaPlayer
                                && blackJack.valoareManaDealer < 21)
                        || (blackJack.valoareManaDealer < 17)) {
                    blackJack.hitDealer();
                    if (blackJack.valoareManaDealer > 21)
                        blackJack.scadereValoareAsiDealer();
                }

                gamePanel.repaint();
            }
        });

        playAgainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                blackJack.reset();

                resetButton.setVisible(true);
                bet50$Button.setVisible(true);
                bet100$Button.setVisible(true);
                bet250$Button.setVisible(true);
                betButton.setVisible(true);
                allinButton.setVisible(true);
                playAgainButton.setVisible(false);

                actualizareBet();
                gamePanel.repaint();
            }
        });

    }

    private void actualizareBet() {
        bet50$Button.setEnabled(true);
        bet100$Button.setEnabled(true);
        bet250$Button.setEnabled(true);
        betButton.setEnabled(true);
        allinButton.setEnabled(true);

        if (balanta == 0 || bet == balanta)
            allinButton.setEnabled(false);

        if (bet > balanta || bet == 0)
            betButton.setEnabled(false);

        if (bet + 50 > balanta) {
            bet50$Button.setEnabled(false);
            bet100$Button.setEnabled(false);
            bet250$Button.setEnabled(false);
        } else if (bet + 100 > balanta) {
            bet100$Button.setEnabled(false);
            bet250$Button.setEnabled(false);
        } else if (bet + 250 > balanta)
            bet250$Button.setEnabled(false);
    }

    public void adaugaSunet(String sunet) {
        try {
            File file = new File(sunet);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}

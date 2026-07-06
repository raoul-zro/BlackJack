import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class LoginRegister {

    public String nume;
    public String parola;

    private JTextField nameField;
    private JPasswordField passwordField;
    private JLabel mesajEroareLabel;
    private JFrame loginregisterFrame;

    private BlackJack blackJack;

    public LoginRegister(BlackJack blackJack) {
        this.blackJack = blackJack;

        JLabel nameLabel = new JLabel("Name:");
        JLabel passwordLabel = new JLabel("Password:");
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginregisterFrame = new JFrame();
        nameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        mesajEroareLabel = new JLabel("", JLabel.CENTER);

        loginregisterFrame.setTitle("Login");
        loginregisterFrame.setSize(500, 720);
        loginregisterFrame.getContentPane().setBackground(new Color(172, 229, 238));
        loginregisterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginregisterFrame.setLocationRelativeTo(null);
        loginregisterFrame.setLayout(null);
        loginregisterFrame.setVisible(true);
        loginregisterFrame.add(nameLabel);
        loginregisterFrame.add(nameField);
        loginregisterFrame.add(passwordLabel);
        loginregisterFrame.add(passwordField);
        loginregisterFrame.add(loginButton);
        loginregisterFrame.add(registerButton);
        loginregisterFrame.add(mesajEroareLabel);

        nameLabel.setBounds(90, 150, 80, 25);
        nameField.setBounds(190, 150, 200, 25);

        passwordLabel.setBounds(80, 200, 80, 25);
        passwordField.setBounds(190, 200, 200, 25);

        loginButton.setBounds(115, 560, 100, 30);
        loginButton.setBackground(new Color(59, 130, 246));
        loginButton.setForeground(Color.WHITE);

        registerButton.setBounds(265, 560, 100, 30);
        registerButton.setBackground(new Color(34, 197, 94));
        registerButton.setForeground(Color.WHITE);

        mesajEroareLabel.setForeground(Color.RED);
        mesajEroareLabel.setBounds(40, 265, 400, 25);
        mesajEroareLabel.setFont(new Font("Arial", Font.BOLD, 18));

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    login();
                } catch (IOException ex) {
                    mesajEroareLabel.setText("Error: " + ex.getMessage());
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    register();
                } catch (IOException ex) {
                    mesajEroareLabel.setText("Error: " + ex.getMessage());
                }
            }
        });

    }

    private void login() throws IOException {
        nume = nameField.getText();
        parola = new String(passwordField.getPassword());

        if (nume.isEmpty() || parola.isEmpty())
            mesajEroareLabel.setText("Completati ambele campuri.");
        else if (DB.verificaUser(nume, parola)) {
            loginregisterFrame.dispose();
            new InterfataJoc(blackJack, this);
        } else
            mesajEroareLabel.setText("Numele sau prola nu este valida.");

    }

    private void register() throws IOException {
        nume = nameField.getText();
        parola = new String(passwordField.getPassword());

        if (nume.isEmpty() || parola.isEmpty())
            mesajEroareLabel.setText("Completati ambele campuri.");
        else if (DB.existaNumeUser(nume))
            mesajEroareLabel.setText("Acest nume exista deja.");
        else {
            DB.createTable();
            DB.adaugaUser(nume, parola, 1000);

            loginregisterFrame.dispose();
            new InterfataJoc(blackJack, this);
        }

    }

}

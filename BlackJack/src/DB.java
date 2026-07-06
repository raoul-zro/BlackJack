import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

    public static Connection connect() {
        String url = "jdbc:sqlite:utilizatori.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Conexiune reusita!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS utilizatori ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nume TEXT NOT NULL, "
                + "parola TEXT NOT NULL, "
                + "balanta INTEGER NOT NULL"
                + ");";

        try (Connection conn = connect();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Eroare la crearea tabelei: " + e.getMessage());
        }
    }

    public static void adaugaUser(String nume, String parola, int balanta) {
        String sql = "INSERT INTO utilizatori (nume, parola, balanta) VALUES (?, ?, ?)";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nume);
            pstmt.setString(2, parola);
            pstmt.setInt(3, balanta);

            pstmt.executeUpdate();
            System.out.println("User adaugat cu succes!");

        } catch (SQLException e) {
            System.out.println("Eroare la adaugare: " + e.getMessage());
        }

    }

    public static boolean verificaUser(String nume, String parola) {
        String sql = "SELECT COUNT(*) FROM utilizatori WHERE nume = ? AND parola = ?";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nume);
            pstmt.setString(2, parola);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Eroare la verificarea utilizatorului: " + e.getMessage());
        }
        return false;
    }

    public static boolean existaNumeUser(String nume) {
        String sql = "SELECT COUNT(*) FROM utilizatori WHERE nume = ?";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nume);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public static int getBalantaUser(String nume) {
        String sql = "SELECT balanta FROM utilizatori WHERE nume = ?";
        int balanta = 0;

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nume);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                balanta = rs.getInt("balanta");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return balanta;
    }

    public static void actualizeazaBalantaUser(String nume, int nouaBalanta) {
        String sql = "UPDATE utilizatori SET balanta = ? WHERE nume = ?";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, nouaBalanta);
            pstmt.setString(2, nume);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}

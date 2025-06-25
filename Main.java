import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/Gym_management";
    private static final String USER = "postgres";
    private static final String PASSWORD = "DBMS@5sem";

    public static Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");

            // Establish the connection
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection Failed.");
            e.printStackTrace();
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        DBConnection.connect();
    }
}
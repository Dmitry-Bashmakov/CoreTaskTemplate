import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/my_database";
        String userName = "root";
        String password = "U73S*72%au9^j";


        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            System.err.println("Connection successful");
        }

    }
}

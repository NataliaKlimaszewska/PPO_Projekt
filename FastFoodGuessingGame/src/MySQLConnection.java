import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class MySQLConnection implements DatabaseConnection {
    public Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/fastfood", "user", "password");
    }
}
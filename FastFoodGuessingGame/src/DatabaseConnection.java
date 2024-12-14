import java.sql.Connection;
import java.sql.SQLException;

interface DatabaseConnection {
    Connection connect() throws SQLException;
}
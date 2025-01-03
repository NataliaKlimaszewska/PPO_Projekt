import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/fastfood_database"; // Nazwa bazy danych
    private static final String USER = "root"; // Użytkownik MySQL
    private static final String PASSWORD = "1212"; // Hasło

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("driver is loaded!");
            String url = "jdbc:mysql://localhost:3306/fastfood_database"; // URL bazy danych
            String user = "root";  // Użytkownik bazy danych (lub inny, który stworzyłaś)
            String password = "1212";
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Database driver not found.", e);
        }

    }

    public static FastFoodItem getRandomMenuItem() {
        String query = "SELECT * FROM menu_items ORDER BY RAND() LIMIT 1"; // Losowy wybór
        try (Connection connection = getConnection(); Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                String name = rs.getString("name");
                int calories = rs.getInt("calories");
                int fat = rs.getInt("fat");
                int carbs = rs.getInt("carbs");
                int protein = rs.getInt("protein");
                String imagePath = rs.getString("image_path");
                return new FastFoodItem(name, calories, fat, carbs, protein, imagePath);
            } //c.d obrazki są w scr/images
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

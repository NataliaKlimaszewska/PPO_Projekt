import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://34.57.248.13:3306/fastfooddatabase"; // Adres IP instancji Google Cloud i nazwa bazy danych
    private static final String USER = "root"; // Nazwa użytkownika MySQL
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("driver is loaded!");
            String url = "jdbc:mysql://34.57.248.13:3306/fastfood_database";
            String user = "root";
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

            // Sprawdzamy, czy zapytanie zwróciło jakiekolwiek dane
            if (rs.next()) {
                String name = rs.getString("name");
                int calories = rs.getInt("calories");
                int fat = rs.getInt("fat");
                int carbs = rs.getInt("carbs");
                int protein = rs.getInt("protein");
                String imagePath = rs.getString("image_path");
                FastFoodItem item = new FastFoodItem(name, calories, fat, carbs, protein, imagePath);
                System.out.println("Item retrieved from database: " + name); // Logowanie
                return item;
            } else {
                System.out.println("No items found in the database."); // Logowanie, jeśli brak wyników
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Zwracamy null, jeśli brak danych
    }
}

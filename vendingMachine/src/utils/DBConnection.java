package utils;

// For SQL database connection
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;


public class DBConnection {
    private final static String URL = "jdbc:mysql://localhost:3306/Vending_machine";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "";

    public static Connection getConnection() throws SQLException 
    {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    
    public static void initializeDatabase() 
    {
        String sqlProducts = """
                            CREATE TABLE IF NOT EXISTS products(
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            product_name VARCHAR(50) NOT NULL UNIQUE,
                            quantity INT UNSIGNED NOT NULL,
                            price_in_pesos INT UNSIGNED NOT NULL
                            )
                            """;
        
        String sqlAdmins = """
                            CREATE TABLE IF NOT EXISTS admins(
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            username VARCHAR(50) NOT NULL UNIQUE,
                            password VARCHAR(100) NOT NULL
                            )
                            """;
        
        try (Connection con = getConnection(); PreparedStatement psProducts = con.prepareStatement(sqlProducts); PreparedStatement psAdmins = con.prepareStatement(sqlAdmins)) 
        {
                psProducts.execute();
                psAdmins.execute();
                
                System.out.println("Databases created successfully.");
        } 
        
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
    
}
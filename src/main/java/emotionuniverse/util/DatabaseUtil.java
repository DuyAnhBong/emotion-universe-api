package emotionuniverse.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility
 * Reads Railway MySQL environment variables
 */
public class DatabaseUtil {
    
    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ MySQL JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL JDBC Driver not found!");
            e.printStackTrace();
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
        
        // Read environment variables from Railway
        String mysqlUrl = System.getenv("MYSQL_URL");
        
        if (mysqlUrl != null && !mysqlUrl.isEmpty()) {
            // MYSQL_URL format: mysql://user:password@host:port/database
            System.out.println("🔗 Connecting using MYSQL_URL...");
            
            // Convert mysql:// to jdbc:mysql://
            String jdbcUrl = mysqlUrl.replace("mysql://", "jdbc:mysql://");
            
            // Remove charset parameter and add proper parameters
            if (jdbcUrl.contains("?")) {
                jdbcUrl = jdbcUrl.substring(0, jdbcUrl.indexOf("?"));
            }
            
            // Add connection parameters (without utf8mb4)
            jdbcUrl += "?useSSL=true&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            
            System.out.println("📍 Database URL: " + jdbcUrl.replaceAll(":[^:@]+@", ":****@"));
            
            Connection conn = DriverManager.getConnection(jdbcUrl);
            System.out.println("✅ Database connection successful!");
            return conn;
            
        } else {
            // Fallback: Build from individual environment variables
            String host = System.getenv("MYSQLHOST");
            String port = System.getenv("MYSQLPORT");
            String database = System.getenv("MYSQLDATABASE");
            String user = System.getenv("MYSQLUSER");
            String password = System.getenv("MYSQLPASSWORD");
            
            System.out.println("🔗 Connecting using individual environment variables...");
            System.out.println("Host: " + host);
            System.out.println("Port: " + port);
            System.out.println("Database: " + database);
            System.out.println("User: " + user);
            
            if (host == null || port == null || database == null || user == null || password == null) {
                throw new SQLException("Missing MySQL environment variables! Make sure Railway MySQL is linked.");
            }
            
            String jdbcUrl = String.format(
                "jdbc:mysql://%s:%s/%s?useSSL=true&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                host, port, database
            );
            
            System.out.println("📍 Database URL: " + jdbcUrl);
            
            Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
            System.out.println("✅ Database connection successful!");
            return conn;
        }
    }
    
    public static void testConnection() {
        try {
            Connection conn = getConnection();
            System.out.println("✅ Database connection test: SUCCESS");
            conn.close();
        } catch (SQLException e) {
            System.err.println("❌ Database connection test: FAILED");
            e.printStackTrace();
        }
    }
}

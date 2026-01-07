package emotionuniverse.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database utility to connect to Railway MySQL
 * Reads connection info from environment variables
 */
public class DatabaseUtil {
    
    static {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ MySQL JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL JDBC Driver not found!");
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }
    
    /**
     * Get database connection from Railway environment variables
     */
    public static Connection getConnection() throws SQLException {
        String mysqlUrl = System.getenv("MYSQL_URL");
        
        // Try MYSQL_URL first (Railway format)
        if (mysqlUrl != null && !mysqlUrl.isEmpty()) {
            System.out.println("🔗 Connecting using MYSQL_URL...");
            
            // Convert mysql:// to jdbc:mysql://
            String jdbcUrl = mysqlUrl.replace("mysql://", "jdbc:mysql://");
            
            // Add connection parameters
            if (!jdbcUrl.contains("?")) {
                jdbcUrl += "?";
            } else {
                jdbcUrl += "&";
            }
            jdbcUrl += "useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&characterEncoding=utf8mb4";
            
            System.out.println("📍 Database URL: " + jdbcUrl.replaceAll(":[^:@]+@", ":****@")); // Hide password in logs
            
            return DriverManager.getConnection(jdbcUrl);
        }
        
        // Fallback to individual environment variables
        String host = System.getenv("MYSQLHOST");
        String port = System.getenv("MYSQLPORT");
        String database = System.getenv("MYSQLDATABASE");
        String user = System.getenv("MYSQLUSER");
        String password = System.getenv("MYSQLPASSWORD");
        
        if (host != null && port != null && database != null) {
            System.out.println("🔗 Connecting using individual variables...");
            
            String url = String.format(
                "jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&characterEncoding=utf8mb4",
                host, port, database
            );
            
            System.out.println("📍 Database host: " + host + ":" + port);
            System.out.println("📍 Database name: " + database);
            System.out.println("📍 Database user: " + user);
            
            return DriverManager.getConnection(url, user, password);
        }
        
        // Local development fallback
        System.out.println("⚠️ No Railway env vars found, using local development settings");
        String localUrl = "jdbc:mysql://localhost:3306/emotion_universe?useSSL=false&serverTimezone=UTC&characterEncoding=utf8mb4";
        return DriverManager.getConnection(localUrl, "root", "password");
    }
    
    /**
     * Close database connection
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("✅ Database connection closed");
            } catch (SQLException e) {
                System.err.println("❌ Error closing connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Test database connection
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("✅ Database connection test: SUCCESS");
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Database connection test: FAILED");
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }
}

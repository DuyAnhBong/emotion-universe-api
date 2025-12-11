import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * Main class with embedded Tomcat
 * Railway will run this to start the server
 */
public class Main {
    public static void main(String[] args) throws LifecycleException {
        // Get port from environment variable (Railway sets $PORT)
        String port = System.getenv("PORT");
        if (port == null || port.isEmpty()) {
            port = "8080"; // Default for local development
        }
        
        System.out.println("===========================================");
        System.out.println("🚀 Starting Emotion Universe API Server");
        System.out.println("===========================================");
        System.out.println("Port: " + port);
        
        // Create Tomcat instance
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.parseInt(port));
        tomcat.getConnector(); // Trigger connector creation
        
        // Setup webapp context
        String webappDir = new File("src/main/webapp/").getAbsolutePath();
        Context ctx = tomcat.addWebapp("", webappDir);
        
        System.out.println("Webapp directory: " + webappDir);
        System.out.println("===========================================");
        
        // Start server
        tomcat.start();
        System.out.println("✅ Server started successfully!");
        System.out.println("📍 Listening on port: " + port);
        System.out.println("===========================================");
        
        // Wait for shutdown
        tomcat.getServer().await();
    }
}
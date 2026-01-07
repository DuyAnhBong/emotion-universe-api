package emotionuniverse;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import emotionuniverse.servlet.GetAllDataServlet;

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
        
        // Create base dir for Tomcat
        String baseDir = new File(System.getProperty("java.io.tmpdir")).getAbsolutePath();
        tomcat.setBaseDir(baseDir);
        
        // Add webapp context
        Context ctx = tomcat.addContext("", baseDir);
        
        // PROGRAMMATICALLY register servlet
        System.out.println("📝 Registering servlet: GetAllDataServlet at /api/all");
        Tomcat.addServlet(ctx, "GetAllDataServlet", new GetAllDataServlet());
        ctx.addServletMappingDecoded("/api/all", "GetAllDataServlet");
        
        System.out.println("✅ Servlet registered successfully!");
        System.out.println("Context path: " + ctx.getPath());
        System.out.println("Base directory: " + baseDir);
        System.out.println("===========================================");
        
        // Start server
        tomcat.start();
        System.out.println("✅ Server started successfully!");
        System.out.println("📍 Listening on port: " + port);
        System.out.println("🔗 API available at: /api/all");
        System.out.println("===========================================");
        
        // Wait for shutdown
        tomcat.getServer().await();
    }
}

package emotionuniverse;

import java.io.File;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

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
        StandardContext ctx = (StandardContext) tomcat.addContext("", baseDir);
        
        // Enable annotation scanning for @WebServlet
        ctx.addLifecycleListener(new Tomcat.FixContextListener());
        
        // Add current directory as resource base
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);
        
        System.out.println("Context configured");
        System.out.println("Base directory: " + baseDir);
        System.out.println("===========================================");
        
        // Start server
        tomcat.start();
        System.out.println("✅ Server started successfully!");
        System.out.println("📍 Listening on port: " + port);
        System.out.println("🔗 Servlets should be available at /api/*");
        System.out.println("===========================================");
        
        // Wait for shutdown
        tomcat.getServer().await();
    }
}

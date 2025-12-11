package emotionuniverse.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import emotionuniverse.dao.EmotionDAO;
import emotionuniverse.dao.NebulaDAO;
import google.gson.Gson;

/**
 * Servlet to get ALL data (emotions + nebulas) in one request
 * Endpoint: /api/all
 */
@WebServlet("/api/all")
public class GetAllDataServlet extends HttpServlet {
    
    private EmotionDAO emotionDAO = new EmotionDAO();
    private NebulaDAO nebulaDAO = new NebulaDAO();
    private Gson gson = new Gson();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("📡 API Request: /api/all");
        
        // Set response headers
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // CORS headers
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        
        PrintWriter out = response.getWriter();
        Map<String, Object> result = new HashMap<>();
        
        try {
            // Get all data
            Map<String, Object> data = new HashMap<>();
            data.put("emotions", emotionDAO.getAllEmotions());
            data.put("nebulas", nebulaDAO.getAllNebulas());
            data.put("emotionTypes", emotionDAO.getAllEmotionTypes());
            
            result.put("success", true);
            result.put("data", data);
            
            System.out.println("✅ API Response: Success");
            
        } catch (Exception e) {
            System.err.println("❌ API Error: " + e.getMessage());
            e.printStackTrace();
            
            result.put("success", false);
            result.put("message", "Error: " + e.getMessage());
            
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        
        // Send JSON response
        out.print(gson.toJson(result));
        out.flush();
    }
    
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle CORS preflight
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}



















package emotionuniverse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emotionuniverse.model.Nebula;
import emotionuniverse.util.DatabaseUtil;

/**
 * Data Access Object for Nebulas
 */
public class NebulaDAO {
    
    /**
     * Get all nebulas from database
     */
    public List<Nebula> getAllNebulas() throws SQLException {
        List<Nebula> nebulas = new ArrayList<>();
        String sql = "SELECT * FROM nebula_images ORDER BY cost ASC, nebula_id ASC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                nebulas.add(mapResultSetToNebula(rs));
            }
            
            System.out.println("✅ Loaded " + nebulas.size() + " nebulas from database");
        }
        
        return nebulas;
    }
    
    /**
     * Get nebula by ID
     */
    public Nebula getNebulaById(int nebulaId) throws SQLException {
        String sql = "SELECT * FROM nebula_images WHERE nebula_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, nebulaId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToNebula(rs);
                }
            }
        }
        
        return null;
    }
    
    /**
     * Get nebulas by cost
     */
    public List<Nebula> getNebulasByCost(int cost) throws SQLException {
        List<Nebula> nebulas = new ArrayList<>();
        String sql = "SELECT * FROM nebula_images WHERE cost = ? ORDER BY nebula_id ASC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, cost);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    nebulas.add(mapResultSetToNebula(rs));
                }
            }
            
            System.out.println("✅ Loaded " + nebulas.size() + " nebulas with cost: " + cost);
        }
        
        return nebulas;
    }
    
    /**
     * Map ResultSet to Nebula object
     */
    private Nebula mapResultSetToNebula(ResultSet rs) throws SQLException {
        Nebula nebula = new Nebula();
        nebula.setNebulaId(rs.getInt("nebula_id"));
        nebula.setName(rs.getString("name"));
        nebula.setImageUrl(rs.getString("image_url"));
        nebula.setColor(rs.getString("color"));
        nebula.setCost(rs.getInt("cost"));
        nebula.setCreatedAt(rs.getTimestamp("created_at").toString());
        return nebula;
    }
}
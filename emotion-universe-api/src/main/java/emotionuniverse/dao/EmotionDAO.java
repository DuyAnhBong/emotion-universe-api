package emotionuniverse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emotionuniverse.model.Emotion;
import emotionuniverse.util.DatabaseUtil;

/**
 * Data Access Object for Emotions
 */
public class EmotionDAO {
    
    /**
     * Get all emotions from database
     */
    public List<Emotion> getAllEmotions() throws SQLException {
        List<Emotion> emotions = new ArrayList<>();
        String sql = "SELECT * FROM emotion_images ORDER BY emotion_id ASC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                emotions.add(mapResultSetToEmotion(rs));
            }
            
            System.out.println("✅ Loaded " + emotions.size() + " emotions from database");
        }
        
        return emotions;
    }
    
    /**
     * Get emotions by type
     */
    public List<Emotion> getEmotionsByType(String emotionType) throws SQLException {
        List<Emotion> emotions = new ArrayList<>();
        String sql = "SELECT * FROM emotion_images WHERE emotion_type = ? ORDER BY variant ASC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, emotionType);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    emotions.add(mapResultSetToEmotion(rs));
                }
            }
            
            System.out.println("✅ Loaded " + emotions.size() + " emotions of type: " + emotionType);
        }
        
        return emotions;
    }
    
    /**
     * Get emotion by ID
     */
    public Emotion getEmotionById(int emotionId) throws SQLException {
        String sql = "SELECT * FROM emotion_images WHERE emotion_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, emotionId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEmotion(rs);
                }
            }
        }
        
        return null;
    }
    
    /**
     * Get all unique emotion types
     */
    public List<String> getAllEmotionTypes() throws SQLException {
        List<String> types = new ArrayList<>();
        String sql = "SELECT DISTINCT emotion_type FROM emotion_images ORDER BY emotion_type ASC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                types.add(rs.getString("emotion_type"));
            }
            
            System.out.println("✅ Found " + types.size() + " emotion types");
        }
        
        return types;
    }
    
    /**
     * Map ResultSet to Emotion object
     */
    private Emotion mapResultSetToEmotion(ResultSet rs) throws SQLException {
        Emotion emotion = new Emotion();
        emotion.setEmotionId(rs.getInt("emotion_id"));
        emotion.setEmotionType(rs.getString("emotion_type"));
        emotion.setImageUrl(rs.getString("image_url"));
        emotion.setVariant(rs.getInt("variant"));
        emotion.setColor(rs.getString("color"));
        emotion.setDescription(rs.getString("description"));
        emotion.setCreatedAt(rs.getTimestamp("created_at").toString());
        return emotion;
    }
}

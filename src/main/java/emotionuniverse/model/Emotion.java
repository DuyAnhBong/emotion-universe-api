package emotionuniverse.model;

/**
 * Emotion model - matches emotion_images table
 */
public class Emotion {
    private int emotionId;
    private String emotionType;
    private String imageUrl;
    private int variant;
    private String color;
    private String description;
    private String createdAt;
    
    // Constructors
    public Emotion() {}
    
    public Emotion(int emotionId, String emotionType, String imageUrl, 
                   int variant, String color, String description) {
        this.emotionId = emotionId;
        this.emotionType = emotionType;
        this.imageUrl = imageUrl;
        this.variant = variant;
        this.color = color;
        this.description = description;
    }
    
    // Getters and Setters
    public int getEmotionId() {
        return emotionId;
    }
    
    public void setEmotionId(int emotionId) {
        this.emotionId = emotionId;
    }
    
    public String getEmotionType() {
        return emotionType;
    }
    
    public void setEmotionType(String emotionType) {
        this.emotionType = emotionType;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public int getVariant() {
        return variant;
    }
    
    public void setVariant(int variant) {
        this.variant = variant;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "Emotion{" +
                "emotionId=" + emotionId +
                ", emotionType='" + emotionType + '\'' +
                ", variant=" + variant +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

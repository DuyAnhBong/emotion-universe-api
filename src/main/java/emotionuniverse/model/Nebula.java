package emotionuniverse.model;

/**
 * Nebula model - matches nebula_images table
 */
public class Nebula {
    private int nebulaId;
    private String name;
    private String imageUrl;
    private String color;
    private int cost;
    private String createdAt;
    
    // Constructors
    public Nebula() {}
    
    public Nebula(int nebulaId, String name, String imageUrl, 
                  String color, int cost) {
        this.nebulaId = nebulaId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.color = color;
        this.cost = cost;
    }
    
    // Getters and Setters
    public int getNebulaId() {
        return nebulaId;
    }
    
    public void setNebulaId(int nebulaId) {
        this.nebulaId = nebulaId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public int getCost() {
        return cost;
    }
    
    public void setCost(int cost) {
        this.cost = cost;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "Nebula{" +
                "nebulaId=" + nebulaId +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", cost=" + cost +
                '}';
    }
}


package com.shaoume.dto;

public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private String icon;
    private String color;
    private boolean active;
    private Integer productCount;
    private String imageUrl;
    private Long parentId;

    public CategoryDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public Integer getProductCount() { return productCount; }
    public void setProductCount(Integer productCount) { this.productCount = productCount; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
}

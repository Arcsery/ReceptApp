package com.example.recept;

public class RecipeItem {
    private String id;
    private String userId;
    private String name;
    private String smallDescription;
    private String description;
    private String foodName;
    private float ratedInfo;
    private int imageResource;

    public  RecipeItem(){

    }

    public RecipeItem(String id, String userId, String name, String smallDescription, String description, String foodName, float ratedInfo, int imageResource) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.smallDescription = smallDescription;
        this.description = description;
        this.foodName = foodName;
        this.ratedInfo = ratedInfo;
        this.imageResource = imageResource;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getSmallDescription() {
        return smallDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getFoodName() {
        return foodName;
    }

    public float getRatedInfo() {
        return ratedInfo;
    }

    public int getImageResource() {
        return imageResource;
    }
}

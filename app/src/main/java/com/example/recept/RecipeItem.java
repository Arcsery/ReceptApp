package com.example.recept;

public class RecipeItem {
    private String name;
    private String description;
    private String foodName;
    private float ratedInfo;
    private final int imageResource;

    public RecipeItem(String name, String description, String foodName, float ratedInfo, int imageResource) {
        this.name = name;
        this.description = description;
        this.foodName = foodName;
        this.ratedInfo = ratedInfo;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
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

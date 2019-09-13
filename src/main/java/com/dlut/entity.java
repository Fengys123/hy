package com.dlut;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class entity {
    @SerializedName("category")
    private String category;

    @SerializedName("ingredients")
    private List<String> ingredients;

    @SerializedName("name")
    private String name;

    @SerializedName("area")
    private String area;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}

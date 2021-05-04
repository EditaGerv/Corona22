package com.example.margarita;

public class Margarita {
    private String id;
    private String name;
    private String types;
    private String category;
    private String glass;

    public Margarita(String id, String name, String types, String category, String glass) {
        this.id = id;
        this.name = name;
        this.types = types;
        this.category = category;
        this.glass = glass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }
    @Override
    public String toString() {
        return "Margarita{" +
                "Drink ID='" + id + '\'' +
                ", Drink Name='" + name + '\'' +
                ", Drink Type='" + types + '\'' +
                ", Category=" + category +
                ", Glass Type=" + glass +
                '}';
    }
}

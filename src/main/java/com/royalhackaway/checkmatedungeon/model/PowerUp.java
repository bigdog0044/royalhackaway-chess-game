package com.royalhackaway.checkmatedungeon.model;

public class PowerUp {
    private String name;
    private String description;

    public PowerUp(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // For simplicity, equals and hashCode are not overridden for MVP
}

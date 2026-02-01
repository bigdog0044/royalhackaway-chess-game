package com.royalhackaway.checkmatedungeon.model;

/**
 * Minimal AIPlayer representation for compile-time and integration testing.
 */
public class AIPlayer {
    private String id;
    private String name;

    public AIPlayer() {
        this.id = "ai";
        this.name = "Dungeon AI";
    }

    public AIPlayer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}

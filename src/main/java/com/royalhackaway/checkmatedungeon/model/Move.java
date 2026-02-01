package com.royalhackaway.checkmatedungeon.model;

public class Move {
    private Position from;
    private Position to;
    private String taunt; // AI taunt message (from Gemini)

    // Getters and setters are used by Jackson for deserialization
    public Position getFrom() {
        return from;
    }

    public void setFrom(Position from) {
        this.from = from;
    }

    public Position getTo() {
        return to;
    }

    public void setTo(Position to) {
        this.to = to;
    }

    public String getTaunt() {
        return taunt;
    }

    public void setTaunt(String taunt) {
        this.taunt = taunt;
    }
}

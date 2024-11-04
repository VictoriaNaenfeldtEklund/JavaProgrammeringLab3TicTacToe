package com.example.laboration3.model;

public class Player {

    private String name;
    private String symbol;
    private String score;

    public Player(String name, String symbol, String score) {
        this.name = name;
        this.symbol = symbol;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}

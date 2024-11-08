package com.example.laboration3.model;

import java.util.Random;

public class MyRandom extends Random {

    private int i;

    public MyRandom(int initialNextIntValue){
        this.i = initialNextIntValue;
    }

    @Override
    public int nextInt(int bound) {
        return i;
    }

    public void setNextInt(int newValue){
        this.i = newValue;
    }
}

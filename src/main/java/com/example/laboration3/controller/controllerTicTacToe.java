package com.example.laboration3.controller;

import com.example.laboration3.model.Model;
import com.example.laboration3.model.SquareIndex;
import javafx.scene.input.MouseEvent;

public class controllerTicTacToe {

    private final Model model = new Model();

    public Model getModel() {
        return model;
    }

    public void square0Clicked(MouseEvent mouseEvent) {
        model.setSquareSymbol(SquareIndex.ZERO);
    }

    public void square1Clicked(MouseEvent mouseEvent) {
        model.setSquareSymbol(SquareIndex.ONE);
    }

    public void square2Clicked(MouseEvent mouseEvent) {
        model.setSquareSymbol(SquareIndex.TWO);
    }

    public void square3Clicked(MouseEvent mouseEvent) {
        model.setSquareSymbol(SquareIndex.THREE);
    }

    public void square4Clicked(MouseEvent mouseEvent) {
        model.setSquareSymbol(SquareIndex.FOUR);
    }

    public void square5Clicked(MouseEvent mouseEvent) {
        model.setSquareSymbol(SquareIndex.FIVE);
    }

    public void square6Clicked(MouseEvent mouseEvent) {
        model.setSquareSymbol(SquareIndex.SIX);
    }

    public void square7Clicked(MouseEvent mouseEvent) {
        model.setSquareSymbol(SquareIndex.SEVEN);
    }

    public void square8Clicked(MouseEvent mouseEvent) {
        model.setSquareSymbol(SquareIndex.EIGHT);
    }
}

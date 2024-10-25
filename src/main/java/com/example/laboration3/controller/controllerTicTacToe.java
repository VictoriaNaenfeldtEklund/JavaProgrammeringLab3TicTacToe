package com.example.laboration3.controller;

import com.example.laboration3.model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class controllerTicTacToe {

    private Model model = new Model();

    public Model getModel() {
        return model;
    }

    @FXML
    public void buttonClicked(MouseEvent mouseEvent) {
        var square = (Button) mouseEvent.getSource();
        var squareSymbol = square.getText();

        if (squareSymbol.isEmpty()){
            model.play(square.getId());
        }
    }
}

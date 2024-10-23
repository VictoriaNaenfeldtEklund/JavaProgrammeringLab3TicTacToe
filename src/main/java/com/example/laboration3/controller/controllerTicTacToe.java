package com.example.laboration3.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class controllerTicTacToe {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}

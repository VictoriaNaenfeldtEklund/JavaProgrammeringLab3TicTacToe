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
        model.playGame(SquareIndex.ZERO);
    }

    public void square1Clicked(MouseEvent mouseEvent) {
        model.playGame(SquareIndex.ONE);
    }

    public void square2Clicked(MouseEvent mouseEvent) {
        model.playGame(SquareIndex.TWO);
    }

    public void square3Clicked(MouseEvent mouseEvent) {
        model.playGame(SquareIndex.THREE);
    }

    public void square4Clicked(MouseEvent mouseEvent) {
        model.playGame(SquareIndex.FOUR);
    }

    public void square5Clicked(MouseEvent mouseEvent) {
        model.playGame(SquareIndex.FIVE);
    }

    public void square6Clicked(MouseEvent mouseEvent) {
        model.playGame(SquareIndex.SIX);
    }

    public void square7Clicked(MouseEvent mouseEvent) {
        model.playGame(SquareIndex.SEVEN);
    }

    public void square8Clicked(MouseEvent mouseEvent) {
        model.playGame (SquareIndex.EIGHT);
    }

    public void opponentComputerClicked(MouseEvent mouseEvent) {
        model.startGameComputer();
    }

    public void opponentFriendClicked(MouseEvent mouseEvent) {
        model.startGameFriend();
    }
}

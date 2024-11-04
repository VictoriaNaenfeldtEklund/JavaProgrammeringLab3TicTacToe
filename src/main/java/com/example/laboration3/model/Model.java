package com.example.laboration3.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.stream.IntStream;

import static com.example.laboration3.model.GameState.*;

public class Model {

    private GameState gameState = GAME_OVER;
    private Player player1 = new Player("Player 1", "X", 0);
    private Player player2 = new Player("Player 2", "O", 0);
    private Player currentPlayer = player1;
    private ObservableList<String> squares = FXCollections.observableArrayList();

    public Model(){
        IntStream.range(0, 9).forEach(_ -> squares.add(""));
    }

    public void startGameComputer(){
        resetGame();
    }

    private void resetGame(){
        resetSquaresToEmpty();
    }

    private void resetSquaresToEmpty() {
        IntStream.range(0, 9).forEach(i -> squares.set(i, ""));
    }

    public ObservableList<String> getSquares() {
        return squares;
    }

    public void setSquareSymbol(SquareIndex squareIndex){
        squares.set(squareIndex.ordinal(), currentPlayer.symbol());
    }
}

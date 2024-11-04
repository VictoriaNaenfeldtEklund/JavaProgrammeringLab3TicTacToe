package com.example.laboration3.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Random;
import java.util.stream.IntStream;

import static com.example.laboration3.model.GameState.*;

public class Model {

    Random random = new Random();
    private GameState currentGameState = GAME_OVER;
    private GameState lastGameState = GAME_OVER;
    private String gamesPlayed = "0";
    private String gameMessage = "";

    private Player player1 = new Player("Player 1", "X", "0");
    private Player player2 = new Player("Player 2", "O", "0");
    private Player currentPlayer = player1;
    private ObservableList<String> squares = FXCollections.observableArrayList();

    public Model(){
        IntStream.range(0, 9).forEach(_ -> squares.add(""));
    }

    public void startGameFriend() {
        resetGame(GAME_FRIEND);
    }

    public void startGameComputer() {
        resetGame(GAME_COMPUTER);

    }

    private void resetGame(GameState newGameState) {

        if(currentGameState != newGameState){
            resetPlayerPointsToZero();
            gamesPlayed = "0";
        }

        resetSquaresToEmpty();
        setCurrentPlayerToRandomStartPlayer();
    }

    private void setCurrentPlayerToRandomStartPlayer() {
       currentPlayer = random.nextInt(2) == 0 ? player1 : player2;
    }

    private void resetSquaresToEmpty() {
        IntStream.range(0, 9).forEach(i -> squares.set(i, ""));
    }

    private void resetPlayerPointsToZero(){
        player1.setScore("0");
        player2.setScore("0");
    }

    public void setSquareSymbol(SquareIndex squareIndex){
        if(squares.get(squareIndex.ordinal()).isEmpty()){
            squares.set(squareIndex.ordinal(), currentPlayer.getSymbol());
        }
    }

    // GET METHODS

    public ObservableList<String> getSquares() {
        return squares;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public String getGamesPlayed() {
        return gamesPlayed;
    }

    public String getGameMessage() {
        return gameMessage;
    }
}

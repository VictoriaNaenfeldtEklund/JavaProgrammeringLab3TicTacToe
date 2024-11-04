package com.example.laboration3.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import static com.example.laboration3.model.GameState.*;

public class Model {

    Random random = new Random();
    private GameState currentGameState = GAME_OVER;
    private GameState lastGameState = GAME_OVER;
    private IntegerProperty gamesPlayed = new SimpleIntegerProperty(0);
    private StringProperty gameMessage = new SimpleStringProperty("");

    private Player player1 = new Player("Player 1", "X");
    private Player player2 = new Player("Player 2", "O");
    private Player currentPlayer = player1;
    private ObservableList<String> squares = FXCollections.observableArrayList();

    public Model(){
        IntStream.range(0, 9).forEach(_ -> squares.add(""));
    }

    public void startGameFriend() {
        currentGameState = GAME_FRIEND;
        resetGame();
        lastGameState = GAME_FRIEND;
    }

    public void startGameComputer() {
        currentGameState = GAME_COMPUTER;
        resetGame();
        lastGameState = GAME_COMPUTER;

        if(currentPlayer.equals(player2)){
            makeComputerMove();
            switchCurrentPlayer();
        }
    }

    public void playGame(SquareIndex squareIndex){

        if(currentGameState != GAME_OVER){
            setSquareSymbol(squareIndex);
            checkForWinning();
            switchCurrentPlayer();

            if (currentGameState == GAME_COMPUTER){
                makeComputerMove();
                checkForWinning();
                switchCurrentPlayer();
            }
        }
    }

    private void checkForWinning(){
        if(checkForWinningRow() || checkForWinningColumn() || checkForWinningDiagonal()){
            currentGameState = GAME_OVER;
            setGamesPlayed(getGamesPlayed() + 1);
            currentPlayer.setScore(currentPlayer.getScore() + 1);
            setGameMessage(currentPlayer.getName() + " wins!");
        } else if (checkForDraw()){
            currentGameState = GAME_OVER;
            setGameMessage("DRAW!");
        }
    }

    private boolean checkForWinningRow() {
        for (int i = 0; i < 7; i = i + 3) {
            String square1 = squares.get(i);
            String square2 = squares.get(i + 1);
            String square3 = squares.get(i + 2);

            if(!square1.isEmpty() && square1.equals(square2) && square2.equals(square3)){
                return true;
            }
        }
        return false;
    }

    private boolean checkForWinningColumn() {
        for (int i = 0; i < 3; i++) {
            String square1 = squares.get(i);
            String square2 = squares.get(i+3);
            String square3 = squares.get(i+6);

            if(!square1.isEmpty() && square1.equals(square2) && square2.equals(square3)){
                return true;
            }
        }
        return false;
    }

    private boolean checkForWinningDiagonal(){
        String s0 = squares.get(0);
        String s2 = squares.get(2);
        String s4 = squares.get(4);
        String s6 = squares.get(6);
        String s8 = squares.get(8);

        return !s4.isEmpty() && (s0.equals(s4) && s4.equals(s8) || s2.equals(s4) && s4.equals(s6));
    }

    private boolean checkForDraw(){
        return squares.stream().noneMatch(String::isEmpty);
    }

    private void makeComputerMove() {

        SquareIndex randomSquareIndex;

        do {
            randomSquareIndex = Arrays.stream(SquareIndex.values()).toList().get(random.nextInt(9));
        } while(!setSquareSymbol(randomSquareIndex));
    }

    private void resetGame() {

        if(lastGameState != currentGameState){
            resetScoresToZero();
        }
        resetSquaresToEmpty();
        switchCurrentPlayer();
        setGameMessage(currentPlayer.getName() + " starts!");
    }

    private void resetScoresToZero() {
        player1.setScore(0);
        player2.setScore(0);
        setGamesPlayed(0);
    }

    private void resetSquaresToEmpty() {
        IntStream.range(0, 9).forEach(i -> squares.set(i, ""));
    }

    private boolean setSquareSymbol(SquareIndex squareIndex){
        if(squares.get(squareIndex.ordinal()).isEmpty()){
            squares.set(squareIndex.ordinal(), currentPlayer.getSymbol());
            return true;
        }
        return false;
    }

    private void switchCurrentPlayer(){
        currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
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

    // GAME PROPERTIES

    public String getGameMessage() {
        return gameMessage.get();
    }

    public StringProperty gameMessageProperty() {
        return gameMessage;
    }

    public void setGameMessage(String gameMessage) {
        this.gameMessage.set(gameMessage);
    }

    public int getGamesPlayed() {
        return gamesPlayed.get();
    }

    public IntegerProperty gamesPlayedProperty() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed.set(gamesPlayed);
    }
}

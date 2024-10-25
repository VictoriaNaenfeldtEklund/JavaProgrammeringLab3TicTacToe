package com.example.laboration3.model;

import static com.example.laboration3.model.Gamestate.*;
import javafx.beans.property.*;
import java.util.ArrayList;
import java.util.List;

public class Model {

    private Gamestate gameState = GAMEOVER;
    private final IntegerProperty gamesPlayed = new SimpleIntegerProperty(0);
    private final StringProperty gameStateMessage = new SimpleStringProperty("");

    private final IntegerProperty scorePlayer1 = new SimpleIntegerProperty(0);
    private final IntegerProperty scorePlayer2 = new SimpleIntegerProperty(0);
    private final Player player1 = new Player("Player1", "X", scorePlayer1);
    private final Player player2 = new Player("Player2", "O", scorePlayer2);
    private Player currentPlayer = player1;

    private final StringProperty square1 = new SimpleStringProperty("");
    private final StringProperty square2 = new SimpleStringProperty("");
    private final StringProperty square3 = new SimpleStringProperty("");
    private final StringProperty square4 = new SimpleStringProperty("");
    private final StringProperty square5 = new SimpleStringProperty("");
    private final StringProperty square6 = new SimpleStringProperty("");
    private final StringProperty square7 = new SimpleStringProperty("");
    private final StringProperty square8 = new SimpleStringProperty("");
    private final StringProperty square9 = new SimpleStringProperty("");
    private final List<StringProperty> squares = new ArrayList<>(9);

    public Model() {
        squares.addAll(List.of(square1, square2, square3, square4, square5, square6, square7, square8, square9));
    }

    public void play(String squareID){

        if(squareID.equals("startButton")) {
            resetGame();
            gameState = GAME;
        }

        if(gameState == GAME){
            setPlayerSymbolToSquare(squareID);
            checkForWinnerOrDraw();

            if(gameState == GAMEOVER){
                gamesPlayed.set(gamesPlayed.get() + 1);
            } else {
                switchCurrentPlayer();
            }
        }
    }

    private void resetGame(){
        for(StringProperty square : squares){
            square.setValue("");
        }
    }

    private void setPlayerSymbolToSquare(String squareID) {
        switch(squareID){
            case "square1" -> setSquare1(currentPlayer.symbol());
            case "square2" -> setSquare2(currentPlayer.symbol());
            case "square3" -> setSquare3(currentPlayer.symbol());
            case "square4" -> setSquare4(currentPlayer.symbol());
            case "square5" -> setSquare5(currentPlayer.symbol());
            case "square6" -> setSquare6(currentPlayer.symbol());
            case "square7" -> setSquare7(currentPlayer.symbol());
            case "square8" -> setSquare8(currentPlayer.symbol());
            case "square9" -> setSquare9(currentPlayer.symbol());
        }
    }

    private void checkForWinnerOrDraw(){

        if (checkForWinner()){
            gameStateMessage.set("Winner is " + currentPlayer.name() + "(" + currentPlayer.symbol() + ")!");
            addPointToPlayerScore();
            gameState = GAMEOVER;
        }

        if(checkForDraw()){
            gameStateMessage.set("DRAW!");
            gameState = GAMEOVER;
        }
    }

    private boolean checkForDraw() {
        return squares.stream().noneMatch(square -> square.get().isEmpty());
    }

    private boolean checkForWinner(){
        if(checkForWinnerRow()){
            return true;
        }

        if(checkForWinnerColumn()){
            return true;
        }

        return checkForWinnerDiagonal();
    }

    private boolean checkForWinnerRow(){

        for (int i = 0; i < 7; i = i + 3) {

            String symbol1 = squares.get(i).get();
            String symbol2 = squares.get(i + 1).get();
            String symbol3 = squares.get(i + 2).get();

            if (isSameAndNotEmpty(symbol1, symbol2, symbol3)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkForWinnerColumn(){
        for (int i = 0; i < 3; i++) {

            String symbol1 = squares.get(i).get();
            String symbol2 = squares.get(i + 3).get();
            String symbol3 = squares.get(i + 6).get();

            if (isSameAndNotEmpty(symbol1, symbol2, symbol3)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkForWinnerDiagonal(){

        // Diagonal 1
        String symbol1 = squares.get(0).get();
        String symbol2 = squares.get(4).get();
        String symbol3 = squares.get(8).get();

        if(isSameAndNotEmpty(symbol1, symbol2, symbol3)){
            return true;
        }

        // Diagonal 2
        symbol1 = squares.get(2).get();
        symbol2 = squares.get(4).get();
        symbol3 = squares.get(6).get();

        return isSameAndNotEmpty(symbol1, symbol2, symbol3);
    }

    private boolean isSameAndNotEmpty(String symbol1, String symbol2, String symbol3){
        if (!symbol1.isEmpty() || !symbol2.isEmpty() || !symbol3.isEmpty()) {
            return symbol1.equals(symbol2) && symbol1.equals(symbol3);
        }
        return false;
    }

    private void addPointToPlayerScore() {
        if(currentPlayer.equals(player1)){
            scorePlayer1.set(scorePlayer1.get() + 1);
        } else {
            scorePlayer2.set(scorePlayer2.get() + 1);
        }
    }

    private void switchCurrentPlayer() {
        if(currentPlayer.equals(player1)) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    // PROPERTY GETTERS AND SETTERS

    public String getSquare1() {
        return square1.get();
    }

    public StringProperty square1Property() {
        return square1;
    }

    public void setSquare1(String square1) {
        this.square1.set(square1);
    }

    public String getSquare2() {
        return square2.get();
    }

    public StringProperty square2Property() {
        return square2;
    }

    public void setSquare2(String square2) {
        this.square2.set(square2);
    }

    public String getSquare3() {
        return square3.get();
    }

    public StringProperty square3Property() {
        return square3;
    }

    public void setSquare3(String square3) {
        this.square3.set(square3);
    }

    public String getSquare4() {
        return square4.get();
    }

    public StringProperty square4Property() {
        return square4;
    }

    public void setSquare4(String square4) {
        this.square4.set(square4);
    }

    public String getSquare5() {
        return square5.get();
    }

    public StringProperty square5Property() {
        return square5;
    }

    public void setSquare5(String square5) {
        this.square5.set(square5);
    }

    public String getSquare6() {
        return square6.get();
    }

    public StringProperty square6Property() {
        return square6;
    }

    public void setSquare6(String square6) {
        this.square6.set(square6);
    }

    public String getSquare7() {
        return square7.get();
    }

    public StringProperty square7Property() {
        return square7;
    }

    public void setSquare7(String square7) {
        this.square7.set(square7);
    }

    public String getSquare8() {
        return square8.get();
    }

    public StringProperty square8Property() {
        return square8;
    }

    public void setSquare8(String square8) {
        this.square8.set(square8);
    }

    public String getSquare9() {
        return square9.get();
    }

    public StringProperty square9Property() {
        return square9;
    }

    public void setSquare9(String square9) {
        this.square9.set(square9);
    }

    public int getScorePlayer1() {
        return scorePlayer1.get();
    }

    public IntegerProperty scorePlayer1Property() {
        return scorePlayer1;
    }

    public void setScorePlayer1(int scorePlayer1) {
        this.scorePlayer1.set(scorePlayer1);
    }

    public int getScorePlayer2() {
        return scorePlayer2.get();
    }

    public IntegerProperty scorePlayer2Property() {
        return scorePlayer2;
    }

    public void setScorePlayer2(int scorePlayer2) {
        this.scorePlayer2.set(scorePlayer2);
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

    public String getGameStateMessage() {
        return gameStateMessage.get();
    }

    public StringProperty gameStateMessageProperty() {
        return gameStateMessage;
    }

    public void setGameStateMessage(String gameStateMessage) {
        this.gameStateMessage.set(gameStateMessage);
    }
}

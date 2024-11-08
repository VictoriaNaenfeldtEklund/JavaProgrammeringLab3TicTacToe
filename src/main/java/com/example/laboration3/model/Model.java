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

    // CONSTRUCTORS

    /**
     * Constructor default.
     * Initializes a list of 9 TicTacToe squares.
     */
    public Model(){
        IntStream.range(0, 9).forEach(_ -> squares.add(""));
    }

    /**
     * Constructor for test class to be able to set a fixed value for random (computer move).
     * Initializes a list of 9 TicTacToe squares.
     * @param random Random
     */
    public Model(Random random){
        this.random = random;
        IntStream.range(0, 9).forEach(_ -> squares.add(""));
    }

    // GAMEPLAY

    /**
     * Call to start game with friend.
     * Sets current- and lastGameState to GAME_FRIEND.
     * Resets game.
     */
    public void startGameFriend() {
        currentGameState = GAME_FRIEND;
        resetGame();
        lastGameState = GAME_FRIEND;
    }

    /**
     * Call to start game with computer.
     * Sets current- and lastGameState to GAME_COMPUTER.
     * Resets game.
     * If player2 is the currentPlayer it starts the game with a computer move.
     */
    public void startGameComputer() {
        currentGameState = GAME_COMPUTER;
        resetGame();
        lastGameState = GAME_COMPUTER;

        if(currentPlayer.equals(player2)){
            makeComputerMove();
            switchCurrentPlayer();
        }
    }

    /**
     * GameState.GAME_OVER:     1. Does nothing.
     * GameState.GAME_FRIEND:   1. Sets currentPlayers symbol to clicked square if empty, else leaves method.
     *                          2. If currentPlayer did not win, switch player, else ends game within method checkForWinnerOrDraw().
     * GameState.GAME_COMPUTER: 1. Same 2 steps as GAME_FRIEND.
     *                          2. Sets computer (player 2) symbol to random empty square.
     *                          3. If computer did not win, switch player, else ends game within method checkForWinnerOrDraw().
     * @param squareIndex SquareIndex: index for where to set player symbol in list of squares.
     */
    public void playGame(SquareIndex squareIndex){

        if(currentGameState != GAME_OVER){
            if(setSquareSymbol(squareIndex)){
                if(!checkForWinnerOrDraw()){
                    switchCurrentPlayer();
                    if (currentGameState == GAME_COMPUTER){
                        makeComputerMove();
                        if(!checkForWinnerOrDraw()){
                            switchCurrentPlayer();
                        }
                    }
                }
            }
        }
    }

    /**
     * Sets computer (player2) symbol to random empty square if there is any empty square left,
     * else does nothing.
     */
    public void makeComputerMove() {

        SquareIndex randomSquareIndex;

        // if-condition is implemented as extra safety but is not really needed for current controller setup as the playGame method
        // never calls this method with a full board.
        if(!isBoardFull()){
            do {
                randomSquareIndex = Arrays.stream(SquareIndex.values()).toList().get(random.nextInt(9));
            } while(!setSquareSymbol(randomSquareIndex));
        }
    }

    /**
     * Sets currentPlayer:s symbol to clicked square (represented by passed squareIndex) if empty.
     * @param squareIndex SquareIndex: represents clicked square:s index (0-8) in the square-list.
     * @return true if symbol is set to empty square, else false.
     */
    public boolean setSquareSymbol(SquareIndex squareIndex){
        if(squares.get(squareIndex.ordinal()).isEmpty()){
            squares.set(squareIndex.ordinal(), currentPlayer.getSymbol());
            return true;
        }
        return false;
    }

    /**
     * Switches currentPlayer (player1/player2).
     */
    public void switchCurrentPlayer(){
        currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
    }

    // TESTS

    /**
     * Checks for winning player (row/column/diagonal) or draw.
     * Sets gameState to GAME_OVER and adds +1 to gamesPlayed if found.
     * @return boolean: true if winner or draw was found, else false.
     */
    public boolean checkForWinnerOrDraw(){
        if(checkForWinner() || checkForDraw()){
            currentGameState = GAME_OVER;
            setGamesPlayed(getGamesPlayed() + 1);
            return true;
        }
        return false;
    }

    /**
     * Checks for winning row, column or diagonal.
     * Adds +1 point to currentPlayer score and sets gameMessage to winner message if found.
     * @return boolean: true if winner was found, else false.
     */
    public boolean checkForWinner(){
        if(checkForWinnerRow() || checkForWinnerColumn() || checkForWinnerDiagonal()){
            currentPlayer.setScore(currentPlayer.getScore() + 1);
            setGameMessage(currentPlayer.getName() + " wins!");
            return true;
        }
        return false;
    }

    /**
     * Checks for winning row. 3 row squares with the same symbol (!empty).
     * @return boolean: true if winning row was found, else false.
     */
    public boolean checkForWinnerRow() {
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

    /**
     * Checks for winning column. 3 column squares with the same symbol (!empty).
     * @return boolean: true if winning column was found, else false.
     */
    public boolean checkForWinnerColumn() {
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

    /**
     * Checks for winning diagonal. 3 diagonal squares with the same symbol (!empty).
     * @return boolean: true if winning diagonal was found, else false.
     */
    public boolean checkForWinnerDiagonal(){
        String s0 = squares.get(0);
        String s2 = squares.get(2);
        String s4 = squares.get(4);
        String s6 = squares.get(6);
        String s8 = squares.get(8);

        return !s4.isEmpty() && ((s0.equals(s4) && s4.equals(s8)) || (s2.equals(s4) && s4.equals(s6)));
    }

    /**
     * Check for draw which is a full !empty board.
     * Sets gameMessage to draw message if found.
     * @return boolean: true if draw was found, else false.
     */
    public boolean checkForDraw(){
        if(isBoardFull()){
            setGameMessage("DRAW!");
            return true;
        }
        return false;
    }

    /**
     * Tests if board is full by checking if there is no empty squares.
     * @return boolean: true if board is full, else false.
     */
    public boolean isBoardFull(){
        return squares.stream().noneMatch(String::isEmpty);
    }

    // RESETS GAME

    /**
     * Resets game.
     * - Sets players score and gamesPlayed to 0 if new gameState (GAME_FRIEND/GAME_COMPUTER).
     * - Sets all squares to "".
     * - Switches player so that the player who did not make the last move gets to start.
     * - Sets gameMessage to show start player.
     */
    public void resetGame() {

        if(lastGameState != currentGameState){
            resetScoresToZero();
        }
        resetSquaresToEmpty();
        switchCurrentPlayer();
        setGameMessage(currentPlayer.getName() + " starts!");
    }

    /**
     * Sets player scores and gamesPlayed to 0.
     */
    public void resetScoresToZero() {
        player1.setScore(0);
        player2.setScore(0);
        setGamesPlayed(0);
    }

    /**
     * Sets all squares to empty ("").
     */
    public void resetSquaresToEmpty() {
        IntStream.range(0, 9).forEach(i -> squares.set(i, ""));
    }

    // GETTERS AND SETTERS

    public ObservableList<String> getSquares() {
        return squares;
    }

    public void setSquares(ObservableList<String> squares) {
        this.squares = squares;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public GameState getCurrentGameState(){
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;
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

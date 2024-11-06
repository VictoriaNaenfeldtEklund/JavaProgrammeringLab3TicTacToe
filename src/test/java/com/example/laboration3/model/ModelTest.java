package com.example.laboration3.model;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    Model model = new Model();

    @Test
    void initialCallToStartGameComputerMakesAComputerMove(){
        Model modelWithRandom = new Model(new MyRandom());
        modelWithRandom.startGameComputer();
        assertEquals(modelWithRandom.getPlayer2().getSymbol(), modelWithRandom.getSquares().getFirst());
    }

    @Test
    void initialCallToStartGameComputerWhenSwitchingStartPlayerSkipsComputerMoveAndKeepsCurrentPlayerToPlayer1(){
        // currentPlayer = player2
        // reset game changes currentPlayer = player1
        // computer move if-condition does not change changes currentPlayer = player1
        model.setCurrentPlayer(model.getPlayer2());
        model.startGameComputer();
        assertEquals(model.getPlayer1(), model.getCurrentPlayer());
    }

    @Test
    void callToMakeComputerMoveDoesNotMakeAMoveIfBoardIsFull(){
        ObservableList<String> squares = model.getSquares();
        squares.setAll(model.getPlayer1().getSymbol());
        model.makeComputerMove();

        assertAll(
                () -> assertTrue(model.isBoardFull()),
                () -> assertEquals(model.getPlayer1().getSymbol(), model.getSquares().getFirst())
        );
    }

    @Test
    @DisplayName("GAME_COMPUTER - Call to playGame with winning square for computer sets GameState to GAME_OVER.")
    void callToPlayGameWithWinningSquareWhenGameStateIsGameComputerChangesGameStateToGameOver(){
        Random random = new MyRandom();
        Model modelWithRandom = new Model(random);

        ObservableList<String> squares = model.getSquares();
        squares.set(0, model.getPlayer2().getSymbol());
        squares.set(4, model.getPlayer2().getSymbol());
        squares.set(8, model.getPlayer2().getSymbol());
        squares.set(3, model.getPlayer1().getSymbol());

        ObservableList<String> squaresInGame = modelWithRandom.getSquares();
        squaresInGame.set(4, modelWithRandom.getPlayer2().getSymbol());
        squaresInGame.set(8, modelWithRandom.getPlayer2().getSymbol());
        modelWithRandom.setSquares(squaresInGame);

        modelWithRandom.setCurrentGameState(GameState.GAME_COMPUTER);
        // sets player1 symbol to index 3 and player2 (computer) symbol to index 0 (via override nextInt).
        modelWithRandom.playGame(SquareIndex.THREE);

        assertAll(
                () -> assertEquals(GameState.GAME_OVER, modelWithRandom.getCurrentGameState()),
                () -> assertEquals(squares, squaresInGame)
        );
    }

    @Test
    @DisplayName("GAME_FRIEND - Call to playGame with winning square for player sets GameState to GAME_OVER.")
    void callToPlayGameWithWinningSquareWhenGameStateIsGameFriendChangesGameStateToGameOver(){

        model.startGameFriend();

        ObservableList<String> squares = model.getSquares();
        squares.set(0, model.getCurrentPlayer().getSymbol());
        squares.set(1, model.getCurrentPlayer().getSymbol());
        model.setSquares(squares);
        model.playGame(SquareIndex.TWO);

        assertEquals(GameState.GAME_OVER, model.getCurrentGameState());
    }

    @Test
    @DisplayName("GAME_COMPUTER - Call to playGame with no winning square for computer keeps GameState GAME_COMPUTER.")
    void callToPlayGameWithNoWinningSquareWhenGameStateIsGameComputerDoesNotChangeGameState(){
        Random random = new MyRandom();
        Model modelWithRandom = new Model(random);

        ObservableList<String> squares = model.getSquares();
        squares.set(0, model.getPlayer2().getSymbol());
        squares.set(2, model.getPlayer2().getSymbol());
        squares.set(8, model.getPlayer2().getSymbol());
        squares.set(4, model.getPlayer1().getSymbol());

        ObservableList<String> squaresInGame = modelWithRandom.getSquares();
        squaresInGame.set(2, modelWithRandom.getPlayer2().getSymbol());
        squaresInGame.set(8, modelWithRandom.getPlayer2().getSymbol());
        modelWithRandom.setSquares(squaresInGame);

        modelWithRandom.setCurrentGameState(GameState.GAME_COMPUTER);
        // sets player1 symbol to index 3 and player2 (computer) symbol to index 0 (via override nextInt).
        modelWithRandom.playGame(SquareIndex.FOUR);

        assertAll(
                () -> assertEquals(GameState.GAME_COMPUTER, modelWithRandom.getCurrentGameState()),
                () -> assertEquals(squares, squaresInGame)
        );
    }

    @Test
    void callToPlayGameWhenGameStateIsGameOverDoesNotChangeSquareList(){
        model.playGame(SquareIndex.ZERO);
        assertEquals("", model.getSquares().getFirst());
    }

    @Test
    void callToPlayGameWithOccupiedSquareWhenGameStateIsGameFriendDoesNotChangeList(){

        // start player = player 2.
        model.startGameFriend();
        ObservableList<String> squares = model.getSquares();
        squares.set(2, model.getPlayer1().getSymbol());
        model.playGame(SquareIndex.TWO);
        // checks so that playGame has not changed index 2 to player2 symbol.
        assertEquals(model.getSquares().get(2), model.getPlayer1().getSymbol());
    }

    @Test
    void callToCheckForWinnerReturnsTrueForFirstRowWin(){
        ObservableList<String> squares = model.getSquares();
        squares.set(0, model.getCurrentPlayer().getSymbol());
        squares.set(1, model.getCurrentPlayer().getSymbol());
        squares.set(2, model.getCurrentPlayer().getSymbol());
        model.setSquares(squares);

        assertTrue(model.checkForWinner());
    }

    @Test
    void callToCheckForWinnerReturnsTrueForSecondRowWin(){
        ObservableList<String> squares = model.getSquares();
        squares.set(3, model.getCurrentPlayer().getSymbol());
        squares.set(4, model.getCurrentPlayer().getSymbol());
        squares.set(5, model.getCurrentPlayer().getSymbol());
        model.setSquares(squares);

        assertTrue(model.checkForWinner());
    }

    @Test
    void callToCheckForWinnerReturnsTrueForThirdRowWin(){
        ObservableList<String> squares = model.getSquares();
        squares.set(6, model.getCurrentPlayer().getSymbol());
        squares.set(7, model.getCurrentPlayer().getSymbol());
        squares.set(8, model.getCurrentPlayer().getSymbol());
        model.setSquares(squares);

        assertTrue(model.checkForWinner());
    }

    @Test
    void callToCheckForWinnerReturnsTrueForUserFirstColWin(){
        ObservableList<String> squares = model.getSquares();
        squares.set(0, model.getCurrentPlayer().getSymbol());
        squares.set(3, model.getCurrentPlayer().getSymbol());
        squares.set(6, model.getCurrentPlayer().getSymbol());
        model.setSquares(squares);

        assertTrue(model.checkForWinner());
    }

    @Test
    void callToCheckForWinnerReturnsTrueForUserSecondColWin(){
        ObservableList<String> squares = model.getSquares();
        squares.set(1, model.getCurrentPlayer().getSymbol());
        squares.set(4, model.getCurrentPlayer().getSymbol());
        squares.set(7, model.getCurrentPlayer().getSymbol());
        model.setSquares(squares);

        assertTrue(model.checkForWinner());
    }

    @Test
    void callToCheckForWinnerReturnsTrueForUserThirdColWin(){
        ObservableList<String> squares = model.getSquares();
        squares.set(2, model.getCurrentPlayer().getSymbol());
        squares.set(5, model.getCurrentPlayer().getSymbol());
        squares.set(8, model.getCurrentPlayer().getSymbol());
        model.setSquares(squares);

        assertTrue(model.checkForWinner());
    }

    @Test
    void callToCheckForWinnerReturnsTrueForUserLeftToRightDiagonalWin(){
        ObservableList<String> squares = model.getSquares();
        squares.set(0, model.getCurrentPlayer().getSymbol());
        squares.set(4, model.getCurrentPlayer().getSymbol());
        squares.set(8, model.getCurrentPlayer().getSymbol());
        model.setSquares(squares);

        assertTrue(model.checkForWinner());
    }

    @Test
    void callToCheckForWinnerReturnsTrueForUserRightToLeftDiagonalWin(){
        ObservableList<String> squares = model.getSquares();
        squares.set(2, model.getCurrentPlayer().getSymbol());
        squares.set(4, model.getCurrentPlayer().getSymbol());
        squares.set(6, model.getCurrentPlayer().getSymbol());
        model.setSquares(squares);

        assertTrue(model.checkForWinner());
    }

    @Test
    void callToCheckForWinnerReturnsFalseForUserNoRowWin(){
        ObservableList<String> squares = model.getSquares();
        squares.set(0, model.getCurrentPlayer().getSymbol());
        squares.set(1, model.getCurrentPlayer().getSymbol());
        squares.set(3, model.getCurrentPlayer().getSymbol());
        model.setSquares(squares);

        assertFalse(model.checkForWinner());
    }

    @Test
    void callToCheckForDrawReturnsTrueForFullNotEmptyBoard(){
        ObservableList<String> squares = model.getSquares();
        squares.setAll(model.getCurrentPlayer().getSymbol());
        model.setSquares(squares);

        assertTrue(model.checkForDraw());
    }

    @Test
    void callToCheckForDrawReturnsFalseForFullEmptyBoard(){
        assertFalse(model.checkForDraw());
    }

    @Test
    void callToCheckForWinnerOrDrawWhenDrawSetsGameMessageToDraw(){
        String x = model.getPlayer1().getSymbol();
        String o = model.getPlayer2().getSymbol();

        ObservableList<String> squares = model.getSquares();
        squares.set(0, x);
        squares.set(1, x);
        squares.set(2, o);
        squares.set(3, o);
        squares.set(4, o);
        squares.set(5, x);
        squares.set(6, x);
        squares.set(7, x);
        squares.set(8, o);
        model.setSquares(squares);

        model.checkForWinnerOrDraw();

        assertEquals(model.getGameMessage(), "DRAW!");
    }
}

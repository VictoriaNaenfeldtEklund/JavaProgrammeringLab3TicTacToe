package com.example.laboration3.model;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  player1 = "X"
 *  player2 = "O"   (Computer is always player2)
 *  If call to startGameComputer() or startGameFriend() then initial start player is player2, else start player is player1.
 *  MyRandom overrides Random method nextInt(int bound), used for tests with random computer move.
 */
class ModelTest {

    Model model = new Model();

    // TEST CODE FOR INITIAL STATE

    @Test
    @DisplayName("GAME_COMPUTER: Initial call to startGameComputer() makes a computer move (sets player2 symbol to square 0 and changes currentGameState to GAME_COMPUTER).")
    void initialCallToStartGameComputerMakesAComputerMove(){

        Model modelWithMyRandom = new Model(new MyRandom(0));

        modelWithMyRandom.startGameComputer();

        assertAll(
                () -> assertEquals(modelWithMyRandom.getPlayer2().getSymbol(), modelWithMyRandom.getSquares().getFirst()),
                () -> assertEquals(GameState.GAME_COMPUTER, modelWithMyRandom.getCurrentGameState())
        );
    }

    @Test
    @DisplayName("GAME_COMPUTER: Initial call to startGameComputer() when start player is player1 does not make a computer move (squares should be empty but changes currentGameState to GAME_COMPUTER).")
    void initialCallToStartGameComputerWhenStartPlayerIsPlayer1DoesNotMakeAComputerMove(){

        Model modelWithMyRandom = new Model(new MyRandom(0));
        modelWithMyRandom.switchCurrentPlayer();

        modelWithMyRandom.startGameComputer();

        assertAll(
                () -> assertEquals(GameState.GAME_COMPUTER, modelWithMyRandom.getCurrentGameState()),
                () -> assertTrue(modelWithMyRandom.getSquares().stream().allMatch(String::isEmpty))
        );
    }

    @Test
    @DisplayName("GAME_FRIEND: initial call to startGameFriend() sets GameState to GAME_FRIEND and resets board")
    void callToStartGameFriendSetsGameStateGameFriendAndResetsBoard(){
        model.startGameComputer(); // sets computer symbol to square 0.

        model.startGameFriend();

        assertAll(
                () -> assertEquals(GameState.GAME_FRIEND, model.getCurrentGameState()),
                () -> assertTrue(model.getSquares().stream().allMatch(String::isEmpty))
        );
    }

    @Test
    @DisplayName("GAME_OVER: call to playGame(SquareIndex squareIndex) leaves game unchanged.")
    void callToPlayGameWhenGameStateGameOverLeavesGameUnchanged(){

        model.playGame(SquareIndex.ZERO);

        assertAll(
                () -> assertEquals(GameState.GAME_OVER, model.getCurrentGameState()),
                () -> assertTrue(model.getSquares().stream().allMatch(String::isEmpty))
        );
    }

    // TEST CODE FOR VALID MOVES

    @Test
    @DisplayName("GAME_COMPUTER: Call to playGame(SquareIndex.ONE) sets player1 symbol to square 1 and player2 symbol to square 0.")
    void callToPlayGameSetsPlayersSymbolsToCorrectSquares(){
        ObservableList<String> expectedSquares = model.getSquares();
        expectedSquares.set(0, "O");
        expectedSquares.set(1, "X");
        Model modelWithMyRandom = new Model(new MyRandom(0));
        modelWithMyRandom.setCurrentGameState(GameState.GAME_COMPUTER);

        modelWithMyRandom.playGame(SquareIndex.ONE);

        assertEquals(expectedSquares, modelWithMyRandom.getSquares());
    }

    @Test
    @DisplayName("GAME_COMPUTER: Call to playGame(SquareIndex occupiedSquare) leaves game unchanged.")
    void callToPlayGameWithOccupiedSquareLeavesGameUnchanged(){
        ObservableList<String> expectedSquares = model.getSquares();
        expectedSquares.set(0, "O");
        Model modelWithMyRandom = new Model(new MyRandom(0));

        modelWithMyRandom.startGameComputer();          // sets computer (player2) symbol "O" to square 0.
        modelWithMyRandom.playGame(SquareIndex.ZERO);

        assertEquals(expectedSquares, modelWithMyRandom.getSquares());
    }

    @Test
    @DisplayName("GAME_COMPUTER: Call to makeComputerMove() with full board leaves game unchanged.")
    void callToMakeComputerMoveWithFullBoardLeavesGameUnchanged(){
        ObservableList<String> fullBoard = model.getSquares();
        fullBoard.setAll("X");

        model.makeComputerMove();

        assertTrue(model.getSquares().stream().allMatch(symbol -> symbol.equals("X")));
    }

    // TEST CODE FOR WIN AND DRAW

    @Test
    @DisplayName("GAME_COMPUTER: Gameplay with winning row for computer sets GameState to GAME_OVER and GameMessage to computer win.")
    void gamePlayWithComputerRowWinSetsGameStateGameOverAndGameMessageToComputerWin(){
        MyRandom myRandom = new MyRandom(0);
        Model modelWithMyRandom = new Model(myRandom);

        // sets computer symbol to square 0.
        modelWithMyRandom.startGameComputer();
        // sets user symbol to square 3 and computer symbol to square 1.
        myRandom.setNextInt(1);
        modelWithMyRandom.playGame(SquareIndex.THREE);
        // sets user symbol to square 4 and computer symbol to square 2,
        // which is a winning row move for computer.
        myRandom.setNextInt(2);
        modelWithMyRandom.playGame(SquareIndex.FOUR);

        assertAll(
                () -> assertEquals(GameState.GAME_OVER, modelWithMyRandom.getCurrentGameState()),
                () -> assertEquals(modelWithMyRandom.getPlayer2().getName() + " wins!", modelWithMyRandom.getGameMessage())
        );
    }

    @Test
    @DisplayName("GAME_COMPUTER: Gameplay with winning column for computer sets GameState to GAME_OVER and GameMessage to computer win.")
    void gamePlayWithComputerColWinSetsGameStateGameOverAndGameMessageToComputerWin(){
        MyRandom myRandom = new MyRandom(0);
        Model modelWithMyRandom = new Model(myRandom);

        // sets computer symbol to square 0.
        modelWithMyRandom.startGameComputer();
        // sets user symbol to square 2 and computer symbol to square 3.
        myRandom.setNextInt(3);
        modelWithMyRandom.playGame(SquareIndex.TWO);
        // sets user symbol to square 5 and computer symbol to square 6,
        // which is a winning row move for computer.
        myRandom.setNextInt(6);
        modelWithMyRandom.playGame(SquareIndex.FIVE);

        assertAll(
                () -> assertEquals(GameState.GAME_OVER, modelWithMyRandom.getCurrentGameState()),
                () -> assertEquals(modelWithMyRandom.getPlayer2().getName() + " wins!", modelWithMyRandom.getGameMessage())
        );
    }

    @Test
    @DisplayName("GAME_COMPUTER: Gameplay with winning diagonal for computer sets GameState to GAME_OVER and GameMessage to computer win.")
    void gamePlayWithComputerDiagonalWinSetsGameStateGameOverAndGameMessageToComputerWin(){
        MyRandom myRandom = new MyRandom(0);
        Model modelWithMyRandom = new Model(myRandom);

        // sets computer symbol to square 0.
        modelWithMyRandom.startGameComputer();
        // sets user symbol to square 2 and computer symbol to square 4.
        myRandom.setNextInt(4);
        modelWithMyRandom.playGame(SquareIndex.TWO);
        // sets user symbol to square 5 and computer symbol to square 8,
        // which is a winning row move for computer.
        myRandom.setNextInt(8);
        modelWithMyRandom.playGame(SquareIndex.FIVE);

        assertAll(
                () -> assertEquals(GameState.GAME_OVER, modelWithMyRandom.getCurrentGameState()),
                () -> assertEquals(modelWithMyRandom.getPlayer2().getName() + " wins!", modelWithMyRandom.getGameMessage())
        );
    }

    @Test
    @DisplayName("GAME_COMPUTER: Gameplay with computer draw sets GameState to GAME_OVER and GameMessage to draw.")
    void gamePlayWithComputerDrawSetsGameStateGameOverAndGameMessageToDraw(){
        /*
                O   O   X
                X   X   O
                O   O   X
         */
        MyRandom myRandom = new MyRandom(0);
        Model modelWithMyRandom = new Model(myRandom);

        // sets computer symbol to square 0.
        modelWithMyRandom.startGameComputer();
        // sets user symbol to square 2 and computer symbol to square 1.
        myRandom.setNextInt(1);
        modelWithMyRandom.playGame(SquareIndex.TWO);
        // sets user symbol to square 3 and computer symbol to square 5.
        myRandom.setNextInt(5);
        modelWithMyRandom.playGame(SquareIndex.THREE);
        // sets user symbol to square 4 and computer symbol to square 6.
        myRandom.setNextInt(6);
        modelWithMyRandom.playGame(SquareIndex.FOUR);
        // sets user symbol to square 8 and computer symbol to square 7, which should equal full board and a draw.
        myRandom.setNextInt(7);
        modelWithMyRandom.playGame(SquareIndex.EIGHT);

        assertAll(
                () -> assertEquals(GameState.GAME_OVER, modelWithMyRandom.getCurrentGameState()),
                () -> assertEquals("DRAW!", modelWithMyRandom.getGameMessage())
        );
    }

    @Test
    @DisplayName("GAME_FRIEND: Gameplay with winning row sets GameState to GAME_OVER and GameMessage to currentPlayer win.")
    void gamePlayWithFriendRowWinSetsGameStateGameOverAndGameMessageToCurrentPlayerWin(){

        model.startGameFriend();
        model.playGame(SquareIndex.THREE);
        model.playGame(SquareIndex.SIX);
        model.playGame(SquareIndex.FOUR);
        model.playGame(SquareIndex.SEVEN);
        model.playGame(SquareIndex.FIVE);

        assertAll(
                () -> assertEquals(GameState.GAME_OVER, model.getCurrentGameState()),
                () -> assertEquals(model.getCurrentPlayer().getName() + " wins!", model.getGameMessage())
        );
    }

    @Test
    @DisplayName("GAME_FRIEND: Gameplay with winning column sets GameState to GAME_OVER and GameMessage to currentPlayer win.")
    void gamePlayWithFriendColWinSetsGameStateGameOverAndGameMessageToCurrentPlayerWin(){

        model.startGameFriend();
        model.playGame(SquareIndex.TWO);
        model.playGame(SquareIndex.ZERO);
        model.playGame(SquareIndex.FIVE);
        model.playGame(SquareIndex.ONE);
        model.playGame(SquareIndex.EIGHT);

        assertAll(
                () -> assertEquals(GameState.GAME_OVER, model.getCurrentGameState()),
                () -> assertEquals(model.getCurrentPlayer().getName() + " wins!", model.getGameMessage())
        );
    }

    @Test
    @DisplayName("GAME_FRIEND: Gameplay with winning diagonal sets GameState to GAME_OVER and GameMessage to currentPlayer win.")
    void gamePlayWithFriendDiagonalWinSetsGameStateGameOverAndGameMessageToCurrentPlayerWin(){

        model.startGameFriend();
        model.playGame(SquareIndex.TWO);
        model.playGame(SquareIndex.ZERO);
        model.playGame(SquareIndex.FOUR);
        model.playGame(SquareIndex.ONE);
        model.playGame(SquareIndex.SIX);

        assertAll(
                () -> assertEquals(GameState.GAME_OVER, model.getCurrentGameState()),
                () -> assertEquals(model.getCurrentPlayer().getName() + " wins!", model.getGameMessage())
        );
    }

    @Test
    @DisplayName("GAME_FRIEND: Gameplay with draw sets GameState to GAME_OVER and GameMessage to draw.")
    void gamePlayWithFriendDrawSetsGameStateGameOverAndGameMessageToDraw(){
        /*
                X   X   O
                O   O   X
                X   O   O
         */

        model.startGameFriend();
        model.playGame(SquareIndex.TWO);
        model.playGame(SquareIndex.ZERO);
        model.playGame(SquareIndex.THREE);
        model.playGame(SquareIndex.ONE);
        model.playGame(SquareIndex.FOUR);
        model.playGame(SquareIndex.FIVE);
        model.playGame(SquareIndex.SEVEN);
        model.playGame(SquareIndex.SIX);
        model.playGame(SquareIndex.EIGHT);

        assertAll(
                () -> assertEquals(GameState.GAME_OVER, model.getCurrentGameState()),
                () -> assertEquals("DRAW!", model.getGameMessage())
        );
    }

    @Test
    @DisplayName("GAME_FRIEND: Two games played with two wins in a row for player2 sets gamesPlayed to 2 and player2 score to 2.")
    void twoGamesWithPlayer2WinsSetsGamesPlayedToTwoAndPlayer2ScoreToTwo(){

        /*
                player 2 starts
                X   O   X
                -   O   -
                -   O   -
         */
        model.startGameFriend();
        model.playGame(SquareIndex.ONE);
        model.playGame(SquareIndex.ZERO);
        model.playGame(SquareIndex.FOUR);
        model.playGame(SquareIndex.TWO);
        model.playGame(SquareIndex.SEVEN);

        /*
                player 1 starts
                X   X   O
                -   -   O
                X   -   O
         */
        model.startGameFriend();
        model.playGame(SquareIndex.ZERO);
        model.playGame(SquareIndex.TWO);
        model.playGame(SquareIndex.ONE);
        model.playGame(SquareIndex.FIVE);
        model.playGame(SquareIndex.SIX);
        model.playGame(SquareIndex.EIGHT);

        assertAll(
                () -> assertEquals(2, model.getGamesPlayed()),
                () -> assertEquals(2, model.getPlayer2().getScore())
        );
    }
}

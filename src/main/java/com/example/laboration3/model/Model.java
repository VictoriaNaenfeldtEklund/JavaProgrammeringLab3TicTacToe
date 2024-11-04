package com.example.laboration3.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.stream.IntStream;

import static com.example.laboration3.model.GameState.*;

public class Model {

    private ObservableList<String> squares = FXCollections.observableArrayList();

    public Model(){
        IntStream.range(0, 9).forEach(_ -> squares.add(""));
    }

    public ObservableList<String> getSquares() {
        return squares;
    }

    public void setSquareSymbol(SquareIndex squareIndex){
        squares.set(squareIndex.ordinal(), "S");
    }
}

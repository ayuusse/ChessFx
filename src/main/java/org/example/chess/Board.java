package org.example.chess;

import java.util.ArrayList;

public class Board {
    String Name;Boolean isWhitePiece;Boolean isFirstMove;

    Board(String Name,Boolean isWhitePiece,Boolean isFirstMove){
        this.Name = Name;
        this.isWhitePiece = isWhitePiece;
        this.isFirstMove = isFirstMove;
    }

    Boolean EnPasant = false;
    ArrayList<String> Check_MovesAvailableUnderCheck = new ArrayList<>();
}

package org.example.chess;

public class Board {
    String Name;Boolean isWhitePiece;Boolean isFirstMove;Boolean CantMove;

    Board(String Name,Boolean isWhitePiece,Boolean isFirstMove,Boolean CantMove){
        this.Name = Name;
        this.isWhitePiece = isWhitePiece;
        this.isFirstMove = isFirstMove;
        this.CantMove = CantMove;
    }
    String getName(){
        return Name;
    }
    Boolean isWhitePiece(){
        return isWhitePiece;
    }
    Boolean CantMove(){
        return CantMove;
    }
    Boolean getIsFirstMove(){return isFirstMove;}
}

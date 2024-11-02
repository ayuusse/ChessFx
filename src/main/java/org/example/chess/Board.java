package org.example.chess;

public class Board {
    String Name;Boolean isWhitePiece;Boolean isFirstMove;Boolean CantMove;
    Boolean EnPasant;

    Board(String Name,Boolean isWhitePiece,Boolean isFirstMove){
        this.Name = Name;
        this.isWhitePiece = isWhitePiece;
        this.isFirstMove = isFirstMove;
        this.CantMove = false;
        this.EnPasant = false;
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
    Boolean Enpassant(){return EnPasant;}
}

package org.example.chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    Images Image = new Images();
    Board[][] Board = new Board[8][8];

    AnchorPane Pane = new AnchorPane();
    ImageView[][] Pices = new ImageView[8][8];

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(Pane, 800, 800);
        Initalize();
        stage.setTitle("Chess!");
        stage.setScene(scene);
        stage.show();
    }

    private void Initalize() {
        SetBoard();
        SyncBoard();
    }

    private void SyncBoard() {
        for(int i=0 ; i < Board.length ; i++){
            for (int j = 0; j < Board[0].length; j++) {
                if(Board[i][j] == null) continue;
                switch ((Board[i][j].Name).substring(0,3)){
                    case "Roo" -> Pices[i][j].setImage(Board[i][j].isWhitePiece?Image.wR:Image.bR);
                    case "Bis" ->  Pices[i][j].setImage(Board[i][j].isWhitePiece?Image.wB:Image.bB);
                    case "Que" ->  Pices[i][j].setImage(Board[i][j].isWhitePiece?Image.wQ:Image.bQ);
                    case "Paw" ->  Pices[i][j].setImage(Board[i][j].isWhitePiece?Image.wP:Image.bP);
                    case "Kin" ->  Pices[i][j].setImage(Board[i][j].isWhitePiece?Image.wKn:Image.bKn);
                    case "Kni" ->  Pices[i][j].setImage(Board[i][j].isWhitePiece?Image.wK:Image.bK);
                    default -> System.out.println("Error When Sync Board");
                }
            }
        }
    }

    private void SetBoard() {
        ImageView BoardImg = new ImageView(Image.Board);
        BoardImg.toBack();
        Pane.getChildren().addAll(BoardImg);

        for (int i = 0; i < Pices.length; i++) {
            for (int j = 0; j < Pices[0].length; j++) {
                Pices[i][j] = new ImageView();
                Pices[i][j].setX(j*100);
                Pices[i][j].setY(i*100);
                Pane.getChildren().add(Pices[i][j]);
            }
        }

        Board[0][0] = new Board("Rook",false,true,false);
        Board[0][1] = new Board("Knight",false,true,false);
        Board[0][2] = new Board("Bishop",false,true,false);
        Board[0][3] = new Board("Queen",false,true,false);
        Board[0][4] = new Board("King",false,true,false);
        Board[0][5] = new Board("Bishop",false,true,false);
        Board[0][6] = new Board("Knight",false,true,false);
        Board[0][7] = new Board("Rook",false,true,false);
        Board[1][0] = new Board("Pawn",false,true,false);
        Board[1][1] = new Board("Pawn",false,true,false);
        Board[1][2] = new Board("Pawn",false,true,false);
        Board[1][3] = new Board("Pawn",false,true,false);
        Board[1][4] = new Board("Pawn",false,true,false);
        Board[1][5] = new Board("Pawn",false,true,false);
        Board[1][6] = new Board("Pawn",false,true,false);
        Board[1][7] = new Board("Pawn",false,true,false);

        Board[7][0] = new Board("Rook",true,true,false);
        Board[7][1] = new Board("Knight",true,true,false);
        Board[7][2] = new Board("Bishop",true,true,false);
        Board[7][3] = new Board("Queen",true,true,false);
        Board[7][4] = new Board("King",true,true,false);
        Board[7][5] = new Board("Bishop",true,true,false);
        Board[7][6] = new Board("Knight",true,true,false);
        Board[7][7] = new Board("Rook",true,true,false);
        Board[6][0] = new Board("Pawn",true,true,false);
        Board[6][1] = new Board("Pawn",true,true,false);
        Board[6][2] = new Board("Pawn",true,true,false);
        Board[6][3] = new Board("Pawn",true,true,false);
        Board[6][4] = new Board("Pawn",true,true,false);
        Board[6][5] = new Board("Pawn",true,true,false);
        Board[6][6] = new Board("Pawn",true,true,false);
        Board[6][7] = new Board("Pawn",true,true,false);
    }


    public static void main(String[] args) {
        launch();
    }
}
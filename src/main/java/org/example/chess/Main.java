package org.example.chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

/*
        Hi, Thank you for reading this  (´▽`ʃ♡ƪ)
        Btw I am not very good at writing comments as you can see.
 */
public class Main extends Application {
    Images Image = new Images();
    static  Board[][] Board = new Board[8][8]; //The main array

    AnchorPane Pane = new AnchorPane();
    ImageView[][] Pices = new ImageView[8][8];//Image array to show the image of Pieces
    ImageView[][] MoveKill = new ImageView[8][8];//Image array that shows the images of places the pieces can move and whom they can kill

    static ArrayList<String> Movable = new ArrayList<>();//Stores all the valid moves
    static ArrayList<String> Killable = new ArrayList<>();//Stores all the valid Kill moves

    @Override
    public void start(Stage stage) {
         /*
            Start method
         */
        Scene scene = new Scene(Pane, 800, 800);
        Initalize();

        scene.setOnMouseClicked(event ->{
            int x =(int)event.getSceneY()/100; // Its inverted I know but it works
            int y =(int)event.getSceneX()/100;
            if(Board[x][y] == null){
                SetMoveKillnull();
                return;
            }
            setMovement(x,y);
        });

        stage.setTitle("Chess!");
        stage.setScene(scene);
        stage.show();
    }

    private void setMovement(int x, int y) {
        Movable = new ArrayList<>();
        Killable = new ArrayList<>();
        SetMoveKillnull();
        Move Move = new Move();
        switch ((Board[x][y].Name).substring(0,3)){
            case "Roo" ->  Move.Rook(x,y);
            case "Bis" ->  Move.Bishop(x,y);
            case "Que" ->  Move.Queen(x,y);
            case "Paw" ->  Move.Pawn(x,y);
            case "Kin" ->  Move.King(x,y);
            case "Kni" ->  Move.Knight(x,y);
            default -> System.out.println("Error When Getting Moves");
        }
        showMoves();
    }

    private void showMoves() {
        for(String s : Movable){
            MoveKill[Integer.parseInt(String.valueOf(s.charAt(0)))][Integer.parseInt(String.valueOf(s.charAt(2)))].setImage(Image.Move);
        }
        for(String s : Killable){
            MoveKill[Integer.parseInt(String.valueOf(s.charAt(0)))][Integer.parseInt(String.valueOf(s.charAt(2)))].setImage(Image.Kill);
        }
    }

    private void Initalize() {
         /*
            Just a method to keep things organaized
         */
        SetBoard();
        SetMoveKillnull();
        SyncBoard();
    }

    private void SetMoveKillnull() {
        /*
            Sets the images of MoveKill to null
         */
        for (int i = 0; i < MoveKill.length; i++) {
            for (int j = 0; j < MoveKill[0].length; j++) {
                MoveKill[i][j].setImage(null);
            }
        }
    }

    private void SyncBoard() {
        /*
            This method syncs the Board Array and the Pieces Array
         */
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
        /*
            Setting Up the Board
         */

        //Adding the Background image of the Chess Board
        ImageView BoardImg = new ImageView(Image.Board);
        BoardImg.toBack();
        Pane.getChildren().addAll(BoardImg);

        //Just Setting up the Pices array
        for (int i = 0; i < Pices.length; i++) {
            for (int j = 0; j < Pices[0].length; j++) {
                Pices[i][j] = new ImageView();
                Pices[i][j].setX(j*100);
                Pices[i][j].setY(i*100);
                Pane.getChildren().add(Pices[i][j]);
            }
        }

        //Doing the same thing in two different Loops. Why idk
        for (int i = 0; i < MoveKill.length; i++) {
            for (int j = 0; j < MoveKill[0].length; j++) {
                MoveKill[i][j] = new ImageView();
                MoveKill[i][j].setX(j*100);
                MoveKill[i][j].setY(i*100);
                Pane.getChildren().add(MoveKill[i][j]);
            }
        }

        //Placing the Pices at the correct place
        Board[4][4] = new Board("Rook",false,true,false);
        Board[0][1] = new Board("Knight",false,true,false);
        Board[0][2] = new Board("Bishop",false,true,false);
        Board[3][3] = new Board("Queen",false,true,false);
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
        Board[4][7] = new Board("Queen",true,true,false);
        Board[7][4] = new Board("King",true,true,false);
        Board[4][5] = new Board("Bishop",true,true,false);
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
        System.out.println("Board set to Default");
    }


    public static void main(String[] args) {
        launch();
    }
}
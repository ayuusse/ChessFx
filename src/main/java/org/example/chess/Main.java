package org.example.chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

/*
        Hi, Thank you for reading this  (´▽`ʃ♡ƪ)
 */
public class Main extends Application {
    Images Image = new Images();
    static  Board[][] Board = new Board[8][8];

    AnchorPane Pane = new AnchorPane();
    ImageView[][] Pices = new ImageView[8][8];
    ImageView[][] MoveKill = new ImageView[8][8];

    static ArrayList<String> Movable = new ArrayList<>();
    static ArrayList<String> Killable = new ArrayList<>();
    static ArrayList<String> Castling = new ArrayList<>();
    static ArrayList<String> Enpassant = new ArrayList<>();
    static ArrayList<String> InCheckMoves = new ArrayList<>();

    int Oldx,Oldy;
    boolean isWhiteToMove = true,isDragging = false;
    ImageView Dragimg;

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(Pane, 800, 800);
        Initalize();

        scene.setOnMouseDragged(event ->{
            if(isDragging) {
                Dragimg.setX(event.getX()-50);
                Dragimg.setY(event.getY()-50);
            }
            else {
                int x =(int)event.getSceneY()/100;
                int y =(int)event.getSceneX()/100;
                if(Board[x][y] == null){
                    SetMoveKillnull();
                    return;
                }
                if(isWhiteToMove != Board[x][y].isWhitePiece) return;
                setMovement(x,y);
                Oldx = x;
                Oldy = y;
                Dragimg.setImage(Pices[x][y].getImage());
                Pices[x][y].setImage(null);
                Dragimg.setX(event.getX()-50);
                Dragimg.setY(event.getY()-50);
                isDragging = true;
            }
        });

        scene.setOnMouseReleased(event ->{
            Dragimg.setImage(null);
            isDragging = false;
            SyncBoard();
        });

        scene.setOnMouseClicked(event ->{
            int x =(int)event.getSceneY()/100;
            int y =(int)event.getSceneX()/100;
            if(Movable.contains(x+" "+y)||Killable.contains(x+" "+y)||Castling.contains(x+" "+y)||Enpassant.contains(x+" "+y)){
                MovePiece(x,y);
                Oldx = -1;
                Oldy = -1;
                SetMoveKillnull();
                return;
            }
            if (x>7 || x<0 || y>7 || y<0) return;
            if(Board[x][y] == null){
                SetMoveKillnull();
                return;
            }
            if(isWhiteToMove != Board[x][y].isWhitePiece) return;
            setMovement(x,y);
            Oldx = x;
            Oldy = y;
        });

        stage.setTitle("Chess!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    int enpassant_x=-1,enpassant_y=-1;
    private void MovePiece(int x, int y) {
        if(enpassant_x>-1 && enpassant_y>-1 && Board[enpassant_x][enpassant_y] != null)
        {
            Board[enpassant_x][enpassant_y].EnPasant = false;
            enpassant_x = -1;enpassant_y = -1;
        }
        if(Castling.contains(x+" "+y)){
            switch (x+" "+y){
                case "7 2"->{
                    Board[7][3] =  new Board("Rook",true,false);
                    Board[7][2] =  new Board("King",true,false);
                    Board[7][0] =  null;
                    Board[7][4] =  null;
                }
                case "7 6"->{
                    Board[7][4] =  null;
                    Board[7][7] =  null;
                    Board[7][5] =  new Board("Rook",true,false);
                    Board[7][6] =  new Board("King",true,false);
                }
                case "0 2"->{
                    Board[0][3] =  new Board("Rook",false,false);
                    Board[0][2] =  new Board("King",false,false);
                    Board[0][0] =  null;
                    Board[0][4] =  null;
                }
                case "0 6"->{
                    Board[0][4] =  null;
                    Board[0][7] =  null;
                    Board[0][5] =  new Board("Rook",false,false);
                    Board[0][6] =  new Board("King",false,false);
                }
            }
        }
        else if (Enpassant.contains(x + " " + y)) {
            Board[x][y] = new Board(Board[Oldx][Oldy].Name,Board[Oldx][Oldy].isWhitePiece,false);
            if(Board[Oldx][Oldy].isWhitePiece)
                Board[x+1][y] = null;
            else
                Board[x-1][y] = null;

            Board[Oldx][Oldy] = null;
        }
        else if((Board[Oldx][Oldy].Name).equals("Pawn") && Math.abs(x-Oldx)==2){
            Board[x][y] = new Board(Board[Oldx][Oldy].Name,Board[Oldx][Oldy].isWhitePiece,false);
            Board[x][y].EnPasant = true;
            enpassant_x =x; enpassant_y=y;
            Board[Oldx][Oldy] = null;
        }
        else{
            Board[x][y] = new Board(Board[Oldx][Oldy].Name,Board[Oldx][Oldy].isWhitePiece,false);
            Board[Oldx][Oldy] = null;
        }
        SyncBoard();
        isWhiteToMove = !isWhiteToMove;
        removeCheckOnlyMoves();
        isInCheck(isWhiteToMove);
    }

    private void removeCheckOnlyMoves() {
        for(int i=0 ; i < Board.length ; i++){
            for (int j = 0; j < Board[0].length; j++) {
                if(Board[i][j] == null ) continue;
                Board[i][j].Check_MovesAvailableUnderCheck = new ArrayList<>();
            }
        }
    }


    private void isInCheck(boolean isWhite) {
        InCheckMoves = new ArrayList<>();
        CheckMoves CheckMoves = new CheckMoves();
        for(int i=0 ; i < Board.length ; i++){
            for (int j = 0; j < Board[0].length; j++) {
                if(Board[i][j] == null || Board[i][j].isWhitePiece == isWhite ) continue;
                switch ((Board[i][j].Name).substring(0,3)){
                    case "Roo" ->{CheckMoves.Rook(i,j);}
                    case "Bis" ->{CheckMoves.Bishop(i,j);}
                    case "Que" ->{CheckMoves.Queen(i,j);}
                    case "Paw" ->{CheckMoves.Pawn(i,j);}
                    case "Kni" ->{CheckMoves.Knight(i,j);}
                    case "Kin" ->{}
                    default -> System.out.println("Error Checks");
                }
            }
        }
        if(!InCheckMoves.isEmpty())
        {
//            isCheckmate(isWhite);
        }
    }
//    private void isCheckmate(boolean isWhite) {
//        for(int i=0 ; i < Board.length ; i++){
//            for (int j = 0; j < Board[0].length; j++) {
//                if(Board[i][j] == null || Board[i][j].isWhitePiece != isWhite ) continue;
//                switch ((Board[i][j].Name).substring(0,3)){
//                    case "Roo" ->{CheckMoves.Rook(i,j);}
//                    case "Bis" ->{CheckMoves.Bishop(i,j);}
//                    case "Que" ->{CheckMoves.Queen(i,j);}
//                    case "Paw" ->{CheckMoves.Pawn(i,j);}
//                    case "Kni" ->{CheckMoves.Knight(i,j);}
//                    case "Kin" ->{}
//                    default -> System.out.println("Error Checks");
//                }
//            }
//        }
//    }
    private void setMovement(int x, int y) {
        Movable = new ArrayList<>();
        Killable = new ArrayList<>();
        SetMoveKillnull();
        Move Move = new Move();
        KingMoves KingMoves = new KingMoves();
        switch ((Board[x][y].Name).substring(0,3)){
            case "Roo" ->  Move.Rook(x,y);
            case "Bis" ->  Move.Bishop(x,y);
            case "Que" ->  Move.Queen(x,y);
            case "Paw" ->  Move.Pawn(x,y);
            case "Kin" ->  KingMoves.Kingmove(x,y);
            case "Kni" ->  Move.Knight(x,y);
            default -> System.out.println("Error When Getting Moves");
        }
        if(!InCheckMoves.isEmpty() && !(Board[x][y].Name.startsWith("Kin")))
        {
            Movable.retainAll(InCheckMoves);
            Killable.retainAll(InCheckMoves);
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
        for(String s : Castling){
            MoveKill[Integer.parseInt(String.valueOf(s.charAt(0)))][Integer.parseInt(String.valueOf(s.charAt(2)))].setImage(Image.Move);
        }
        for(String s : Enpassant){
            MoveKill[Integer.parseInt(String.valueOf(s.charAt(0)))][Integer.parseInt(String.valueOf(s.charAt(2)))].setImage(Image.Kill);
        }
    }

    private void Initalize() {
        SetBoard();
        SetMoveKillnull();
        SyncBoard();
    }

    private void SetMoveKillnull() {
        Movable = new ArrayList<>();
        Killable = new ArrayList<>();
        Castling = new ArrayList<>();
        Enpassant = new ArrayList<>();
        for (int i = 0; i < MoveKill.length; i++) {
            for (int j = 0; j < MoveKill[0].length; j++) {
                MoveKill[i][j].setImage(null);
            }
        }
    }

    private void SyncBoard() {
        for (int i = 0; i < Pices.length; i++) {
            for (int j = 0; j < Pices[0].length; j++) {
                Pices[i][j].setImage(null);
            }
        }
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

        for (int i = 0; i < MoveKill.length; i++) {
            for (int j = 0; j < MoveKill[0].length; j++) {
                MoveKill[i][j] = new ImageView();
                MoveKill[i][j].setX(j*100);
                MoveKill[i][j].setY(i*100);
                Pane.getChildren().add(MoveKill[i][j]);
            }
        }

        Dragimg = new ImageView();
        Pane.getChildren().addAll(Dragimg);

        Board[0][0] = new Board("Rook",false,true);
        Board[0][1] = new Board("Knight",false,true);
        Board[0][2] = new Board("Bishop",false,true);
        Board[2][4] = new Board("Queen",false,true);
        Board[0][4] = new Board("King",false,true);
        Board[0][5] = new Board("Bishop",false,true);
        Board[0][6] = new Board("Knight",false,true);
        Board[0][7] = new Board("Rook",false,true);
        Board[1][0] = new Board("Pawn",false,true);
        Board[1][1] = new Board("Pawn",false,true);
        Board[1][2] = new Board("Pawn",false,true);
        Board[1][3] = new Board("Pawn",false,true);
        Board[1][4] = new Board("Pawn",false,true);
        Board[1][5] = new Board("Pawn",false,true);
        Board[1][6] = new Board("Pawn",false,true);
        Board[1][7] = new Board("Pawn",false,true);

        Board[7][0] = new Board("Rook",true,true);
        Board[7][1] = new Board("Knight",true,true);
        Board[7][2] = new Board("Bishop",true,true);
        Board[7][3] = new Board("Queen",true,true);
        Board[5][4] = new Board("King",true,true);
        Board[7][5] = new Board("Bishop",true,true);
        Board[7][6] = new Board("Knight",true,true);
        Board[4][4] = new Board("Rook",true,true);
        Board[6][0] = new Board("Pawn",true,true);
        Board[6][1] = new Board("Pawn",true,true);
        Board[6][2] = new Board("Pawn",true,true);
        Board[6][3] = new Board("Pawn",true,true);
        Board[6][4] = new Board("Pawn",true,true);
        Board[6][5] = new Board("Pawn",true,true);
        Board[6][6] = new Board("Pawn",true,true);
        Board[6][7] = new Board("Pawn",true,true);
        System.out.println("Board set to Default");
    }


    public static void main(String[] args) {
        launch();
    }
}

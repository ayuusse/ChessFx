package org.example.chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.util.ArrayList;

/*
        Hi, Thank you for reading this  (´▽`ʃ♡ƪ)
 */
public class Main extends Application {
    Images Image;
    Timer Timer;
    CheckMoves CheckMoves;
    King_Movement KingMoves;
    Piece_Movement Move;
    static Board[][] Board = new Board[8][8];

    AnchorPane Pane = new AnchorPane();

    ImageView[][] Pices_ImageView = new ImageView[8][8];
    ImageView[][] Movable_Killable_ImageView = new ImageView[8][8];

    static ArrayList<String> Valid_Moves = new ArrayList<>();
    static ArrayList<String> Valid_Killable_Moves = new ArrayList<>();
    static ArrayList<String> Valid_Castling_Moves = new ArrayList<>();
    static ArrayList<String> Valid_Enpassant_Moves = new ArrayList<>();
    static ArrayList<String> Valid_Moves_UnderCheck = new ArrayList<>();

    boolean isWhiteToMove = true,isDragging = false;

    int enpassant_x=-1,enpassant_y=-1,Oldx,Oldy;

    ImageView Drag_ImageView;

    MediaPlayer KillSound,MoveSound;

    @Override
    public void start(Stage stage) {
        Pane.setStyle("-fx-background-color: #171716;");

        Scene scene = new Scene(Pane, 1200, 800);
        Initalize();

        scene.setOnMouseDragged(event ->{
            if(event.getSceneX()>800) return;
            if(isDragging) {
                Drag_ImageView.setX(event.getX()-50);
                Drag_ImageView.setY(event.getY()-50);
            }
            else {
                int x =(int)event.getSceneY()/100;
                int y =(int)event.getSceneX()/100;
                if(Board[x][y] == null){
                    Set_MoveKill_null();
                    return;
                }
                if(isWhiteToMove != Board[x][y].isWhitePiece) return;
                get_Moves(x,y);
                Oldx = x;
                Oldy = y;
                Drag_ImageView.setImage(Pices_ImageView[x][y].getImage());
                Pices_ImageView[x][y].setImage(null);
                Drag_ImageView.setX(event.getX()-50);
                Drag_ImageView.setY(event.getY()-50);
                isDragging = true;
            }
        });

        scene.setOnMouseReleased(event ->{
            Drag_ImageView.setImage(null);
            isDragging = false;
            SyncImagesOnBoard();
        });

        scene.setOnMouseClicked(event ->{
            if(event.getSceneX()>800) return;
            int x =(int)event.getSceneY()/100;
            int y =(int)event.getSceneX()/100;
            if(Valid_Moves.contains(x+" "+y)|| Valid_Killable_Moves.contains(x+" "+y)|| Valid_Castling_Moves.contains(x+" "+y)|| Valid_Enpassant_Moves.contains(x+" "+y)){
                MovePiece(x,y);
                Oldx = -1;
                Oldy = -1;
                Set_MoveKill_null();
                return;
            }
            if (x>7 || x<0 || y>7 || y<0) return;
            if(Board[x][y] == null){
                Set_MoveKill_null();
                return;
            }
            if(isWhiteToMove != Board[x][y].isWhitePiece) return;
            get_Moves(x,y);
            Oldx = x;
            Oldy = y;
        });

        Timer.addTimer(Pane);
        Timer.StartTimer1();

        stage.setResizable(false);
        stage.setTitle("Chess!");
        stage.setScene(scene);
        stage.getIcons().add(Image.bK);
        stage.show();
    }
    private void MovePiece(int x, int y) {
        if(enpassant_x>-1 && enpassant_y>-1 && Board[enpassant_x][enpassant_y] != null)
        {
            Board[enpassant_x][enpassant_y].EnPasant = false;
            enpassant_x = -1;enpassant_y = -1;
        }
        if(Valid_Castling_Moves.contains(x+" "+y)){
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
            MoveSound.stop();
            MoveSound.play();
        }
        else if (Valid_Enpassant_Moves.contains(x + " " + y)) {
            Board[x][y] = new Board(Board[Oldx][Oldy].Name,Board[Oldx][Oldy].isWhitePiece,false);
            if(Board[Oldx][Oldy].isWhitePiece)
                Board[x+1][y] = null;
            else
                Board[x-1][y] = null;

            KillSound.stop();
            KillSound.play();
            Board[Oldx][Oldy] = null;
        }
        else if((Board[Oldx][Oldy].Name).equals("Pawn") && Math.abs(x-Oldx)==2){
            Board[x][y] = new Board(Board[Oldx][Oldy].Name,Board[Oldx][Oldy].isWhitePiece,false);
            Board[x][y].EnPasant = true;
            enpassant_x =x; enpassant_y=y;
            Board[Oldx][Oldy] = null;
            MoveSound.stop();
            MoveSound.play();
        }
        else {
            if (Valid_Moves.contains(x + " " + y)) {
                MoveSound.stop();
                MoveSound.play();
            } else {
                KillSound.stop();
                KillSound.play();
            }
            Board[x][y] = new Board(Board[Oldx][Oldy].Name,Board[Oldx][Oldy].isWhitePiece,false);
            Board[Oldx][Oldy] = null;
        }
        SyncImagesOnBoard();
        isWhiteToMove = !isWhiteToMove;

        if(isWhiteToMove) {
            Timer.StartTimer1();
            Timer.pauseTimer2();
        }else {
            Timer.StartTimer2();
            Timer.pauseTimer1();
        }
        Remove_ValidMovesUnderCheck();
        is_In_Check(isWhiteToMove);
    }
    private void Remove_ValidMovesUnderCheck() {
        for(int i=0 ; i < Board.length ; i++){
            for (int j = 0; j < Board[0].length; j++) {
                if(Board[i][j] == null ) continue;
                Board[i][j].Check_MovesAvailableUnderCheck = new ArrayList<>();
            }
        }
    }
    public void GameOver() {
        System.out.println("------------------------------------------------------ GAME OVER ------------------------------------------------------");
    }
    private void is_In_Check(boolean isWhite) {
        Valid_Moves = new ArrayList<>();
        Valid_Killable_Moves = new ArrayList<>();
        Valid_Moves_UnderCheck = new ArrayList<>();
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
        if(!Valid_Moves_UnderCheck.isEmpty())
        {
            is_Checkmate(isWhite);
        }
    }
    private void is_Checkmate(boolean isWhite) {
        for(int i=0 ; i < Board.length ; i++){
            for (int j = 0; j < Board[0].length; j++) {
                if(Board[i][j] == null || Board[i][j].isWhitePiece != isWhite ) continue;
                switch ((Board[i][j].Name).substring(0,3)){
                    case "Roo" -> Move.Rook(i,j);
                    case "Bis" ->Move.Bishop(i,j);
                    case "Que" ->Move.Queen(i,j);
                    case "Paw" ->Move.Pawn(i,j);
                    case "Kin" ->KingMoves.Kingmove(i,j);
                    case "Kni" ->Move.Knight(i,j);
                    default -> System.out.println("Error Checks");
                }
            }
        }
        Valid_Moves.retainAll(Valid_Moves_UnderCheck);
        Valid_Killable_Moves.retainAll(Valid_Moves_UnderCheck);
        if(Valid_Moves.isEmpty() && Valid_Killable_Moves.isEmpty())
        {
            GameOver();
        }
    }
    private void get_Moves(int x, int y) {
        Valid_Moves = new ArrayList<>();
        Valid_Killable_Moves = new ArrayList<>();
        Set_MoveKill_null();
        switch ((Board[x][y].Name).substring(0,3)){
            case "Roo" ->  Move.Rook(x,y);
            case "Bis" ->  Move.Bishop(x,y);
            case "Que" ->  Move.Queen(x,y);
            case "Paw" ->  Move.Pawn(x,y);
            case "Kin" ->  KingMoves.Kingmove(x,y);
            case "Kni" ->  Move.Knight(x,y);
            default -> System.out.println("Error When Getting Moves");
        }
        if(!Valid_Moves_UnderCheck.isEmpty() && !(Board[x][y].Name.startsWith("Kin")))
        {
            Valid_Moves.retainAll(Valid_Moves_UnderCheck);
            Valid_Killable_Moves.retainAll(Valid_Moves_UnderCheck);
        }
        show_Moves();
    }
    private void show_Moves() {
        for(String s : Valid_Moves){
            Movable_Killable_ImageView[Integer.parseInt(s.substring(0,1))][Integer.parseInt(s.substring(2,3))].setImage(Image.Move);
        }
        for(String s : Valid_Killable_Moves){
            Movable_Killable_ImageView[Integer.parseInt(s.substring(0,1))][Integer.parseInt(s.substring(2,3))].setImage(Image.Kill);
        }
        for(String s : Valid_Castling_Moves){
            Movable_Killable_ImageView[Integer.parseInt(s.substring(0,1))][Integer.parseInt(s.substring(2,3))].setImage(Image.Move);
        }
        for(String s : Valid_Enpassant_Moves){
            Movable_Killable_ImageView[Integer.parseInt(s.substring(0,1))][Integer.parseInt(s.substring(2,3))].setImage(Image.Kill);
        }
    }
    private void Initalize() {
        Timer = new Timer();
        Image = new Images();
        CheckMoves = new CheckMoves();
        KingMoves = new King_Movement();
        Move = new Piece_Movement();
        KillSound = new MediaPlayer(Image.KillSound);
        MoveSound = new MediaPlayer(Image.MoveSound);

        SetBoardToDefault();
        Set_MoveKill_null();
        SyncImagesOnBoard();
    }
    private void Set_MoveKill_null() {
        Valid_Moves = new ArrayList<>();
        Valid_Killable_Moves = new ArrayList<>();
        Valid_Castling_Moves = new ArrayList<>();
        Valid_Enpassant_Moves = new ArrayList<>();
        for (int i = 0; i < Movable_Killable_ImageView.length; i++) {
            for (int j = 0; j < Movable_Killable_ImageView[0].length; j++) {
                Movable_Killable_ImageView[i][j].setImage(null);
            }
        }
    }
    private void SyncImagesOnBoard() {
        for (int i = 0; i < Pices_ImageView.length; i++) {
            for (int j = 0; j < Pices_ImageView[0].length; j++) {
                Pices_ImageView[i][j].setImage(null);
            }
        }
        for(int i=0 ; i < Board.length ; i++){
            for (int j = 0; j < Board[0].length; j++) {
                if(Board[i][j] == null) continue;
                switch ((Board[i][j].Name).substring(0,3)){
                    case "Roo" -> Pices_ImageView[i][j].setImage(Board[i][j].isWhitePiece?Image.wR:Image.bR);
                    case "Bis" ->  Pices_ImageView[i][j].setImage(Board[i][j].isWhitePiece?Image.wB:Image.bB);
                    case "Que" ->  Pices_ImageView[i][j].setImage(Board[i][j].isWhitePiece?Image.wQ:Image.bQ);
                    case "Paw" ->  Pices_ImageView[i][j].setImage(Board[i][j].isWhitePiece?Image.wP:Image.bP);
                    case "Kin" ->  Pices_ImageView[i][j].setImage(Board[i][j].isWhitePiece?Image.wKn:Image.bKn);
                    case "Kni" ->  Pices_ImageView[i][j].setImage(Board[i][j].isWhitePiece?Image.wK:Image.bK);
                    default -> System.out.println("Error When Sync Images to Board");
                }
            }
        }
    }
    private void SetBoardToDefault() {
        ImageView BoardImg = new ImageView(Image.Board);
        Pane.getChildren().add(BoardImg);

        for (int i = 0; i < Pices_ImageView.length; i++) {
            for (int j = 0; j < Pices_ImageView[0].length; j++) {
                Pices_ImageView[i][j] = new ImageView();
                Pices_ImageView[i][j].setX(j*100);
                Pices_ImageView[i][j].setY(i*100);
                Pane.getChildren().add(Pices_ImageView[i][j]);
            }
        }

        for (int i = 0; i < Movable_Killable_ImageView.length; i++) {
            for (int j = 0; j < Movable_Killable_ImageView[0].length; j++) {
                Movable_Killable_ImageView[i][j] = new ImageView();
                Movable_Killable_ImageView[i][j].setX(j*100);
                Movable_Killable_ImageView[i][j].setY(i*100);
                Pane.getChildren().add(Movable_Killable_ImageView[i][j]);
            }
        }

        Drag_ImageView = new ImageView();
        Pane.getChildren().add(Drag_ImageView);

        Board[0][0] = new Board("Rook",false,true);
        Board[0][1] = new Board("Knight",false,true);
        Board[0][2] = new Board("Bishop",false,true);
        Board[0][3] = new Board("Queen",false,true);
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
        Board[7][4] = new Board("King",true,true);
        Board[7][5] = new Board("Bishop",true,true);
        Board[7][6] = new Board("Knight",true,true);
        Board[7][7] = new Board("Rook",true,true);
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

package org.example.chess;

import java.util.ArrayList;

public class Move extends Main{
    boolean isInsideBoard(int x,int y){
            return x<8 && x>=0 && y<8 && y>=0;
    }
    void Rook(int x, int y)
    {
        int[] xarr = new int[]{-1,1,0,0};
        int[] yarr = new int[]{0,0,-1,1};
        for (int i = 0; i < xarr.length; i++) {
            int tx=x,ty=y;
            while (isInsideBoard(tx+xarr[i],ty+yarr[i]))
            {
                tx+=xarr[i];
                ty+=yarr[i];
                if(Board[tx][ty] == null){
                    Movable.add(tx+" "+ty);
                } else if (Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece) {
                    Killable.add(tx+" "+ty);
                    break;
                }else{
                    break;
                }
            }
        }
    }
    void Bishop(int x, int y)
    {
        int[] xarr = new int[]{-1,1,1,-1};
        int[] yarr = new int[]{-1,1,-1,1};
        for (int i = 0; i < xarr.length; i++) {
            int tx=x,ty=y;
            while (isInsideBoard(tx+xarr[i],ty+yarr[i]))
            {
                tx+=xarr[i];
                ty+=yarr[i];
                if(Board[tx][ty] == null){
                    Movable.add(tx+" "+ty);
                } else if (Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece) {
                    Killable.add(tx+" "+ty);
                    break;
                }else{
                    break;
                }
            }
        }
    }
    void Queen(int x, int y)
    {
        int[] xarr = new int[]{-1,1,0,0,-1,1,1,-1};
        int[] yarr = new int[]{0,0,-1,1,-1,1,-1,1};
        for (int i = 0; i < xarr.length; i++) {
            int tx=x,ty=y;
            while (isInsideBoard(tx+xarr[i],ty+yarr[i]))
            {
                tx+=xarr[i];
                ty+=yarr[i];
                if(Board[tx][ty] == null){
                    Movable.add(tx+" "+ty);
                } else if (Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece) {
                    Killable.add(tx+" "+ty);
                    break;
                }else{
                    break;
                }
            }
        }
    }
    void Knight(int x, int y)
    {

    }
    void King(int x, int y)
    {

    }
    void Pawn(int x, int y)
    {

    }
}

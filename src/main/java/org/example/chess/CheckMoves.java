package org.example.chess;

import java.util.ArrayList;

public class CheckMoves extends Main {
    boolean isInsideBoard(int x,int y){
        return x<8 && x>=0 && y<8 && y>=0;
    }
    void Rook(int x, int y)
    {
        ArrayList<String> temp = new ArrayList<>();
        String pieceinway = null;
        int[] xarr = new int[]{-1,1,0,0};
        int[] yarr = new int[]{0,0,-1,1};
        for (int i = 0; i < xarr.length; i++) {
            int tx=x,ty=y;
            while (isInsideBoard(tx+xarr[i],ty+yarr[i]))
            {
                tx+=xarr[i];
                ty+=yarr[i];

                if(Board[tx][ty] == null){
                    temp.add(tx+" "+ty);
                }
                else if (Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece && Board[tx][ty].Name.startsWith("Kin")) {
                    temp.add(x+" "+y);
                    if(pieceinway != null)
                    {
                        Board[Integer.parseInt(pieceinway.substring(0, 1))][Integer.parseInt(pieceinway.substring(2, 3))].Check_MovesAvailableUnderCheck = temp;
                    }
                    else
                    {
                        InCheckMoves.addAll(temp);
                    }
                    return;
                }
                else if (Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece)
                {
                    if(pieceinway != null)
                    {
                        return;
                    }
                    pieceinway = tx+" "+ty;
                }
                else{
                    break;
                }
            }
            pieceinway = null;
            temp.clear();
        }
    }
    void Bishop(int x, int y)
    {
        ArrayList<String> temp = new ArrayList<>();
        String pieceinway = null;
        int[] xarr = new int[]{-1,1,1,-1};
        int[] yarr = new int[]{-1,1,-1,1};
        for (int i = 0; i < xarr.length; i++) {
            int tx=x,ty=y;
            while (isInsideBoard(tx+xarr[i],ty+yarr[i]))
            {
                tx+=xarr[i];
                ty+=yarr[i];
                if(Board[tx][ty] == null){
                    temp.add(tx+" "+ty);
                } else if (Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece && Board[tx][ty].Name.startsWith("Kin")) {
                    temp.add(x+" "+y);
                    if(pieceinway != null)
                    {
                        Board[Integer.parseInt(pieceinway.substring(0, 1))][Integer.parseInt(pieceinway.substring(2, 3))].Check_MovesAvailableUnderCheck = temp;
                    }
                    else
                    {
                        InCheckMoves.addAll(temp);
                    }
                    return;
                }
                else if (Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece)
                {
                    if(pieceinway != null)
                    {
                        return;
                    }
                    pieceinway = tx+" "+ty;
                }
                else{
                    break;
                }
            }
            pieceinway = null;
            temp.clear();
        }
    }
    void Queen(int x, int y)
    {
        ArrayList<String> temp = new ArrayList<>();
        String pieceinway = null;
        int[] xarr = new int[]{-1,1,0,0,-1,1,1,-1};
        int[] yarr = new int[]{0,0,-1,1,-1,1,-1,1};
        for (int i = 0; i < xarr.length; i++) {
            int tx=x,ty=y;
            while (isInsideBoard(tx+xarr[i],ty+yarr[i]))
            {
                tx+=xarr[i];
                ty+=yarr[i];
                if(Board[tx][ty] == null){
                    temp.add(tx+" "+ty);
                } else if (Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece && Board[tx][ty].Name.startsWith("Kin")) {
                    System.out.println("INCHECK");
                    temp.add(x+" "+y);
                    if(pieceinway != null)
                    {
                        Board[Integer.parseInt(pieceinway.substring(0, 1))][Integer.parseInt(pieceinway.substring(2, 3))].Check_MovesAvailableUnderCheck = temp;
                    }
                    else
                    {
                        InCheckMoves.addAll(temp);
                    }
                    return;
                }
                else if (Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece)
                {
                    if(pieceinway != null)
                    {
                        return;
                    }
                    pieceinway = tx+" "+ty;
                }
                else{
                    break;
                }
            }
            pieceinway = null;
            temp.clear();
        }
    }
    void Knight(int x, int y)
    {
        int[] xarr = new int[]{-1,1,-1,1,2,2,-2,-2};
        int[] yarr = new int[]{-2,-2,2,2,-1,1,-1,1};

        for (int i = 0; i < xarr.length; i++) {
            int tx = x+xarr[i];
            int ty = y+yarr[i];
            if(!(isInsideBoard(tx,ty))) continue;
            if (Board[tx][ty] != null && Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece && Board[tx][ty].Name.startsWith("Kin")) {
                InCheckMoves.add(x+" "+y);
                return;
            }
        }
    }
    void Pawn(int x, int y)
    {
        int[] xarr;int[] yarr;
        if(Board[x][y].isWhitePiece){
            xarr = new int[]{-1,-2,-1};
        }else{
            xarr = new int[]{1,2,1};
        }
        yarr = new int[]{0,0,-1,1};

        if(isInsideBoard(x+xarr[2],y+yarr[2]) && Board[x+xarr[2]][y+yarr[2]] != null && (Board[x+xarr[2]][y+yarr[2]].isWhitePiece != Board[x][y].isWhitePiece)&& Board[x+xarr[2]][y+yarr[2]].Name.startsWith("Kin")){
            InCheckMoves.add(x+" "+y);
            return;
        }
        if(isInsideBoard(x+xarr[2],y+yarr[3]) && Board[x+xarr[2]][y+yarr[3]] != null && (Board[x+xarr[2]][y+yarr[3]].isWhitePiece != Board[x][y].isWhitePiece)&& Board[x+xarr[2]][y+yarr[3]].Name.startsWith("Kin")) {
            InCheckMoves.add(x+" "+y);
            return;
        }
    }
}

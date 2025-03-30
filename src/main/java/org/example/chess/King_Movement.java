package org.example.chess;

import java.util.HashSet;
public class King_Movement extends Main {
    HashSet<String> KingAttackable = new HashSet<>();

    boolean isInsideBoard(int x,int y){
        return x<8 && x>=0 && y<8 && y>=0;
    }
    void Kingmove(int x, int y)
    {
        int[] xarr = new int[]{-1,-1,-1,0,0,1,1,1};
        int[] yarr = new int[]{-1,0,1,-1,1,-1,0,1};
        GetNonMovable(x,y);
        for (int i = 0; i < xarr.length; i++) {
            int tx = x+xarr[i];
            int ty = y+yarr[i];
            if(!(isInsideBoard(tx,ty))) continue;
            if(Board[tx][ty] == null && !(KingAttackable.contains(tx+" "+ty))){
                Valid_Moves.add(tx+" "+ty);
            } else if (!(Board[tx][ty] == null) && (Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece) && !(KingAttackable.contains(tx+" "+ty))) {
                Valid_Killable_Moves.add(tx + " " + ty);
            }
        }

        if(Board[x][y].isFirstMove && Valid_Moves_UnderCheck.isEmpty()){
            int X;
            if(Board[x][y].isWhitePiece){
                X = 7;
             }else {
                X = 0;
            }
            int[] Left = new int[]{3,2,1};
            int[] Right = new int[]{6,5};

            if(Board[X][0] != null && Board[X][0].isFirstMove){
                boolean canCastle = true;
                for(int Y : Left){
                    if (!(Board[X][Y] == null && !(KingAttackable.contains(X + " " + Y)))) {
                        canCastle = false;
                        break;
                    }
                }
                if(canCastle){
                    Valid_Castling_Moves.add(X+" "+2);
                }
            }
            if(Board[X][7] != null && Board[X][7].isFirstMove){
                boolean canCastle = true;
                for(int Y : Right){
                    if (!(Board[X][Y] == null && !(KingAttackable.contains(X + " " + Y)))) {
                        canCastle = false;
                        break;
                    }
                }
                if(canCastle){
                    Valid_Castling_Moves.add(X+" "+6);
                }
            }
        }
    }

    private void GetNonMovable(int x, int y) {
        KingAttackable = new HashSet<>();
        for (int i = 0; i < Board.length; i++) {
            for (int j = 0; j < Board[0].length; j++) {
                if(Board[i][j] == null || Board[i][j].isWhitePiece == Board[x][y].isWhitePiece) continue;
                switch ((Board[i][j].Name).substring(0,3)){
                    case "Roo" ->  Rook(i,j);
                    case "Bis" ->  Bishop(i,j);
                    case "Que" ->  Queen(i,j);
                    case "Paw" ->  Pawn(i,j);
                    case "Kin" ->  King(i,j);
                    case "Kni" ->  Knight(i,j);
                    default -> System.out.println("Error When Getting Non movable places for king");
                }
            }
        }
    }

    private void Pawn(int x, int y) {
        int xarr;
        int[] yarr;
        xarr = Board[x][y].isWhitePiece?-1:1;
        yarr = new int[]{-1,1};

        if(isInsideBoard(x+xarr,y+yarr[0])){
            KingAttackable.add((x+xarr)+" "+(y+yarr[0]));
        }
        if(isInsideBoard(x+xarr,y+yarr[1])) {
            KingAttackable.add((x+xarr)+" "+(y+yarr[1]));
        }
    }

    private void King(int x, int y) {
        int[] xarr = new int[]{-1,-1,-1,0,0,1,1,1};
        int[] yarr = new int[]{-1,0,1,-1,1,-1,0,1};
        for (int i = 0; i < xarr.length; i++) {
            int tx = x+xarr[i];
            int ty = y+yarr[i];
            if(!(isInsideBoard(tx,ty))) continue;
            if(Board[tx][ty] == null){
                KingAttackable.add(tx+" "+ty);
            } else if (Board[tx][ty].isWhitePiece == Board[x][y].isWhitePiece) {
                KingAttackable.add(tx + " " + ty);
            }
        }
    }

    private void Knight(int x, int y) {
        int[] xarr = new int[]{-1,1,-1,1,2,2,-2,-2};
        int[] yarr = new int[]{-2,-2,2,2,-1,1,-1,1};
        for (int i = 0; i < xarr.length; i++) {
            int tx = x+xarr[i];
            int ty = y+yarr[i];
            if(!(isInsideBoard(tx,ty))) continue;
            if(Board[tx][ty] == null){
                KingAttackable.add(tx+" "+ty);
            }
            else if (Board[tx][ty].isWhitePiece == Board[x][y].isWhitePiece) {
                KingAttackable.add(tx + " " + ty);
            }
        }
    }

    private void Queen(int x, int y) {
        int[] xarr = new int[]{-1,1,0,0,-1,1,1,-1};
        int[] yarr = new int[]{0,0,-1,1,-1,1,-1,1};
        for (int i = 0; i < xarr.length; i++) {
            int tx=x,ty=y;
            while (isInsideBoard(tx+xarr[i],ty+yarr[i]))
            {
                tx+=xarr[i];
                ty+=yarr[i];
                if(Board[tx][ty] == null){
                    KingAttackable.add(tx+" "+ty);
                }
                else if (Board[tx][ty].isWhitePiece == Board[x][y].isWhitePiece) {
                    KingAttackable.add(tx+" "+ty);
                    break;
                }
                else if (!(Board[tx][ty].Name.equals("King"))) {
                    break;
                }
            }
        }
    }

    private void Bishop(int x, int y) {
        int[] xarr = new int[]{-1,1,1,-1};
        int[] yarr = new int[]{-1,1,-1,1};
        for (int i = 0; i < xarr.length; i++) {
            int tx=x,ty=y;
            while (isInsideBoard(tx+xarr[i],ty+yarr[i]))
            {
                tx+=xarr[i];
                ty+=yarr[i];
                if(Board[tx][ty] == null){
                    KingAttackable.add(tx+" "+ty);
                }
                else if (Board[tx][ty].isWhitePiece == Board[x][y].isWhitePiece) {
                    KingAttackable.add(tx+" "+ty);
                    break;
                }
                else if (!(Board[tx][ty].Name.equals("King"))) {
                    break;
                }
            }
        }
    }

    private void Rook(int x, int y) {
        int[] xarr = new int[]{-1,1,0,0};
        int[] yarr = new int[]{0,0,-1,1};
        for (int i = 0; i < xarr.length; i++) {
            int tx=x,ty=y;
            while (isInsideBoard(tx+xarr[i],ty+yarr[i]))
            {
                tx+=xarr[i];
                ty+=yarr[i];
                if(Board[tx][ty] == null){
                    KingAttackable.add(tx+" "+ty);
                }
                else if (Board[tx][ty].isWhitePiece == Board[x][y].isWhitePiece) {
                    KingAttackable.add(tx+" "+ty);
                    break;
                }
                else if (!(Board[tx][ty].Name.equals("King"))) {
                    break;
                }
            }
        }
    }
}

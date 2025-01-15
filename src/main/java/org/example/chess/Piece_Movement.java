package org.example.chess;


public class Piece_Movement extends Main{
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
                    Valid_Moves.add(tx+" "+ty);
                }
                else if (Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece) {
                    Valid_Killable_Moves.add(tx+" "+ty);
                    break;
                }
                else{
                    break;
                }
            }
        }
        if(!Board[x][y].Check_MovesAvailableUnderCheck.isEmpty())
        {
            Valid_Moves.retainAll(Board[x][y].Check_MovesAvailableUnderCheck);
            Valid_Killable_Moves.retainAll(Board[x][y].Check_MovesAvailableUnderCheck);
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
                    Valid_Moves.add(tx+" "+ty);
                } else if (Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece) {
                    Valid_Killable_Moves.add(tx+" "+ty);
                    break;
                }else{
                    break;
                }
            }
        }
        if(!Board[x][y].Check_MovesAvailableUnderCheck.isEmpty())
        {
            Valid_Moves.retainAll(Board[x][y].Check_MovesAvailableUnderCheck);
            Valid_Killable_Moves.retainAll(Board[x][y].Check_MovesAvailableUnderCheck);
        }
    }
    boolean Queen(int x, int y)
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
                    Valid_Moves.add(tx+" "+ty);
                } else if (Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece) {
                    Valid_Killable_Moves.add(tx+" "+ty);
                    break;
                }else{
                    break;
                }
            }
        }
        if(!Board[x][y].Check_MovesAvailableUnderCheck.isEmpty())
        {
            Valid_Moves.retainAll(Board[x][y].Check_MovesAvailableUnderCheck);
            Valid_Killable_Moves.retainAll(Board[x][y].Check_MovesAvailableUnderCheck);
        }
        return !Valid_Moves.isEmpty() || !Valid_Killable_Moves.isEmpty();
    }

    void Knight(int x, int y)
    {
        int[] xarr = new int[]{-1,1,-1,1,2,2,-2,-2};
        int[] yarr = new int[]{-2,-2,2,2,-1,1,-1,1};

        for (int i = 0; i < xarr.length; i++) {
            int tx = x+xarr[i];
            int ty = y+yarr[i];
            if(!(isInsideBoard(tx,ty))) continue;
            if(Board[tx][ty] == null){
                Valid_Moves.add(tx+" "+ty);
            }
            else if (Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece) {
                Valid_Killable_Moves.add(tx + " " + ty);
            }
        }
        if(!Board[x][y].Check_MovesAvailableUnderCheck.isEmpty())
        {
            Valid_Moves.retainAll(Board[x][y].Check_MovesAvailableUnderCheck);
            Valid_Killable_Moves.retainAll(Board[x][y].Check_MovesAvailableUnderCheck);
        }
    }
    void Pawn(int x, int y)
    {
        int[] xarr;int[] yarr;int enpassant;
        if(Board[x][y].isWhitePiece){
            xarr = new int[]{-1,-2,-1};
            enpassant = 3;
        }else{
            xarr = new int[]{1,2,1};
            enpassant = 4;
        }
        yarr = new int[]{0,0,-1,1};

        if(isInsideBoard(x+xarr[0],y+yarr[0]) && Board[x+xarr[0]][y+yarr[0]] == null){
            Valid_Moves.add((x+xarr[0])+" "+(y+yarr[0]));
            if(isInsideBoard(x+xarr[1],y+yarr[1]) && Board[x+xarr[1]][y+yarr[1]] == null && Board[x][y].isFirstMove){
                Valid_Moves.add((x+xarr[1])+" "+(y+yarr[1]));
            }
        }

        if(isInsideBoard(x+xarr[2],y+yarr[2]) && Board[x+xarr[2]][y+yarr[2]] != null && (Board[x+xarr[2]][y+yarr[2]].isWhitePiece != Board[x][y].isWhitePiece)){
            Valid_Killable_Moves.add((x+xarr[2])+" "+(y+yarr[2]));
        }
        if(isInsideBoard(x+xarr[2],y+yarr[3]) && Board[x+xarr[2]][y+yarr[3]] != null && (Board[x+xarr[2]][y+yarr[3]].isWhitePiece != Board[x][y].isWhitePiece)) {
            Valid_Killable_Moves.add((x+xarr[2])+" "+(y+yarr[3]));
        }

        if(x==enpassant){
            if(isInsideBoard(enpassant,y-1) && Board[enpassant][y-1]!=null && Board[enpassant][y-1].EnPasant)
            {
                Valid_Enpassant_Moves.add((enpassant+xarr[0])+" "+(y-1));
            }
            if(isInsideBoard(enpassant,y+1) && Board[enpassant][y+1]!=null && Board[enpassant][y+1].EnPasant)
            {
                Valid_Enpassant_Moves.add((enpassant+xarr[0])+" "+(y+1));
            }
        }

        if(!Board[x][y].Check_MovesAvailableUnderCheck.isEmpty())
        {
            Valid_Moves.retainAll(Board[x][y].Check_MovesAvailableUnderCheck);
            Valid_Killable_Moves.retainAll(Board[x][y].Check_MovesAvailableUnderCheck);
        }
    }
}

package org.example.chess;

import java.util.HashSet;

public class Move extends Main{
    boolean isInsideBoard(int x,int y){
        /*
            This function checks if the given value of x and y are within the Chess Board
         */
            return x<8 && x>=0 && y<8 && y>=0;
    }
    void Rook(int x, int y)
    {
         /*
            READ THIS METHOD IF YOU WANT TO UNDERSTAND HOW THE MOVEMENT WORKS
         */

        /*
            This function calculates the possible movements of a Rook at position (x, y) on a chess board.
            The Rook can move horizontally or vertically, so we check all 4 directions.
         */

        // Arrays representing the direction vectors for the Rook's movement.
        // xarr holds movement along the x-axis (left and right),
        // yarr holds movement along the y-axis (up and down).
        int[] xarr = new int[]{-1,1,0,0};
        int[] yarr = new int[]{0,0,-1,1};

        // Loop through all possible movement directions (left, right, up, down)
        for (int i = 0; i < xarr.length; i++) {
            int tx=x,ty=y;// Using temporary variables to store the Rook's current position

            // While the Rook stays within the bounds of the board
            while (isInsideBoard(tx+xarr[i],ty+yarr[i]))
            {
                // Move the Rook one step in the current direction
                tx+=xarr[i];
                ty+=yarr[i];

                // If the destination square is empty, add it to the list of possible moves
                if(Board[tx][ty] == null){
                    Movable.add(tx+" "+ty);
                }

                // If there is an opponent's piece at the destination, add it to the Killable list and stop checking further in this direction
                else if (Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece) {
                    Killable.add(tx+" "+ty);
                    break;// Stop checking further in this direction after encountering an enemy piece
                }

                // If there is a friendly piece at the destination, stop checking further in this direction
                else{
                    break;
                }
            }
        }
    }
    void Bishop(int x, int y)
    {
        /*
            This function calculates the possible movements of a Bishop at position (x, y) on a chess board.
            The Bishop can move Diagonally in four directions.
            Same loop as rook only the Values in the array are changed
         */
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
        /*
            This function calculates the possible movements of a Queen at position (x, y) on a chess board.
            The Queen Is a combination of rook and a Bishop
            The values in the array is the Combination of Values of the arrays in Rook and Bishop
         */
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
        int[] xarr = new int[]{-1,1,-1,1,2,2,-2,-2};
        int[] yarr = new int[]{-2,-2,2,2,-1,1,-1,1};

        for (int i = 0; i < xarr.length; i++) {
            int tx = x+xarr[i];
            int ty = y+yarr[i];
            if(!(isInsideBoard(tx,ty))) continue;
            if(Board[tx][ty] == null){
                Movable.add(tx+" "+ty);
            }
            else if (Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece) {
                Killable.add(tx + " " + ty);
            }
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
            Movable.add((x+xarr[0])+" "+(y+yarr[0]));
            if(isInsideBoard(x+xarr[1],y+yarr[1]) && Board[x+xarr[1]][y+yarr[1]] == null && Board[x][y].isFirstMove){
                Movable.add((x+xarr[1])+" "+(y+yarr[1]));
            }
        }

        if(isInsideBoard(x+xarr[2],y+yarr[2]) && Board[x+xarr[2]][y+yarr[2]] != null && (Board[x+xarr[2]][y+yarr[2]].isWhitePiece != Board[x][y].isWhitePiece)){
            Killable.add((x+xarr[2])+" "+(y+yarr[2]));
        }
        if(isInsideBoard(x+xarr[2],y+yarr[3]) && Board[x+xarr[2]][y+yarr[3]] != null && (Board[x+xarr[2]][y+yarr[3]].isWhitePiece != Board[x][y].isWhitePiece)) {
            Killable.add((x+xarr[2])+" "+(y+yarr[3]));
        }

        if(x==enpassant){
            if(isInsideBoard(enpassant,y-1) && Board[enpassant][y-1]!=null && Board[enpassant][y-1].EnPasant)
            {
                Enpassant.add((enpassant+xarr[0])+" "+(y-1));
            }
            if(isInsideBoard(enpassant,y+1) && Board[enpassant][y+1]!=null && Board[enpassant][y+1].EnPasant)
            {
                Enpassant.add((enpassant+xarr[0])+" "+(y+1));
            }
        }
    }
}

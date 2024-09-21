package org.example.chess;

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
        /*
       This function calculates the possible movements of a Knight at position (x, y) on a chess board.
       The Knight moves in an "L" shape, so the movement offsets are pre-defined.
        */

        // Arrays representing the movement vectors for the Knight.
        // The Knight moves either 2 squares in one direction and 1 square in a perpendicular direction.
        int[] xarr = new int[]{-1,1,-1,1,2,2,-2,-2};// x-axis changes (L-shape moves)
        int[] yarr = new int[]{-2,-2,2,2,-1,1,-1,1};// y-axis changes (L-shape moves)

        // Loop through all possible movement of Knight
        for (int i = 0; i < xarr.length; i++) {
            int tx = x+xarr[i]; // x position
            int ty = y+yarr[i]; // y position

            // Check if the new position (tx, ty) is within the board bounds
            if(!(isInsideBoard(tx,ty))) continue;

            // If the destination square is empty, add it to the list of possible moves
            if(Board[tx][ty] == null){
                Movable.add(tx+" "+ty);
            }

            // If there is an opponent's piece at the destination, add it to the Killable list
            else if (Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece) {
                Killable.add(tx + " " + ty);
            }
        }
    }
    void King(int x, int y)
    {
        int[] xarr = new int[]{-1,-1,-1,0,0,1,1,1};
        int[] yarr = new int[]{-1,0,1,-1,1,-1,0,1};
        for (int i = 0; i < xarr.length; i++) {
            int tx = x+xarr[i];
            int ty = y+yarr[i];
            if(!(isInsideBoard(tx,ty))) continue;
            if(Board[tx][ty] == null){
                Movable.add(tx+" "+ty);
            } else if (Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece) {
                Killable.add(tx + " " + ty);
            }
        }
    }
    void Pawn(int x, int y)
    {
        /*
       This function calculates the possible movements of a Pawn at position (x, y) on a chess board.
       The Pawn's movement depends on its color:
       - White Pawns move upwards and can capture diagonally.
       - Black Pawns move downwards and can capture diagonally.
        */

        // Arrays representing the movement vectors for the Pawn based on its color.
        // White Pawns move up the board, Black Pawns move down the board.
        int[] wxarr;int[] wyarr;
        if(Board[x][y].isWhitePiece){
            wxarr = new int[]{-1,-2,-1,-1};// White Pawn movement (upwards)
            wyarr = new int[]{0,0,-1,1};// White Pawn can move one or two squares forward or capture diagonally
        }else{
            wxarr = new int[]{1,2,1,1};// Black Pawn movement (downwards)
            wyarr = new int[]{0,0,-1,1};// Black Pawn can move one or two squares forward or capture diagonally
        }

        // Loop through all possible movement offsets for the Pawn
        for(int i=0;i<wxarr.length;i++){
            int tx = x+wxarr[i];
            int ty = y+wyarr[i];

            // Skip if the new position (tx, ty) is out of bounds
            if(!isInsideBoard(tx, ty)){continue;}

            switch (i){

                // Case 0: Single square move forward
                case 0 ->{
                    // If the destination square is empty, add it to the Movable list
                    if(Board[tx][ty]==null){
                        Movable.add(tx+" "+ty);
                    }else{
                        // Stop checking further in this direction if there is a piece blocking the path
                        i=2;
                    }
                }

                // Case 1: Two squares move forward (initial move only)
                case 1 ->{
                    // Add position to Movable list if the Pawn is on its first move and the destination is empty
                    if(Board[x][y].isFirstMove && Board[tx][ty]==null){
                        Movable.add(tx+" "+ty);
                    }
                }

                // Cases 2 and 3: Diagonal capture moves
                case 2, 3 ->{
                    // If the destination square has an opponent's piece, add it to the Killable list
                    if(Board[tx][ty]!=null && (Board[tx][ty].isWhitePiece != Board[x][y].isWhitePiece)){
                        Killable.add(tx+" "+ty);
                    }
                }
            }
        }
    }
}

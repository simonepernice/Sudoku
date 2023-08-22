/**
*   Sudoku is a game to play Sudoku on the phone
*   Copyright (C) 2006 Simone Pernice <pernice@libero.it>
*
*   This file is part of Sudoku.
*
*   Sudoku is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   Sudoku is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with Sudoku.  If not, see <https://www.gnu.org/licenses/>.
*/

import javax.microedition.lcdui.*;

public abstract class Paintable {
    
    protected static int XSIZE = -1, XSIZEDRAWING;
    protected static int YSIZE = -1, YSIZEDRAWING;    
    
    public static final int BLACK =     0X00000000;  
    public static final int GRAY =      0X00AAAAAA;  
    public static final int LIGHTGRAY = 0X00DDDDDD;  
    public static final int WHITE =     0X00FFFFFF;  
    public static final int YELLOW=     0X00FFFF00;  
    public static final int RED =       0X00AA0000;
    public static final int GREEN =     0X00008800;
    public static final int BLUE =      0X000000FF;
    
    public static int initialize (Canvas toPaint, int titleSize) {
        if (XSIZE == -1) XSIZE = toPaint.getWidth() / ChessBoard.SIZE;
        if (YSIZE == -1) YSIZE = (toPaint.getHeight()-titleSize) / ChessBoard.SIZE;
        XSIZEDRAWING = XSIZE /Cell.DRAWINGSSQRT;
        YSIZEDRAWING = YSIZE /Cell.DRAWINGSSQRT;
        return (toPaint.getHeight() - YSIZE * ChessBoard.SIZE);
    }    
    
    //This is called to paint this object at coordinates x, y
    abstract void paint (int x, int y, Graphics g);
    
}

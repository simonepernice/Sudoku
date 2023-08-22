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

import javax.microedition.lcdui.Canvas;

public class Cursor extends Paintable {
    
    private Cell cursorCell;
    private ChessBoard chessBoard;
    
    public Cursor(ChessBoard cb) {
        this.chessBoard = cb;
        cursorCell = chessBoard.getCell(0, 0);
    }
    
    public void addElement (Element e) {
        Element old = cursorCell.getElement();
        if (old != null && old.isImposed()) return;
        cursorCell.setElement(e);
    }
    
    public Cell getCell () {
        return cursorCell;
    }
    
    public void move (int gameAction) {
        switch (gameAction) {                
            case Canvas.FIRE:
                if (cursorCell.isClearable()) {
                    cursorCell.setElement(null);
                    chessBoard.checkIfBoardIsCorrect();
                } 
                break;            
            default:
                cursorCell = chessBoard.nextCell (cursorCell, gameAction);
                break;
        }
    }

    void paint(int x0, int y0, javax.microedition.lcdui.Graphics g) {
        g.setColor(YELLOW);
        if (cursorCell.thereIsDrawing()) {
            g.drawRect(x0+cursorCell.getX()*XSIZE+cursorCell.getDrawingX()*XSIZEDRAWING, y0+cursorCell.getY()*YSIZE+cursorCell.getDrawingY()*YSIZEDRAWING, XSIZEDRAWING, YSIZEDRAWING);        
            g.drawRect(x0+cursorCell.getX()*XSIZE+cursorCell.getDrawingX()*XSIZEDRAWING-1, y0+cursorCell.getY()*YSIZE+cursorCell.getDrawingY()*YSIZEDRAWING-1, XSIZEDRAWING+2, YSIZEDRAWING+2);                    
        } else {
            g.drawRect(x0+cursorCell.getX()*XSIZE, y0+cursorCell.getY()*YSIZE, XSIZE, YSIZE);        
            g.drawRect(x0+cursorCell.getX()*XSIZE-1, y0+cursorCell.getY()*YSIZE-1, XSIZE+2, YSIZE+2);        
        }
    }

    void toggleSure() {
        if (cursorCell.thereIsElement()) cursorCell.getElement().toggleSure();
        else if (cursorCell.thereIsDrawing()) cursorCell.nextDrawing();
    }

    void toggleDrawing() {
        cursorCell.toggleDrawing();
    }
}

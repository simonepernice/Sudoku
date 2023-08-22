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

import java.io.*;
import javax.microedition.lcdui.*;

public class Element extends Paintable implements Recordable {        
    
    private static final byte XDISP = 1, YDISP = 1;
    
    private Symbol symbol;
    
    private byte status;
    private static final byte IMPOSED = 1, WRONG = 2, SURE = 4;
    
    public static void initialize () {
        byte[] width =  {(byte) (XSIZE - 2 * XDISP), (byte) (XSIZEDRAWING - 2 * XDISP)};
        byte[] height = {(byte) (YSIZE - 2 * YDISP), (byte) (YSIZEDRAWING - 2 * YDISP)};
        Symbol.initializeZoom(width, height);
    }
    
    public Element () {
        this (RandomNumber.get (ChessBoard.SIZE), false);
    }    
    
    public Element (int value) {
        this (value, false);
    }
    
    public Element (Element e) {
        this (e.symbol.getValue(), e.isImposed());  
    }
    
    public Element (int value, boolean imposed) {
        status = 0;
        setImposed (imposed);
        symbol = new Symbol (value);
    }
      
    public Symbol getSymbol () {
        return symbol;
    }       
    
    public void setImposed (boolean value) {
        if (value) status |= IMPOSED;
        else status &= ~IMPOSED;
    }
    
    public boolean isImposed () {
        return (status & IMPOSED) != 0;
    }
    
    public void setWrong (boolean value) {
        if (value) status |= WRONG;
        else status &= ~WRONG;
    }
        
    public boolean isWrong () {
        return (status & WRONG) != 0;
    }
    
    public void setSure (boolean value) {
        if (value) status |= SURE;
        else status &= ~SURE;
    }
    
    public void toggleSure () {
        if (isSure()) setSure(false);
        else if (! isWrong()) setSure(true);
    }
        
    public boolean isSure() {
        return (status & SURE) != 0;
    }    
    
    public boolean equals (Element b) {
        if (b == null) return false;       
        return symbol.equals(b.symbol);
    }

    void paint(int x, int y, javax.microedition.lcdui.Graphics g) {       
        if (isImposed()) g.setColor(GREEN);        
        else if (isWrong()) g.setColor(RED);
        else if (isSure()) g.setColor(BLUE);
        else g.setColor(BLACK);
        symbol.paint(x+XDISP+1, y+YDISP+1, g);
    }

    public void save(DataOutputStream dos) throws IOException {
        symbol.save(dos);
        dos.writeByte (status);
    }

    public void load(DataInputStream dis) throws IOException {
        symbol = new Symbol ();
        symbol.load (dis);
        status = dis.readByte();
    }

}

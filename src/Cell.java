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

public class Cell extends Paintable implements Recordable {
    
    public static final int DRAWINGSSQRT = 2;
    private static final int DRAWINGS = DRAWINGSSQRT * DRAWINGSSQRT;
    protected int x, y;                 //position in the chess
    private Element element;            //element conteined if != null
    
    private Element[] drawing;          //drawing != null is used to discriminate if the cell is in drawing mode
    private int drawingCell;
    
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        element = null;
        drawing = null;
    }
    
    public void toggleDrawing () {
        if (drawing != null) {
            drawing = null;
            element = null;
        } else {
            element = null;
            drawing = new Element[DRAWINGS];
        }
    }
    
    public boolean thereIsDrawing () {
        return drawing != null;
    }

    public void setElement (Element e) {
        if (drawing != null) {
            drawing[drawingCell] = e;
            nextDrawing();
        } else this.element = e;
    }
    
    public Element getElement () {
        return element;
    }
    
    public boolean thereIsElement () {
        return element != null;
    }

    public int getX () {
        return x;
    }
    
    public int getY () {
        return y;
    }

    public boolean equals (Cell a) {
        return x == a.x && y == a.y;
    }

    public int getDrawingX () {
        return drawingCell%DRAWINGSSQRT;
    }
    
    public int getDrawingY () {
        return drawingCell/DRAWINGSSQRT;
    }
    
    void paint(int x, int y, javax.microedition.lcdui.Graphics g) {
        if (drawing != null) {
            g.setColor(GRAY);
            g.drawLine(x+XSIZEDRAWING, y, x+XSIZEDRAWING, y+YSIZE);
            g.drawLine(x, y+YSIZEDRAWING, x+XSIZE, y+YSIZEDRAWING);
            Symbol.setZoom(1);
            for (int i=0; i < DRAWINGS; ++i)
                    if (drawing[i] != null) drawing[i].paint(x+i%DRAWINGSSQRT*XSIZEDRAWING, y+i/DRAWINGSSQRT*YSIZEDRAWING, g);
            Symbol.setZoom(0);
        } else if (element != null) element.paint(x, y, g);
    }  
    
    public void save(DataOutputStream dos) throws IOException {
        dos.writeBoolean (thereIsElement());
        if (thereIsElement()) getElement().save(dos);
        
        dos.writeBoolean (thereIsDrawing());
        if (thereIsDrawing()) 
            for (int i=0; i < DRAWINGS; ++i) {
                dos.writeBoolean (drawing[i] != null);
                if (drawing[i] != null) drawing[i].save(dos);
            }
    }

    public void load(DataInputStream dis) throws IOException {
        if (dis.readBoolean()) {
            Element e = new Element ();
            e.load(dis);
            setElement(e);
        }
        
        if (dis.readBoolean()) {
            drawing = new Element [DRAWINGS];
            for (int i=0; i < DRAWINGS; ++i)
                if (dis.readBoolean()) {
                    drawing[i] = new Element();
                    drawing[i].load(dis);        
                }                
        }
    }    

    public void clear() {
        element = null;
        drawing = null;
    }

    void nextDrawing() {
        ++drawingCell;
        if (drawingCell >= DRAWINGS) drawingCell = 0;
    }

    public boolean isClearable() {
        if (thereIsElement() && (element.isImposed() || element.isSure())) return false;
        return true;
    }
    
}

import java.io.*;
import java.util.*;
import javax.microedition.lcdui.*;

public class ChessBoard extends Paintable implements Recordable {
    public final static int SIZESQRT = 3;
    public final static int SIZE = SIZESQRT * SIZESQRT;   //number of rows and culumns
    public final static int SIZE2C = SIZE / 2 + SIZE % 2; //ceiling of SIZE / 2
    
    private Cell[][] cellChessBoard;   
    private static Bag[] rows = new Bag[SIZE];
    private static Bag[] cols = new Bag[SIZE];
    private static Bag[] sqrs = new Bag[SIZE];
    
    private static Checker check = new Checker ();
            
        
    public ChessBoard() {
        int r, c;
        cellChessBoard = new Cell[SIZE][SIZE];       
        
        for (r=0; r < SIZE; ++r)
            for (c=0; c < SIZE; ++c)
                cellChessBoard[c][r] = new Cell(c, r);
        
        initializePuzzleBuilder();        
    }   
    
    public void initializePuzzleBuilder () {
        int r, c;
        for (r=0; r < SIZE; ++r) {
            rows[r] = new Bag ();
            cols[r] = new Bag ();
            sqrs[r] = new Bag ();
        }
    }
    
    public void paint (int x, int y, Graphics g) {        
        g.setColor(LIGHTGRAY);
        g.fillRect(x, y, SIZE * XSIZE, SIZE * YSIZE);   
        
        int r, c;
        for (c=0; c < SIZE; ++c)
            for (r=0; r < SIZE; ++r)
                cellChessBoard[c][r].paint(x + c*XSIZE , y + r*YSIZE, g);
        
        r = 0;
        for (c=0; c <= SIZE; ++c) {
            if (r == 0) {
                r = SIZESQRT;
                g.setColor(BLACK);
            } else g.setColor(GRAY);
            --r;
            g.drawLine(x + c * XSIZE, y, x + c * XSIZE, y + SIZE * YSIZE);
            g.drawLine(x, y + c * YSIZE, x + SIZE * XSIZE, y + c * YSIZE);
        }                                
    }
        
    
    private void clearWrongBoardStatus () {
        int r, c;
        for (c=0; c < SIZE; ++c)
            for (r=0; r < SIZE; ++r) 
                if (cellChessBoard[c][r].thereIsElement()) cellChessBoard[c][r].getElement().setWrong(false);
    }
   
    public boolean checkIfBoardIsCorrect () {
        clearWrongBoardStatus();
        
        boolean result = true;        
        
        int r, c;
        for (c=0; c < SIZE; c += SIZESQRT) 
            for (r=0; r < SIZE; r += SIZESQRT)
                result &= checkSquareCorrect (c, r);        
        
        for (c=0; c < SIZE; ++c) 
                result &= checkRowCorrect (c);

        for (r=0; r < SIZE; ++r)
                result &= checkColumnCorrect (r);    
        
        return result;                
    }
    
    public void clearAllCells () {
        int r, c;
        for (c=0; c < SIZE; ++c)
            for (r=0; r < SIZE; ++r) 
                cellChessBoard[c][r].clear();            
    }
    
    public void clearAllNotSureCells () {
        Element e;
        int r, c;
        for (c=0; c < SIZE; ++c)
            for (r=0; r < SIZE; ++r) {
                if (cellChessBoard[c][r].isClearable()) cellChessBoard[c][r].clear();
            }
        checkIfBoardIsCorrect();
    }    
    
    public Cell getCell (int x, int y) {
        return cellChessBoard[x][y];
    }    
    
    public boolean makePuzzle () {       
        Element e;
        
        int r, c, sq;
        for (r=0; r < SIZE; ++r) {
            rows[r].rebuild();
            cols[r].rebuild();
            sqrs[r].rebuild();
        }
        
        for (r=0; r < SIZE; ++r) {
            for (c=0; c < SIZE; ++c) {
                e = cols[c].getRandom();             
                sq = divideBySIZESQRT(r) + floorToSIZESQRT(c);
                while (! rows[r].isPresent(e) || ! sqrs[sq].isPresent(e)) {                            
                    e = cols[c].next();
                    if (e == null) {
                        return false;
                    }
                }
                cellChessBoard[c][r].setElement(e);
                if (e != null) {
                    rows[r].removeElement();
                    cols[c].removeElement();
                    sqrs[sq].removeElement();
                }                    
            }
        }
   
        return true;
    }    
    
    //fast divider (not apply operator /)
    private int divideBySIZESQRT (int i) {
        int result = 0;
        while (true) {
            i -= SIZESQRT;
            if (i < 0) return result;
            ++result;
        } 
    }
    
    //fast floor (not apply operator / nor %)
    private int floorToSIZESQRT (int i) {
        int result = 0;
        while (true) {
            i -= SIZESQRT;
            if (i < 0) return result;
            result += SIZESQRT;
        } 
    }    
    
    private boolean checkSquareCorrect(final int i, final int j) {        
        check.reset();
        Element e;
        int r, c;
        for (c=i; c < i + SIZESQRT; ++c)
            for (r=j; r < j + SIZESQRT; ++r) {
                e = cellChessBoard[c][r].getElement();
                if (e != null && check.isProblemAdd(e.getSymbol())) e.setWrong(true);
            }
        check.reset();
        for (c=i + SIZESQRT -1; c >= i; --c)
            for (r=j + SIZESQRT -1; r >= j ; --r) {
                e = cellChessBoard[c][r].getElement();
                if (e != null && check.isProblemAdd(e.getSymbol())) e.setWrong(true);
            }        
        return check.isCorrect();
    }

    private boolean checkRowCorrect(final int y) {
        check.reset();
        Element e;
        int r, c;
        for (c=0; c < SIZE; ++c) {
            e = cellChessBoard[c][y].getElement();
            if (e != null && check.isProblemAdd(e.getSymbol())) e.setWrong(true);
        }           
        check.reset();
        for (c=SIZE-1; c >= 0; --c) {
            e = cellChessBoard[c][y].getElement();
            if (e != null && check.isProblemAdd(e.getSymbol())) e.setWrong(true);
        }                   
        return check.isCorrect();
    }
    
    private boolean checkColumnCorrect(final int x) {
        check.reset();
        Element e;
        int r, c;
        for (r=0; r < SIZE; ++r) {
            e = cellChessBoard[x][r].getElement();
            if (e != null && check.isProblemAdd(e.getSymbol())) e.setWrong(true);
        }            
        check.reset();
        for (r=SIZE-1; r >= 0; --r) {
            e = cellChessBoard[x][r].getElement();
            if (e != null && check.isProblemAdd(e.getSymbol())) e.setWrong(true);
        }        
        return check.isCorrect();
    }
    
    public void copy (final ChessBoard cb, final int cells) {
        int xy, x0, y0, x1 = 0, y1 = 0;
        int i, j;
        final int STEP = 2;
        
        for (i=0; i < cells; i += STEP) {
            do {
                xy = RandomNumber.get(SIZE*SIZE);
                x0 = xy / SIZE;
                y0 = xy % SIZE;
            } while (cellChessBoard[x0][y0].thereIsElement() || (x0 == SIZE2C && y0 == SIZE2C));
            for (j = 0; j < STEP; ++j) {
                switch (j) {
                    case 0:
                        x1 = x0;
                        y1 = y0;
                        break;
                    case 1:
                        x1 = SIZE - 1 - x0;
                        y1 = SIZE - 1 - y0;
                        break;
                }
                cellChessBoard[x1][y1].setElement(cb.cellChessBoard[x1][y1].thereIsElement() ? new Element(cb.cellChessBoard[x1][y1].getElement()) : null);                
            }
        }
    }
    
    public Cell nextCell (Cell current, int direction) {
        int r, c;
        c = current.getX();
        r = current.getY();
        switch (direction) {
            case Canvas.UP:
                if (r > 0) return cellChessBoard[c][r-1];
                return cellChessBoard[c][ChessBoard.SIZE-1];
            case Canvas.DOWN:
                if (r < SIZE - 1) return cellChessBoard[c][r+1];
                return cellChessBoard[c][0];
            case Canvas.LEFT:
                if (c > 0) return cellChessBoard[c-1][r];
                return cellChessBoard[ChessBoard.SIZE-1][r];
            case Canvas.RIGHT:
                if (c < SIZE - 1) return cellChessBoard[c+1][r];
                return cellChessBoard[0][r];
        }
        return current;
    }    

    public void save(DataOutputStream dos) throws IOException {
        int r, c;
        for (c=0; c < SIZE; ++c)
            for (r=0; r < SIZE; ++r) 
                cellChessBoard[c][r].save(dos);
    }

    public void load(DataInputStream dis) throws IOException {
        int r, c;
        for (c=0; c < SIZE; ++c)
            for (r=0; r < SIZE; ++r) 
                cellChessBoard[c][r].load(dis);        
    }

}

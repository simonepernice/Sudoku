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

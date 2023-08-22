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

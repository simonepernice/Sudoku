import java.io.*;
import javax.microedition.lcdui.Graphics;
/*
 * Symbol.java
 *
 * Created on 25 novembre 2006, 14.58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author simone
 */
public class Symbol extends Paintable implements Recordable {
    private static final byte HEIGHT = 7, WIDTH = 5, SKIP = -1;   
    private static final byte[][] SYMBOLS = {
        {1,1,2,0,2,6,3,6,SKIP,1,6},
        {0,1,1,0,3,0,4,1,4,2,0,6,4,6},                                  
        {0,1,1,0,3,0,4,1,4,2,3,3,2,3,SKIP,3,3,4,4,4,5,3,6,1,6,0,5},
        {4,5,0,5,3,0,3,6},
        {4,0,0,0,0,2,3,2,4,3,4,5,3,6,0,6},        
        {4,1,3,0,1,0,0,1,0,5,1,6,3,6,4,5,4,4,3,3,1,3,0,4},        
        {0,1,0,0,4,0,4,1,3,2,3,6},
        {1,0,0,1,0,2,1,3,3,3,4,4,4,5,3,6,1,6,0,5,0,4,1,3,3,3,SKIP,4,2,4,1,3,0,1,0},
        {0,5,1,6,3,6,4,5,4,1,3,0,1,0,0,1,0,2,1,3,3,3,4,2}
    };
    
    private static byte[][][] zoomedSymbols;
    private static byte[] thickness;
    private static byte zoomInUse;
    
    private static int x0, y0, x1, y1, x2, i, j, k;
        
    private byte value;
            
    public Symbol () {
        this (0);
    }
    
    public Symbol(int value) {
        if (value >= ChessBoard.SIZE || value < 0) value = 0;
        this.value = (byte) value ;
    }
    
    public boolean equals (Symbol s) {
        return value == s.value;
    }
    
    public byte getValue () {
        return value;
    }
    
    public static void initializeZoom (byte[] width, byte[] height) {//the new symbols ratio is precomputed to increase drawing speed        
        thickness = new byte [width.length];
        for (i=0; i < width.length; ++i) {
            thickness[i] = (byte) Math.min (width[i]/WIDTH/2, height[i]/HEIGHT/2);
        }
        zoomedSymbols = new byte [width.length][][];
        for (j=0; j < width.length; ++j) {
            zoomedSymbols[j] = new byte [SYMBOLS.length][];
            for (k=0; k < SYMBOLS.length; ++k) {
                zoomedSymbols[j][k] = new byte [SYMBOLS[k].length];
                for (i=0; i < SYMBOLS[k].length; ++i) {
                    if (SYMBOLS[k][i] == SKIP) {
                        zoomedSymbols[j][k][i] = SKIP;
                        continue;
                    }
                    zoomedSymbols[j][k][i] = (byte) (SYMBOLS[k][i] * (width[j] - thickness[j]) / WIDTH);
                    ++i;
                    zoomedSymbols[j][k][i] = (byte) (SYMBOLS[k][i] * (height[j] - thickness[j]) / HEIGHT);
                }
            }            
        }
    }
        
    public static void setZoom (int z) {
        if (z < 0 || z >= zoomedSymbols.length) z = 0;
        zoomInUse = (byte) z;
    }

    void paint(int x, int y, Graphics g) {
        final int s = SYMBOLS[value].length;
        for (k=0; k <= thickness[zoomInUse]; ++k)
            for (j=0; j <= thickness[zoomInUse]; ++j) {
                x1 = zoomedSymbols[zoomInUse][value][0]+j;
                y1 = zoomedSymbols[zoomInUse][value][1]+k;   
                x2 = zoomedSymbols[zoomInUse][value][2];
                i = 3;
                do {                                        
                    x0 = x1;
                    y0 = y1;
                    x1 = x2+j;
                    y1 = zoomedSymbols[zoomInUse][value][i++]+k;
                    if (i < s) x2 = zoomedSymbols[zoomInUse][value][i++];
                    if (x2 == SKIP) x2 = zoomedSymbols[zoomInUse][value][i++];
                    else g.drawLine(x+x0, y+y0, x+x1, y+y1);
                } while (i < s);
            }
    }

    public void save(DataOutputStream dos) throws IOException {
        dos.writeByte(value);
    }

    public void load(DataInputStream dis) throws IOException {
        value = dis.readByte();
    }
    
}

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

public class TitleBar extends Paintable implements Recordable {
        
    private static final Font FONT = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
    private static final String TIME = "Elapsed time: ";
    public  static final int FONTHEIGHT = FONT.getHeight();
    
    private long startTime, elapsed;


    public void resetTime () {
        startTime = System.currentTimeMillis();
    }
    
    public void restartTime () {
        startTime = System.currentTimeMillis() - elapsed;
    }
     
    public int elapsedSeconds () {
        elapsed = System.currentTimeMillis() - startTime;
        return (int) (elapsed / 1000);
    }

    void paint(int x, int y, javax.microedition.lcdui.Graphics g) {
        g.setColor(BLACK);
        g.drawString(TIME+toTime(elapsedSeconds()), x, y, 0);        
    }    

    public void save(DataOutputStream dos) throws IOException {
        dos.writeLong(elapsed);
    }

    public void load(DataInputStream dis) throws IOException {
        elapsed = dis.readLong();
    }
    
    public static String toTime (int s) {
        StringBuffer time = new StringBuffer();
        int h = s / 3600;
        if (h > 0) {
            time.append(h).append("h");
            s -= h * 3600;
        }
        int m = s / 60;
        if (m > 0) {
            time.append(m).append("m");
            s -= m * 60;
        }
        return  time.append(s).append("s").toString();
    }
}

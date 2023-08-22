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

/*
 * WaitForm.java
 *
 * Created on 10 novembre 2006, 22.42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import javax.microedition.lcdui.*;
/**
 *
 * @author simone
 */
public class WaitForm extends Form {
    private Gauge gTimer;
    private ChoiceGroup cgDifficul;
    
    /**
     * Creates a new instance of WaitForm
     */
    public WaitForm() {
        super ("Wait, please...");
        gTimer = new Gauge ("I am building the puzzle...", false, Gauge.INDEFINITE, Gauge.INCREMENTAL_UPDATING);
        append (gTimer);
    }
    
    public void next () {
        gTimer.setValue(Gauge.INCREMENTAL_UPDATING);
    }
    
}

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
 * Checker.java
 *
 * Created on 5 novembre 2006, 12.18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author simone
 */
public class Checker {
    private int values;

    public boolean isProblemAdd (Symbol s) {        
        int add = (1 << s.getValue());
        if ((values & add) != 0) {
            return true;
        }
        values |= add;
        return false;
    }
    
    /*It is simple to understand that checks agains missing symbols because it compare with 111111111
      but it also check for duplicate elements because the number of symbol for row, column e square is 9, so a duplication is like a missing*/
    
    public boolean isCorrect () {
        return values == 0x000001FF; //111111111
    }

    void reset() {
        values = 0;
    }
    
}

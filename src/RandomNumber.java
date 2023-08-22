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
 * RandomNumber.java
 *
 * Created on 5 novembre 2006, 11.23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import java.util.Random;
/**
 *
 * @author simone
 */
public class RandomNumber {
    
    private final static Random randGenerator = new Random();
    
    public static int get (int max) {
        return Math.abs(randGenerator.nextInt()) % max;
    }
    
    public static void setSequence () {
        randGenerator.setSeed(System.currentTimeMillis());
    }
    
    public static void setSequence (int seed) {
        randGenerator.setSeed (seed);
    }    
    
}

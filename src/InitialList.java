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

import java.io.IOException;
import javax.microedition.lcdui.*;

public class InitialList extends List implements CommandListener, Showable {
    
    private Sudoku sudoku;
    
    private Alert help, about, gameOver;
    private Command exit;
    
    public InitialList (Sudoku sudoku) {
        super ("Sudoku", List.IMPLICIT);
        
        this.sudoku = sudoku;
        
        append ("New Game", null);
        append ("Continue last game", null);       
        append ("High Scores", null);
        append ("Help", null);
        append ("About", null);
        append ("Quit", null);
                
        addCommand (new Command ("SELECT", Command.OK, 0));    
        
        setSelectedIndex(1, true); //to continue last game
        
        setCommandListener(this);
        
        ScreensKeeper.initialList = this; //This must go before difficultform        
        ScreensKeeper.playScreen = new PlayScreen ();        
        ScreensKeeper.highScoresList = new HighScoresList ();
        ScreensKeeper.initialDataForm = new InitialDataForm ();                
        
        help = new Alert ("Help", "Sudoku is the well known puzzle game. This version is based upon a chess made by 9 x 9 cells, which are divided in nine squares of 3 x 3 cells (highlighted in the chess). Every cell can store a number between 1 and 9. Sudoku target is to fill all the cells with a number avoiding duplication in every row, column and in the nine squares. Before starting a new game the phone plays solitaire Sudoku matches until it finds a new correct puzzle. Then the phone will show you only few numbers (as more as lower is the difficult level) of its puzzle (drawn in green), which you cannot edit. That means the puzzle to solve is always different and there is at least a solution! Your target is to fill-in the empty cells following Sudoku rules as soon as possible. When a wrong number is inserted, it is drawn in red. There is a cursor (a yellow square) showing where the next number will be inserted. Use numbers between 1 and 9 to insert a new value in the cursor position. Use arrows to move the cursor. If you think the selected number is correct, you can avoid to delete it. Use * to protect/unprotect a number you think is correct (drawn in blue). Use fire button to clear the number in the cursor position or number 0 to clear all the inserted numbers (excepted the ones you think are correct). Use SOLUTION to see the phone solution but your score will not be entered in high score! Use back to go to the initial screen. Every cell has a drawing mode, use # to turn it on and off. It is useful to take notes when you are not sure of the number to set in a cell. In drawing mode a cell can contain four different numbers. Use * to navigate between them, the fire and the number keys behave like standard cell. When you exit, the game is saved in order that you can continue it later. The best ten players (ordered by difficult level and then time) are stored in high score. There is also a challenge mode: insert a number between 0 and 99 to build the same puzzle to challenge yours friends (the score is not added to high score)! Every time Sudoku is started it is possible to continue the last game. Enjoy!", null, AlertType.WARNING);
        help.setTimeout (Alert.FOREVER);
        
        Image simone=null;
        try {
            simone = Image.createImage("/Simone.png");
        } catch (IOException ioe) {}
        about = new Alert ("About", "Sudoku version 2.0.4, build 11, 6th April 2008 is under GNU GPL copyleft license. Sudoku is written by Simone Pernice for my father in law Franco, who likes a lot that game. Sudoku uses vectorial graphic to run on every phone. Thanks to Ilario and Stefania for improvement suggestions. If you like this game send a postcard to Simone Pernice, via Alagna 11, 10155 Torino, Italy. If you find a bug write to pernice@libero.it I will fix it!", simone, AlertType.WARNING);
        about.setTimeout (Alert.FOREVER);
        
        gameOver = new Alert ("Game Over", "Try again!!!", null, AlertType.WARNING);
        gameOver.setTimeout(Alert.FOREVER);
    }

    public void commandAction(Command command, Displayable displayable) {
        switch (getSelectedIndex()) {
            case 0: //new game
                ScreensKeeper.initialDataForm.show();
                break;
            case 1: //continue last game
                ScreensKeeper.playScreen.show();
                break;              
            case 2: //high score
                ScreensKeeper.highScoresList.show();
                break;
            case 3: //help
                ScreensKeeper.display.setCurrent(help, this);
                break;
            case 4: //about 
                ScreensKeeper.display.setCurrent (about, this);
                break;
            case 5: //quit
                sudoku.destroyApp(true);
                break;                    
        }
    }   
    
    public void show () {
        setSelectedIndex(1, true); //to continue last game
        ScreensKeeper.display.setCurrent(this);
    }
    
    public void gameOver () {
        ScreensKeeper.display.setCurrent(gameOver, this);
    }
}

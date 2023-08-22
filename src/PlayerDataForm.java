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

import javax.microedition.lcdui.*;

public class PlayerDataForm extends Form implements CommandListener, Showable {
    
    private TextField tfName;
    private StringItem siTime, siLevel;
    private int time, level;
    
    public PlayerDataForm () {
        super ("New record!");
        
        tfName = new TextField ("Insert your name", null, 10, TextField.ANY);
        siTime = new StringItem ("Your time is", null);
        siLevel = new StringItem ("Level", null);
        
        append (siTime);
        append (tfName);
        append (siLevel);
        
        addCommand(new Command("OK", Command.BACK, 0));
        
        setCommandListener(this);
    }
    
    public void setActive (int score) {
        siTime.setText(Integer.toString(score));
        
    }

    public void commandAction(Command command, Displayable displayable) {
        if (tfName.getString().length() > 0) {
            ScreensKeeper.highScoresList.addPlayer(new PlayerScore (tfName.getString(), time, level));
            ScreensKeeper.highScoresList.show ();
        }
    }

    public void addPlayer (int t, int l) {  
        this.time = t;
        this.level = l;
        tfName.setString("");
        siTime.setText(TitleBar.toTime(time));
        siLevel.setText(InitialDataForm.getLevel((byte)level));        
    }                
    
    public void show () {
        ScreensKeeper.display.setCurrent(this);
    }
}

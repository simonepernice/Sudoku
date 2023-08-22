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

public class InitialDataForm extends Form implements CommandListener, Showable {
    
    private TextField tfChallengeNumber;
    private ChoiceGroup cgDifficultLevel;
    
    public InitialDataForm () {
        super ("Initial Data");
        
        cgDifficultLevel = new ChoiceGroup ("Set difficult Level", ChoiceGroup.EXCLUSIVE);
        cgDifficultLevel.append("Simple", null);
        cgDifficultLevel.append("Medium", null);        
        cgDifficultLevel.append("Advanced", null);
        tfChallengeNumber = new TextField ("If you want to challenge your friends insert the same challenge number and all of you will get exactly the same sequence of pieces. Only the best will win!", null, 2, TextField.NUMERIC);

        append (cgDifficultLevel);
        append (tfChallengeNumber);
        
        addCommand(new Command("OK", Command.OK, 0));
        addCommand(new Command("BACK", Command.BACK, 0));
        
        setCommandListener(this);
    }
    
    public void commandAction(Command command, Displayable displayable) {
        if (command.getCommandType() == Command.BACK) ScreensKeeper.initialList.show();
        else ScreensKeeper.playScreen.resetBoard(tfChallengeNumber.getString().length() == 0 ? -1 : Math.abs(Integer.parseInt(tfChallengeNumber.getString())), cgDifficultLevel.getSelectedIndex());
            
    }

    public void show () {        
        tfChallengeNumber.setString("");
        ScreensKeeper.display.setCurrent(this);
    }                
    
    public static String getLevel(byte level) {
        switch (level) {
            case 0:
                return "SIM";
            case 1:
                return "MED";
            case 2:
                return "ADV";
        }
        return "SIM";
    }    
}

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
import javax.microedition.rms.*;

public class HighScoresList extends List implements CommandListener, Showable {
    
    private final static int NUMOFSCORES = 10;
    private PlayerScore[] results;

    private PlayerDataForm playerDataForm;
    
    public HighScoresList() {
        super ("High Score", List.IMPLICIT);
        
        results = new PlayerScore[NUMOFSCORES];
        
        playerDataForm = new PlayerDataForm ();
        
        loadScores();
        
        refreshScreen ();
        
        addCommand(new Command ("BACK", Command.BACK, 0));
        
        setCommandListener(this);        
    }
    
    public void refreshScreen () {
        deleteAll();
        append (" Time  Level   Name", null);
        for (int i=0; i < NUMOFSCORES; ++i) {
            if (results[i] == null) continue;
            append(results[i].toString(), null);
        }
    }
    
    public void loadScores () {
        RecordStore rs ;
        int i, id;
        PlayerScore ps;
        try {
            rs = RecordStore.openRecordStore("FOM", true);
            RecordEnumeration re = rs.enumerateRecords(null, null, false);
            for (i=0; i<re.numRecords() && i<NUMOFSCORES; ++i) {
                id = re.nextRecordId();
                addToHighScore (loadScore (rs, id));            
            }
            for (; i<NUMOFSCORES; ++i) {
                ps = new PlayerScore ();
                saveScore(ps, rs);
                addToHighScore (ps);
            }
            rs.closeRecordStore();
        } catch (Throwable t) {return;} 
    }
    
    public boolean isGoodTime (int score) {     
        if (score < results[NUMOFSCORES-1].getScore()) return true;
        return false;
    }
    
    private PlayerScore loadScore (RecordStore rs, int id) throws Exception {
        PlayerScore ps = new PlayerScore();
        ps.setRecordID(id);
        ps.load(new DataInputStream(new ByteArrayInputStream (rs.getRecord(id))));   
        return ps;
    }
    
    private void resaveScore (PlayerScore ps, RecordStore rs) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream ();
        ps.save(new DataOutputStream (baos));
        rs.setRecord(ps.getRecordID(), baos.toByteArray(), 0, baos.size());
    }
    
    private void saveScore (PlayerScore ps, RecordStore rs) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream ();
        ps.save(new DataOutputStream (baos));
        ps.setRecordID(rs.addRecord(baos.toByteArray(), 0, baos.size()));             
    }
    
    private PlayerScore addToHighScore (PlayerScore ps) {
        PlayerScore tmp, first=ps;
        for (int i = 0; i < NUMOFSCORES; ++i) {
            if (ps.isBiggerThan (results[i])) {
                tmp = results[i];
                results[i] = ps;
                ps = tmp;
                if (ps == null) break;//That can happen only at the initialization
            }
        }        
        return ps;
    }
    
    public void add (PlayerScore ps) {
        if (! isGoodTime (ps.getScore())) return;
        RecordStore rs;
        try {
            rs = RecordStore.openRecordStore("FOM", true);
            ps.setRecordID(addToHighScore(ps).getRecordID());
            resaveScore(ps, rs);
            rs.closeRecordStore();
        } catch (Throwable t) {}
        
        refreshScreen ();
        select (ps);
    }
    
    private void select (PlayerScore ps) {
        for (int i=0; i < results.length; ++i)
            if (results[i] == ps) {
                setSelectedIndex(i+1, true);
                break;
            }        
    }
    
    public void commandAction (Command c, Displayable d) { 
        ScreensKeeper.initialList.show();
    }

    public void show () {
        ScreensKeeper.display.setCurrent(this);        
    }
    
    public void addPlayer (PlayerScore data) {
        add ((PlayerScore) data);                
    }
    
    public void tryToAddPlayer (int time, int level) {
        if (isGoodTime (time)) {
            playerDataForm.addPlayer(time, level);
            playerDataForm.show();
        } else ScreensKeeper.initialList.gameOver ();
    }  
}

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
import java.util.Vector;
import javax.microedition.rms.*;
import java.io.*;

public class PlayScreen extends Canvas implements CommandListener, Showable, Recordable {
    
    private WaitForm waiter;
    private Alert warningSolution, warningClearAll;
    
    private ChessBoard computer, player, toShow;
    private RecordStore recordStore;
    
    private TitleBar titleBar;
    private final int titleBarHeight;
    
    private Cursor cursor;

    private int challenge = -1, level;
    private boolean paused, solutionSeen, endOfMatch;
    
    public PlayScreen () {
        addCommand(new Command ("BACK", Command.BACK, 0));
        addCommand(new Command ("SOLUTION", Command.OK, 1));
        setCommandListener(this);
        
        waiter = new WaitForm ();
        titleBarHeight = Paintable.initialize(this, TitleBar.FONTHEIGHT);        
        Element.initialize();
        
        computer = new ChessBoard ();
        player = new ChessBoard ();
        toShow = player;        
        
        titleBar = new TitleBar ();
        
        cursor = new Cursor (player);        
        
        paused = true;  
        
        warningSolution = new Alert("Warning", "If you see the solution your time will not be added to high score! Are you sure?", null, AlertType.WARNING);
        warningSolution.setTimeout(Alert.FOREVER);
        warningSolution.addCommand(new Command("YES", Command.OK, 1));
        warningSolution.addCommand(new Command("NO", Command.CANCEL, 0));
        warningSolution.setCommandListener(this);
        
        warningClearAll = new Alert("Warning", "All your not-sure number will be deleated. Are you sure?", null, AlertType.WARNING);
        warningClearAll.setTimeout(Alert.FOREVER);
        warningClearAll.addCommand(new Command("YES", Command.OK, 1));
        warningClearAll.addCommand(new Command("NO", Command.CANCEL, 0));        
        warningClearAll.setCommandListener(this);
        
        load ();
        player.checkIfBoardIsCorrect();        
    }       
    
    private class MakePuzzle extends Thread {
        public MakePuzzle () {
            start();
        }
        
        public void run () {
            computer.clearAllCells();
            
            int i=0;
            while (!computer.makePuzzle()) {
                ++i;
                if (i >= 3 * ChessBoard.SIZE) {
                    waiter.next();
                    i = 0;
                }
            }
            player.copy(computer, cellToCopy(level));
            toShow = player;            
            ScreensKeeper.playScreen.show();
            titleBar.resetTime();            
            new Repainter ();                        
        }
    }
    
    private class Repainter extends Thread {
        public Repainter () {
            start();
        }
        
        public void run () {
            paused = false;
            do {
                repaint ();
                try {
                    sleep (1000);
                } catch (Throwable t) {}                
            } while (! paused && ! endOfMatch);
        }
    }
    
    public void paint(Graphics graphics) {
        graphics.setColor (Paintable.WHITE);
        graphics.fillRect (0, 0, getWidth(), getHeight());                       
        if (toShow == player) titleBar.paint (0, 0, graphics);
        toShow.paint (0, titleBarHeight, graphics);  
        if (toShow == player) cursor.paint (0, titleBarHeight, graphics);
    }
    
    public void keyPressed (int keyCode) {
        if (endOfMatch || toShow == computer) return;
        if (keyCode == Canvas.KEY_POUND) {
            if (! (cursor.getCell().thereIsElement() && cursor.getCell().getElement().isImposed())) cursor.toggleDrawing();
        } else if (keyCode == Canvas.KEY_STAR) {            
            cursor.toggleSure();
        } else if (keyCode == Canvas.KEY_NUM0) {
            ScreensKeeper.display.setCurrent(warningClearAll, this);
        } else if (keyCode >= Canvas.KEY_NUM1 && keyCode <= Canvas.KEY_NUM9) {
            cursor.addElement(new Element(keyCode-Canvas.KEY_NUM1));            
            checkForWin();
        }
        else cursor.move(getGameAction(keyCode));
        
        repaint();         
    }    
    
    public void commandAction(Command command, Displayable displayable) {
        if (displayable == warningClearAll) {
            if (command.getCommandType() == Command.OK) player.clearAllNotSureCells();
            toShow = player;
            show();            
        } else if (displayable == warningSolution) {
            if (command.getCommandType() == Command.OK) {
                toShow = computer;
                solutionSeen = true;
            }
            show();            
        } else { //if (displayable == this) {
            if (command.getCommandType() == Command.BACK) {
                paused = true;
                ScreensKeeper.initialList.show(); 
            } else {
                if (toShow == player) {
                    if (solutionSeen) {
                        toShow = computer;
                        repaint();                        
                    } else {
                        ScreensKeeper.display.setCurrent(warningSolution, this);
                    }
                } else {
                    toShow = player;
                    repaint();
                }
            }
        } 
    }        
    
    public void show () {
        titleBar.restartTime();
        ScreensKeeper.display.setCurrent(this);
        new Repainter ();        
    }

    public void resetBoard (int seed, int level) {
        this.level = level;
        ScreensKeeper.display.setCurrent(waiter);
        player.clearAllCells();
        solutionSeen = false;
        endOfMatch = false;
        challenge = seed;
        if (challenge == -1) {
            RandomNumber.setSequence();
        } else {
            RandomNumber.setSequence(challenge);
            computer.initializePuzzleBuilder();
        }   
        new MakePuzzle ();
    } 
    
    private int cellToCopy (int level) {
        switch (level) {
            case 0:
                return 36;
            case 1:
                return 28;
            case 2:
                return 20;
        }        
        return 20;
    }

    private void checkForWin() {
        if (player.checkIfBoardIsCorrect()) {
            endOfMatch = true;
            if (solutionSeen) {
                Alert a = new Alert ("Warning", "The match is not valid because the solution was seen.", null, AlertType.INFO);
                a.setTimeout(Alert.FOREVER);
                ScreensKeeper.display.setCurrent(a, (Displayable) ScreensKeeper.initialList);                                    
            } else if (challenge < 0) {            
                ScreensKeeper.highScoresList.tryToAddPlayer(titleBar.elapsedSeconds(), level);
            } else {            
                Alert a = new Alert ("Challenge Score", "For the challenge number: "+challenge+"\non difficult level: "+level+"\nyour time is:"+titleBar.elapsedSeconds()+"\nCompare it with your friend to find the winner!", null, AlertType.INFO);
                a.setTimeout(Alert.FOREVER);
                ScreensKeeper.display.setCurrent(a, (Displayable) ScreensKeeper.initialList);                
            }
        }
    }

    public void save() {
        try {
            if (recordStore == null) recordStore = RecordStore.openRecordStore ("chess", true);        
            ByteArrayOutputStream baos = new ByteArrayOutputStream ();
            DataOutputStream dos = new DataOutputStream (baos);
            save(dos);
            recordStore.setRecord(1, baos.toByteArray(), 0, baos.size());
        } catch (Throwable t) {}
    }
    
    public void load() {
        try {
            if (recordStore == null) recordStore = RecordStore.openRecordStore ("chess", true);
            if (recordStore.getNumRecords() < 1) {
                recordStore.addRecord(null, 0, 0);
                save ();
            } else {
                load (new DataInputStream(new ByteArrayInputStream(recordStore.getRecord(1))));
            }
        } catch (Throwable t) {}
    }
    
    public void close () {
        try {
            if (recordStore != null) recordStore.closeRecordStore();
        } catch (Throwable t) {}
    }

    public void save(DataOutputStream dos) throws IOException {
        dos.writeInt(challenge);
        dos.writeInt(level);
        dos.writeBoolean(paused);
        dos.writeBoolean(solutionSeen);
        dos.writeBoolean(endOfMatch);
        computer.save(dos);
        player.save(dos);
        titleBar.save(dos);
    }

    public void load(DataInputStream dis) throws IOException {
        challenge = dis.readInt();
        level = dis.readInt();
        paused = dis.readBoolean();
        solutionSeen = dis.readBoolean();
        endOfMatch = dis.readBoolean();
        computer.load(dis);
        player.load(dis);
        titleBar.load(dis);
    }      
}

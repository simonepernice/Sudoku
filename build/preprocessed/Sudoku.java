import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class Sudoku extends MIDlet {
    private InitialList initialList = null;
    private static long timer;
    
    public void startApp() {
        if (initialList == null) {
            ScreensKeeper.display = Display.getDisplay(this);
            initialList = new InitialList (this);
        }
        initialList.show();
    }
    
    public void pauseApp() {
        ScreensKeeper.playScreen.save();
    }
    
    public void destroyApp(boolean unconditional) {
        ScreensKeeper.playScreen.save();
        ScreensKeeper.playScreen.close();
        notifyDestroyed();
    }
}

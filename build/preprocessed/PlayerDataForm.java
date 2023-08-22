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

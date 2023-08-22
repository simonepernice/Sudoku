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

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

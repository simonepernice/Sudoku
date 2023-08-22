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

import java.io.*;
/*
 * Recordable.java
 *
 * Created on 12 novembre 2006, 0.54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author simone
 */
public interface Recordable {
    
    public void save (DataOutputStream dos) throws IOException;
    
    public void load (DataInputStream dis) throws IOException;
    
}

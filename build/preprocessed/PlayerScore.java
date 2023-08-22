import java.io.*;

public class PlayerScore implements Recordable {
    public static final int MAXTIME = 99*3600+59*60+59;
    
    private String name;
    private int time;
    private int recordID;
    private byte level;

    public PlayerScore(String name, int time, int level) {
        this.name = name;
        this.time = time;
        this.level = (byte) level;
        recordID = -1;
    }
    
    public PlayerScore () {
        this ("", MAXTIME, 0);
    }
    
    public boolean isBiggerThan (PlayerScore b) {
        if (b == null) return true;
        if (b.level != level) return (level - b.level) > 0;
        return (b.time - time) > 0;
    }    
    
    public int getRecordID () {
        return recordID;
    }
    
    public void setRecordID (int rid) {
        recordID = rid;
    }   
    
    public int getScore () {
        return time;
    }
    
    public String getName () {
        return name;
    }
    
    public String toString () {
        String sscore = TitleBar.toTime(time);
        if (time < MAXTIME) return "         ".substring(sscore.length()) + sscore + "  " + InitialDataForm.getLevel(level) + "  " + name;
        return "";
    }        

    public void save (DataOutputStream dos) throws IOException {
        dos.writeUTF(name);
        dos.writeInt(time);
        dos.writeByte(level);
    }    
    
    public void load(DataInputStream dis) throws IOException {
        name = dis.readUTF();
        time = dis.readInt();
        level = dis.readByte();        
    }
}

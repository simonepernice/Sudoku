import java.util.Vector;

public class Bag {
    private Vector elements, removed;
    private int next = 0, repeat = 0;
    
    public Bag () {
        this (ChessBoard.SIZE);
    }
    
    public Bag(final int nOfElements) {
        elements = new Vector (nOfElements);
        removed = new Vector (nOfElements);

        int i;
        for (i=0; i < nOfElements; ++i)
            elements.addElement(new Element (i, true));
        
        mixer ();
    }
    
    public void rebuild () {
        final int size = removed.size();
        int i;
        for (i = 0; i < size; ++i)
            elements.addElement(removed.elementAt(i));
        
        removed.removeAllElements();
    }
    
    private void mixer () {
        int i;
        final int nOfElements = elements.size();
        for (i=0; i <  2 * nOfElements ; ++i)
            swap (RandomNumber.get(nOfElements), RandomNumber.get(nOfElements));                        
    }
    
    public Element getRandom() {
        if (elements.size() == 0) return null;
        next = repeat = RandomNumber.get(elements.size());
        return (Element) elements.elementAt(next);
    }
    
    public Element next () {
        if (elements.size() == 0) return null;
        if (++next >= elements.size()) next = 0;
        if (next == repeat) return null;
        return (Element) elements.elementAt(next);
    }
    
    public boolean isPresent (Element e) {
        final int s = elements.size();
        for (next =0; next < s; ++next)
            if (((Element) elements.elementAt(next)).equals(e)) {
                repeat = next;
                return true;
            }
        return false;
    }
    
    public void removeElement () {
        if (next >= elements.size()) return;
        removed.addElement(elements.elementAt(next));
        elements.removeElementAt(next);
    }
    
    private void swap(int i, int j) {
        Object tmp = elements.elementAt(j);
        elements.setElementAt(elements.elementAt(i), j);
        elements.setElementAt(tmp, i);
    }
}

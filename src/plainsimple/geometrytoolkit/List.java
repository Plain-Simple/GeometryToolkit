package plainsimple.geometrytoolkit;

import java.util.ArrayList;

public class List {
    /* stores elements in list */
    private ArrayList<Double> elements = new ArrayList<>();
    private String name = "";
    public List() {}
    public List(ArrayList<Double> constructor) {
        elements = constructor;
    }
    public void addElement(double d) { elements.add(d); }
    public boolean removeElement(double d) { return elements.remove(d); }
    public int size() { return elements.size(); }
    public double get(int index) { return elements.get(index); }
    public void clear() { elements.clear(); }
    /* returns sum of all elements */
    public double sum() {
        double result = 0;
        for(int i = 0; i < elements.size(); i++)
            result += elements.get(i);
        return result;
    }
    /* returns product of all elements */
    public double product() {
        if(elements.isEmpty())
            return 0.0;
        else {
            double result = elements.get(0);
            for (int i = 1; i < elements.size(); i++)
                result = result * elements.get(i);
            return result;
        }
    }
    /* adds elements of lists together (non-destructive) */
    public List addList(List l) {
        List result = new List(elements);
        for(int i = 0; i < l.size(); i++)
            result.addElement(l.get(i));
        return result;
    }
    /* removes duplicate elements */
    public List removeDuplicates(List l) {
        return new List();
    }
}

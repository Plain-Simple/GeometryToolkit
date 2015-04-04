/* used in Matrix Class to store rows */
package plainsimple.geometrytoolkit;

import java.util.ArrayList;

public class RowVector {
    private ArrayList<Double> elements;
    public RowVector() { elements = new ArrayList<>(); }
    public RowVector(ArrayList<Double> elements) { this.elements = elements; }
    public RowVector(int size) { elements = new ArrayList<>(size); }

    /* returns element at location element_num in row */
    public double get(int element_num) throws IndexOutOfBoundsException {
        try {
            return elements.get(element_num);
        }catch(IndexOutOfBoundsException e) {
            throw e;
        }
    }
    /* returns number of elements in row */
    public int size() { return elements.size(); }
    /* sets element at specified position */
    public void set(int index, double element) throws IndexOutOfBoundsException {
        try {
            elements.set(index, element);
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }
    /* adds element to row */
    public void addElement(double element) { elements.add(element); }

    /* adds two rows */
    public RowVector add(RowVector r) {
        RowVector result = new RowVector(elements.size());
        for(int i = 0; i < result.size(); i++)
            result.set(i, elements.get(i) + r.get(i));
        return result;
    }
    /* subtracts one row from another */
    public RowVector subtract(RowVector r) {
        RowVector result = new RowVector(elements.size());
        for(int i = 0; i < result.size(); i++)
            result.set(i, elements.get(i) - r.get(i));
        return result;
    }
    /* multiplies row by scalar */
    public RowVector multiply(double d) {
        RowVector result = new RowVector(elements.size());
        for(int i = 0; i < result.size(); i++)
            result.set(i, elements.get(i) * d);
        return result;
    }
    /* merges r to current row vector from the right */
    public RowVector merge(RowVector r) {
        RowVector result = new RowVector(size() + r.size());
        for(int i = 0; i < size(); i++)
            result.set(i, get(i));
        for(int i = 0; i < r.size(); i++)
            result.set(size() + i, r.get(i));
        return result;
    }
    /* returns a String representation of row */
    public String getString() {
        return elements.toString();
    }
}

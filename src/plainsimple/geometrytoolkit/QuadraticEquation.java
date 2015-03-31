package plainsimple.geometrytoolkit;

import java.util.ArrayList;

public class QuadraticEquation {
    /* in the form ax^2 + bx + c = 0 */
    private double a;
    private double b;
    private double c;
    private String name;
    public QuadraticEquation() {
        a = 0.0;
        b = 0.0;
        c = 0.0;
        name = "";
    }
    public QuadraticEquation(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    /* returns arraylist with solution(s) */
    private ArrayList<Double> solve() {
        ArrayList<Double> solutions = new ArrayList<>();
        double discriminant = Math.pow(b, 2) - (4 * a * c);
        if (discriminant > 0) { /* equation has real solutions */
            /* -b +- root b^2 - 4ac / 2a */
            solutions.add((-b + Math.sqrt(discriminant)) / (2 * a));
            solutions.add((-b - Math.sqrt(discriminant)) / (2 * a));
        } else if(discriminant == 0) { /* one solution */
            solutions.add((-b + discriminant) / (2 * a));
        } else { /* no real solutions */ // todo: complex numbers
            solutions.add(0.0);
        }
        return  solutions;
    }
    /* returns vertex of parabola as a 2D point */
    private Point2D getVertex() {
        double h = -b / (2 * a);
        return new Point2D(h, plugIn(h));
    }
    private double getYIntercept() {
        return plugIn(0);
    }
    /* returns table as String for output */
    private String getTable(int table_boundaries[]) { // todo: finish

        return "";
    }
    /* returns factored form as a String */
    private ArrayList<String> getFactoredForm(boolean allow_complex) { // todo: finish
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<Double> solutions = solve();
        if(solutions.size() == 1)
            result.add("(x" + getOppositeSign(solutions.get(0)) + solutions.get(0) + ")^2");
        else if(solutions.size() == 2) {
            result.add("(x" + getOppositeSign(solutions.get(0)) + solutions.get(0) + ")(x"
                + getOppositeSign(solutions.get(1)) + ")");
        }
        return result;
    }
    /* calculates ax^2 + bx + c given x*/
    private double plugIn(double x) {
        return a * Math.pow(x, 2) + b * x + c;
    }
    private char getSign(double input) {
        if (input >= 0)
            return '+';
        else
            return '-';
    }
    private char getOppositeSign(double input) {
        if(input >= 0)
            return '-';
        else
            return '+';
    }
    /* Prints equation with proper formatting */
    public String getEquation() {
    /* the output will be stored here */
    String equation_output = "";
    /* if the coefficient is 0, nothing happens */
        if (a != 0) {
        /* outputs the coefficient as long as it isn't 1 */
            if (a != 1) {
                equation_output += Double.toString(a);
            }
            equation_output += "x^2";
        }

        if (b != 0) {
    /* outputs the sign of the coefficient */
            equation_output += getSign(b);
            if (b != 1) {
                equation_output += Double.toString(b);
            }
            equation_output += 'x';
        }
        if (c != 0) {
            equation_output += getSign(c);
            if (c != 1) {
                equation_output += Double.toString(c);
            }
        }
        return (equation_output += " = 0");
    }

}

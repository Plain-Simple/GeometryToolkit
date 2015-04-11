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
    private ArrayList<ComplexNumber> solve() {
        ArrayList<ComplexNumber> solutions = new ArrayList<>();
        double discriminant = Math.pow(b, 2) - (4 * a * c);
        if (discriminant > 0) { /* equation has real solutions */
            /* -b +- root b^2 - 4ac / 2a */
            solutions.add(new ComplexNumber((-b + Math.sqrt(discriminant) / (2 * a)), 0));
            solutions.add(new ComplexNumber((-b - Math.sqrt(discriminant) / (2 * a)), 0));
        } else if(discriminant == 0) { /* one solution */
            solutions.add(new ComplexNumber((-b + discriminant) / (2 * a), 0));
        } else { /* no real solutions */
            solutions.add(new ComplexNumber(-b / (2 * a), Math.sqrt(discriminant * -1) / (2 * a)));
            solutions.add(new ComplexNumber(-b / (2 * a), -1 * Math.sqrt(discriminant * -1) / (2 * a)));
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
    private String getTable(int table_boundaries[]) {
        String result = "";
        /* get arraylists of x values and results from plugging in to equation */
        ArrayList<String> column_1 = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        column_1.add("x");
        values.add("f(x)");
        for(int i = table_boundaries[0]; i <= table_boundaries[1]; i++) {
            column_1.add(Integer.toString(i));
            values.add(Double.toString(plugIn(i)));
        }
        /* establish longest values in each column (for formatting) */
        int largest_col = 0;
        int largest_val = 0;
        for(int i = 0; i < column_1.size(); i++) {
            if(column_1.get(i).length() > largest_col)
                largest_col = column_1.get(i).length();
            if(values.get(i).length() > largest_val)
                largest_val = values.get(i).length();
        }
        /* table format: | largest_col | largest_val | */
        int num_columns = largest_col + largest_val + 6;
        int num_rows = column_1.size() + 1;
        String col_divider = "";
        for(int i = 0; i < num_columns; i++)
            col_divider += "-";
        for(int i = 0; i < num_rows; i++) {
            result += col_divider + "\n";
            result += "| " + column_1.get(i);
            for(int j = column_1.get(i).length(); j < largest_col + 1; j++)
                result += " ";
            result += "| " + values.get(i);
            for(int j = values.get(i).length(); j < largest_val + 1; j++)
                result += " ";
            result += "|\n";
        }
        return result;
    }
    /* returns factored form as a String */
    private String getFactoredForm() { // todo: finish
        String result = "";
        ArrayList<ComplexNumber> solutions = solve();
        if(a != 1)
            result += a;
        if(solutions.size() == 1) {
            result += "(x" + getOppositeSign(solutions.get(0).a());
            if(solutions.get(0).isComplex())
                result += getOppositeSign(solutions.get(0).b()) + "i)^2";
        } else if(solutions.size() == 2) {
            result += "(x" + getOppositeSign(solutions.get(0).a());
            if(solutions.get(0).isComplex())
                result += getOppositeSign(solutions.get(0).b()) + "i";
            result += ")(x" + getOppositeSign(solutions.get(1).a());
            if(solutions.get(1).isComplex())
                result += getOppositeSign(solutions.get(1).b()) + "i)";
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
